package com.ilfidev.yoursights.UiElements.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.ilfidev.yoursights.UiElements.cards.RouteCard
import com.ilfidev.yoursights.models.data.Route
import com.ilfidev.yoursights.viewModels.MapViewModel


@Composable
fun RouteSearchScreen(routes: List<Route>, viewModel: MapViewModel) {
    Column() {
        Text("Маршруты, куда входит Кремль")

        LazyColumn {
            items(routes) { result ->
                RouteCard(route = result, onClick = {
                    viewModel.showRouteOnMap(result)
                    viewModel.showRoute()
                })
            }
        }
    }
}