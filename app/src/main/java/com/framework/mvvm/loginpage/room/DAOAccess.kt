package com.framework.mvvm.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.framework.mvvm.model.LoginTableModel
import com.framework.mvvm.data.model.User

@Dao
interface DAOAccess {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun InsertData(loginTableModel: LoginTableModel)

    @Query("SELECT * FROM Login WHERE Username =:username")
    fun getLoginDetails(username: String?) : LiveData<LoginTableModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun InsertPostData(userModel: User)

    @Query("SELECT * FROM PostData")
    fun getUserPostDetails() : LiveData<List<User>>
}