package cat.itic.myapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        findViewById<ImageButton>(R.id.menuButton)
            .setOnClickListener {
                finish()
            }

        findViewById<ImageButton>(R.id.profileTopButton)
            .setOnClickListener {
                startActivity(Intent(this, ProfileActivity::class.java))
            }

        findViewById<Button>(R.id.homeButton)
            .setOnClickListener {
                startActivity(Intent(this, HomeActivity::class.java))
            }

        findViewById<Button>(R.id.profileButton)
            .setOnClickListener {
                startActivity(Intent(this, ProfileActivity::class.java))
            }

        findViewById<Button>(R.id.collectionsButton)
            .setOnClickListener {
                startActivity(Intent(this, CollectionsActivity::class.java))
            }

        findViewById<Button>(R.id.wishlistButton)
            .setOnClickListener {
                startActivity(Intent(this, WishlistActivity::class.java))
            }

        findViewById<Button>(R.id.forumsButton)
            .setOnClickListener {
                startActivity(Intent(this, ForumsActivity::class.java))
            }

        findViewById<Button>(R.id.exploreButton)
            .setOnClickListener {
                startActivity(Intent(this, AllGamesListActivity::class.java))
            }

        findViewById<Button>(R.id.notificationsButton)
            .setOnClickListener {
                startActivity(Intent(this, NotificationsActivity::class.java))
            }

        findViewById<Button>(R.id.settingsButton)
            .setOnClickListener {
                startActivity(Intent(this, SettingsActivity::class.java))
            }

        findViewById<Button>(R.id.logoutButton)
            .setOnClickListener {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }

        findViewById<Button>(R.id.closeAppButton)
            .setOnClickListener {
                finishAffinity()
            }
    }
}
