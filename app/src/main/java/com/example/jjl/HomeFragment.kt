package com.example.jjl

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jjl.databinding.FragmenHomeBinding


class HomeFragment : Fragment() {

lateinit var binding:FragmenHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

       binding=FragmenHomeBinding.inflate(inflater)
        return binding.root
    }


}