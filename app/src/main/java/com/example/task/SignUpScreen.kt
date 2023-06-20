package com.example.task

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.task.databinding.ActivitySignUpScreenBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpScreen : AppCompatActivity() {

    lateinit var signUpBinding: ActivitySignUpScreenBinding

    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        signUpBinding = ActivitySignUpScreenBinding.inflate(layoutInflater)
        val view = signUpBinding.root
        setContentView(view)

        signUpBinding.layoutSignUp.setOnClickListener {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            val focusedView = currentFocus
            if (focusedView != null) {
                inputMethodManager.hideSoftInputFromWindow(focusedView.windowToken, 0)
            }
        }

        signUpBinding.signIn.setOnClickListener {
            var intent = Intent(this, LoginScreen::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        signUpBinding.passwordFieldSignUp.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD

        signUpBinding.signUpBtn.setOnClickListener {

            val userEmail = signUpBinding.emailTextFieldSignUp.text.toString()
            val userPassword = signUpBinding.passwordFieldSignUp.text.toString()


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
                signupWithFirebase(userEmail,userPassword)
            }
        }


    }

    fun isEmailValid(email: String): Boolean {
        val regexPattern = Regex("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        return regexPattern.matches(email)
    }

    //signup method
    private fun signupWithFirebase(userEmail: String, userPassword: String) {
        showLoadingDialog()

        auth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener { task ->

            if (task.isSuccessful) {

                dismissLoadingDialog()

                Toast.makeText(
                    applicationContext,
                    "Your account has been created",
                    Toast.LENGTH_SHORT
                ).show()
                finish()

            } else {

                dismissLoadingDialog()

                Toast.makeText(applicationContext, task.exception?.toString(), Toast.LENGTH_SHORT)
                    .show()

            }

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