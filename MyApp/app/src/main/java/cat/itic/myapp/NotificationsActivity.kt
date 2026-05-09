package cat.itic.myapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NotificationsActivity : AppCompatActivity() {

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)

        val notifications = mutableListOf(
            NotificationItem(
                "New comment on your post",
                "Bob commented: 'Great gameplay review!'"
            ),
            NotificationItem(
                "Event reminder",
                "Don't forget the tournament starting tomorrow at 5 PM."
            )
        )

        val recycler = findViewById<RecyclerView>(R.id.notificationsRecycler)
        val adapter = NotificationsAdapter(notifications)

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        findViewById<Button>(R.id.deleteAllButton).setOnClickListener {
            notifications.clear()
            adapter.notifyDataSetChanged()
        }

        findViewById<ImageButton>(R.id.menuButton)
            .setOnClickListener { startActivity(Intent(this, MenuActivity::class.java)) }

        findViewById<ImageButton>(R.id.profileButton)
            .setOnClickListener { startActivity(Intent(this, ProfileActivity::class.java)) }
    }

    data class NotificationItem(val title: String, val message: String)

    class NotificationsAdapter(private val items: MutableList<NotificationItem>) :
        RecyclerView.Adapter<NotificationsAdapter.NotificationHolder>() {

        inner class NotificationHolder(view: View) : RecyclerView.ViewHolder(view) {
            val title: TextView = view.findViewById(R.id.notificationTitle)
            val message: TextView = view.findViewById(R.id.notificationMessage)
            val delete: Button = view.findViewById(R.id.deleteNotificationButton)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_notification, parent, false)
            return NotificationHolder(view)
        }

        override fun onBindViewHolder(holder: NotificationHolder, position: Int) {
            val item = items[position]

            holder.title.text = item.title
            holder.message.text = item.message

            holder.delete.setOnClickListener {
                items.removeAt(position)
                notifyItemRemoved(position)
            }
        }

        override fun getItemCount() = items.size
    }
}
