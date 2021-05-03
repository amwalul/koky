package com.amwa.koky.page.detail.direction

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.amwa.koky.R
import com.amwa.koky.databinding.FragmentStepBinding
import com.amwa.koky.utils.Constants

class StepFragment : Fragment(R.layout.fragment_step) {

    private var binding: FragmentStepBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentStepBinding.bind(view)

        val step = arguments?.getString(Constants.ARG_STEP)
        binding?.root?.text = step?.slice(2..step.lastIndex)
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}