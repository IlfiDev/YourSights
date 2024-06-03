package com.ilfidev.yoursights.UiElements

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.common.util.MapUtils
import com.ilfidev.yoursights.R
import com.ilfidev.yoursights.models.data.MapPoint
import com.ilfidev.yoursights.models.repository.MapRepository
import com.ilfidev.yoursights.utils.UtilsMap
import com.ilfidev.yoursights.viewModels.MapViewModel
import kotlinx.coroutines.launch
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import java.util.UUID
import kotlin.math.roundToInt


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun OsmdroidMapView() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            val mapView = MapView(context)
            mapView.setTileSource(TileSourceFactory.MAPNIK)
            mapView.minZoomLevel = 4.0
            mapView.setBuiltInZoomControls(true)
            mapView.setMultiTouchControls(true)
            val startPoint = GeoPoint(55.0, 37.0)
            val startMarker: Marker = Marker(mapView)
            startMarker.setTitle("Эрмитаж")
            val drawable = ContextCompat.getDrawable(context, R.drawable.ic_launcher_background)
//            startMarker.icon = UtilsMap.prepareDrawable(resources, drawable!!, "Эрмитаж", 10.0)
            startMarker.icon = ContextCompat.getDrawable(context, R.drawable.museum_big)
            startMarker.setPosition(startPoint)
            startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            startMarker.setOnMarkerClickListener { marker, mapView ->
                showBottomSheet = true
                false

            }
            mapView.overlays.add(startMarker)
            mapView
        }
    )
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetState
        ) {
            // Sheet content
            Button(onClick = {
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        showBottomSheet = false
                    }
                }
            }) {
                Text("Скрыть информацию")

            }
            Column(modifier = Modifier.fillMaxSize()) {
                for (i in 0..25) {
                    Text("Test")
                }
            }
        }
    }
}

@Preview
@Composable
fun SearchSightCardPreview() {
    SearchSightCard(cardInfo = MapPoint(UUID.randomUUID(), "Test", "Moscow", GeoPoint(0.0, 0.0), "aa", "aaa"))
}


@Composable
fun SearchSightCard(cardInfo: MapPoint) {
    Card(elevation = CardDefaults.elevatedCardElevation(10.dp)) {
        Image(
            modifier = Modifier
                .size(200.dp)
                .clip(RoundedCornerShape(16.dp)),
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "test",
            )
        Text(cardInfo.name)
        Text(cardInfo.city)
    }
}


@Composable
fun MainScreenNavigationBar() {

    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf("Search", "Add Route")
    var icon: ImageVector
    BottomAppBar {
        NavigationBar {
            items.forEachIndexed { index, item ->
                icon = when (index) {
                    0 -> Icons.Outlined.Search

                    1 -> Icons.Outlined.Create

                    else -> Icons.Filled.Search
                }
                NavigationBarItem(
                    icon = { Icon(icon, contentDescription = item) },
                    label = { Text(item) },
                    selected = selectedItem == index,
                    onClick = { selectedItem = index }
                )
            }
        }
    }
}
