package com.trungdz.nfcproject.presentation.ui

import android.app.PendingIntent
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.*
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.trungdz.nfcproject.R
import com.trungdz.nfcproject.databinding.ActivityMainBinding
import com.trungdz.nfcproject.presentation.util.ItemMenuTag
import com.trungdz.nfcproject.presentation.viewmodel.ShareViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val viewModel: ShareViewModel by viewModels()
    lateinit var mNfcAdapter: NfcAdapter
    var maGV: Int = -1;
    private var oldPos = ItemMenuTag.ITEM_UNKNOWN
    private var currentTag: String? = null
    private val techList = arrayOf(
        arrayOf(
            NfcA::class.java.name,
            NfcB::class.java.name,
            NfcF::class.java.name,
            NfcV::class.java.name,
            IsoDep::class.java.name,
            MifareClassic::class.java.name,
            MifareUltralight::class.java.name,
            Ndef::class.java.name
        )
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()


//        mNfcAdapter = NfcAdapter.getDefaultAdapter(this)

        // device does not support NFC
//        if (mNfcAdapter == null) {
//            Toast.makeText(this, "This device doesn't support NFC.", Toast.LENGTH_LONG).show()
//            finish()
//            return
//        }

//        if (!mNfcAdapter.isEnabled()) {
//            findViewById<TextView>(R.id.textView_explanation).text = "NFC is disabled."
//        } else {
//            findViewById<TextView>(R.id.textView_explanation).text = "NFC is enabled."
//        }

//        handleIntent(intent)

        // Start coding here
        setEvent()

        makeDisplayFullScreen(true)
        navigateFragment(LoginFragment(), ItemMenuTag.ID_DANGNHAP_FRAGMENT)
    }

    fun setTextTenGV(s: String) {
        binding.txtTenGV.text = "Giảng viên: $s"
    }

    private fun setEvent() {
        binding.btnDangXuat.setOnClickListener {
            makeDisplayFullScreen(true)
            resetUI()
            navigateFragment(LoginFragment(), ItemMenuTag.ID_DANGNHAP_FRAGMENT)
        }

        binding.btnDangKy.setOnClickListener {
            resetSelectorItemMenu(oldPos)
            binding.btnDangKy.setBackgroundColor(Color.parseColor("#FF9800"))
            oldPos = ItemMenuTag.ITEM_DANGKY
            currentTag = ItemMenuTag.ID_DANGKY_FRAGMENT
            navigateFragment(DangKyFragment(), currentTag)
        }

        binding.btnDiemDanh.setOnClickListener {
            resetSelectorItemMenu(oldPos)
            binding.btnDiemDanh.setBackgroundColor(Color.parseColor("#FF9800"))
            oldPos = ItemMenuTag.ITEM_DIEMDANH
            currentTag = ItemMenuTag.ID_DIEMDANH_FRAGMENT

            navigateFragment(DiemDanhFragment(), currentTag)
        }

        binding.btnNhacNho.setOnClickListener {
            resetSelectorItemMenu(oldPos)
            binding.btnNhacNho.setBackgroundColor(Color.parseColor("#FF9800"))

            oldPos = ItemMenuTag.ITEM_NHACNHO
            currentTag = ItemMenuTag.ID_NHACNHO_FRAGMENT
            navigateFragment(NhacNhoFragment(), ItemMenuTag.ID_NHACNHO_FRAGMENT)
        }
        binding.btnLichSu.setOnClickListener {
            resetSelectorItemMenu(oldPos)
            binding.btnLichSu.setBackgroundColor(Color.parseColor("#FF9800"))
            oldPos = ItemMenuTag.ITEM_LICHSU
            currentTag = ItemMenuTag.ID_LICHSU_FRAGMENT
            navigateFragment(LichSuDiemDanhFragment(), ItemMenuTag.ID_LICHSU_FRAGMENT)
        }
    }

    private fun resetSelectorItemMenu(oldPos: Int) {
        when (oldPos) {
            ItemMenuTag.ITEM_DANGKY -> {
                binding.btnDangKy.setBackgroundColor(Color.parseColor("#CC2196F3"))
            }

            ItemMenuTag.ITEM_DIEMDANH -> {
                binding.btnDiemDanh.setBackgroundColor(Color.parseColor("#CC2196F3"))

            }

            ItemMenuTag.ITEM_LICHSU -> {
                binding.btnLichSu.setBackgroundColor(Color.parseColor("#CC2196F3"))

            }

            ItemMenuTag.ITEM_NHACNHO -> {
                binding.btnNhacNho.setBackgroundColor(Color.parseColor("#CC2196F3"))
            }
        }
    }

    fun makeDisplayFullScreen(status: Boolean) {
        if (status) {
            binding.groupView.visibility = View.GONE
        } else {
            binding.groupView.visibility = View.VISIBLE
        }
    }

    private fun resetUI() {
        resetSelectorItemMenu(oldPos)
        removeFragment(currentTag)
        oldPos = ItemMenuTag.ITEM_UNKNOWN
    }

    fun navigateFragment(fragment: Fragment, tag: String?) {
        try {
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.containerFragment, fragment, tag)
            ft.commit()
        } catch (e: Exception) {
            Toast.makeText(this, "Lỗi hiển thị màn hình", Toast.LENGTH_SHORT).show()
        }
    }

    fun navigateFragmentAddToBackStack(fragment: Fragment, tag: String?) {
        try {
            val ft = supportFragmentManager.beginTransaction()
            ft.add(R.id.containerFragment, fragment, tag)
            supportFragmentManager.findFragmentByTag(currentTag)?.let { ft.hide(it) }
            ft.addToBackStack(null)
            ft.commit()
        } catch (e: Exception) {
            Toast.makeText(this, "Lỗi hiển thị màn hình", Toast.LENGTH_SHORT).show()
        }
    }

    fun removeFragment(tag: String?) {
        val fragment = supportFragmentManager.findFragmentByTag(tag)
        if (fragment != null) {
            supportFragmentManager.beginTransaction().remove(fragment).commit()
        }
    }

    private fun setObserver() {
//        viewModel.messageAuthResponse.observe(this) {
//            when (it) {
//                is Resource.Loading -> Log.d("XacThuc", " is loading")
//                is Resource.Success -> {
//                    Toast.makeText(this, it.data?.message, Toast.LENGTH_SHORT).show()
//                    maGV = binding.editMaGV.text.toString().toInt()
//                    setItemMenu(true)
//                    binding.editMaGV.clearFocus()
//                    binding.editMaGV.isEnabled=false
//                    binding.btnXacNhan.text = "Thoát"
//                    binding.btnXacNhan.setBackgroundColor(Color.parseColor("#B3FA0303"))
//                }
//                is Resource.Error -> {
//                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
////                    clearFragment()
////                    removeFragment("DANGKY")
////                    setItemMenu(false)
//                }
//            }
//        }

//        viewModel.messageDangKyResponse.observe(this) {
//            when (it) {
//                is Resource.Loading -> Log.d("DangKy", " is loading")
//                is Resource.Success -> {
//                    Toast.makeText(this, it.data?.message, Toast.LENGTH_LONG).show()
//                    viewModel.maSV = null
//                    setItemMenu(true)
//                }
//                is Resource.Error -> {
//                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
    }

    override fun onBackPressed() {
        val alertDialog: AlertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle("Thông báo")
        alertDialog.setMessage("Bạn muốn thoát ứng dụng?")
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,
            "Đồng ý",
            DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
                finish()
            })
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,
        "Không",
        DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
        })
        alertDialog.show()
    }

    override fun onResume() {
        super.onResume()
        // creating pending intent:
        if (NfcAdapter.getDefaultAdapter(this) != null) {
            val pendingIntent = PendingIntent.getActivity(
                this, 0, Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0
            )
            // creating intent receiver for NFC events:
            val filter = IntentFilter()
            filter.addAction(NfcAdapter.ACTION_TAG_DISCOVERED)
            filter.addAction(NfcAdapter.ACTION_NDEF_DISCOVERED)
            filter.addAction(NfcAdapter.ACTION_TECH_DISCOVERED)
            // enabling foreground dispatch for getting intent from NFC event:
            val nfcAdapter = NfcAdapter.getDefaultAdapter(this)
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, arrayOf(filter), techList)
        }

    }

    override fun onPause() {
        super.onPause()
        // disabling foreground dispatch:
        if (NfcAdapter.getDefaultAdapter(this) != null) {
            val nfcAdapter = NfcAdapter.getDefaultAdapter(this)
            nfcAdapter.disableForegroundDispatch(this)
        }
//        val nfcAdapter = NfcAdapter.getDefaultAdapter(this)
//        nfcAdapter.disableForegroundDispatch(this)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent!!.action == NfcAdapter.ACTION_TAG_DISCOVERED) {
            val tag = intent!!.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
            val uid = tag!!.id
            // Convert the UID bytes to a hexadecimal string
            val uidString: String = ByteArrayToHexString(uid)

            if (oldPos == ItemMenuTag.ITEM_DANGKY) {
                if (viewModel.maSV != null) {
                    viewModel.capNhatTheDiemDanh(uidString)
                } else {
                    Toast.makeText(this, "Vui lòng chọn sinh viên!", Toast.LENGTH_SHORT).show()
                }
            } else if (oldPos == ItemMenuTag.ITEM_DIEMDANH) {
                viewModel.diemDanhBangThe(uidString)
            }


//            val dep = IsoDep.get(tag)
//            if (dep == null) {
//                // IsoDep is not supported by this Tag.
//                Log.d("AAAA1111", "IsoDep is not supported")
//
////                Log.d("AAAA1111", intent.getParcelableExtra(NfcAdapter.EXTRA_TAG));
//                return
//            }
////            Log.d("AAAA2222", intent.getParcelableExtra(NfcAdapter.EXTRA_TAG));
//            //            Log.d("AAAA2222", intent.getParcelableExtra(NfcAdapter.EXTRA_TAG));
//            Log.d("AAAA2222", "IsoDep is support")
        }
    }

    private fun ByteArrayToHexString(inArray: ByteArray): String {
        var i = -1
        var j = 0
        var inn = -1
        val hex =
            arrayOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F")
        var out = ""
        j = 0
        while (j < inArray.size) {
            inn = inArray[j].toInt() and 0xff
            i = inn shr 4 and 0x0f
            out += hex[i]
            i = inn and 0x0f
            out += hex[i]
            ++j
        }
        return out
    }

    private fun handleIntent(intent: Intent) {
        TODO("Not yet implemented")
    }
}