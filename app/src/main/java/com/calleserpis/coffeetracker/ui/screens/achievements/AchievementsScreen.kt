package com.calleserpis.coffeetracker.ui.screens.achievements

import android.R.attr.fontStyle
import android.R.attr.logo
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.calleserpis.coffeetracker.R
import com.calleserpis.coffeetracker.domain.model.Achievement
import com.calleserpis.coffeetracker.domain.model.AchievementDefinition
import com.calleserpis.coffeetracker.ui.common.EmptyData

@Composable
fun AchievementsScreen(
    viewModel: AchievementsViewModel = hiltViewModel()
) {
    val state: AchievementsState by viewModel.state.collectAsStateWithLifecycle()

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(IntrinsicSize.Min)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        if (state.allAchievements.isEmpty()) {
            EmptyData()

        } else {
            state.allAchievements.forEach { achievement ->
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
                    var logro = AchievementDefinition.ALL.find { it.id == achievement.id }
                    Text(
                        text = stringResource(logro!!.titleRes),
                        textAlign = TextAlign.Justify,
                        style = MaterialTheme.typography.titleLarge,
                        color = colorScheme.primary,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 14.dp, start = 14.dp, end = 14.dp),
                    )
                    Text(
                        text = stringResource(logro!!.descriptionRes),
                        textAlign = TextAlign.Justify,
                        style = MaterialTheme.typography.bodySmall,
                        fontStyle = FontStyle.Italic,
                        color = colorScheme.onSurface,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                    )
                    Image(
                        painter = painterResource(id = logro!!.iconRes),
                        contentDescription = null,
                        modifier = Modifier.padding(16.dp)
                    )

                }
            }
        }
    }

}