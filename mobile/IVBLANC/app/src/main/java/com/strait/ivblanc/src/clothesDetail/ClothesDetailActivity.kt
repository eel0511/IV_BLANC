package com.strait.ivblanc.src.clothesDetail

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.strait.ivblanc.R
import com.strait.ivblanc.config.BaseActivity
import com.strait.ivblanc.data.model.dto.Clothes
import com.strait.ivblanc.databinding.ActivityClothesDetailBinding
import com.strait.ivblanc.util.CategoryCode
import com.strait.ivblanc.util.MaterialCode

class ClothesDetailActivity : BaseActivity<ActivityClothesDetailBinding>(ActivityClothesDetailBinding::inflate) {
    lateinit var clothes: Clothes
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.getParcelableExtra<Clothes>("clothes")?.let {
            clothes = it
        } ?: finish()
        setClothesInfo()
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.imageViewClothesDetailClose.setOnClickListener {
            finish()
        }
        binding.imageViewClothesDetailFavorite.setOnClickListener {
            when(clothes.favorite) {
                0 -> {
                    // TODO: 2022/01/31 즐겨찾기 세팅 함수 호출   
                }
                else -> {
                    // TODO: 2022/01/31 즐겨찾기 해제 함수 호출
                }
            }
        }
        binding.imageViewClothesDetailStyle.setOnClickListener {
            // TODO: 2022/01/31 스타일 생성 화면으로 이동 
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
        binding.imageViewClothesDetailColor.backgroundTintList = ColorStateList.valueOf(Color.parseColor(clothes.color))
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