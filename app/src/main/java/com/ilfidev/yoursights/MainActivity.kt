@file:OptIn(ExperimentalMaterial3Api::class)

package com.ilfidev.yoursights

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ilfidev.yoursights.UiElements.LoginScreen
import com.ilfidev.yoursights.UiElements.OsmdroidMapView
import com.ilfidev.yoursights.UiElements.RegisterScreen
import com.ilfidev.yoursights.UiElements.screens.LoginScreen
import com.ilfidev.yoursights.UiElements.screens.MainSearchScreen
import com.ilfidev.yoursights.UiElements.screens.RegisterScreen
import com.ilfidev.yoursights.UiElements.screens.RoutesScreen
import com.ilfidev.yoursights.models.data.MapPoint
import com.ilfidev.yoursights.models.repository.MapOnlineRepository
import com.ilfidev.yoursights.ui.theme.YourSightsTheme
import com.ilfidev.yoursights.viewModels.MapViewModel
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import org.osmdroid.config.Configuration

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.INTERNET), 0)
        }
        super.onCreate(savedInstanceState)
        setContent {
            YourSightsTheme {
                val mapViewModel = viewModel<MapViewModel>(
                    factory = object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            return MapViewModel(MapOnlineRepository()) as T
                        }
                    }
                )
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = LoginScreen ) {
                    composable<MainScreen> {
                        MainSearchScreen(mapViewModel, navController)
                    }
                    composable<LoginScreen> {
                        LoginScreen(navController)
                    }
                    composable<RegisterScreen> {
                        com.ilfidev.yoursights.UiElements.screens.RegisterScreen(navController = navController)
                    }
//                    composable<RoutesScreen> {
//                        com.ilfidev.yoursights.UiElements.screens.RoutesScreen(stops = listOf(
//                            MapPoint()
//                        ))
//                    }
                }
                // A surface container using the 'background' color from the theme
            }
        }
        val ctx = applicationContext
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx))
        Configuration.getInstance().userAgentValue = "MapApp"

    }
}

@Serializable
object MainScreen