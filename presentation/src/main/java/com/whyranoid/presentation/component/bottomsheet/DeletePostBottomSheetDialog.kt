package com.whyranoid.presentation.component.bottomsheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.whyranoid.domain.usecase.community.DeletePostUseCase
import com.whyranoid.presentation.theme.WalkieTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeletePostBottomSheetDialog(
    postId: Long,
    deletePostUseCase: DeletePostUseCase? = get(),
    dismiss: () -> Unit = {},
    refreshScreen: () -> Unit = {},
) {
    val modalBottomSheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val bottomPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

    ModalBottomSheet(
        onDismissRequest = { dismiss() },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        Column(
            modifier = Modifier
                .padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = bottomPadding)
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Button(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .fillMaxWidth(), onClick = {
                    scope.launch {
                        modalBottomSheetState.hide()
                        deletePostUseCase?.invoke(postId)?.onSuccess {
                            dismiss()
                            refreshScreen()
                        }?.onFailure {
                            dismiss()
                        }
                    }
                }) {
                Text("게시물 삭제")
            }
        }
    }
}

@Preview
@Composable
fun CustomBottomSheetDialogPreview() {
    WalkieTheme {
        // preview 확인을 위해 get() 을 null로 변경 필요
        DeletePostBottomSheetDialog(1)
    }
}