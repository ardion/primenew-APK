package com.example.jjl

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.jjl.BaseActivity
import com.example.jjl.R

import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.slide_page.*
import kotlinx.coroutines.NonCancellable.start

class AppIntroScreen : BaseActivity(), View.OnClickListener {

    val mResources = intArrayOf(R.drawable.picture1, R.drawable.picture2, R.drawable.picture3)
    lateinit var adapter: SlidingPagerAdapter
    var currentTab = 0
    var tabCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutid(R.layout.slide_page)

        tabCount = mResources.size
        adapter = SlidingPagerAdapter(supportFragmentManager, mResources)
        viewPager.adapter = adapter


        val pageTransformer = ParallaxTransformer()
        viewPager.setPageTransformer(true, pageTransformer)


        tabLayout.setupWithViewPager(viewPager)
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager.currentItem = tab!!.position
            }

        })
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                currentTab = position + 1
                if (currentTab == tabCount) {
                    skip.text = getString(R.string.start)
                } else {
                    skip.text = getString(R.string.skip)
                }
            }

            override fun onPageSelected(position: Int) {
            }

        })
        next.setOnClickListener(this)
        skip.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.next -> {
                if (currentTab == tabCount) {
                    skip.text = getString(R.string.start)
                } else {
                    skip.text = getString(R.string.skip)
                    viewPager.currentItem = currentTab
                }
            }

            R.id.skip -> {
                val intentlogin = Intent(this, login::class.java)
                startActivity(intentlogin)
                // Proceed to Main/Home Activity of the App
            }
        }
    }
}

class SlidingPagerAdapter(fragmentManager: FragmentManager?, val mResources: IntArray) : FragmentPagerAdapter(
    fragmentManager!!
) {

    override fun getItem(position: Int): Fragment {
        return IntroPage().newInstance(position)
    }

    override fun getCount(): Int {
        return mResources.size
    }
}