package com.example.krendikova.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
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