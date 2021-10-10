package com.marinj.shoppingwarfare.feature.cart.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.marinj.shoppingwarfare.R.string

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CartCameraPermission(
    navigateToSettingsScreen: () -> Unit,
) {
    // Track if the user doesn't want to see the rationale any more.
    var doNotShowRationale by rememberSaveable { mutableStateOf(false) }

    // Camera permission state
    val cameraPermissionState = rememberPermissionState(
        android.Manifest.permission.CAMERA
    )

    when {
        cameraPermissionState.hasPermission -> {
            Text("Camera permission Granted")
        }
        cameraPermissionState.shouldShowRationale || !cameraPermissionState.permissionRequested -> {
            if (doNotShowRationale) {
                Text("Feature not available")
            } else {
                Column(modifier = Modifier.fillMaxHeight(0.25f)) {
                    Text(stringResource(string.cart_camera_permission_reasoning))
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Button(onClick = { cameraPermissionState.launchPermissionRequest() }) {
                            Text("Request permission")
                        }
                        Spacer(Modifier.width(8.dp))
                        Button(onClick = { doNotShowRationale = true }) {
                            Text("Don't show rationale again")
                        }
                    }
                }
            }
        }
        // If the criteria above hasn't been met, the user denied the permission. Let's present
        // the user with a FAQ in case they want to know more and send them to the Settings screen
        // to enable it the future there if they want to.
        else -> {
            Column {
                Text(
                    "Camera permission denied. See this FAQ with information about why we " +
                        "need this permission. Please, grant us access on the Settings screen."
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = navigateToSettingsScreen) {
                    Text("Open Settings")
                }
            }
        }
    }
}
