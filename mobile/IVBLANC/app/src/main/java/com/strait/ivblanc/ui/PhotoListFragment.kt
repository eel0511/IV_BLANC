package com.strait.ivblanc.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.strait.ivblanc.R
import com.strait.ivblanc.adapter.ExpandableRecyclerViewAdapter
import com.strait.ivblanc.config.BaseFragment
import com.strait.ivblanc.data.model.dto.Clothes
import com.strait.ivblanc.data.model.dto.PhotoItem
import com.strait.ivblanc.data.model.dto.Style
import com.strait.ivblanc.data.model.viewmodel.ClothesViewModel
import com.strait.ivblanc.data.model.viewmodel.MainViewModel
import com.strait.ivblanc.data.model.viewmodel.StyleViewModel
import com.strait.ivblanc.databinding.FragmentPhotoListBinding
import com.strait.ivblanc.src.clothesDetail.ClothesDetailActivity
import com.strait.ivblanc.src.styleMaking.StyleMakingActivity
import com.strait.ivblanc.util.CategoryCode

// TODO: 2022/02/04 generic 오용, 리팩터링 필수
private const val TAG = "PhotoListFragment_debuk"
class PhotoListFragment<T> : BaseFragment<FragmentPhotoListBinding>(FragmentPhotoListBinding::bind, R.layout.fragment_photo_list) {
    lateinit var exAdapter: ExpandableRecyclerViewAdapter<T>
    private val viewModel: MainViewModel by activityViewModels()
    private val clothesViewModel: ClothesViewModel by activityViewModels()
    private val styleViewModel: StyleViewModel by activityViewModels()
    lateinit var largeCategories: List<String>
    var smallCategories = listOf<Pair<Int, Int>>()

