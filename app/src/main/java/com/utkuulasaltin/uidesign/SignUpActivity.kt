package com.utkuulasaltin.uidesign

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.EditText
import android.widget.ImageView

class SignUpActivity : AppCompatActivity() {

    private lateinit var ivChangePasswordVisibility: ImageView     // Declares a button for manipulating the visibility of password
    private lateinit var etPassword: EditText     // Declares an edittable text box for the password input
    private var isVisibilityOn = false     // Declares a statement for manipulating the visibility actions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        ivChangePasswordVisibility  = findViewById(R.id.imageViewShowPassword)     // Binds the class object and the XML element to each other
        etPassword = findViewById(R.id.editTextPassword)

        hideText(etPassword)     // Hides the password when the application is launched

        ivChangePasswordVisibility.setOnClickListener {     // Listens to click events of the visibility button
            if (isVisibilityOn) {
                ivChangePasswordVisibility.setImageResource(R.drawable.ic_baseline_visibility_off_24)    // Changes the visibility icon
                isVisibilityOn = false   // Changes the statement
                hideText(etPassword)   // Hides the password
            } else {
                ivChangePasswordVisibility.setImageResource(R.drawable.ic_baseline_visibility_24)
                isVisibilityOn = true
                showText(etPassword)
            }
        }
    }

    fun backToMain(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()    // Destroys current activity so that app returns to previous one when was clicked back arrow button
    }

    fun hideText(editText: EditText) {
        editText.transformationMethod = PasswordTransformationMethod.getInstance()   // Assigns a value to the transformation method for hiding the text object
    }

    fun showText(editText: EditText) {
        editText.transformationMethod = HideReturnsTransformationMethod.getInstance()   // Assigns a value to the transformation method for showing the text object
    }
}