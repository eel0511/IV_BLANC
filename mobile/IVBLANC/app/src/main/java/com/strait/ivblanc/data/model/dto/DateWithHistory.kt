package com.strait.ivblanc.data.model.dto

import org.joda.time.DateTime

// TODO: 2022/02/07 history type 변경 필요 
data class DateWithHistory(val date: DateTime?, var history: Any? = null)
