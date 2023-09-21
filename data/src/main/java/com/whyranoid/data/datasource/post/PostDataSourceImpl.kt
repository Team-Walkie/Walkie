package com.whyranoid.data.datasource.post

import com.whyranoid.domain.datasource.PostDataSource
import com.whyranoid.domain.model.post.Post
import com.whyranoid.domain.model.post.PostPreview
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class PostDataSourceImpl(private val postService: PostService) : PostDataSource {
    // TODO: change to api call
    override suspend fun getPostPreviews(uid: Long): Result<List<PostPreview>> {
        return Result.success(PostPreview.DUMMY_LIST)
    }

    override suspend fun getPostPreviews(
        uid: Long,
        year: Int,
        month: Int,
        day: Int,
    ): Result<List<PostPreview>> {
        return Result.success(PostPreview.DUMMY_LIST)
    }

    // TODO: change to api call
    override suspend fun getPost(postId: Long): Result<Post> {
        return Result.success(Post.DUMMY)
    }

    override suspend fun uploadPost(
        uid: Long,
        content: String,
        colorMode: Int,
        history: String,
        imagePath: String,
    ): Result<String> {
        val uidBody = RequestBody.create(MediaType.parse("text/plain"), uid.toString())
        val contentBody = RequestBody.create(MediaType.parse("text/plain"), content)
        val colorModeBody = RequestBody.create(MediaType.parse("text/plain"), colorMode.toString())
        val historyBody = RequestBody.create(MediaType.parse("text/plain"), history)

        val file = File(imagePath)
        val fileBody = RequestBody.create(MediaType.parse("image/*"), file)
        val imagePart = MultipartBody.Part.createFormData("image", file.name, fileBody)

        return kotlin.runCatching {
            val uploadPostResponse =
                postService.uploadPost(uidBody, contentBody, colorModeBody, historyBody, imagePart)
                    .body()
            uploadPostResponse?.message.toString()
        }
    }
}
