package com.example.jjl.project

import android.util.Log
import com.example.jjl.databinding.ItemProjectBinding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.jjl.R
import com.example.jjl.databinding.ItemWorkerBinding
import com.squareup.picasso.Picasso

class projectAdabter() : RecyclerView.Adapter<projectAdabter.projectHolder>() {

    private var items = mutableListOf<projectModel>()


    fun addList(list: List<projectModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):projectHolder {
        return projectHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_project, parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: projectHolder, position: Int) {


        val item = items[position]

        holder.binding.tvNameproject.text = item.name_project
        holder.binding.tvDescriptionproject.text = item.description_project

//        holder.binding.containerproject.setOnClickListener {
//            listener.OnClick(item.id_project)
//        }
    }

    class projectHolder( val binding:ItemProjectBinding ) : RecyclerView.ViewHolder(binding.root)

//    interface OnClickViewListener{
//        fun OnClick(id:String)
//    }
}

