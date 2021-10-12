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

    Column(
        modifier = Modifier
            .fillMaxHeight(0.25f)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when {
            cameraPermissionState.hasPermission -> {
                Text(
                    text = "Camera permission Granted, TODO actually indicate/open the Camera"
                )
            }
            cameraPermissionState.shouldShowRationale || !cameraPermissionState.permissionRequested -> {
                if (doNotShowRationale) {
                    Text(text = stringResource(string.feature_not_available))
                } else {
                    Text(text = stringResource(string.cart_camera_permission_reasoning))
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Button(onClick = { cameraPermissionState.launchPermissionRequest() }) {
                            Text(stringResource(string.request_permission))
                        }
                        Spacer(Modifier.width(8.dp))
                        Button(onClick = { doNotShowRationale = true }) {
                            Text(stringResource(string.do_not_show_rationale))
                        }
                    }
                }
            }
            // If the criteria above hasn't been met, the user denied the permission. Let's present
            // the user with a FAQ in case they want to know more and send them to the Settings screen
            // to enable it the future there if they want to.
            else -> {
                Text(text = stringResource(string.camera_permission_denied))
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = navigateToSettingsScreen) {
                    Text(text = stringResource(string.open_settings))
                }
            }
        }
    }
}
