package com.framework.mvvm.data.api

import com.framework.mvvm.data.model.User
import io.reactivex.Single

interface ApiService {

    fun getUsers(): Single<List<User>>

}