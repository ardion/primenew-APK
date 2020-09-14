package com.example.jjl

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jjl.databinding.FragmentOffersBinding
import com.example.jjl.databinding.FragmentProjectBinding


class ProjectFragment : Fragment() {

    lateinit var binding: FragmentProjectBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= FragmentProjectBinding.inflate(inflater)

        binding.recyclerView.adapter=SimpleRecyclerViewAdapter()
        binding.recyclerView.layoutManager= LinearLayoutManager(activity, RecyclerView.VERTICAL,false)
        return binding.root
    }


}