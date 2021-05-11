package com.example.digitalisi.ui.cases

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.digitalisi.R
import com.example.digitalisi.repository.CaseRepository
import com.example.digitalisi.ui.categories.CategoryActivity
import org.json.JSONObject

class Case : AppCompatActivity() {
    private lateinit var viewModel : CaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_case)

        val processId= intent.getStringExtra("processId").toString()
        val form= intent.getStringExtra("form").toString()

        val caseRepository = CaseRepository()
        val viewModelFactory = CaseViewModelFactory(caseRepository)
        viewModel = ViewModelProvider(this,viewModelFactory).get(CaseViewModel::class.java)

        val sharedPref = getSharedPreferences("userDetails", Context.MODE_PRIVATE)
        val savedSession : String? = sharedPref.getString("JSESSIONID",null)
        val savedtoken : String? = sharedPref.getString("X-Bonita-API-Token",null)

        viewModel.submitForm(savedSession+";"+savedtoken,""+savedtoken?.substring(19),processId,form)
        viewModel.caseResponse.observe(this, Observer { response->
            if (response.isSuccessful){
                val case = response.body()!!
                Toast.makeText(this,"CASE SUCCESS: "+case.caseId.toString(),Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this,"CASE SUBMISSION FAILED",Toast.LENGTH_LONG).show()
                Log.d("caseError",response.errorBody()!!.string())
                Log.d("caseErrorCode",response.code().toString())
                Log.d("caseErrorResponse",response.toString())
            }
        })

        val intent = Intent(this, CategoryActivity::class.java)
        this.startActivity(intent)

    }
}