package com.gpluslf.remindme.core.domain

data class Achievement(

    val title: String,

    val userId: Long,

    val body: String,

    val isCompleted: Boolean,

    val percentage: Int
)
