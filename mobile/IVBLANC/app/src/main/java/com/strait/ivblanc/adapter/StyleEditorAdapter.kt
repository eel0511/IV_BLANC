package com.strait.ivblanc.adapter

import android.content.res.Resources
import android.util.Log
import android.view.*
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.strait.ivblanc.R
import com.strait.ivblanc.data.model.dto.Clothes

private const val TAG = "EditorAdapter_debuk"

class StyleEditorAdapter(val containerView: ViewGroup) {
    private var _focusedImageView: ImageView? = null

    // K = largeCategory, V = clothes
    // 상의, 하의 등 각 카테고리 마다 하나의 아이템만 존재
    private val clothesMap = mutableMapOf<Int, Clothes>()

    // 각 카테고리 아이템을 나타내는 이미지뷰
    // largeCategory, imageView
    private val imageViews = mutableMapOf<Int, ImageView>()

    var imageX = 0f
    var imageY = 0f
    var eventX = 0f
    var eventY = 0f

    init {
        val scaleGestureDetector = ScaleGestureDetector(
            containerView.context,
            object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
                override fun onScale(detector: ScaleGestureDetector?): Boolean {
                    Log.d(TAG, "onScale: ${_focusedImageView?.id}")
                    val scaleFactor = _focusedImageView?.scaleX
                    scaleFactor?.let {
                        _focusedImageView?.scaleY = scaleFactor * detector!!.scaleFactor
                        _focusedImageView?.scaleX = scaleFactor * detector!!.scaleFactor
                    }
                    return true
                }
            })

        containerView.setOnTouchListener { v, event ->
            // 모든 터치 이벤트는 제스처 디텍터로 전달
            scaleGestureDetector.onTouchEvent(event)
            _focusedImageView?.let {
                // 1개의 포인터만 존재할 때, 이미지 드래그 실행
                // TODO: 2022/02/08 핀치 줌을 한 뒤, 한 손가락만 놨을 때 드래그 이벤트 발생 방지 추가
                if (event.pointerCount < 2) {
                    moveFocusedImageView(it, event)
                }
            }
            v?.performClick()
            true
        }
    }

    private fun moveFocusedImageView(imageView: ImageView, event: MotionEvent) {
        when (event.actionMasked) {
            // 다운일 때 이미지 뷰의 위치, 이벤트의 위치 저장
            MotionEvent.ACTION_DOWN -> {
                Log.d(TAG, "moveFocusedImageView: DOWN")
                imageX = imageView.x
                imageY = imageView.y
                eventX = event.x
                eventY = event.y
            }
            // 드래그일 때, 저장된 포인트와 움직인 포인트의 차이만큼 이미지뷰 이동
            MotionEvent.ACTION_MOVE -> {
                Log.d(TAG, "moveFocusedImageView: MOVE")
                imageView.animate().x(imageX + (event.x - eventX))
                    .y(imageY + (event.y - eventY))
                    .setDuration(0)
                    .start()
                settingxy(event,imageView)

            }
            //
            MotionEvent.ACTION_UP -> {
                settingxy(event,imageView)
                Log.d(TAG, "moveFocusedImageView: UP")
                imageX = 0f
                imageY = 0f
                eventX = 0f
                eventY = 0f
            }
        }
    }

    fun settingxy(event: MotionEvent,imageView: ImageView){
        Log.d(TAG, "settingxy: "+containerView.x+" "+containerView.y+" "+imageView.x+" "+imageView.y)
        //우
        if (imageView.x +imageView.width > containerView.width) {
            imageView.animate().x(containerView.width.toFloat()-imageView.width)
                .y(imageView.y)
                .setDuration(0)
                .start()
        }
        //좌
        if (imageView.x  < 0) {
            imageView.animate().x(0f)
                .y(imageView.y)
                .setDuration(0)
                .start()
        }
        //하
        if (imageView.y+imageView.height > containerView.height) {
            imageView.animate().x(imageView.x)
                .y(containerView.height.toFloat()-imageView.height)
                .setDuration(0)
                .start()
        }
        //상
        if (imageView.y  < 0) {
            imageView.animate().x(imageView.x)
                .y(0f)
                .setDuration(0)
                .start()
        }
        //우하
       if(imageView.x +imageView.width > containerView.width && imageView.y+imageView.height > containerView.height){
           imageView.animate().x(containerView.width.toFloat()-imageView.width)
               .y(containerView.height.toFloat()-imageView.height)
               .setDuration(0)
               .start()
       }
        //우상
        if(imageView.x +imageView.width > containerView.width && imageView.y  < 0){
            imageView.animate().x(containerView.width.toFloat()-imageView.width)
                .y(0f)
                .setDuration(0)
                .start()
        }
        //좌하
        if(imageView.x  < 0 && imageView.y+imageView.height > containerView.height){
            imageView.animate().x(0f)
                .y(containerView.height.toFloat()-imageView.height)
                .setDuration(0)
                .start()
        }
        //좌상
        if(imageView.x  < 0 &&imageView.y  < 0){
            imageView.animate().x(0f)
                .y(0f)
                .setDuration(0)
                .start()

        }
    }
    fun changeImageZorder(list: List<Int>) {
        var order = 10F
        list.forEach {
            imageViews[it]?.z = order
            order -= 1F
        }
    }

    fun addImageView(imageView: ImageView) {
        imageViews[imageViews.size + 1] = imageView.apply {
            setOnClickListener {
                setFocusedImageView(this)
                Log.d(TAG, "addImageView: ${this.id}")
            }
            isClickable = false
        }
    }

    // 카테고리에 해당하는 옷이 추가되거나 변경될 때, 이미지뷰에 세팅
    // 이때 추가, 변경된 이미지의 뷰로 포커스 이동
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
            it.isClickable = true
            setFocusedImageView(it)
            Glide.with(it).load(clothes.url).into(it)
        }
    }

    fun setFocusedImageView(imageView: ImageView) {
        _focusedImageView?.let { image ->
            deleteFocusedImageIndicator(image)
        }
        setFocusedImageIndicator(imageView)
        _focusedImageView = imageView
    }

    fun dismissFocusedImageView() {
        _focusedImageView?.let {
            deleteFocusedImageIndicator(it)
        }
        _focusedImageView = null
    }

    private fun deleteFocusedImageIndicator(imageView: ImageView) {
        imageView.background = null
    }

    private fun setFocusedImageIndicator(imageView: ImageView) {
        imageView.background = ResourcesCompat.getDrawable(imageView.resources, R.drawable.circle_stroke, null)
    }

    private fun removeImage(clothes: Clothes) {
        imageViews[getLargeCategory(clothes)]?.let {
            it.setImageDrawable(null)
            it.isClickable = false
        }
    }

    private fun getLargeCategory(clothes: Clothes): Int {
        return clothes.category.toString()[0].digitToInt()
    }

    // Int를 pixel로 변환
    private val Int.dp: Int
        get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

}