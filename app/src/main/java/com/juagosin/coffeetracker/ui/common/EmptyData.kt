package com.juagosin.coffeetracker.ui.common

import android.R.attr.text
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.juagosin.coffeetracker.R

@Composable
fun EmptyData(txtTitle: String = "No hay registros de consumo de café") {
    Box(
        ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Image(
                painterResource(R.drawable.sadcoffecup),
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 8.dp)

            )
            Text(
                text = txtTitle,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                color = colorScheme.primary,
                modifier = Modifier.padding(bottom = 8.dp).fillMaxWidth()
            )
        }
    }
}