package cat.itic.myapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val username = findViewById<EditText>(R.id.loginUsernameEditText)
        val password = findViewById<EditText>(R.id.loginPasswordEditText)
        val loginButton = findViewById<Button>(R.id.loginButton)

        viewModel.usernameError.observe(this) {
            username.error = it
        }

        viewModel.passwordError.observe(this) {
            password.error = it
        }

        viewModel.loginSuccess.observe(this) {
            if (it == true) {
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
        }

        loginButton.setOnClickListener {
            viewModel.onLoginClicked(
                username.text.toString(),
                password.text.toString()
            )
        }

        findViewById<TextView>(R.id.newUserText).setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }

        findViewById<Button>(R.id.closeLoginAppButton).setOnClickListener {
            finishAffinity()
        }
    }
}
