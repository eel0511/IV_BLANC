package com.strait.ivblanc.data.model.viewmodel

import android.graphics.drawable.AdaptiveIconDrawable
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.strait.ivblanc.util.ClickListener

class PhotoSelectViewModel: ViewModel() {
    private val _toolbarTitle = MutableLiveData<String>()
    val toolbarTitle: LiveData<String> get() = _toolbarTitle
    private val _leadingIconDrawable = MutableLiveData<Int>()
    val leadingIconDrawable: LiveData<Int> get() = _leadingIconDrawable

    private val _trailingIconDrawable = MutableLiveData<Int>()
    val trailingIconDrawable: LiveData<Int> get() = _trailingIconDrawable

    fun setToolbarTitle(title: String) {
        _toolbarTitle.postValue(title)
    }

    fun setLeadingIcon(resId: Int) {
        _leadingIconDrawable.postValue(resId)
    }

    fun setTrailingIcon(resId: Int) {
        _trailingIconDrawable.postValue(resId)
    }
}