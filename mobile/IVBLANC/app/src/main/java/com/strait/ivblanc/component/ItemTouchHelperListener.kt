package com.strait.ivblanc.component

interface ItemTouchHelperListener {
    fun onItemMove(startPosition: Int, endPosition: Int): Boolean
}