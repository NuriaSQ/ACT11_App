package cat.itic.myapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val newGamesRecycler = findViewById<RecyclerView>(R.id.homeNewGamesRecycler)
        newGamesRecycler.layoutManager = LinearLayoutManager(this)

        val newGamesAdapter = NewGamesAdapter(mutableListOf()) { videogame ->
            val intent = Intent(this, GameViewActivity::class.java)
            intent.putExtra("gameId", videogame.id)
            startActivity(intent)
        }
        newGamesRecycler.adapter = newGamesAdapter

        lifecycleScope.launch {
            try {
                val response = VideogameAPI.API().getAllVideogames()
                if (response.isSuccessful) {
                    val games = response.body() ?: emptyList()
                    newGamesAdapter.updateData(games)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        val activityList = mutableListOf(
            HomeActivityItem("Updated notes for Cyberpunk", android.R.drawable.ic_menu_edit),
            HomeActivityItem("New message in your forum thread", android.R.drawable.ic_dialog_email)
        )

        val activityRecycler = findViewById<RecyclerView>(R.id.homeActivityRecycler)
        activityRecycler.layoutManager = LinearLayoutManager(this)
        activityRecycler.adapter = ActivityAdapter(activityList)

        findViewById<ImageButton>(R.id.menuButton)
            .setOnClickListener { startActivity(Intent(this, MenuActivity::class.java)) }

        findViewById<ImageButton>(R.id.profileButton)
            .setOnClickListener { startActivity(Intent(this, ProfileActivity::class.java)) }
    }

    data class HomeActivityItem(val text: String, val iconRes: Int)

    class NewGamesAdapter(
        private var items: MutableList<Videogame>,
        private val onClick: (Videogame) -> Unit
    ) : RecyclerView.Adapter<NewGamesAdapter.NewGameHolder>() {

        inner class NewGameHolder(view: View) : RecyclerView.ViewHolder(view) {
            val title: TextView = view.findViewById(R.id.newGameTitle)
            val image: ImageView = view.findViewById(R.id.newGameImage)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewGameHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_home_new_game, parent, false)
            return NewGameHolder(view)
        }

        override fun onBindViewHolder(holder: NewGameHolder, position: Int) {
            val game = items[position]
            holder.title.text = game.name

            Glide.with(holder.image.context)
                .load(game.image)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(holder.image)

            holder.itemView.setOnClickListener {
                onClick(game)
            }
        }

        override fun getItemCount() = items.size

        @SuppressLint("NotifyDataSetChanged")
        fun updateData(newGames: List<Videogame>) {
            items = newGames.toMutableList()
            notifyDataSetChanged()
        }
    }

    class ActivityAdapter(private val items: MutableList<HomeActivityItem>) :
        RecyclerView.Adapter<ActivityAdapter.ActivityHolder>() {

        inner class ActivityHolder(view: View) : RecyclerView.ViewHolder(view) {
            val icon: ImageView = view.findViewById(R.id.activityIcon)
            val text: TextView = view.findViewById(R.id.activityText)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_home_activity, parent, false)
            return ActivityHolder(view)
        }

        override fun onBindViewHolder(holder: ActivityHolder, position: Int) {
            val item = items[position]
            holder.text.text = item.text
            holder.icon.setImageResource(item.iconRes)
        }

        override fun getItemCount() = items.size
    }
}