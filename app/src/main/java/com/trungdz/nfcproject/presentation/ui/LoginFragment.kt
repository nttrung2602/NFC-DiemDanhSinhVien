package com.trungdz.nfcproject.presentation.ui

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.trungdz.nfcproject.R
import com.trungdz.nfcproject.data.ulti.Resource
import com.trungdz.nfcproject.databinding.FragmentLoginBinding
import com.trungdz.nfcproject.presentation.util.ItemMenuTag
import com.trungdz.nfcproject.presentation.viewmodel.LoginFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    lateinit var binding:FragmentLoginBinding
    private val viewModel:LoginFragmentViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding=FragmentLoginBinding.bind(view)
        setEvent()
        setObserver()
    }

    private fun setEvent(){
        binding.btnLogin.setOnClickListener {
            val username=binding.edtUsername.text.toString()
            val password=binding.edtPassword.text.toString()

            if(username.isEmpty()){
                Toast.makeText(context,"Tài khoản không được để trống!",Toast.LENGTH_SHORT).show()
            }else if(password.isEmpty()){
                Toast.makeText(context,"Mật khẩu không được để trống!",Toast.LENGTH_SHORT).show()
            }else{
                viewModel.xacThucGiangVien(username,password)
            }
        }
    }
    private fun setObserver() {
        viewModel.messageAuthResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> Log.d("XacThuc", " is loading")
                is Resource.Success -> {
                    Toast.makeText(context, it.data?.message, Toast.LENGTH_SHORT).show()
                    val activity=(activity as MainActivity)
                    activity.makeDisplayFullScreen(false)
                    activity.setTextTenGV(it.data!!.hoTen)
                    activity.maGV=binding.edtUsername.text.toString().toInt()
                    val fragment=activity.supportFragmentManager.findFragmentByTag(ItemMenuTag.ID_DANGNHAP_FRAGMENT)
                    if (fragment != null) {
                        activity.supportFragmentManager.beginTransaction().remove(fragment).commit()
                    }
                }
                is Resource.Error -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}