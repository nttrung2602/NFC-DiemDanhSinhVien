package com.trungdz.nfcproject.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trungdz.nfcproject.data.model.dto.ChiTietBuoiVang
import com.trungdz.nfcproject.data.model.response.DataListResponse
import com.trungdz.nfcproject.data.ulti.Resource
import com.trungdz.nfcproject.domain.repository.IRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChiTietBuoiVangFragmentViewModel @Inject constructor(private val iRepository: IRepository): ViewModel() {
    val list:MutableLiveData<Resource<DataListResponse<ChiTietBuoiVang>>> = MutableLiveData()

    fun layChiTietBuoiHocVangCuaSinhVien(maLTC:Int,maSV: String)=viewModelScope.launch {
        val response=iRepository.layChiTietBuoiHocVangCuaSinhVien(maLTC, maSV)
        list.postValue(response)
    }
}