package com.amwa.koky.page.detail.direction

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.amwa.koky.R
import com.amwa.koky.databinding.FragmentDirectionBinding
import com.amwa.koky.ui.StepAdapter
import com.amwa.koky.ui.StepNumberAdapter
import com.amwa.koky.utils.Constants

class DirectionFragment : Fragment(R.layout.fragment_direction) {

    private var binding: FragmentDirectionBinding? = null

    private val stepNumberAdapter by lazy {
        StepNumberAdapter(stepNumberInteraction)
    }

    private val stepNumberInteraction by lazy {
        object : StepNumberAdapter.Interaction {
            override fun onItemSelected(item: Int) {
                setSelectedStep(item)
            }
        }
    }

    private val onStepPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)

            stepNumberAdapter.setSelectedStep(position + 1)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentDirectionBinding.bind(view)

        initPage()
        initStepNumberView()
        initData()
    }

    private fun setSelectedStep(stepNumber: Int) {
        binding?.vpStep?.setCurrentItem(stepNumber - 1, true)
        stepNumberAdapter.setSelectedStep(stepNumber)
    }

    private fun initPage() {
        binding?.vpStep?.apply {
            registerOnPageChangeCallback(onStepPageChangeCallback)
            (getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }
    }

    private fun initStepNumberView() = binding?.apply {
        rvStepNumber.adapter = stepNumberAdapter
        rvStepNumber.setHasFixedSize(true)
    }

    private fun initData() {
        arguments?.getStringArrayList(Constants.ARG_DIRECTION)?.let {
            binding?.vpStep?.adapter = StepAdapter(this@DirectionFragment, it)
            val stepNumberList = it.mapIndexed { index, _ -> index + 1 }
            stepNumberAdapter.submitList(stepNumberList)
        }
    }

    override fun onDestroyView() {
        binding?.apply {
            vpStep.unregisterOnPageChangeCallback(onStepPageChangeCallback)
            vpStep.adapter = null
            rvStepNumber.adapter = null
        }
        binding = null

        super.onDestroyView()
    }
}