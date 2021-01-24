package com.akerimtay.movieapp.utils

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri

fun getBrowserIntent(url: String) = Intent(Intent.ACTION_VIEW, Uri.parse(url))

fun getYoutubeIntent(videoId: String): Intent {
    val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$videoId"))
    val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=$videoId"))
    return try {
        appIntent
    } catch (ex: ActivityNotFoundException) {
        webIntent
    }
}