package com.vaxinterview.bookman.ui.viewmodels

data class DetailUiModel (
    val id: Long,
    val titleDisplay: String,
    val authorDisplay: String,
    val statusDisplay: String,
    val available: Boolean,
    val costDisplay: String,
    val lastUpdated: String,
    val bookmarked: Boolean,
    val checkDisplay: String,
    val dueBack: String
)

