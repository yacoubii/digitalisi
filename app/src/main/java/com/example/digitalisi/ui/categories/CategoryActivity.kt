package com.example.digitalisi.ui.categories

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.digitalisi.R
import com.example.digitalisi.adapter.RecyclerAdapter
import com.example.digitalisi.repository.CategoryRepository

class CategoryActivity : AppCompatActivity() {

    private lateinit var viewModel : CategoryViewModel
    private val myAdapter by lazy { RecyclerAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        //recycler view settings
        setupRecyclerView()

        val categoryRepository = CategoryRepository()
        val viewModelFactory = CategoryViewModelFactory(categoryRepository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(CategoryViewModel::class.java)

        val sharedPref = getSharedPreferences("userDetails",Context.MODE_PRIVATE)
        val savedSession : String? = sharedPref.getString("JSESSIONID",null)
        val savedtoken : String? = sharedPref.getString("X-Bonita-API-Token",null)
        val savedUserId : String? = sharedPref.getString("USER_ID",null)
        val p = 0
        viewModel.getCategories(savedSession+";"+savedtoken,p,savedUserId+"")
        viewModel.categoryResponse.observe(this, Observer{response->
            if (response.isSuccessful){
                val categories = response.body()!!
                Log.d("Categories",categories[0].toString())
                myAdapter.setData(categories)
            }else{
                Toast.makeText(this,"CATEGORIES LIST FAILED",Toast.LENGTH_LONG).show()
                Log.d("Response",response.errorBody()!!.string())
            }

        })
    }

    private fun setupRecyclerView(){
        var layoutManager: RecyclerView.LayoutManager? = null
        layoutManager = LinearLayoutManager(this)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter=myAdapter
    }
}