package com.course.app.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * One row in the "library_items" table.
 *
 * A single table stores both Books and Magazines.
 * Fields that do not apply to a given type are stored as null.
 *
 *   Book     → author, isbn, yearPublished are filled; publisher/issueNumber/issueYear are null
 *   Magazine → publisher, issueNumber, issueYear are filled; author/isbn/yearPublished are null
 *   isRead applies to both types.
 */
@Entity(tableName = "library_items")
data class LibraryItemEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,           // 0 = "let Room assign the real ID on insert"

    // ── Shared fields ──────────────────────────────────────────────────────────
    val type: ItemType,        // discriminator: BOOK or MAGAZINE
    val title: String,

    // ── Book-only fields ───────────────────────────────────────────────────────
    val author: String? = null,
    val isbn: String? = null,

    @ColumnInfo(name = "year_published")
    val yearPublished: Int? = null,

    @ColumnInfo(name = "is_read")
    val isRead: Boolean = false,

    // ── Magazine-only fields ───────────────────────────────────────────────────
    val publisher: String? = null,

    @ColumnInfo(name = "issue_number")
    val issueNumber: Int? = null,

    @ColumnInfo(name = "issue_year")
    val issueYear: Int? = null
)
