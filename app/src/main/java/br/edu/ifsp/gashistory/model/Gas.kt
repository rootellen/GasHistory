package br.edu.ifsp.gashistory.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Gas(
    val data: String,
    val valor: Float
): Parcelable
