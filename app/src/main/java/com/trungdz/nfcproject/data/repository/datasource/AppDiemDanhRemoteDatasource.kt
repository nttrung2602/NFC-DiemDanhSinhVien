package com.trungdz.nfcproject.data.repository.datasource

import com.trungdz.nfcproject.data.model.dto.*
import com.trungdz.nfcproject.data.model.response.*
import retrofit2.Response

interface AppDiemDanhRemoteDatasource {

    suspend fun xacThucGiangVien(maGV: String,password:String): Response<LoginResponse>
    suspend fun xuatLopTinChiTheoMaGV(maGV: Int): Response<DataListResponse<ThongTinLTCTheoMAGV>>
    suspend fun xuatNgayHocVaTietHocCuaLTCChuaChotDiemDanh(
        maLTC: Int,
        chotDiemDanh: Boolean = false
    ): Response<NgayHocVaTietHocListResponse>

    suspend fun layChiTietBuoiHocVangCuaSinhVien(maLTC: Int,maSV: String):Response<DataListResponse<ChiTietBuoiVang>>
    suspend fun xuatThongTinTatCaSinhVienCuaLTC(
        maLTC: Int,
        filterByMSSV: String = "",
        trangThaiTheDiemDanh: Boolean = true
    ): Response<DataListResponse<ThongTinSinhVienDangKyLTC>>

    suspend fun layDanhSachDiemDanhSinhVienTheoTKB_LTC(
        maLTC: Int,
        ngayHoc: String,
        tietHoc: String,
        filterByName: String = "",
        filterByVang: Int = -1
    ): Response<DataListResponse<ThongTinDiemDanhSVLTC>>

    suspend fun locTopSoLuongSinhVienVangNhieu(
        maLTC: Int,
        soLuong: Int
    ): Response<DataListResponse<ThongTinSinhVienNhacNho>>
    suspend fun chotDiemDanh(
      maLTC: Int,
      ngayHoc: String,
       tietHoc: String,
       confirmAllAttendance: Boolean,
      ghiChu:String=""
    ):Response<MessageResponse>

    suspend fun layGhiChuBuoiHocCuaLTC(maLTC: Int,ngayHoc: String,tietHoc: String):Response<GhiChuResponse>

    // DangKy
    suspend fun capNhatTheDiemDanh(
        theDiemDanh: String,
        maLTC: Int,
        maSV: String,
        ngayHoc: String,
        tietHoc: String
    ): Response<MessageResponse>

    suspend fun huyTheDiemDanh(maLTC: Int, maSV: String?): Response<MessageResponse>

    suspend fun diemDanhThuCong(
        maLTC: Int,
        maSV: String,
        ngayHoc: String,
        tietHoc: String,
        vang: Int
    ): Response<MessageResponse>

    suspend fun diemDanhBangThe(
        maLTC: Int,
        ngayHoc: String,
        tietHoc: String,
        theDiemDanh: String
    ): Response<DiemDanhTheResponse>
}
