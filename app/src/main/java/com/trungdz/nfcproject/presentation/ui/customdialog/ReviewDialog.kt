package com.trungdz.nfcproject.presentation.ui.customdialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.google.gson.Gson
import com.trungdz.nfcproject.R
import com.trungdz.nfcproject.databinding.DialogGhichuChotdiemdanhBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewDialog(private val listener: Listener) : DialogFragment() {

    object Static {
        fun newInstance(listener:Listener): ReviewDialog {
            val reviewDialog = ReviewDialog(listener)
            return reviewDialog
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        this.isCancelable = false
        return inflater.inflate(R.layout.dialog_ghichu_chotdiemdanh, container)
    }

    private lateinit var binding:DialogGhichuChotdiemdanhBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DialogGhichuChotdiemdanhBinding.bind(view)

        setEventListener()
    }

    private fun setEventListener() {
        binding.btnConfirm.setOnClickListener {
            val text=binding.editTextNote.text.toString()
            listener.onClickConfirm(text)
            dismiss()
        }
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }

    interface Listener{
        fun onClickConfirm(text:String)
    }
}