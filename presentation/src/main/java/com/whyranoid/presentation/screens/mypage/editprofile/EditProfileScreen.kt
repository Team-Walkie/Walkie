package com.whyranoid.presentation.screens.mypage.editprofile

import android.Manifest
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.rememberModalBottomSheetState
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.whyranoid.domain.util.EMPTY
import com.whyranoid.presentation.R
import com.whyranoid.presentation.component.button.CircularIconButton
import com.whyranoid.presentation.component.button.WalkieBottomSheetButton
import com.whyranoid.presentation.component.button.WalkiePositiveButton
import com.whyranoid.presentation.reusable.SingleToast
import com.whyranoid.presentation.reusable.WalkieTextField
import com.whyranoid.presentation.theme.WalkieTypography
import com.whyranoid.presentation.util.createImageFile
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.util.Objects

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EditProfileScreen(navController: NavController) {
    val viewModel = koinViewModel<EditProfileViewModel>()
    val walkieId = viewModel.walkieId.collectAsStateWithLifecycle(initialValue = 0L)
    val name = viewModel.name.collectAsStateWithLifecycle(initialValue = String.EMPTY)
    val nick = viewModel.nick.collectAsStateWithLifecycle(initialValue = String.EMPTY)
    val context = LocalContext.current

    LaunchedEffect(viewModel.profileImg) {
        viewModel.profileImg.collectLatest {
            it?.let { url -> viewModel.setProfileUrl(url) }
        }
    }

    val file = context.createImageFile()
    val uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        context.packageName + ".provider", file
    )

    val cameraLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicture()) {
        viewModel.setProfileUrl(uri.toString())
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            cameraLauncher.launch(uri)
        } else {
            // 권한 거부시
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) {
        it?.let { uri -> viewModel.setProfileUrl(uri.toString()) }
    }

    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )

    val coroutineScope = rememberCoroutineScope()

    BackHandler(enabled = bottomSheetState.isVisible) {
        coroutineScope.launch {
            bottomSheetState.hide()
        }
    }

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetContent = {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(start = 20.dp, end = 20.dp, top = 18.dp, bottom = 26.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "프로필 사진 변경",
                    style = WalkieTypography.SubTitle
                )

                Spacer(modifier = Modifier.height(15.dp))

                WalkieBottomSheetButton(
                    buttonText = "새 프로필 사진 찍기",
                    onClick = {
                        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                WalkieBottomSheetButton(
                    buttonText = "앨범에서 프로필 사진 가져오기",
                    onClick = {
                        galleryLauncher.launch("image/*")
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                WalkieBottomSheetButton(
                    buttonText = "현재 프로필 사진 삭제",
                    onClick = {
                        viewModel.setProfileUrl(null)
                    }
                )
            }
        }
    ) {
        EditProfileContent(
            walkieId = walkieId.value ?: 0L,
            name = name.value.orEmpty(),
            nick = nick.value.orEmpty(),
            bottomSheetState = bottomSheetState,
            viewModel = viewModel,
        ) {
            navController.popBackStack()
        }
    }


}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EditProfileContent(
    walkieId: Long,
    name: String,
    nick: String,
    bottomSheetState: ModalBottomSheetState,
    viewModel: EditProfileViewModel,
    popBackStack: () -> Unit
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardHeight = WindowInsets.ime.getBottom(LocalDensity.current)
    val isDuplicateNickName by viewModel.isDuplicateNickName.collectAsStateWithLifecycle()
    val currentProfileImg by viewModel.currentProfileUrl.collectAsStateWithLifecycle()
    var isChangeEnabled by remember { mutableStateOf(false) }

    var initialProfileImg by remember { mutableStateOf<String?>(null) }
    LaunchedEffect(viewModel.profileImg) {
        viewModel.profileImg.collectLatest {
            initialProfileImg = it
        }
    }

    LaunchedEffect(currentProfileImg) {
        if (currentProfileImg != initialProfileImg) {
            isChangeEnabled = true
        }
    }

    LaunchedEffect(viewModel.isMyInfoChanged) {
        viewModel.isMyInfoChanged.collectLatest {
            SingleToast.show(context, "정보가 수정되었습니다.")
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
                model = currentProfileImg,
                contentDescription = "프로필 이미지",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .matchParentSize()
                    .clip(shape = CircleShape)
            )

            CircularIconButton(
                modifier = Modifier.align(Alignment.BottomEnd),
                onClick = {
                    coroutineScope.launch {
                        bottomSheetState.show()
                    }
                },
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
                                viewModel.checkDuplicateNickName(nickName)
                                // 성공 시 editMode 해제
                                focusManager.clearFocus()
                            }
                    )
                }

            },
            onValueChange = {
                if (it.matches(nickNameRegex)) {
                    nickName = it
                } else {
                    SingleToast.show(context, "닉네임은 30자 이내로 영문,숫자,마침표,_만 입력해주세요.")
                }
            }
        )

        Spacer(modifier = Modifier.height(40.dp))

        Spacer(modifier = Modifier.weight(1f))

        WalkiePositiveButton(
            text = "변경",
            isEnabled = isChangeEnabled,
            onClicked = {
                viewModel.changeMyInfo(walkieId, nickName, currentProfileImg)
            }
        )

        Spacer(modifier = Modifier.height(20.dp))
    }
}