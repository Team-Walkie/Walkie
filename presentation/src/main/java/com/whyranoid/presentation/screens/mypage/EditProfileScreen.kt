package com.whyranoid.presentation.screens.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.whyranoid.presentation.theme.WalkieTypography

@Composable
fun EditProfileScreen(name: String = "", nick: String = "", navController: NavController) {
    EditProfileContent(name = name, nick = nick) {
        navController.popBackStack()
    }
}

@Composable
fun EditProfileContent(name: String, nick: String, onCloseClicked: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
    ) {
        Box(
            Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "프로필 편집",
                fontStyle = WalkieTypography.Title.fontStyle,
                fontSize = WalkieTypography.Title.fontSize,
                fontFamily = WalkieTypography.Title.fontFamily,
                fontWeight = WalkieTypography.Title.fontWeight,
            )
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = "close button",
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.CenterStart)
                    .clickable {
                    },
            )
        }

        Spacer(modifier = Modifier.height(48.dp))

        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(90.dp),
        ) {
            Image(
                painter = painterResource(com.google.android.material.R.drawable.ic_mtrl_chip_checked_circle),
                contentDescription = "프로필 이미지",
                modifier = Modifier.run {
                    matchParentSize()
                        .clip(shape = CircleShape)
                        .border(1.dp, Color.Gray, CircleShape)
                },
            )
            CircularIconButton(
                modifier = Modifier.align(Alignment.BottomEnd),
                onClick = { /* 버튼 클릭 시 동작 */ },
                contentDescription = "프로필 편집",
                icon = Icons.Default.Edit,
                tint = Color(0xFFEEEEEE),
                backgroundColor = Color(0xFF989898),
                iconSize = 24.dp,
                buttonSize = 30.dp,
            )
        }

        Spacer(modifier = Modifier.height(36.dp))

        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "프로필 사진 변경",
            fontWeight = WalkieTypography.Title.fontWeight,
        )

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            modifier = Modifier.align(Alignment.Start),
            text = "이름 변경",
            fontWeight = WalkieTypography.Title.fontWeight,
        )

        Spacer(modifier = Modifier.height(16.dp))

        CheckableCustomTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(34.dp)
                .background(
                    Color(0xFFEEEEEE),
                    RoundedCornerShape(10.dp),
                )
                .padding(horizontal = 8.dp),
            initialText = name,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Edit Icon",
                    tint = Color(0xFF989898),
                )
            },
            placeholderText = "이름",
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            modifier = Modifier.align(Alignment.Start),
            text = "닉네임 변경",
            fontWeight = WalkieTypography.Title.fontWeight,
        )

        Spacer(modifier = Modifier.height(16.dp))

        // TODO 임시로 만든 변수, viewModel, uiState로 관리하도록 변경 필요(139, 171 line)
        var nickCheckState: Boolean? by rememberSaveable {
            mutableStateOf(null)
        }

        CheckableCustomTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(34.dp)
                .background(
                    Color(0xFFEEEEEE),
                    RoundedCornerShape(10.dp),
                )
                .padding(horizontal = 8.dp),
            initialText = nick,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Edit Icon",
                    tint = Color(0xFF989898),
                )
            },
            placeholderText = "닉네임",
            checkButton = { text ->
                Text(
                    text = "확인",
                    modifier = Modifier.clickable {
                        nickCheckState = text.length > 3
                    },
                )
            },
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (nickCheckState == true) {
            Text(
                text = "사용 가능한 닉네임입니다.",
                color = Color.Blue,
                fontSize = WalkieTypography.Body2.fontSize,
            )
        } else if (nickCheckState == false) {
            Text(
                text = "이미 사용 중인 닉네임입니다.",
                color = Color.Red,
                fontSize = WalkieTypography.Body2.fontSize,
            )
        }
    }
}

@Composable
fun CircularIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    icon: ImageVector,
    tint: Color = Color.White,
    backgroundColor: Color = Color.Gray,
    iconSize: Dp = 24.dp,
    buttonSize: Dp = 48.dp,
) {
    Box(
        modifier = modifier
            .size(buttonSize)
            .clip(CircleShape)
            .background(backgroundColor)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = tint,
            modifier = Modifier.size(iconSize),
        )
    }
}

// TODO: 커스텀 텍스트 필드 변경 요망, 텍스트를 외부로 부터 받고 안내 메세지도 받도록 변경해야 함
@Composable
private fun CheckableCustomTextField(
    modifier: Modifier = Modifier,
    initialText: String = "",
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    placeholderText: String = "Placeholder",
    fontSize: TextUnit = MaterialTheme.typography.bodyMedium.fontSize,
    checkButton: (@Composable (String) -> Unit)? = null,
) {
    var text by rememberSaveable { mutableStateOf(initialText) }
    BasicTextField(
        modifier = modifier
            .background(
                MaterialTheme.colorScheme.surface,
                MaterialTheme.shapes.small,
            )
            .fillMaxWidth(),
        value = text,
        onValueChange = {
            text = it
        },
        singleLine = true,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        textStyle = LocalTextStyle.current.copy(
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = fontSize,
        ),
        decorationBox = { innerTextField ->
            Row(
                modifier,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (leadingIcon != null) leadingIcon()
                Box(Modifier.weight(1f)) {
                    if (text.isEmpty()) {
                        Text(
                            placeholderText,
                            style = LocalTextStyle.current.copy(
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                                fontSize = fontSize,
                            ),
                        )
                    }
                    innerTextField()
                }
                if (trailingIcon != null && checkButton == null) {
                    trailingIcon()
                } else if (trailingIcon != null && checkButton != null && text.isEmpty()) {
                    trailingIcon()
                } else if (checkButton != null && text.isEmpty().not()) checkButton(text)
            }
        },
    )
}
