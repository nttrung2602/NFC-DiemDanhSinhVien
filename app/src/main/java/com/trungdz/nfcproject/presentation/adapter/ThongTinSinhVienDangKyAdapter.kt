package com.trungdz.nfcproject.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.trungdz.nfcproject.data.model.dto.ThongTinSinhVienDangKyLTC
import com.trungdz.nfcproject.databinding.ItemThongtinSinhvienDangkyBinding

class ThongTinSinhVienDangKyAdapter(
    var itemList: List<ThongTinSinhVienDangKyLTC>,
    val listener: Listener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemThongtinSinhvienDangkyBinding.inflate(layoutInflater, parent, false)

        return ItemHolder(binding)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item=itemList[position]

        if(holder is ItemHolder){
            holder.bind(item)
        }
    }

    fun updateStatusBtnUpdate(status:String?,position: Int){
        if(status == "HUY"){
            itemList[position].thediemdanh=null
        }else{ // update
            itemList[position].thediemdanh="XXX"
        }
        notifyItemChanged(position)
    }

    inner class ItemHolder(val binding: ItemThongtinSinhvienDangkyBinding) :
        ViewHolder(binding.root) {
        fun bind(item: ThongTinSinhVienDangKyLTC) {
            // binding data here
            binding.txtHoTen.text="Họ tên: ${item.hoten}"
            binding.txtMaSV.text="MSSV: ${item.masv}"
            binding.txtMaLop.text="Mã lớp: ${item.malop}"
            if(item.thediemdanh !=null){
                binding.btnCapNhat.text="Cập nhật"
            }else{
                binding.btnCapNhat.text="Thêm"
            }

            binding.btnCapNhat.setOnClickListener {
                listener.onClickUpdate(itemList[adapterPosition],adapterPosition)
            }

            binding.btnHuy.setOnClickListener {
                listener.onClickCancel(itemList[adapterPosition],adapterPosition)
            }
        }
    }

    interface Listener {
        fun onClickUpdate(item: ThongTinSinhVienDangKyLTC, position: Int)
        fun onClickCancel(item: ThongTinSinhVienDangKyLTC, position: Int)
    }
}