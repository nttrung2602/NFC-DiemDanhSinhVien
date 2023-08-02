package com.trungdz.nfcproject.data.model.dto

import com.google.gson.annotations.SerializedName

data class ThongTinDiemDanhSVLTC(
    @SerializedName("masv")
    val maSV:String,
    @SerializedName("hoten")
    val hoTen: String,
    @SerializedName("malop")
    val maLop: String,
    @SerializedName("tenlop")
    val tenLop: String,
    @SerializedName("vang")
    var vang: Int
)