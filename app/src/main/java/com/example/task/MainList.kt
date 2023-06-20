package com.example.task

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mycric.model.getClient
import com.example.task.adapters.RecyclerViewAdapter
import com.example.task.databinding.ActivityMainListBinding
import com.example.task.model.AllCountry
import com.example.task.model.AllCountryWise
import com.example.task.model.AllCountryWiseItem
import com.example.task.model.CricModel
import com.example.task.service.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainList : AppCompatActivity() {


    lateinit var recyclerViewAdapter: RecyclerViewAdapter

    private lateinit var progressDialog: ProgressDialog

    lateinit var mainListBinder: ActivityMainListBinding

    lateinit var arrayList: AllCountryWise


    val baseUrl: String = "https://corona.lmao.ninja/v2/countries/"

    lateinit var search_view : SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainListBinder = ActivityMainListBinding.inflate(layoutInflater)
        val view = mainListBinder.root
        setContentView(view)

        supportActionBar?.hide()

        search_view= findViewById(R.id.searchViewTextField)

//      recyclerViewAdapter =  RecyclerViewAdapter( this@ )
        showLoadingDialog()
        val creation = getClient(baseUrl)?.create(ApiInterface::class.java)
        val call = creation?.saveUser()

        call!!.enqueue(object : Callback<AllCountryWise> {
            override fun onResponse(
                call: Call<AllCountryWise>,
                response: Response<AllCountryWise>
            ) {
                dismissLoadingDialog()
                arrayList = response.body() as AllCountryWise
                Log.d("Response", "Response API : ${arrayList[0]}")
                mainListBinder.rcyView.layoutManager = LinearLayoutManager(this@MainList)
                recyclerViewAdapter = RecyclerViewAdapter(this@MainList, arrayList)
                mainListBinder.rcyView.adapter = recyclerViewAdapter


                recyclerViewAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<AllCountryWise>, t: Throwable) {
                dismissLoadingDialog()
                Log.e("Errors", "error     " + t.message)
                Toast.makeText(
                    this@MainList,
                    "Error : API ${t.message}",
                    Toast.LENGTH_LONG
                ).show()
            }


        }


        )





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