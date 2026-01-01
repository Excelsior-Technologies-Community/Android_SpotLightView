package com.ext.android_spotlightview




import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ext.android_spot_light_view.SpotlightView

class MainActivity : AppCompatActivity() {

    private lateinit var spotlightView: SpotlightView
    private lateinit var tvTitle: TextView
    private lateinit var tvClickHere: TextView
    private lateinit var tvDescription: TextView

    private var currentStep = 0
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setupListeners()
    }

    private fun initViews() {
        spotlightView = findViewById(R.id.spotlightView)
        tvTitle = findViewById(R.id.tvTitle)
        tvClickHere = findViewById(R.id.tvClickHere)
        tvDescription = findViewById(R.id.tvDescription)
    }

    private fun setupListeners() {
        tvClickHere.setOnClickListener {
            startTutorialTour()
        }

        spotlightView.setOnClickListener {
            handleSpotlightClick()
        }
    }

    private fun startTutorialTour() {
        currentStep = 0
        showNextStep()
    }

    private fun showNextStep() {
        when (currentStep) {
            0 -> {
                // Step 1: Highlight "Click to Spotlight" text
                // This spotlight is already configured in XML with:
                // - titleText="Start Here"
                // - descriptionText="This is the clickable text\nthat starts the spotlight demo"
                // - overlayColor="#DD000000"
                // - spotlightShape="circle"
                // Just call showSpotlight to display it
                spotlightView.showSpotlight(tvClickHere)
            }
            1 -> {
                // Step 2: Highlight Title - Configured programmatically in Kotlin
                spotlightView.apply {
                    setTitleText("App Title")
                    setDescriptionText("This is the main title\nof your application")
                    setSpotlightShape(SpotlightView.SHAPE_ROUNDED_RECTANGLE)
                    setOverlayColor(Color.parseColor("#CC1A237E"))
                    setTitleTextColor(Color.parseColor("#FFEB3B"))
                    setDescriptionTextColor(Color.parseColor("#FFF9C4"))
                    setSpotlightPadding(30f)
                    showSpotlight(tvTitle)
                }
            }
            2 -> {
                // Step 3: Highlight Description - Also configured programmatically
                spotlightView.apply {
                    setTitleText("Description Area")
                    setDescriptionText("Read important information\nand instructions here")
                    setSpotlightShape(SpotlightView.SHAPE_RECTANGLE)
                    setOverlayColor(Color.parseColor("#DD004D40"))
                    setTitleTextColor(Color.parseColor("#4CAF50"))
                    setDescriptionTextColor(Color.parseColor("#C8E6C9"))
                    setSpotlightPadding(40f)
                    showSpotlight(tvDescription)
                }
            }
            else -> {
                // Tour complete
                spotlightView.hideSpotlight()
                resetSpotlightStyles()
                return
            }
        }
    }

    private fun handleSpotlightClick() {
        if (currentStep < 3) {
            currentStep++
            handler.postDelayed({
                showNextStep()
            }, 300)
        }
    }

    private fun resetSpotlightStyles() {
        spotlightView.apply {
            setOverlayColor(Color.parseColor("#DD000000"))
            setTitleTextColor(Color.WHITE)
            setDescriptionTextColor(Color.parseColor("#E0E0E0"))
            setSpotlightShape(SpotlightView.SHAPE_CIRCLE)
            setSpotlightPadding(24f)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
}