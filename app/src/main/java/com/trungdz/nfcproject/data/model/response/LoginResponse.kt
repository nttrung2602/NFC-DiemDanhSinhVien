package com.trungdz.nfcproject.data.model.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(val message:String, @SerializedName("hoten")val hoTen:String)
