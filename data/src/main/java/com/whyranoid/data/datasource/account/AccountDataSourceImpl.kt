package com.whyranoid.data.datasource.account

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.core.net.toUri
import com.whyranoid.data.R
import com.whyranoid.data.getResult
import com.whyranoid.data.model.account.toLoginData
import com.whyranoid.data.model.account.toUserInfo
import com.whyranoid.domain.datasource.AccountDataSource
import com.whyranoid.domain.model.account.LoginData
import com.whyranoid.domain.model.account.UserInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.util.Locale
import kotlin.math.min

class AccountDataSourceImpl(
    private val accountService: AccountService,
    private val context: Context,
) : AccountDataSource {
    override suspend fun signUp(
        name: String,
        nickName: String,
        profileUrl: String?,
        authId: String,
        agreeGps: Boolean,
        agreeMarketing: Boolean,
    ): Result<Long> {
        val userNameBody = nickName.toRequestBody("text/plain".toMediaTypeOrNull())
        val nameBody = name.toRequestBody("text/plain".toMediaTypeOrNull())
        val authIdBody = authId.toRequestBody("text/plain".toMediaTypeOrNull())
        val agreeGpsBody = agreeGps.toString().lowercase(Locale.ROOT)
            .toRequestBody("text/plain".toMediaTypeOrNull())
        val agreeSubscriptionBody = agreeMarketing.toString().lowercase(Locale.ROOT)
            .toRequestBody("text/plain".toMediaTypeOrNull())
        val profileImageBody = uploadImageFromUrl(profileUrl, "profileImg")

        return kotlin.runCatching {
            val response = accountService.signUp(
                userNameBody,
                nameBody,
                profileImageBody,
                authIdBody,
                agreeGpsBody,
                agreeSubscriptionBody
            )

            if (response.isSuccessful.not()) {
                throw Exception(response.errorBody().toString())
            } else if (response.body() == null) throw Exception(response.message())
            requireNotNull(response.body()?.walkieId?.toLong() ?: throw Exception("empty response"))
        }.onFailure {
            it.printStackTrace()
        }
    }

    override suspend fun nickCheck(nickName: String): Result<Pair<Boolean, String>> {
        return kotlin.runCatching {
            val response = accountService.checkNickName(nickName)
            if (response.isSuccessful.not()) {
                throw Exception(response.errorBody().toString())
            } else if (response.body() == null) throw Exception(response.message())
            requireNotNull(response.body()).let { Pair(it.isDuplicated, it.nickName ?: "empty") }
        }
    }

    override suspend fun signIn(authorId: String): Result<LoginData> {
        return kotlin.runCatching {
            val response = accountService.login(authorId)
            response.getResult {
                it.toLoginData()
            }
        }
    }

    override suspend fun changeMyInfo(
        walkieId: Long,
        nickName: String,
        profileUrl: String?
    ): Result<Boolean> {
        return kotlin.runCatching {
            val imageUrl:MultipartBody.Part = uploadImageFromUrl(
                imageUrl = profileUrl,
                partName = "profileImg",
            )
            val nickNameRequest = nickName
                .toRequestBody("text/plain".toMediaTypeOrNull())
            val walkieIdRequest = walkieId.toString()
                .toRequestBody("text/plain".toMediaTypeOrNull())
            val isImgDeletedRequest = (profileUrl == null).toString().lowercase(Locale.ROOT)
                .toRequestBody("text/plain".toMediaTypeOrNull())

            val response = accountService.changeMyInfo(
                nickName = nickNameRequest,
                id = walkieIdRequest,
                isImgDeleted = isImgDeletedRequest,
                profileImg = imageUrl
            )
            if (response.isSuccessful) {
                return Result.success(true)
            } else {
                return Result.failure(
                    Exception(response.message())
                )
            }
        }
    }

    override suspend fun getUserInfo(walkieId: Long): Result<UserInfo> {
        return kotlin.runCatching {
            accountService.getMyInfo(walkieId).getResult { it.toUserInfo() }
        }
    }

    override suspend fun leave(walkieId: Long): Result<Unit> {
        return kotlin.runCatching {
            accountService.leave(walkieId).getResult { }
        }
    }

    private suspend fun uploadImageFromUrl(
        imageUrl: String?,
        partName: String
    ): MultipartBody.Part {
        return withContext(Dispatchers.IO) {
            imageUrl?.let { url ->
                if (url.startsWith("content://")) {
                    makePartFromContentUri(url.toUri(), partName)
                } else {
                    makePartFromUrl(url, partName)
                }
            } ?: kotlin.run {
                makePartFromRes(R.drawable.ic_walkie_logo, partName)
            }
        }
    }

    private fun makePartFromRes(
        @DrawableRes resId: Int,
        partName: String
    ): MultipartBody.Part {
        val bitmap = BitmapFactory.decodeResource(context.resources, resId)

        // 임시 파일 생성
        val tempFile = File.createTempFile("drawable", ".png", context.cacheDir)
        tempFile.deleteOnExit()

        // 비트맵을 PNG 파일로 저장
        val outputStream = FileOutputStream(tempFile)
        resizeBitmap(bitmap).compress(Bitmap.CompressFormat.PNG, 70, outputStream)
        outputStream.close()

        // MultipartBody.Part 생성
        val requestBody = tempFile
            .asRequestBody("image/png".toMediaTypeOrNull())

        return MultipartBody.Part.createFormData(
            partName, // 서버에서 받을 파라미터 이름
            tempFile.name,
            requestBody
        )
    }

    private fun makePartFromUrl(url: String, partName: String): MultipartBody.Part {
        // URL로부터 이미지 다운로드
        val connection = URL(url).openConnection()
        val inputStream = connection.getInputStream()

        // 임시 파일 생성
        val tempFile = File.createTempFile("downloaded_image", ".jpg")
        tempFile.deleteOnExit()

        // 입력 스트림을 파일로 저장
        inputStream.use { input ->
            tempFile.outputStream().use { output ->
                input.copyTo(output)
            }
        }

        // MultipartBody.Part 생성
        val requestBody = tempFile
            .asRequestBody("image/jpg".toMediaTypeOrNull())

        return MultipartBody.Part.createFormData(
            partName, // 서버에서 받을 파라미터 이름
            tempFile.name,
            requestBody
        )
    }

    private fun makePartFromContentUri(uri: Uri, partName: String): MultipartBody.Part {
        val bitmap: Bitmap = context.contentResolver.openInputStream(uri).use {
            BitmapFactory.decodeStream(it)
        }

        val tempFile = File.createTempFile("temp_upload", ".jpeg", context.cacheDir)

        FileOutputStream(tempFile).use {
            resizeBitmap(bitmap).compress(Bitmap.CompressFormat.JPEG, 70, it)
        }

        val requestBody = tempFile.asRequestBody("image/jpeg".toMediaTypeOrNull())

        return MultipartBody.Part.createFormData(
            partName,
            tempFile.name,
            requestBody
        )
    }

    private fun resizeBitmap(bitmap: Bitmap, maxWidth: Int = 1080, maxHeight: Int = 1920): Bitmap {
        val ratio = min(maxWidth.toFloat() / bitmap.width, maxHeight.toFloat() / bitmap.height)
        val width = (bitmap.width * ratio).toInt()
        val height = (bitmap.height * ratio).toInt()
        return Bitmap.createScaledBitmap(bitmap, width, height, true)
    }
}
