package com.gpluslf.remindme.search.model

import androidx.compose.runtime.Immutable

@Immutable
data class SearchBarState(
    val query: String = "",
)