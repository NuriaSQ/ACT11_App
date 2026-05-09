package cat.itic.myapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class NotificationsConfigurationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications_configuration)

        val menuBtn = findViewById<ImageButton>(R.id.menuButton)
        val profileBtn = findViewById<ImageButton>(R.id.profileButton)

        menuBtn.setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
        }

        profileBtn.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }
}