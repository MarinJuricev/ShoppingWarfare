package com.marinj.shoppingwarfare.feature.cart.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus.*
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.marinj.shoppingwarfare.R.string
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartEvent

@Composable
fun CartCameraPermission(
    onCartEvent: (CartEvent) -> Unit,
    navigateToSettingsScreen: () -> Unit,
) {
    // Camera permission state
    val cameraPermissionState: PermissionState = rememberPermissionState(
        android.Manifest.permission.CAMERA
    )

    Column(
        modifier = Modifier
            .fillMaxHeight(0.25f)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (cameraPermissionState.status) {
            Granted -> CartCameraPreview(onCartEvent = onCartEvent)
            is Denied -> {
                if (cameraPermissionState.status.shouldShowRationale) {
                    Text(text = stringResource(string.cart_camera_permission_reasoning))
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { cameraPermissionState.launchPermissionRequest() }) {
                        Text(stringResource(string.request_permission))
                    }
                } else {
                    Text(text = stringResource(string.camera_permission_denied))
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = navigateToSettingsScreen) {
                        Text(text = stringResource(string.open_settings))
                    }

                }
            }
        }
    }
}
