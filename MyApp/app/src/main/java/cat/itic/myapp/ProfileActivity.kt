package cat.itic.myapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val menuBtn = findViewById<ImageButton>(R.id.menuButton)
        val profileTopBtn = findViewById<ImageButton>(R.id.profileTopButton)
        val friendListBtn = findViewById<Button>(R.id.friendListButton)
        val wishlistBtn = findViewById<Button>(R.id.wishlistButton)
        val settingsBtn = findViewById<Button>(R.id.settingsButton)
        val logoutBtn = findViewById<Button>(R.id.logoutButton)

        menuBtn.setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
        }

        profileTopBtn.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        friendListBtn.setOnClickListener {
            startActivity(Intent(this, FriendsListActivity::class.java))
        }

        wishlistBtn.setOnClickListener {
            startActivity(Intent(this, WishlistActivity::class.java))
        }

        settingsBtn.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        logoutBtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}
