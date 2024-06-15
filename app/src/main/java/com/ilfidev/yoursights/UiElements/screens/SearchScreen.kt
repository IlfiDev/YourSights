package com.ilfidev.yoursights.UiElements.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ilfidev.yoursights.MainScreen
import com.ilfidev.yoursights.R
import com.ilfidev.yoursights.UiElements.SearchScreen
import com.ilfidev.yoursights.UiElements.cards.SearchSightCard
import com.ilfidev.yoursights.models.data.MapPoint
import com.ilfidev.yoursights.viewModels.MapViewModel
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
//@Preview
@Composable
fun SearchScreen(viewModel: MapViewModel) {
    val searchResults = listOf(
    MapPoint(images = listOf(R.drawable.cremlino_moskovsky_kreml2, R.drawable.main_gate_to_the_kremlin, R.drawable.moscow_a_very_beautiful), position = GeoPoint(55.741556, 37.620028), city = "Москва", name = "Кремль",
        allData = "Моско́вский Кремль[1] — крепость в центре Москвы и древнейшая её часть, главный общественно-политический и историко-художественный комплекс города, официальная резиденция Президента Российской Федерации, вплоть до распада СССР в декабре 1991 года была официальной резиденцией Генерального секретаря ЦК КПСС (в 1990—1991 годах — Президента СССР). Одно из самых известных архитектурных сооружений в мире.\n" +
                "\n" +
                "Расположен на высоком левом берегу Москвы-реки — Боровицком холме, при впадении в неё реки Неглинной. В плане Кремль — неправильный треугольник площадью 27,5 гектара. Южная стена обращена к Москве-реке, северо-западная — к Александровскому саду, восточная — к Красной площади[2]. "),
        MapPoint(images = listOf(R.drawable.kremli_5), name = "Ростовский кремль", city = "Ростов")
    )
    var searchText by remember { mutableStateOf("Test") }
    var showCards by remember {
        mutableStateOf(false)
    }
    Scaffold(content = {
        Column() {
            TextField(modifier = Modifier.fillMaxWidth(), value = searchText, onValueChange = {
                searchText = it
                if (it.length >= 6) {
                    showCards = true
                }
            })
            if (showCards) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(all = 5.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp))
                {
                    items(searchResults) { result ->
                        SearchSightCard(cardInfo = result, onClick = {
                            viewModel.showRoutesSearch()
                        })
                    }
                }
            }
        }
        },
        floatingActionButton = {

        }
    )
        
}