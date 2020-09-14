package com.example.jjl

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.jjl.databinding.FragmenHomeBinding
import com.example.jjl.databinding.FragmenNewsBinding

class NewsFragment: Fragment() {

    lateinit var binding: FragmenNewsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding=FragmenNewsBinding.inflate(inflater)
        return binding.root
    }


}