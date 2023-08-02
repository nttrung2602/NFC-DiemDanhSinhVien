package com.trungdz.nfcproject.presentation.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.trungdz.nfcproject.data.model.dto.ThongTinDiemDanhSVLTC
import com.trungdz.nfcproject.databinding.FragmentDiemDanhBinding
import com.trungdz.nfcproject.databinding.ItemThongtinSinhvienDiemdanhBinding

class ThongTinSinhVienDiemDanhAdapter(
    var itemList: List<ThongTinDiemDanhSVLTC>, val listener: Listener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemThongtinSinhvienDiemdanhBinding.inflate(layoutInflater, parent, false)

        return ItemHolder(binding)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = itemList[position]

        if (holder is ItemHolder) {
            holder.bind(item)
        }
    }

    fun updateStatusDiemDanhThe(maSV: String) {
        var element = itemList.find { it.maSV == maSV }
        element?.let {
            it.vang = 1
            val index = itemList.indexOf(element)
            notifyItemChanged(index)
        }
    }

    fun updateStatusVang(position: Int,statusVang:Int){
        itemList[position].vang=statusVang
        notifyItemChanged(position)
    }

    inner class ItemHolder(private val binding: ItemThongtinSinhvienDiemdanhBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        fun bind(item: ThongTinDiemDanhSVLTC) {
            binding.txtHoTen.text = "Họ tên: ${item.hoTen}"
            binding.txtMaSV.text = "MSSV: ${item.maSV}"
            binding.txtMaLop.text = "Mã lớp: ${item.maLop}"
//            setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)))

            // reset selector
            binding.btnVang.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor("#33000000"))
            binding.btnCM.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#33000000"))
            binding.btnVCP.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor("#33000000"))
            // selector
            when (item.vang) {
                0 -> binding.btnVang.backgroundTintList =
                    ColorStateList.valueOf(Color.parseColor("#B30AF314"))
                1 -> binding.btnCM.backgroundTintList =
                    ColorStateList.valueOf(Color.parseColor("#B30AF314"))
                2 -> binding.btnVCP.backgroundTintList =
                    ColorStateList.valueOf(Color.parseColor("#B30AF314"))
            }

            binding.btnCM.setOnClickListener {
                listener.onClickCM(
                    itemList[adapterPosition], adapterPosition
                )
            }
            binding.btnVCP.setOnClickListener {
                listener.onClickVCP(
                    itemList[adapterPosition], adapterPosition
                )
            }
            binding.btnVang.setOnClickListener {
                listener.onClickVang(
                    itemList[adapterPosition], adapterPosition
                )
            }
        }
    }

    interface Listener {
        fun onClickVCP(item: ThongTinDiemDanhSVLTC, position: Int)
        fun onClickCM(item: ThongTinDiemDanhSVLTC, position: Int)
        fun onClickVang(item: ThongTinDiemDanhSVLTC, position: Int)
    }
}