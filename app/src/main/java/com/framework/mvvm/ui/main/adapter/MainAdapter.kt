package com.framework.mvvm.ui.main.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.framework.mvvm.R
import com.framework.mvvm.data.model.User
import kotlinx.android.synthetic.main.item_layout.view.*

class MainAdapter(
    private val users: ArrayList<User>,
    val btnlistener: BtnClickListener
) : RecyclerView.Adapter<MainAdapter.DataViewHolder>() {

    companion object {
        var mClickListener: BtnClickListener? = null
    }

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User) {
            itemView.textViewUserName.text = user.title
            itemView.textViewUserEmail.text = user.body
            itemView.ivFav.setOnClickListener {
                if (mClickListener != null) {
                    mClickListener?.onBtnClick(user)
                    Log.d("Adapter", "clickedd")
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_layout, parent,
                false
            )
        )

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {

        holder.bind(users[position])
        mClickListener = btnlistener
    }

    fun addData(list: List<User>) {
        users.addAll(list)
    }

    open interface BtnClickListener {
        fun onBtnClick(user: User)
    }
}