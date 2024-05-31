package com.ilfidev.yoursights.utils

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build

object UtilsMap {

    private val linePaint = Paint().apply {
        strokeWidth = 2f
        style = Paint.Style.STROKE
        color = Color.RED
    }
    private val imagePaint = Paint()
    private val textPaint = Paint().apply {
        color = Color.RED
        textSize = 25F
        textAlign = Paint.Align.CENTER
    }

    fun prepareDrawable(
        resources: Resources,
        drawable: Drawable,
        text: String,
        iconSizeNum: Double?,
    ): Drawable {

        return writeOnDrawable(resources, drawable, text, iconSizeNum)
    }

    fun writeOnDrawable(
        resources: Resources,
        drawable: Drawable,
        targetNum: String,
        iconSizeNum: Double?,
    ): BitmapDrawable {
        return prepareImage(
            resources = resources,
            drawable = drawable,
            title = targetNum
        )
    }

    private fun calculateQuotient(length: Int): Double {
        return if (length == 1)
            3.5
        else {
            val d = length * 2.25
            if (d in 3.5..9.0) d else 9.0
        }
    }

    private fun drawableToBitmap(drawable: Drawable?, iconSizeNum: Double?): Bitmap {
        if (drawable is BitmapDrawable) {
            if (drawable.bitmap != null) {
                if (iconSizeNum != null) {
                    val sizeWidth = (drawable.bitmap.width.toDouble() * (iconSizeNum / 10)).toInt()
                    val sizeHeight =
                        (drawable.bitmap.height.toDouble() * (iconSizeNum / 10)).toInt()
                    if (sizeWidth > 0 && sizeHeight > 0)
                        return Bitmap.createScaledBitmap(
                            drawable.bitmap,
                            sizeWidth,
                            sizeHeight,
                            true
                        )
                }
                return drawable.bitmap
            }
        }

        val bitmap: Bitmap = if (drawable!!.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
            Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
        } else if (iconSizeNum != null) {
            Bitmap.createBitmap(
                iconSizeNum.toInt(), iconSizeNum.toInt(),
                Bitmap.Config.ARGB_8888
            )
        } else {
            Bitmap.createBitmap(
                drawable.intrinsicWidth, drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
        }

        return bitmap
    }

    fun prepareImage(
        resources: Resources,
        drawable: Drawable,
        title: String,
    ): BitmapDrawable {
        val bitmap = drawableToBitmap(drawable, null)
        val calculatedWidth = (maxOf(drawable.intrinsicWidth.toFloat(), textPaint.measureText(title)) + 20).toInt()
        val mutableBitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Bitmap.createBitmap(
                calculatedWidth,
                drawable.intrinsicHeight + 10,
                Bitmap.Config.ARGB_8888,
                true
            )
        } else {
            Bitmap.createBitmap(
                Bitmap.createBitmap(
                    calculatedWidth,
                    drawable.intrinsicHeight + 10,
                    Bitmap.Config.ARGB_8888,
                )
            )
        }
        val width = 500
        val canvas = Canvas(mutableBitmap)
        val x = if (width == drawable.intrinsicWidth) {
            0f
        } else {
            (width / 2) - (drawable.intrinsicWidth / 2).toFloat()
        }

        canvas.drawBitmap(bitmap, x, 0f, imagePaint)
        canvas.drawText(title, (width / 2).toFloat(), (drawable.intrinsicHeight ).toFloat(), textPaint)

        return BitmapDrawable(resources, mutableBitmap)
    }

    fun prepareImageWithNoText(
        resources: Resources,
        imageID: Int,
    ): BitmapDrawable {
        val drawable = resources.getDrawable(imageID, null)
        val bitmap = drawableToBitmap(drawable, null).run {
            Bitmap.createBitmap(
                this,
                0,
                0,
                width,
                height,
                Matrix().apply { postRotate(-90f) },
                false
            )
        }
        return BitmapDrawable(resources, bitmap)
    }
}
