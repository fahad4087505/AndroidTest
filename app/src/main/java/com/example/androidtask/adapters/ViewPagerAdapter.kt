package com.example.androidtask.adapters
import android.content.Context;
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.androidtask.fragments.ContactsFragment
import com.example.androidtask.fragments.UserListFragment

class ViewPagerAdapter(private val myContext: Context, fm: FragmentManager, internal var totalTabs: Int) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                return UserListFragment()
            }
            1 -> {
                return ContactsFragment()
            }
            else -> return UserListFragment()
        }
    }

    override fun getCount(): Int {
        return totalTabs
    }
}