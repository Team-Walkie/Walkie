package com.whyranoid.presentation.screens.challenge

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.whyranoid.domain.util.EMPTY
import com.whyranoid.domain.util.getToday
import com.whyranoid.presentation.R
import com.whyranoid.presentation.component.bar.BasicBackButtonTopBar
import com.whyranoid.presentation.theme.WalkieTypography
import com.whyranoid.presentation.viewmodel.challenge.ChallengeImageSaveViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@Composable
fun ChallengeImageSaveScreen(
    navController: NavController,
    challengeId: Long,
) {

    val viewModel = koinViewModel<ChallengeImageSaveViewModel>()
    val state by viewModel.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val graphicsLayer = rememberGraphicsLayer()

    LaunchedEffect(Unit) {
        viewModel.getChallengeDetail(challengeId)
    }

    Scaffold(
        topBar = {
            BasicBackButtonTopBar {
                navController.popBackStack()
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier,
        ) {

            Spacer(modifier = Modifier.size(118.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center,
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .drawWithContent {
                            graphicsLayer.record {
                                this@drawWithContent.drawContent()
                            }
                            drawLayer(graphicsLayer)
                        }
                        .clickable {
                            coroutineScope.launch {
                                val bitmap = graphicsLayer.toImageBitmap()
                                saveBitmapToGallery(
                                    context = navController.context,
                                    bitmap = bitmap.asAndroidBitmap(),
                                    fileName = "walkie_challenge_${challengeId}"
                                )
                            }
                        },
                    contentAlignment = Alignment.TopCenter,
                ) {

                    Image(
                        modifier = Modifier
                            .size(300.dp),
                        painter = painterResource(id = R.drawable.bg_badge),
                        contentDescription = "Badge background"
                    )

                    Column(
                        modifier = Modifier,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {

                        Spacer(modifier = Modifier.height(25.dp))

                        Text(
                            text = state.challenge.getDataOrNull()?.name ?: String.EMPTY,
                            style = WalkieTypography.Title
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        AsyncImage(
                            model = state.challenge.getDataOrNull()?.badge?.imageUrl,
                            modifier = Modifier.size(180.dp),
                            contentDescription = "challenge badge image"
                        )

                    }

                    Box(
                        modifier = Modifier.matchParentSize(),
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(end = 22.dp, bottom = 20.dp),
                            text = getToday().split(" ").first().replace("-", "."),
                            style = WalkieTypography.Body2
                        )
                    }

                }
            }
        }
    }
}


fun saveBitmapToGallery(context: Context, bitmap: Bitmap, fileName: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        // Android 10(Q) 이상 - Scoped Storage 방식
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "$fileName.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            put(MediaStore.Images.Media.IS_PENDING, 1)
        }

        val contentResolver = context.contentResolver
        val uri =
            contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        uri?.let {
            try {
                contentResolver.openOutputStream(it)?.use { outputStream ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                }
                contentValues.clear()
                contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
                contentResolver.update(uri, contentValues, null, null)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    } else {
        val directory =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val file = File(directory, "$fileName.jpg")

        try {
            FileOutputStream(file).use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}