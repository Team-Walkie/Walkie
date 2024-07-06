package com.whyranoid.presentation.screens.mypage.editprofile

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.whyranoid.domain.util.EMPTY
import com.whyranoid.presentation.R
import com.whyranoid.presentation.component.button.WalkiePositiveButton
import com.whyranoid.presentation.reusable.WalkieTextField
import com.whyranoid.presentation.theme.WalkieTypography
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun EditProfileScreen(navController: NavController) {
    val viewModel = koinViewModel<EditProfileViewModel>()
    val walkieId = viewModel.walkieId.collectAsStateWithLifecycle(initialValue = 0L)
    val name = viewModel.name.collectAsStateWithLifecycle(initialValue = String.EMPTY)
    val nick = viewModel.nick.collectAsStateWithLifecycle(initialValue = String.EMPTY)
    val profileImg = viewModel.profileImg.collectAsStateWithLifecycle(initialValue = String.EMPTY)

    EditProfileContent(
        walkieId = walkieId.value ?: 0L,
        name = name.value.orEmpty(),
        nick = nick.value.orEmpty(),
        profileImg = profileImg.value.orEmpty(),
        viewModel = viewModel,
    ) {
        navController.popBackStack()
    }
}

@Composable
fun EditProfileContent(
    walkieId: Long,
    name: String,
    nick: String,
    profileImg: String,
    viewModel: EditProfileViewModel,
    popBackStack: () -> Unit
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val focusRequester = remember { FocusRequester() }
    val keyboardHeight = WindowInsets.ime.getBottom(LocalDensity.current)
    val isDuplicateNickName by viewModel.isDuplicateNickName.collectAsStateWithLifecycle()
    var isChangeEnabled by remember { mutableStateOf(false) }

    LaunchedEffect(viewModel.isMyInfoChanged) {
        viewModel.isMyInfoChanged.collectLatest {
            Toast.makeText(context, "정보가 수정되었습니다.", Toast.LENGTH_SHORT).show()
            popBackStack()
        }
    }

    LaunchedEffect(isDuplicateNickName) {
        if (isDuplicateNickName == false) isChangeEnabled = true
    }

    LaunchedEffect(keyboardHeight) {
        coroutineScope.launch {
            scrollState.scrollBy(keyboardHeight.toFloat())
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            .verticalScroll(scrollState)
    ) {
        Box(
            Modifier.fillMaxSize()
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "프로필 편집",
                style = WalkieTypography.Title
            )
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = "close button",
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.CenterStart)
                    .clickable {
                        popBackStack()
                    },
            )
        }

        Spacer(modifier = Modifier.height(48.dp))

        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(90.dp),
        ) {
            AsyncImage(
                model = profileImg,
                contentDescription = "프로필 이미지",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .matchParentSize()
                    .clip(shape = CircleShape)
            )

            CircularIconButton(
                modifier = Modifier.align(Alignment.BottomEnd),
                onClick = { /* 버튼 클릭 시 동작 */ },
                contentDescription = "프로필 편집",
                icon = ImageVector.vectorResource(id = R.drawable.ic_edit_icon),
                tint = Color(0xFF999999),
                backgroundColor = Color(0xFFEEEEEE),
                iconSize = 24.dp,
                buttonSize = 30.dp,
            )
        }

        Spacer(modifier = Modifier.height(22.dp))

        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "프로필 사진 변경",
            style = WalkieTypography.Body1_SemiBold
        )

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            modifier = Modifier.align(Alignment.Start),
            text = "이름",
            style = WalkieTypography.Body1
        )

        Spacer(modifier = Modifier.height(10.dp))

        WalkieTextField(
            text = name,
            readOnly = true,
            focusRequester = focusRequester,
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            modifier = Modifier.align(Alignment.Start),
            text = "워키닉네임",
            style = WalkieTypography.Body1
        )

        Spacer(modifier = Modifier.height(10.dp))

        var nickName by remember { mutableStateOf(nick) }
        var isEditMode by remember { mutableStateOf(false) }

        LaunchedEffect(nick) {
            nickName = nick
        }

        val nickNameRegex = Regex("[a-zA-Z0-9_.]{0,29}")

        WalkieTextField(
            modifier = Modifier,
            focusRequester = focusRequester,
            text = nickName,
            isEnabled = isEditMode,
            isValidValue = isDuplicateNickName?.not(),
            trailings = {
                if (isEditMode.not()) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_edit_icon),
                        contentDescription = "edit icon",
                        modifier = Modifier
                            .clickable {
                                focusRequester.requestFocus()
                                isEditMode = true
                            }
                    )
                } else {
                    Text(
                        text = "확인",
                        style = WalkieTypography.Caption.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .clickable {
                                // 닉네임 중복 api 연결
                                viewModel.checkDuplicateNickName(nickName)
                                // 성공 시 editMode 해제
                            }
                    )
                }

            },
            onValueChange = {
                if (it.matches(nickNameRegex)) {
                    nickName = it
                } else {
                    Toast.makeText(context, "닉네임은 30자 이내로 영문,숫자,마침표,_만 입력해주세요.", Toast.LENGTH_SHORT).show()
                }
            }
        )

        Spacer(modifier = Modifier.height(40.dp))

        Spacer(modifier = Modifier.weight(1f))

        WalkiePositiveButton(
            text = "변경",
            isEnabled = isChangeEnabled,
            onClicked = {
                viewModel.changeMyInfo(walkieId, nickName, profileImg)
            }
        )

        Spacer(modifier = Modifier.height(20.dp))
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
