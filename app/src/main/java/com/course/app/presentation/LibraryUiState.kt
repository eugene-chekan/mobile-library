package com.course.app.presentation

import com.course.app.data.LibraryItemEntity

data class LibraryUiState(
    val items: List<LibraryItemEntity> = emptyList(),
    val isLoading: Boolean = true,
    val viewMode: ViewMode = ViewMode.LIST
)
