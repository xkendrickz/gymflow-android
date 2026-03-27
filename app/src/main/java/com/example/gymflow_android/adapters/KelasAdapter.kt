package com.example.gymflow_android.adapters
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.gymflow_android.R
import com.example.gymflow_android.models.Kelas
import com.example.gymflow_android.pegawaiView.KelasActivity
import com.example.gymflow_android.pegawaiView.PresensiInstrukturActivity

class KelasAdapter(private var list: List<Kelas>, private val context: Context) :
    RecyclerView.Adapter<KelasAdapter.ViewHolder>() {

    fun setKelasList(data: Array<Kelas>) { list = data.toList(); notifyDataSetChanged() }
    override fun getItemCount() = list.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_kelas, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.tvKelas.text      = item.nama_kelas ?: "—"
        holder.tvInstruktur.text = item.nama_instruktur ?: "—"

        holder.cvKelas.setOnClickListener {
            val intent = Intent(context, PresensiInstrukturActivity::class.java)
            intent.putExtra("id", Bundle().apply {
                putString("nama_instruktur", item.nama_instruktur)
                putString("nama_kelas", item.nama_kelas)
                putInt("id_jadwal_harian", item.id_jadwal_harian)
            })
            if (context is KelasActivity)
                context.startActivityForResult(intent, KelasActivity.LAUNCH_ADD_ACTIVITY)
        }
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val tvKelas: TextView      = v.findViewById(R.id.tv_nama_kelas)
        val tvInstruktur: TextView = v.findViewById(R.id.tv_nama_instruktur)
        val cvKelas: CardView      = v.findViewById(R.id.cv_kelas)
    }
}