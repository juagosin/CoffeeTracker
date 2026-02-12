package com.calleserpis.coffeetracker.ui.screens.achievements

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.calleserpis.coffeetracker.R

@Composable
fun AchievementsScreen() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ){



        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = colorScheme.surfaceContainerLow,
                contentColor = colorScheme.onSurfaceVariant
            ),
        ) {
            Text(
                text = stringResource(R.string.achievement_first_coffee_title),
                textAlign = TextAlign.Justify,
                style = MaterialTheme.typography.titleLarge,
                color = colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 14.dp, start = 14.dp, end = 14.dp),
            )
            Text(
                text = stringResource(R.string.achievement_first_coffee_description),
                textAlign = TextAlign.Justify,
                style = MaterialTheme.typography.bodySmall,
                fontStyle = FontStyle.Italic,
                color = colorScheme.onSurface,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
            )
            Image(
                painter = painterResource(id = R.drawable.ic_achievement_first),
                contentDescription = null,
                modifier = Modifier.padding(16.dp)
            )

        }
    }

}