package com.strait.ivblanc.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.strait.ivblanc.R
import com.strait.ivblanc.adapter.ExpandableRecyclerViewAdapter
import com.strait.ivblanc.config.BaseFragment
import com.strait.ivblanc.data.model.dto.Clothes
import com.strait.ivblanc.data.model.dto.PhotoItem
import com.strait.ivblanc.databinding.FragmentPhotoListBinding

class PhotoListFragment : BaseFragment<FragmentPhotoListBinding>(FragmentPhotoListBinding::bind, R.layout.fragment_photo_list) {
    lateinit var exAdapter: ExpandableRecyclerViewAdapter<Clothes>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        exAdapter = ExpandableRecyclerViewAdapter<Clothes>(requireActivity()).apply {
            // TODO: 2022/01/26 기본 테스트 데이터
            val header = PhotoItem<Clothes>(ExpandableRecyclerViewAdapter.HEADER, "최근").apply {
                initInvisibleItems()
            }
            data.add(header)
            data.add(PhotoItem(ExpandableRecyclerViewAdapter.CHILD, content = Clothes(0, 0, "black", 0, "yy", 0, 0, 0, 0, 0, 0, "dd", "url", 0)))
            data.add(PhotoItem(ExpandableRecyclerViewAdapter.CHILD, content = Clothes(0, 0, "black", 0, "yy", 0, 0, 0, 0, 0, 0, "dd", "url", 0)))
            data.add(PhotoItem(ExpandableRecyclerViewAdapter.CHILD, content = Clothes(0, 0, "black", 0, "yy", 0, 0, 0, 0, 0, 0, "dd", "url", 0)))
            data.add(PhotoItem(ExpandableRecyclerViewAdapter.CHILD, content = Clothes(0, 0, "black", 0, "yy", 0, 0, 0, 0, 0, 0, "dd", "url", 0)))
            data.add(PhotoItem(ExpandableRecyclerViewAdapter.CHILD, content = Clothes(0, 0, "black", 0, "yy", 0, 0, 0, 0, 0, 0, "dd", "url", 0)))
            data.add(PhotoItem(ExpandableRecyclerViewAdapter.HEADER, "즐겨찾기"))
            data.add(PhotoItem(ExpandableRecyclerViewAdapter.CHILD, content = Clothes(0, 0, "black", 0, "yy", 0, 0, 0, 0, 0, 0, "dd", "url", 0)))
            data.add(PhotoItem(ExpandableRecyclerViewAdapter.CHILD, content = Clothes(0, 0, "black", 0, "yy", 0, 0, 0, 0, 0, 0, "dd", "url", 0)))
            data.add(PhotoItem(ExpandableRecyclerViewAdapter.CHILD, content = Clothes(0, 0, "black", 0, "yy", 0, 0, 0, 0, 0, 0, "dd", "url", 0)))
            data.add(PhotoItem(ExpandableRecyclerViewAdapter.CHILD, content = Clothes(0, 0, "black", 0, "yy", 0, 0, 0, 0, 0, 0, "dd", "url", 0)))
            data.add(PhotoItem(ExpandableRecyclerViewAdapter.CHILD, content = Clothes(0, 0, "black", 0, "yy", 0, 0, 0, 0, 0, 0, "dd", "url", 0)))

            itemLongClickListener = object : ExpandableRecyclerViewAdapter.ItemLongClickListener {
                override fun onLongClick(position: Int) {
                    Toast.makeText(requireActivity(), "longclick", Toast.LENGTH_SHORT).show()
                }
            }
        }

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
                    // TODO: 2022/01/24 상세 뷰로 이동
                    ExpandableRecyclerViewAdapter.CHILD -> {

                    }
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
    }
}