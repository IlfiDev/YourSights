package com.ilfidev.yoursights.UiElements

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.common.util.MapUtils
import com.ilfidev.yoursights.R
import com.ilfidev.yoursights.models.repository.MapRepository
import com.ilfidev.yoursights.utils.UtilsMap
import com.ilfidev.yoursights.viewModels.MapViewModel
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import kotlin.math.roundToInt


@Preview
@Composable
fun OsmdroidMapView() {
    val context = LocalContext.current
    val viewModel = MapViewModel(MapRepository())
    val density = LocalDensity.current
    val resources = context.resources
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            val mapView = MapView(context)
            mapView.setTileSource(TileSourceFactory.MAPNIK)
            mapView.minZoomLevel = 4.0
            mapView.setBuiltInZoomControls(true)
            mapView.setMultiTouchControls(true)
            val startPoint = GeoPoint(37.5, 55.5)
            val startMarker: Marker = Marker(mapView)
            startMarker.setTitle("Эрмитаж")
            val drawable = ContextCompat.getDrawable(context, R.drawable.ic_launcher_background)
//            startMarker.icon = UtilsMap.prepareDrawable(resources, drawable!!, "Эрмитаж", 10.0)
            startMarker.icon = ContextCompat.getDrawable(context, R.drawable.museum_big)
            startMarker.setPosition(startPoint)
            startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            mapView.overlays.add(startMarker)
            mapView
        }
    )
}
