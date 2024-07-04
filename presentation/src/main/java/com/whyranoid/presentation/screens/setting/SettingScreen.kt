package com.whyranoid.presentation.screens.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.whyranoid.domain.model.user.User
import com.whyranoid.presentation.screens.Screen
import com.whyranoid.presentation.theme.WalkieColor
import com.whyranoid.presentation.theme.WalkieTypography
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreen(navHostController: NavHostController) {
    val viewModel = koinViewModel<SettingViewModel>()
    val user = viewModel.user.collectAsState()

    val scrollState = rememberScrollState()

    user.value?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            TopAppBar { navHostController.popBackStack() }
            ProfileSection(it) {
                navHostController.navigate(Screen.EditProfileScreen.route) }
            SettingsList()
        }
    }
}

@Composable
fun TopAppBar(
    onBackClicked: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            modifier = Modifier.clickable { onBackClicked() }
        )
        Spacer(modifier = Modifier.width(16.dp))
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "설정", style = MaterialTheme.typography.h6)
        }
    }
}

@Composable
fun ProfileSection(
    user: User,
    onProfileEditClicked: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(vertical = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = user.imageUrl,
            contentDescription = "Profile Image",
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = user.nickname, style = MaterialTheme.typography.subtitle1)
            Text(text = user.name, style = MaterialTheme.typography.body2)
        }
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                onProfileEditClicked()
            }
            .background(WalkieColor.GrayBackground)
            .padding(12.dp)
    ) {
        Text(
            text = "프로필 편집",
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.button
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun SettingsList() {
    val settingsGroups = listOf(
        "내 정보" to listOf("내 계정", "내 신체 정보"),
        "소셜" to listOf("프로필 공개 설정", "친구 관리"),
        "앱 설정" to listOf("러닝 설정", "알림 설정", "푸시 설정"),
        "앱 관리" to listOf("고객센터", "라이센스", "이용약관 및 운영정책", "버전 정보")
    )


    settingsGroups.forEach { (groupTitle, items) ->
        Spacer(modifier = Modifier.height(8.dp))
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .background(WalkieColor.GrayBackground)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = groupTitle,
            style = WalkieTypography.Body1_SemiBold,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .padding(horizontal = 20.dp)
        )
        items.forEach { item ->
            SettingsItem(text = item)
        }
    }


    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(8.dp)
            .background(WalkieColor.GrayBackground)
    )

    Text(
        text = "로그아웃",
        style = MaterialTheme.typography.button,
        modifier = Modifier
            .padding(vertical = 16.dp)
            .padding(horizontal = 20.dp),
        textAlign = TextAlign.Center
    )


    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(8.dp)
            .background(WalkieColor.GrayBackground)
    )

    Text(
        text = "계정 삭제",
        style = MaterialTheme.typography.button,
        modifier = Modifier
            .padding(vertical = 16.dp)
            .padding(horizontal = 20.dp),
        textAlign = TextAlign.Center
    )

    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(8.dp)
            .background(WalkieColor.GrayBackground)
    )
}

@Composable
fun SettingsItem(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = text, style = MaterialTheme.typography.body1)
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = "Navigate"
        )
    }
}