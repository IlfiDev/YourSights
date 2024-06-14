package com.ilfidev.yoursights.UiElements

import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.ilfidev.yoursights.R
import com.ilfidev.yoursights.models.data.MapPoint
import com.ilfidev.yoursights.viewModels.MapViewModel
import kotlinx.coroutines.launch
import org.osmdroid.bonuspack.routing.OSRMRoadManager
import org.osmdroid.bonuspack.routing.RoadManager
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import java.util.UUID


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OsmdroidMapView(viewModel: MapViewModel) {

    val route by viewModel.uiState.collectAsState()
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            val policy = ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
            Configuration.getInstance().userAgentValue = "MyUserAgent/1.0"
            val roadManager = OSRMRoadManager(context, "MyUserAgent/1.0")
            roadManager.setMean(OSRMRoadManager.MEAN_BY_FOOT)
            val road = roadManager.getRoad(route.stops.map { it.position } as ArrayList<GeoPoint>?)
            val roadOverlay = RoadManager.buildRoadOverlay(road)
            val mapView = MapView(context)
            mapView.overlays.add(roadOverlay)
            mapView.setTileSource(TileSourceFactory.MAPNIK)
            mapView.minZoomLevel = 4.0
            mapView.setBuiltInZoomControls(true)
            mapView.setMultiTouchControls(true)
            val drawable = ContextCompat.getDrawable(context, R.drawable.ic_launcher_background)
//            startMarker.icon = UtilsMap.prepareDrawable(resources, drawable!!, "Эрмитаж", 10.0)
            var counter = 0
            route.stops.forEachIndexed { index, mapPoint ->
                Log.i("Test Route", "$counter")
                counter += 1
                val startMarker: Marker = Marker(mapView)
                startMarker.setTitle("Эрмитаж")
                startMarker.icon = ContextCompat.getDrawable(context, R.drawable.point_flag)
                startMarker.setPosition(mapPoint.position)
                startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                startMarker.setOnMarkerClickListener { marker, mapView ->
                    viewModel.showPointInfo()
                    false

                }
                mapView.overlays.add(startMarker)
            }
            mapView
        }
    )

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
