package com.example.myapplication.Tool

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.palette.graphics.Palette
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

interface OnColorGeneratedListener {
    fun onColorGenerated(color: Int)
}
//设置背景颜色
//private val mainLayout: ConstraintLayout
class SetColor(private val listener: OnColorGeneratedListener) :
    AsyncTask<String?, Void?, Bitmap?>() {
    override fun doInBackground(vararg strings: String?): Bitmap? {
        val imageUrl = strings[0]

        var bitmap: Bitmap? = null
        try {
            val url = URL(imageUrl)
            val connection = url.openConnection() as HttpURLConnection
            connection.connect()
            val inputStream: InputStream = connection.inputStream
            bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream.close()
            connection.disconnect()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return bitmap
    }
//    mainLayout.setBackgroundColor(color)
    override fun onPostExecute(bitmap: Bitmap?) {
        var color:Int
        if (bitmap != null) {
            Palette.from(bitmap).generate { palette ->
                val dominantSwatch = palette?.dominantSwatch
                dominantSwatch?.let {
                    color = it.rgb
                    listener.onColorGenerated(color)
                }
            }
        }
    }
}