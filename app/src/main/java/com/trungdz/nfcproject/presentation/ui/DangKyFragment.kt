package com.trungdz.nfcproject.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.CompoundButton
import android.widget.RadioGroup.OnCheckedChangeListener
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.trungdz.nfcproject.R
import com.trungdz.nfcproject.data.model.dto.ThongTinSinhVienDangKyLTC
import com.trungdz.nfcproject.data.ulti.Resource
import com.trungdz.nfcproject.databinding.FragmentDangKyBinding
import com.trungdz.nfcproject.presentation.adapter.ThongTinSinhVienDangKyAdapter
import com.trungdz.nfcproject.presentation.viewmodel.DangKyFragmentViewModel
import com.trungdz.nfcproject.presentation.viewmodel.ShareViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DangKyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class DangKyFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_dang_ky, container, false)
    }

    private lateinit var binding: FragmentDangKyBinding
    private val viewModel: DangKyFragmentViewModel by viewModels()
    private val shareViewModel: ShareViewModel by activityViewModels()
    lateinit var itemThongTinSinhVienDangKyAdapter: ThongTinSinhVienDangKyAdapter
    var maLTC = -1
    var posAdapter = -1
    var statusButtonItemAdapter: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDangKyBinding.bind(view)
        setObserver()
        setEvent()
        val maGV = (activity as MainActivity).maGV

        viewModel.xuatLopTinChiTheoMaGV(maGV)
    }

    private fun setEvent() {
        binding.spnLTC.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
                val list = viewModel.listLTC.value?.data?.list
                list?.let {
                    if (it.isNotEmpty()) {
                        maLTC = viewModel.listLTC.value!!.data!!.list[pos].maLTC
                        shareViewModel.maLTC = maLTC // shareViewModel

                        viewModel.xuatNgayHocVaTietHocCuaLTCC(maLTC)
                        viewModel.xuatThongTinTatCaSinhVienCuaLTC(maLTC)
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        binding.spnNgayHoc.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
                val ngayHoc = viewModel.listNgayHocVaTietHoc.value!!.data!!.listNgayHoc[pos].ngayHoc
                shareViewModel.ngayHoc = ngayHoc

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        binding.spnTietHoc.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
                val tietHoc = viewModel.listNgayHocVaTietHoc.value!!.data!!.listTietHoc[pos].tietHoc
                shareViewModel.tietHoc = tietHoc
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        binding.edtTimKiem.doOnTextChanged { text, start, before, count ->
            if (shareViewModel.maLTC != -1) {
                viewModel.filterByMSSV = text.toString()
                viewModel.xuatThongTinTatCaSinhVienCuaLTC(maLTC)
            }
        }

        binding.checkboxDangKyThe.setOnCheckedChangeListener(object :
            CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                if (p1) {
                    viewModel.filterByTrangThaiTheDiemDanh = false
                    viewModel.xuatThongTinTatCaSinhVienCuaLTC(maLTC)
                } else {
                    viewModel.filterByTrangThaiTheDiemDanh = true
                    viewModel.xuatThongTinTatCaSinhVienCuaLTC(maLTC)
                }
            }

        })
    }

    private fun setObserver() {
        viewModel.listLTC.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> Log.d("DangKyFragment", " is Loading")
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
                    Log.d("DangKyFragment", " is error")
                }
            }
        }

        viewModel.listNgayHocVaTietHoc.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> Log.d("DangKyFragment", " is Loading")
                is Resource.Success -> {
                    it.data?.let { it2 ->
                        val listNgayHoc = it2.listNgayHoc
                        val listTietHoc = it2.listTietHoc

                        val spinnerNgayHocAdapter = ArrayAdapter(
                            requireContext(),
                            com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                            listNgayHoc
                        )
                        val spinnerTietHocAdapter = ArrayAdapter(
                            requireContext(),
                            com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                            listTietHoc
                        )

                        spinnerNgayHocAdapter.setDropDownViewResource(R.layout.multiline_spinner_dropdown_item)
                        spinnerTietHocAdapter.setDropDownViewResource(R.layout.multiline_spinner_dropdown_item)

                        binding.spnNgayHoc.adapter = spinnerNgayHocAdapter
                        binding.spnTietHoc.adapter = spinnerTietHocAdapter
                    }
                }
                is Resource.Error -> {
                    Log.d("DangKyFragment", " is error")
                }
            }
        }

        viewModel.listThongTinSinhVienDangKyLTC.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> Log.d("DangKyFragment", " is Loading")
                is Resource.Success -> {
                    it.data?.let { it2 ->
                        val itemList = it2.list

                        itemThongTinSinhVienDangKyAdapter = ThongTinSinhVienDangKyAdapter(
                            itemList,
                            object : ThongTinSinhVienDangKyAdapter.Listener {
                                override fun onClickUpdate(
                                    item: ThongTinSinhVienDangKyLTC, position: Int
                                ) {
                                    shareViewModel.maSV = item.masv
                                    statusButtonItemAdapter = "UPDATE"
                                    posAdapter = position
                                    Toast.makeText(
                                        requireContext(), "Quét thẻ để hoàn tất", Toast.LENGTH_SHORT
                                    ).show()
                                }

                                override fun onClickCancel(
                                    item: ThongTinSinhVienDangKyLTC, position: Int
                                ) {
                                    statusButtonItemAdapter = "HUY"
                                    posAdapter = position
                                    // huy the diem danh shareviewmodel
                                    shareViewModel.huyTheDiemDanh(item.masv)
                                }
                            })
                        binding.recyclerThongTinSinhVien.adapter = itemThongTinSinhVienDangKyAdapter
                    }
                }
                is Resource.Error -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        shareViewModel.messageDangKyResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> Log.d("DangKy", " is loading")
                is Resource.Success -> {
                    Toast.makeText(context, it.data?.message, Toast.LENGTH_LONG).show()
                    shareViewModel.maSV = null
                    itemThongTinSinhVienDangKyAdapter.updateStatusBtnUpdate(
                        statusButtonItemAdapter, posAdapter
                    )
                }
                is Resource.Error -> {
                    statusButtonItemAdapter = null
                    posAdapter = -1
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
         * @return A new instance of fragment DangKyFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) = DangKyFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM1, param1)
                putString(ARG_PARAM2, param2)
            }
        }
    }
}