package com.example.jjl.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.jjl.R
import com.example.jjl.databinding.ItemWorkerBinding
import com.squareup.picasso.Picasso

class homeaAdabter(val items: ArrayList<workerModel>
                   , val listener: OnClickViewListener
) : RecyclerView.Adapter<homeaAdabter.homeHolder>() {

//    private var items = mutableListOf<workerModel>()

    fun addList(list: List<workerModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):homeHolder {
        return homeHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_worker, parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: homeHolder, position: Int) {


        val item = items[position]
        Picasso.get().load("http://35.172.182.122:8080/uploads/"+item.image).into(holder.binding.image)
        holder.binding.tvName.text = item.name
        holder.binding.tvSkill.text = item.skill
        holder.binding.tvDomicile.text = item.domicile
        holder.binding.containerworker.setOnClickListener {
        listener.OnClick(item.id_worker)
        }
    }

    class homeHolder( val binding:ItemWorkerBinding ) : RecyclerView.ViewHolder(binding.root)

//fun updatelist(list: MutableList<workerModel>){
//    items = list
//    notifyDataSetChanged()
//}
//
    interface OnClickViewListener{
        fun OnClick(id:String)
    }
}

//private val items = mutableListOf<ProjectsModel>()
//
//fun addList(list: List<ProjectsModel>) {
//    items.clear()
//    items.addAll(list)
//    notifyDataSetChanged()
//}
//
//override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectsHolder {
//    return ProjectsHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_project, parent, false))
//}
//
//override fun getItemCount(): Int = items.size
//
//override fun onBindViewHolder(holder: ProjectsHolder, position: Int) {
//    val item = items[position]
//    holder.binding.tvDuration.text = item.duration
//    holder.binding.tvName.text = item.name
//    holder.binding.tvDesc.text = item.description
//}
//
//class ProjectsHolder( val binding: ItemProjectBinding) : RecyclerView.ViewHolder(binding.root)
//}