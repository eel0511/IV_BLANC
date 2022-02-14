package com.strait.ivblanc.ui

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.strait.ivblanc.R
import com.strait.ivblanc.adapter.BottomSheetClothesRVAdapter
import com.strait.ivblanc.adapter.ExpandableRecyclerViewAdapter
import com.strait.ivblanc.adapter.HorizontalRVAdapter
import com.strait.ivblanc.adapter.PhotoListRVAdapter
import com.strait.ivblanc.config.BaseFragment
import com.strait.ivblanc.data.model.dto.Clothes
import com.strait.ivblanc.data.model.dto.PhotoItem
import com.strait.ivblanc.data.model.dto.Style
import com.strait.ivblanc.data.model.viewmodel.ClothesViewModel
import com.strait.ivblanc.data.model.viewmodel.FriendViewModel
import com.strait.ivblanc.data.model.viewmodel.MainViewModel
import com.strait.ivblanc.data.model.viewmodel.StyleViewModel
import com.strait.ivblanc.databinding.FragmentPhotoListBinding
import com.strait.ivblanc.src.clothesDetail.ClothesDetailActivity
import com.strait.ivblanc.src.friend.FriendCloset
import com.strait.ivblanc.src.styleMaking.StyleMakingActivity
import com.strait.ivblanc.util.CategoryCode
import com.strait.ivblanc.util.Status

// TODO: 2022/02/04 generic 오용, 리팩터링 필수
private const val TAG = "PhotoListFragment_debuk"
class PhotoListFragment<T> : BaseFragment<FragmentPhotoListBinding>(FragmentPhotoListBinding::bind, R.layout.fragment_photo_list) {
    lateinit var horizontalRVAdapter: HorizontalRVAdapter<T>
    lateinit var photoListRVAdapter: PhotoListRVAdapter<T>
    private val viewModel: MainViewModel by activityViewModels()
    private val clothesViewModel: ClothesViewModel by activityViewModels()
    private val styleViewModel: StyleViewModel by activityViewModels()
    private val friendViewModel: FriendViewModel by activityViewModels()
    private var FriendEmail = ""
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
        friendViewModel.firendEmail.observe(requireActivity()){
            FriendEmail = it
        }

        horizontalRVAdapter = HorizontalRVAdapter()
        horizontalRVAdapter.itemClickListener = object: HorizontalRVAdapter.ItemClickListener {
            override fun onClick(position: Int) {
                val intent = when(val item = horizontalRVAdapter.data[position]) {
                    is Clothes -> Intent(requireActivity(), ClothesDetailActivity::class.java).putExtra("clothes", item)
                    is Style -> Intent(requireActivity(), StyleMakingActivity::class.java).putExtra("style", item)
                    else -> return
                }
                startActivity(intent)
            }
        }

        binding.recyclerViewPhotoListFHorizontal.apply {
            adapter = horizontalRVAdapter
            layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.HORIZONTAL, false)
            addItemDecoration(object: RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    super.getItemOffsets(outRect, view, parent, state)
                    outRect.right = 20
                }
            })
        }

        photoListRVAdapter = PhotoListRVAdapter()
        photoListRVAdapter.itemClickListener = object: PhotoListRVAdapter.ItemClickListener {
            override fun onClick(position: Int) {
                val intent = when(val item = horizontalRVAdapter.data[position]) {
                    is Clothes -> Intent(requireActivity(), ClothesDetailActivity::class.java).putExtra("clothes", item)
                    is Style -> Intent(requireActivity(), StyleMakingActivity::class.java).putExtra("style", item)
                    else -> return
                }
                startActivity(intent)
            }
        }
        photoListRVAdapter.itemLongClickListener = object : PhotoListRVAdapter.ItemLongClickListener {
            override fun onLongClick(position: Int) {
                val item = photoListRVAdapter.data[position] as Any
                showDeleteDialog(item)
            }
        }

        binding.recyclerViewPhotoListF.apply {
            adapter = photoListRVAdapter
            layoutManager = GridLayoutManager(requireContext(), 3)
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
                binding.fabMain.visibility=View.GONE
                clothesViewModel.clothesList.observe(this) {
                    photoListRVAdapter.setDatas(it as List<T>)
                }
                clothesViewModel.recentlyAddedClothesList.observe(viewLifecycleOwner) {
                    if(it.isNotEmpty()) {
                        binding.linearLayoutPhotoListFRecent.visibility = View.VISIBLE
                    } else {
                        binding.linearLayoutPhotoListFRecent.visibility = View.GONE
                    }
                    horizontalRVAdapter.setDatas(it as List<T>)
                }
            }
            "style" -> {
                binding.fabMain.visibility=View.GONE
                styleViewModel.styleListLiveData.observe(this) {
                    photoListRVAdapter.setDatas(it as List<T>)
                }
                styleViewModel.recentlyAddedStyleList.observe(this) {
                    if(it.isNotEmpty()) {
                        horizontalRVAdapter.setDatas(it as List<T>)
                    }
                }

                styleViewModel.styleDeleteResponseStatus.observe(requireActivity()) {
                    if(it.status == Status.SUCCESS) {
                        styleViewModel.getAllStyles()
                    }
                }
            }
            "f0"->{
                binding.fabMain.visibility=View.GONE
                clothesViewModel.clothesList.observe(requireActivity()) {
                    horizontalRVAdapter.setDatas(it as List<T>)
                }
            }
            "f1"->{
                binding.fabMain.visibility=View.VISIBLE
                binding.fabMain.setOnClickListener {
                    val intent = Intent(requireActivity(),StyleMakingActivity::class.java)
                    intent.putExtra("friendEmail", FriendEmail)
                    startActivity(intent)
                }

                styleViewModel.styleListLiveData.observe(requireActivity()) {
                    Log.d(TAG, "setObserverLiveData: "+it)
                    photoListRVAdapter.setDatas(it as List<T>)
                }

                styleViewModel.recentlyAddedStyleList.observe(requireActivity()) {
                    horizontalRVAdapter.setDatas(it as List<T>)
                }

                styleViewModel.styleDeleteResponseStatus.observe(requireActivity()) {
                    Log.d(TAG, "setObserverLiveData: "+it)
                    if(it.status == Status.SUCCESS) {
                        styleViewModel.getAllFriendStyles(FriendEmail)
                    }
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

    fun showDeleteDialog(item: Any) {
        val content: String = when(item) {
            is Clothes -> "이 옷을 삭제하시겠습니까?"
            // TODO: 2022/01/26 룩 type으로 변경
            else -> "이 룩을 삭제하시겠습니까?"
        }
        DeleteDialog(requireActivity())
            .setContent(content)
            .setOnPositiveClickListener {
                when (item) {
                    is Clothes -> viewModel.deleteClothesById((item).clothesId)
                    is Style -> styleViewModel.deleteStyleById((item).styleId)
                }
            }.build().show()
    }
}