package com.example.task

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.mycric.model.getClient
import com.example.task.databinding.ActivityGlobleCountryScreenBinding
import com.example.task.model.AllCountry
import com.example.task.service.AllCountryInterface
import com.example.task.service.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GlobleCountryScreen : AppCompatActivity() {

    lateinit var globleBinding : ActivityGlobleCountryScreenBinding

    val baseUrl : String = "https://corona.lmao.ninja/v2/all/"

    lateinit  var globlData: AllCountry

    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        globleBinding = ActivityGlobleCountryScreenBinding.inflate(layoutInflater)
        val view = globleBinding.root
        setContentView(view)
        supportActionBar?.hide()

        showLoadingDialog()

        val creation = getClient(baseUrl)?.create(AllCountryInterface::class.java)
        val call = creation?.allCountry()

        call!!.enqueue(object : Callback<AllCountry> {
            override fun onResponse(call: Call<AllCountry>, response: Response<AllCountry>) {
                dismissLoadingDialog()
                globlData = response.body() as AllCountry
                Log.d("Response", "Response API : ${globlData}")
                globleBinding.caseNumber.text = globlData.cases.toString() + "[+${globlData.todayCases}]"
                globleBinding.deaths.text = globlData.deaths.toString() + "[+${globlData.todayDeaths}]"
                globleBinding.recoveredNumber.text = globlData.recovered.toString() + "[+${globlData.todayRecovered}]"
                globleBinding.affectedNumber.text = globlData.affectedCountries.toString()
            }

            override fun onFailure(call: Call<AllCountry>, t: Throwable) {
                dismissLoadingDialog()
                Log.e("Errors", "error     " + t.message)
                Toast.makeText(
                    this@GlobleCountryScreen,
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