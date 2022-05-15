package com.framework.mvvm.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.framework.mvvm.viewmodel.LoginViewModel
import com.framework.mvvm.R
import com.framework.mvvm.ui.main.view.MainActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.login_page.*

class LoginActivity : AppCompatActivity() {

    lateinit var loginViewModel: LoginViewModel

    lateinit var context: Context

//     lateinit var strUsername: String
//     lateinit var strPassword: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_page)

        context = this@LoginActivity

        loginViewModel = ViewModelProviders.of(
            this
        ).get(LoginViewModel::class.java)

        textChangeListner()
        clickListner()

//        btnReadLogin.setOnClickListener {
//
//            strUsername = txtUsername.text.toString().trim()
//
//            loginViewModel.getLoginDetails(context, strUsername)!!.observe(this, Observer {
//                if (it == null) {
//                    lblReadResponse.text = "Data Not Found"
//                    lblUseraname.text = "- - -"
//                    lblPassword.text = "- - -"
//                }
//                else {
//                    lblUseraname.text = it.Username
//                    lblPassword.text = it.Password
//
//                    lblReadResponse.text = "Data Found Successfully"
//                }
//            })
//        }
    }

    private fun clickListner() {
        btnAddLogin.setOnClickListener {

            val strUsername = txtUsername.text.toString().trim()
            val strPassword = txtPassword.text.toString().trim()

            if (strUsername.isEmpty() || !isValidString(strUsername)) {
                txtUsername.error = context.getString(R.string.valid_email)
            }
            else if (strPassword.isEmpty() || strPassword.length < 8) {
                txtPassword.error = context.getString(R.string.password_error)
            }
            else {
                loginViewModel.insertData(context, strUsername, strPassword)
//                lblInsertResponse.text = "Inserted Successfully"

                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun textChangeListner() {
        txtUsername.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                txtUsername.error = null
            }
        })

        txtPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                txtPassword.error = null
            }
        })

    }

    fun isValidString(str: String): Boolean{
        return android.util.Patterns.EMAIL_ADDRESS.matcher(str).matches()
    }
}