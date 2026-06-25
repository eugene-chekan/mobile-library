package com.course.app.data

import androidx.room.TypeConverter

class Converters {

    // ItemType  →  "BOOK" / "MAGAZINE"  (stored in SQLite as TEXT)
    @TypeConverter
    fun fromItemType(type: ItemType): String = type.name

    // "BOOK" / "MAGAZINE"  →  ItemType
    @TypeConverter
    fun toItemType(value: String): ItemType = ItemType.valueOf(value)
}
