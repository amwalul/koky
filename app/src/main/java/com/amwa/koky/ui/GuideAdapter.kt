package com.amwa.koky.ui

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class GuideAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val fragmentsItem: ArrayList<Fragment> = ArrayList()

    fun submitList(items: List<Fragment>) {
        fragmentsItem.apply {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }

    override fun getItemCount() = fragmentsItem.size

    override fun createFragment(position: Int) = fragmentsItem[position]
}