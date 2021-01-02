package com.akerimtay.movieapp.utils

import android.content.Intent
import android.net.Uri

fun getBrowserIntent(url: String) = Intent(Intent.ACTION_VIEW, Uri.parse(url))