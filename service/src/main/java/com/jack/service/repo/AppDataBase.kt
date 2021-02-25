package com.jack.service.repo

import android.content.Context
import androidx.annotation.Keep
import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * @description:数据库基础类
 * @author:  JacKMoChen
 * @email:   chenjifangvip@163.com
 * @time :   2021/1/30 11:01
 */
@Database(entities = [UserInfo::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    abstract val userDao: UserDao

    companion object {
        @Volatile
        private var instance: AppDataBase? = null
        private const val APP_DB_NAME = "app_db"

        @Synchronized
        fun getInstance(context: Context): AppDataBase {
            return instance ?: Room.databaseBuilder(context, AppDataBase::class.java, APP_DB_NAME)
                .build().also {
                    instance = it
                }
        }
    }
}

//定义数据entity
@Entity(tableName = "tb_user_info")
data class UserInfo(
    @PrimaryKey
    val id: Int,
    val course_permission: Boolean,
    val token: String?,
    @Embedded
    val user: User?
) {
    @Keep
    data class User(
        @ColumnInfo(name = "user_id")
        val id: Int, val logo_url: String?,
        val reg_time: String?,
        val username: String?
    )
}

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: UserInfo)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateUser(user: UserInfo)

    @Delete
    fun deleteUser(user: UserInfo)

    @Query("select * from tb_user_info where id=:id")
    fun queryLiveUser(id: Int = 0): LiveData<UserInfo>

    @Query("select * from tb_user_info where id=:id")
    fun queryUser(id: Int = 0): UserInfo?
}