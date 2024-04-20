package ru.yangel.hackathon.calendar.presentation.screen.components

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import com.kizitonwose.calendar.core.CalendarDay
import ru.yangel.hackathon.R
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

@Composable
fun CalendarContent(
    onBirthdayClick: (CalendarDay) -> Unit,
    birthdays: List<CalendarDay>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
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
                modifier = modifier.fillMaxWidth(),
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
            onBirthdayClick = onBirthdayClick
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