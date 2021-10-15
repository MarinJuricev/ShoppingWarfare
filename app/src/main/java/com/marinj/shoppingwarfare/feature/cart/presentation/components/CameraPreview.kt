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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.common.util.concurrent.ListenableFuture
import timber.log.Timber
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

private const val DATE_FORMAT = "yyyy:MM:dd HH:mm:ss"
private const val JPG_EXTENSION = ".jpg"

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

            setupCameraView(
                cameraProviderFuture,
                previewView,
                lifecycleOwner,
                cameraSelector,
                onImageSaved,
                onImageError,
                context
            )

            previewView
        }
    )
}

private fun setupCameraView(
    cameraProviderFuture: ListenableFuture<ProcessCameraProvider>,
    previewView: PreviewView,
    lifecycleOwner: LifecycleOwner,
    cameraSelector: CameraSelector,
    onImageSaved: (String) -> Unit,
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

        val imageCapture = ImageCapture.Builder().build()
        try {
            // Must unbind the use-cases before rebinding them.
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifecycleOwner, cameraSelector, preview, imageCapture
            )
            setupImageCaptureListener(
                imageCapture, context, onImageSaved, onImageError
            )
        } catch (exc: Exception) {
            Timber.e("Use case binding failed", exc)
        }
    }, ContextCompat.getMainExecutor(context))
    }

    private fun setupImageCaptureListener(
        imageCapture: ImageCapture,
        context: Context,
        onImageSaved: (String) -> Unit,
        onImageError: () -> Unit
    ) {
        // Create time-stamped output file to hold the image
        val photoFile = File(
            context.cacheDir,
            SimpleDateFormat(
                DATE_FORMAT, Locale.US
            ).format(System.currentTimeMillis()) + JPG_EXTENSION
        )

        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(context),
//        object : ImageCapture.OnImageCapturedCallback() {
//            override fun onCaptureSuccess(imageProxy: ImageProxy) {
// //                val image: Image = imageProxy.image // Do what you want with the image
//
//                imageProxy.close() // Make sure to close the image
//            }
//
//            override fun onError(exception: ImageCaptureException) {
//                // Handle exception
//            }
// )        })
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    onImageSaved(savedUri.toString())
                }

                override fun onError(exc: ImageCaptureException) {
                    Timber.d("Photo capture failed: ${exc.message}", exc)
                    onImageError()
                }
            }
        )
    }
    