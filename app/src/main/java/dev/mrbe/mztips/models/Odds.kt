package dev.mrbe.mztips.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Odds(
    var id: String? = "",
    var date: String = "",
    var oddsTip: String = "",
    var oddsResult: Int = -1,
    var tournament: String = ""
) : Parcelable
