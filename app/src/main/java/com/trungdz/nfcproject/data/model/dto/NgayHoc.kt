package com.trungdz.nfcproject.data.model.dto

import android.util.Log
import com.google.gson.annotations.SerializedName
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*

data class NgayHoc(
    @SerializedName("ngayhoc")
    val ngayHoc: String
){
    override fun toString(): String {
        val df= SimpleDateFormat("dd/MM/yyyy")

        return df.format(Date.valueOf(ngayHoc))
    }
}
