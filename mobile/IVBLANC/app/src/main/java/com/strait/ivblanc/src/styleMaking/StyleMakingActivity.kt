package com.strait.ivblanc.src.styleMaking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.strait.ivblanc.R
import com.strait.ivblanc.adapter.BottomSheetClothesRVAdapter
import com.strait.ivblanc.adapter.StyleEditorAdapter
import com.strait.ivblanc.adapter.StyleRecyclerViewAdapter
import com.strait.ivblanc.component.ItemTouchHelperCallback
import com.strait.ivblanc.config.BaseActivity
import com.strait.ivblanc.data.model.dto.Clothes
import com.strait.ivblanc.data.model.dto.Style
import com.strait.ivblanc.data.model.viewmodel.ClothesViewModel
import com.strait.ivblanc.data.model.viewmodel.MainViewModel
import com.strait.ivblanc.databinding.ActivityStyleMakingBinding
import com.strait.ivblanc.util.CategoryCode

private const val TAG = "StyleMakingActivity_debuk"
class StyleMakingActivity : BaseActivity<ActivityStyleMakingBinding>(ActivityStyleMakingBinding::inflate) {
    lateinit var style: Style
    lateinit var styleEditorAdapter: StyleEditorAdapter
    lateinit var recyclerViewAdapter: StyleRecyclerViewAdapter
    lateinit var bottomSheetRVAdapter: BottomSheetClothesRVAdapter
    lateinit var itemTouchHelper: ItemTouchHelper
    lateinit var bottomSheet: BottomSheetBehavior<ConstraintLayout>
    private val clothesViewModel: ClothesViewModel by viewModels()
    lateinit var largeCategories: List<String>
    var smallCategories = listOf<Pair<Int, Int>>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        // clothesViewModel에서 모든 옷 요청
        clothesViewModel.getAllClothesWithCategory(CategoryCode.TOTAL)
        intent.getParcelableExtra<Style>("style")?.let {
            style = it
        }
        bottomSheet = BottomSheetBehavior.from(binding.constraintLayoutStyleMakingBottomSheet)
        setToolbar()
        setBottomSheetDropDown()
        setStyleEditorAdapter()
        setRecyclerView()
        setBottomSheetRecyclerView()
    }

    private fun setToolbar() {
        val toolbar = binding.toolbarStyleMaking.toolbar
        toolbar.findViewById<TextView>(R.id.textView_toolbar).text = "스타일 만들기"
    }

    private fun setBottomSheetDropDown() {
        largeCategories = getLargeCategoryString()
        // 초기화 시 대분류 전체, 소분류 전체로 세팅
        binding.autoCompleteTextViewClothesSelectBottomSheetFLargeCategory.setText(resources.getText(R.string.total))
        binding.autoCompleteTextViewClothesSelectBottomSheetFSmallCategory.setText(resources.getText(R.string.total))

        val largeCategoryAdapter = ArrayAdapter(this, R.layout.list_category_item, largeCategories)
        binding.autoCompleteTextViewClothesSelectBottomSheetFLargeCategory.setAdapter(largeCategoryAdapter)

        // 대분류가 바뀜에 따라 소분류 바꿈
        clothesViewModel.largeCategory.observe(this) {
            smallCategories = clothesViewModel.getSmallCategoriesByLargeCategory(it)
            val smallCategoryAdapter = ArrayAdapter(this, R.layout.list_category_item, getSmallCategoryStringByLargeCategory(it))
            binding.autoCompleteTextViewClothesSelectBottomSheetFSmallCategory.setAdapter(smallCategoryAdapter)
        }

        // 대분류 edittext observe -> viewModel에 대분류 카테고리 변경, 대분류 카테고리로 clothesUpdate
        // 대분류가 전체라면 소분류도 전체로 변경.
        binding.autoCompleteTextViewClothesSelectBottomSheetFLargeCategory.addTextChangedListener {
            val largeCategory = clothesViewModel.largeCategorySet.find { pair ->
                it.toString() == resources.getString(pair.second) && pair.first in 0..9
            }

            largeCategory?.let {
                binding.autoCompleteTextViewClothesSelectBottomSheetFSmallCategory.setText(resources.getText(R.string.total))
                clothesViewModel.setLargeCategory(it.first)
                clothesViewModel.updateClothesByCategory(it.first)
            }
        }

        // 소분류 edittext observe -> viewModel 소분류에 맞는 옷 필터링
        binding.autoCompleteTextViewClothesSelectBottomSheetFSmallCategory.addTextChangedListener {
            if(it.toString().isBlank() || it.toString().isEmpty()) {
                clothesViewModel.setSmallCategory(CategoryCode.UNSELECTED)
                return@addTextChangedListener
            }

            val smallCategory = smallCategories.find { pair ->
                val string = resources.getString(pair.second)
                it.toString() == string
            }

            smallCategory?.let { pair ->
                if(pair.first != CategoryCode.TOTAL_SMALL) {
                    clothesViewModel.updateClothesByCategory(pair.first)
                } else {
                    // TOTAL_SMALL이라면 대분류로 옷 분류
                    clothesViewModel.updateClothesByCategory(clothesViewModel.largeCategory.value!!)
                }
            }
        }
    }

    private fun setRecyclerView() {
        // TODO: 2022/02/08 recyclerViewAdapterItem
        val items = mutableListOf<Clothes>()
        items.add(Clothes(10, 0, "#FFFFFF", 0, "", 0, 1, 0, 0, 1, 100, "", "https://storage.googleapis.com/iv-blanc.appspot.com/909c85b8-dc08-4e18-8b44-c6faf79a9be8.jpg", 13))
        items.add(Clothes(10, 1, "#FFFFFF", 0, "", 0, 1, 0, 0, 1, 100, "", "https://storage.googleapis.com/iv-blanc.appspot.com/909c85b8-dc08-4e18-8b44-c6faf79a9be8.jpg", 13))
        items.add(Clothes(10, 2, "#FFFFFF", 0, "", 0, 1, 0, 0, 1, 100, "", "https://storage.googleapis.com/iv-blanc.appspot.com/909c85b8-dc08-4e18-8b44-c6faf79a9be8.jpg", 13))
        items.add(Clothes(10, 3, "#FFFFFF", 0, "", 0, 1, 0, 0, 1, 100, "", "https://storage.googleapis.com/iv-blanc.appspot.com/909c85b8-dc08-4e18-8b44-c6faf79a9be8.jpg", 13))
        items.add(Clothes(10, 4, "#FFFFFF", 0, "", 0, 1, 0, 0, 1, 100, "", "https://storage.googleapis.com/iv-blanc.appspot.com/909c85b8-dc08-4e18-8b44-c6faf79a9be8.jpg", 13))
        items.add(Clothes(10, 5, "#FFFFFF", 0, "", 0, 1, 0, 0, 1, 100, "", "https://storage.googleapis.com/iv-blanc.appspot.com/909c85b8-dc08-4e18-8b44-c6faf79a9be8.jpg", 13))
        items.add(Clothes(10, 6, "#FFFFFF", 0, "", 0, 1, 0, 0, 1, 100, "", "https://storage.googleapis.com/iv-blanc.appspot.com/909c85b8-dc08-4e18-8b44-c6faf79a9be8.jpg", 13))
        recyclerViewAdapter = StyleRecyclerViewAdapter().apply {
            data = items
        }
        itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(recyclerViewAdapter))
        binding.recyclerViewStyleMakingClothes.apply {
            adapter = recyclerViewAdapter
            layoutManager = LinearLayoutManager(this@StyleMakingActivity, RecyclerView.VERTICAL, false)
        }
        itemTouchHelper.attachToRecyclerView(binding.recyclerViewStyleMakingClothes)
    }

    private fun setBottomSheetRecyclerView() {
        bottomSheetRVAdapter = BottomSheetClothesRVAdapter().apply { 
            itemClickListener = object: BottomSheetClothesRVAdapter.ItemClickListener {
                override fun onClick(clothes: Clothes) {
                    setClothesToEditor(clothes)
                }
            }
        }
        binding.recyclerViewStyleMakingBottomSheet.apply {
            adapter = bottomSheetRVAdapter
            layoutManager = GridLayoutManager(this@StyleMakingActivity, 3, RecyclerView.VERTICAL, false)
        }
        clothesViewModel.clothesList.observe(this) {
            bottomSheetRVAdapter.setData(it)
        }
    }

    private fun setStyleEditorAdapter() {
        styleEditorAdapter = StyleEditorAdapter(binding.constraintLayoutStyleMakingEdit)
        styleEditorAdapter.addImageView(binding.imageViewStyleMakingTop)
        styleEditorAdapter.addImageView(binding.imageViewStyleMakingBottom)
        styleEditorAdapter.addImageView(binding.imageViewStyleMakingOuter)
        styleEditorAdapter.addImageView(binding.imageViewStyleMakingShoes)
        styleEditorAdapter.addImageView(binding.imageViewStyleMakingBag)
        styleEditorAdapter.addImageView(binding.imageViewStyleMakingHat)
        styleEditorAdapter.addImageView(binding.imageViewStyleMakingEtc)
        if(this::style.isInitialized) {
            style.styleDetails.forEach {
                styleEditorAdapter.addOrUpdateClothes(it.clothes)
            }
        }
    }

    private fun getLargeCategoryString(): List<String> {
        val result = mutableListOf<String>()
        clothesViewModel.largeCategorySet.forEach {
            result.add(resources.getString(it.second))
        }
        return result.toList()
    }

    private fun getSmallCategoryStringByLargeCategory(largeCategory: Int): List<String> {
        val result = mutableListOf<String>()
        result.add(resources.getString(R.string.total))
        clothesViewModel.getSmallCategoriesByLargeCategory(largeCategory).forEach {
            result.add(resources.getString(it.second))
        }
        return result.toList()
    }
    
    private fun setClothesToEditor(clothes: Clothes) {
        if(this::styleEditorAdapter.isInitialized) {
            Log.d(TAG, "setClothesToEditor: ${clothes.clothesId}")
            styleEditorAdapter.addOrUpdateClothes(clothes)    
        }
    }
}