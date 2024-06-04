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
        viewModelScope.launch {
            getRoute(UUID.randomUUID())
        }
    }
    suspend fun getRoute(routeId: UUID) {
        repository.getRoute(routeId)
        repository.routesSharedFlow.collect {
            routeData ->
            _mapMarkersState.value = routeData
        }
    }
}