package com.amwa.koky.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.amwa.koky.page.detail.direction.StepFragment
import com.amwa.koky.utils.Constants

class StepAdapter(fragment: Fragment, private val steps: List<String>) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount() = steps.size

    override fun createFragment(position: Int): Fragment {
        return StepFragment().apply {
            val bundle = Bundle()
            bundle.putString(Constants.ARG_STEP, steps[position])
            arguments = bundle
        }
    }
}