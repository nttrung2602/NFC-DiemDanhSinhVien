package com.trungdz.nfcproject.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trungdz.nfcproject.data.model.dto.ThongTinDiemDanhSVLTC
import com.trungdz.nfcproject.data.model.dto.ThongTinLTCTheoMAGV
import com.trungdz.nfcproject.data.model.dto.ThongTinSinhVienNhacNho
import com.trungdz.nfcproject.data.model.response.DataListResponse
import com.trungdz.nfcproject.data.model.response.NgayHocVaTietHocListResponse
import com.trungdz.nfcproject.data.ulti.Resource
import com.trungdz.nfcproject.domain.repository.IRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NhacNhoFragmentViewModel @Inject constructor(val iRepository: IRepository) : ViewModel() {
    // livedata
    val listLTC: MutableLiveData<Resource<DataListResponse<ThongTinLTCTheoMAGV>>> =
        MutableLiveData()
    val listNgayHocVaTietHoc: MutableLiveData<Resource<NgayHocVaTietHocListResponse>> =
        MutableLiveData()
    val listThongTinSinhVienNhacNho: MutableLiveData<Resource<DataListResponse<ThongTinSinhVienNhacNho>>> =
        MutableLiveData()

    //
    var maGV: Int = -1
    var ngayHoc: String = ""
    var tietHoc: String = ""
    var maLTC: Int = -1
    fun xuatLopTinChiTheoMaGV(maGV: Int) = viewModelScope.launch {
        val response = iRepository.xuatLopTinChiTheoMaGV(maGV)
        listLTC.postValue(response)
    }

    fun xuatNgayHocVaTietHocCuaLTC(maLTC: Int) = viewModelScope.launch {
        val response = iRepository.xuatNgayHocVaTietHocCuaLTCChuaChotDiemDanh(maLTC, true)

        listNgayHocVaTietHoc.postValue(response)
    }

    fun locTopSoLuongSinhVienVangNhieu(soLuong: Int = 10) = viewModelScope.launch {
        val response = iRepository.locTopSoLuongSinhVienVangNhieu(maLTC, soLuong)

        listThongTinSinhVienNhacNho.postValue(response)
    }
}