package com.example.digitalisi.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.digitalisi.ui.contract.Contract
import com.example.digitalisi.R
import com.example.digitalisi.model.Process

class ProcessesRecyclerAdapter: RecyclerView.Adapter<ProcessesRecyclerAdapter.ViewHolder>() {
    private var processes = emptyList<Process>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout_process,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return processes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemTitle.text=processes[position].displayName
        holder.itemDetail.text=processes[position].description
        holder.itemId.text=processes[position].id
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var itemImage: ImageView
        var itemTitle: TextView
        var itemDetail: TextView
        var itemId: TextView
        init {
            itemImage=itemView.findViewById(R.id.item_image)
            itemTitle=itemView.findViewById(R.id.item_title)
            itemDetail=itemView.findViewById(R.id.item_detail)
            itemId=itemView.findViewById(R.id.item_id)

            itemView.setOnClickListener { v : View ->
                //val position : Int = bindingAdapterPosition
                val intent = Intent(itemView.context, Contract::class.java)
                intent.putExtra("processId",itemId.text.toString())
                itemView.context.startActivity(intent)
                //Toast.makeText(itemView.context, "Process "+itemId.text.toString()+" Clicked",Toast.LENGTH_SHORT).show()
            }

        }
    }

    fun setData(newList: List<Process>){
        processes =newList
        notifyDataSetChanged()
    }
}