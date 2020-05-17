package com.example.androidtask.activities

import android.os.Bundle
import android.view.View
import com.example.androidtask.R
import com.example.androidtask.adapters.ViewPagerAdapter
import com.example.androidtask.base.BaseActivity
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.action_bar_layout.*
import kotlinx.android.synthetic.main.activity_tabs.*

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tabs)
        init()
    }

    private fun init() {
        titleTextview?.text = "Home"
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Online"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Local"))
        tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL
        val adapter = ViewPagerAdapter(this,supportFragmentManager, tabLayout!!.tabCount)
        viewPager!!.adapter = adapter
        viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager!!.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {
            }
            override fun onTabReselected(tab: TabLayout.Tab) {
            }
        })
    }
}