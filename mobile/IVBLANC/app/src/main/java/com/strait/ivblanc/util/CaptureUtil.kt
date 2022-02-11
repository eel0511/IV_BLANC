package com.strait.ivblanc.util

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*

class CaptureUtil {
    companion object {
        fun saveCapture(view: View): Uri? {
            val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            view.draw(canvas)

            val currentMillis = System.currentTimeMillis()
            val name = "ivblanc${SimpleDateFormat("yyyy-MM-d/hh:mm:ss", Locale.KOREA).format(currentMillis)}.jpg"
            var fos: OutputStream?
            var imageUri: Uri?

            view.context.contentResolver.also { resolver ->
                val contentValues = ContentValues().apply {
                    put(MediaStore.Images.ImageColumns.DISPLAY_NAME, name)
                    put(MediaStore.Images.ImageColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.Images.ImageColumns.DATE_TAKEN, currentMillis)
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        put(MediaStore.Images.ImageColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                    }
                }

                imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                fos = imageUri?.let {
                    resolver.openOutputStream(it)
                }
            }

            fos?.use {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, it)
            }

            return if(imageUri == null) {
                null
            } else {
                imageUri
            }
        }

        fun getAbsolutePathFromImageUri(context: Context, uri: Uri): String? {
            val cursor = context.contentResolver.query(uri, arrayOf(MediaStore.MediaColumns.DATA), null, null, null)
            var absolutPath: String? = null
            cursor?.use {
                if(it.moveToFirst()) absolutPath = it.getString(0)
            }
            return absolutPath
        }

        fun deleteImageByUri(context: Context, uri: Uri) {
            context.contentResolver.delete(uri, null,null)
        }
    }
}