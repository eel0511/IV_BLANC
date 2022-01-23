package com.strait.ivblanc.data.model.dto

class PhotoItem<T>(val type: Int, var text:String? = null, var content: T) {
    private var invisibleItems: ArrayList<T>? = null
    fun addInvisibleItem(invisibleItem: T) = invisibleItems?.add(invisibleItem)
    fun clearInvisibleItem() = invisibleItems?.clear()
    fun initInvisibleItems() {
        invisibleItems = arrayListOf()
    }
    fun setNullInvisibleItems() {
        invisibleItems = null
    }
}