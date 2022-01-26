package com.strait.ivblanc.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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