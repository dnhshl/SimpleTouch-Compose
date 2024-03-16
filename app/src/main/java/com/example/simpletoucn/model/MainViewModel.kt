package com.example.simpletoucn.model

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.material3.SnackbarDuration
import androidx.compose.ui.graphics.Color
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpletoucn.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

val Context.dataStore by preferencesDataStore(name = "settings")


data class SnackbarState(
    val message: String,
    val duration: SnackbarDuration = SnackbarDuration.Short
)


@Serializable
data class ScoreListItem(
    val radius: Float = 1f,
    val nClicks: Int = 0,
    val time: Long = 0L,
) {
    val score: Float = time.toFloat() / nClicks

    override fun toString(): String {
        return "%.1f ms/Klick bei %d Klicks mit Radius %d".format(score, nClicks, radius.toInt())
    }
}


class MainViewModel(application: Application): AndroidViewModel(application) {

    var stringResourceVariable = UiText.StringResource(R.string.scoreListEntry)

    private val dataStore = application.dataStore

    private val scoreListKey = stringPreferencesKey("score_list_key")

    val scoreList = dataStore.data
        .map { preferences ->
            // Liste als String aus dem datastore auslesen
            val scoreListAsString = preferences[scoreListKey] ?: "[]"
            // dekodieren und als Listendatenstruktur bereitstellen
            Json.decodeFromString<List<ScoreListItem>>(scoreListAsString)
        }

    // Ein neues todo hinzufügen oder ein bestehendes todo ersetzen
    fun addToList(scoreItem: ScoreListItem) {
        viewModelScope.launch {
            dataStore.edit { preferences ->
                // aktualle Liste aus Datastore lesen (kodiert als String)
                val currentListAsString = preferences[scoreListKey] ?: "[]"
                // aktuelle Liste dekodieren
                val currentList = Json.decodeFromString<MutableList<ScoreListItem>>(currentListAsString)
                // aktuellen Score zur Liste hinzufügen
                currentList.add(scoreItem)
                // Liste sortieren und auf Top 20 begrenzen
                val sortedList = currentList.sortedBy { it.score }.take(20)
                // neue Liste kodieren und in Datastore schreiben
                preferences[scoreListKey] = Json.encodeToString<List<ScoreListItem>>(sortedList)
            }
        }
    }



    private val circleColorKey = stringPreferencesKey("circle_color_key")
    val circleColor = dataStore.data
        .map { preferences ->
            val hexColor = preferences[circleColorKey] ?: Color.Red.toHexString()
            Log.i(">>>","hexColor from Datastore $hexColor")
            Color(android.graphics.Color.parseColor(hexColor))
        }

    fun setCircleColor(color: Color) {
        viewModelScope.launch {
            dataStore.edit { preferences ->
                preferences[circleColorKey] = color.toHexString()
            }
        }
    }



    private val backgroundColorKey = stringPreferencesKey("background_color_key")
    val backgroundColor = dataStore.data
        .map { preferences ->
            val hexColor = preferences[backgroundColorKey] ?: Color.Red.toHexString()
            Log.i(">>>","hexColor from Datastore $hexColor")
            Color(android.graphics.Color.parseColor(hexColor))
        }
    fun setBackgroundColor(color: Color) {
        viewModelScope.launch {
            dataStore.edit { preferences ->
                preferences[backgroundColorKey] = color.toHexString()
            }
        }
    }

    private val circleRadiusKey = floatPreferencesKey("circle_radius_key")
    val circleRadius = dataStore.data
        .map { preferences ->
            preferences[circleRadiusKey] ?: 50f
        }
        //.onStart { emit(-1f) }
    fun setCircleRadius(value: Float) {
        viewModelScope.launch {
            dataStore.edit { preferences ->
                preferences[circleRadiusKey] = value
            }
        }
    }

    private val numberClicksKey = intPreferencesKey("number_clicks_key")
    val numberClicks = dataStore.data
        .map { preferences ->
            preferences[numberClicksKey] ?: 5
        }
    fun setNumberClicks(value: Int) {
        viewModelScope.launch {
            dataStore.edit { preferences ->
                preferences[numberClicksKey] = value
            }
        }
    }

    // Definition im ViewModel
    private var _introScreen = MutableStateFlow(true)
    val introScreen: StateFlow<Boolean> = _introScreen

    fun disableIntroScreen() {
        _introScreen.value = false
    }





    // Snackbar

    private val _snackbarState = MutableStateFlow<SnackbarState?>(null)
    val snackbarState: StateFlow<SnackbarState?> = _snackbarState.asStateFlow()

    fun showSnackbar(
        message: String,
        duration: SnackbarDuration = SnackbarDuration.Short
    ) {
        _snackbarState.value = SnackbarState(message, duration)
    }

    fun dismissSnackbar() {
        Log.i(">>>", "Snackbar dismissed")
        _snackbarState.value = null
    }
}

