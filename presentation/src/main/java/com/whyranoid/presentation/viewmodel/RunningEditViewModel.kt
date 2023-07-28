package com.whyranoid.presentation.viewmodel

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.cachedIn
import com.whyranoid.presentation.viewmodel.GalleryPagingSource.Companion.PAGING_SIZE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RunningEditViewModel : ViewModel() {
    private val _selectedState: MutableStateFlow<Uri?> = MutableStateFlow(null)
    val selectedState: StateFlow<Uri?> get() = _selectedState.asStateFlow()

    private fun galleryPagingItems(context: Context) =
        Pager(
            config = PagingConfig(
                pageSize = PAGING_SIZE,
            ),
            pagingSourceFactory = {
                GalleryPagingSource(context)
            },
        ).flow

    fun getImages(context: Context): Flow<PagingData<Uri>> =
        galleryPagingItems(context).cachedIn(viewModelScope)

    fun select(uri: Uri) {
        _selectedState.value = uri
    }
}

class GalleryPagingSource(private val context: Context) : PagingSource<Int, Uri>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Uri> {
        return try {
            val currentPage = params.key ?: 0
            val offset = currentPage * PAGING_SIZE

            val imageUris = getImageUris(context.contentResolver, offset, PAGING_SIZE)

            LoadResult.Page(
                data = imageUris,
                prevKey = if (currentPage == 0) null else currentPage - 1,
                nextKey = if (imageUris.isNotEmpty()) currentPage + 1 else null,
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    private fun getImageUris(contentResolver: ContentResolver, offset: Int, limit: Int): List<Uri> {
        val imageUris = mutableListOf<Uri>()
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.TITLE,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.MIME_TYPE,
            MediaStore.Images.Media.DATE_TAKEN,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media.SIZE,
            MediaStore.Images.Media.WIDTH,
            MediaStore.Images.Media.HEIGHT,
        )
        val selection =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Images.Media.SIZE + " > 0"
            } else {
                null
            }

        val cursor: Cursor? = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            val sortOrder = "${MediaStore.Images.Media.DATE_TAKEN} DESC LIMIT $limit OFFSET $offset"
            contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                null,
                sortOrder,
            )
        } else {
            contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                bundleOf(
                    ContentResolver.QUERY_ARG_OFFSET to offset,
                    ContentResolver.QUERY_ARG_LIMIT to limit,
                    ContentResolver.QUERY_ARG_SORT_COLUMNS to arrayOf(MediaStore.Images.Media.DATE_TAKEN),
                    ContentResolver.QUERY_ARG_SORT_DIRECTION to ContentResolver.QUERY_SORT_DIRECTION_DESCENDING,
                    ContentResolver.QUERY_ARG_SQL_SELECTION to selection,
                ),
                null,
            )
        }
        cursor?.use {
            val idColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            while (it.moveToNext()) {
                val imageId = it.getLong(idColumn)
                val contentUri = Uri.withAppendedPath(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    imageId.toString(),
                )
                imageUris.add(contentUri)
            }
        }
        return imageUris
    }

    override fun getRefreshKey(state: PagingState<Int, Uri>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    companion object {
        const val PAGING_SIZE = 20
    }
}
