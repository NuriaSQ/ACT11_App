package cat.itic.myapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SignInActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(SignInActivity::class.java)

    @Test
    fun register_correcte_mostraHome() {

        onView(withId(R.id.usernameEditText))
            .perform(typeText("user123"), closeSoftKeyboard())

        onView(withId(R.id.emailEditText))
            .perform(typeText("test@gmail.com"), closeSoftKeyboard())

        onView(withId(R.id.passwordEditText))
            .perform(typeText("password1"), closeSoftKeyboard())

        onView(withId(R.id.confirmPasswordEditText))
            .perform(typeText("password1"), closeSoftKeyboard())

        onView(withId(R.id.signInButton))
            .perform(click())

        onView(withId(R.id.homeActivityRecycler))
            .check(matches(isDisplayed()))
    }

    @Test
    fun register_usernameBuit_mostraError() {

        onView(withId(R.id.emailEditText))
            .perform(typeText("test@gmail.com"), closeSoftKeyboard())

        onView(withId(R.id.passwordEditText))
            .perform(typeText("password1"), closeSoftKeyboard())

        onView(withId(R.id.confirmPasswordEditText))
            .perform(typeText("password1"), closeSoftKeyboard())

        onView(withId(R.id.signInButton))
            .perform(click())

        onView(withId(R.id.usernameEditText))
            .check(matches(hasErrorText("Username is required")))
    }

    @Test
    fun register_emailInvalid_mostraError() {

        onView(withId(R.id.usernameEditText))
            .perform(typeText("user123"), closeSoftKeyboard())

        onView(withId(R.id.emailEditText))
            .perform(typeText("email"), closeSoftKeyboard())

        onView(withId(R.id.passwordEditText))
            .perform(typeText("password1"), closeSoftKeyboard())

        onView(withId(R.id.confirmPasswordEditText))
            .perform(typeText("password1"), closeSoftKeyboard())

        onView(withId(R.id.signInButton))
            .perform(click())

        onView(withId(R.id.emailEditText))
            .check(matches(hasErrorText("Invalid email")))
    }

    @Test
    fun register_passwordCurta_mostraError() {

        onView(withId(R.id.usernameEditText))
            .perform(typeText("user123"), closeSoftKeyboard())

        onView(withId(R.id.emailEditText))
            .perform(typeText("test@gmail.com"), closeSoftKeyboard())

        onView(withId(R.id.passwordEditText))
            .perform(typeText("123"), closeSoftKeyboard())

        onView(withId(R.id.confirmPasswordEditText))
            .perform(typeText("123"), closeSoftKeyboard())

        onView(withId(R.id.signInButton))
            .perform(click())

        onView(withId(R.id.passwordEditText))
            .check(matches(hasErrorText("Minimum 8 characters")))
    }

    @Test
    fun elements_visibles_inicial() {

        onView(withId(R.id.usernameEditText)).check(matches(isDisplayed()))
        onView(withId(R.id.emailEditText)).check(matches(isDisplayed()))
        onView(withId(R.id.passwordEditText)).check(matches(isDisplayed()))
        onView(withId(R.id.confirmPasswordEditText)).check(matches(isDisplayed()))
        onView(withId(R.id.signInButton)).check(matches(isDisplayed()))
    }
}