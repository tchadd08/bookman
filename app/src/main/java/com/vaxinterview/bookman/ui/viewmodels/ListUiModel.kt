package com.vaxinterview.bookman.ui.viewmodels

data class ListUiModel (
    val id: Long,
    val titleDisplay: String,
    val authorDisplay: String,
    val available: Boolean,
    val bookmarked: Boolean
)