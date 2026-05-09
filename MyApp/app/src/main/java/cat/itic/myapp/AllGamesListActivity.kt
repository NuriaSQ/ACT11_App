package cat.itic.myapp

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class AllGamesListActivity : AppCompatActivity() {

    private lateinit var adapter: VideogameAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_games_list)

        val recyclerView = findViewById<RecyclerView>(R.id.gamesRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = VideogameAdapter(emptyList()) { game ->
            val intent = Intent(this, GameViewActivity::class.java)
            intent.putExtra("gameId", game.id)
            startActivity(intent)
        }

        recyclerView.adapter = adapter

        val search = findViewById<EditText>(R.id.searchExplore)
        search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                adapter.filter(s.toString())
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        findViewById<ImageButton>(R.id.menuButton)
            .setOnClickListener { startActivity(Intent(this, MenuActivity::class.java)) }

        findViewById<ImageButton>(R.id.profileButton)
            .setOnClickListener { startActivity(Intent(this, ProfileActivity::class.java)) }

        loadGames()
    }

    private fun loadGames() {
        lifecycleScope.launch {
            try {
                val response = VideogameAPI.API().getAllVideogames()

                println("Code HTTP: ${response.code()}")

                if (response.isSuccessful) {
                    val games = response.body() ?: emptyList()
                    println("Received Games: ${games.size}")
                    adapter.updateData(games)
                } else {
                    println("Error HTTP")
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}