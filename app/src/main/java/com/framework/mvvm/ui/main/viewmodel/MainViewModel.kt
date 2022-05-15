package com.framework.mvvm.ui.main.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.framework.mvvm.model.LoginTableModel
import com.framework.mvvm.repository.LoginRepository
import com.framework.mvvm.data.model.User
import com.framework.mvvm.data.repository.MainRepository
import com.framework.mvvm.utils.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    private val users = MutableLiveData<Resource<List<User>>>()
    private val compositeDisposable = CompositeDisposable()
    var userPostData: LiveData<List<User>>? = null
    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        users.postValue(Resource.loading(null))
        compositeDisposable.add(
            mainRepository.getUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ userList ->
                    users.postValue(Resource.success(userList))
                }, { throwable ->
                    users.postValue(Resource.error("Something Went Wrong", null))
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun getUsers(): LiveData<Resource<List<User>>> {
        return users
    }

    fun insertData(context: Context, user: User) {
        LoginRepository.insertPostData(context, user)
    }

    fun getUserPostDetails(context: Context) : LiveData<List<User>>? {
        userPostData = LoginRepository.getUserPostDetails(context)
        return userPostData
    }


}