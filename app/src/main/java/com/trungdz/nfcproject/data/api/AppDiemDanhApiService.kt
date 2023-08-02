package com.trungdz.nfcproject.data.api

import com.trungdz.nfcproject.data.model.dto.ThongTinDiemDanhSVLTC
import com.trungdz.nfcproject.data.model.dto.ThongTinLTCTheoMAGV
import com.trungdz.nfcproject.data.model.dto.ThongTinSinhVienDangKyLTC
import com.trungdz.nfcproject.data.model.dto.ThongTinSinhVienNhacNho
import com.trungdz.nfcproject.data.model.request.CapNhatTheDiemDanhRequest
import com.trungdz.nfcproject.data.model.request.LTCTheoMaGVRequest
import com.trungdz.nfcproject.data.model.request.ThongTinTheDiemDanhRequest
import com.trungdz.nfcproject.data.model.response.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AppDiemDanhApiService {
    // Define endpoint here
    @POST("giangvien/auth")
    suspend fun authenticateGiangVien(@Query("magv") maGV: Int): Response<MessageResponse>

    // LTC
    @POST("ltc/giangvien")
    suspend fun xuatLopTinChiTheoMaGV(@Body ltcTheoMaGVRequest: LTCTheoMaGVRequest): Response<DataListResponse<ThongTinLTCTheoMAGV>>

    @GET("ltc/thongtinbuoihocltc")
    suspend fun xuatNgayHocVaTietHocCuaMotLTC(
        @Query("maltc") maLTC: Int,
        @Query("chotdiemdanh") chotDiemDanh: Boolean = false
    ): Response<NgayHocVaTietHocListResponse>

    @GET("ltc/thongtinsinhvien")
    suspend fun xuatThongTinTatCaSinhVienCuaLTC(
        @Query("maltc") maLTC: Int,
        @Query("filterbymssv") filterByMSSV: String = "",
        @Query("trangthaithediemdanh") trangThaiTheDiemDanh: Boolean = true
    ): Response<DataListResponse<ThongTinSinhVienDangKyLTC>>

    @GET("ltc/diemdanh")
    suspend fun layDanhSachDiemDanhSinhVienTheoTKB_LTC(
        @Query("maltc") maLTC: Int,
        @Query("ngayhoc") ngayHoc: String,
        @Query("tiethoc") tietHoc: String,
        @Query("filterbyname") filterByName: String = "",
        @Query("filterbyvang") filterByVang: Int = -1
    ): Response<DataListResponse<ThongTinDiemDanhSVLTC>>

    @GET("ltc/nhacnhosinhvien")
    suspend fun locTopSoLuongSinhVienVangNhieu(
        @Query("maltc") maLTC: Int,
        @Query("soluong") soLuong: Int
    ): Response<DataListResponse<ThongTinSinhVienNhacNho>>

    @POST("ltc/giangvien/chotdiemdanh")
    suspend fun chotDiemDanh(
        @Query("maltc") maLTC: Int,
        @Query("ngayhoc") ngayHoc: String,
        @Query("tiethoc") tietHoc: String,
        @Query("confirmAllAttendance") confirmAllAttendance: Boolean,
        @Query("ghichu") ghiChu: String = ""
    ): Response<MessageResponse>

    @GET("ltc/buoihoc/ghichu")
    suspend fun layGhiChuBuoiHocCuaLTC(
        @Query("maltc") maLTC: Int,
        @Query("ngayhoc") ngayHoc: String,
        @Query("tiethoc") tietHoc: String
    ): Response<GhiChuResponse>


    // DangKy
    @POST("dangky/thediemdanh")
    suspend fun capNhatTheDiemDanh(
        @Query("maltc") maLTC: Int,
        @Query("ngayhoc") ngayHoc: String,
        @Query("tiethoc") tietHoc: String,
        @Body capNhatTheDiemDanhRequest: CapNhatTheDiemDanhRequest
    ): Response<MessageResponse>

    @POST("dangky/thediemdanh/huy")
    suspend fun huyTheDiemDanh(
        @Query("maltc") maLTC: Int,
        @Query("masv") maSV: String?
    ): Response<MessageResponse>

    @POST("dangky/diemdanh/thucong")
    suspend fun diemDanhThuCong(
        @Query("maltc") maLTC: Int,
        @Query("masv") maSV: String,
        @Query("ngayhoc") ngayHoc: String,
        @Query("tiethoc") tietHoc: String,
        @Query("vang") vang: Int
    ): Response<MessageResponse>

    @POST("dangky/diemdanh/the")
    suspend fun diemDanhBangThe(
        @Query("maltc") maLTC: Int,
        @Query("ngayhoc") ngayHoc: String,
        @Query("tiethoc") tietHoc: String,
        @Body thongTinTheDiemDanhRequest: ThongTinTheDiemDanhRequest
    ): Response<DiemDanhTheResponse>
}