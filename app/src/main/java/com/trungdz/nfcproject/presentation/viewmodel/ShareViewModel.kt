package com.trungdz.nfcproject.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trungdz.nfcproject.data.model.response.DiemDanhTheResponse
import com.trungdz.nfcproject.data.model.response.MessageResponse
import com.trungdz.nfcproject.data.ulti.Resource
import com.trungdz.nfcproject.domain.repository.IRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShareViewModel @Inject constructor(private val iRepository: IRepository) : ViewModel() {

//    val messageAuthResponse: MutableLiveData<Resource<MessageResponse>> = MutableLiveData()
    val messageDangKyResponse: MutableLiveData<Resource<MessageResponse>> = MutableLiveData()
    val messageDiemDanhTheResponse: MutableLiveData<Resource<DiemDanhTheResponse>> = MutableLiveData()

    //
    var maSV: String? = null  // reset khi chuyển tab (chưa làm)
    var maLTC: Int = -1
    var ngayHoc: String? = null
    var tietHoc: String? = null
    fun resetViewModel() {
        this.maSV = null  // reset khi chuyển tab (chưa làm)
        this.maLTC = -1
        this.ngayHoc = null
        this.tietHoc = null
    }

//    fun xacThucGiangVien(maGV: Int) = viewModelScope.launch {
//
//        val response = iRepository.xacThucGiangVien(maGV,)
//        messageAuthResponse.postValue(response)
//    }

    fun capNhatTheDiemDanh(theDiemDanh: String) = viewModelScope.launch {
        if (maSV != null && ngayHoc != null && tietHoc != null) {
            val response =
                iRepository.capNhatTheDiemDanh(theDiemDanh, maLTC, maSV!!, ngayHoc!!, tietHoc!!)
            messageDangKyResponse.postValue(response)
        } else {
            messageDangKyResponse.postValue(Resource.Error(message = "Yêu cầu chọn đủ thông tin!"))
        }
    }

    fun huyTheDiemDanh(maSV: String) = viewModelScope.launch {
        val response = iRepository.huyTheDiemDanh(maLTC, maSV)
        messageDangKyResponse.postValue(response)
    }

    fun diemDanhBangThe(theDiemDanh: String) = viewModelScope.launch {
        if (ngayHoc != null && tietHoc != null) {
            val response = iRepository.diemDanhBangThe(maLTC, ngayHoc!!, tietHoc!!, theDiemDanh)
            messageDiemDanhTheResponse.postValue(response)
        }else{
            messageDiemDanhTheResponse.postValue(Resource.Error(message = "Yêu cầu thông tin buổi học để điểm danh"))
        }
    }

    // Điểm danh bằng thẻ
}