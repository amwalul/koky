package com.amwa.koky.page.detail.ingredient

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.amwa.koky.R
import com.amwa.koky.databinding.FragmentIngredientBinding
import com.amwa.koky.ui.IngredientAdapter
import com.amwa.koky.utils.Constants

class IngredientFragment : Fragment(R.layout.fragment_ingredient) {

    private var binding: FragmentIngredientBinding? = null

    private val ingredientAdapter by lazy {
        IngredientAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentIngredientBinding.bind(view)

        initData()
    }

    private fun initData() {
        arguments?.getStringArrayList(Constants.ARG_INGREDIENT)?.let {
            binding?.root?.apply {
                adapter = ingredientAdapter
                setHasFixedSize(true)
            }

            ingredientAdapter.submitList(it.toList())
        }
    }

    override fun onDestroyView() {
        binding?.root?.adapter = null
        binding = null

        super.onDestroyView()
    }
}