package com.example.actualizar.ui.screens.profile

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.actualizar.R
import com.example.actualizar.ui.components.UpdateButton
import java.util.Calendar
import kotlin.math.abs
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChangeBirthdayScreen(navController: NavController) {
    val cal = remember { Calendar.getInstance() }
    val ctx = LocalContext.current

    // datos
    val years = remember { (1950..cal.get(Calendar.YEAR)).toList() }
    val monthLabels = remember {
        listOf("Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre")
    }

    var selectedYear by remember { mutableStateOf((cal.get(Calendar.YEAR) - 20).coerceIn(years.first(), years.last())) }
    var selectedMonth by remember { mutableStateOf(cal.get(Calendar.MONTH) + 1) }
    var selectedDay by remember { mutableStateOf(cal.get(Calendar.DAY_OF_MONTH)) }

    val daysInMonth by remember(selectedYear, selectedMonth) { mutableStateOf(daysFor(selectedYear, selectedMonth)) }
    LaunchedEffect(daysInMonth) { if (selectedDay > daysInMonth) selectedDay = daysInMonth }

    // estados + snap
    val yearState = rememberLazyListState(indexOf(years, selectedYear))
    val monthState = rememberLazyListState(selectedMonth - 1)
    val dayState = rememberLazyListState(selectedDay - 1)

    val yearCentered by centeredIndex(yearState)
    val monthCentered by centeredIndex(monthState)
    val dayCentered by centeredIndex(dayState)

    LaunchedEffect(yearCentered) { yearCentered?.let { years.getOrNull(it)?.let { y -> selectedYear = y } } }
    LaunchedEffect(monthCentered) { monthCentered?.let { idx -> selectedMonth = idx + 1 } }
    LaunchedEffect(dayCentered) { dayCentered?.let { idx -> selectedDay = idx + 1 } }

    // medidas
    val carouselsHeight = 196.dp
    val pillHeight = 44.dp
    val colYear = 96.dp
    val colMonth = 124.dp
    val colDay = 72.dp
    val columnsSpacing = 16.dp
    val groupWidth = colYear + colMonth + colDay + columnsSpacing * 2
    val green = Color(0xFF1D5E21)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
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
            text = "FECHA DE\nNACIMIENTO",
            style = MaterialTheme.typography.displayLarge, // Comfortaa
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Left,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        Spacer(Modifier.height(12.dp))

        // —— Carruseles con UNA píldora verde semitransparente del ancho del grupo
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(carouselsHeight),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .width(groupWidth)
                    .height(pillHeight)
                    .background(green.copy(alpha = 0.82f), RoundedCornerShape(14.dp))
                    .align(Alignment.Center)
            )

            Row(
                modifier = Modifier
                    .width(groupWidth)
                    .height(carouselsHeight),
                horizontalArrangement = Arrangement.spacedBy(columnsSpacing),
                verticalAlignment = Alignment.CenterVertically
            ) {
                VerticalCarousel(
                    items = years.map { it.toString() },
                    listState = yearState,
                    columnWidth = colYear,
                    viewportHeight = carouselsHeight,
                    rowHeight = pillHeight
                )
                VerticalCarousel(
                    items = monthLabels,
                    listState = monthState,
                    columnWidth = colMonth,
                    viewportHeight = carouselsHeight,
                    rowHeight = pillHeight
                )
                VerticalCarousel(
                    items = (1..daysInMonth).map { it.toString() },
                    listState = dayState,
                    columnWidth = colDay,
                    viewportHeight = carouselsHeight,
                    rowHeight = pillHeight
                )
            }
        }

        // === IMAGEN ocupa SOLO el espacio entre carrusel y botón ===
        //    Así garantizamos: (a) está por debajo del carrusel, (b) las piernas arriba del botón
        Box(
            modifier = Modifier
                .weight(1f)             // ← llena el espacio disponible
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(R.drawable.fechana_gen),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxHeight()    // ← usa TODO el alto disponible en este bloque
                    .graphicsLayer(     // ← pequeño “zoom” para que se vea grande en cualquier pantalla
                        scaleX = 2.10f,
                        scaleY = 2.10f
                    ),
                contentScale = ContentScale.Fit,
                alpha = 0.96f
            )
        }

        UpdateButton(
            onClick = {
                Toast.makeText(ctx, "¡Fecha de nacimiento actualizada!", Toast.LENGTH_SHORT).show()
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

/* --------------------- Carrusel vertical --------------------- */

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun VerticalCarousel(
    items: List<String>,
    listState: LazyListState,
    columnWidth: Dp,
    viewportHeight: Dp,
    rowHeight: Dp,
    itemSpacing: Dp = 8.dp
) {
    val fling = rememberSnapFlingBehavior(listState)
    val edgePadding = (viewportHeight - rowHeight) / 2

    LazyColumn(
        state = listState,
        flingBehavior = fling,
        verticalArrangement = Arrangement.spacedBy(itemSpacing),
        contentPadding = PaddingValues(vertical = edgePadding),
        modifier = Modifier
            .width(columnWidth)
            .fillMaxHeight()
    ) {
        itemsIndexed(items) { index, label ->
            val selected = isIndexCentered(listState, index)
            CarouselRowItem(
                text = label,
                selected = selected,
                height = rowHeight
            )
        }
    }
}

@Composable
private fun CarouselRowItem(
    text: String,
    selected: Boolean,
    height: Dp
) {
    val fg = if (selected) Color.White else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.75f)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium, // Figtree
            color = fg,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

/* --------------------- Helpers --------------------- */

private fun indexOf(list: List<Int>, value: Int): Int =
    list.indexOf(value).takeIf { it >= 0 } ?: 0

private fun daysFor(year: Int, month: Int): Int {
    val thirtyOne = setOf(1,3,5,7,8,10,12)
    return when {
        month in thirtyOne -> 31
        month == 2 -> if (isLeap(year)) 29 else 28
        else -> 30
    }
}
private fun isLeap(year: Int) =
    (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)

/** Índice centrado para LazyList vertical */
@Composable
private fun centeredIndex(state: LazyListState): State<Int?> {
    return remember(state) {
        derivedStateOf {
            val layout = state.layoutInfo
            if (layout.visibleItemsInfo.isEmpty()) return@derivedStateOf null
            val viewportCenter = (layout.viewportStartOffset + layout.viewportEndOffset) / 2
            var minDist = Int.MAX_VALUE
            var result: Int? = null
            for (item in layout.visibleItemsInfo) {
                val itemCenter = item.offset + item.size / 2
                val dist = abs(itemCenter - viewportCenter)
                if (dist < minDist) { minDist = dist; result = item.index }
            }
            result
        }
    }
}
@Composable
private fun isIndexCentered(state: LazyListState, index: Int): Boolean {
    val centered by centeredIndex(state)
    return centered == index
}
