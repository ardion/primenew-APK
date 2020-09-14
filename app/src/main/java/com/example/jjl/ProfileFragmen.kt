package com.example.jjl

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.jjl.databinding.FragmenProfileBinding

class ProfileFragmen : Fragment() {

    lateinit var binding: FragmenProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= FragmenProfileBinding.inflate(inflater)
        binding.github.setOnClickListener{
            val intent=Intent(activity,GithubActivity::class.java)
            startActivity(intent)
        }
        return binding.root

    }





}