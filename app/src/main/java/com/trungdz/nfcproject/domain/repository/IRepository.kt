package com.trungdz.nfcproject.domain.repository

import com.trungdz.nfcproject.data.model.dto.ThongTinDiemDanhSVLTC
import com.trungdz.nfcproject.data.model.dto.ThongTinLTCTheoMAGV
import com.trungdz.nfcproject.data.model.dto.ThongTinSinhVienDangKyLTC
import com.trungdz.nfcproject.data.model.dto.ThongTinSinhVienNhacNho
import com.trungdz.nfcproject.data.model.response.*
import com.trungdz.nfcproject.data.ulti.Resource
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query


interface IRepository {

    // GiangVien
    suspend fun xacThucGiangVien(maGV: Int): Resource<MessageResponse>
    suspend fun xuatLopTinChiTheoMaGV(maGV: Int): Resource<DataListResponse<ThongTinLTCTheoMAGV>>
    suspend fun xuatNgayHocVaTietHocCuaLTCChuaChotDiemDanh(
        maLTC: Int,
        chotDiemDanh: Boolean = false
    ): Resource<NgayHocVaTietHocListResponse>

    suspend fun xuatThongTinTatCaSinhVienCuaLTC(
        maLTC: Int,
        filterByMSSV: String = "",
        trangThaiTheDiemDanh: Boolean = true
    ): Resource<DataListResponse<ThongTinSinhVienDangKyLTC>>

    suspend fun layDanhSachDiemDanhSinhVienTheoTKB_LTC(
        maLTC: Int,
        ngayHoc: String,
        tietHoc: String,
        filterByName: String = "",
        filterByVang: Int = -1
    ): Resource<DataListResponse<ThongTinDiemDanhSVLTC>>

    suspend fun locTopSoLuongSinhVienVangNhieu(
        maLTC: Int,
        soLuong: Int
    ): Resource<DataListResponse<ThongTinSinhVienNhacNho>>

    suspend fun chotDiemDanh(
       maLTC: Int,
        ngayHoc: String,
        tietHoc: String,
        confirmAllAttendance: Boolean=false,
       ghiChu:String=""
    ):Resource<MessageResponse>

    suspend fun layGhiChuBuoiHocCuaLTC(maLTC: Int,ngayHoc: String,tietHoc: String):Resource<GhiChuResponse>

    // DangKy
    suspend fun capNhatTheDiemDanh(
        theDiemDanh: String,
        maLTC: Int,
        maSV: String,
        ngayHoc: String,
        tietHoc: String
    ): Resource<MessageResponse>

    suspend fun huyTheDiemDanh(maLTC: Int, maSV: String?): Resource<MessageResponse>

    suspend fun diemDanhThuCong(
        maLTC: Int,
        maSV: String,
        ngayHoc: String,
        tietHoc: String,
        vang: Int
    ): Resource<MessageResponse>

    suspend fun diemDanhBangThe(
        maLTC: Int,
        ngayHoc: String,
        tietHoc: String,
        theDiemDanh: String
    ): Resource<DiemDanhTheResponse>
}