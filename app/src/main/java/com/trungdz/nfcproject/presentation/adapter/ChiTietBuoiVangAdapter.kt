package com.trungdz.nfcproject.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.trungdz.nfcproject.data.model.dto.ChiTietBuoiVang
import com.trungdz.nfcproject.databinding.ItemChitietBuoivangSinhvienBinding
import com.trungdz.nfcproject.databinding.ItemThongtinSinhvienDangkyBinding
import java.sql.Date
import java.text.SimpleDateFormat

class ChiTietBuoiVangAdapter(val itemList: List<ChiTietBuoiVang>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    inner class ItemViewHolder(val binding: ItemChitietBuoivangSinhvienBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ChiTietBuoiVang) {
            val df = SimpleDateFormat("dd/MM/yyyy")
            binding.txtNgayHoc.text = "Ngày học: ${df.format(Date.valueOf(item.ngayHoc))}"
            binding.txtTietHoc.text = "Tiết học: ${item.tietHoc}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemChitietBuoivangSinhvienBinding.inflate(layoutInflater, parent, false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = itemList[position]
        if (holder is ItemViewHolder) {
            holder.bind(item)
        }
    }
}