    // TODO: 2022/02/10 toolbar는 host에서 관리로 변경 
    override fun onResume() {
        super.onResume()
        when(tag) {
            "clothes" -> {
                viewModel.setToolbarTitle("옷")
            }
            "style" -> {
                viewModel.setToolbarTitle("스타일")
            }
        }
        viewModel.setLeadingIcon(R.drawable.ic_add)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        exAdapter = ExpandableRecyclerViewAdapter(requireActivity())
        exAdapter.itemClickListener = object : ExpandableRecyclerViewAdapter.ItemClickListener {
            override fun onClick(position: Int, viewType: Int) {
                when(viewType) {
                    ExpandableRecyclerViewAdapter.HEADER -> {
                        val data = this@PhotoListFragment.exAdapter.data
                        var count = 0
                        val item = data[position]
                        // invisibleItems가 있을 때 펼침
                        if(!item.isInvisibleItemsNull() && item.invisibleItems?.size?:0 != 0) {
                            var index = position + 1
                            for(i in item.invisibleItems!!) {
                                data.add(index, PhotoItem(ExpandableRecyclerViewAdapter.CHILD, content = i))
                                index++
                            }
                            exAdapter.notifyItemRangeInserted(position + 1, index - position - 1)
                            item.clearInvisibleItem()
                        }
                        // invisibleItems가 없을 때 접음
                        else if(!item.isInvisibleItemsNull() && item.invisibleItems!!.size == 0) { // invisibleItems가 없을 때
                            while (data.size > position + 1 && data[position + 1].type == ExpandableRecyclerViewAdapter.CHILD) {
                                item.addInvisibleItem(data.removeAt(position + 1).content!!)
                                count++
                            }
                            exAdapter.notifyItemRangeRemoved(position + 1, count)
                        }
                    }
                    // TODO: 2022/01/30 data.content type에 따라 분기
                    ExpandableRecyclerViewAdapter.CHILD -> {
                        exAdapter.data[position].content?.let { item ->
                            val intent = when(item) {
                                is Clothes -> {
                                    Intent(requireActivity(), ClothesDetailActivity::class.java).putExtra("clothes", item)
                                }
                                is Style -> {
                                    Intent(requireActivity(), StyleMakingActivity::class.java).putExtra("style", item)
                                }
                                else -> return
                            }
                            startActivity(intent)
                        }
                    }
                }
            }
        }

        // 아이템 뷰타입이 child일 때만 longClickListener 작동
        exAdapter.itemLongClickListener = object : ExpandableRecyclerViewAdapter.ItemLongClickListener {
            override fun onLongClick(position: Int) {
                val item = exAdapter.data[position]
                if(item.type == ExpandableRecyclerViewAdapter.CHILD) {
                    showDeleteDialog(item)
                }
            }
        }

        binding.recyclerViewPhotoListF.apply {
            this.adapter = exAdapter
            layoutManager = GridLayoutManager(requireContext(), 3).also {

                it.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return if(this@PhotoListFragment.exAdapter.data[position].type == ExpandableRecyclerViewAdapter.HEADER) { // header면 전체 칸 차지
                            3
                        } else {
                            1
                        }
                    }
                }
            }
        }

        // TODO: 2022/01/26 통신 상태에 따라 로딩 뷰 제공
        viewModel.clothesResponseStatus.observe(requireActivity()) {

        }

        // TODO: 2022/02/10 tag 분기.. 
        if(tag == "clothes") {
            setDropDown()
        }else if(tag=="f0"){
            setDropDown()
        }
        else {
            binding.textInputLayoutPhotoListFCategory.visibility = View.GONE
            binding.textInputLayoutPhotoListFSmallCategory.visibility = View.GONE
        }

        setObserverLiveData()
    }

    private fun setObserverLiveData() {
        when(tag) {
            // TODO: 2022/02/10 분기 처리 아름답게
            "clothes" -> {
                clothesViewModel.clothesListLiveData.observe(requireActivity()) {
                    Log.d("aaaaaaaa", "setObserverLiveData: "+clothesViewModel.clothesListLiveData.value)
                    exAdapter.data = it as ArrayList<PhotoItem<T>>
                    exAdapter.notifyDataSetChanged()
                }
            }
            "style" -> {
                styleViewModel.styleListLiveData.observe(requireActivity()) {
                    exAdapter.data = styleViewModel.makePhotoItemList(it.toMutableList()) as ArrayList<PhotoItem<T>>
                    exAdapter.notifyDataSetChanged()
                }
            }
            "f0"->{
                clothesViewModel.clothesListLiveData.observe(requireActivity()) {
                    Log.d("aaaaaaaa", "setObserverLiveData: "+clothesViewModel.clothesListLiveData.value)
                    exAdapter.data = it as ArrayList<PhotoItem<T>>
                    exAdapter.notifyDataSetChanged()
                }
            }
            "f1"->{
                styleViewModel.styleListLiveData.observe(requireActivity()) {
                    exAdapter.data = styleViewModel.makePhotoItemList(it.toMutableList()) as ArrayList<PhotoItem<T>>
                    exAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun setDropDown() {
        largeCategories = getLargeCategoryString()
        // 초기화 시 대분류 전체, 소분류 전체로 세팅
        binding.autoCompleteTextViewPhotoListFCategory.setText(resources.getText(R.string.total))
        binding.autoCompleteTextViewPhotoListFSmallCategory.setText(resources.getText(R.string.total))

        val largeCategoryAdapter = ArrayAdapter(requireActivity(), R.layout.list_category_item, largeCategories)
        binding.autoCompleteTextViewPhotoListFCategory.setAdapter(largeCategoryAdapter)

        // 대분류가 바뀜에 따라 소분류 바꿈
        clothesViewModel.largeCategory.observe(this) {
            smallCategories = clothesViewModel.getSmallCategoriesByLargeCategory(it)
            val smallCategoryAdapter = ArrayAdapter(requireActivity(), R.layout.list_category_item, getSmallCategoryStringByLargeCategory(it))
            binding.autoCompleteTextViewPhotoListFSmallCategory.setAdapter(smallCategoryAdapter)
        }

        // 대분류 edittext observe -> viewModel에 대분류 카테고리 변경, 대분류 카테고리로 clothesUpdate
        // 대분류가 전체라면 소분류도 전체로 변경.
        binding.autoCompleteTextViewPhotoListFCategory.addTextChangedListener {
            val largeCategory = clothesViewModel.largeCategorySet.find { pair ->
                it.toString() == resources.getString(pair.second) && pair.first in 0..9
            }

            largeCategory?.let {
                binding.autoCompleteTextViewPhotoListFSmallCategory.setText(resources.getText(R.string.total))
                clothesViewModel.setLargeCategory(it.first)
                clothesViewModel.updateClothesByCategory(it.first)
            }
        }

        // 소분류 edittext observe -> viewModel 소분류에 맞는 옷 필터링
        binding.autoCompleteTextViewPhotoListFSmallCategory.addTextChangedListener {
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

    fun showDeleteDialog(item: PhotoItem<*>) {
        val content: String = when(item.content) {
            is Clothes -> "이 옷을 삭제하시겠습니까?"
            // TODO: 2022/01/26 룩 type으로 변경
            else -> "이 룩을 삭제하시겠습니까?"
        }
        DeleteDialog(requireActivity())
            .setContent(content)
            .setOnPositiveClickListener(object : View.OnClickListener {
                override fun onClick(p0: View?) {
                    viewModel.deleteClothesById((item.content as Clothes).clothesId)
                }
            }).build().show()
    }
}