package com.strait.ivblanc.adapter

import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.strait.ivblanc.data.model.dto.Clothes

class StyleEditorAdapter(val containerView: ViewGroup) {
    // K = largeCategory, V = clothes
    // 상의, 하의 등 각 카테고리 마다 하나의 아이템만 존재
    private val clothesMap = mutableMapOf<Int, Clothes>()

    // 각 카테고리 아이템을 나타내는 이미지뷰
    private val imageViews = mutableMapOf<Int, ImageView>()

    fun addImageView(imageView: ImageView) {
        imageViews[imageViews.size + 1] = imageView
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