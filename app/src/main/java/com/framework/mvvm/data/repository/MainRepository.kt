package com.framework.mvvm.data.repository

import android.content.Context
import com.framework.mvvm.repository.LoginRepository
import com.framework.mvvm.data.api.ApiHelper
import com.framework.mvvm.data.model.User
import io.reactivex.Single

class MainRepository(private val apiHelper: ApiHelper) {

    fun getUsers(): Single<List<User>> {
        return apiHelper.getUsers()
    }

}