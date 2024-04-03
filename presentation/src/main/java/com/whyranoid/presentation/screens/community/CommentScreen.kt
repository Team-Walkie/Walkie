package com.whyranoid.presentation.screens.community

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.whyranoid.domain.model.post.Comment
import com.whyranoid.domain.model.post.Post
import com.whyranoid.domain.repository.PostRepository
import com.whyranoid.presentation.component.bar.WalkieTopBar
import com.whyranoid.presentation.theme.WalkieColor
import com.whyranoid.presentation.theme.WalkieTheme
import com.whyranoid.presentation.theme.WalkieTypography
import org.koin.androidx.compose.get

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CommentScreen(
    modifier: Modifier = Modifier,
    post: Post,
    onProfileClicked: (uid: Long) -> Unit = { },
    onBackClicked: () -> Unit = { },
    postRepo: PostRepository = get(),
) {
    var comments by remember { mutableStateOf<List<Comment>?>(null) }
    LaunchedEffect(post.id) {
        postRepo.getComments(post.id).onSuccess {
            comments = it
            Log.d("ju0828", it.toString())
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            WalkieTopBar(
                middleContent = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            modifier =
                                Modifier.align(Alignment.CenterStart)
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
    ) { paddingValues ->
        Column {
            Column(
                modifier = Modifier.padding(paddingValues).padding(horizontal = 20.dp),
            ) {
                PostComment(
                    post.author.uid,
                    post.author.imageUrl,
                    post.author.nickname,
                    post.contents,
                    onProfileClicked,
                )
            }

            Box(
                modifier =
                    Modifier.padding(top = 16.dp).height(1.dp).fillMaxWidth()
                        .background(WalkieColor.GrayBorder),
            )

            LazyColumn(
                modifier =
                    Modifier.fillMaxSize().padding(top = 16.dp)
                        .padding(paddingValues).padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(all = 16.dp),
            ) {
                comments?.let { list ->
                    items(list.size) { index ->
                        PostComment(
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
    }
}

@Composable
fun PostComment(
    uid: Long,
    imageUrl: String,
    nickname: String,
    content: String,
    onProfileClicked: (uid: Long) -> Unit = { },
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = imageUrl,
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

@Preview
@Composable
fun CommentScreenPreview() {
    WalkieTheme {
        CommentScreen(post = Post.DUMMY)
    }
}
