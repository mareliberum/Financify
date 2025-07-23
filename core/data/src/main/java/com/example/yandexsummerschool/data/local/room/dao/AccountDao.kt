package com.example.yandexsummerschool.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.yandexsummerschool.data.local.room.entities.AccountEntity

@Dao
interface AccountDao {
    @Query("SELECT * FROM AccountEntity LIMIT 1")
    fun getAccount(): AccountEntity?

    @Insert(
        entity = AccountEntity::class,
        onConflict = OnConflictStrategy.REPLACE,
    )
    fun insertAccount(accountEntity: AccountEntity)
}
