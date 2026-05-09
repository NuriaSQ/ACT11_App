package cat.itic.myapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch

class GameViewActivity : AppCompatActivity() {

    private lateinit var gameTitle: TextView
    private lateinit var gameImage: ImageView
    private lateinit var gameDescription: TextView

    private var currentGame: Videogame? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_view)

        val menuBtn = findViewById<ImageButton>(R.id.menuButton)
        val profileBtn = findViewById<ImageButton>(R.id.profileButton)

        val collectionBtn = findViewById<MaterialButton>(R.id.addToCollection)
        val wishlistBtn = findViewById<Button>(R.id.addToWishlist)

        menuBtn.setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
        }

        profileBtn.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        collectionBtn.setOnClickListener {
            startActivity(Intent(this, CollectionsActivity::class.java))
        }

        wishlistBtn.setOnClickListener {

            currentGame?.let {

                if (!WishlistRepository.wishlistGames.contains(it)) {
                    WishlistRepository.wishlistGames.add(it)
                }

            }

            startActivity(Intent(this, WishlistActivity::class.java))
        }

        gameTitle = findViewById(R.id.gameTitle)
        gameImage = findViewById(R.id.gameImage)
        gameDescription = findViewById(R.id.gameDescription)

        val gameId = intent.getLongExtra("gameId", -1L)

        if (gameId != -1L) {
            loadGame(gameId)
        }
    }

    private fun loadGame(gameId: Long) {
        lifecycleScope.launch {
            try {
                val response = VideogameAPI.API().getVideogameById(gameId)
                if (response.isSuccessful) {

                    val game = response.body()

                    game?.let {

                        currentGame = it

                        gameTitle.text = it.name
                        gameDescription.text = it.description ?: ""

                        Glide.with(this@GameViewActivity)
                            .load(it.image)
                            .placeholder(android.R.drawable.ic_menu_gallery)
                            .into(gameImage)
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}