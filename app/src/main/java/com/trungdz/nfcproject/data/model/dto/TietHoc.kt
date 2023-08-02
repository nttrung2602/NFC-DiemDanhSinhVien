package com.trungdz.nfcproject.data.model.dto

import com.google.gson.annotations.SerializedName

data class TietHoc(
    @SerializedName("tiethoc")
    var tietHoc: String
){
    override fun toString(): String {
        return tietHoc
    }
}
