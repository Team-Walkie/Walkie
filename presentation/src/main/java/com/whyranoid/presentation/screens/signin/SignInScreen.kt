package com.whyranoid.presentation.screens.signin

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whyranoid.presentation.theme.WalkieTheme
import com.whyranoid.presentation.viewmodel.SignInState
import com.whyranoid.presentation.viewmodel.SignInViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignInScreen(
    isDay: Boolean = true,
    finishSignIn: () -> Unit,
) {
    val viewModel = koinViewModel<SignInViewModel>()
    val signInState = viewModel.signInState.collectAsStateWithLifecycle()

    when (signInState.value) {
        is SignInState.InitialState -> SignInInitialScreen(isDay = isDay, finishSignIn) { authId, name, url ->
            viewModel.goToAgreeState(authId, name, url)
        }

        is SignInState.AgreeState -> SignInAgreeScreen { agreeGps, agreeMarketing ->
            viewModel.goToUserNameState(agreeGps, agreeMarketing)
        }

        is SignInState.UserNameState -> SignInUserNameScreen { viewModel.goToInfoState() }
        is SignInState.InfoState -> SignInInfoScreen { viewModel.goToDoneState() }
        is SignInState.Done -> SignInDoneScreen { finishSignIn() }
    }
}

@Preview
@Composable
fun PreviewSignInScreen() {
    WalkieTheme {
        SignInScreen {}
    }
}
