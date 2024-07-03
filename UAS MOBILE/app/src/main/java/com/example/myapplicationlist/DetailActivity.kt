package com.example.myapplicationlist

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val detailImageView: ImageView = findViewById(R.id.detailImageView)
        val detailTextView: TextView = findViewById(R.id.detailTextView)

        val character = intent.getParcelableExtra<Character>("character")

        character?.let {
            Glide.with(this)
                .load(it.imageUrl)
                .into(detailImageView)

            detailTextView.text = buildString {
                append("Name: ${it.name}\n\n")
                append("Films: ${it.films.joinToString(", ")}\n\n")
                append("Short Films: ${it.shortFilms.joinToString(", ")}\n\n")
                append("TV Shows: ${it.tvShows.joinToString(", ")}\n\n")
                append("Video Games: ${it.videoGames.joinToString(", ")}\n\n")
                append("Park Attractions: ${it.parkAttractions.joinToString(", ")}\n\n")
                append("Allies: ${it.allies.joinToString(", ")}\n\n")
                append("Enemies: ${it.enemies.joinToString(", ")}\n\n")
                append("Source URL: ${it.sourceUrl}\n\n")
            }
        }
    }
}
