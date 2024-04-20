package ru.yangel.hackathon.calendar.presentation.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.kizitonwose.calendar.core.DayPosition
import ru.yangel.hackathon.R
import ru.yangel.hackathon.calendar.presentation.screen.components.Calendar
import ru.yangel.hackathon.calendar.presentation.screen.samples.CalendarSamples
import ru.yangel.hackathon.navigation.utils.noRippleClickable
import ru.yangel.hackathon.ui.common.PrimaryButton
import ru.yangel.hackathon.ui.theme.CodGray
import ru.yangel.hackathon.ui.theme.Nevada
import ru.yangel.hackathon.ui.theme.PaddingMedium
import ru.yangel.hackathon.ui.theme.PaddingRegular
import ru.yangel.hackathon.ui.theme.PaddingSmall
import ru.yangel.hackathon.ui.theme.Type13
import ru.yangel.hackathon.ui.theme.Type24
import ru.yangel.hackathon.ui.theme.White
import java.time.LocalDate

@Composable
fun CalendarScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val birthdays = remember {
        mutableStateListOf(
            com.kizitonwose.calendar.core.CalendarDay(
                date = LocalDate.now().minusDays(15),
                position = DayPosition.MonthDate
            ),
            com.kizitonwose.calendar.core.CalendarDay(
                date = LocalDate.now().minusDays(9),
                position = DayPosition.MonthDate
            ),
            com.kizitonwose.calendar.core.CalendarDay(
                date = LocalDate.now().plusDays(2),
                position = DayPosition.MonthDate
            ),
            com.kizitonwose.calendar.core.CalendarDay(
                date = LocalDate.now().plusDays(4),
                position = DayPosition.MonthDate
            ),
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(White)
            .padding(
                horizontal = PaddingMedium,
                vertical = PaddingRegular
            ),
        verticalArrangement = Arrangement.spacedBy(PaddingRegular)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Ваш календарь",
                    color = CodGray,
                    style = Type24
                )

                Icon(
                    modifier = Modifier.noRippleClickable { /* TODO navigate to settings*/ },
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_settings),
                    tint = Nevada,
                    contentDescription = null
                )
            }

            Spacer(modifier = Modifier.height(PaddingSmall))

            Text(
                text = "Здесь отображаются дни рождения ваших коллег",
                color = Nevada,
                style = Type13
            )
        }

        Calendar(
            birthdays = birthdays,
            onBirthdayClick = {
                // TODO invoke vm method, open bottom sheet
            }
        )

        CalendarSamples()

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_info),
                tint = Nevada,
                contentDescription = null
            )

            Spacer(modifier = Modifier.width(PaddingSmall))

            Text(
                text = "Нажмите на день, чтобы посмотреть, у кого из коллег день рождения",
                color = Nevada,
                style = Type13
            )
        }
        
        PrimaryButton(
            text = "Экспортировать в календарь",
            onClick = {
                Toast.makeText(
                    context,
                    "Функционал появится позже!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        )
    }
}

@Preview
@Composable
private fun CalendarScreenPreview() {
    CalendarScreen()
}