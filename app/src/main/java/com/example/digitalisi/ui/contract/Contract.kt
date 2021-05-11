package com.example.digitalisi.ui.contract

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.View.NOT_FOCUSABLE
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.forEach
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.digitalisi.ui.cases.Case
import com.example.digitalisi.R
import com.example.digitalisi.model.Constraint
import com.example.digitalisi.model.InputX
import com.example.digitalisi.repository.ContractRepository
import org.json.JSONObject
import java.util.*

class Contract : AppCompatActivity(){
    private lateinit var viewModel : ContractViewModel
    var formattedDateTime = ""
    var contractName = ""
    var processId =""
    lateinit var contractConstraints : List<Constraint>
    var constraints : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contract)
        processId= intent.getStringExtra("processId").toString()

        val contractRepository = ContractRepository()
        val viewModelFactory = ContractViewModelFactory(contractRepository)
        viewModel = ViewModelProvider(this,viewModelFactory).get(ContractViewModel::class.java)

        val sharedPref = getSharedPreferences("userDetails", Context.MODE_PRIVATE)
        val savedSession : String? = sharedPref.getString("JSESSIONID",null)
        val savedtoken : String? = sharedPref.getString("X-Bonita-API-Token",null)

        viewModel.getContract(savedSession+";"+savedtoken,processId)
        viewModel.contractResponse.observe(this, Observer { response->
            if(response.isSuccessful){
                val contract = response.body()!!
                Log.d("contractConstraints",contract.constraints.toString())
                contractConstraints=contract.constraints
                Log.d("contractInputs",contract.inputs[0].inputs.toString())
                Log.d("contractName",contract.inputs[0].name)
                contractName = contract.inputs[0].name
                //creating the form
                makeForm(contract.inputs[0].inputs)
            }else{
                Toast.makeText(this,"CONTRACT FAILED",Toast.LENGTH_LONG).show()
                Log.d("Response",response.errorBody()!!.string())
            }
        })
    }

    fun makeForm(inputs:List<InputX>){
        val linearLayout = findViewById<LinearLayout>(R.id.LinearLayout)
        //adding title and subtitle
        val textView = TextView(this)
        textView.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        )
        textView.text=contractName
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,28F)
        textView.setTextColor(Color.parseColor("#1DB954"))
        textView.setPadding(20,20,20,10)
        textView.setTypeface(null, Typeface.BOLD)
        linearLayout?.addView(textView)

        val desc = TextView(this)
        desc.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        )
        desc.text="Please fill out this form to start your process."
        desc.setTextSize(TypedValue.COMPLEX_UNIT_SP,16F)
        desc.setPadding(20,20,20,60)
        linearLayout?.addView(desc)

        //looping through each type, and making the form
        if(!inputs.isNullOrEmpty()){
            inputs.forEach {
                when(it.type){
                    "OFFSETDATETIME" -> {
                        val linearLayout = findViewById<LinearLayout>(R.id.LinearLayout)
                        val textView = TextView(this)
                        textView.layoutParams = LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                        )
                        textView.text=it.name+" (datetime)"
                        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,20F)
                        textView.setTextColor(Color.parseColor("#1DB954"))
                        textView.setPadding(20,20,20,20)
                        linearLayout?.addView(textView)

                        val editText = EditText(this)
                        editText.layoutParams = LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                        )
                        editText.hint= "Entrez "+it.name
                        editText.inputType = InputType.TYPE_CLASS_DATETIME
                        editText.setTag(it.name);
                        editText.setPadding(20,20,20,40)

                        //Datetime dialog
                        editText.setFocusable(NOT_FOCUSABLE)
                        editText.setOnClickListener {
                            val now = Calendar.getInstance()
                            val datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                                val monthFormatted : String
                                val monthReal=month+1
                                if(monthReal.toString().length!=2){
                                    monthFormatted="0"+monthReal.toString()
                                }else{
                                    monthFormatted=monthReal.toString()
                                }
                                val dayFormatted : String
                                if(dayOfMonth.toString().length!=2){
                                    dayFormatted="0"+dayOfMonth.toString()
                                }else{
                                    dayFormatted=dayOfMonth.toString()
                                }
                                val selectedDate= year.toString()+"-"+monthFormatted+"-"+dayFormatted
                                timePicker(selectedDate,editText)
                            },
                            now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH))
                            datePicker.show()
                        }
                        linearLayout?.addView(editText)
                    }
                    "TEXT" ->{
                        val linearLayout = findViewById<LinearLayout>(R.id.LinearLayout)
                        val textView = TextView(this)
                        textView.layoutParams = LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                        )
                        textView.text=it.name
                        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,20F)
                        textView.setTextColor(Color.parseColor("#1DB954"))
                        textView.setPadding(20,20,20,20)
                        linearLayout?.addView(textView)

                        val editText = EditText(this)
                        editText.layoutParams = LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                        )
                        editText.hint= "Entrez "+it.name
                        editText.setPadding(20,20,20,40)
                        editText.setTag(it.name)
                        linearLayout?.addView(editText)
                    }
                    "LOCALDATE" -> {
                        val linearLayout = findViewById<LinearLayout>(R.id.LinearLayout)
                        val textView = TextView(this)
                        textView.layoutParams = LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                        )
                        textView.text=it.name+" (localdate)"
                        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,20F)
                        textView.setTextColor(Color.parseColor("#1DB954"))
                        textView.setPadding(20,20,20,20)
                        linearLayout?.addView(textView)

                        val editText = EditText(this)
                        editText.layoutParams = LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                        )
                        editText.hint= "Entrez "+it.name
                        editText.inputType = InputType.TYPE_CLASS_DATETIME
                        editText.setTag(it.name);
                        editText.setPadding(20,20,20,40)
                        //Date dialog
                        editText.setFocusable(NOT_FOCUSABLE)
                        editText.setOnClickListener {
                            val now = Calendar.getInstance()
                            val datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                                val monthFormatted : String
                                val monthReal=month+1
                                if(monthReal.toString().length!=2){
                                    monthFormatted="0"+monthReal.toString()
                                }else{
                                    monthFormatted=monthReal.toString()
                                }
                                val dayFormatted : String
                                if(dayOfMonth.toString().length!=2){
                                    dayFormatted="0"+dayOfMonth.toString()
                                }else{
                                    dayFormatted=dayOfMonth.toString()
                                }
                                editText.setText(year.toString()+"-"+monthFormatted+"-"+dayFormatted)
                            },
                                    now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH))
                            datePicker.show()
                        }
                        linearLayout?.addView(editText)
                    }
                    "BOOLEAN"->{
                        val linearLayout = findViewById<LinearLayout>(R.id.LinearLayout)
                        val checkBox = CheckBox(this)
                        checkBox.setText(it.name)
                        checkBox.layoutParams = LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                        )
                        checkBox.setTag(it.name)
                        checkBox.setPadding(20,20,20,40)
                        linearLayout?.addView(checkBox)
                    }
                    else -> Toast.makeText(this, it.type+"Type not supported",Toast.LENGTH_LONG).show();
                }
            }
            //creating the submit button
            val linearLayout = findViewById<LinearLayout>(R.id.LinearLayout)
            val submitButton = Button(this)
            submitButton.layoutParams = LinearLayout.LayoutParams(
                    400,
                    100
            )
            submitButton.setText("SUBMIT")
            submitButton.setBackgroundResource(R.drawable.round_bg)
            submitButton.setPadding(20,20,20,20)
            //setting the onclick listener on the button
            submitButton.setOnClickListener {
                //getting constraints
                contractConstraints.forEach{constraint: Constraint ->
                    if(!constraint.explanation.isEmpty() && !constraint.explanation.isBlank()){
                        for (s in constraint.explanation){
                            if (s==' '){
                                constraints+=";"
                                break
                            }
                            constraints+=s
                        }
                    }
                }
                //whole object with the contract name
                val objectToSend = JSONObject()
                //object of the form
                val form = JSONObject()
                //handling constraints
                linearLayout.forEach { view: View ->
                    val constraintsList = constraints.split(";")
                    constraintsList.forEach { element : String ->
                        if (element!=" " && !element.isEmpty() && !element.isBlank() && !element.isNullOrBlank()){
                            if(view.getTag() == element){
                                if(view is EditText){
                                    val myedit : EditText = view
                                    if(myedit.text.toString().isBlank() || myedit.text.toString().isEmpty()){
                                        Toast.makeText(this,element+" is mandatory.",Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }
                    }
                    //creating the object to submit
                    if (view.getTag() != null) {
                        if (view is EditText){
                            val myedit : EditText = view
                            if(myedit.text.toString().isBlank() || myedit.text.toString().isEmpty()){
                                objectToSend.put(""+view.getTag()?.toString(),JSONObject.NULL)
                            }else{
                                objectToSend.put(""+view.getTag()?.toString(),myedit.text)
                            }
                        }else if (view is CheckBox){
                            val mycheck : CheckBox = view
                            if (mycheck.isChecked){
                                objectToSend.put(""+view.getTag()?.toString(),"true")
                            }else{
                                objectToSend.put(""+view.getTag()?.toString(),"false")
                            }
                        }
                    }
                }

                form.put(contractName,objectToSend)
                val intent = Intent(this, Case::class.java)
                intent.putExtra("form",form.toString())
                intent.putExtra("processId",processId)
                this.startActivity(intent)
            }
            linearLayout?.addView(submitButton)
        }
    }

    fun timePicker(date:String,editText: EditText){
        val now = Calendar.getInstance()
        val timePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            val minuteFormatted : String
            if(minute.toString().length != 2){
                minuteFormatted="0"+minute.toString()
            }else{
                minuteFormatted = minute.toString()
            }
            formattedDateTime = date+"T"+hourOfDay.toString()+":"+minuteFormatted+":00Z"
            editText.setText(formattedDateTime)
        },
                now.get(Calendar.HOUR_OF_DAY),now.get(Calendar.MINUTE),true)
                timePicker.show()
    }
}