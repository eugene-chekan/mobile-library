package com.course.app.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface LibraryDao {

    // ── Read ──────────────────────────────────────────────────────────────────

    /**
     * Returns all items sorted by title.
     * Flow: re-emits automatically whenever the table changes.
     * Not a suspend function — Flow is lazy and starts when collected.
     */
    @Query("SELECT * FROM library_items ORDER BY title ASC")
    fun getAllItems(): Flow<List<LibraryItemEntity>>

    /**
     * Returns a single item by ID, or null if not found.
     * suspend: one-shot read, runs on a background thread.
     */
    @Query("SELECT * FROM library_items WHERE id = :itemId")
    suspend fun getItemById(itemId: Int): LibraryItemEntity?

    // ── Write ─────────────────────────────────────────────────────────────────

    /**
     * Inserts a new item. Returns the auto-generated row ID.
     * REPLACE strategy: if an item with the same primary key exists, it is replaced.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: LibraryItemEntity): Long

    /**
     * Updates an existing item matched by its primary key (id).
     * The entire row is overwritten with the new values.
     */
    @Update
    suspend fun update(item: LibraryItemEntity)

    /**
     * Deletes the item whose primary key matches the given entity.
     */
    @Delete
    suspend fun delete(item: LibraryItemEntity)
}
