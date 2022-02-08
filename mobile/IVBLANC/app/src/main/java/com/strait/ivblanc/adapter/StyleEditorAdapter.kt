package com.strait.ivblanc.adapter

import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.view.*
import android.widget.ImageView
import androidx.core.view.MotionEventCompat
import com.bumptech.glide.Glide
import com.strait.ivblanc.data.model.dto.Clothes
import com.strait.ivblanc.src.photoSelect.PhotoSelectActivity
import kotlin.math.abs

private const val TAG = "EditorAdapter_debuk"
class StyleEditorAdapter(val containerView: ViewGroup) {
    var focusedImageView: ImageView? = null
    // K = largeCategory, V = clothes
    // 상의, 하의 등 각 카테고리 마다 하나의 아이템만 존재
    private val clothesMap = mutableMapOf<Int, Clothes>()

    // 각 카테고리 아이템을 나타내는 이미지뷰
    private val imageViews = mutableMapOf<Int, ImageView>()

    var imageX = 0f
    var imageY = 0f
    var eventX = 0f
    var eventY = 0f

    init {
        val scaleGestureDetector = ScaleGestureDetector(containerView.context, object: ScaleGestureDetector.SimpleOnScaleGestureListener() {
            override fun onScale(detector: ScaleGestureDetector?): Boolean {
                Log.d(TAG, "onScale: ${focusedImageView?.id}")
                val scaleFactor = focusedImageView?.scaleX
                scaleFactor?.let {
                    focusedImageView?.scaleY = scaleFactor * detector!!.scaleFactor
                    focusedImageView?.scaleX = scaleFactor * detector!!.scaleFactor
                }
                return true
            }
        })

        containerView.setOnTouchListener { v, event ->
            Log.d(TAG, "touch: ")
            scaleGestureDetector.onTouchEvent(event)
            focusedImageView?.let {
                if(event.pointerCount < 2) {
                    moveFocusedImageView(it, event)
                }
            }
            v?.performClick()
            true
        }
    }

    private fun moveFocusedImageView(imageView: ImageView, event: MotionEvent) {
        when(event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                imageX = imageView.x
                imageY = imageView.y
                eventX = event.x
                eventY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                imageView.animate().x(imageX + (event.x - eventX))
                    .y(imageY + (event.y - eventY))
                    .setDuration(0)
                    .start()
            }
            MotionEvent.ACTION_UP -> {
                imageX = 0f
                imageY = 0f
                eventX = 0f
                eventY = 0f
            }
        }
    }

    fun addImageView(imageView: ImageView) {
        imageViews[imageViews.size + 1] = imageView.apply {
            setOnClickListener {
                focusedImageView = this
                Log.d(TAG, "addImageView: ${this.id}")
            }
        }
    }

    fun addOrUpdateClothes(clothes: Clothes) {
        val largeCategory = getLargeCategory(clothes)
        clothesMap[largeCategory] = clothes
        setImage(clothes)
    }

    fun deleteClothes(clothes: Clothes) {
        val largeCategory = getLargeCategory(clothes)
        clothesMap.remove(largeCategory)
        removeImage(clothes)
    }

    private fun setImage(clothes: Clothes) {
        imageViews[getLargeCategory(clothes)]?.let {
            it.layoutParams.width = 150.dp
            it.layoutParams.height = 150.dp
            it.visibility = View.VISIBLE
            Glide.with(it).load(clothes.url).into(it)
        }
    }

    private fun removeImage(clothes: Clothes) {
        imageViews[getLargeCategory(clothes)]?.let {
            it.setImageDrawable(null)
            it.visibility = View.GONE
        }
    }

    private fun getLargeCategory(clothes: Clothes): Int {
        return clothes.category.toString()[0].digitToInt()
    }

    // Int를 pixel로 변환
    private val Int.dp: Int
        get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

}