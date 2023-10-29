package com.whyranoid.walkie.walkiedialog

import androidx.lifecycle.ViewModel
import com.whyranoid.domain.usecase.broadcast.AddGpsListener
import com.whyranoid.domain.usecase.broadcast.AddNetworkListener
import com.whyranoid.domain.usecase.broadcast.GetGpsState
import com.whyranoid.domain.usecase.broadcast.GetNetworkState
import com.whyranoid.domain.usecase.broadcast.RemoveGpsListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

class DialogViewModel(
    private val addNetworkListener: AddNetworkListener,
    private val removeNetworkListener: AddNetworkListener,
    private val getNetworkState: GetNetworkState,
    private val addGpsListener: AddGpsListener,
    private val removeGpsListener: RemoveGpsListener,
    private val getGpsState: GetGpsState,
) : ViewModel() {
    init {
        addNetworkListener()
        addGpsListener()
    }

    private val _locationPermissionDialogState =
        MutableStateFlow<DialogState>(DialogState.Initialized)
    private val _storagePermissionDialogState =
        MutableStateFlow<DialogState>(DialogState.Initialized)

    val locationPermissionDialogState get() = _locationPermissionDialogState.asStateFlow()
    val storagePermissionDialogState get() = _storagePermissionDialogState.asStateFlow()

    val gpsDialogState = getGpsState().map {
        if (it) DialogState.Valid else DialogState.InValid(DialogContentProvider.GPS)
    }
    val networkDialogState = getNetworkState().map {
        if (it) DialogState.Valid else DialogState.InValid(DialogContentProvider.Network)
    }

    fun setPermission(permission: String, showDialog: Boolean) {
        if (permission == DialogContentProvider.LocationPermission.PERMISSION) {
            _locationPermissionDialogState.value =
                if (showDialog) DialogState.Valid else DialogState.InValid(DialogContentProvider.LocationPermission)
        } else if (permission == DialogContentProvider.StoragePermission.PERMISSION) {
            _storagePermissionDialogState.value =
                if (showDialog) DialogState.Valid else DialogState.InValid(DialogContentProvider.StoragePermission)
        }
    }

    override fun onCleared() {
        removeGpsListener()
        removeNetworkListener()
        super.onCleared()
    }
}
