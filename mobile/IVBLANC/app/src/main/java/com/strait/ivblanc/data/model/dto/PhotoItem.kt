package com.strait.ivblanc.data.model.dto

class PhotoItem<T>(val type: Int, var text:String? = null, var content: T) {
    private val invisibleItems = arrayListOf<T>()
    fun addInvisibleItem(invisibleItem: T) = invisibleItems.add(invisibleItem)
    fun clearInvisibleItem() = invisibleItems.clear()
}