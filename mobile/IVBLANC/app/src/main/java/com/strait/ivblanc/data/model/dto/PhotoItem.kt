package com.strait.ivblanc.data.model.dto

class PhotoItem<T>(val type: Int, var text:String? = null) {
    private val invisibleItems = arrayListOf<T>()
    fun addInvisibleItem(invisibleItem: T) = invisibleItems.add(invisibleItem)
    fun clearInvisibleItem() = invisibleItems.clear()
}