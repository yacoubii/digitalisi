package com.example.digitalisi.ui.processes

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.digitalisi.R
import com.example.digitalisi.adapter.ProcessesRecyclerAdapter
import com.example.digitalisi.repository.ProcessRepository

class Processes : AppCompatActivity() {

    private lateinit var viewModel : ProcessViewModel
    private val myAdapter by lazy { ProcessesRecyclerAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_processes)
        val categoryId= intent.getStringExtra("categoryID").toString()

        setupRecyclerView()
        val processRepository = ProcessRepository()
        val viewModelFactory = ProcessViewModelFactory(processRepository)
        viewModel = ViewModelProvider(this,viewModelFactory).get(ProcessViewModel::class.java)

        val sharedPref = getSharedPreferences("userDetails", Context.MODE_PRIVATE)
        val savedSession : String? = sharedPref.getString("JSESSIONID",null)
        val savedtoken : String? = sharedPref.getString("X-Bonita-API-Token",null)
        val savedUserId : String? = sharedPref.getString("USER_ID",null)

        val p = 0
        val c = 100
        //val o = "version%20desc"
        val f1 = "activationState=ENABLED"
        val f2 = savedUserId.toString()
        val f3 = categoryId
        viewModel.getProcesses(savedSession+";"+savedtoken,p,c,f1,f2,f3)
        viewModel.processResponse.observe(this, Observer { response->
            if (response.isSuccessful){
                val processes = response.body()!!
                myAdapter.setData(processes)
            }else{
                Toast.makeText(this,"PROCESSES LIST FAILED",Toast.LENGTH_LONG).show()
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