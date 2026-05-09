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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch

class CollectionsModifyActivity : AppCompatActivity() {

    private lateinit var allGames: List<Videogame>
    private val selectedGames = mutableListOf<Videogame>()
    private val searchResults = mutableListOf<Videogame>()

    private lateinit var collection: CollectionsRepository.GameCollection
    private var collectionIndex = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collections_modify)

        collectionIndex = intent.getIntExtra("collectionIndex", -1)
        collection = CollectionsRepository.collections[collectionIndex]

        val nameEdit = findViewById<EditText>(R.id.collectionName)
        val descEdit = findViewById<EditText>(R.id.collectionDescription)
        val saveBtn = findViewById<Button>(R.id.saveCollection)

        nameEdit.setText(collection.name)
        descEdit.setText(collection.description)

        selectedGames.addAll(collection.games)

        val selectedRecycler = findViewById<RecyclerView>(R.id.selectedGamesRecycler)
        selectedRecycler.layoutManager = LinearLayoutManager(this)
        val selectedAdapter = SelectedGamesAdapter(selectedGames)
        selectedRecycler.adapter = selectedAdapter

        val searchRecycler = findViewById<RecyclerView>(R.id.searchResultsRecycler)
        searchRecycler.layoutManager = LinearLayoutManager(this)

        val searchAdapter = SearchAdapter(searchResults) { game ->
            if (!selectedGames.contains(game)) {
                selectedGames.add(game)
                selectedAdapter.notifyItemInserted(selectedGames.size - 1)
            }
        }

        searchRecycler.adapter = searchAdapter

        val searchEdit = findViewById<EditText>(R.id.searchGame)

        lifecycleScope.launch {
            val response = VideogameAPI.API().getAllVideogames()
            allGames = if (response.isSuccessful) response.body() ?: emptyList() else emptyList()
        }

        searchEdit.addTextChangedListener(object : TextWatcher {

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

        saveBtn.setOnClickListener {

            val name = nameEdit.text.toString()

            if (name.isEmpty()) {
                Toast.makeText(this, "Collection name required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            collection.name = name
            collection.description = descEdit.text.toString()
            collection.games = selectedGames.toMutableList()

            startActivity(Intent(this, CollectionsActivity::class.java))
            finish()
        }

        findViewById<ImageButton>(R.id.menuButton)
            .setOnClickListener { startActivity(Intent(this, MenuActivity::class.java)) }

        findViewById<ImageButton>(R.id.profileButton)
            .setOnClickListener { startActivity(Intent(this, ProfileActivity::class.java)) }
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

    class SelectedGamesAdapter(private val items: MutableList<Videogame>) :
        RecyclerView.Adapter<SelectedGamesAdapter.GameHolder>() {

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

            holder.delete.setOnClickListener {
                items.removeAt(position)
                notifyItemRemoved(position)
            }
        }

        override fun getItemCount() = items.size
    }
}