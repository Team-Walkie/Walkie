package com.whyranoid.presentation.reusable

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.whyranoid.presentation.theme.WalkieColor
import com.whyranoid.presentation.viewmodel.RunningEditViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun GalleryGrid(
    modifier: Modifier = Modifier,
    column: Int,
    onImageSelected: (Uri) -> Unit,
) {
    val viewModel = koinViewModel<RunningEditViewModel>()
    val selectedImageState = viewModel.selectedState.collectAsStateWithLifecycle()
    val images = viewModel.getImages(LocalContext.current).collectAsLazyPagingItems()

    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(column),
        contentPadding = PaddingValues(4.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        items(images.itemCount) { index ->
            images[index]?.let { uri ->
                GalleryImage(uri, isSelected = uri == selectedImageState.value) {
                    viewModel.select(uri)
                    onImageSelected(uri)
                }
            }
        }
    }
}

@Composable
fun GalleryImage(
    uri: Uri,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    onClick: () -> Unit,
) {
    val expandState = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .border(
                width = if (isSelected) 2.dp else 0.dp,
                color = WalkieColor.Primary,
                shape = RectangleShape,
            )
            .background(Color.Gray)
            .zIndex(if (expandState.value) 1f else 0f)
            .fillMaxSize()
            .then(modifier)
            .clickable { onClick() },
    ) {
        AsyncImage(
            model = uri,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .align(Alignment.Center),
            contentScale = ContentScale.Crop,
        )
    }
}
