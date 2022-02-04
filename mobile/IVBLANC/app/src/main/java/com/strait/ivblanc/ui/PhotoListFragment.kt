package com.strait.ivblanc.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.strait.ivblanc.R
import com.strait.ivblanc.adapter.ExpandableRecyclerViewAdapter
import com.strait.ivblanc.config.BaseFragment
import com.strait.ivblanc.data.model.dto.Clothes
import com.strait.ivblanc.data.model.dto.PhotoItem
import com.strait.ivblanc.data.model.viewmodel.MainViewModel
import com.strait.ivblanc.databinding.FragmentPhotoListBinding
import com.strait.ivblanc.src.clothesDetail.ClothesDetailActivity

private const val TAG = "PhotoListFragment_debuk"
class PhotoListFragment<T> : BaseFragment<FragmentPhotoListBinding>(FragmentPhotoListBinding::bind, R.layout.fragment_photo_list) {
    lateinit var exAdapter: ExpandableRecyclerViewAdapter<T>
    private val viewModel: MainViewModel by activityViewModels()

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
                                else -> null
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
        viewModel.clothesListLiveData.observe(requireActivity()) {
            exAdapter.data = it as ArrayList<PhotoItem<T>>
            exAdapter.notifyDataSetChanged()
        }

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
                    // TODO: 2022/01/26 옷 삭제 함수 invoke
                    viewModel.deleteClothesById((item.content as Clothes).clothesId)
                }
            }).build().show()
    }
}