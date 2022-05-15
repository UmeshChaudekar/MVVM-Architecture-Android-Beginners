package com.framework.mvvm.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.framework.mvvm.model.LoginTableModel
import com.framework.mvvm.room.LoginDatabase
import com.framework.mvvm.data.model.User
import io.reactivex.annotations.SchedulerSupport.IO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginRepository {

    companion object {

        var loginDatabase: LoginDatabase? = null

        var loginTableModel: LiveData<LoginTableModel>? = null
        var postTableModel: LiveData<List<User>>? = null

        fun initializeDB(context: Context) : LoginDatabase {
            return LoginDatabase.getDataseClient(context)
        }

        fun insertData(context: Context, username: String, password: String) {

            loginDatabase = initializeDB(context)

            CoroutineScope(Dispatchers.IO).launch {
                val loginDetails = LoginTableModel(username, password)
                loginDatabase!!.loginDao().InsertData(loginDetails)
            }

        }

        fun insertPostData(context: Context, user: User) {

            loginDatabase = initializeDB(context)

            CoroutineScope(Dispatchers.IO).launch {
//                val userPost = User(user.id, user.userId, user.title, user.body)
                loginDatabase!!.loginDao().InsertPostData(user)
            }

        }

        fun getLoginDetails(context: Context, username: String) : LiveData<LoginTableModel>? {

            loginDatabase = initializeDB(context)

            loginTableModel = loginDatabase!!.loginDao().getLoginDetails(username)

            return loginTableModel
        }


        fun getUserPostDetails(context: Context) : LiveData<List<User>>? {

            loginDatabase = initializeDB(context)

            postTableModel = loginDatabase!!.loginDao().getUserPostDetails()

            return postTableModel
        }

    }
}