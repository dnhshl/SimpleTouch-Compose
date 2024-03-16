package com.example.simpletoucn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.simpletoucn.ui.screens.MyApp
import com.example.simpletoucn.ui.theme.SimpleTouchTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleTouchTheme {
                MyApp()
            }
        }
    }
}

