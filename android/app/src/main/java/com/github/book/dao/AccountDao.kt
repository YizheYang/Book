package com.github.book.dao

import androidx.room.*
import com.github.book.entity.AccountBean

/**
 * description none
 * author ez_yang@qq.com
 * date 2021.11.27 下午 5:37
 * version 1.0
 * update none
 **/
@Dao
interface AccountDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAccount(accountBean: AccountBean)

    @Query("SELECT * FROM account WHERE account==(SELECT MAX(account) FROM ACCOUNT)")
    fun getLastAccount(): AccountBean?

    @Query("SELECT * FROM account WHERE account==:account")
    fun getAccount(account: String): AccountBean?

    @Query("SELECT * FROM account")
    fun getAllAccount(): List<AccountBean>?

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateAccount(accountBean: AccountBean)

    @Delete
    fun deleteAccount(accountBean: AccountBean)
}