package com.github.book.database

import androidx.room.*
import com.github.book.dao.AccountDao
import com.github.book.entity.AccountBean

/**
 * description none
 * author ez_yang@qq.com
 * date 2021.11.27 下午 6:25
 * version 1.0
 * update none
 **/
@Database(entities = [AccountBean::class], version = 1)
abstract class AccountDatabase : RoomDatabase() {
    abstract fun getAccountDao(): AccountDao
}