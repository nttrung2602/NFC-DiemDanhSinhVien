package com.trungdz.nfcproject.data.repository.datasourceImp


import com.trungdz.nfcproject.data.api.AppDiemDanhApiService
import com.trungdz.nfcproject.data.model.dto.*
import com.trungdz.nfcproject.data.model.request.CapNhatTheDiemDanhRequest
import com.trungdz.nfcproject.data.model.request.LTCTheoMaGVRequest
import com.trungdz.nfcproject.data.model.request.LoginGiangVienRequest
import com.trungdz.nfcproject.data.model.request.ThongTinTheDiemDanhRequest
import com.trungdz.nfcproject.data.model.response.*
import com.trungdz.nfcproject.data.repository.datasource.AppDiemDanhRemoteDatasource
import retrofit2.Response
import javax.inject.Inject

class AppDiemDanhRemoteDataSourceImp @Inject constructor(private val appDiemDanhApiService: AppDiemDanhApiService) :
    AppDiemDanhRemoteDatasource {
    override suspend fun xacThucGiangVien(maGV: String, password: String): Response<LoginResponse> {
        return appDiemDanhApiService.authenticateGiangVien(LoginGiangVienRequest(maGV,password))
    }

    override suspend fun xuatLopTinChiTheoMaGV(maGV: Int): Response<DataListResponse<ThongTinLTCTheoMAGV>> {
        return appDiemDanhApiService.xuatLopTinChiTheoMaGV(LTCTheoMaGVRequest(maGV = maGV))
    }

    override suspend fun xuatNgayHocVaTietHocCuaLTCChuaChotDiemDanh(
        maLTC: Int, chotDiemDanh: Boolean
    ): Response<NgayHocVaTietHocListResponse> {
        return appDiemDanhApiService.xuatNgayHocVaTietHocCuaMotLTC(maLTC, chotDiemDanh)
    }

    override suspend fun xuatThongTinTatCaSinhVienCuaLTC(
        maLTC: Int, filterByMSSV: String, trangThaiTheDiemDanh: Boolean
    ): Response<DataListResponse<ThongTinSinhVienDangKyLTC>> {
        return appDiemDanhApiService.xuatThongTinTatCaSinhVienCuaLTC(
            maLTC, filterByMSSV, trangThaiTheDiemDanh
        )
    }

    override suspend fun layChiTietBuoiHocVangCuaSinhVien(
        maLTC: Int,
        maSV: String
    ): Response<DataListResponse<ChiTietBuoiVang>> {
        return appDiemDanhApiService.layChiTietBuoiHocVangCuaSinhVien(maLTC, maSV)
    }
    override suspend fun layDanhSachDiemDanhSinhVienTheoTKB_LTC(
        maLTC: Int, ngayHoc: String, tietHoc: String, filterByName: String, filterByVang: Int
    ): Response<DataListResponse<ThongTinDiemDanhSVLTC>> {
        return appDiemDanhApiService.layDanhSachDiemDanhSinhVienTheoTKB_LTC(
            maLTC, ngayHoc, tietHoc, filterByName, filterByVang
        )
    }

    override suspend fun locTopSoLuongSinhVienVangNhieu(
        maLTC: Int,
        soLuong: Int
    ): Response<DataListResponse<ThongTinSinhVienNhacNho>> {
        return appDiemDanhApiService.locTopSoLuongSinhVienVangNhieu(maLTC, soLuong)
    }

    override suspend fun chotDiemDanh(
        maLTC: Int,
        ngayHoc: String,
        tietHoc: String,
        confirmAllAttendance: Boolean,
        ghiChu: String
    ): Response<MessageResponse> {
        return appDiemDanhApiService.chotDiemDanh(maLTC, ngayHoc, tietHoc, confirmAllAttendance,ghiChu)
    }

    override suspend fun layGhiChuBuoiHocCuaLTC(
        maLTC: Int,
        ngayHoc: String,
        tietHoc: String
    ): Response<GhiChuResponse> {
        return appDiemDanhApiService.layGhiChuBuoiHocCuaLTC(maLTC, ngayHoc, tietHoc)
    }

    // DangKy
    override suspend fun diemDanhThuCong(
        maLTC: Int, maSV: String, ngayHoc: String, tietHoc: String, vang: Int
    ): Response<MessageResponse> {
        return appDiemDanhApiService.diemDanhThuCong(maLTC, maSV, ngayHoc, tietHoc, vang)
    }

    override suspend fun capNhatTheDiemDanh(
        theDiemDanh: String, maLTC: Int, maSV: String, ngayHoc: String, tietHoc: String
    ): Response<MessageResponse> {
        val capNhatTheDiemDanhRequest = CapNhatTheDiemDanhRequest(maSV, theDiemDanh)

        return appDiemDanhApiService.capNhatTheDiemDanh(
            maLTC, ngayHoc, tietHoc, capNhatTheDiemDanhRequest
        )
    }

    override suspend fun huyTheDiemDanh(maLTC: Int, maSV: String?): Response<MessageResponse> {
        return appDiemDanhApiService.huyTheDiemDanh(maLTC, maSV)
    }

    override suspend fun diemDanhBangThe(
        maLTC: Int, ngayHoc: String, tietHoc: String, theDiemDanh: String
    ): Response<DiemDanhTheResponse> {
        return appDiemDanhApiService.diemDanhBangThe(
            maLTC, ngayHoc, tietHoc, ThongTinTheDiemDanhRequest(theDiemDanh)
        )
    }
}