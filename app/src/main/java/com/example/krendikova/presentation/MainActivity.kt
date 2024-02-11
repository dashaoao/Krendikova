package com.example.krendikova.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.krendikova.R
import com.example.krendikova.presentation.films.FilmsFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.main_container,
                FilmsFragment.newInstance(FilmsFragment.Companion.Type.POPULAR)
            )
            .commit()
    }
}