package com.strait.ivblanc.src.login

import android.app.StatusBarManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.strait.ivblanc.R
import com.strait.ivblanc.config.BaseFragment
import com.strait.ivblanc.databinding.FragmentJoinBinding

class JoinFragment : BaseFragment<FragmentJoinBinding>(FragmentJoinBinding::bind, R.layout.fragment_join) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val parent = super.onCreateView(inflater, container, savedInstanceState)
        val constraintLayout = parent!!.findViewById<ConstraintLayout>(R.id.constaraintLayout_joinF)

        var result = 0

        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")

        if (resourceId > 0) {

            result = resources.getDimensionPixelSize(resourceId)

        }
        constraintLayout.minHeight = requireActivity().windowManager.defaultDisplay.height - result

        return parent
    }
}