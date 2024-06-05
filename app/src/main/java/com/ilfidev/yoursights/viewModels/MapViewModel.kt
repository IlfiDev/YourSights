package com.ilfidev.yoursights.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilfidev.yoursights.models.data.Route
import com.ilfidev.yoursights.models.repository.MapInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.UUID

class MapViewModel(val repository: MapInterface) : ViewModel() {

    private val _mapMarkersState = MutableStateFlow(Route())
    val uiState: StateFlow<Route> = _mapMarkersState

    init {
        getRoute(UUID.randomUUID())
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
}