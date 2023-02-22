package com.example.dermoapp.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dermoapp.databinding.HomeConsultItemBinding
import com.example.dermoapp.models.Consult
import com.example.dermoapp.utils.DateUtil
import com.example.dermoapp.utils.StatusUtil
import com.example.dermoapp.views.ConsultDetailActivity

class HomeConsultsAdapter(var consults: List<Consult>) :
    RecyclerView.Adapter<HomeConsultsAdapter.HomeConsultsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeConsultsViewHolder {
        val binding = HomeConsultItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeConsultsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeConsultsViewHolder, position: Int) {
        with(holder) {
            with(consults[position]) {
                binding.consultCity.text = this.city
                binding.consultDate.text = DateUtil.dateToString(
                    DateUtil.stringToDate(this.createdAt)!!
                )
                binding.consultStatus.text = StatusUtil.statusToString(
                    holder.itemView.context, this.status
                )
                binding.consultStatus.setTextColor(
                    StatusUtil.statusToColor(holder.itemView.context, this.status)
                )

                holder.itemView.setOnClickListener {
                    val consultAsString: String = this.asGsonString()
                    val intent = Intent(holder.itemView.context, ConsultDetailActivity::class.java)
                    intent.putExtra(ConsultDetailActivity.CONSULT_EXTRA, consultAsString)
                    holder.itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return consults.size
    }

    inner class HomeConsultsViewHolder(val binding: HomeConsultItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}