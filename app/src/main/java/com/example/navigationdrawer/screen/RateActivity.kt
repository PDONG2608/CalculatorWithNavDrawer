package com.example.navigationdrawer.screen

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.example.navigationdrawer.R
import com.example.navigationdrawer.ThemeUtils
import com.example.navigationdrawer.databinding.ActivityRateBinding

class RateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRateBinding
    private lateinit var stars: List<ImageView>
    private var selectedRating = 0

    private lateinit var starDescriptions: List<Int>
    override fun onCreate(savedInstanceState: Bundle?) {
        ThemeUtils.setAppTheme(this)
        super.onCreate(savedInstanceState)
        binding = ActivityRateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        starDescriptions = listOf(
            R.string.terrible, R.string.bad, R.string.okay, R.string.good, R.string.excellent
        )
        stars = listOf(
            binding.star1, binding.star2, binding.star3, binding.star4, binding.star5
        )

        stars.forEachIndexed { index, imageView ->
            imageView.setOnClickListener {
                updateStars(index + 1)
            }
        }

        binding.rateConfirmButton.setOnClickListener {
            if (selectedRating == 0) {
                Toast.makeText(this, getString(R.string.please_select_rating), Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            Toast.makeText(
                this,
                getString(R.string.thanks_for_rate) + " " + binding.ratingLabel.text.toString() + ", " +
                        getString(R.string.we_will_do_our_best),
                Toast.LENGTH_SHORT
            ).show()
            resetDefaultView()
        }
    }

    private fun resetDefaultView() {
        binding.commentInput.text.clear()
        binding.commentInput.clearFocus()
    }

    private fun updateStars(selected: Int) {
        selectedRating = selected
        for (i in stars.indices) {
            stars[i].setImageResource(
                if (i < selected) R.drawable.start_green else R.drawable.star_white
            )
            stars[i].setColorFilter(
                if (i < selected) Color.parseColor("#00C569") else Color.parseColor("#CCCCCC")
            )
        }

        binding.ratingLabel.text = getString(starDescriptions[selected - 1])
    }
}