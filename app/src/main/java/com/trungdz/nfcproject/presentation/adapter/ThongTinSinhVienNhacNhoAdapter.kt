package com.trungdz.nfcproject.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.trungdz.nfcproject.data.model.dto.ThongTinSinhVienNhacNho
import com.trungdz.nfcproject.databinding.ItemThongtinSinhvienNhacnhoBinding

class ThongTinSinhVienNhacNhoAdapter(
    val itemList: List<ThongTinSinhVienNhacNho>,
    val listener: Listener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface Listener {
        fun onClick(item: ThongTinSinhVienNhacNho, position: Int)
    }

    inner class ItemHolder(val binding: ItemThongtinSinhvienNhacnhoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ThongTinSinhVienNhacNho) {
            binding.txtHoTen.text = "Họ tên: ${item.hoTen}"
            binding.txtMaLop.text = "Mã lớp: ${item.maLop}"
            binding.txtMaSV.text = "MSSV: ${item.maSV}"
            binding.txtSBV.text = "Số buổi vắng: ${item.soBuoiVang}"

            binding.btnChiTiet.setOnClickListener {
                listener.onClick(itemList[adapterPosition],adapterPosition) 
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemThongtinSinhvienNhacnhoBinding.inflate(layoutInflater, parent, false)
        return ItemHolder(binding)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = itemList[position]

        if (holder is ItemHolder) {
            holder.bind(item)
        }
    }

}