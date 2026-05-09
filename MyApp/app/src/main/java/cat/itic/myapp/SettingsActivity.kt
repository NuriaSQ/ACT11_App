package cat.itic.myapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val menuBtn = findViewById<ImageButton>(R.id.menuButton)
        val profileBtn = findViewById<ImageButton>(R.id.profileButton)
        val generalBtn = findViewById<Button>(R.id.generalButton)
        val accountBtn = findViewById<Button>(R.id.accountButton)
        val notificationsBtn = findViewById<Button>(R.id.notificationsButton)
        val appearanceBtn = findViewById<Button>(R.id.appearanceButton)
        val languageBtn = findViewById<Button>(R.id.languageButton)
        val statsBtn = findViewById<Button>(R.id.statsButton)
        val logoutBtn = findViewById<Button>(R.id.logoutButton)
        val exitAppBtn = findViewById<Button>(R.id.exitAppButton)

        menuBtn.setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
        }

        profileBtn.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        generalBtn.setOnClickListener {
            startActivity(Intent(this, GeneralConfigurationActivity::class.java))
        }

        accountBtn.setOnClickListener {
            startActivity(Intent(this, AccountActivity::class.java))
        }

        notificationsBtn.setOnClickListener {
            startActivity(Intent(this, NotificationsActivity::class.java))
        }

        appearanceBtn.setOnClickListener {
            startActivity(Intent(this, AppearanceActivity::class.java))
        }

        languageBtn.setOnClickListener {
            startActivity(Intent(this, LanguagesActivity::class.java))
        }

        statsBtn.setOnClickListener {
            startActivity(Intent(this, StatsActivity::class.java))
        }

        logoutBtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        exitAppBtn.setOnClickListener {
            finishAffinity()
        }
    }

    override fun onResume() {
        super.onResume()
        StatsManager.increment(this, "settings_opens")
    }
}
