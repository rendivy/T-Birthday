package ru.yangel.hackathon.calendar.presentation.screen.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import ru.yangel.hackathon.R
import ru.yangel.hackathon.calendar.presentation.utils.noRippleInteractionSource
import ru.yangel.hackathon.ui.theme.AliceBlue
import ru.yangel.hackathon.ui.theme.CodGray
import ru.yangel.hackathon.ui.theme.Primary
import ru.yangel.hackathon.ui.theme.PrimaryAlt
import ru.yangel.hackathon.ui.theme.Type15

@Composable
fun CalendarDay(
    day: CalendarDay,
    isBirthday: Boolean,
    isToday: Boolean,
    onClick: (CalendarDay) -> Unit
) {
    val isInRange = day.position == DayPosition.MonthDate
    if (!isInRange) return

    Card(
        modifier = Modifier
            .padding(
                horizontal = 8.dp,
                vertical = 5.dp
            )
            .aspectRatio(1f),
        onClick = {
            onClick(day)
        },
        colors = CardDefaults.cardColors(
            containerColor = if (isToday) {
                PrimaryAlt
            } else if (isBirthday) {
                Primary
            } else {
                AliceBlue
            }
        ),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        interactionSource = if (!isBirthday) {
            noRippleInteractionSource
        } else {
            remember { MutableInteractionSource() }
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (isBirthday) {
                Icon(
                    modifier = Modifier
                        .size(20.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_congratulations),
                    tint = CodGray,
                    contentDescription = null
                )
            } else {
                Text(
                    text = day.date.dayOfMonth.toString(),
                    color = CodGray,
                    style = Type15
                )
            }
        }
    }
}