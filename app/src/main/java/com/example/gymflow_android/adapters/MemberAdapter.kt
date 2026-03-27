package com.example.gymflow_android.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gymflow_android.R
import com.example.gymflow_android.instrukturView.PresensiMember
import com.example.gymflow_android.models.BookingKelas
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MemberAdapter(
    private var memberList: List<BookingKelas>,
    private val context: Context
) : RecyclerView.Adapter<MemberAdapter.ViewHolder>() {

    fun setMemberList(list: Array<BookingKelas>) {
        memberList = list.toList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_member, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = memberList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = memberList[position]
        holder.tvMember.text     = item.nama_member ?: "—"
        holder.tvInstruktur.text = item.nama_instruktur ?: "—"
        holder.tvKelas.text      = item.nama_kelas ?: "—"
        holder.tvJenis.text      = item.jenis ?: "—"
        holder.tvStatus.text     = if (item.status == 1) "Hadir" else "Tidak Hadir"

        holder.btnStatus.setOnClickListener {
            MaterialAlertDialogBuilder(context)
                .setTitle("Konfirmasi")
                .setMessage("Yakin ingin mengupdate status booking ini?")
                .setNegativeButton("Batal", null)
                .setPositiveButton("Update") { _, _ ->
                    val id = item.id_booking_kelas ?: return@setPositiveButton
                    if (context is PresensiMember) context.updateStatus(id)
                }
                .show()
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvMember: TextView     = itemView.findViewById(R.id.tv_nama_member)
        val tvInstruktur: TextView = itemView.findViewById(R.id.tv_nama_instruktur)
        val tvKelas: TextView      = itemView.findViewById(R.id.tv_nama_kelas)
        val tvJenis: TextView      = itemView.findViewById(R.id.tv_jenis)
        val tvStatus: TextView     = itemView.findViewById(R.id.tv_status)
        val btnStatus: ImageButton = itemView.findViewById(R.id.btn_status)
    }
}