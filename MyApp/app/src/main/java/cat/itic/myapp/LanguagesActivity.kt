package cat.itic.myapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LanguagesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_languages)

        val languages = mutableListOf(
            Language("English", true),
            Language("Spanish", false),
            Language("Catalan", false)
        )

        val recycler = findViewById<RecyclerView>(R.id.languagesRecycler)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = LanguagesAdapter(languages)

        findViewById<ImageButton>(R.id.menuButton)
            .setOnClickListener { startActivity(Intent(this, MenuActivity::class.java)) }

        findViewById<ImageButton>(R.id.profileButton)
            .setOnClickListener { startActivity(Intent(this, ProfileActivity::class.java)) }
    }

    data class Language(val name: String, var selected: Boolean)

    class LanguagesAdapter(private val items: MutableList<Language>) :
        RecyclerView.Adapter<LanguagesAdapter.LanguageHolder>() {

        private var selectedIndex = items.indexOfFirst { it.selected }

        inner class LanguageHolder(view: View) : RecyclerView.ViewHolder(view) {
            val name: TextView = view.findViewById(R.id.languageName)
            val radio: RadioButton = view.findViewById(R.id.languageRadio)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_language, parent, false)
            return LanguageHolder(view)
        }

        override fun onBindViewHolder(holder: LanguageHolder, position: Int) {
            val lang = items[position]

            holder.name.text = lang.name
            holder.radio.isChecked = position == selectedIndex

            holder.itemView.setOnClickListener {
                val pos = holder.bindingAdapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    selectedIndex = pos
                    notifyItemRangeChanged(0, items.size)
                }
            }

            holder.radio.setOnClickListener {
                val pos = holder.bindingAdapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    selectedIndex = pos
                    notifyItemRangeChanged(0, items.size)
                }
            }
        }

        override fun getItemCount() = items.size
    }
}
