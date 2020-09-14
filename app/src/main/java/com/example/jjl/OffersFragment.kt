package com.example.jjl

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jjl.databinding.FragmenHomeBinding
import com.example.jjl.databinding.FragmentOffersBinding


class OffersFragment : Fragment() {

    lateinit var binding: FragmentOffersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= FragmentOffersBinding.inflate(inflater)
        return binding.root
    }


}
