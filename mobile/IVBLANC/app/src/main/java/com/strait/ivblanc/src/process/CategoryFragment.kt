package com.strait.ivblanc.src.process

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import com.strait.ivblanc.R
import com.strait.ivblanc.config.BaseFragment
import com.strait.ivblanc.data.model.viewmodel.ProcessViewModel
import com.strait.ivblanc.databinding.FragmentCategoryBinding

class CategoryFragment : BaseFragment<FragmentCategoryBinding>(FragmentCategoryBinding::bind, R.layout.fragment_category) {
    private val processViewModel: ProcessViewModel by activityViewModels()
    lateinit var largeCategories: List<String>
    var smallCategories = listOf<Pair<Int, Int>>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        largeCategories = getLargeCategoryString()
        val largeCategoryAdapter = ArrayAdapter(requireActivity(), R.layout.list_category_item, largeCategories)
        binding.autoCompleteCategoryFLarge.setAdapter(largeCategoryAdapter)

        // 대분류가 바뀜에 따라 소분류 바꿈
        processViewModel.largeCategory.observe(requireActivity()) {
            smallCategories = processViewModel.getSmallCategoriesByLargeCategory(it)
            val smallCategoryAdapter = ArrayAdapter(requireActivity(), R.layout.list_category_item, getSmallCategoryStringByLargeCategory(it))
            binding.autoCompleteCategoryFSmall.setAdapter(smallCategoryAdapter)
        }

        // 대분류 edittext observe -> viewModel에 대분류 카테고리 변경
        binding.autoCompleteCategoryFLarge.addTextChangedListener {
            val largeCategory = processViewModel.largeCategorySet.find { pair ->
                it.toString() == resources.getString(pair.second) && pair.first in 0..9
            }

            largeCategory?.let {
                processViewModel.setLargeCategory(it.first)
            }

        }

        // 소분류 edittext observe -> viewModel 소분류 카테고리 변경
        binding.autoCompleteCategoryFSmall.addTextChangedListener {
            val smallCategory = smallCategories.find {
                 resources.getString(it.second) == it.toString()
            }

            smallCategory?.let {
                processViewModel.setSmallCategory(it.first)
            }
        }

        // 사이즈 edittext observe -> viewModel 사이즈 변경
        binding.editTextCategoryFSize.addTextChangedListener {
            processViewModel.setSize(it.toString().toInt())
        }

        // 다음 버튼 화면 이동
        // TODO: 2022/02/04 ProcessActivity에 의존성이 걸린다. 리팩터링 필요 
        binding.buttonCategoryFNext.setOnClickListener {
            (requireActivity() as ProcessActivity).goNext()
        }
    }

    private fun getLargeCategoryString(): List<String> {
        val result = mutableListOf<String>()
        processViewModel.largeCategorySet.forEach {
            result.add(resources.getString(it.second))
        }
        return result.toList()
    }

    private fun getSmallCategoryStringByLargeCategory(largeCategory: Int): List<String> {
        val result = mutableListOf<String>()
        processViewModel.getSmallCategoriesByLargeCategory(largeCategory).forEach {
            result.add(resources.getString(it.second))
        }
        return result.toList()
    }


}