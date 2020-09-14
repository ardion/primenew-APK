package com.example.jjl

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.jjl.R

import kotlinx.android.synthetic.main.intro_layout.*

class IntroPage : Fragment() {

    var position = 0
    val mResources = intArrayOf(R.drawable.picture1, R.drawable.picture2, R.drawable.picture3)
    val mTitle= arrayOf("Job Vacancy","Fast","Easy")
    val mDescription= arrayOf("you can find many job listings at various top companies",
        "companies can see you quickly","you can easily apply for jobs")

    fun newInstance(position: Int): IntroPage {
        val fragment = IntroPage()
        val arguments = Bundle()
        arguments.putInt("POSITION", position)
        fragment.setArguments(arguments)
        return fragment
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater!!.inflate(R.layout.intro_layout, container, false);
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val args = arguments
        position = args?.getInt("POSITION")!!


        introImage.setImageDrawable(resources.getDrawable(mResources[position]))
        title.text=mTitle[position]
        description.text=mDescription[position]


    }
}
