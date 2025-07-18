package com.example.yandexsummerschool.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.yandexsummerschool.data.dto.transactions.Category
import com.example.yandexsummerschool.data.local.room.entities.CategoryEntity

@Dao
interface CategoriesDao {
    @Query("select * from categoryentity")
    fun getAllCategories(): List<CategoryEntity>

    @Query("select * from categoryentity where id = :id")
    fun getCategoryById(id: Int): Category

    @Insert(
        entity = CategoryEntity::class,
        onConflict = OnConflictStrategy.REPLACE,
    )
    fun insertCategories(categoriesList: List<CategoryEntity>)
}
