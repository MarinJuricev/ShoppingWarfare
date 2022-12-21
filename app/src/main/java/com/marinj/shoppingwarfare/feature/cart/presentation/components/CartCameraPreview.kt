package com.marinj.shoppingwarfare.feature.cart.presentation.components

import android.content.Context
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.marinj.shoppingwarfare.R
import com.marinj.shoppingwarfare.core.components.ShoppingWarfareIconButton
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartEvent
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartEvent.ReceiptCaptureError
import timber.log.Timber
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

private const val DATE_FORMAT = "yyyy:MM:dd HH:mm:ss"
private const val JPG_EXTENSION = ".jpg"
private const val ICON_ALPHA = 0.5f

// https://stackoverflow.com/questions/61795508/how-can-i-use-a-cameraview-with-jetpack-compose
@Composable
fun CartCameraPreview(
    modifier: Modifier = Modifier,
    cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
    scaleType: PreviewView.ScaleType = PreviewView.ScaleType.FILL_CENTER,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    context: Context = LocalContext.current,
    onCartEvent: (CartEvent) -> Unit,
) {
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val imageCapture = remember { ImageCapture.Builder().build() }

    Box {
        AndroidView(
            modifier = modifier,
            factory = { context ->
                val previewView = PreviewView(context).apply {
                    this.scaleType = scaleType
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                    )
                    // Preview is incorrectly scaled in Compose on some devices without this
                    implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                }

                cameraProviderFuture.addListener({
                    val cameraProvider = cameraProviderFuture.get()

                    setupCameraView(
                        cameraProvider = cameraProvider,
                        previewView = previewView,
                        imageCapture = imageCapture,
                        lifecycleOwner = lifecycleOwner,
                        cameraSelector = cameraSelector,
                        onCartEvent = onCartEvent,
                    )
                }, ContextCompat.getMainExecutor(context))

                previewView
            },
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
                    onCartEvent,
                )
            },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.take_photo_icon),
                tint = MaterialTheme.colors.surface.copy(alpha = ICON_ALPHA),
                contentDescription = null,
            )
        }
    }
}

private fun setupCameraView(
    cameraProvider: ProcessCameraProvider,
    previewView: PreviewView,
    imageCapture: ImageCapture?,
    lifecycleOwner: LifecycleOwner,
    cameraSelector: CameraSelector,
    onCartEvent: (CartEvent) -> Unit,
) {
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
            lifecycleOwner,
            cameraSelector,
            preview,
            imageCapture,
        )
    } catch (exc: Exception) {
        Timber.e("Use case binding failed", exc)
        onCartEvent(ReceiptCaptureError)
        cameraProvider.unbindAll()
    }
}

private fun setupImageCaptureListener(
    imageCapture: ImageCapture?,
    context: Context,
    onCartEvent: (CartEvent) -> Unit,
) {
    // Create time-stamped output file to hold the image
    val photoFile = File(
        context.filesDir,
        SimpleDateFormat(
            DATE_FORMAT,
            Locale.US,
        ).format(System.currentTimeMillis()) + JPG_EXTENSION,
    )

    // Create output options object which contains file + metadata
    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

    imageCapture?.takePicture(
        outputOptions,
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                onCartEvent(CartEvent.ReceiptCaptureSuccess(output.savedUri?.lastPathSegment))
            }

            override fun onError(exc: ImageCaptureException) {
                Timber.d("Photo capture failed: ${exc.message}", exc)
                onCartEvent(ReceiptCaptureError)
            }
        },
    )
}
