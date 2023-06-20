package com.example.task

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import com.example.task.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    lateinit var mainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        var view  = mainBinding.root
        setContentView(view)

//      actionBar = "Cric App"
        getSupportActionBar()?.setTitle("Covid App");

        mainBinding.webView.setOnClickListener {
            var intent = Intent(this,WebView::class.java)
            startActivity(intent)
        }

        mainBinding.navMainListBtn.setOnClickListener {
            var intent = Intent(this, MainList::class.java)
            startActivity(intent)
        }

        mainBinding.GlobleBtn.setOnClickListener {
            var intent = Intent(this, GlobleCountryScreen::class.java)
            startActivity(intent)
        }

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_delete_all,menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
         if (item.itemId == R.id.signOut){

            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this@MainActivity,LoginScreen::class.java)
            startActivity(intent)
            finish()

        }

        return super.onOptionsItemSelected(item)
    }

}