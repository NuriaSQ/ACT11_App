package cat.itic.myapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

class SignInActivity : AppCompatActivity() {

    private val viewModel: SignInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val username = findViewById<EditText>(R.id.usernameEditText)
        val email = findViewById<EditText>(R.id.emailEditText)
        val password = findViewById<EditText>(R.id.passwordEditText)
        val confirmPassword = findViewById<EditText>(R.id.confirmPasswordEditText)
        val button = findViewById<Button>(R.id.signInButton)

        viewModel.usernameError.observe(this) {
            username.error = it
        }

        viewModel.emailError.observe(this) {
            email.error = it
        }

        viewModel.passwordError.observe(this) {
            password.error = it
        }

        viewModel.confirmPasswordError.observe(this) {
            confirmPassword.error = it
        }

        viewModel.formValid.observe(this) {
            if (it == true) {
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
        }

        button.setOnClickListener {
            viewModel.onRegisterClicked(
                username.text.toString(),
                email.text.toString(),
                password.text.toString(),
                confirmPassword.text.toString()
            )
        }

        findViewById<TextView>(R.id.alreadyRegisteredText).setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        findViewById<Button>(R.id.closeAppButton).setOnClickListener {
            finishAffinity()
        }
    }
}