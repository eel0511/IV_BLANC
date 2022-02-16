package com.strait.ivblanc.src.clothesDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.strait.ivblanc.R
import com.strait.ivblanc.databinding.FragmentAiResultBinding

class AiResultFragment(val url: String) : DialogFragment() {
    lateinit var binding: FragmentAiResultBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAiResultBinding.bind(inflater.inflate(R.layout.fragment_ai_result, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(requireActivity()).load(url).into(binding.imageViewAiResultF)
        isCancelable = true
    }

}