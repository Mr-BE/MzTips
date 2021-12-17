package dev.mrbe.mztips.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class odds(
    var date: Date = Date(),
    var oddsTip: String = ""
): Parcelable
