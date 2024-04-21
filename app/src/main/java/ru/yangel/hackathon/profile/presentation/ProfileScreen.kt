package ru.yangel.hackathon.profile.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import org.koin.androidx.compose.koinViewModel
import ru.yangel.hackathon.R
import ru.yangel.hackathon.real_follows.ui.screen.YellowLoader
import ru.yangel.hackathon.navigation.utils.noRippleClickable
import ru.yangel.hackathon.profile.presentation.viewmodel.ProfileState
import ru.yangel.hackathon.profile.presentation.viewmodel.ProfileViewModel
import ru.yangel.hackathon.ui.common.PrimaryButton
import ru.yangel.hackathon.ui.theme.CodGray
import ru.yangel.hackathon.ui.theme.PaddingMedium
import ru.yangel.hackathon.ui.theme.Primary
import ru.yangel.hackathon.ui.theme.Type15
import ru.yangel.hackathon.ui.theme.Type24
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun ProfileScreen(
    userId: String,
    navController: NavController,
    profileViewModel: ProfileViewModel = koinViewModel()
) {
    val profileState = profileViewModel.profileState.collectAsStateWithLifecycle()
    val isUserAlreadySubscribed =
        profileViewModel.isUserAlreadySubscribed.collectAsStateWithLifecycle()
    var buttonText = ""
    var buttonAction: () -> Unit = {}
    var trailingIcon: Int? = R.drawable.filled_ic

    when (isUserAlreadySubscribed.value) {
        true -> {
            buttonText = "В отслеживаемом"
            buttonAction = { profileViewModel.unsubscribe(userId) }
            trailingIcon = R.drawable.filled_ic
        }

        false -> {
            buttonText = "Отслеживать"
            buttonAction = { profileViewModel.subscribe(userId) }
            trailingIcon = null
        }
    }


    when (val state = profileState.value) {
        is ProfileState.Initial -> {
            profileViewModel.getProfile(userId)
        }

        is ProfileState.Loading -> {
            YellowLoader()
        }

        is ProfileState.Success -> {
            val profileModel = state.profile

            Scaffold(
                floatingActionButton = {
                    AnimatedVisibility(
                        visible = isUserAlreadySubscribed.value,
                        enter = fadeIn(),
                        exit = fadeOut(),
                    ) {
                        FloatingActionButton(modifier = Modifier.padding(all = 24.dp),
                            containerColor = Primary,
                            contentColor = CodGray,
                            shape = RoundedCornerShape(16.dp),
                            onClick = { navController.navigate("wishlist/${profileModel.id}/${profileModel.fullName}/${profileModel.birthDate}/${URLEncoder.encode(profileModel.photoUrl, StandardCharsets.UTF_8.toString())}") }) {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.cart_icon),
                                contentDescription = null
                            )
                        }
                    }
                },
                modifier = Modifier.padding(top = 24.dp),
                topBar = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp, start = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.arrow_back),
                            modifier = Modifier.noRippleClickable { navController.popBackStack() },
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
                        Spacer(modifier = Modifier.width(PaddingMedium))
                        Text(text = "Профиль", style = Type24, color = CodGray)
                    }
                },
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    SubcomposeAsyncImage(
                        model = profileModel.photoUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(top = 24.dp)
                            .size(128.dp)
                            .clip(RoundedCornerShape(64.dp))
                    )
                    Text(
                        text = profileModel.fullName,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp),
                        textAlign = TextAlign.Center,
                        style = Type24
                    )
                    Text(
                        text = "Software Engineer",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        textAlign = TextAlign.Center,
                        style = Type15
                    )
                    Text(
                        text = convertDateFormat(profileModel.birthDate),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        textAlign = TextAlign.Center,
                        style = Type15
                    )
                    Text(
                        text = profileModel.affiliate?.name ?: "",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp),
                        textAlign = TextAlign.Center,
                        style = Type15
                    )
                    Text(
                        text = profileModel.command?.name ?: "Команда мобильного банка",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        textAlign = TextAlign.Center,
                        style = Type15
                    )
                    Spacer(modifier = Modifier.padding(24.dp))
                    PrimaryButton(
                        text = buttonText,
                        onClick = { buttonAction() },
                        trailingIconResId = trailingIcon,
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                    )
                }
            }
        }

        is ProfileState.Error -> {}
    }
}

fun convertDateFormat(inputDate: String): String {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val outputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    val date = LocalDate.parse(inputDate, inputFormatter)
    return date.format(outputFormatter)
}