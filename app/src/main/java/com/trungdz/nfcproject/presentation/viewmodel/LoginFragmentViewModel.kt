package com.trungdz.nfcproject.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trungdz.nfcproject.data.model.response.LoginResponse
import com.trungdz.nfcproject.data.model.response.MessageResponse
import com.trungdz.nfcproject.data.ulti.Resource
import com.trungdz.nfcproject.domain.repository.IRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginFragmentViewModel @Inject constructor(private val iRepository: IRepository):ViewModel() {
    val messageAuthResponse: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    fun xacThucGiangVien(maGV: String,password:String) = viewModelScope.launch {

        val response = iRepository.xacThucGiangVien(maGV,password)
        messageAuthResponse.postValue(response)
    }

}