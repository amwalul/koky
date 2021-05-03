package com.amwa.koky.page.detail

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.amwa.koky.R
import com.amwa.koky.data.Error
import com.amwa.koky.data.Loading
import com.amwa.koky.data.Success
import com.amwa.koky.databinding.FragmentDetailBinding
import com.amwa.koky.domain.model.Recipe
import com.amwa.koky.extension.gone
import com.amwa.koky.extension.visible
import com.amwa.koky.page.detail.direction.DirectionFragment
import com.amwa.koky.page.detail.ingredient.IngredientFragment
import com.amwa.koky.ui.GuideAdapter
import com.amwa.koky.utils.Constants
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val viewModel by viewModels<DetailViewModel>()

    private val args by navArgs<DetailFragmentArgs>()

    private var binding: FragmentDetailBinding? = null

    private lateinit var guideAdapter: GuideAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentDetailBinding.bind(view)

        initToolbar()
        initBackPress()
        initGuideSheet()
        initRefreshButton()
        initObservers()
    }

    private fun initToolbar() {
        binding?.ivBack?.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun initBackPress() {
        val onBackPress = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                initOnBackPressed()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPress)
    }

    private fun initOnBackPressed() = binding?.apply {
        val sheetGuide = BottomSheetBehavior.from(llSheetGuide)

        if (sheetGuide.state == BottomSheetBehavior.STATE_EXPANDED) {
            sheetGuide.state = BottomSheetBehavior.STATE_COLLAPSED
        } else {
            findNavController().navigateUp()
        }

    } ?: findNavController().navigateUp()

    private fun initData(data: Recipe) {
        initFavoriteButton(data)
        initPicture(data)
        initTextData(data)
        initGuide(data)
        initTabListener()
    }

    private fun initFavoriteButton(data: Recipe) {
        val imageUrl = if (!data.thumb.isNullOrBlank()) data.thumb else args.imageUrl

        binding?.cbFavorite?.apply {
            isEnabled = true
            isChecked = !data.key.isNullOrBlank()

            setOnCheckedChangeListener { _, isChecked ->
                val dataArgs = data.apply {
                    key = args.key
                    thumb = imageUrl
                }

                if (isChecked) {
                    viewModel.saveRecipe(dataArgs)
                } else {
                    viewModel.deleteRecipe(dataArgs)
                }
            }
        }
    }

    private fun initPicture(data: Recipe) = binding?.apply {
        val placeholderResource = ContextCompat.getColor(requireContext(), R.color.green)
        val placeholder = ColorDrawable(placeholderResource)
        val imageUrl = if (!data.thumb.isNullOrBlank()) data.thumb else args.imageUrl

        Glide.with(requireContext())
            .load(imageUrl)
            .placeholder(placeholder)
            .into(ivPicture)
    }

    private fun initTextData(data: Recipe) = binding?.apply {
        with(data) {
            tvName.text = title
            tvServings.text = servings
            tvDuration.text = times
            tvDifficulty.text = dificulty
            tvDescription.text = desc
        }
    }

    private fun initGuide(data: Recipe) {
        val ingredientFragment = IngredientFragment()
        ingredientFragment.arguments = Bundle().apply {
            putStringArrayList(Constants.ARG_INGREDIENT, ArrayList(data.ingredient))
        }

        val directionFragment = DirectionFragment()
        directionFragment.arguments = Bundle().apply {
            putStringArrayList(Constants.ARG_DIRECTION, ArrayList(data.step))
        }

        guideAdapter.submitList(listOf(ingredientFragment, directionFragment))
    }

    private fun initTabListener() {
        binding?.apply {
            val sheetGuide = BottomSheetBehavior.from(llSheetGuide)

            tlGuide.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    sheetGuide.state = BottomSheetBehavior.STATE_EXPANDED
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    sheetGuide.state = BottomSheetBehavior.STATE_EXPANDED
                }
            })
        }
    }

    private fun initRefreshButton() = binding?.rlRefresh?.setOnClickListener {
        viewModel.getFavoriteRecipeDetail(args.key)
    }

    private fun showRecipeProgress(show: Boolean, isError: Boolean = false) = binding?.apply {
        if (show) {
            progressRecipe.visible()
            rlRefresh.gone()
            svContent.gone()
        } else {
            progressRecipe.gone()

            if (isError) {
                rlRefresh.visible()
            } else {
                svContent.visible()
            }
        }
    }

    private fun initGuideSheet() {
        binding?.apply {
            val sheetGuide = BottomSheetBehavior.from(llSheetGuide)

            tlGuide.viewTreeObserver.addOnGlobalLayoutListener {
                sheetGuide.peekHeight = tlGuide.height
                llContent.updatePadding(bottom = tlGuide.height)
            }

            guideAdapter = GuideAdapter(this@DetailFragment)
            vpGuide.adapter = guideAdapter

            TabLayoutMediator(tlGuide, vpGuide) { tab, position ->
                tab.text = when (position) {
                    0 -> "BAHAN"
                    1 -> "LANGKAH"
                    else -> ""
                }
            }.attach()

            (vpGuide.getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }
    }

    private fun initObservers() {
        observeFavoriteRecipeDetail()
    }

    private fun observeFavoriteRecipeDetail() {
        viewModel.getFavoriteRecipeDetail(args.key)
        viewModel.favoriteRecipe.observe(viewLifecycleOwner, { recipe ->
            recipe?.let {
                initData(it)
            } ?: observeRecipeDetail()
        })
    }

    private fun observeRecipeDetail() {
        viewModel.getRecipeDetail(args.key).observe(viewLifecycleOwner, {
            val resource = it ?: return@observe

            when (resource) {
                is Success -> {
                    showRecipeProgress(false)
                    val data = resource.data!!
                    initData(data)
                }
                Loading -> {
                    showRecipeProgress(true)
                }
                is Error -> {
                    showRecipeProgress(false, isError = true)
                    Toast.makeText(requireContext(), R.string.network_error, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }

    override fun onDestroyView() {
        binding?.vpGuide?.adapter = null
        binding = null

        super.onDestroyView()
    }
}