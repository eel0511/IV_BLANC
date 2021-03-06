package com.strait.ivblanc.src.history

import android.app.Dialog
import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import com.bumptech.glide.Glide
import com.strait.ivblanc.config.BaseActivity
import com.strait.ivblanc.data.model.dto.History
import com.strait.ivblanc.databinding.ActivityHistoryDetailBinding

import android.location.Address
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.strait.ivblanc.R
import com.strait.ivblanc.adapter.HistoryDetailRecyclerViewAdapter
import com.strait.ivblanc.adapter.PhotoRecyclerViewAdapter
import com.strait.ivblanc.data.model.dto.HistoryPhoto
import com.strait.ivblanc.data.model.viewmodel.HistoryViewModel
import com.strait.ivblanc.ui.DeleteDialog
import com.strait.ivblanc.util.CaptureUtil
import com.strait.ivblanc.util.Status

import java.io.IOException
import java.util.*

private const val TAG = "HistoryDetailActivity_debuk"
class HistoryDetailActivity : BaseActivity<ActivityHistoryDetailBinding>(
    ActivityHistoryDetailBinding::inflate) {
    lateinit var history: History
    lateinit var historyDetailRecyclerViewAdapter: HistoryDetailRecyclerViewAdapter
    private val historyViewModel: HistoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.getParcelableExtra<History>("history")?.let {
            history = it
        } ?: finish()
        Log.d("HISTORY_DETAIL", "onCreate called, historyId = " + history.historyId)
        setHistoryInfo()
        setClickListeners()
        setRecyclerView()
        setObserverLiveData()
    }

    private fun setClickListeners() {
        binding.imageViewHistoryDetailClose.setOnClickListener {
            finish()
        }
        binding.imageViewHistoryDetailEdit.setOnClickListener {
            val intent = Intent(this, HistoryEditActivity::class.java)
                .putExtra("history", history)
                .putExtra("location", getLocation())
            startActivity(intent)
            finish()
        }
        binding.imageViewHistoryDetailAddPhoto.setOnClickListener {
            showGalleryDialog()
        }
    }

    private fun setObserverLiveData() {
        historyViewModel.historyResponseStatus.observe(this) {
            when(it.status) {
                Status.SUCCESS -> {
                    toast("??????", Toast.LENGTH_SHORT)
                }
                Status.LOADING -> {
                    toast("????????? ???", Toast.LENGTH_SHORT)
                }
                Status.ERROR -> {
                    toast(it.message.toString(), Toast.LENGTH_SHORT)
                }
            }
        }
    }

    private fun showGalleryDialog() {
        val galleryFragment = HistoryPhotoFragment(object: HistoryPhotoFragment.ImageSelectedListener {
            override fun getResult(imageUris: List<Uri>) {
                if(imageUris.isEmpty()) return
                val absolutePathList = mutableListOf<String>()
                for(i in imageUris) {
                    CaptureUtil.getAbsolutePathFromImageUri(this@HistoryDetailActivity, i)?.let {
                        absolutePathList.add(
                            it
                        )
                    }
                }
                historyViewModel.addHistoryPhotos(history.historyId, absolutePathList)
            }
        })
        galleryFragment.show(supportFragmentManager, "photo")
    }

    // TODO: 2022/02/16 ?????? ????????? ?????? ???????????? ?????? 
    private fun showGalleryDialogForUpdate(photo: HistoryPhoto) {
        val galleryFragment = HistoryPhotoFragment(object: HistoryPhotoFragment.ImageSelectedListener {
            override fun getResult(imageUris: List<Uri>) {
                (supportFragmentManager.findFragmentByTag("photo") as DialogFragment).dismiss()
                if(imageUris.isEmpty()) return

                val absolutePath = CaptureUtil.getAbsolutePathFromImageUri(this@HistoryDetailActivity, imageUris[0])
                absolutePath?.let {
                    historyViewModel.updateHistoryPhotos(photo.photoId, absolutePath)
                }
            }
        })
        galleryFragment.show(supportFragmentManager, "photo")
    }

    private fun setHistoryInfo() {
        if(history.styleUrl != null) {
            Glide.with(this).load(history.styleUrl).override(1350, 1350).into(binding.imageViewHistoryDetailStyle)
        }

        if(history.subject == ""){
            binding.textViewHistoryDetailSubject.text = "?????? ??????"
        } else {
            binding.textViewHistoryDetailSubject.text = history.subject
        }

        binding.textViewHistoryDetailDate.text = history.date
        binding.textViewHistoryDetailLocation.text = getLocation()
        binding.textViewHistoryDetailTemperature.text = history.temperature_high.toString() + "??C/" + history.temperature_low.toString() + "??C"
        binding.textViewHistoryDetailText.text = history.text

        if(history.photos.isEmpty())
            binding.recyclerViewHistoryDetailPhoto.visibility = View.GONE

        if(history.weather == "??????"){
            binding.imageViewHistoryDetailWeather.setImageResource(R.drawable.icon_weather_sunny_48);
        } else if(history.weather == "??????"){
            binding.imageViewHistoryDetailWeather.setImageResource(R.drawable.icon_weather_cloudy_64);
        } else if(history.weather == "???"){
            binding.imageViewHistoryDetailWeather.setImageResource(R.drawable.icon_weather_snowy_48);
        } else if(history.weather == "???"){
            binding.imageViewHistoryDetailWeather.setImageResource(R.drawable.icon_weather_rainy_48);
        }
    }

    private fun getLocation() : String {

        val geocoder = Geocoder(this, Locale.KOREA)

        var list: List<Address>? = null
        try {
            val latitude = history.location
            val longitude = history.field
            list = geocoder.getFromLocation(
                latitude,  // ??????
                longitude,  // ??????
                10
            ) // ????????? ?????? ??????
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e("test", "????????? ?????? - ???????????? ??????????????? ????????????")
        }

        if (list != null && !list.isEmpty()) {
            var cut = list.get(0).toString().split("\"")
            return cut[1]
        }
        return "??? ??? ?????? ??????"
    }

    private fun setRecyclerView(){
        historyDetailRecyclerViewAdapter = HistoryDetailRecyclerViewAdapter()
        historyDetailRecyclerViewAdapter.apply {
            data = history.photos
            itemLongClickListener = object: HistoryDetailRecyclerViewAdapter.ItemLongClickListener {
                override fun onClick(photo: HistoryPhoto) {
                    showSelectDialog(photo)
                }
            }
        }
        binding.recyclerViewHistoryDetailPhoto.apply {
            adapter = historyDetailRecyclerViewAdapter
            layoutManager = LinearLayoutManager(this@HistoryDetailActivity, RecyclerView.HORIZONTAL, false)
        }
    }

    private fun showSelectDialog(photo: HistoryPhoto) {
        DeleteDialog(this)
            .setContent("???????????? ?????????????????????????")
            .setNegativeButtonText("??????")
            .setOnNegativeClickListener {
                showGalleryDialogForUpdate(photo)
            }
            .setPositiveButtonText("??????")
            .setOnPositiveClickListener {
                historyViewModel.deleteHistoryPhoto(photo.photoId)
            }.build().show()
    }
}