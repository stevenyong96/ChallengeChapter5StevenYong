package com.example.challengechapter5stevenyong

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.challengechapter5stevenyong.fragment.LandingPage1Fragment
import com.example.challengechapter5stevenyong.fragment.LandingPage2Fragment
import com.example.challengechapter5stevenyong.fragment.LandingPage3Fragment
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator

class LandingActivity: AppCompatActivity() , LandingPage3Fragment.UserNameInputListener {

    lateinit var  viewPager : ViewPager
    lateinit var dotIndicator : DotsIndicator
    lateinit var imgNext : ImageView

    var namaUser = ""

    override  fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        viewPager = findViewById(R.id.view_pager)
        dotIndicator = findViewById(R.id.dots_indicator)
        imgNext = findViewById(R.id.img_next)
        viewPager.adapter = SimpleViewPagerAdapter(supportFragmentManager)
        dotIndicator.setViewPager(viewPager)

        imgNext.setOnClickListener {
            val currentIndex = viewPager.currentItem
            viewPager.currentItem = currentIndex+1

            if(currentIndex == 2) {
                val intentToHome = Intent(this, HalamanMenuActivity::class.java)
                intentToHome.putExtra("DATA_USER_NAME", namaUser)
                startActivity(intentToHome)
            }

            if(currentIndex == 0) {
                listener?.onDataSend("data from activity")
            }
        }

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                if(position == 1 || position == 0|| namaUser.isNotEmpty()) imgNext.visibility = View.VISIBLE
                else imgNext.visibility = View.GONE
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })
    }

    private inner class SimpleViewPagerAdapter(fm : FragmentManager ) : FragmentStatePagerAdapter(fm) {
        override fun getCount(): Int = 3
        override fun getItem(position: Int): Fragment = when (position) {
            0 -> LandingPage1Fragment()
            1 -> LandingPage2Fragment()
            else -> LandingPage3Fragment()
        }
    }

    interface OnSendDataToFragment {
        fun onDataSend(input: String)
    }

    var listener: OnSendDataToFragment? = null


    override fun afterUserInputName(input: String) {
        if(input.isNotEmpty()) imgNext.visibility = View.VISIBLE else imgNext.visibility = View.GONE
        namaUser = input
    }
}