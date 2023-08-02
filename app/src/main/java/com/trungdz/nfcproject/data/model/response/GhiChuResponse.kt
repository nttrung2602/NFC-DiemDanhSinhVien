package com.trungdz.nfcproject.data.model.response

import com.google.gson.annotations.SerializedName

data class GhiChuResponse(
    @SerializedName("ghichu")
    val ghiChu: String?
)
