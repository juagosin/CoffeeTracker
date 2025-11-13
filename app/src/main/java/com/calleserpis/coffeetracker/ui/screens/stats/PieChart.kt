package com.calleserpis.coffeetracker.ui.screens.stats

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.collections.forEachIndexed
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt
data class PieChartData(
    val label: String,
    val value: Float,
    val color: Color
)





@Composable
fun ModernPieChart(titleChart: String,data: List<PieChartData>) {


    var selectedIndex by remember { mutableStateOf<Int?>(null) }
    val animationProgress = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        animationProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1000)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = titleChart,
            textAlign = TextAlign.Justify,
            style = MaterialTheme.typography.titleLarge,
            color = colorScheme.primary,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        PieChartCanvas(
            data = data,
            selectedIndex = selectedIndex,
            animationProgress = animationProgress.value,
            onSliceClick = { index ->
                selectedIndex = if (selectedIndex == index) null else index
            },
            modifier = Modifier
                .size(300.dp)
                .padding(16.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Leyenda
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            data.forEachIndexed { index, item ->
                LegendItem(
                    label = item.label,
                    value = item.value,
                    color = item.color,
                    isSelected = selectedIndex == index
                )
            }
        }
    }
}

@Composable
fun PieChartCanvas(
    data: List<PieChartData>,
    selectedIndex: Int?,
    animationProgress: Float,
    onSliceClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val total = data.sumOf { it.value.toDouble() }.toFloat()
    val textMeasurer = rememberTextMeasurer()

    Canvas(
        modifier = modifier
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    val center = Offset(size.width / 2f, size.height / 2f)
                    val radius = min(size.width, size.height) / 2f
                    val distance = sqrt(
                        (offset.x - center.x).pow(2) + (offset.y - center.y).pow(2)
                    )

                    if (distance <= radius) {
                        var angle = atan2(offset.y - center.y, offset.x - center.x)
                        angle = (angle * 180 / PI.toFloat() + 90 + 360) % 360

                        var currentAngle = 0f
                        data.forEachIndexed { index, item ->
                            val sweepAngle = (item.value / total) * 360f
                            if (angle >= currentAngle && angle < currentAngle + sweepAngle) {
                                onSliceClick(index)
                                return@detectTapGestures
                            }
                            currentAngle += sweepAngle
                        }
                    }
                }
            }
    ) {
        val radius = min(size.width, size.height) / 2f
        val center = Offset(size.width / 2f, size.height / 2f)
        var startAngle = -90f

        data.forEachIndexed { index, item ->
            val sweepAngle = (item.value / total) * 360f * animationProgress
            val isSelected = selectedIndex == index
            val scale = if (isSelected) 1.05f else 1f

            scale(scale, pivot = center) {
                drawArc(
                    color = item.color,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = true,
                    topLeft = Offset(
                        center.x - radius,
                        center.y - radius
                    ),
                    size = Size(radius * 2, radius * 2),
                    alpha = if (selectedIndex == null || isSelected) 1f else 0.6f
                )
            }

            // Dibujar porcentaje
            if (animationProgress > 0.8f) {
                val percentage = (item.value / total * 100).toInt()
                val angleInRadians = (startAngle + sweepAngle / 2) * PI / 180
                val textRadius = radius * 0.7f
                val textX = center.x + textRadius * cos(angleInRadians).toFloat()
                val textY = center.y + textRadius * sin(angleInRadians).toFloat()

                val textLayoutResult = textMeasurer.measure(
                    text = "$percentage%",
                    style = androidx.compose.ui.text.TextStyle(
                        color = Color.White,
                        fontSize = 16.sp
                    )
                )

                drawText(
                    textLayoutResult = textLayoutResult,
                    topLeft = Offset(
                        textX - textLayoutResult.size.width / 2,
                        textY - textLayoutResult.size.height / 2
                    )
                )
            }

            startAngle += sweepAngle
        }
    }
}

@Composable
fun LegendItem(
    label: String,
    value: Float,
    color: Color,
    isSelected: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .padding(2.dp)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawCircle(
                    color = color,
                    alpha = if (isSelected) 1f else 0.7f
                )
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f),
            color = if (isSelected) MaterialTheme.colorScheme.primary else colorScheme.onSurface
        )

        Text(
            text = value.toInt().toString(),
            style = MaterialTheme.typography.bodyMedium,
            color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray
        )
    }
}
