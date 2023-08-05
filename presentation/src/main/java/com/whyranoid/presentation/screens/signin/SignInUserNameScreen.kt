package com.whyranoid.presentation.screens.signin

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whyranoid.presentation.reusable.CheckableCustomTextField
import com.whyranoid.presentation.theme.SystemColor
import com.whyranoid.presentation.theme.WalkieColor
import com.whyranoid.presentation.theme.WalkieTypography
import com.whyranoid.presentation.viewmodel.SignInState
import com.whyranoid.presentation.viewmodel.SignInViewModel
import org.koin.androidx.compose.koinViewModel
import java.util.*

@Composable
fun SignInUserNameScreen(onSuccess: () -> Unit) {
    val viewModel = koinViewModel<SignInViewModel>()
    val signInState = viewModel.signInState.collectAsStateWithLifecycle()
    val userNameState = signInState.value as SignInState.UserNameState

    Surface(
        modifier = Modifier.background(Color.White).padding(20.dp),
    ) {
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.Start) {
            Spacer(modifier = Modifier.height(68.dp))
            Text("환영합니다!", style = WalkieTypography.Title)
            Text("회원님의 정보를 입력해주세요.", style = WalkieTypography.Body1_Normal)
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                "이름",
                style = WalkieTypography.Body1_Normal,
                modifier = Modifier.padding(bottom = 10.dp),
            )

            CheckableCustomTextField(
                text = userNameState.name,
                onTextChanged = { text -> viewModel.setUserNameState(userNameState.copy(name = text)) },
                modifier = Modifier.fillMaxWidth().height(40.dp).background(Color.White)
                    .border(1.dp, WalkieColor.GrayDefault, RoundedCornerShape(12.dp)),
                trailingIcon = null,
                placeholderText = "",
                checkButton = null,
                textStyle = WalkieTypography.Body1_ExtraBold,
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "닉네임(계정이름)",
                style = WalkieTypography.Body1_Normal,
                modifier = Modifier.padding(bottom = 10.dp),
            )
            CheckableCustomTextField(
                text = userNameState.nickName,
                onTextChanged = { text -> viewModel.setUserNameState(userNameState.copy(nickName = text)) },
                modifier = Modifier.fillMaxWidth().height(40.dp).background(Color.White)
                    .border(1.dp, WalkieColor.GrayDefault, RoundedCornerShape(12.dp)),
                trailingIcon = null,
                placeholderText = "",
                checkButton = { text ->
                    Text(
                        text = "중복확인",
                        modifier = Modifier.clickable {
                            viewModel.checkDupNickName()
                        },
                        color = WalkieColor.Primary,
                    )
                },
                textStyle = WalkieTypography.Body1_ExtraBold,
            )
            Box(Modifier.height(20.dp)) {
                userNameState.isDuplicated?.let { isDuplicated ->
                    Text(
                        text = if (isDuplicated) "중복된 닉네임입니다." else "사용가능한 닉네임입니다.",
                        style = WalkieTypography.Caption.copy(if (isDuplicated) SystemColor.Error else SystemColor.Positive),
                    )
                }
            }

            Text(
                "생년월일",
                style = WalkieTypography.Body1_Normal,
                modifier = Modifier.padding(bottom = 10.dp),
            )

            val context = LocalContext.current
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().height(40.dp).clip(RoundedCornerShape(12.dp))
                    .clickable(role = Role.Image) {
                        datePicker(
                            context,
                            userNameState.year,
                            userNameState.month,
                            userNameState.day,
                        ) { year, month, day ->
                            viewModel.setUserNameState(
                                userNameState.copy(
                                    year = year,
                                    month = month,
                                    day = day,
                                ),
                            )
                        }
                    },
            ) {
                Text(
                    text = if (userNameState.year != null) "${userNameState.year}년" else "-",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(96.dp).fillMaxHeight().border(
                        1.dp,
                        WalkieColor.GrayDefault,
                        RoundedCornerShape(12.dp),
                    ).padding(top = 8.dp),
                    style = WalkieTypography.Body1_ExtraBold,
                )
                Text(
                    style = WalkieTypography.Body1_ExtraBold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(96.dp).fillMaxHeight().border(
                        1.dp,
                        WalkieColor.GrayDefault,
                        RoundedCornerShape(12.dp),
                    ).padding(top = 8.dp),
                    text = if (userNameState.month != null) "${userNameState.month}월" else "-",
                )
                Text(
                    style = WalkieTypography.Body1_ExtraBold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(96.dp).fillMaxHeight().border(
                        1.dp,
                        WalkieColor.GrayDefault,
                        RoundedCornerShape(12.dp),
                    ).padding(top = 8.dp),
                    text = if (userNameState.day != null) "${userNameState.day}일" else "-",
                )
            }

            Text(
                "전화번호",
                style = WalkieTypography.Body1_Normal,
                modifier = Modifier.padding(bottom = 10.dp).padding(top = 20.dp),
            )
            CheckableCustomTextField(
                text = userNameState.phoneNumber,
                onTextChanged = { text -> viewModel.setUserNameState(userNameState.copy(phoneNumber = text)) },
                modifier = Modifier.fillMaxWidth().height(40.dp).background(Color.White)
                    .border(1.dp, WalkieColor.GrayDefault, RoundedCornerShape(12.dp)),
                trailingIcon = null,
                placeholderText = "",
                checkButton = { text ->
                    Text(
                        text = "인증하기",
                        modifier = Modifier.clickable {
                            // TODO 인증번호 받기 기능 추가
                            viewModel.setUserNameState(userNameState.copy(checkNumber = ""))
                        },
                        color = WalkieColor.Primary,
                    )
                },
                textStyle = WalkieTypography.Body1_ExtraBold,
            )
            Spacer(modifier = Modifier.height(20.dp))

            if (userNameState.checkNumber != null) {
                Text(
                    "인증번호 입력",
                    style = WalkieTypography.Body1_Normal,
                    modifier = Modifier.padding(bottom = 10.dp),
                )
                CheckableCustomTextField(
                    text = userNameState.checkNumber,
                    onTextChanged = { text ->
                        viewModel.setUserNameState(
                            userNameState.copy(
                                checkNumber = text,
                            ),
                        )
                    },
                    modifier = Modifier.fillMaxWidth().height(40.dp).background(Color.White)
                        .border(1.dp, WalkieColor.GrayDefault, RoundedCornerShape(12.dp)),
                    trailingIcon = null,
                    placeholderText = "",
                    checkButton = { text ->
                        Text(
                            text = "확인",
                            modifier = Modifier.clickable {
                                // TODO 인증번호 확인 기능
                                viewModel.setUserNameState(userNameState.copy(isValidate = text.length > 3))
                            },
                            color = WalkieColor.Primary,
                        )
                    },
                    textStyle = WalkieTypography.Body1_ExtraBold,
                )
            }
            userNameState.isValidate?.let { isValidate ->
                Text(
                    text = if (isValidate) "인증 성공" else "인증 번호가 다릅니다.",
                    style = WalkieTypography.Caption.copy(color = if (isValidate) SystemColor.Positive else SystemColor.Error),
                )
            }

            Spacer(modifier = Modifier.weight(1f))
            val enable =
                userNameState.name.isNotBlank() && userNameState.isDuplicated == false && userNameState.year != null && userNameState.month != null && userNameState.day != null && userNameState.isValidate == true
            Button(
                modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                onClick = {
                    onSuccess()
                },
                contentPadding = PaddingValues(vertical = 6.dp),
                shape = RoundedCornerShape(8.dp),
                enabled = enable,
                colors = ButtonDefaults.buttonColors(backgroundColor = WalkieColor.Primary),
                elevation = null,
            ) {
                Text("다음", color = Color.White)
            }
        }
    }
}

fun datePicker(
    context: Context,
    year: Int?,
    month: Int?,
    day: Int?,
    onDateSelected: (Int, Int, Int) -> Unit,
) {
    DatePickerDialog(
        context,
        { _, year, month, day ->
            onDateSelected(year, month, day)
        },
        year ?: Calendar.getInstance().get(Calendar.YEAR),
        month ?: Calendar.getInstance().get(Calendar.MONTH),
        day ?: Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
    ).show()
}

@Composable
fun DatePickerText() {
}

@Preview
@Composable
fun PreviewDatePickerSeparate() {
}
