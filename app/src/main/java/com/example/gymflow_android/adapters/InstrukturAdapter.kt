package com.example.gymflow_android.adapters
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gymflow_android.R
import com.example.gymflow_android.models.Instruktur

class InstrukturAdapter(private var list: List<Instruktur>, private val context: Context) :
    RecyclerView.Adapter<InstrukturAdapter.ViewHolder>() {

    fun setInstrukturList(data: Array<Instruktur>) { list = data.toList(); notifyDataSetChanged() }
    override fun getItemCount() = list.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_instruktur, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.tvKelas.text   = item.nama_kelas ?: "—"
        holder.tvTanggal.text = item.hari ?: "—"
        holder.tvIzin.text    = item.izin ?: "—"
        holder.tvMulai.text   = item.mulai_kelas ?: "—"
        holder.tvSelesai.text = item.selesai_kelas ?: "—"
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val tvKelas: TextView   = v.findViewById(R.id.tv_nama_kelas)
        val tvTanggal: TextView = v.findViewById(R.id.tv_tanggal)
        val tvIzin: TextView    = v.findViewById(R.id.tv_detail_izin)
        val tvMulai: TextView   = v.findViewById(R.id.tv_mulai_kelas)
        val tvSelesai: TextView = v.findViewById(R.id.tv_selesai_kelas)
    }
}