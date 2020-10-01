package org.kp.consumer.android.baseproject.db

import androidx.lifecycle.LiveData
import androidx.room.*
import org.kp.consumer.android.baseproject.model.BaseItem


@Dao
interface BaseItemDao{
    @Query("SELECT * FROM base_items")
    fun getAll(): List<BaseItem>

    //This one is observable using LiveData
    @Query("SELECT * FROM base_items WHERE title LIKE :title")
    fun getByTitle(title: String): LiveData<List<BaseItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg baseItems: BaseItem)

    @Delete
    fun delete(baseItem: BaseItem)

    @Update
    fun updateBaseItem(vararg baseItems: BaseItem)

}