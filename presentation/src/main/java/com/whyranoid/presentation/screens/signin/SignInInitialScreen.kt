package com.whyranoid.presentation.screens.signin

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.whyranoid.presentation.R
import com.whyranoid.presentation.theme.WalkieTheme
import com.whyranoid.presentation.theme.WalkieTypography
import com.whyranoid.presentation.viewmodel.SignInViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SignInInitialScreen(
    modifier: Modifier = Modifier,
) {
    val viewModel = koinViewModel<SignInViewModel>()
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                val data: Intent? = result.data
                val task: Task<GoogleSignInAccount> =
                    GoogleSignIn.getSignedInAccountFromIntent(data)
                task.getResult(ApiException::class.java)?.let { account ->
                    handleSignInResult(account, viewModel)
                } ?: scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar("계정 연결에 실패했습니다.")
                }
            } else {
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar("계정 연결에 실패했습니다.")
                }
            }
        }

    Scaffold(scaffoldState = scaffoldState) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color.White),
        ) {
            Spacer(modifier = Modifier.height(112.dp))
            Text(
                "Let's Walkie",
                style = WalkieTypography.Title,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 112.dp),
            )
            Image(
                painter = painterResource(id = R.drawable.walkie_splash_logo),
                contentDescription = "image description",
                contentScale = ContentScale.None,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 292.dp),
            )

            Text(
                "SNS로 간편 가입",
                style = WalkieTypography.Body1_Normal,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 232.dp),
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(32.dp),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 160.dp),
            ) {
                Surface(
                    // TODO 실제 Auth 인증 구현
                    modifier = Modifier.clickable { },
                    elevation = 2.dp,
                    shape = CircleShape,
                    color = Color(0xFFFEE500),
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_kakao_logo),
                        contentDescription = "login with kakao",
                        modifier = Modifier
                            .size(50.dp)
                            .padding(13.dp),
                        tint = Color.Black,
                    )
                }
                Surface(
                    // TODO 실제 Auth 인증 구현
                    modifier = Modifier.clickable { },
                    elevation = 2.dp,
                    shape = CircleShape,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_naver_logo),
                        contentDescription = "login with naver",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(50.dp),
                    )
                }
                val context = LocalContext.current
                Surface(
                    modifier = Modifier.clickable { signInWithGoogle(launcher, context) },
                    elevation = 2.dp,
                    shape = CircleShape,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_google_logo),
                        contentDescription = "login with google",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(50.dp)
                            .padding(13.dp),
                    )
                }
            }

            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 100.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text("이미 계정이 있으신가요?")
                Text("로그인하기", color = Color.Red, style = WalkieTypography.Body1_SemiBold)
            }
        }
    }
}

private fun handleSignInResult(
    account: GoogleSignInAccount,
    viewModel: SignInViewModel,
) {
    runCatching {
        val uid = account.id
        val name = account.displayName
        val url = account.photoUrl?.let { it.toString() }
        viewModel.goToAgreeState(uid.toString(), name.toString(), url)
    }
}

private fun signInWithGoogle(
    resultLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>,
    context: Context,
) {
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail()
        .requestProfile().requestId().build()

    val googleSignInClient = GoogleSignIn.getClient(context as Activity, gso)
    val signInIntent = googleSignInClient.signInIntent
    resultLauncher.launch(signInIntent)
}

@Preview
@Composable
fun PreviewSignInInitialScreen() {
    WalkieTheme {
        SignInInitialScreen()
    }
}
