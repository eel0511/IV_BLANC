package com.strait.ivblanc.src.process

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import com.strait.ivblanc.R
import com.strait.ivblanc.config.BaseFragment
import com.strait.ivblanc.data.model.viewmodel.ProcessViewModel
import com.strait.ivblanc.databinding.FragmentSeasonBinding
import com.strait.ivblanc.util.SeasonCode

class SeasonFragment : BaseFragment<FragmentSeasonBinding>(FragmentSeasonBinding::bind, R.layout.fragment_season) {
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
        
    }

    // SeasonCode에서 찾지 못한다면 0 리턴
    // data가 SeasonCode에서 시작하므로 예외 발생은 희박
    private fun getSeasonCode(seasonString: String): Int {
        val result = SeasonCode.LIST.find {
            resources.getString(it.second) == seasonString
        }
        return result?.first?:0
    }

    private fun getSeasons(): List<String> {
        val result = mutableListOf<String>()
        SeasonCode.LIST.forEach {
            result.add(resources.getString(it.second))
        }
        return result.toList()
    }
}