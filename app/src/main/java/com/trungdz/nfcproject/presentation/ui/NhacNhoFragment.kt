package com.trungdz.nfcproject.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.trungdz.nfcproject.R
import com.trungdz.nfcproject.data.ulti.Resource
import com.trungdz.nfcproject.databinding.FragmentNhacNhoBinding
import com.trungdz.nfcproject.presentation.adapter.ThongTinSinhVienDiemDanhAdapter
import com.trungdz.nfcproject.presentation.adapter.ThongTinSinhVienNhacNhoAdapter
import com.trungdz.nfcproject.presentation.viewmodel.NhacNhoFragmentViewModel
import com.trungdz.nfcproject.presentation.viewmodel.ShareViewModel
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NhacNhoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class NhacNhoFragment : Fragment() {
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
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nhac_nho, container, false)
    }

    lateinit var binding: FragmentNhacNhoBinding
    private val viewModel: NhacNhoFragmentViewModel by viewModels()
    private val shareViewModel: ShareViewModel by activityViewModels()
    lateinit var itemThongTinSinhVienNhacNhoAdapter: ThongTinSinhVienNhacNhoAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNhacNhoBinding.bind(view)
        shareViewModel.resetViewModel()

        setObserver()
        setEvent()
        val maGV = (activity as MainActivity).maGV
        viewModel.xuatLopTinChiTheoMaGV(maGV)
        viewModel.maGV = maGV
    }

    private fun setEvent() {

        binding.spnLTC.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
                val list = viewModel.listLTC.value?.data?.list
                list?.let {
                    if (it.isNotEmpty()) {
                        viewModel.maLTC = viewModel.listLTC.value!!.data!!.list[pos].maLTC
                        viewModel.locTopSoLuongSinhVienVangNhieu()
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        binding.edtSL.doOnTextChanged { text, _, _, _ ->
            if (text.toString() != "" && text.toString()[0] == '0') {
                binding.edtSL.setText(text.toString().drop(1))
            } else if (text.toString().isEmpty()) {
                viewModel.locTopSoLuongSinhVienVangNhieu()
            } else {
                viewModel.locTopSoLuongSinhVienVangNhieu(binding.edtSL.text.toString().toInt())
            }
        }
    }

    private fun setObserver() {
        viewModel.listLTC.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> Log.d("NhacNhoFragment", " is Loading")
                is Resource.Success -> {
                    it.data?.let { it2 ->
                        val itemList = it2.list
                        val spinnerAdapter = ArrayAdapter(
                            requireContext(),
                            com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                            itemList
                        )

                        spinnerAdapter.setDropDownViewResource(R.layout.multiline_spinner_dropdown_item) //set view dropdown
                        binding.spnLTC.adapter = spinnerAdapter
                    }
                }
                is Resource.Error -> {
                    Log.d("NhacNhoFragment", " is error")
                }
            }
        }

        viewModel.listThongTinSinhVienNhacNho.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> Log.d("NhacNhoFragment", " is Loading")
                is Resource.Success -> {
                    it.data?.let { it2 ->
                        val itemList = it2.list

                        itemThongTinSinhVienNhacNhoAdapter =
                            ThongTinSinhVienNhacNhoAdapter(itemList)

                        binding.recyclerThongTinSinhVien.adapter =
                            itemThongTinSinhVienNhacNhoAdapter
                    }
                }
                is Resource.Error -> {
                    Log.d("NhacNhoFragment", " is error")
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
         * @return A new instance of fragment NhacNhoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) = NhacNhoFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM1, param1)
                putString(ARG_PARAM2, param2)
            }
        }
    }
}