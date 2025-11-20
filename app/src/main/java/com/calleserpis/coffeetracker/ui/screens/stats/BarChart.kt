package com.example.app.ui.components

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import android.graphics.Paint
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.ui.text.style.TextAlign
import kotlin.collections.forEachIndexed

data class BarData(
    val txtBar: String,
    val txtLarge: String,
    val value: Float
)

@Composable
fun BarChart(
    data: List<BarData>,
    modifier: Modifier = Modifier,
    title: String = "Últimos 12 Meses",
    valueLabel: String = "Cafés"
) {
    var selectedIndex by remember { mutableStateOf(-1) }
    var animationPlayed by remember { mutableStateOf(false) }

    val animatedProgress by animateFloatAsState(
        targetValue = if (animationPlayed) 1f else 0f,
        animationSpec = tween(
            durationMillis = 1200,
            easing = LinearOutSlowInEasing
        ),
        label = "bar_animation"
    )

    LaunchedEffect(Unit) {
        animationPlayed = true
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            textAlign = TextAlign.Justify,
            style = MaterialTheme.typography.titleMedium,
            color = colorScheme.primary,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        if (selectedIndex >= 0 && selectedIndex < data.size) {
            Text(
                text = "${data[selectedIndex].txtLarge} : ${data[selectedIndex].value.toInt()} $valueLabel",
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFF8B5A3C),
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        } else {
            Spacer(modifier = Modifier.height(40.dp))
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures { offset ->
                            val barWidth = (size.width - 80f) / data.size
                            val index = ((offset.x - 40f) / barWidth).toInt()

                            if (index in data.indices) {
                                selectedIndex = if (selectedIndex == index) -1 else index
                            }
                        }
                    }
            ) {
                val canvasWidth = size.width
                val canvasHeight = size.height
                val padding = 40f
                val bottomPadding = 60f
                val chartWidth = canvasWidth - (padding * 2)
                val chartHeight = canvasHeight - bottomPadding - padding

                val maxValue = data.maxOfOrNull { it.value } ?: 1f
                val barWidth = chartWidth / data.size
                val barSpacing = barWidth * 0.2f
                val actualBarWidth = barWidth - barSpacing

                // Líneas horizontales de referencia (grid)
                val gridLines = 5
                val textPaint = Paint().apply {
                    color = android.graphics.Color.GRAY
                    textSize = 28f
                    textAlign = Paint.Align.RIGHT
                }

                for (i in 0..gridLines) {
                    val y = padding + (chartHeight * i / gridLines)
                    val gridValue = maxValue * (1f - i.toFloat() / gridLines)

                    // Línea del grid
                    drawLine(
                        color = Color.Gray.copy(alpha = 0.15f),
                        start = Offset(padding, y),
                        end = Offset(canvasWidth - padding, y),
                        strokeWidth = 1f
                    )

                    // Valor del grid
                    drawIntoCanvas { canvas ->
                        canvas.nativeCanvas.drawText(
                            "${gridValue.toInt()}",
                            padding - 30f,
                            y + 5f,
                            textPaint
                        )
                    }
                }

                // Línea base
                drawLine(
                    color = Color.Gray.copy(alpha = 0.3f),
                    start = Offset(padding, padding + chartHeight),
                    end = Offset(canvasWidth - padding, padding + chartHeight),
                    strokeWidth = 2f
                )

                // Barras
                val monthTextPaint = Paint().apply {
                    textSize = 30f
                    textAlign = Paint.Align.CENTER
                }

                val valueTextPaint = Paint().apply {
                    color = Color(0xFF8B5A3C).toArgb()
                    textSize = 36f
                    textAlign = Paint.Align.CENTER
                    isFakeBoldText = true
                }

                data.forEachIndexed { index, monthData ->
                    val x = padding + (index * barWidth) + (barSpacing / 2)
                    val normalizedHeight = (monthData.value / maxValue) * chartHeight
                    val animatedHeight = normalizedHeight * animatedProgress
                    val y = padding + chartHeight - animatedHeight

                    val isSelected = selectedIndex == index
                    val barColor = if (isSelected) {
                        Color(0xFF10B981)
                    } else {
                        Color(0xFFF59E0B)
                    }

                    val barWidthAdjusted = if (isSelected) actualBarWidth * 1.1f else actualBarWidth
                    val xAdjusted = if (isSelected) x - (barWidthAdjusted - actualBarWidth) / 2 else x

                    // Sombra de la barra
                    drawRoundRect(
                        color = Color.Black.copy(alpha = 0.1f),
                        topLeft = Offset(xAdjusted + 4f, y + 4f),
                        size = Size(barWidthAdjusted, animatedHeight),
                        cornerRadius = CornerRadius(8f, 8f)
                    )

                    // Gradiente de la barra
                    val gradient = Brush.verticalGradient(
                        colors = listOf(
                            barColor.copy(alpha = 0.7f),
                            barColor
                        ),
                        startY = y,
                        endY = y + animatedHeight
                    )

                    // Barra principal
                    drawRoundRect(
                        brush = gradient,
                        topLeft = Offset(xAdjusted, y),
                        size = Size(barWidthAdjusted, animatedHeight),
                        cornerRadius = CornerRadius(8f, 8f)
                    )

                    // Borde de la barra
                    drawRoundRect(
                        color = barColor.copy(alpha = 0.3f),
                        topLeft = Offset(xAdjusted, y),
                        size = Size(barWidthAdjusted, animatedHeight),
                        cornerRadius = CornerRadius(8f, 8f),
                        style = Stroke(width = 2f)
                    )

                    // Destello superior
                    if (animatedProgress > 0.8f && animatedHeight > 20f) {
                        drawRoundRect(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.White.copy(alpha = 0.4f),
                                    Color.Transparent
                                ),
                                startY = y,
                                endY = y + animatedHeight * 0.3f
                            ),
                            topLeft = Offset(xAdjusted + 4f, y + 4f),
                            size = Size(barWidthAdjusted * 0.5f, animatedHeight * 0.25f),
                            cornerRadius = CornerRadius(6f, 6f)
                        )
                    }

                    // Valor sobre la barra si está seleccionada
                    if (isSelected && animatedProgress > 0.9f) {
                        drawIntoCanvas { canvas ->
                            canvas.nativeCanvas.drawText(
                                "${monthData.value.toInt()}",
                                xAdjusted + barWidthAdjusted / 2,
                                y - 10f,
                                valueTextPaint
                            )
                        }
                    }

                    // Etiqueta del mes
                    drawIntoCanvas { canvas ->
                        monthTextPaint.color = if (isSelected) {
                            Color(0xFF8B5A3C).toArgb()
                        } else {
                            android.graphics.Color.GRAY
                        }
                        monthTextPaint.isFakeBoldText = isSelected

                        canvas.nativeCanvas.drawText(
                            monthData.txtBar,
                            x + actualBarWidth / 2,
                            canvasHeight - 25f,
                            monthTextPaint
                        )
                    }
                }
            }
        }

        Text(
            text = "Toca una barra para ver detalles",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

