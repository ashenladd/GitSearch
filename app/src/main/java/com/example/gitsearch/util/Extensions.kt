package com.example.gitsearch.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.gitsearch.util.DateConstant.DATE_MONTH_YEAR_FORMAT
import com.example.gitsearch.util.DateConstant.TIME_FORMAT
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun ImageView.roundedCornerImage(corner: Int) {
    val cornerSize = this.context.dpToPx(corner)
    Glide.with(this.context)
        .asBitmap()
        .load(this)
        .fitCenter()
        .transform(RoundedCorners(cornerSize))
        .placeholder(androidx.constraintlayout.widget.R.color.material_grey_800)
        .error(androidx.constraintlayout.widget.R.color.material_grey_800)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(
                resource: Bitmap,
                transition: Transition<in Bitmap>?,
            ) {
                this@roundedCornerImage.setImageBitmap(resource)
            }

            override fun onLoadCleared(placeholder: Drawable?) {}
        })
}

fun Context.dpToPx(dp: Int): Int {
    return (dp * resources.displayMetrics.density).toInt()
}

private fun Long.formatDate(toFormat: String = DATE_MONTH_YEAR_FORMAT): String {
    val sdf = SimpleDateFormat(
        toFormat,
        Locale.getDefault()
    )
    sdf.timeZone = TimeZone.getDefault()
    return sdf.format(Date(this))
}


private fun String.parseDate(fromFormat: String = TIME_FORMAT): Long {
    val sdf = SimpleDateFormat(
        fromFormat,
        Locale.getDefault()
    )
    sdf.timeZone = TimeZone.getTimeZone("UTC")
    return sdf.parse(this)?.time ?: 0L
}

fun String.parseFormatDate(
    fromFormat: String = TIME_FORMAT,
    toFormat: String = DATE_MONTH_YEAR_FORMAT,
): String {
    return this.parseDate(fromFormat).formatDate(toFormat)
}
