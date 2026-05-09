package cat.itic.myapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ForumsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forums)

        val posts = mutableListOf(
            ForumPost(
                "CommanderShepard",
                "Mass Effect trilogy discussion: Favorite moments and choices in the series."
            ),
            ForumPost(
                "SilentFan42",
                "Silent Hill 2 horror analysis: Scariest parts and atmosphere breakdown."
            ),
            ForumPost(
                "YakuzaKing",
                "Yakuza game experiences: Best fights, side stories, and hidden gems in the series."
            )
        )

        val recycler = findViewById<RecyclerView>(R.id.forumRecycler)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = ForumAdapter(posts)

        findViewById<ImageButton>(R.id.menuButton)
            .setOnClickListener { startActivity(Intent(this, MenuActivity::class.java)) }

        findViewById<ImageButton>(R.id.profileButton)
            .setOnClickListener { startActivity(Intent(this, ProfileActivity::class.java)) }
    }

    data class ForumPost(val author: String, val content: String)

    class ForumAdapter(private val items: MutableList<ForumPost>) :
        RecyclerView.Adapter<ForumAdapter.PostHolder>() {

        inner class PostHolder(view: View) : RecyclerView.ViewHolder(view) {
            val author: TextView = view.findViewById(R.id.postAuthor)
            val content: TextView = view.findViewById(R.id.postContent)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_forum_post, parent, false)
            return PostHolder(view)
        }

        override fun onBindViewHolder(holder: PostHolder, position: Int) {
            val post = items[position]
            holder.author.text = post.author
            holder.content.text = post.content
        }

        override fun getItemCount() = items.size
    }
}
