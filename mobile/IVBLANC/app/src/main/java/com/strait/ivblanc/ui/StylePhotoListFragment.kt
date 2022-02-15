package com.strait.ivblanc.ui

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.strait.ivblanc.R
import com.strait.ivblanc.adapter.HorizontalRVAdapter
import com.strait.ivblanc.adapter.PhotoListRVAdapter
import com.strait.ivblanc.config.BaseFragment
import com.strait.ivblanc.data.model.dto.Clothes
import com.strait.ivblanc.data.model.dto.Style
import com.strait.ivblanc.data.model.viewmodel.FriendViewModel
import com.strait.ivblanc.data.model.viewmodel.StyleViewModel
import com.strait.ivblanc.databinding.FragmentStylePhotoListBinding
import com.strait.ivblanc.src.clothesDetail.ClothesDetailActivity
import com.strait.ivblanc.src.styleMaking.StyleMakingActivity
import com.strait.ivblanc.util.Status

class StylePhotoListFragment : BaseFragment<FragmentStylePhotoListBinding>(FragmentStylePhotoListBinding::bind, R.layout.fragment_style_photo_list) {
    lateinit var horizontalRVAdapter: HorizontalRVAdapter<Style>
    lateinit var photoListRVAdapter: PhotoListRVAdapter<Style>
    private val styleViewModel: StyleViewModel by activityViewModels()
    private val friendViewModel: FriendViewModel by activityViewModels()
    private var FriendEmail = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        setRecyclerView()
        setObserver()
    }

    private fun setRecyclerView() {
        horizontalRVAdapter = HorizontalRVAdapter()
        horizontalRVAdapter.itemClickListener = object: HorizontalRVAdapter.ItemClickListener {
            override fun onClick(position: Int) {
                val intent = Intent(requireActivity(), StyleMakingActivity::class.java).putExtra("style", horizontalRVAdapter.data[position])
                startActivity(intent)
            }
        }

        binding.recyclerViewStylePhotoListFHorizontal.apply {
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
                val intent = Intent(requireActivity(), StyleMakingActivity::class.java).putExtra("style", photoListRVAdapter.data[position])
                startActivity(intent)
            }
        }
        photoListRVAdapter.itemLongClickListener = object : PhotoListRVAdapter.ItemLongClickListener {
            override fun onLongClick(position: Int) {
                val item = photoListRVAdapter.data[position]
                showDeleteDialog(item)
            }
        }

        binding.recyclerViewStylePhotoListF.apply {
            adapter = photoListRVAdapter
            layoutManager = GridLayoutManager(requireContext(), 3)
        }
    }

    private fun setObserver() {
        styleViewModel.styleListLiveData.observe(viewLifecycleOwner) {
            photoListRVAdapter.setDatas(it as List<Style>)
        }
        styleViewModel.recentlyAddedStyleList.observe(viewLifecycleOwner) {
            if(it.isNotEmpty()) {
                horizontalRVAdapter.setDatas(it as List<Style>)
            }
        }
        styleViewModel.styleDeleteResponseStatus.observe(viewLifecycleOwner) {
            if(it.status == Status.SUCCESS) {
                styleViewModel.getAllStyles()
            }
        }
    }

    fun showDeleteDialog(item: Style) {
        val content: String = "이 룩을 삭제하시겠습니까?"
        DeleteDialog(requireActivity())
            .setContent(content)
            .setOnPositiveClickListener {
                styleViewModel.deleteStyleById(item.styleId)
            }.build().show()
    }

}