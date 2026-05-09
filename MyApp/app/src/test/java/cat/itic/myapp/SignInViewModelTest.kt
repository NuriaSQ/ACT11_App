package cat.itic.myapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SignInViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: SignInViewModel

    @Before
    fun setUp() {
        viewModel = SignInViewModel()
    }

    @Test
    fun register_ambDadesCorrectes_formValidTrue() {
        viewModel.onRegisterClicked("user123", "test@gmail.com", "password1", "password1")

        assertTrue(viewModel.formValid.value == true)
    }

    @Test
    fun register_ambUsernameBuit_mostraError() {
        viewModel.onRegisterClicked("", "test@gmail.com", "password1", "password1")

        assertEquals("Username is required", viewModel.usernameError.value)
    }

    @Test
    fun register_ambUsernameCurt_mostraError() {
        viewModel.onRegisterClicked("ab", "test@gmail.com", "password1", "password1")

        assertEquals("Minimum 3 characters", viewModel.usernameError.value)
    }

    @Test
    fun register_ambEmailInvalid_mostraError() {
        viewModel.onRegisterClicked("user", "email", "password1", "password1")

        assertEquals("Invalid email", viewModel.emailError.value)
    }

    @Test
    fun register_ambPasswordCurta_mostraError() {
        viewModel.onRegisterClicked("user", "test@gmail.com", "pass", "pass")

        assertEquals("Minimum 8 characters", viewModel.passwordError.value)
    }

    @Test
    fun register_passwordSenseNumero_mostraError() {
        viewModel.onRegisterClicked("user", "test@gmail.com", "password", "password")

        assertEquals("Must contain at least one number", viewModel.passwordError.value)
    }

    @Test
    fun register_confirmPasswordBuit_mostraError() {
        viewModel.onRegisterClicked("user", "test@gmail.com", "password1", "")

        assertEquals("Confirm password required", viewModel.confirmPasswordError.value)
    }

    @Test
    fun register_passwordsNoCoincideixen_mostraError() {
        viewModel.onRegisterClicked("user", "test@gmail.com", "password1", "password2")

        assertEquals("Passwords do not match", viewModel.confirmPasswordError.value)
    }

    @Test
    fun register_varisErrors_formValidFalse() {
        viewModel.onRegisterClicked("", "mal", "123", "456")

        assertFalse(viewModel.formValid.value == true)
    }

    @Test
    fun register_despresError_iDadesCorrectes_formValidTrue() {
        viewModel.onRegisterClicked("", "", "", "")

        assertFalse(viewModel.formValid.value == true)

        viewModel.onRegisterClicked("user", "test@gmail.com", "password1", "password1")

        assertTrue(viewModel.formValid.value == true)
    }
}