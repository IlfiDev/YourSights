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
import com.ilfidev.yoursights.UiElements.dialogs.AnotherDialog
import com.ilfidev.yoursights.models.data.MapPoint
import com.ilfidev.yoursights.models.data.Route
import com.ilfidev.yoursights.viewModels.MapViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.osmdroid.bonuspack.routing.OSRMRoadManager
import org.osmdroid.bonuspack.routing.Road
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
    var route: Route = Route()
    var key by remember { mutableStateOf(0) }
    val listOfMarkers = mutableListOf<Marker>()
    val listOfRoads = mutableListOf<Road>()
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            val mapView = MapView(context)
            val mapController = mapView.controller
            mapController.setZoom(8)
            mapController.animateTo(GeoPoint(55.5, 37.5))
            val policy = ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
            Configuration.getInstance().userAgentValue = "MyUserAgent/1.0"
            val roadManager = OSRMRoadManager(context, "MyUserAgent/1.0")
            roadManager.setMean(OSRMRoadManager.MEAN_BY_FOOT)
            mapView.setHorizontalMapRepetitionEnabled(false)
            mapView.setVerticalMapRepetitionEnabled(false)

            CoroutineScope(Dispatchers.IO).launch {
                viewModel.uiState.collect { value ->
                    route = value

                    val road = roadManager.getRoad(route.stops.map { it.position } as ArrayList<GeoPoint>?)
                    val roadOverlay = RoadManager.buildRoadOverlay(road)
                    mapView.overlays.add(roadOverlay)
                    mapView.setTileSource(TileSourceFactory.MAPNIK)
                    mapView.minZoomLevel = 4.0
                    mapView.setBuiltInZoomControls(true)
                    mapView.setMultiTouchControls(true)
                    val drawable = ContextCompat.getDrawable(context, R.drawable.ic_launcher_background)
//            startMarker.icon = UtilsMap.prepareDrawable(resources, drawable!!, "Эрмитаж", 10.0)
                    var counter = 0
                    listOfMarkers.forEach { item ->
                        item.remove(mapView)
                    }

                    route.stops.forEachIndexed { index, mapPoint ->
                        mapView.invalidate()
                        Log.i("Test Route", "$counter")
                        counter += 1
                        val startMarker: Marker = Marker(mapView)
                        startMarker.setTitle(index.toString())
                        startMarker.icon = ContextCompat.getDrawable(context, R.drawable.point_flag)
                        startMarker.setPosition(mapPoint.position)
                        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                        startMarker.setOnMarkerClickListener { marker, mapView ->
                            viewModel.showPointInfo(route.stops[index])
                            Log.i("Point", route.stops[index].toString())
                            false

                        }
                        mapView.overlays.add(startMarker)
                        listOfMarkers.add(startMarker)
                    }
                }
            }
            mapView
        }
    )

}



@Composable
fun MainScreenNavigationBar(viewModel: MapViewModel) {

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
                    onClick = {
                        selectedItem = index

                        viewModel.setScreenTo(index)
                    }
                )
            }
        }
    }
}


