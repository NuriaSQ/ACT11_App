package cat.itic.myapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class VideogameAdapter(
    private var games: List<Videogame>,
    private val onClick: (Videogame) -> Unit
) : RecyclerView.Adapter<VideogameAdapter.GameViewHolder>() {

    private var allGames: List<Videogame> = games

    inner class GameViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.gameName)
        val image: ImageView = view.findViewById(R.id.gameImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_game, parent, false)
        return GameViewHolder(view)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = games[position]

        holder.name.text = game.name

        Glide.with(holder.image.context)
            .load(game.image)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .into(holder.image)

        holder.itemView.setOnClickListener {
            onClick(game)
        }
    }

    override fun getItemCount() = games.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newGames: List<Videogame>) {
        games = newGames
        allGames = newGames
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filter(query: String) {
        games = if (query.isEmpty()) {
            allGames
        } else {
            allGames.filter {
                it.name.contains(query, ignoreCase = true)
            }
        }
        notifyDataSetChanged()
    }
}