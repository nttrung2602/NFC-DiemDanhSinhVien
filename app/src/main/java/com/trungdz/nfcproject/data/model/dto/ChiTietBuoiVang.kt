package com.trungdz.nfcproject.data.model.dto

import com.google.gson.annotations.SerializedName
import java.sql.Date

data class ChiTietBuoiVang(@SerializedName("ngayhoc") val ngayHoc: String, @SerializedName("tiethoc")val tietHoc:String)
