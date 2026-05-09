package cat.itic.myapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.material.switchmaterial.SwitchMaterial
import java.util.Locale

class GeneralConfigurationActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var tts: TextToSpeech
    private lateinit var recognizer: SpeechRecognizer
    private var ttsReady = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_general_configuration)

        tts = TextToSpeech(this, this)
        recognizer = SpeechRecognizer.createSpeechRecognizer(this)

        val menuBtn = findViewById<ImageButton>(R.id.menuButton)
        val profileBtn = findViewById<ImageButton>(R.id.profileButton)
        val switchVoice = findViewById<SwitchMaterial>(R.id.switchVoice)
        val voiceButton = findViewById<ImageButton>(R.id.voiceButton)

        val prefs = getSharedPreferences("settings", MODE_PRIVATE)

        switchVoice.isChecked = prefs.getBoolean("voice_enabled", false)

        switchVoice.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("voice_enabled", isChecked).apply()
        }

        val recognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US")
        }

        recognizer.setRecognitionListener(object : RecognitionListener {
            override fun onResults(results: Bundle?) {
                val text = results
                    ?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    ?.get(0)
                    ?.lowercase()

                if (prefs.getBoolean("voice_enabled", false) && text != null) {
                    handleVoiceCommand(text)
                }
            }

            override fun onError(error: Int) {}
            override fun onReadyForSpeech(params: Bundle?) {}
            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() {}
            override fun onPartialResults(partialResults: Bundle?) {}
            override fun onEvent(eventType: Int, params: Bundle?) {}
        })

        voiceButton.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), 1)
                return@setOnClickListener
            }
            recognizer.startListening(recognizerIntent)
        }

        menuBtn.setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
        }

        profileBtn.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        findViewById<Button>(R.id.logoutButton)
            .setOnClickListener {
                speak("Logging out", prefs)
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }

        findViewById<Button>(R.id.closeAppButton)
            .setOnClickListener {
                speak("Closing application", prefs)
                finishAffinity()
            }
    }

    private fun handleVoiceCommand(command: String) {
        val prefs = getSharedPreferences("settings", MODE_PRIVATE)

        when {
            command.contains("profile") -> {
                speak("Opening profile", prefs)
                startActivity(Intent(this, ProfileActivity::class.java))
            }

            command.contains("menu") -> {
                speak("Opening menu", prefs)
                startActivity(Intent(this, MenuActivity::class.java))
            }

            command.contains("back") -> {
                speak("Going back", prefs)
                onBackPressedDispatcher.onBackPressed()
            }

            command.contains("logout") -> {
                speak("Logging out", prefs)
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }

            command.contains("close") -> {
                speak("Closing application", prefs)
                finishAffinity()
            }

            else -> {
                speak("Command not recognized", prefs)
            }
        }
    }

    private fun speak(text: String, prefs: android.content.SharedPreferences) {
        if (prefs.getBoolean("voice_enabled", false) && ttsReady) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts.language = Locale.US
            ttsReady = true
        }
    }

    override fun onDestroy() {
        recognizer.destroy()
        tts.stop()
        tts.shutdown()
        super.onDestroy()
    }
}