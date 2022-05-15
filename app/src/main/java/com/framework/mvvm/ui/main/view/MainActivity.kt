package com.framework.mvvm.ui.main.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.framework.mvvm.R
import com.framework.mvvm.data.api.ApiHelper
import com.framework.mvvm.data.api.ApiServiceImpl
import com.framework.mvvm.data.model.User
import com.framework.mvvm.ui.base.ViewModelFactory
import com.framework.mvvm.ui.main.adapter.MainAdapter
import com.framework.mvvm.ui.main.viewmodel.MainViewModel
import com.framework.mvvm.utils.Status
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: MainAdapter
    lateinit var context: Context
    lateinit var usersFavList: List<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context = this@MainActivity
        setupUI()
        setupViewModel()
        setupObserver()
        setClickListner()
    }

    private fun setClickListner() {
        btnPostList.setOnClickListener {
            val size: Int = adapter.itemCount
            if (size > 0) {
                for (i in 0 until size) {
                    adapter.notifyItemRemoved(0)
                }
                adapter.notifyItemRangeRemoved(0, size)

            }
            setupUI()
            setupObserver()
        }

        btnFavList.setOnClickListener{
            mainViewModel.getUserPostDetails(context)!!.observe(this@MainActivity, Observer {
                if (it == null) {
                    Log.d("Activity", "Data Not found ")
                } else {
                    Log.d("Activity", "Data Found Successfully  + $it")

                    val size: Int = adapter.itemCount
                    if (size > 0) {
                        for (i in 0 until size) {
                            adapter.notifyItemRemoved(0)
                        }
                        adapter.notifyItemRangeRemoved(0, size)

                    }

                    setupUI()
                    renderList(it)
                }
            })
        }
    }

    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(arrayListOf(), object : MainAdapter.BtnClickListener {
            override fun onBtnClick(user: User) {
                Log.d("Activity", "clickedd")
                //we need to insert this user into room db
                mainViewModel.insertData(context, user)
                Toast.makeText(this@MainActivity, context.getString(R.string.fav_added_success), Toast.LENGTH_SHORT).show()
//                mainViewModel.getUserPostDetails(context)!!.observe(this@MainActivity, Observer {
//                    if (it == null) {
//                        Log.d("Activity", "Data Not found ")
//                    } else {
//                        Log.d("Activity", "Data Found Successfully  + $it")
//                        renderList(listOf(it))
//                    }
//                })
            }
        })
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
    }

    private fun setupObserver() {
        mainViewModel.getUsers().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    it.data?.let { users -> renderList(users) }
                    recyclerView.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun renderList(users: List<User>) {
        adapter.addData(users)
        adapter.notifyDataSetChanged()
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(ApiServiceImpl()))
        ).get(MainViewModel::class.java)
    }
}
