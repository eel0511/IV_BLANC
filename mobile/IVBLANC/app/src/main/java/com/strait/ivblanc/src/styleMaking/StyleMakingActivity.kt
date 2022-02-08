package com.strait.ivblanc.src.styleMaking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
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

class StyleMakingActivity : BaseActivity<ActivityStyleMakingBinding>(ActivityStyleMakingBinding::inflate) {
    lateinit var style: Style
    lateinit var styleEditorAdapter: StyleEditorAdapter
    lateinit var recyclerViewAdapter: StyleRecyclerViewAdapter
    lateinit var bottomSheetRVAdapter: BottomSheetClothesRVAdapter
    lateinit var itemTouchHelper: ItemTouchHelper
    lateinit var bottomSheet: BottomSheetBehavior<ConstraintLayout>
    private val clothesViewModel: ClothesViewModel by viewModels()
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
        setStyleEditorAdapter()
        setRecyclerView()
        setBottomSheetRecyclerView()
    }

    private fun setToolbar() {
        val toolbar = binding.toolbarStyleMaking.toolbar
        toolbar.findViewById<TextView>(R.id.textView_toolbar).text = "스타일 만들기"
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
        bottomSheetRVAdapter = BottomSheetClothesRVAdapter()
        binding.recyclerViewStyleMakingBottomSheet.apply {
            adapter = bottomSheetRVAdapter
            layoutManager = GridLayoutManager(this@StyleMakingActivity, 3, RecyclerView.VERTICAL, false)
        }
        clothesViewModel.clothesListLiveData.observe(this) {
            bottomSheetRVAdapter.setData()
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
}