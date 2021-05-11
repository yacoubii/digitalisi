package com.example.digitalisi.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.digitalisi.ui.processes.Processes
import com.example.digitalisi.R
import com.example.digitalisi.model.Category

class RecyclerAdapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    private var categories = emptyList<Category>()
    private var title = arrayOf("Title one","Title two","Title three","Title four","Title five","Title six","Title seven","Title eignt")
    private var detail = arrayOf("Item detail one","Item detail two","Item detail three","Item detail four","Item detail five"
                                ,"Item detail six","Item detail seven","Item detail eight")
    //private var image = intArrayOf(R.drawable.bullet,R.drawable.bullet,R.drawable.bullet,R.drawable.bullet,R.drawable.bullet,R.drawable.bullet,R.drawable.bullet,R.drawable.bullet)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
       val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        holder.itemTitle.text=categories[position].displayName
        holder.itemDetail.text=categories[position].description
        holder.itemId.text=categories[position].id
        //holder.itemImage.setImageResource(image[position])
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var itemImage:ImageView
        var itemTitle:TextView
        var itemDetail:TextView
        var itemId:TextView
        init {
            itemImage=itemView.findViewById(R.id.item_image)
            itemTitle=itemView.findViewById(R.id.item_title)
            itemDetail=itemView.findViewById(R.id.item_detail)
            itemId=itemView.findViewById(R.id.item_id)

            itemView.setOnClickListener { v : View ->
                //val position : Int = bindingAdapterPosition
                val intent = Intent(itemView.context, Processes::class.java)
                intent.putExtra("categoryID","categoryId="+itemId.text.toString())
                itemView.context.startActivity(intent)
            }

        }
    }

    fun setData(newList: List<Category>){
        categories =newList
        notifyDataSetChanged()
    }

}