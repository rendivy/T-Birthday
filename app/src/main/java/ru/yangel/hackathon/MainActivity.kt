package ru.yangel.hackathon

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import ru.yangel.hackathon.notification.database.DateDatabase
import ru.yangel.hackathon.notification.database.entity.SubscribedUser
import ru.yangel.hackathon.notification.workmanager.BirthdayAlarmReceiver
import ru.yangel.hackathon.notification.workmanager.NotificationHelper
import ru.yangel.hackathon.ui.theme.HackathonTheme
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.Calendar

class MainActivity : ComponentActivity(), KoinComponent {

    private lateinit var database: DateDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = Room.databaseBuilder(
            this,
            DateDatabase::class.java, "database-name"
        ).build()
        setDailyAlarm()
        sendTestNotification()
        setContent {
            HackathonTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }

    private fun setDailyAlarm() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val users = emptyList<SubscribedUser>().toMutableList()
        CoroutineScope(Dispatchers.IO).launch {
            users += database.subscribedUserDao().getAll()
        }
        val upcomingBirthdays = users.filter {
            val birthdayThisYear = it.dateOfBirth.withYear(LocalDate.now().year)
            ChronoUnit.DAYS.between(LocalDate.now(), birthdayThisYear) == 12L
        }

        upcomingBirthdays.forEach { user ->
            val intent = Intent(this, BirthdayAlarmReceiver::class.java)
            intent.putExtra("userId", user.userId)
            val pendingIntent = PendingIntent.getBroadcast(
                this,
                user.userId.toInt(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

            val calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, 12)
                set(Calendar.MINUTE, 0)
            }

            alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )
        }

    }

    private fun sendTestNotification() {
        val notificationHelper = NotificationHelper(this)
        val notification = notificationHelper.createNotification()

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NotificationHelper.NOTIFICATION_ID, notification)
    }

}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HackathonTheme {
        Greeting("Android")
    }
}