package com.trungdz.nfcproject.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.trungdz.nfcproject.R
import com.trungdz.nfcproject.data.ulti.Resource
import com.trungdz.nfcproject.databinding.FragmentChiTietBuoiVangBinding
import com.trungdz.nfcproject.presentation.adapter.ChiTietBuoiVangAdapter
import com.trungdz.nfcproject.presentation.util.ItemMenuTag
import com.trungdz.nfcproject.presentation.viewmodel.ChiTietBuoiVangFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ChiTietBuoiVangFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class ChiTietBuoiVangFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_chi_tiet_buoi_vang, container, false)
    }

    private lateinit var binding: FragmentChiTietBuoiVangBinding
    private val viewModel: ChiTietBuoiVangFragmentViewModel by viewModels()
    private lateinit var chiTietBuoiVangAdapter: ChiTietBuoiVangAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentChiTietBuoiVangBinding.bind(view)

//        Log.d("Trung-Bundle1","${arguments?.getInt("maLTC")}")
//        Log.d("Trung-Bundle2","${arguments?.getString("maSV")}")

        setEvent()
        setObserver()
        viewModel.layChiTietBuoiHocVangCuaSinhVien(
            requireArguments().getInt("maLTC"),
            requireArguments().getString("maSV")!!
        )

    }

    private fun setEvent() {
        binding.backImage.setOnClickListener {
            val activity = (activity as MainActivity)
//            activity.removeFragment(ItemMenuTag.ID_CHITIETBUOIVANG_FRAGMENT)
            activity.supportFragmentManager.popBackStack()
            activity.makeDisplayFullScreen(false)
        }
    }

    private fun setObserver() {
        viewModel.list.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    it.data?.let { it2 ->
                        val list = it2.list
                        chiTietBuoiVangAdapter=ChiTietBuoiVangAdapter(list)
                        binding.recyclerView.adapter=chiTietBuoiVangAdapter
                    }
                }
                is Resource.Error -> {}
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
         * @return A new instance of fragment ChiTietBuoiVangFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ChiTietBuoiVangFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}