@Composable
fun OsmdroidEditView(viewModel: MapViewModel) {
    var route: Route = Route()
    var key by remember { mutableStateOf(0) }
    val listOfMarkers = mutableListOf<Marker>()
    val listOfRoads = mutableListOf<Road>()
    val context = LocalContext.current
//    lateinit var roadManager: OSRMRoadManager
    val mapView = MapView(context)
    var showDialog by remember { mutableStateOf(false)}
    val marker = Marker(mapView)
    val gallery =  MapPoint(images = listOf(R.drawable.img_20190614_110857_largejpg, R.drawable.photo5jpg), position = GeoPoint(55.747224, 37.605240),
        name = "Государственный музей изобразительных искусств имени А. С. Пушкина", city = "Москва",
        allData = "Пу́шкинский музе́й — российский государственный художественный музей в Москве, одно из крупнейших в современной России собраний западного искусства. Созданный по инициативе историка и археолога, профессора Московского университета И. В. Цветаева, музей был открыт в 1912 году под названием «Музей изящных искусств имени императора Александра III при Императорском Московском университете». Главное здание музея было построено по проекту архитектора Романа Клейна в неоклассическом стиле в виде античного храма. Изначально музей был задуман как учебный, однако после революции 1917 года учреждение было преобразовано в Государственный музей изобразительных искусств. В 1937 году музей получил имя поэта Александра Пушкина. В 1991 году ГМИИ имени Пушкина внесли в Государственный свод особо ценных объектов культурного наследия народов Российской Федерации.\n" +
                "\n" +
                "По состоянию на 2018 год экспозиция состоит из более чем 670 тысяч предметов и включает в себя коллекцию слепков с античных статуй, художественные произведения, археологические находки, а также собрание предметов из Древнего Египта и Древнего Рима. В 2018 году музей посетили 1,3 млн человек, благодаря чему он занял 47-е место в числе самых посещаемых художественных музеев мира.\n",
    )
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->

            val allPoints = listOf(MapPoint())
            val policy = ThreadPolicy.Builder().permitAll().build()
            val mapController = mapView.controller
            mapController.setZoom(8)
            mapController.animateTo(GeoPoint(55.5, 37.5))
            StrictMode.setThreadPolicy(policy)
            Configuration.getInstance().userAgentValue = "MyUserAgent/1.0"
            val roadManager = OSRMRoadManager(context, "MyUserAgent/1.0")
            roadManager.setMean(OSRMRoadManager.MEAN_BY_FOOT)

            mapView.setTileSource(TileSourceFactory.MAPNIK)
            mapView.minZoomLevel = 4.0
            mapView.setBuiltInZoomControls(true)
            mapView.setMultiTouchControls(true)
            mapView.setOnTouchListener { v, event ->
                mapView.invalidate()
                Log.i("MapPoint", (mapView.mapCenter as GeoPoint).toString())
                marker.position = (mapView.mapCenter as GeoPoint)
                false
            }
            marker.icon = ContextCompat.getDrawable(context, R.drawable.baseline_clear_24)
            mapView.overlays.add(marker)

            allPoints.forEach { item ->

            }
            val galleryMarer = Marker(mapView)
            galleryMarer.icon = ContextCompat.getDrawable(context, R.drawable.museum_big)
            galleryMarer.position = gallery.position
            galleryMarer.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)

            galleryMarer.setOnMarkerClickListener { marker, mapView ->
//                viewModel.setNewPoint()
                showDialog = true
                false
            }
            mapView.overlays.add(galleryMarer)

            CoroutineScope(Dispatchers.IO).launch {
                viewModel.points.collect { value ->
                    value.forEachIndexed { index, item ->

                        if (index != 2) {
                            val startMarker: Marker = Marker(mapView)
//                        startMarker.setTitle(index.toString())
                            startMarker.icon = ContextCompat.getDrawable(context, R.drawable.baseline_pin_drop_24)
                            startMarker.setPosition(item.position)
                            startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)

                            mapView.overlays.add(startMarker)
                        }
                        Log.i("Index", index.toString())
                        if (value.size >= 3) {
                            val points = arrayListOf(GeoPoint(55.5, 37.5),
                                GeoPoint(55.6, 37.6),gallery.position
                            )
                            val road = roadManager.getRoad(points as java.util.ArrayList<GeoPoint>?)
                            val roadOverlay = RoadManager.buildRoadOverlay(road)
                            mapView.overlays.add(roadOverlay)
                        }
                    }
                }
            }
//            CoroutineScope(Dispatchers.IO).launch {
//                viewModel.uiState.collect { value ->
//                    route = value
//
//                    val road = roadManager.getRoad(route.stops.map { it.position } as ArrayList<GeoPoint>?)
//                    val roadOverlay = RoadManager.buildRoadOverlay(road)
//                    mapView.overlays.add(roadOverlay)
//                    val drawable = ContextCompat.getDrawable(context, R.drawable.ic_launcher_background)
////            startMarker.icon = UtilsMap.prepareDrawable(resources, drawable!!, "Эрмитаж", 10.0)
//                    var counter = 0
//                    listOfMarkers.forEach { item ->
//                        item.remove(mapView)
//                    }
//
//                    route.stops.forEachIndexed { index, mapPoint ->
//                        mapView.invalidate()
//                        Log.i("Test Route", "$counter")
//                        counter += 1
//                        val startMarker: Marker = Marker(mapView)
//                        startMarker.setTitle(index.toString())
//                        startMarker.icon = ContextCompat.getDrawable(context, R.drawable.point_flag)
//                        startMarker.setPosition(mapPoint.position)
//                        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
//                        startMarker.setOnMarkerClickListener { marker, mapView ->
//                            viewModel.showPointInfo(route.stops[index])
//                            Log.i("Point", route.stops[index].toString())
//                            false
//
//                        }
//                        mapView.overlays.add(startMarker)
//                        listOfMarkers.add(startMarker)
//                    }
//                }
//            }
            mapView
        }
    )
    if (showDialog) {
        AnotherDialog(cardInfo = gallery, onDismissRequest = {
            showDialog = false
            viewModel.setNewPoint()
        })

    }

}
