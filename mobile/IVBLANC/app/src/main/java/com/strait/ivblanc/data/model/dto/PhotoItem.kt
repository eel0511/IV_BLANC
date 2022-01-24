package com.strait.ivblanc.data.model.dto

class PhotoItem<T>(val type: Int, var text:String? = null, var content: T? = null) {
    var invisibleItems: ArrayList<T>? = null
    var inVisibleItemsSize = getInvisibleItemsSize()

    fun addInvisibleItem(invisibleItem: T) = invisibleItems?.add(invisibleItem)
    fun clearInvisibleItem() = invisibleItems?.clear()
    fun initInvisibleItems() {
        invisibleItems = arrayListOf()
    }
    fun setNullInvisibleItems() {
        invisibleItems = null
    }
    fun isInvisibleItemsNull():Boolean {
        if(invisibleItems == null) return true
        return false
    }
    private fun getInvisibleItemsSize() = invisibleItems?.size?: 0
}