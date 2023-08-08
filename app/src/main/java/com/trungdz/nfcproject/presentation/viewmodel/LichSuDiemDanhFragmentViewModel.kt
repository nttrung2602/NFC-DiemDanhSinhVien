package com.trungdz.nfcproject.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trungdz.nfcproject.data.model.dto.ThongTinDiemDanhSVLTC
import com.trungdz.nfcproject.data.model.dto.ThongTinLTCTheoMAGV
import com.trungdz.nfcproject.data.model.response.DataListResponse
import com.trungdz.nfcproject.data.model.response.GhiChuResponse
import com.trungdz.nfcproject.data.model.response.MessageResponse
import com.trungdz.nfcproject.data.model.response.NgayHocVaTietHocListResponse
import com.trungdz.nfcproject.data.ulti.Resource
import com.trungdz.nfcproject.domain.repository.IRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LichSuDiemDanhFragmentViewModel @Inject constructor(private val iRepository: IRepository) :
    ViewModel() {
    // livedata
    val listLTC: MutableLiveData<Resource<DataListResponse<ThongTinLTCTheoMAGV>>> =
        MutableLiveData()
    val listNgayHocVaTietHoc: MutableLiveData<Resource<NgayHocVaTietHocListResponse>> =
        MutableLiveData()
    val listThongTinSinhVienDangKyLTC: MutableLiveData<Resource<DataListResponse<ThongTinDiemDanhSVLTC>>> =
        MutableLiveData()
    val messageDiemDanhResponse: MutableLiveData<Resource<MessageResponse>> = MutableLiveData()
//    val messageChotDiemDanhResponse: MutableLiveData<Resource<MessageResponse>> = MutableLiveData()
    val ghiChuResponse:MutableLiveData<Resource<GhiChuResponse>> = MutableLiveData()

    //
    var ngayHoc: String = ""
    var tietHoc: String = ""
    var filterByVang: Int = -1
    var filterByName = ""
    var maGV: Int = -1
    var maLTC: Int = -1

    //
    fun xuatLopTinChiTheoMaGV(maGV: Int) = viewModelScope.launch {
        val response = iRepository.xuatLopTinChiTheoMaGV(maGV)
        listLTC.postValue(response)
    }

    fun xuatNgayHocVaTietHocCuaLTC(maLTC: Int) = viewModelScope.launch {
        val response = iRepository.xuatNgayHocVaTietHocCuaLTCChuaChotDiemDanh(maLTC, true)

        listNgayHocVaTietHoc.postValue(response)
    }

    fun layDanhSachDiemDanhSinhVienTheoTKB_LTC() = viewModelScope.launch {
        if (ngayHoc != "" && tietHoc != "" && filterByVang != -1) {

            val response = iRepository.layDanhSachDiemDanhSinhVienTheoTKB_LTC(
                maLTC, ngayHoc, tietHoc, filterByName, filterByVang
            )
            val response1=iRepository.layGhiChuBuoiHocCuaLTC(maLTC, ngayHoc, tietHoc)
            listThongTinSinhVienDangKyLTC.postValue(response)
            ghiChuResponse.postValue(response1)

        }
//        else {
//            listThongTinSinhVienDangKyLTC.postValue(Resource.Error(message = "Chưa thể tải danh sách điểm danh. Thử lại sau!"))
//        }
    }

    fun diemDanhThuCong(maSV: String, statusVang: Int) = viewModelScope.launch {
        val response = iRepository.diemDanhThuCong(maLTC, maSV, ngayHoc, tietHoc, statusVang)
        messageDiemDanhResponse.postValue(response)
    }

//    fun chotDiemDanh(confirmAllAttendance: Boolean = false, ghiChu: String = "") =
//        viewModelScope.launch {
//            val response =
//                iRepository.chotDiemDanh(maLTC, ngayHoc, tietHoc, confirmAllAttendance, ghiChu)
//            messageChotDiemDanhResponse.postValue(response)
//        }
}