package ru.yangel.hackathon.calendar.presentation.screen.samples

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import ru.yangel.hackathon.R
import ru.yangel.hackathon.calendar.presentation.utils.noRippleInteractionSource
import ru.yangel.hackathon.ui.theme.CodGray
import ru.yangel.hackathon.ui.theme.Nevada
import ru.yangel.hackathon.ui.theme.PaddingSmall
import ru.yangel.hackathon.ui.theme.Primary
import ru.yangel.hackathon.ui.theme.SampleCalendarDaySize
import ru.yangel.hackathon.ui.theme.Type13

@Composable
fun BirthdayCalendarDaySample() {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            modifier = Modifier.size(SampleCalendarDaySize),
            onClick = {},
            colors = CardDefaults.cardColors(
                containerColor = Primary
            ),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(2.dp),
            interactionSource = noRippleInteractionSource
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier
                        .size(20.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_congratulations),
                    tint = CodGray,
                    contentDescription = null
                )
            }
        }

        Spacer(modifier = Modifier.width(PaddingSmall))

        Text(
            text = "День рождения!",
            style = Type13,
            color = Nevada
        )
    }
}