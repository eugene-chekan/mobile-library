package com.course.app.di

import android.content.Context
import com.course.app.data.LibraryDatabase
import com.course.app.data.LibraryRepository
import com.course.app.presentation.LibraryViewModelFactory

/**
 * Manual DI container. Owns and wires all app-level dependencies.
 * Lives in LibraryApplication so there is exactly one instance per process.
 *
 * Dependency graph:
 *   Context → LibraryDatabase → LibraryDao → LibraryRepository → LibraryViewModelFactory
 */
class AppContainer(context: Context) {
    private val database = LibraryDatabase.getInstance(context)
    private val repository = LibraryRepository(database.libraryDao())
    val viewModelFactory = LibraryViewModelFactory(repository)
}
