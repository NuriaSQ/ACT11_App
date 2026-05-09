package cat.itic.myapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    private val _usernameError = MutableLiveData<String?>()
    val usernameError: LiveData<String?> = _usernameError

    private val _passwordError = MutableLiveData<String?>()
    val passwordError: LiveData<String?> = _passwordError

    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Boolean> = _loginSuccess

    fun onLoginClicked(username: String, password: String) {
        _usernameError.value = validateUsername(username)
        _passwordError.value = validatePassword(password)

        _loginSuccess.value = _usernameError.value == null && _passwordError.value == null
    }

    private fun validateUsername(username: String): String? {
        if (username.isBlank()) return "Username is required"
        if (username.length < 3) return "Minimum 3 characters"
        return null
    }

    private fun validatePassword(password: String): String? {
        if (password.length < 8) return "Minimum 8 characters"
        if (!password.any { it.isDigit() }) return "Must contain at least one number"
        return null
    }
}
