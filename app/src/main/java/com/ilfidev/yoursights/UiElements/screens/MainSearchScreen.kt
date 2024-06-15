package com.ilfidev.yoursights.UiElements.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ilfidev.yoursights.R
import com.ilfidev.yoursights.UiElements.MainScreenNavigationBar
import com.ilfidev.yoursights.UiElements.OsmdroidEditView
import com.ilfidev.yoursights.UiElements.OsmdroidMapView
import com.ilfidev.yoursights.UiElements.animations.AnimateSlideInOut
import com.ilfidev.yoursights.UiElements.cards.RouteStopsCard
import com.ilfidev.yoursights.UiElements.dialogs.DialogWithImage
import com.ilfidev.yoursights.UiElements.dialogs.SendRoute
import com.ilfidev.yoursights.models.data.MapPoint
import com.ilfidev.yoursights.models.data.Route
import com.ilfidev.yoursights.viewModels.MapViewModel
import com.ilfidev.yoursights.viewModels.MapViewModel.SheetStates
import kotlinx.coroutines.launch
import org.osmdroid.util.GeoPoint


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainSearchScreen(viewModel: MapViewModel, navController: NavController) {
    val context = LocalContext.current
    var showBottomSheet by remember { mutableStateOf(false) }
//    val sheetState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    var isDrawerOpen by remember { mutableStateOf(false) }
    val items = listOf(R.drawable.baseline_person_24, R.drawable.baseline_map_24, R.drawable.baseline_settings_24)
    val itmesText = listOf("Профиль", "Мои маршруты", "Настройки")
    val selectedItem = remember { mutableStateOf(items[0]) }
    var showSearch by remember { mutableStateOf(false) }
    var visible by remember { mutableStateOf(true) }
    var openDialog by remember { mutableStateOf(false) }
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(skipHiddenState = false)
    )
    var showEndDialog by remember { mutableStateOf(false) }
