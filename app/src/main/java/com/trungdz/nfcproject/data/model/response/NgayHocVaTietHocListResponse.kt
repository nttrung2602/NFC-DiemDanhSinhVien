package com.trungdz.nfcproject.data.model.response

import com.google.gson.annotations.SerializedName
import com.trungdz.nfcproject.data.model.dto.NgayHoc
import com.trungdz.nfcproject.data.model.dto.TietHoc

data class NgayHocVaTietHocListResponse(
    @SerializedName("listngayhoc")
    var listNgayHoc:List<NgayHoc>,
    @SerializedName("listtiethoc")
    var listTietHoc:List<TietHoc>
)