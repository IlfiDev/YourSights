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
import androidx.navigation.NavController
import com.ilfidev.yoursights.MainScreen
import com.ilfidev.yoursights.UiElements.RoutesScreen
import com.ilfidev.yoursights.UiElements.SearchScreen
import com.ilfidev.yoursights.UiElements.cards.SearchSightCard
import com.ilfidev.yoursights.models.data.MapPoint

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
//@Preview
@Composable
fun SearchScreen(navController: NavController) {
    val searchResults = listOf(MapPoint(), MapPoint(), MapPoint(), MapPoint(), MapPoint(), MapPoint(), MapPoint())
    var searchText by remember { mutableStateOf("Test") }
    Scaffold(content = {
            TextField(modifier = Modifier.fillMaxWidth(), value = searchText, onValueChange = {searchText = it})
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(all = 5.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp))
            {
                items(searchResults) { result ->
                    SearchSightCard(cardInfo = result, onClick = {
                        navController.navigate(RoutesScreen)
                    })
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(MainScreen) }) {
                
            }
        }
    )
        
}