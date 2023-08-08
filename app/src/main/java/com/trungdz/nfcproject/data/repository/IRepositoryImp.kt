package com.trungdz.nfcproject.data.repository

import com.google.gson.GsonBuilder
import com.trungdz.nfcproject.data.model.dto.*
import com.trungdz.nfcproject.data.model.response.*
import com.trungdz.nfcproject.data.repository.datasource.AppDiemDanhRemoteDatasource
import com.trungdz.nfcproject.data.ulti.Resource
import com.trungdz.nfcproject.domain.repository.IRepository
import retrofit2.Response
import javax.inject.Inject


class IRepositoryImp @Inject constructor(private val appDiemDanhRemoteDatasource: AppDiemDanhRemoteDatasource) :
    IRepository {
    override suspend fun xacThucGiangVien(maGV: String, password: String): Resource<LoginResponse> {
        return responseToXacThucGiangVien(appDiemDanhRemoteDatasource.xacThucGiangVien(maGV,password))
    }

    private fun responseToXacThucGiangVien(response: Response<LoginResponse>): Resource<LoginResponse> {
        if (response.isSuccessful)
            response.body()?.let {
                return Resource.Success(it)
            }

        return Resource.Error(message = errorMessage(response.errorBody()?.string()))
    }

    override suspend fun xuatLopTinChiTheoMaGV(maGV: Int): Resource<DataListResponse<ThongTinLTCTheoMAGV>> {
        return responseToXuatLopTinChiTheoMaGV(
            appDiemDanhRemoteDatasource.xuatLopTinChiTheoMaGV(
                maGV
            )
        )
    }

    private fun responseToXuatLopTinChiTheoMaGV(response: Response<DataListResponse<ThongTinLTCTheoMAGV>>): Resource<DataListResponse<ThongTinLTCTheoMAGV>> {
        if (response.isSuccessful)
            response.body()?.let { return Resource.Success(it) }

        return Resource.Error(message = errorMessage(response.errorBody()?.string()))
    }

    override suspend fun xuatNgayHocVaTietHocCuaLTCChuaChotDiemDanh(
        maLTC: Int,
        chotDiemDanh: Boolean
    ): Resource<NgayHocVaTietHocListResponse> {
        return responseToXuatNgayHocVaTietHocCuaLTCChuaChotDiemDanh(
            appDiemDanhRemoteDatasource.xuatNgayHocVaTietHocCuaLTCChuaChotDiemDanh(
                maLTC,
                chotDiemDanh
            )
        )
    }

    override suspend fun layChiTietBuoiHocVangCuaSinhVien(
        maLTC: Int,
        maSV: String
    ): Resource<DataListResponse<ChiTietBuoiVang>> {
        return responseToLayChiTietBuoiHocVangCuaSinhVien(appDiemDanhRemoteDatasource.layChiTietBuoiHocVangCuaSinhVien(maLTC, maSV))
    }

    private fun responseToLayChiTietBuoiHocVangCuaSinhVien(response: Response<DataListResponse<ChiTietBuoiVang>>):Resource<DataListResponse<ChiTietBuoiVang>>{
        if(response.isSuccessful)
            response.body()?.let { return Resource.Success(it) }
        return Resource.Error(message = errorMessage(response.errorBody()?.string()))
    }

    private fun responseToXuatNgayHocVaTietHocCuaLTCChuaChotDiemDanh(response: Response<NgayHocVaTietHocListResponse>): Resource<NgayHocVaTietHocListResponse> {
        if (response.isSuccessful)
            response.body()?.let { return Resource.Success(it) }
        return Resource.Error(message = errorMessage(response.errorBody()?.string()))
    }

    override suspend fun xuatThongTinTatCaSinhVienCuaLTC(
        maLTC: Int,
        filterByMSSV: String,
        trangThaiTheDiemDanh: Boolean
    ): Resource<DataListResponse<ThongTinSinhVienDangKyLTC>> {
        return responseToXuatThongTinTatCaSinhVienCuaLTC(
            appDiemDanhRemoteDatasource.xuatThongTinTatCaSinhVienCuaLTC(
                maLTC,
                filterByMSSV,
                trangThaiTheDiemDanh
            )
        )
    }

    private fun responseToXuatThongTinTatCaSinhVienCuaLTC(response: Response<DataListResponse<ThongTinSinhVienDangKyLTC>>): Resource<DataListResponse<ThongTinSinhVienDangKyLTC>> {
        if (response.isSuccessful)
            response.body()?.let { return Resource.Success(it) }
        return Resource.Error(message = errorMessage(response.errorBody()?.string()))
    }


    override suspend fun layDanhSachDiemDanhSinhVienTheoTKB_LTC(
        maLTC: Int,
        ngayHoc: String,
        tietHoc: String,
        filterByName: String,
        filterByVang: Int
    ): Resource<DataListResponse<ThongTinDiemDanhSVLTC>> {
        return responseToLayDanhSachDiemDanhSinhVienTheoTKB_LTC(
            appDiemDanhRemoteDatasource.layDanhSachDiemDanhSinhVienTheoTKB_LTC(
                maLTC,
                ngayHoc,
                tietHoc,
                filterByName,
                filterByVang
            )
        )
    }

    private fun responseToLayDanhSachDiemDanhSinhVienTheoTKB_LTC(response: Response<DataListResponse<ThongTinDiemDanhSVLTC>>): Resource<DataListResponse<ThongTinDiemDanhSVLTC>> {

        if (response.isSuccessful)
            response.body()?.let { return Resource.Success(it) }
        return Resource.Error(message = errorMessage(response.errorBody()?.string()))

    }

    override suspend fun locTopSoLuongSinhVienVangNhieu(
        maLTC: Int,
        soLuong: Int
    ): Resource<DataListResponse<ThongTinSinhVienNhacNho>> {
        return responseToLocTopSoLuongSinhVienVangNhieu(
            appDiemDanhRemoteDatasource.locTopSoLuongSinhVienVangNhieu(
                maLTC,
                soLuong
            )
        )
    }

    private fun responseToLocTopSoLuongSinhVienVangNhieu(response: Response<DataListResponse<ThongTinSinhVienNhacNho>>): Resource<DataListResponse<ThongTinSinhVienNhacNho>> {
        if (response.isSuccessful)
            response.body()?.let { return Resource.Success(it) }

        return Resource.Error(message = errorMessage(response.errorBody()?.string()))
    }

    override suspend fun chotDiemDanh(
        maLTC: Int,
        ngayHoc: String,
        tietHoc: String,
        confirmAllAttendance: Boolean,
        ghiChu: String
    ): Resource<MessageResponse> {
        return responseToChotDiemDanh(
            appDiemDanhRemoteDatasource.chotDiemDanh(
                maLTC,
                ngayHoc,
                tietHoc,
                confirmAllAttendance, ghiChu
            )
        )
    }

    private fun responseToChotDiemDanh(response: Response<MessageResponse>): Resource<MessageResponse> {
        if (response.isSuccessful)
            response.body()?.let { return Resource.Success(it) }
        return Resource.Error(message = errorMessage(response.errorBody()?.string()))
    }

    override suspend fun layGhiChuBuoiHocCuaLTC(
        maLTC: Int,
        ngayHoc: String,
        tietHoc: String
    ): Resource<GhiChuResponse> {
        return responseToLayGhiChuBuoiHocCuaLTC(
            appDiemDanhRemoteDatasource.layGhiChuBuoiHocCuaLTC(
                maLTC,
                ngayHoc,
                tietHoc
            )
        )
    }

    private fun responseToLayGhiChuBuoiHocCuaLTC(response: Response<GhiChuResponse>): Resource<GhiChuResponse> {
        if (response.isSuccessful)
            response.body()?.let { return Resource.Success(it) }
        return Resource.Error(message = errorMessage(response.errorBody()?.string()))
    }

    // DangKy===========================
    override suspend fun capNhatTheDiemDanh(
        theDiemDanh: String, maLTC: Int, maSV: String, ngayHoc: String, tietHoc: String
    ): Resource<MessageResponse> {
        return responsesToCapNhatTheDiemDanh(
            appDiemDanhRemoteDatasource.capNhatTheDiemDanh(
                theDiemDanh,
                maLTC,
                maSV,
                ngayHoc,
                tietHoc
            )
        )
    }

    private fun responsesToCapNhatTheDiemDanh(response: Response<MessageResponse>): Resource<MessageResponse> {
        if (response.isSuccessful)
            response.body()?.let { return Resource.Success(it) }
        return Resource.Error(message = errorMessage(response.errorBody()?.string()))
    }

    override suspend fun huyTheDiemDanh(maLTC: Int, maSV: String?): Resource<MessageResponse> {
        return responseToHuyTheDiemDanh(appDiemDanhRemoteDatasource.huyTheDiemDanh(maLTC, maSV))
    }

    private fun responseToHuyTheDiemDanh(response: Response<MessageResponse>): Resource<MessageResponse> {
        if (response.isSuccessful)
            response.body()?.let { return Resource.Success(it) }
        return Resource.Error(message = errorMessage(response.errorBody()?.string()))
    }


    override suspend fun diemDanhThuCong(
        maLTC: Int,
        maSV: String,
        ngayHoc: String,
        tietHoc: String,
        vang: Int
    ): Resource<MessageResponse> {
        return responseToDiemDanhThuCong(
            appDiemDanhRemoteDatasource.diemDanhThuCong(
                maLTC,
                maSV,
                ngayHoc,
                tietHoc,
                vang
            )
        )
    }

    private fun responseToDiemDanhThuCong(response: Response<MessageResponse>): Resource<MessageResponse> {
        if (response.isSuccessful)
            response.body()?.let { return Resource.Success(it) }
        return Resource.Error(message = errorMessage(response.errorBody()?.string()))
    }

    override suspend fun diemDanhBangThe(
        maLTC: Int,
        ngayHoc: String,
        tietHoc: String,
        theDiemDanh: String
    ): Resource<DiemDanhTheResponse> {
        return responseToDiemDanhBangThe(
            appDiemDanhRemoteDatasource.diemDanhBangThe(
                maLTC,
                ngayHoc,
                tietHoc,
                theDiemDanh
            )
        )
    }

    private fun responseToDiemDanhBangThe(response: Response<DiemDanhTheResponse>): Resource<DiemDanhTheResponse> {
        if (response.isSuccessful)
            response.body()?.let { return Resource.Success(it) }
        return Resource.Error(message = errorMessage(response.errorBody()?.string()))
    }

    // util
    private fun errorMessage(message: String?): String {
        val gson = GsonBuilder().create()
        val messageResponse = gson.fromJson(message, MessageResponse::class.java)

        return messageResponse.message
    }

}