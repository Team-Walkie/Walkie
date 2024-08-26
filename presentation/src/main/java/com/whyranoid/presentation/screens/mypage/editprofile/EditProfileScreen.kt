package com.whyranoid.presentation.screens.mypage.editprofile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.util.Log
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
import coil.request.ImageRequest
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EditProfileScreen(navController: NavController) {
    val viewModel = koinViewModel<EditProfileViewModel>()
    val walkieId = viewModel.walkieId.collectAsStateWithLifecycle(initialValue = 0L)

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val file = context.createImageFile()
    val uri = FileProvider.getUriForFile(
        context,
        "com.whyranoid.walkie.provider",
        file
    )

    val cameraLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                viewModel.setProfileUrl(uri.toString())
                viewModel.isChangeButtonEnabled.value = true
            }
        }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            uri?.let { uri ->
                cameraLauncher.launch(uri)
            }
        } else {
            // 권한 거부시
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            viewModel.setProfileUrl(result.data?.data.toString())
            viewModel.isChangeButtonEnabled.value = true
        }
    }

    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )

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
                        coroutineScope.launch {
                            bottomSheetState.hide()
                        }
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                WalkieBottomSheetButton(
                    buttonText = "앨범에서 프로필 사진 가져오기",
                    onClick = {
                        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                            addCategory(Intent.CATEGORY_OPENABLE)
                            type = "image/*"
                        }
                        galleryLauncher.launch(intent)
                        coroutineScope.launch {
                            bottomSheetState.hide()
                        }
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                WalkieBottomSheetButton(
                    buttonText = "현재 프로필 사진 삭제",
                    onClick = {
                        viewModel.setProfileUrl(null)
                        coroutineScope.launch {
                            bottomSheetState.hide()
                        }
                    }
                )
            }
        }
    ) {
        EditProfileContent(
            walkieId = walkieId.value ?: 0L,
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
    val userInfoUiState by viewModel.userInfoUiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getUserInfo()
    }

    LaunchedEffect(viewModel.isMyInfoChanged) {
        viewModel.isMyInfoChanged.collectLatest {
            SingleToast.show(context, "정보가 수정되었습니다.")
            popBackStack()
        }
    }

    LaunchedEffect(isDuplicateNickName) {
        if (isDuplicateNickName == false) viewModel.isChangeButtonEnabled.value = true
    }

    LaunchedEffect(keyboardHeight) {
        coroutineScope.launch {
            scrollState.scrollBy(keyboardHeight.toFloat())
        }
    }

    if (userInfoUiState != null) {
        val name by remember { mutableStateOf(userInfoUiState?.name) }
        var nickname by remember { mutableStateOf(userInfoUiState?.nickname) }
        val profileImg = userInfoUiState?.profileUrl

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
                    model = ImageRequest.Builder(context)
                        .data(profileImg)
                        .fallback(R.drawable.ic_default_profile)
                        .error(R.drawable.ic_default_profile)
                        .build(),
                    onError = {
                        Log.d("sm.shin", "error: ${it.result.throwable.message}")
                    },
                    contentDescription = "프로필 이미지",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(90.dp)
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
                text = name.orEmpty(),
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

            var isEditMode by remember { mutableStateOf(false) }

            val nickNameRegex = Regex("[a-zA-Z0-9_.]{0,29}")

            WalkieTextField(
                modifier = Modifier,
                focusRequester = focusRequester,
                text = nickname.orEmpty(),
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
                                    viewModel.checkDuplicateNickName(nickname.orEmpty())
                                    // 성공 시 editMode 해제
                                    focusManager.clearFocus()
                                }
                        )
                    }

                },
                onValueChange = {
                    if (it.matches(nickNameRegex)) {
                        nickname = it
                    } else {
                        SingleToast.show(context, "닉네임은 30자 이내로 영문,숫자,마침표,_만 입력해주세요.")
                    }
                }
            )

            Spacer(modifier = Modifier.height(40.dp))

            Spacer(modifier = Modifier.weight(1f))

            WalkiePositiveButton(
                text = "변경",
                isEnabled = viewModel.isChangeButtonEnabled.value,
                onClicked = {
                    viewModel.changeMyInfo(walkieId, nickname.orEmpty(), profileImg)
                }
            )

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}