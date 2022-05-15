package com.framework.mvvm.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "PostData")
data class User(
    @SerializedName("id")
    @ColumnInfo(name = "idU")
    val idU: Int = 0,
    @SerializedName("userId")
    @ColumnInfo(name = "userId")
    val userId: Int = 0,
    @SerializedName("title")
    @ColumnInfo(name = "title")
    val title: String = "",
    @SerializedName("body")
    @ColumnInfo(name = "body")
    val body: String = ""
){

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var Id: Int? = null

}