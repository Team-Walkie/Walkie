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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SignInInitialScreen(
    isDay: Boolean = true,
    goToAgreeState: (authId: String, name: String, url: String?) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                val data: Intent? = result.data
                val task: Task<GoogleSignInAccount> =
                    GoogleSignIn.getSignedInAccountFromIntent(data)
                task.getResult(ApiException::class.java)?.let { account ->
                    handleSignInResult(
                        account = account,
                        goToAgreeState = goToAgreeState,
                    ) { errorMsg ->
                        scope.launch { scaffoldState.snackbarHostState.showSnackbar(errorMsg) }
                    }
                }
            } else {
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar("계정 연결에 실패했습니다.")
                }
            }
        }

    Scaffold(scaffoldState = scaffoldState) {
        Box {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = if (isDay) R.drawable.splash_day else R.drawable.splash_night),
                contentDescription = "splash screen",
                contentScale = ContentScale.Crop,
            )

            Text(
                "SNS로 간편 가입",
                style = WalkieTypography.Body1.copy(color = if (isDay) Color.White else Color.Black),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 232.dp),
            )

            val context = LocalContext.current
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 160.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White)
                    .clickable { signInWithGoogle(launcher, context) },
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_google_logo),
                    contentDescription = "login with google",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .size(50.dp)
                        .padding(13.dp),
                )

                Text("구글로 로그인하기", modifier = Modifier.align(Alignment.Center))
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
    goToAgreeState: (authId: String, name: String, url: String?) -> Unit,
    showErrorSnackBar: (msg: String) -> Unit,
) {
    runCatching {
        val uid = requireNotNull(account.id) { showErrorSnackBar("authId is null") }
        val name = requireNotNull(account.displayName) { showErrorSnackBar("name is null") }
        val url = account.photoUrl?.let { it.toString() }
        goToAgreeState(uid, name, url)
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
fun DaySignInInitialScreenPreview() {
    WalkieTheme {
        SignInInitialScreen(true) { _, _, _ -> }
    }
}

@Preview
@Composable
fun NightSignInInitialScreenPreview() {
    WalkieTheme {
        SignInInitialScreen(false) { _, _, _ -> }
    }
}
