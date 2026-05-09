package cat.itic.myapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CollectionViewActivity : AppCompatActivity() {

    private lateinit var collection: CollectionsRepository.GameCollection
    private var collectionIndex = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection_view)

        collectionIndex = intent.getIntExtra("collectionIndex", -1)
        collection = CollectionsRepository.collections[collectionIndex]

        val subtitle = findViewById<TextView>(R.id.collectionSubtitle)
        subtitle.text = collection.name

        val recycler = findViewById<RecyclerView>(R.id.collectionGamesRecycler)
        recycler.layoutManager = LinearLayoutManager(this)

        val adapter = CollectionGamesAdapter(collection.games)
        recycler.adapter = adapter

        findViewById<ImageButton>(R.id.menuButton)
            .setOnClickListener { startActivity(Intent(this, MenuActivity::class.java)) }

        findViewById<ImageButton>(R.id.profileButton)
            .setOnClickListener { startActivity(Intent(this, ProfileActivity::class.java)) }
    }

    class CollectionGamesAdapter(private val items: MutableList<Videogame>) :
        RecyclerView.Adapter<CollectionGamesAdapter.GameHolder>() {

        inner class GameHolder(view: View) : RecyclerView.ViewHolder(view) {

            val name: TextView = view.findViewById(R.id.gameName)
            val image: ImageView = view.findViewById(R.id.gameImage)
            val delete: Button = view.findViewById(R.id.deleteButton)

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameHolder {

            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_selected_game, parent, false)

            return GameHolder(view)
        }

        override fun onBindViewHolder(holder: GameHolder, position: Int) {

            val game = items[position]

            holder.name.text = game.name

            Glide.with(holder.image.context)
                .load(game.image)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(holder.image)

            holder.itemView.setOnClickListener {

                val intent = Intent(holder.itemView.context, GameViewActivity::class.java)
                intent.putExtra("gameId", game.id)
                holder.itemView.context.startActivity(intent)

            }

            holder.delete.setOnClickListener {

            items.removeAt(position)
                notifyItemRemoved(position)

            }
        }

        override fun getItemCount() = items.size
    }
}