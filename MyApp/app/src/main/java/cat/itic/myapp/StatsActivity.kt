package cat.itic.myapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class StatsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)

        val menuBtn = findViewById<ImageButton>(R.id.menuButton)
        val profileBtn = findViewById<ImageButton>(R.id.profileButton)

        menuBtn.setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
        }

        profileBtn.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        loadStats()
    }

    override fun onResume() {
        super.onResume()
        loadStats()
    }

    private fun loadStats() {

        val barChart = findViewById<BarChart>(R.id.barChart)
        val co2Text = findViewById<TextView>(R.id.co2TextView)

        val added = StatsManager.get(this, "wishlist_added").toFloat()
        val removed = StatsManager.get(this, "wishlist_removed").toFloat()

        val entries = listOf(
            BarEntry(0f, added),
            BarEntry(1f, removed)
        )

        val dataSet = BarDataSet(entries, "Wishlist Activity")
        dataSet.color = Color.CYAN
        dataSet.valueTextColor = Color.WHITE
        dataSet.valueTextSize = 14f

        val data = BarData(dataSet)
        barChart.data = data

        val labels = listOf("Added", "Removed")

        val xAxis = barChart.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.textColor = Color.WHITE
        xAxis.granularity = 1f

        barChart.axisLeft.textColor = Color.WHITE
        barChart.axisRight.isEnabled = false
        barChart.legend.textColor = Color.WHITE

        val description = Description()
        description.text = "Wishlist usage statistics"
        description.textColor = Color.WHITE
        barChart.description = description

        barChart.invalidate()

        val minutes = added + removed
        val consumoKWh = minutes * 0.05f / 60f
        val co2 = consumoKWh * 0.231f

        co2Text.text = "Estimated CO₂: %.3f kg".format(co2)
    }
}