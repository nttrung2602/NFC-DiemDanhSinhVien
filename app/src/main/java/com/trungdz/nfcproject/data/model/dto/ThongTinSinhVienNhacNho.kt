package com.trungdz.nfcproject.data.model.dto

import com.google.gson.annotations.SerializedName

data class ThongTinSinhVienNhacNho(
    @SerializedName("hoten")
    val hoTen:  String,
    @SerializedName("malop")
    val maLop: String,
    @SerializedName("masv")
    val maSV: String,
    @SerializedName("phai")
    val phai: String,
    @SerializedName("sobuoivang")
    val soBuoiVang: Int
)