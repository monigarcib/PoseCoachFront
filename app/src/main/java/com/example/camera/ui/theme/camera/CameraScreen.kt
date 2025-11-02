package com.example.camera.ui

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import android.view.ViewGroup
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.camera.R

@Composable
fun CameraScreen(
    isRunning: Boolean,
    cameraSelector: CameraSelector,
    onToggleRunning: () -> Unit,
    onSwitchCamera: () -> Unit,
    onNavigateHome: () -> Unit,
    overlay: @Composable BoxScope.() -> Unit = {}
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val previewView = remember {
        PreviewView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            scaleType = PreviewView.ScaleType.FILL_CENTER
        }
    }

    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED
        )
    }
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted -> hasCameraPermission = granted }

    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }

    LaunchedEffect(isRunning, hasCameraPermission, cameraSelector) {
        val provider = try { cameraProviderFuture.get() } catch (e: Exception) {
            Log.e("CameraScreen", "CameraProvider error", e); null
        } ?: return@LaunchedEffect

        if (hasCameraPermission && isRunning) {
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }
            try {
                provider.unbindAll()
                provider.bindToLifecycle(lifecycleOwner, cameraSelector, preview)
            } catch (e: Exception) {
                Log.e("CameraScreen", "bindToLifecycle error", e)
            }
        } else {
            try { provider.unbindAll() } catch (_: Exception) {}
        }
    }

    Box(Modifier.fillMaxSize()) {
        AndroidView(factory = { previewView }, modifier = Modifier.fillMaxSize())
        Box(Modifier.fillMaxSize(), content = overlay)

        // ✅ Barra inferior verde con los tres íconos
        BottomBar(
            isRunning = isRunning,
            onRecordClick = {
                if (!hasCameraPermission) permissionLauncher.launch(Manifest.permission.CAMERA)
                else onToggleRunning()
            },
            onHomeClick = onNavigateHome,
            onSwitchCamera = onSwitchCamera,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun BottomBar(
    isRunning: Boolean,
    onRecordClick: () -> Unit,
    onHomeClick: () -> Unit,
    onSwitchCamera: () -> Unit,
    modifier: Modifier = Modifier
) {
    val green = Color(0xFF00A651)
    val iconColor = Color(0xFF0B0B0B) // negro de los íconos

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp)
    ) {
        // Franja inferior verde con esquinas superiores redondeadas
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(72.dp)
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .background(green)
        )

        // Íconos planos: izquierda (home), centro (start/stop), derecha (change)
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(72.dp)
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // HOME (izquierda)
            PlainIconButton(
                resId = R.drawable.hogar,
                contentDescription = "Home",
                tint = iconColor,
                onClick = onHomeClick
            )

            // START / STOP (centro)
            val currentIcon = if (isRunning) R.drawable.stop else R.drawable.start
            PlainIconButton(
                resId = currentIcon,
                contentDescription = if (isRunning) "Stop" else "Start",
                tint = if (isRunning) Color.Red else iconColor,
                onClick = onRecordClick,
                size = 30.dp // un poquito más grande para destacar
            )

            // CHANGE CAMERA (derecha)
            PlainIconButton(
                resId = R.drawable.change,
                contentDescription = "Switch camera",
                tint = iconColor,
                onClick = onSwitchCamera
            )
        }
    }
}

@Composable
private fun PlainIconButton(
    resId: Int,
    contentDescription: String,
    tint: Color,
    onClick: () -> Unit,
    size: Dp = 26.dp
) {
    androidx.compose.material3.IconButton(
        onClick = onClick,
        modifier = Modifier.size(44.dp) // área táctil cómoda
    ) {
        Icon(
            painter = painterResource(id = resId),
            contentDescription = contentDescription,
            tint = tint,
            modifier = Modifier.size(size)
        )
    }
}




