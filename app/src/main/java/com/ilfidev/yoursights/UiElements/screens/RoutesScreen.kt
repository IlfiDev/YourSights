package com.ilfidev.yoursights.UiElements.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ilfidev.yoursights.UiElements.cards.RouteStopsCard
import com.ilfidev.yoursights.models.data.MapPoint
import com.ilfidev.yoursights.models.data.Route


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoutesScreen(route: Route) {
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
        // Sheet content
//        Button(onClick = {
//            scope.launch { sheetState.hide() }.invokeOnCompletion {
//                if (!sheetState.isVisible) {
//                    showBottomSheet = false
//                }
//            }
//        }) {
//            Text("Скрыть информацию")
//
//        }
        LazyColumn {

            items(route.stops) { result ->
                RouteStopsCard(cardInfo = result)
            }
        }
    }


@Preview
@Composable
fun RouteScreenPreview() {

    val searchResults = listOf(MapPoint(), MapPoint(), MapPoint(), MapPoint(), MapPoint(), MapPoint(), MapPoint())
    val route = Route(stops = searchResults)
    RoutesScreen(route = route)
}
