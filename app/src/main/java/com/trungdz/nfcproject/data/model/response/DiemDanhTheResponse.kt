package com.trungdz.nfcproject.data.model.response

import com.google.gson.annotations.SerializedName

data class DiemDanhTheResponse(val message: String, @SerializedName("masv") val maSV: String)
