package com.example.actualizar.ui.screens.profile

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.actualizar.R
import com.example.actualizar.ui.components.UpdateButton
import kotlin.math.abs
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext

@SuppressLint("Range")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChangeHeightScreen(navController: NavController) {
    val heights = remember { (120..220).toList() }
    var selected by remember { mutableStateOf(172) }

    // Carrusel
    val listState = rememberLazyListState(
        initialFirstVisibleItemIndex = heights.indexOf(selected).coerceAtLeast(0)
    )
    val centeredIndex by centeredIndex(listState)
    LaunchedEffect(centeredIndex) {
        centeredIndex?.let { i -> heights.getOrNull(i)?.let { selected = it } }
    }

    val viewportHeight = 140.dp
    val rowHeight = 44.dp
    val boxShape = RoundedCornerShape(14.dp)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Imagen de fondo a la derecha (igual que en otras pantallas)
        Image(
            painter = painterResource(R.drawable.fechana_gen),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .fillMaxHeight(1.02f)
                .padding(end = 60.dp)
                .offset(x = (-0.5).dp, y = (-2).dp)
                .graphicsLayer(
                    scaleX = 2.50f,  // más grande
                    scaleY = 2.50f
                ),
            contentScale = ContentScale.Fit,
            alpha = 0.98f
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            Spacer(Modifier.height(8.dp))
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Atrás",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }

            Text(
                text = "ALTURA",
                style = MaterialTheme.typography.displayLarge, // Comfortaa
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Right,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )

            Spacer(Modifier.height(12.dp))

            // ===== Picker con números visibles arriba/abajo + recuadro con número y "cm" =====
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(viewportHeight),
                contentAlignment = Alignment.CenterStart
            ) {
                // 1) Lista vertical detrás, con items tenues
                NumberColumnWithFade(
                    items = heights.map { it.toString() },
                    listState = listState,
                    viewportHeight = viewportHeight,
                    rowHeight = rowHeight,
                    columnWidth = 100.dp
                )

                // 2) Recuadro por encima, con número actual y "cm" dentro
                Box(
                    modifier = Modifier
                        .width(180.dp)
                        .height(rowHeight)
                        .clip(boxShape)
                        .border(1.dp, Color.White.copy(alpha = 0.85f), boxShape)
                        .background(Color.Black.copy(alpha = 0.25f))
                        .align(Alignment.CenterStart)
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = selected.toString(),
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                            color = Color.White
                        )
                        Spacer(Modifier.weight(1f))
                        Text(
                            text = "cm",
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                            color = Color.White
                        )
                    }
                }
            }

            Spacer(Modifier.weight(1f))

            val ctx = LocalContext.current

            UpdateButton(
                onClick = {
                    Toast.makeText(ctx, "¡Altura actualizada!", Toast.LENGTH_SHORT).show()
                    // navController.popBackStack()
                },
                text = "ACTUALIZAR",
                enabled = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 5.dp)
            )
        }
    }
}

/* ---------- Subcomponentes ---------- */

/** Lista vertical detrás con números; atenúa los extremos con un fade arriba/abajo. */
@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun NumberColumnWithFade(
    items: List<String>,
    listState: LazyListState,
    viewportHeight: Dp,
    rowHeight: Dp,
    columnWidth: Dp,
    itemSpacing: Dp = 8.dp
) {
    val fling = rememberSnapFlingBehavior(listState)
    val edgePadding = (viewportHeight - rowHeight) / 2

    Box(
        modifier = Modifier
            .width(columnWidth)
            .height(viewportHeight)
    ) {
        LazyColumn(
            state = listState,
            flingBehavior = fling,
            verticalArrangement = Arrangement.spacedBy(itemSpacing),
            contentPadding = PaddingValues(vertical = edgePadding),
            modifier = Modifier.matchParentSize()
        ) {
            itemsIndexed(items) { index, label ->
                val selected = isIndexCentered(listState, index)
                val alpha = if (selected) 0.0f else 0.35f // ocultamos el centrado (lo muestra el recuadro)
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = alpha),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .height(rowHeight)
                        .fillMaxWidth()
                        .wrapContentHeight(Alignment.CenterVertically)
                )
            }
        }

        // Capa de fade (oscurece arriba/abajo para simular los números saliendo)
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.verticalGradient(
                        0f to MaterialTheme.colorScheme.background,     // negro arriba
                        0.15f to Color.Transparent,                      // se aclara
                        0.85f to Color.Transparent,                      // centro limpio
                        1f to MaterialTheme.colorScheme.background       // negro abajo
                    )
                )
        )
    }
}

/* ---------- Helpers del carrusel ---------- */

@Composable
private fun centeredIndex(state: LazyListState): State<Int?> {
    return remember(state) {
        derivedStateOf {
            val layout = state.layoutInfo
            if (layout.visibleItemsInfo.isEmpty()) return@derivedStateOf null
            val viewportCenter = (layout.viewportStartOffset + layout.viewportEndOffset) / 2
            var minDist = Int.MAX_VALUE
            var idx: Int? = null
            for (item in layout.visibleItemsInfo) {
                val itemCenter = item.offset + item.size / 2
                val dist = abs(itemCenter - viewportCenter)
                if (dist < minDist) { minDist = dist; idx = item.index }
            }
            idx
        }
    }
}

@Composable
private fun isIndexCentered(state: LazyListState, index: Int): Boolean {
    val centered by centeredIndex(state)
    return centered == index
}
