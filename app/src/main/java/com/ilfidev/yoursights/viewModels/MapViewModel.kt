package com.ilfidev.yoursights.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.ilfidev.yoursights.models.repository.MapInterface

class MapViewModel(val repository: MapInterface) : ViewModel() {
}