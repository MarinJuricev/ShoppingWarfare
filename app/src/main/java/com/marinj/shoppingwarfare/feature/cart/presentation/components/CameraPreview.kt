package com.marinj.shoppingwarfare.feature.cart.presentation.components

import android.content.Context
import android.net.Uri
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.common.util.concurrent.ListenableFuture
import com.marinj.shoppingwarfare.R
import com.marinj.shoppingwarfare.core.components.ShoppingWarfareIconButton
import kotlinx.coroutines.delay
import timber.log.Timber
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

private const val DATE_FORMAT = "yyyy:MM:dd HH:mm:ss"
private const val JPG_EXTENSION = ".jpg"
private const val ICON_ALPHA = 0.5f
private const val CAMERA_FLASH_DURATION = 100L

// https://stackoverflow.com/questions/61795508/how-can-i-use-a-cameraview-with-jetpack-compose
@Composable
fun CameraPreview(
    modifier: Modifier = Modifier,
    cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
    scaleType: PreviewView.ScaleType = PreviewView.ScaleType.FILL_CENTER,
    onImageSaved: (String) -> Unit = {},
    onImageError: () -> Unit = {},
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    var triggerFlash by remember { mutableStateOf(false) }
    val transition = updateTransition(
        targetState = triggerFlash,
        label = "flashAnimation"
    )
    val flashBackground by transition.animateColor(
        label = "flashBackground"
    ) { shouldFlash ->
        if (shouldFlash) {
            LaunchedEffect(key1 = shouldFlash) {
                delay(CAMERA_FLASH_DURATION)
                triggerFlash = false
            }
            Color.White
        } else Color.Transparent
    }
    var imageCapture: ImageCapture? = null

    Box {
        AndroidView(
            modifier = modifier,
            factory = { context ->
                val previewView = PreviewView(context).apply {
                    this.scaleType = scaleType
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    // Preview is incorrectly scaled in Compose on some devices without this
                    implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                }
                val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
                ImageCapture.Builder().build().also { imageCapture = it }

                setupCameraView(
                    cameraProviderFuture,
                    previewView,
                    imageCapture,
                    lifecycleOwner,
                    cameraSelector,
                    onImageError,
                    context,
                )

                previewView
            }
        )
        ShoppingWarfareIconButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp),
            backgroundAlpha = ICON_ALPHA,
            onClick = {
                setupImageCaptureListener(
                    imageCapture,
                    context,
                    { triggerFlash = true },
                    onImageSaved,
                    onImageError,
                )
            },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.take_photo_icon),
                tint = MaterialTheme.colors.surface.copy(alpha = ICON_ALPHA),
                contentDescription = null
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(flashBackground)
        )
    }
}

private fun setupCameraView(
    cameraProviderFuture: ListenableFuture<ProcessCameraProvider>,
    previewView: PreviewView,
    imageCapture: ImageCapture?,
    lifecycleOwner: LifecycleOwner,
    cameraSelector: CameraSelector,
    onImageError: () -> Unit,
    context: Context,
) {
    cameraProviderFuture.addListener({
        val cameraProvider = cameraProviderFuture.get()

        // Preview
        val preview = Preview.Builder()
            .build()
            .also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }

        try {
            // Must unbind the use-cases before rebinding them.
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifecycleOwner, cameraSelector, preview, imageCapture
            )
        } catch (exc: Exception) {
            Timber.e("Use case binding failed", exc)
            onImageError()
        }
    }, ContextCompat.getMainExecutor(context))
    }

    private fun setupImageCaptureListener(
        imageCapture: ImageCapture?,
        context: Context,
        cameraTriggered: () -> Unit,
        onImageSaved: (String) -> Unit,
        onImageError: () -> Unit
    ) {
        // Create time-stamped output file to hold the image
        val photoFile = File(
            context.filesDir,
            SimpleDateFormat(
                DATE_FORMAT, Locale.US
            ).format(System.currentTimeMillis()) + JPG_EXTENSION
        )

        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture?.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    cameraTriggered()
                    onImageSaved(savedUri.toString())
                }

                override fun onError(exc: ImageCaptureException) {
                    Timber.d("Photo capture failed: ${exc.message}", exc)
                    cameraTriggered()
                    onImageError()
                }
            }
        )
    }
    