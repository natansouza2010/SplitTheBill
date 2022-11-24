package com.example.splitthebill.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pessoa(
    val id: Int,
    var nome: String,
    var valorPago: Double,
    var valorAReceber: Double,

    ): Parcelable