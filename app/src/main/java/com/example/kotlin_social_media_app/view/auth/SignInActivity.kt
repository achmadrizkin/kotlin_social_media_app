package com.example.kotlin_social_media_app.view.auth

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kotlin_social_media_app.R
import com.example.kotlin_social_media_app.model.UserAuth
import com.example.kotlin_social_media_app.view.bottomNav.BottomNavActivity
import com.example.kotlin_social_media_app.view_model.SignInActivityViewModel
import com.example.kotlin_social_media_app.view_model.UserActivityViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInActivity : AppCompatActivity() {

    private lateinit var viewModelSignIn: SignInActivityViewModel

    private lateinit var mAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    lateinit var buttonSignIn: Button
    lateinit var buttonLogin: Button
    lateinit var buttonRegister: Button

    lateinit var etEmail: EditText
    lateinit var etPassword: EditText

    companion object {
        private const val RC_SIGN_IN = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        buttonSignIn = findViewById(R.id.buttonSignIn)
        buttonLogin = findViewById(R.id.buttonLogin)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        buttonRegister = findViewById(R.id.buttonRegister)


        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("908661767228-63045vq77io4rkd6jdafj5fm3u44l4f6.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        mAuth = FirebaseAuth.getInstance()

        //
        iniViewModel()
        postUserOrUpdateObservable()

        //
        buttonSignIn.setOnClickListener {
            signIn()
        }

        //
        buttonLogin.setOnClickListener {
            loginUser()
        }

        buttonRegister.setOnClickListener {
            createUser()
        }
    }

    private fun iniViewModel() {
        viewModelSignIn = ViewModelProvider(this).get(SignInActivityViewModel::class.java)
    }

    private fun postUserOrUpdate(email: String) {
       viewModelSignIn.createUserOrUpdateOfData(email)
    }

    private fun postUserOrUpdateObservable() {
        viewModelSignIn.createUserOrUpdateObservable().observe(this, Observer {
            if (it == null) {
                Toast.makeText(
                    this,
                    "Welcome Back !",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    this,
                    "Register Success",
                    Toast.LENGTH_LONG
                ).show()
                finish()
            }
        })
    }

    private fun loginUser() {
        val email = etEmail.getText().toString()
        val password = etPassword.getText().toString()

        if (email.isEmpty()) {
            Toast.makeText(this, "Email is cannot be empty", Toast.LENGTH_SHORT).show()
        } else if (password.isEmpty()) {
            Toast.makeText(this, "Password is cannot be empty", Toast.LENGTH_SHORT).show()
        } else {
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(ContentValues.TAG, "signInWithEmail:success")
                        val user = mAuth.currentUser

                        // add to database
                        postUserOrUpdate(user?.email!!)

                        //
                        val intent = Intent(this, BottomNavActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(ContentValues.TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth.currentUser
        if (currentUser != null) {
            val intent = Intent(this, BottomNavActivity::class.java)
            startActivity(intent)
        }
    }

    private fun createUser() {
        val email = etEmail.getText().toString()
        val password = etPassword.getText().toString()

        if (email.isEmpty()) {
            Toast.makeText(this, "Email is cannot be empty", Toast.LENGTH_SHORT).show()
        } else if (password.isEmpty()) {
            Toast.makeText(this, "Password is cannot be empty", Toast.LENGTH_SHORT).show()
        } else {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Register Complete", Toast.LENGTH_SHORT)
                        .show()

                    // add to database
                    postUserOrUpdate(email)

                    //
                    val intent = Intent(this, BottomNavActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Register Error: " + task.exception, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception

            if (task.isSuccessful) {
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d("SignInActivity", "firebaseAuthWithGoogle:" + account.id)
                    firebaseAuthWithGoogle(account.idToken!!)

                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Log.w("SignInActivity", "Google sign in failed", e)
                }
            } else {
                Log.w("SignInActivity", exception.toString())
            }

        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("SignInActivity", "signInWithCredential:success")

                    //
                    val intent = Intent(this, BottomNavActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("SignInActivity", "signInWithCredential:failure", task.exception)
                }
            }
    }
}