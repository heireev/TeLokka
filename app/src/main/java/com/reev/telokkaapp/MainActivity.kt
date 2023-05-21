package com.reev.telokkaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.room.Room
import com.reev.telokkaapp.data.source.local.room.planplacedatabase.PlanningDatabase
import com.reev.telokkaapp.ui.theme.TeLokkaAppTheme

class MainActivity : ComponentActivity() {
    private lateinit var database: PlanningDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = Room.databaseBuilder(
            applicationContext,
            PlanningDatabase::class.java,
            "te_lokka_db"
        ).build()

        setContent {
            TeLokkaAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TeLokkaApp()
                }
            }
        }
    }
}