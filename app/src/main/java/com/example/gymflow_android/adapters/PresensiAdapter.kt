package com.example.gymflow_android.adapters
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gymflow_android.R
import com.example.gymflow_android.models.Presensi

class PresensiAdapter(private var list: List<Presensi>, private val context: Context) :
    RecyclerView.Adapter<PresensiAdapter.ViewHolder>() {

    fun setPresensiList(data: Array<Presensi>) { list = data.toList(); notifyDataSetChanged() }
    override fun getItemCount() = list.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_presensi, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.tvNama.text    = item.nama_aktivitas ?: "—"
        holder.tvTanggal.text = item.tanggal ?: "—"
        holder.tvJenis.text   = item.jenis ?: "—"
        holder.tvStatus.text  = item.status ?: "—"
        holder.tvKelas.text   = item.kelas ?: "—"
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val tvNama: TextView    = v.findViewById(R.id.tv_nama_aktivitas)
        val tvTanggal: TextView = v.findViewById(R.id.tv_tanggal)
        val tvJenis: TextView   = v.findViewById(R.id.tv_jenis)
        val tvStatus: TextView  = v.findViewById(R.id.tv_status)
        val tvKelas: TextView   = v.findViewById(R.id.tv_kelas)
    }
}