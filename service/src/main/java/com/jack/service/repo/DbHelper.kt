package com.jack.service.repo

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object DbHelper {
    fun getLiveUserInfo(context: Context)= AppDataBase.getInstance(context).userDao.queryLiveUser()


    fun getUserInfo(context: Context)= AppDataBase.getInstance(context).userDao.queryUser()


    fun deleteUser(context: Context) {
        GlobalScope.launch(Dispatchers.IO) {
            getUserInfo(context)?.let { info ->
                AppDataBase.getInstance(context).userDao.deleteUser(info)
            }
        }
    }

    /**
     * 新增用户数据
     */
    fun insertUserInfo(context: Context,userInfo: UserInfo) {
        GlobalScope.launch(Dispatchers.IO) {
            AppDataBase.getInstance(context).userDao.insertUser(userInfo)
        }
    }

    fun updateUserInfo(context: Context,userInfo: UserInfo) {
        GlobalScope.launch(Dispatchers.IO) {
            AppDataBase.getInstance(context).userDao.updateUser(userInfo)
        }
    }

}