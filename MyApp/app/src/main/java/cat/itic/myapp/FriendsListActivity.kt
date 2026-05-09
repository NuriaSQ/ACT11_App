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

class FriendsListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends_list)

        val friends = mutableListOf(
            Friend("Username_01"),
            Friend("Username_02"),
            Friend("Username_03")
        )

        val recycler = findViewById<RecyclerView>(R.id.friendsRecycler)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = FriendsAdapter(friends)

        findViewById<ImageButton>(R.id.menuButton)
            .setOnClickListener { startActivity(Intent(this, MenuActivity::class.java)) }

        findViewById<ImageButton>(R.id.profileButton)
            .setOnClickListener { startActivity(Intent(this, ProfileActivity::class.java)) }
    }

    data class Friend(val username: String)

    class FriendsAdapter(private val items: MutableList<Friend>) :
        RecyclerView.Adapter<FriendsAdapter.FriendHolder>() {

        inner class FriendHolder(view: View) : RecyclerView.ViewHolder(view) {
            val image: ImageView = view.findViewById(R.id.friendImage)
            val chat: Button = view.findViewById(R.id.chatButton)
            val block: Button = view.findViewById(R.id.blockButton)
            val delete: Button = view.findViewById(R.id.deleteButton)
            val username: TextView = view.findViewById(R.id.friendUsername)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_friend, parent, false)
            return FriendHolder(view)
        }

        override fun onBindViewHolder(holder: FriendHolder, position: Int) {
            val friend = items[position]

            holder.username.text = friend.username
            holder.image.setImageResource(android.R.drawable.ic_menu_gallery)

            holder.chat.setOnClickListener {}

            holder.block.setOnClickListener {}

            holder.delete.setOnClickListener {
                items.removeAt(position)
                notifyItemRemoved(position)
            }
        }

        override fun getItemCount() = items.size
    }
}
