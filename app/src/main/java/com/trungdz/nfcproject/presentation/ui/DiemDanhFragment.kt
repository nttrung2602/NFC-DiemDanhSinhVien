package com.trungdz.nfcproject.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.trungdz.nfcproject.R
import com.trungdz.nfcproject.data.model.dto.ThongTinDiemDanhSVLTC
import com.trungdz.nfcproject.data.ulti.Resource
import com.trungdz.nfcproject.databinding.FragmentDiemDanhBinding
import com.trungdz.nfcproject.presentation.adapter.ThongTinSinhVienDiemDanhAdapter
import com.trungdz.nfcproject.presentation.ui.customdialog.ReviewDialog
import com.trungdz.nfcproject.presentation.viewmodel.DiemDanhFragmentViewModel
import com.trungdz.nfcproject.presentation.viewmodel.ShareViewModel
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DiemDanhFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class DiemDanhFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_diem_danh, container, false)
    }

    private lateinit var binding: FragmentDiemDanhBinding
    private val viewModel: DiemDanhFragmentViewModel by viewModels()
    private val shareViewModel: ShareViewModel by activityViewModels()
     var itemThongTinSinhVienDiemDanhAdapter: ThongTinSinhVienDiemDanhAdapter?=null
    val listFilterVang = arrayOf("Không lọc", "Vắng", "Có mặt", "Vắng có phép")
    var posAdapter = -1
    var selectedStatusVang = -1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDiemDanhBinding.bind(view)
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
                Log.d("Trung-DiemDanh","aaaa")
                val list = viewModel.listLTC.value?.data?.list
                list?.let {
                    if (it.isNotEmpty()) {
                        viewModel.maLTC = viewModel.listLTC.value!!.data!!.list[pos].maLTC
                        shareViewModel.maLTC = viewModel.maLTC // shareViewModel

                        viewModel.xuatNgayHocVaTietHocCuaLTC(viewModel.maLTC)
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        binding.spnFilterVang.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
//                val list = viewModel.listNgayHocVaTietHoc.value?.data?.listNgayHoc

//                if(list!!.isNotEmpty()){
                    if (pos == 0) {
                        viewModel.filterByVang = -1
//                        viewModel.layDanhSachDiemDanhSinhVienTheoTKB_LTC()
                        if(itemThongTinSinhVienDiemDanhAdapter != null){
                            (itemThongTinSinhVienDiemDanhAdapter?.itemList as ArrayList).clear()
                            itemThongTinSinhVienDiemDanhAdapter?.notifyDataSetChanged()
                        }
                    } else {
                        // pos - 1
                        viewModel.filterByVang = pos - 1
                        viewModel.layDanhSachDiemDanhSinhVienTheoTKB_LTC()
                    }

//                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        binding.spnNgayHoc.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
                    val ngayHoc = viewModel.listNgayHocVaTietHoc.value!!.data!!.listNgayHoc[pos].ngayHoc
                    shareViewModel.ngayHoc = ngayHoc
                    viewModel.ngayHoc = ngayHoc

                    viewModel.layDanhSachDiemDanhSinhVienTheoTKB_LTC()

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        binding.spnTietHoc.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
                    val tietHoc = viewModel.listNgayHocVaTietHoc.value!!.data!!.listTietHoc[pos].tietHoc
                    shareViewModel.tietHoc = tietHoc
                    viewModel.tietHoc = tietHoc

                    viewModel.layDanhSachDiemDanhSinhVienTheoTKB_LTC()



            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }


        binding.edtTimKiem.doOnTextChanged { text, _, _, _ ->
            if (viewModel.maLTC != -1) {
                viewModel.filterByName = text.toString()
                viewModel.layDanhSachDiemDanhSinhVienTheoTKB_LTC()
            }
        }

        binding.btnCDD.setOnClickListener {
            val reviewDialog=ReviewDialog.Static.newInstance(object : ReviewDialog.Listener{
                override fun onClickConfirm(text: String) {
                    viewModel.chotDiemDanh(confirmAllAttendance = false, ghiChu =text)
                }
            })
            reviewDialog.show(requireFragmentManager(),"dialog_note")
        }

        binding.btnDDConLai.setOnClickListener {
            val reviewDialog=ReviewDialog.Static.newInstance(object : ReviewDialog.Listener{
                override fun onClickConfirm(text: String) {
                    viewModel.chotDiemDanh(confirmAllAttendance = true, ghiChu =text)
                }
            })
            reviewDialog.show(requireFragmentManager(),"dialog_note")
        }
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

                        // spinner listFilterVang
                        val spinnerAdapter = ArrayAdapter(
                            requireContext(),
                            com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                            listFilterVang
                        )
                        spinnerAdapter.setDropDownViewResource(R.layout.multiline_spinner_dropdown_item) //set view dropdown
                        binding.spnFilterVang.adapter = spinnerAdapter

                        //
                        if (listNgayHoc.isNotEmpty() && listTietHoc.isNotEmpty()) {
                            viewModel.ngayHoc = listNgayHoc[0].ngayHoc
                            viewModel.tietHoc = listTietHoc[0].tietHoc
                            viewModel.layDanhSachDiemDanhSinhVienTheoTKB_LTC()
                        }
                    }
                }
                is Resource.Error -> {
                    Log.d("DangKyFragment", " is error")
                }
            }
        }

        viewModel.listThongTinSinhVienDangKyLTC.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> Log.d("DiemDanhFragment", " is Loading")

                is Resource.Success -> {
                    it.data?.let { it2 ->
                        val itemList = it2.list

                        itemThongTinSinhVienDiemDanhAdapter = ThongTinSinhVienDiemDanhAdapter(
                            itemList,
                            object : ThongTinSinhVienDiemDanhAdapter.Listener {
                                override fun onClickVCP(
                                    item: ThongTinDiemDanhSVLTC, position: Int
                                ) {
                                    viewModel.diemDanhThuCong(item.maSV, 2)
                                    selectedStatusVang = 2
                                    posAdapter = position
                                }

                                override fun onClickCM(
                                    item: ThongTinDiemDanhSVLTC, position: Int
                                ) {
                                    viewModel.diemDanhThuCong(item.maSV, 1)
                                    selectedStatusVang = 1
                                    posAdapter = position
                                }

                                override fun onClickVang(
                                    item: ThongTinDiemDanhSVLTC, position: Int
                                ) {
                                    viewModel.diemDanhThuCong(item.maSV, 0)
                                    selectedStatusVang = 0
                                    posAdapter = position
                                }
                            })

                        binding.recyclerThongTinSinhVien.adapter =
                            itemThongTinSinhVienDiemDanhAdapter
                    }
                }
                is Resource.Error -> Toast.makeText(context,it.message,Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.messageDiemDanhResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> Log.d("DiemDanhFragment", " is Loading")

                is Resource.Success -> {
                    it.data?.let { it2 ->
                        Toast.makeText(context, it2.message, Toast.LENGTH_SHORT).show()

                        itemThongTinSinhVienDiemDanhAdapter?.updateStatusVang(
                            posAdapter,
                            selectedStatusVang
                        )

                    }
                }
                is Resource.Error -> {
                    posAdapter = -1
                    selectedStatusVang = -1
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        shareViewModel.messageDiemDanhTheResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> Log.d("DiemDanhFragment", " is Loading")

                is Resource.Success -> {
                    it.data?.let { it2 ->
                        Toast.makeText(context, "${it2.message} ${it2.maSV}", Toast.LENGTH_SHORT).show()
                        itemThongTinSinhVienDiemDanhAdapter?.updateStatusDiemDanhThe(it2.maSV)
                    }
                }
                is Resource.Error -> Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.messageChotDiemDanhResponse.observe(viewLifecycleOwner){
            when (it) {
                is Resource.Loading -> Log.d("DiemDanhFragment", " is Loading")

                is Resource.Success -> {
                    it.data?.let { it2 ->
                        Toast.makeText(context, "${it2.message}", Toast.LENGTH_SHORT).show()
                        viewModel.xuatNgayHocVaTietHocCuaLTC(viewModel.maLTC)
                    }
                }
                is Resource.Error -> Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
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
         * @return A new instance of fragment DiemDanhFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) = DiemDanhFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM1, param1)
                putString(ARG_PARAM2, param2)
            }
        }
    }
}