package com.course.app.data

import kotlinx.coroutines.flow.Flow

class LibraryRepository(private val dao: LibraryDao) {

    // The ViewModel observes this directly.
    // Room re-emits a fresh list every time the table changes.
    val allItems: Flow<List<LibraryItemEntity>> = dao.getAllItems()

    suspend fun getById(id: Int): LibraryItemEntity? = dao.getItemById(id)

    // Single entry point for both create and edit.
    // id == 0 means the item is new (Room will assign a real ID on insert).
    // id  > 0 means the item already exists and should be overwritten.
    suspend fun save(item: LibraryItemEntity) {
        if (item.id == 0) dao.insert(item) else dao.update(item)
    }

    suspend fun delete(item: LibraryItemEntity) = dao.delete(item)
}