//    val scope = rememberCoroutineScope()
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {

            val data by viewModel.showDataType.collectAsState()
//
//            if (data == SheetStates.Close) {
//                showBottomSheet = false
//            } else {
//                showBottomSheet = true
//            }
//            RouteStopsCard(cardInfo = MapPoint())
            Log.i("DATA", data.toString())
//            RoutesScreen(stops = listOf(MapPoint()))

            val mainRoute = Route(
                stops = listOf(
                    MapPoint(images = listOf(R.drawable.cremlino_moskovsky_kreml2, R.drawable.main_gate_to_the_kremlin, R.drawable.moscow_a_very_beautiful), position = GeoPoint(55.741556, 37.620028), city = "Москва", name = "Кремль",
                        allData = "Моско́вский Кремль[1] — крепость в центре Москвы и древнейшая её часть, главный общественно-политический и историко-художественный комплекс города, официальная резиденция Президента Российской Федерации, вплоть до распада СССР в декабре 1991 года была официальной резиденцией Генерального секретаря ЦК КПСС (в 1990—1991 годах — Президента СССР). Одно из самых известных архитектурных сооружений в мире.\n" +
                                "\n" +
                                "Расположен на высоком левом берегу Москвы-реки — Боровицком холме, при впадении в неё реки Неглинной. В плане Кремль — неправильный треугольник площадью 27,5 гектара. Южная стена обращена к Москве-реке, северо-западная — к Александровскому саду, восточная — к Красной площади[2]. "),
                    MapPoint(images = listOf(R.drawable.caption, R.drawable.tretyakov_gallery_in),
                        position = GeoPoint(55.752004, 37.617734),
                        name = "Третьяковская галерея", city = "Москва",
                        allData = "Третьяко́вская галере́я, Госуда́рственная Третьяко́вская галере́я (сокр. ГТГ; разг. Третьяко́вка) — российский государственный художественный музей в Москве, созданный на основе исторических коллекций купцов братьев Павла и Сергея Михайловичей Третьяковых; одно из крупнейших в мире собраний русского изобразительного искусства.\n" +
                                "\n" +
                                "История галереи традиционно отсчитывается с 1856 года — времени первых документированных приобретений П. М. Третьякова; в 1867 году галерея была открыта для посещения, а в 1892 году передана в собственность Москве. На момент передачи коллекция музея насчитывала 1276 картин, 471 рисунок, 10 скульптур русских художников, а также 84 картины иностранных мастеров. После революции 1917 года галерея была национализирована, коллекция стала пополняться из конфискованных частных собраний и музеев. В 1985 году Государственная картинная галерея на Крымском Валу была объединена с Третьяковской галереей и образовала единый музейный комплекс — Новая Третьяковка вместе с Центральным домом художника. В здании в Лаврушинском переулке была размещена коллекция живописи с древнейших времён до 1910-х годов, а в отделе на Крымском Валу — искусство XX века. "),
                    MapPoint(images = listOf(R.drawable.img_20190614_110857_largejpg, R.drawable.photo5jpg), position = GeoPoint(55.747224, 37.605240),
                        name = "Государственный музей изобразительных искусств имени А. С. Пушкина", city = "Москва",
                        allData = "Пу́шкинский музе́й — российский государственный художественный музей в Москве, одно из крупнейших в современной России собраний западного искусства. Созданный по инициативе историка и археолога, профессора Московского университета И. В. Цветаева, музей был открыт в 1912 году под названием «Музей изящных искусств имени императора Александра III при Императорском Московском университете». Главное здание музея было построено по проекту архитектора Романа Клейна в неоклассическом стиле в виде античного храма. Изначально музей был задуман как учебный, однако после революции 1917 года учреждение было преобразовано в Государственный музей изобразительных искусств. В 1937 году музей получил имя поэта Александра Пушкина. В 1991 году ГМИИ имени Пушкина внесли в Государственный свод особо ценных объектов культурного наследия народов Российской Федерации.\n" +
                                "\n" +
                                "По состоянию на 2018 год экспозиция состоит из более чем 670 тысяч предметов и включает в себя коллекцию слепков с античных статуй, художественные произведения, археологические находки, а также собрание предметов из Древнего Египта и Древнего Рима. В 2018 году музей посетили 1,3 млн человек, благодаря чему он занял 47-е место в числе самых посещаемых художественных музеев мира.\n",
                        ),
//                    MapPoint(position = GeoPoint(55.624251, 37.514248)),
                    MapPoint(images = listOf(R.drawable.cosmos, R.drawable.cosmos2), position = GeoPoint(55.822751, 37.639752),
                        name = "Музей космонавтики", city = "Москва",
                        allData = "Музей космона́втики в Москве (ранее Мемориальный музей космонавтики) — музей космической тематики в стилобате монумента «Покорителям космоса» на Аллее Космонавтов ВДНХ. Музей был открыт 10 апреля 1981 года — к 20-летию полёта в космос Юрия Гагарина[2]. Предметный фонд музея на январь 2017-го насчитывает более 100 тыс. единиц хранения. Общая площадь музея — 8400 м², из них 3720 м² занимает непосредственно экспозиция. Среди экспонатов находятся образцы ракетно-космической техники, личные вещи космонавтов и конструкторов, архивные документы и предметы нумизматики и филателии. Музею принадлежит филиал — Дом-музей академика Сергея Павловича Королёва на 1-й Останкинской улице. Помимо выставочной работы, музей занимается культурно-просветительской деятельностью и проводит масштабную научную работу. По данным на 2016 год, ежегодное число посетителей достигло 510 тысяч человек, а к 2020-му выросла до 750 тысяч"),
                ),
                name = "Музеи Москвы" )
            val routesList = listOf(mainRoute)
            val mapPointData by viewModel.mapPointInfo.collectAsState()
            when (data) {
                SheetStates.MapPoint -> RouteStopsCard(cardInfo = mapPointData)
                SheetStates.RouteWithStops -> RoutesScreen(route = routesList[0])
                SheetStates.Routes -> RouteSearchScreen(routes = routesList , viewModel = viewModel)
                SheetStates.Close -> {
                    RouteStopsCard(cardInfo = MapPoint())
                }
                SheetStates.Search -> SearchScreen(viewModel)
            }
        },
        sheetPeekHeight = 10.dp
    ) {
//        Box(
//            modifier = Modifier
//                .fillMaxSize(),
//            contentAlignment = Alignment.Center
//        ) {


        ModalNavigationDrawer(

            gesturesEnabled = isDrawerOpen,
            drawerState = drawerState,

            drawerContent = {

                ModalDrawerSheet {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {

                        Icon(modifier = Modifier.size(100.dp), painter = painterResource(id = R.drawable.user_icon), contentDescription = "",)

                        Text("Filippov", style = TextStyle(fontSize = 20.sp))
                        Spacer(Modifier.height(12.dp))
                        items.forEachIndexed {index, item ->
                            NavigationDrawerItem(
                                icon = { Icon(painter = painterResource(id = item), contentDescription = null) },
                                label = { Text(itmesText[index]) },
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
                }
            },
            content = {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    "Your Sights",
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
                        val mapType by viewModel.osmScreen.collectAsState()
                        if (mapType == 0) {
                            showSearch = true
                        } else {
                            showSearch = false
                        }
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            AnimateSlideInOut(visible = showSearch) {
                                SearchScreen(viewModel)
                            }
                            AnimateSlideInOut(visible = visible) {
                                when (mapType) {
                                    0 -> OsmdroidMapView(viewModel = viewModel)
                                    1 -> OsmdroidEditView(viewModel = viewModel)
                                }
                            }
//                        if (showSearch) {
//                        } else {
//                        }
                        }
                        if (showBottomSheet) {
                        }
                    },
                    bottomBar = {

                        AnimateSlideInOut(visible = visible) {
                            MainScreenNavigationBar(viewModel)
                        }
                    },
                    floatingActionButton = {
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {

                            FloatingActionButton(onClick = {
//                                showSearch = true
//                                visible = false
                                if (showSearch) {
                                    viewModel.showSearch()
                                } else {
                                    openDialog = true
                                }
                            }) {
                                if(showSearch) {
                                    Icon(painter = painterResource(id = R.drawable.baseline_search_24), contentDescription = "test" )
                                } else {

                                    Icon(painter = painterResource(id = R.drawable.baseline_create_24), contentDescription = "test" )
                                }
                            }
                            if (openDialog) {
                                DialogWithImage(
                                    onDismissRequest = {
                                        openDialog = false
                                        viewModel.setNewPoint()

                                                       },
                                )
                            }
                            if (!showSearch) {
                                FloatingActionButton(onClick = {
//                                showSearch = true
//                                visible = false
                                    showEndDialog = true
                                }) {
                                    Text(text = "Завершить")

                                }
                            }
                        }
                    },
                )

            }
        )

        val expandData by viewModel.expand.collectAsState()
        Log.i("DataExp", expandData.toString())
        scope.launch {
            if(expandData) {
                scaffoldState.bottomSheetState.expand()
            } else {
                scaffoldState.bottomSheetState.partialExpand()
            }
            if(scaffoldState.bottomSheetState.currentValue == SheetValue.Hidden) {
                Log.i("DataExp2", expandData.toString())
            }
        }
        }
    if (showEndDialog) {
        SendRoute(route = Route(), onDismissRequest = {
            showEndDialog = false
        })
    }
    LaunchedEffect(key1 = scaffoldState.bottomSheetState.currentValue) {
        snapshotFlow { scaffoldState.bottomSheetState.currentValue }.collect { bottomSheetState ->
            Log.i("DataState", bottomSheetState.name)
            if (bottomSheetState == SheetValue.PartiallyExpanded) {
                Log.i("DataExp3", "A")
                viewModel.setExpand(false)
                // Handle bottom sheet being collapsed
            } else {
                viewModel.setExpand(true)
                Log.i("DataExp3", "B")
                // Handle bottom sheet being expanded
            }
        }
    }
}
//}
//}
//    BottomSheetScaffold(
//        scaffoldState = sheetState,
////                            onDismissRequest = {
////                                viewModel.closeSheet()
////                                showBottomSheet = false
////                                scope.launch { sheetState.hide() }
////                            },
//        sheetPeekHeight = 100.dp,
//        sheetContent = {
//
//            val data by viewModel.showDataType.collectAsState()
//
//            if (data == SheetStates.Close) {
//                showBottomSheet = false
//            } else {
//                showBottomSheet = true
//            }
//            when (data) {
//                SheetStates.MapPoint -> RouteStopsCard(cardInfo = MapPoint())
//                SheetStates.Routes -> RoutesScreen(stops = listOf(MapPoint()))
//                SheetStates.Close -> {}
//            }
//        }
//    ) {
//
        // Sheet content

//    }
//}

