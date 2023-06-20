package com.example.task

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.mycric.model.getClient
import com.example.task.adapters.DisRecyclerViewAdapter
import com.example.task.adapters.RecyclerViewAdapter
import com.example.task.databinding.ActivityDiscriptionBinding
import com.example.task.model.AllCountryWise
import com.example.task.model.CountryInfo
import com.example.task.model.DisModel
import com.example.task.service.ApiInterface
import com.example.task.service.DisCountryApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DiscriptionScreen : AppCompatActivity() {

    lateinit var recyclerViewAdapter: DisRecyclerViewAdapter

    private lateinit var progressDialog: ProgressDialog

    lateinit var disBinding: ActivityDiscriptionBinding

    lateinit var info: String
    lateinit var name: String

    lateinit var arrayList: DisModel

   lateinit var  baseUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        disBinding = ActivityDiscriptionBinding.inflate(layoutInflater)
        var view = disBinding.root
        setContentView(view)

        supportActionBar?.hide()

        info = getIntent().getStringExtra("info").toString()
        name = getIntent().getStringExtra("name").toString()
        baseUrl = "https://corona.lmao.ninja/v2/historical/$info/"

        disBinding.countryNameDis.text = name

        disBinding.backicon.setOnClickListener {
            finish()
        }
        disBinding.backtext.setOnClickListener {
            finish()
        }

        showLoadingDialog()
        val creation = getClient(baseUrl)?.create(DisCountryApi::class.java)
        val call = creation?.disCountry()

        call!!.enqueue(object : Callback<DisModel> {
            override fun onResponse(
                call: Call<DisModel>,
                response: Response<DisModel>
            ) {
                dismissLoadingDialog()
                arrayList = response.body() as DisModel

                Log.d("Response1", "Response API : ${arrayList}")
                disBinding.disRcyView.layoutManager = LinearLayoutManager(this@DiscriptionScreen)
                recyclerViewAdapter = DisRecyclerViewAdapter(this@DiscriptionScreen,arrayList  )
                disBinding.disRcyView.adapter = recyclerViewAdapter


                recyclerViewAdapter.notifyDataSetChanged()

            }

            override fun onFailure(call: Call<DisModel>, t: Throwable) {
                dismissLoadingDialog()
                Log.e("Errors", "error     " + t.message)
                Toast.makeText(
                    this@DiscriptionScreen,
                    "Error : API ${t.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        })


    }


    private fun showLoadingDialog() {
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Loading...")
        progressDialog.setCancelable(false)
        progressDialog.show()
    }

    private fun dismissLoadingDialog() {
        progressDialog.dismiss()
    }

}