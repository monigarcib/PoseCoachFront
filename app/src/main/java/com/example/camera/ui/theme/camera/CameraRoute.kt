package com.example.camera.ui

import androidx.camera.core.CameraSelector
import androidx.compose.runtime.*

@Composable
fun CameraRoute() {
    var isRunning by remember { mutableStateOf(false) }
    var cameraSelector by remember { mutableStateOf(CameraSelector.DEFAULT_BACK_CAMERA) }

    CameraScreen(
        isRunning = isRunning,
        cameraSelector = cameraSelector,
        onToggleRunning = { isRunning = !isRunning },
        onSwitchCamera = {
            cameraSelector = if (cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA)
                CameraSelector.DEFAULT_FRONT_CAMERA else CameraSelector.DEFAULT_BACK_CAMERA
        },
        onNavigateHome = { },
        overlay = {
            PoseOverlay(showRec = isRunning, showHint = true)
        }
    )
}
