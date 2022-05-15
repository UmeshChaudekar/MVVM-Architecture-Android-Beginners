package com.framework.mvvm.data.api

import com.framework.mvvm.data.model.User
import com.rx2androidnetworking.Rx2AndroidNetworking
import io.reactivex.Single

class ApiServiceImpl : ApiService {

    override fun getUsers(): Single<List<User>> {
        return Rx2AndroidNetworking.get("https://jsonplaceholder.typicode.com/posts")
            .build()
            .getObjectListSingle(User::class.java)
    }

}