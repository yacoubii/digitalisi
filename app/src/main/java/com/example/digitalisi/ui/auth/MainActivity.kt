package com.example.digitalisi.ui.auth

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.digitalisi.ui.categories.CategoryActivity
import com.example.digitalisi.R
import com.example.digitalisi.repository.LoginRepository
import com.example.digitalisi.repository.UserIdRepository

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel : AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
    }

    //OnClick
    fun login(view: View) {
        val username : EditText = findViewById(R.id.username)
        val password : EditText = findViewById(R.id.password)
        val message : TextView = findViewById(R.id.message)
        val progressBar : ProgressBar = findViewById(R.id.progressBar)
        progressBar.visibility=View.VISIBLE
        message.setText("")

        val loginRepository = LoginRepository()
        val userIdRepository = UserIdRepository()
        val viewModelFactory = AuthViewModelFactory(loginRepository, userIdRepository)

        //Authenticate
        viewModel = ViewModelProvider(this, viewModelFactory).get(AuthViewModel::class.java)
        viewModel.login(username.text.toString(),password.text.toString(),false)
        viewModel.myResponse.observe(this, Observer { response ->
            if(response.isSuccessful){
                Log.d("Response",response.headers().values("Set-Cookie").toString())
                val Cookies : List<String> =  response.headers().values("Set-Cookie")
                val jsessionid = Cookies[1].split(";")[0]
                val token = Cookies[2].split(";")[0]
                val sharedPref = getSharedPreferences("userDetails",Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.apply {
                    putString("JSESSIONID", jsessionid)
                    putString("X-Bonita-API-Token", token)
                }.apply()

                //Getting user id
                val savedSession : String? = sharedPref.getString("JSESSIONID",null)
                val savedtoken : String? = sharedPref.getString("X-Bonita-API-Token",null)
                viewModel.getUserId(savedSession+";"+savedtoken)
                viewModel.userIdResponse.observe(this, Observer { response ->
                    if(response.isSuccessful){
                        progressBar.visibility=View.GONE
                        Log.d("ResponseUserId",response.body()!!.user_id)
                        val editor = sharedPref.edit()
                        editor.apply {
                            putString("USER_ID", "user_id="+response.body()!!.user_id)
                        }.apply()
                        val intent = Intent(this, CategoryActivity::class.java)
                        startActivity(intent)
                    }else{
                        progressBar.visibility=View.GONE
                        Log.d("Response",response.errorBody()!!.string())
                    }
                })
            }else{
                progressBar.visibility=View.GONE
                Toast.makeText(this,"LOGIN FAILED!",Toast.LENGTH_LONG).show()
                Log.d("Response",response.errorBody()!!.string())
            }
        })
    }
}