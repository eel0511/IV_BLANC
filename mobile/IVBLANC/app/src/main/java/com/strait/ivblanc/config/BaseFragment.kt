package com.strait.ivblanc.config

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<B : ViewBinding>(private val bind: (View) -> B, @LayoutRes layoutResId: Int) : Fragment(layoutResId) {
    private var _binding: B? = null

    protected val binding get() = _binding!!

    // onCreateView 오버라이딩 제거
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bind(super.onCreateView(inflater, container, savedInstanceState)!!)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    fun toast(msg: String, duration: Int) {
        Toast.makeText(activity, msg, duration).show()
    }
}