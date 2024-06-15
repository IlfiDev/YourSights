package com.ilfidev.yoursights.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilfidev.yoursights.R
import com.ilfidev.yoursights.models.data.MapPoint
import com.ilfidev.yoursights.models.data.Route
import com.ilfidev.yoursights.models.repository.MapInterface
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.osmdroid.util.GeoPoint
import java.util.UUID
import kotlin.contracts.contract

class MapViewModel(val repository: MapInterface) : ViewModel() {

    private val _mapMarkersState = MutableStateFlow(Route())
    val uiState: StateFlow<Route> = _mapMarkersState

    private val _showDataType = MutableStateFlow(SheetStates.Routes)
    val showDataType: StateFlow<SheetStates> = _showDataType


    private var _expand = MutableStateFlow(false)
    val expand: StateFlow<Boolean> = _expand

    private var _mapPointInfo = MutableStateFlow(MapPoint())
    val mapPointInfo: StateFlow<MapPoint> = _mapPointInfo

    private var _osmScreen = MutableStateFlow(0)
    val osmScreen: StateFlow<Int> = _osmScreen


    var counter = 0

    private var _points = MutableStateFlow(listOf(MapPoint()))
    val points: StateFlow<List<MapPoint>> = _points


    enum class SheetStates {
        Close,
        MapPoint,
        RouteWithStops,
        Routes,
        Search,

    }
    init {
//        getRoute(UUID.randomUUID())
    }
    fun getRoute(routeId: UUID) {
        viewModelScope.launch {
            repository.getRoute(routeId)
            repository.routesSharedFlow.collect {
                    routeData ->
                _mapMarkersState.value = routeData
            }
        }
    }

    fun getSightInfo(sightId: UUID) {
        viewModelScope.launch {
            repository.getMapPoint(sightId)
        }
    }

    fun getRoutesWithSight(sightId: UUID) {
        viewModelScope.launch {
            repository.getRoutesWithSight(sightId)
        }
    }

    fun showPointInfo(info: MapPoint) {
        _showDataType.value = SheetStates.MapPoint
        _mapPointInfo.value = info
        _expand.value = true
    }

    fun showSearch() {

        _showDataType.value = SheetStates.Search
        _expand.value = true
    }

    fun showRoute() {
        _showDataType.value = SheetStates.RouteWithStops
        _expand.value = false
    }
    fun showRoutesSearch() {
        _showDataType.value = SheetStates.Routes
    }

    fun closeSheet() {
        _showDataType.value = SheetStates.Close
        _expand.value = true
    }
    fun setExpand(value: Boolean) {
        _expand.value = value
        Log.i("DataVM", _expand.value.toString())
    }

    fun showRouteOnMap(route: Route) {
        _mapMarkersState.value = route
    }
    fun setScreenTo(screenIndex: Int) {
        _osmScreen.value = screenIndex
    }

    fun setNewPoint() {
        when (counter) {
            0 -> {
                val newList = mutableListOf(MapPoint(name = "1", position = GeoPoint(55.5, 37.5), allData = "text"))
                _points.value = newList
            }
            1 -> {
                val newPoint = mutableListOf<MapPoint>()
                newPoint.addAll(_points.value)
                newPoint.add(MapPoint(name = "2", position = GeoPoint(55.6, 37.6), allData = "text"))
                _points.value = newPoint
            }
            2 -> {
                val newPoint = mutableListOf<MapPoint>()
                newPoint.addAll(_points.value)
                newPoint.add(
                MapPoint(images = listOf(R.drawable.img_20190614_110857_largejpg, R.drawable.photo5jpg), position = GeoPoint(55.747224, 37.605240),
                    name = "Государственный музей изобразительных искусств имени А. С. Пушкина", city = "Москва",
                    allData = "Пу́шкинский музе́й — российский государственный художественный музей в Москве, одно из крупнейших в современной России собраний западного искусства. Созданный по инициативе историка и археолога, профессора Московского университета И. В. Цветаева, музей был открыт в 1912 году под названием «Музей изящных искусств имени императора Александра III при Императорском Московском университете». Главное здание музея было построено по проекту архитектора Романа Клейна в неоклассическом стиле в виде античного храма. Изначально музей был задуман как учебный, однако после революции 1917 года учреждение было преобразовано в Государственный музей изобразительных искусств. В 1937 году музей получил имя поэта Александра Пушкина. В 1991 году ГМИИ имени Пушкина внесли в Государственный свод особо ценных объектов культурного наследия народов Российской Федерации.\n" +
                            "\n" +
                            "По состоянию на 2018 год экспозиция состоит из более чем 670 тысяч предметов и включает в себя коллекцию слепков с античных статуй, художественные произведения, археологические находки, а также собрание предметов из Древнего Египта и Древнего Рима. В 2018 году музей посетили 1,3 млн человек, благодаря чему он занял 47-е место в числе самых посещаемых художественных музеев мира.\n",
                )
                )
                _points.value = newPoint
            }
        }
        Log.i("Jopa", counter.toString())
        Log.i("Jopa", _points.value.size.toString())
        counter += 1
    }
}