package com.strait.ivblanc.src.process

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.strait.ivblanc.R
import com.strait.ivblanc.config.BaseActivity
import com.strait.ivblanc.data.model.viewmodel.ProcessViewModel
import com.strait.ivblanc.databinding.ActivityProcessBinding
import com.strait.ivblanc.util.Status
import com.strait.ivblanc.util.StatusCode

class ProcessActivity : BaseActivity<ActivityProcessBinding>(ActivityProcessBinding::inflate) {
    lateinit var viewPager: ViewPager2
    lateinit var viewPagerAdapter: FragmentStateAdapter
    lateinit var loadingDialog: Dialog
    private val FRAGMENT_NUMBER = 4
    private val processViewModel: ProcessViewModel by viewModels()
    lateinit var imgUri: Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.getStringExtra("uri")?.let {
            imgUri = Uri.parse(it.toString())
        }
        if(!this::imgUri.isInitialized) {
            toast("선택한 이미지가 없습니다.", Toast.LENGTH_SHORT)
            finish()
        } else {
            init()
        }
    }

    private fun init() {
//        viewPager = binding.viewpagerProcess
//        viewPagerAdapter = SimpleFragmentStateAdapter(this)
//        viewPager.adapter = viewPagerAdapter
        binding.imageViewProcess.setImageURI(imgUri)
        processViewModel.imgUri = imgUri
        processViewModel.absoluteImgPath = getAbsolutePath(imgUri)
        processViewModel.clothesResponseStatus.observe(this) { resource ->
            when(resource.status) {
                Status.SUCCESS -> {
                    dismissLoading()
                    toast("옷 등록이 완료되었습니다.", Toast.LENGTH_SHORT)
                    setResult(StatusCode.OK)
                    finish()
                }
                Status.LOADING -> {
                    showLoading()
                }
                Status.ERROR -> {
                    dismissLoading()
                    resource.message?.let { it -> toast(it, Toast.LENGTH_SHORT) }
                }
            }
        }
    }

    private fun getAbsolutePath(imgUri: Uri): String? {
        val cursor = contentResolver.query(imgUri, arrayOf(MediaStore.MediaColumns.DATA), null, null, null)
        if (cursor != null) {
            if(cursor.moveToFirst()) {
                return cursor.getString(0)
            }
        }
        return null
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

    inner class SimpleFragmentStateAdapter(fa: FragmentActivity): FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = FRAGMENT_NUMBER

        override fun createFragment(position: Int): Fragment {
            return when(position) {
                0 -> CategoryFragment()
                1 -> ColorFragment()
                2 -> MaterialFragment()
                else -> SeasonFragment()
            }
        }
    }

    fun goNext() {
        if(viewPager.currentItem < 4) {
            viewPager.currentItem += 1
        }
    }

    fun goBefore() {
        if(viewPager.currentItem > 0) {
            viewPager.currentItem -= 1
        }
    }
}