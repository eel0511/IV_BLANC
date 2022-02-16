package com.strait.ivblanc.src.process

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.get
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import com.google.android.material.chip.ChipGroup
import com.strait.ivblanc.R
import com.strait.ivblanc.config.BaseFragment
import com.strait.ivblanc.data.model.viewmodel.ProcessViewModel
import com.strait.ivblanc.databinding.FragmentSeasonBinding
import com.strait.ivblanc.util.SeasonCode

class SeasonFragment :
    BaseFragment<FragmentSeasonBinding>(FragmentSeasonBinding::bind, R.layout.fragment_season) {
    private val processViewModel: ProcessViewModel by activityViewModels()
    private lateinit var seasons: List<String>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        seasons = getSeasons()
        val seasonAdapter = ArrayAdapter(requireActivity(), R.layout.list_dropdown, seasons)
        binding.autoCompleteSeasonF.setAdapter(seasonAdapter)
        binding.autoCompleteSeasonF.addTextChangedListener {
            processViewModel.setSeason(getSeasonCode(it.toString()))
        }
        binding.buttonSeasonFBefore.setOnClickListener {
            (requireActivity() as ProcessActivity).goBefore()
        }
        binding.buttonSeasonFUpload.setOnClickListener {
            processViewModel.addClothes()
        }
        initchip()
        initselect()
        binding.spring.isSelected = true
        processViewModel.setSeason(getSeasonCode("봄"))
        binding.spring.setChipBackgroundColorResource(R.color.ivblanc_newpink)
    }

    private fun initchip() {
        binding.spring.setChipBackgroundColorResource(R.color.gray_100)
        binding.summer.setChipBackgroundColorResource(R.color.gray_100)
        binding.autumn.setChipBackgroundColorResource(R.color.gray_100)
        binding.winter.setChipBackgroundColorResource(R.color.gray_100)
        binding.spring.setOnClickListener {
            initselect()
            it.isSelected = true
            processViewModel.setSeason(getSeasonCode("봄"))
            binding.spring.setChipBackgroundColorResource(R.color.ivblanc_newpink)
            Log.d("bsbsbs", "initchip: "+processViewModel.season.value!!)
        }
        binding.summer.setOnClickListener {
            initselect()
            it.isSelected = true
            processViewModel.setSeason(getSeasonCode("여름"))
            binding.summer.setChipBackgroundColorResource(R.color.ivblanc_newpink)
            Log.d("bsbsbs", "initchip: "+processViewModel.season.value!!)
        }
        binding.autumn.setOnClickListener {
            initselect()
            it.isSelected = true
            processViewModel.setSeason(getSeasonCode("가을"))
            binding.autumn.setChipBackgroundColorResource(R.color.ivblanc_newpink)
            Log.d("bsbsbs", "initchip: "+processViewModel.season.value!!)
        }
        binding.winter.setOnClickListener {
            initselect()
            it.isSelected = true
            processViewModel.setSeason(getSeasonCode("겨울"))
            binding.winter.setChipBackgroundColorResource(R.color.ivblanc_newpink)
            Log.d("bsbsbs", "initchip: "+processViewModel.season.value!!)
        }
    }

    private fun initselect() {
        binding.spring.isSelected = false
        binding.summer.isSelected = false
        binding.autumn.isSelected = false
        binding.winter.isSelected = false
        binding.spring.setChipBackgroundColorResource(R.color.gray_100)
        binding.summer.setChipBackgroundColorResource(R.color.gray_100)
        binding.autumn.setChipBackgroundColorResource(R.color.gray_100)
        binding.winter.setChipBackgroundColorResource(R.color.gray_100)
    }

    // SeasonCode에서 찾지 못한다면 0 리턴
    // data가 SeasonCode에서 시작하므로 예외 발생은 희박
    private fun getSeasonCode(seasonString: String): Int {
        val result = SeasonCode.LIST.find {
            resources.getString(it.second) == seasonString
        }
        return result?.first ?: 0
    }

    private fun getSeasons(): List<String> {
        val result = mutableListOf<String>()
        SeasonCode.LIST.forEach {
            result.add(resources.getString(it.second))
        }
        return result.toList()
    }
}