package com.example.task

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.task.databinding.ActivityLoginScreenBinding
import com.google.firebase.auth.FirebaseAuth

class LoginScreen : AppCompatActivity() {

    lateinit var loginBindig : ActivityLoginScreenBinding

    val auth : FirebaseAuth = FirebaseAuth.getInstance()

    private lateinit var progressDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        loginBindig = ActivityLoginScreenBinding.inflate(layoutInflater)
        val view = loginBindig.root
        setContentView(view)

        loginBindig.signUp.setOnClickListener {
            var intent = Intent(this, SignUpScreen::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        loginBindig.layout.setOnClickListener{
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            val focusedView = currentFocus
            if (focusedView != null) {
                inputMethodManager.hideSoftInputFromWindow(focusedView.windowToken, 0)
            }
        }

        loginBindig.passwordFieldLogin.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD

        loginBindig.loginBtn.setOnClickListener {

            val userEmail = loginBindig.emailTextFieldLogin.text.toString()
            val userPassword = loginBindig.passwordFieldLogin.text.toString()


            if (userEmail.isEmpty()) {
                Toast.makeText(this, "Please, Enter email.", Toast.LENGTH_LONG).show()
            } else if (userPassword.isEmpty()) {
                Toast.makeText(this, "Please, Enter password.", Toast.LENGTH_LONG).show()
            } else if (!isEmailValid(userEmail)) {
                Toast.makeText(this, "Enter valid email.", Toast.LENGTH_LONG).show()
            } else if (userPassword.length < 6) {
                Toast.makeText(
                    this,
                    "Password can't be smaller than six digits.",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                signinWithFirebase(userEmail,userPassword)
            }
        }


    }

    fun isEmailValid(email: String): Boolean {
        val regexPattern = Regex("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        return regexPattern.matches(email)
    }

    fun signinWithFirebase(userEmail : String, userPassword : String){

        showLoadingDialog()

        auth.signInWithEmailAndPassword(userEmail, userPassword)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    dismissLoadingDialog()
                    Toast.makeText(applicationContext,"Login is successful", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    finish()

                } else {
                    // If sign in fails, display a message to the user.
                    dismissLoadingDialog()
                    Toast.makeText(applicationContext,task.exception?.message.toString(), Toast.LENGTH_LONG).show()
                }
            }

    }

    override fun onStart() {
        super.onStart()

        val user = auth.currentUser

        if (user != null){

            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()

        }

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