package com.example.aviationlovers

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.aviationlovers.DataFile

class MyAdapter(private val context: Context, private var dataList: List<DataFile>) : RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(context).load(dataList[position].dataImage)
            .into(holder.recImage)
        holder.recRoute.text = dataList[position].dataRoute
        holder.recCompany.text = dataList[position].dataCompany
        holder.recMiles.text = dataList[position].dataMiles

//        holder.recCard.setOnClickListener {
//            val intent = Intent(context, DetailActivity::class.java)
//            intent.putExtra("Image", dataList[holder.adapterPosition].dataImage)
//            intent.putExtra("Description", dataList[holder.adapterPosition].dataRoute)
//            intent.putExtra("Title", dataList[holder.adapterPosition].dataCompany)
//            intent.putExtra("Priority", dataList[holder.adapterPosition].dataMiles)
//            context.startActivity(intent)
//        }

    }

    override fun getItemCount(): Int {
        return dataList.size
    }
    fun searchDataList(searchList: List<DataFile>) {
        dataList = searchList
        notifyDataSetChanged()
    }
}

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var recImage: ImageView
    var recRoute: TextView
    var recCompany: TextView
    var recMiles: TextView
    var recCard: CardView

    init {
        recImage = itemView.findViewById(R.id.recImage)
        recRoute = itemView.findViewById(R.id.recRoute)
        recCompany = itemView.findViewById(R.id.recCompany)
        recMiles = itemView.findViewById(R.id.recMiles)
        recCard = itemView.findViewById(R.id.recCard)
    }
}