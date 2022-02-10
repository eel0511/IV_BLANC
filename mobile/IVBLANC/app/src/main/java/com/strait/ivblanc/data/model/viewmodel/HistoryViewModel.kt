package com.strait.ivblanc.data.model.viewmodel

import androidx.lifecycle.ViewModel
import com.strait.ivblanc.data.repository.HistoryRepository

class HistoryViewModel: ViewModel() {
    val historyRepository = HistoryRepository()


}