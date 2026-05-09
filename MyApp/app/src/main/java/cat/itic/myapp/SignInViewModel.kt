package cat.itic.myapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignInViewModel : ViewModel() {

    private val _usernameError = MutableLiveData<String?>()
    val usernameError: LiveData<String?> = _usernameError

    private val _emailError = MutableLiveData<String?>()
    val emailError: LiveData<String?> = _emailError

    private val _passwordError = MutableLiveData<String?>()
    val passwordError: LiveData<String?> = _passwordError

    private val _confirmPasswordError = MutableLiveData<String?>()
    val confirmPasswordError: LiveData<String?> = _confirmPasswordError

    private val _formValid = MutableLiveData<Boolean>()
    val formValid: LiveData<Boolean> = _formValid

    fun onRegisterClicked(
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    ) {

        val usernameError = validateUsername(username)
        val emailError = validateEmail(email)
        val passwordError = validatePassword(password)
        val confirmPasswordError = validateConfirmPassword(password, confirmPassword)

        _usernameError.value = usernameError
        _emailError.value = emailError
        _passwordError.value = passwordError
        _confirmPasswordError.value = confirmPasswordError

        _formValid.value =
            usernameError == null &&
                    emailError == null &&
                    passwordError == null &&
                    confirmPasswordError == null
    }

    private fun validateUsername(username: String): String? {
        if (username.isBlank()) return "Username is required"
        if (username.length < 3) return "Minimum 3 characters"
        return null
    }

    private fun validateEmail(email: String): String? {
        val regex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
        if (!regex.matches(email)) return "Invalid email"
        return null
    }

    private fun validatePassword(password: String): String? {
        if (password.length < 8) return "Minimum 8 characters"
        if (!password.any { it.isDigit() }) return "Must contain at least one number"
        return null
    }

    private fun validateConfirmPassword(password: String, confirmPassword: String): String? {
        if (confirmPassword.isBlank()) return "Confirm password required"
        if (password != confirmPassword) return "Passwords do not match"
        return null
    }
}