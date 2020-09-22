package com.masudinn.todo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.masudinn.todo.R
import com.masudinn.todo.model.Todo
import kotlinx.android.synthetic.main.item.view.*
import java.util.ArrayList
import javax.security.auth.callback.Callback

class RecylcerAdapter(val callback: Callback) : RecyclerView.Adapter<RecylcerAdapter.ViewHolder>() {
    private var dataList:List<Todo> = ArrayList()

    class ViewHolder(itemView: View,val callback: Callback) : RecyclerView.ViewHolder(itemView) {
        fun bindData(data :Todo){
            itemView.judul.text = data.title
            itemView.desc.text = data.desc
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false)
        return ViewHolder(view,callback)
    }

    override fun getItemCount(): Int {
       return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.bindData(dataList[position])
    }
}