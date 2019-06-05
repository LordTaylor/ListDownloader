package com.lordtaylor.listdownloader.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(indices = arrayOf(Index(value = ["userid"], unique = true)))
data class Item(@PrimaryKey(autoGenerate = true) var _id: Long, @ColumnInfo(name = "userid") var id: String="", var name: String="", var description: String="", var icon: String="", var timestamp: String="") {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Item

        if (id != other.id) return false
        if (name != other.name) return false
        if (description != other.description) return false
        if (icon != other.icon) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + icon.hashCode()
        result = 31 * result + timestamp.hashCode()
        return result
    }
}

