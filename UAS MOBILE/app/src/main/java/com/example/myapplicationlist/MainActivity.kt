package com.example.myapplicationlist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CharacterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchCharacters()
    }

    private fun fetchCharacters() {
        lifecycleScope.launch {
            try {
                val response = RetrofitInstance.api.getCharacters()
                adapter = CharacterAdapter(response.data) { character ->
                    val intent = Intent(this@MainActivity, DetailActivity::class.java)
                    intent.putExtra("character", character)
                    startActivity(intent)
                }
                recyclerView.adapter = adapter
            } catch (e: Exception) {
                Log.e("MainActivity", "Failed to load characters", e)
                Toast.makeText(this@MainActivity, getString(R.string.failed_to_load_characters), Toast.LENGTH_SHORT).show()
            }
        }
    }
}

interface DisneyApi {
    @GET("character")
    suspend fun getCharacters(): CharacterResponse
}

object RetrofitInstance {
    val api: DisneyApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.disneyapi.dev/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DisneyApi::class.java)
    }
}

data class CharacterResponse(
    val data: List<Character>
)
