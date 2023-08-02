package com.trungdz.nfcproject.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trungdz.nfcproject.data.model.dto.ThongTinLTCTheoMAGV
import com.trungdz.nfcproject.data.model.dto.ThongTinSinhVienDangKyLTC
import com.trungdz.nfcproject.data.model.response.DataListResponse
import com.trungdz.nfcproject.data.model.response.NgayHocVaTietHocListResponse
import com.trungdz.nfcproject.data.ulti.Resource
import com.trungdz.nfcproject.domain.repository.IRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DangKyFragmentViewModel @Inject constructor(val iRepository: IRepository) : ViewModel() {
    val listLTC: MutableLiveData<Resource<DataListResponse<ThongTinLTCTheoMAGV>>> =
        MutableLiveData()
    val listNgayHocVaTietHoc: MutableLiveData<Resource<NgayHocVaTietHocListResponse>> =
        MutableLiveData()
    val listThongTinSinhVienDangKyLTC: MutableLiveData<Resource<DataListResponse<ThongTinSinhVienDangKyLTC>>> =
        MutableLiveData()

    var filterByTrangThaiTheDiemDanh: Boolean = true
    var filterByMSSV: String = ""
    fun xuatLopTinChiTheoMaGV(maGV: Int) = viewModelScope.launch {
        val response = iRepository.xuatLopTinChiTheoMaGV(maGV)
        listLTC.postValue(response)
    }

    fun xuatNgayHocVaTietHocCuaLTCC(maLTC: Int) = viewModelScope.launch {
        val response = iRepository.xuatNgayHocVaTietHocCuaLTCChuaChotDiemDanh(maLTC, false)

        listNgayHocVaTietHoc.postValue(response)
    }

    fun xuatThongTinTatCaSinhVienCuaLTC(
        maLTC: Int
//        filterByMSSV: String = ""
//        filterByTrangThaiTheDiemDanh: Boolean = true
    ) = viewModelScope.launch {
        if (maLTC != -1) {
            val response = iRepository.xuatThongTinTatCaSinhVienCuaLTC(
                maLTC,
                filterByMSSV,
                filterByTrangThaiTheDiemDanh
            )
            listThongTinSinhVienDangKyLTC.postValue(response)

        } else {

            listNgayHocVaTietHoc.postValue(Resource.Error(message = "Không tồn tại lớp tín chỉ nào"))
        }
    }
}