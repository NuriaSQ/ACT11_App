package cat.itic.myapp

import android.annotation.SuppressLint
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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CollectionsActivity : AppCompatActivity() {

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        recycler.adapter?.notifyDataSetChanged()
    }

    private lateinit var recycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collections)

        recycler = findViewById(R.id.collectionsRecycler)

        recycler.layoutManager = GridLayoutManager(this, 2)
        recycler.adapter = CollectionsAdapter(CollectionsRepository.collections)

        findViewById<ImageButton>(R.id.menuButton)
            .setOnClickListener { startActivity(Intent(this, MenuActivity::class.java)) }

        findViewById<ImageButton>(R.id.profileButton)
            .setOnClickListener { startActivity(Intent(this, ProfileActivity::class.java)) }

        findViewById<Button>(R.id.createButton)
            .setOnClickListener {
                startActivity(Intent(this, CollectionCreateActivity::class.java))
            }
    }

    class CollectionsAdapter(private val items: MutableList<CollectionsRepository.GameCollection>) :
        RecyclerView.Adapter<CollectionsAdapter.CollectionHolder>() {

        inner class CollectionHolder(view: View) : RecyclerView.ViewHolder(view) {
            val name: TextView = view.findViewById(R.id.collectionName)
            val image: ImageView = view.findViewById(R.id.collectionImage)
            val modify: Button = view.findViewById(R.id.modifyButton)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_collection, parent, false)
            return CollectionHolder(view)
        }

        override fun onBindViewHolder(holder: CollectionHolder, position: Int) {

            val collection = items[position]

            holder.name.text = collection.name

            if (collection.games.isNotEmpty()) {

                Glide.with(holder.image.context)
                    .load(collection.games[0].image)
                    .placeholder(android.R.drawable.ic_menu_gallery)
                    .into(holder.image)

            } else {

                holder.image.setImageResource(android.R.drawable.ic_menu_gallery)

            }

            holder.itemView.setOnClickListener {

                val intent = Intent(holder.itemView.context, CollectionViewActivity::class.java)
                intent.putExtra("collectionIndex", position)
                holder.itemView.context.startActivity(intent)

            }

            holder.modify.setOnClickListener {

                val intent = Intent(holder.itemView.context, CollectionsModifyActivity::class.java)
                intent.putExtra("collectionIndex", position)
                holder.itemView.context.startActivity(intent)

            }
        }

        override fun getItemCount() = items.size
    }
}