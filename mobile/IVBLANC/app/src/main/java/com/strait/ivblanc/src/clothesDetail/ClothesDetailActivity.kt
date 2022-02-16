package com.strait.ivblanc.src.clothesDetail

import android.app.Dialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.strait.ivblanc.R
import com.strait.ivblanc.config.BaseActivity
import com.strait.ivblanc.data.model.dto.Clothes
import com.strait.ivblanc.data.model.response.ClothesSimpleResponse
import com.strait.ivblanc.data.model.viewmodel.ClothesViewModel
import com.strait.ivblanc.data.model.viewmodel.MainViewModel
import com.strait.ivblanc.databinding.ActivityClothesDetailBinding
import com.strait.ivblanc.util.*

class ClothesDetailActivity : BaseActivity<ActivityClothesDetailBinding>(ActivityClothesDetailBinding::inflate) {
    lateinit var clothes: Clothes
    lateinit var loadingDialog: Dialog
    val clothesViewModel: ClothesViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.getParcelableExtra<Clothes>("clothes")?.let {
            clothes = it
            if(clothes.category.toString().startsWith("1")) {
                binding.imageViewClothesDetailStyle.visibility = View.VISIBLE
            }
        } ?: finish()
        setClothesInfo()
        setClickListeners()
        initFavorite()
        setObserverLiveData()
    }

    private fun showLoading() {
        loadingDialog = Dialog(this)
        loadingDialog.apply {
            setContentView(R.layout.dialog_loading)
            window?.setBackgroundDrawable(ResourcesCompat.getDrawable(resources, R.drawable.rounded_rectangle, null))
            setCanceledOnTouchOutside(false)
            setCancelable(false)
        }.show()
    }

    private fun dismissLoading() {
        if(this::loadingDialog.isInitialized && loadingDialog.isShowing) {
            loadingDialog.dismiss()
        }
    }

    private fun setObserverLiveData() {
        //좋아요 하트 실시간 변경, 0 받아오면 오류임
        clothesViewModel.resFavorite.observe(this){
            if(clothesViewModel.resFavorite.value==0){
                favoriteDialog("error")
            }else{
                if(clothes.favorite==0){
                    clothes.favorite=1
                }else{
                    clothes.favorite=0
                }
                initFavorite()
            }
        }

        clothesViewModel.clothesResponseStatus.observe(this) {
            when(it.status) {
                Status.SUCCESS -> {
                    dismissLoading()
                    if(it.data is ClothesSimpleResponse) {
                        val resultUrl = (it.data as ClothesSimpleResponse).dataSet!!
                        val resultFragment = AiResultFragment(resultUrl)
                        resultFragment.show(supportFragmentManager, "aiResult")
                    }
                }
                Status.ERROR -> {
                    dismissLoading()
                    toast(it.message!!, Toast.LENGTH_SHORT)
                }
                Status.LOADING -> {
                    showLoading()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        initFavorite()
    }
    fun initFavorite(){
        if(clothes.favorite==1){
            binding.imageViewClothesDetailFavorite.setImageResource(R.drawable.ic_heart_filled)
        }else{
            binding.imageViewClothesDetailFavorite.setImageResource(R.drawable.ic_heart_out_line)
        }
    }
    fun favoriteDialog(title: String) {
        MaterialAlertDialogBuilder(this, R.style.MyDialogTheme)
            .setTitle(title)
            .setPositiveButton("확인") { dialog, which ->
                // Respond to positive button press
                object : View.OnClickListener {
                    override fun onClick(v: View?) {
                        //applicant 수정 필요 받아오는값으로
                        onBackPressed()
                    }
                }
            }
            .show()
    }
    private fun setClickListeners() {
        binding.imageViewClothesDetailClose.setOnClickListener {
            finish()
        }

        if(intent.getStringExtra("friend").isNullOrBlank()) {
            binding.imageViewClothesDetailFavorite.setOnClickListener {
                when(clothes.favorite) {
                    0 -> {
                        clothesViewModel.addFavorite(clothes.clothesId)
                    }
                    else -> {
                        clothesViewModel.deleteFavorite(clothes.clothesId)
                    }
                }
                setResult(StatusCode.OK)
                clothesViewModel.getAllClothesWithCategory(CategoryCode.TOTAL)
            }
        }

        binding.imageViewClothesDetailStyle.setOnClickListener {
            clothesViewModel.betaUrl(clothes.url)
        }
    }

    private fun setClothesInfo() {
        Glide.with(this).load(clothes.url).into(binding.imageViewClothesDetail)
        // TODO: 2022/01/31 카테고리 코드 변경시 수정
        val codeSet = CategoryCode().codeSet
        val materialCodeSet = MaterialCode().codeSet
        val largeCategoryCode = clothes.category.toString()[0].digitToInt()
        binding.textViewClothesDetailCategoryLarge.text = resources.getString(codeSet[largeCategoryCode]!!)
        binding.textViewClothesDetailCategorySmall.text = resources.getString(codeSet[clothes.category]!!)
        // TODO: 2022/02/11 variety, etc 컬러코드 대응 필요
        when(clothes.color) {
            "variety" -> {
                binding.imageViewClothesDetailColor.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.color_wheel, null))
            }
            "etc" -> {
                binding.imageViewClothesDetailColor.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_etc, null))
            }
            else -> {
                binding.imageViewClothesDetailColor.backgroundTintList = ColorStateList.valueOf(Color.parseColor(clothes.color))
            }
        }
        binding.textViewClothesDetailMaterial.text = resources.getString(materialCodeSet[clothes.material]!!)
        binding.textViewClothesDetailSize.text = clothes.size.toString()
        binding.textViewClothesDetailSeason.text = when(clothes.season) {
            1 -> "봄"
            2 -> "여름"
            3 -> "가을"
            4 -> "겨울"
            else -> "미입력"
        }
        binding.textViewClothesDetailLike.text = clothes.likePoint.toString()
        binding.textViewClothesDetailDislike.text = clothes.dislikePoint.toString()
    }
}