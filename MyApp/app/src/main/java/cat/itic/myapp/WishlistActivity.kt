package cat.itic.myapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch

class WishlistActivity : AppCompatActivity() {

    private lateinit var allGames: List<Videogame>

    private val wishlistGames = WishlistRepository.wishlistGames
    private val searchResults = mutableListOf<Videogame>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wishlist)

        val wishlistRecycler = findViewById<RecyclerView>(R.id.wishlistRecyclerView)
        val searchRecycler = findViewById<RecyclerView>(R.id.searchResultsRecycler)

        wishlistRecycler.layoutManager = LinearLayoutManager(this)
        searchRecycler.layoutManager = LinearLayoutManager(this)

        val wishlistAdapter = WishlistAdapter(wishlistGames)
        val searchAdapter = SearchAdapter(searchResults) { game ->

            if (!wishlistGames.contains(game)) {
                wishlistGames.add(game)
                wishlistAdapter.notifyItemInserted(wishlistGames.size - 1)
                StatsManager.increment(this, "wishlist_added")
            }

        }

        wishlistRecycler.adapter = wishlistAdapter
        searchRecycler.adapter = searchAdapter

        val search = findViewById<EditText>(R.id.searchWishlist)

        findViewById<ImageButton>(R.id.menuButton)
            .setOnClickListener { startActivity(Intent(this, MenuActivity::class.java)) }

        findViewById<ImageButton>(R.id.profileButton)
            .setOnClickListener { startActivity(Intent(this, ProfileActivity::class.java)) }

        lifecycleScope.launch {

            val response = VideogameAPI.API().getAllVideogames()

            allGames = if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                emptyList()
            }

        }

        search.addTextChangedListener(object : TextWatcher {

            @SuppressLint("NotifyDataSetChanged")
            override fun afterTextChanged(s: Editable?) {

                val query = s.toString()

                searchResults.clear()

                if (query.isNotEmpty()) {

                    searchResults.addAll(
                        allGames.filter {
                            it.name.contains(query, true)
                        }
                    )

                }

                searchAdapter.notifyDataSetChanged()

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    class SearchAdapter(
        private val items: List<Videogame>,
        private val onAdd: (Videogame) -> Unit
    ) : RecyclerView.Adapter<SearchAdapter.Holder>() {

        inner class Holder(view: View) : RecyclerView.ViewHolder(view) {

            val title: TextView = view.findViewById(R.id.wishlistTitle)
            val image: ImageView = view.findViewById(R.id.wishlistImage)
            val add: Button = view.findViewById(R.id.deleteButton)

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {

            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_wishlist, parent, false)

            return Holder(view)
        }

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: Holder, position: Int) {

            val game = items[position]

            holder.title.text = game.name

            Glide.with(holder.image.context)
                .load(game.image)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(holder.image)

            holder.add.text = "Add"

            holder.add.setOnClickListener {
                onAdd(game)
            }
        }

        override fun getItemCount() = items.size
    }

    class WishlistAdapter(private val items: MutableList<Videogame>) :
        RecyclerView.Adapter<WishlistAdapter.Holder>() {

        inner class Holder(view: View) : RecyclerView.ViewHolder(view) {

            val title: TextView = view.findViewById(R.id.wishlistTitle)
            val image: ImageView = view.findViewById(R.id.wishlistImage)
            val delete: Button = view.findViewById(R.id.deleteButton)

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {

            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_wishlist, parent, false)

            return Holder(view)
        }

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: Holder, position: Int) {

            val game = items[position]

            holder.title.text = game.name

            Glide.with(holder.image.context)
                .load(game.image)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(holder.image)

            holder.delete.text = "Delete"

            holder.delete.setOnClickListener {

                items.removeAt(position)
                notifyItemRemoved(position)
                StatsManager.increment(holder.itemView.context, "wishlist_removed")

            }

            holder.itemView.setOnClickListener {

                val intent = Intent(holder.itemView.context, GameViewActivity::class.java)
                intent.putExtra("gameId", game.id)
                holder.itemView.context.startActivity(intent)

            }
        }

        override fun getItemCount() = items.size
    }
}