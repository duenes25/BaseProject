package org.kp.consumer.android.baseproject.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "base_items")
data class BaseItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "subTitle") val subTitle: String
)
