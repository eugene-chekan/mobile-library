package com.course.app.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.course.app.data.LibraryItemEntity
import com.course.app.data.LibraryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LibraryViewModel(private val repository: LibraryRepository) : ViewModel() {

    // viewMode is pure UI preference — not stored in Room
    private val _viewMode = MutableStateFlow(ViewMode.LIST)

    /**
     * combine() merges two Flows into one.
     * Every time either allItems or _viewMode emits a new value,
     * the lambda runs and produces a new LibraryUiState.
     *
     * stateIn() converts the cold combined Flow into a hot StateFlow
     * that Compose can collect. WhileSubscribed(5_000) keeps the
     * upstream Flow active for 5 s after the last collector disappears,
     * which is long enough to survive a screen rotation (~1 s).
     */
    val uiState: StateFlow<LibraryUiState> = combine(
        repository.allItems,
        _viewMode
    ) { items, viewMode ->
        LibraryUiState(
            items = items,
            isLoading = false,
            viewMode = viewMode
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = LibraryUiState()   // isLoading = true until first DB emission
    )

    fun toggleViewMode() {
        _viewMode.update { current ->
            if (current == ViewMode.LIST) ViewMode.GRID else ViewMode.LIST
        }
    }

    fun save(item: LibraryItemEntity) {
        viewModelScope.launch { repository.save(item) }
        // No manual state update needed — Room re-emits allItems automatically
    }

    fun delete(item: LibraryItemEntity) {
        viewModelScope.launch { repository.delete(item) }
    }

    suspend fun getById(id: Int): LibraryItemEntity? = repository.getById(id)
}
