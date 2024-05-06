package com.whyranoid.presentation.screens.community

import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.whyranoid.domain.model.post.Comment
import com.whyranoid.domain.model.post.Post
import com.whyranoid.domain.model.user.User
import com.whyranoid.domain.repository.PostRepository
import com.whyranoid.domain.repository.UserRepository
import com.whyranoid.domain.usecase.GetMyUidUseCase
import com.whyranoid.domain.usecase.community.SendCommentUseCase
import com.whyranoid.presentation.component.bar.WalkieTopBar
import com.whyranoid.presentation.theme.WalkieColor
import com.whyranoid.presentation.theme.WalkieTheme
import com.whyranoid.presentation.theme.WalkieTypography
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get

@Composable
fun CommentScreen(
    modifier: Modifier = Modifier,
    post: Post,
    onProfileClicked: (uid: Long) -> Unit = { },
    onBackClicked: () -> Unit = { },
    postRepo: PostRepository = get(),
    userRepo: UserRepository = get(),
    myUid: GetMyUidUseCase = get(),
    sendCommentUseCase: SendCommentUseCase = get(),
) {
    var comments by remember { mutableStateOf<List<Comment>?>(null) }
    var user by remember { mutableStateOf<User?>(null) }
    var isProgress by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(post.id, isProgress) {
        postRepo.getComments(post.id).onSuccess {
            comments = it.reversed()
            Log.d("ju0828", it.toString())
        }

        myUid().onSuccess { uid ->
            userRepo.getUser(uid).onSuccess {
                user = it
            }
        }
    }

    Scaffold(
        topBar = {
            WalkieTopBar(
                middleContent = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            modifier =
                                Modifier
                                    .align(Alignment.CenterStart)
                                    .clickable { onBackClicked() },
                            imageVector = Icons.Filled.KeyboardArrowLeft,
                            contentDescription = "Left Arrow",
                        )

                        Text(
                            text = "댓글",
                            style = WalkieTypography.Title.copy(fontWeight = FontWeight(600)),
                        )
                    }
                },
            )
        },
        bottomBar = {
            BottomTextField(
                modifier = Modifier, // .imePadding(),
                imageUrl = user?.imageUrl ?: User.DUMMY.imageUrl,
                onSendClicked = { content ->
                    isProgress = true
                    scope.launch {
                        sendCommentUseCase(post.id, content).onSuccess {
                            isProgress = false
                        }.onFailure {
                            isProgress = false
                            Toast.makeText(context, "댓글 작성에 실패했습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
            )
        },
    ) { paddingValues ->
        LazyColumn(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp)
                    .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            item {
                PostComment(
                    modifier =
                        Modifier
                            .padding(horizontal = 20.dp),
                    post.author.uid,
                    post.author.imageUrl,
                    post.author.nickname,
                    post.contents,
                    onProfileClicked,
                )

                Spacer(
                    modifier =
                        Modifier
                            .padding(top = 16.dp)
                            .height(1.dp)
                            .fillMaxWidth()
                            .background(WalkieColor.GrayDisable),
                )
            }

            comments?.let { list ->
                items(list.size) { index ->
                    PostComment(
                        modifier =
                            Modifier
                                .padding(horizontal = 20.dp)
                                .padding(top = 8.dp),
                        list[index].commenterId,
                        list[index].commenter.imageUrl,
                        list[index].commenter.nickname,
                        list[index].content,
                        onProfileClicked,
                    )
                }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize().imePadding(),
        contentAlignment = Alignment.Center,
    ) {
        if (isProgress) CircularProgressIndicator()
    }
}

@Composable
fun PostComment(
    modifier: Modifier = Modifier,
    uid: Long,
    imageUrl: String,
    nickname: String,
    content: String,
    onProfileClicked: (uid: Long) -> Unit = { },
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = imageUrl.ifBlank { User.DUMMY.imageUrl },
            contentDescription = "user image",
            modifier =
                Modifier
                    .size(34.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .clickable { onProfileClicked(uid) },
            contentScale = ContentScale.Crop,
        )

        val text =
            buildAnnotatedString {
                withStyle(
                    SpanStyle(fontWeight = FontWeight.Bold),
                ) {
                    append(nickname)
                }
                append(" $content")
            }

        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = text,
            style = WalkieTypography.Body2,
        )
    }
}

@Composable
fun BottomTextField(
    modifier: Modifier = Modifier,
    imageUrl: String,
    onSendClicked: (String) -> Unit = {},
) {
    var textFieldValue by remember { mutableStateOf(TextFieldValue("")) }

    Column(modifier) {
        Spacer(modifier = Modifier.background(WalkieColor.GrayDisable).fillMaxWidth().height(1.dp))
        Row(
            modifier = Modifier.fillMaxWidth().height(50.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            listOf(
                "❤\uFE0F",
                "\uD83D\uDE4C",
                "\uD83D\uDD25",
                "\uD83D\uDC4F",
                "\uD83D\uDE22",
                "\uD83D\uDE0D",
                "\uD83D\uDE2E",
                "\uD83D\uDE02",
            ).forEach {
                Text(
                    modifier =
                        Modifier.clip(RoundedCornerShape(6.dp))
                            .clickable {
                                val newCursorPosition = textFieldValue.text.length + 2
                                textFieldValue =
                                    TextFieldValue(textFieldValue.text + it).copy(
                                        selection = TextRange(newCursorPosition),
                                    )
                            }.padding(6.dp),
                    fontSize = 20.sp,
                    text = it,
                )
            }
        }
        Spacer(modifier = Modifier.background(WalkieColor.GrayDisable).fillMaxWidth().height(1.dp))
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
            contentAlignment = Alignment.CenterStart,
        ) {
            if (textFieldValue.text.isEmpty()) {
                Text(
                    modifier = Modifier.padding(start = 60.dp),
                    text = "댓글 달기",
                    style =
                        LocalTextStyle.current.copy(
                            fontSize = 16.sp,
                            color = WalkieColor.GrayDefault,
                        ),
                )
            }

            AsyncImage(
                model = imageUrl.ifBlank { User.DUMMY.imageUrl },
                contentDescription = "user image",
                modifier =
                    Modifier
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                        .align(Alignment.TopStart)
                        .size(34.dp)
                        .clip(RoundedCornerShape(50.dp)),
                contentScale = ContentScale.Crop,
            )

            Text(
                modifier =
                    Modifier.clickable {
                        if (textFieldValue.text.isNotBlank()) {
                            onSendClicked(textFieldValue.text)
                            textFieldValue = TextFieldValue("")
                        }
                    }.padding(12.dp)
                        .align(Alignment.TopEnd),
                text = "게시",
                style =
                    LocalTextStyle.current.copy(
                        fontSize = 16.sp,
                        color = if (textFieldValue.text.isNotBlank()) WalkieColor.Primary else WalkieColor.GrayDisable,
                    ),
            )

            BasicTextField(
                modifier = Modifier.padding(end = 60.dp),
                value = textFieldValue,
                onValueChange = { textFieldValue = it },
                textStyle =
                    LocalTextStyle.current.copy(fontSize = 16.sp),
                decorationBox = { innerTextField ->
                    Column {
                        Row(
                            modifier = Modifier.padding(start = 60.dp, top = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceAround,
                        ) {
                            innerTextField()
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                    }
                },
            )
        }
    }
}

@Preview
@Composable
fun BottomTextFieldPreview() {
    WalkieTheme {
        BottomTextField(imageUrl = User.DUMMY.imageUrl)
    }
}

@Preview
@Composable
fun CommentScreenPreview() {
    val repo: PostRepository = get()
    WalkieTheme {
        CommentScreen(post = Post.DUMMY, postRepo = repo)
    }
}
