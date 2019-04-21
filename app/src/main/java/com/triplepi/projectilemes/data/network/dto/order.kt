package com.triplepi.projectilemes.data.network.dto

import java.util.*

data class OrderIM(
    val id: Long,
    val quantity: Int,
    val number: String,
    val start: Date,
    val deadline: Date,
    val processId: Long,
    val priority: Int,
    val batches: List<BatchIM>
)