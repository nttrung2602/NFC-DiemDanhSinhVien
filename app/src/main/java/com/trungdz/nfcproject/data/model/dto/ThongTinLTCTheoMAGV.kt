package com.trungdz.nfcproject.data.model.dto

import com.google.gson.annotations.SerializedName

data class ThongTinLTCTheoMAGV(
    @SerializedName("maltc")
    val maLTC: Int,
    @SerializedName("thongtinlophoc")
    val thongTinLopHoc: String
){
    override fun toString(): String {
        return thongTinLopHoc
    }
}