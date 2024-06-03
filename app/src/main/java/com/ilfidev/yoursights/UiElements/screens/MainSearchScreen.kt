package com.ilfidev.yoursights.UiElements.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ilfidev.yoursights.R
import com.ilfidev.yoursights.UiElements.MainScreenNavigationBar
import com.ilfidev.yoursights.UiElements.OsmdroidMapView
import com.ilfidev.yoursights.UiElements.animations.AnimateSlideInOut
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainSearchScreen() {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    var isDrawerOpen by remember { mutableStateOf(false) }
    val items = listOf(Icons.Default.Close, Icons.Default.Clear, Icons.Default.Call)
    val selectedItem = remember { mutableStateOf(items[0]) }
    var showSearch by remember { mutableStateOf(false) }
    var visible by remember { mutableStateOf(true) }
    ModalNavigationDrawer(

        gesturesEnabled = isDrawerOpen,
        drawerState = drawerState,
        drawerContent = {

            ModalDrawerSheet {
                Spacer(Modifier.height(12.dp))
                items.forEach { item ->
                    NavigationDrawerItem(
                        icon = { Icon(item, contentDescription = null) },
                        label = { Text(item.name) },
                        selected = item == selectedItem.value,
                        onClick = {
                            scope.launch {
                                drawerState.close()
                                isDrawerOpen = false
                                Log.i("DrawerState", "$isDrawerOpen")
                            }
                            selectedItem.value = item
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }

        },
        content = {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                "Simple TopAppBar",
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = { scope.launch {
                                drawerState.open()
                                isDrawerOpen = true
                                Log.i("DrawerState", "$isDrawerOpen")
                            } }) {
                                Icon(
                                    imageVector = Icons.Filled.Menu,
                                    contentDescription = "Localized description"
                                )
                            }
                        }
                    )
                },
                content = {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        AnimateSlideInOut(visible = showSearch) {
                            SearchScreen()
                        }
                        AnimateSlideInOut(visible = visible) {
                            OsmdroidMapView()
                        }
//                        if (showSearch) {
//                        } else {
//                        }
                    }
                },
                bottomBar = {
                    AnimateSlideInOut(visible = visible) {
                        MainScreenNavigationBar()
                    }
                },
                floatingActionButton = {
                    if (!showSearch) {
                        FloatingActionButton(onClick = {
                            showSearch = true
                            visible = false
                        }) {
                            Icon(painter = painterResource(id = R.drawable.baseline_search_24), contentDescription = "test" )
                        }
                    }
                },
            )
        }
    )
}

