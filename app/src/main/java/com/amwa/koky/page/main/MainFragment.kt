package com.amwa.koky.page.main

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.amwa.koky.R
import com.amwa.koky.data.Error
import com.amwa.koky.data.Loading
import com.amwa.koky.data.Success
import com.amwa.koky.databinding.FragmentMainBinding
import com.amwa.koky.domain.model.Category
import com.amwa.koky.domain.model.Recipe
import com.amwa.koky.domain.model.SearchHistory
import com.amwa.koky.extension.*
import com.amwa.koky.ui.CategoryAdapter
import com.amwa.koky.ui.RecipeAdapter
import com.amwa.koky.ui.SearchHistoryAdapter
import com.amwa.koky.utils.CommonUtils
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel by activityViewModels<MainViewModel>()

    private var binding: FragmentMainBinding? = null

    private val recipeAdapter by lazy {
        RecipeAdapter(recipeInteraction)
    }

    private val favoriteAdapter by lazy {
        RecipeAdapter(recipeInteraction)
    }

    private val recipeInteraction by lazy {
        object : RecipeAdapter.Interaction {
            override fun onItemSelected(item: Recipe) {
                val directions =
                    MainFragmentDirections.actionMainFragmentToDetailFragment(
                        item.key!!,
                        item.thumb
                    )
                findNavController().navigate(directions)
            }
        }
    }

    private val categoryAdapter by lazy {
        CategoryAdapter(categoryInteraction)
    }

    private val categoryInteraction by lazy {
        object : CategoryAdapter.Interaction {
            override fun onItemSelected(item: Category) {
                setCategory(item.key)
                binding?.btnMenu?.isChecked = false
            }
        }
    }

    private val searchHistoryAdapter by lazy {
        SearchHistoryAdapter(searchHistoryInteraction)
    }

    private val searchHistoryInteraction by lazy {
        object : SearchHistoryAdapter.Interaction {
            override fun onItemSelected(item: SearchHistory) {
                binding?.svSearch?.setQuery(item.query, false)
                viewModel.deleteHistory(item)
                searchRecipe(item.query)
            }

            override fun onDeleteClicked(item: SearchHistory) {
                viewModel.deleteHistory(item)
            }
        }
    }

    private lateinit var openNavAnimation: Animation

    private lateinit var closeNavAnimation: Animation

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMainBinding.bind(view)

        initAnimations()
        initAdapters()
        initNavDrawer()
        initBackPress()
        initSearchSheet()
        initFavoriteSheet()
        initRefreshButton()
        initObservers()
    }

    private fun setCategory(key: String) {
        viewModel.setCategory(key)
        categoryAdapter.setCategory(key)
    }

    private fun setEmptyCategory() {
        categoryAdapter.setCategory("")
    }

    private fun initAnimations() {
        openNavAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in_from_left)
        closeNavAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_out_to_left)
    }

    private fun initAdapters() = binding?.apply {
        rvRecipe.adapter = recipeAdapter
        rvRecipe.setHasFixedSize(true)

        rvCategory.adapter = categoryAdapter

        rvFavorite.adapter = favoriteAdapter
        rvFavorite.setHasFixedSize(true)

        rvHistory.adapter = searchHistoryAdapter
        rvHistory.setHasFixedSize(true)
    }

    private fun initNavDrawer() = binding?.apply {
        btnMenu.setOnCheckedChangeListener { _, b ->
            if (b) startOpenNavAnimation() else startCloseNavAnimation()
        }
    }

    private fun startOpenNavAnimation() = binding?.rlCategory?.apply {
        val animation = openNavAnimation.apply {
            onAnimationStart(::visible)
        }
        startAnimation(animation)
    }

    private fun startCloseNavAnimation() = binding?.rlCategory?.apply {
        val animation = closeNavAnimation.apply {
            onAnimationEnd(::gone)
        }
        startAnimation(animation)
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
        val sheetSearch = BottomSheetBehavior.from(llSheetSearch)
        val sheetFavorite = BottomSheetBehavior.from(llSheetFavorite)

        when {
            rlCategory.isVisible() -> {
                btnMenu.isChecked = false
            }
            sheetSearch.state == BottomSheetBehavior.STATE_EXPANDED -> {
                sheetSearch.state = BottomSheetBehavior.STATE_HIDDEN
            }
            sheetFavorite.state == BottomSheetBehavior.STATE_EXPANDED -> {
                sheetFavorite.state = BottomSheetBehavior.STATE_HIDDEN
            }
            else -> {
                requireActivity().finish()
            }
        }
    } ?: requireActivity().finish()

    private fun initSearchSheet() = binding?.apply {
        val sheetSearch = BottomSheetBehavior.from(llSheetSearch)

        btnSearch.setOnClickListener {
            sheetSearch.state = BottomSheetBehavior.STATE_EXPANDED
        }

        sheetSearch.onStateChanged { newState ->
            when (newState) {
                BottomSheetBehavior.STATE_HIDDEN -> {
                    svSearch.clearFocus()
                }
                BottomSheetBehavior.STATE_EXPANDED -> {
                    svSearch.requestFocus()
                    CommonUtils.showKeyboard(svSearch)
                }
            }
        }

        llSheetSearch.setOnClickListener {
            svSearch.clearFocus()
        }

        svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchRecipe(query ?: "")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        tvDeleteHistories.setOnClickListener {
            showDeleteAllHistoriesAlert()
        }
    }

    private fun searchRecipe(query: String) = binding?.run {
        viewModel.searchRecipe(query)
        viewModel.addHistory(SearchHistory(query))

        val sheetSearch = BottomSheetBehavior.from(llSheetSearch)
        sheetSearch.state = BottomSheetBehavior.STATE_HIDDEN

        setEmptyCategory()
    }

    private fun showDeleteAllHistoriesAlert() {
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Hapus")
            .setMessage("Hapus semua riwayat pencarian?")
            .setPositiveButton("Ya") { dialog, _ ->
                viewModel.deleteAllHistories()
                dialog.dismiss()
            }
            .setNegativeButton("Tidak") { dialog, _ ->
                dialog.cancel()
            }
            .create()

        dialog.setOnShowListener {
            val greenColor = ContextCompat.getColor(requireContext(), R.color.green)

            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(greenColor)
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(greenColor)
        }

        dialog.show()
    }

    private fun initFavoriteSheet() = binding?.apply {
        val sheetFavorite = BottomSheetBehavior.from(llSheetFavorite)

        ivFavoriteBack.setOnClickListener {
            sheetFavorite.state = BottomSheetBehavior.STATE_HIDDEN
        }

        btnFavorite.setOnClickListener {
            sheetFavorite.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    private fun initRefreshButton() = binding?.apply {
        val onRefreshClick = View.OnClickListener {
            viewModel.refreshRecipeList()
            viewModel.refreshCategoryList()
        }
        rlRecipeRefresh.setOnClickListener(onRefreshClick)
        rlCategoryRefresh.setOnClickListener(onRefreshClick)
    }

    private fun showRecipeProgress(
        show: Boolean,
        isEmpty: Boolean = false,
        isError: Boolean = false
    ) = binding?.apply {
        if (show) {
            progressRecipe.visible()
            rlRecipeRefresh.gone()
            rvRecipe.gone()
            tvRecipeEmpty.gone()
        } else {
            progressRecipe.gone()

            if (isError) {
                rlRecipeRefresh.visible()
            } else {
                if (isEmpty) {
                    tvRecipeEmpty.visible()
                } else {
                    rvRecipe.visible()
                }
            }
        }
    }

    private fun showCategoryProgress(show: Boolean, isError: Boolean = false) = binding?.apply {
        if (show) {
            progressCategory.visible()
            rlCategoryRefresh.gone()
            llCategory.gone()
        } else {
            progressCategory.gone()

            if (isError) {
                rlCategoryRefresh.visible()
            } else {
                llCategory.visible()
            }
        }
    }

    private fun initFavoriteData(favoriteList: List<Recipe>) {
        favoriteAdapter.submitList(favoriteList)

        binding?.apply {
            if (favoriteList.isEmpty()) {
                rvFavorite.gone()
                tvFavoriteEmpty.visible()
            } else {
                rvFavorite.visible()
                tvFavoriteEmpty.gone()
            }
        }
    }

    private fun initObservers() {
        viewModel.recipeList.observe(viewLifecycleOwner, {
            val resource = it ?: return@observe

            when (resource) {
                is Success -> {
                    val data = resource.data!!
                    showRecipeProgress(false, isEmpty = data.isEmpty())
                    recipeAdapter.submitList(data)
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

        viewModel.categoryList.observe(viewLifecycleOwner, {
            val resource = it ?: return@observe

            when (resource) {
                is Success -> {
                    showCategoryProgress(false)
                    categoryAdapter.submitList(resource.data!!)
                    viewModel.removeCategoryListSource()
                }
                Loading -> {
                    showCategoryProgress(true)
                }
                is Error -> {
                    showCategoryProgress(false, isError = true)
                    Toast.makeText(requireContext(), R.string.network_error, Toast.LENGTH_SHORT)
                        .show()
                    viewModel.removeCategoryListSource()
                }
            }
        })

        viewModel.favoriteList.observe(viewLifecycleOwner, {
            initFavoriteData(it.reversed())
        })

        viewModel.historyList.observe(viewLifecycleOwner, {
            searchHistoryAdapter.submitList(it.reversed())
        })
    }

    override fun onDestroyView() {
        binding?.apply {
            rvRecipe.adapter = null
            rvCategory.adapter = null
            rvFavorite.adapter = null
            rvHistory.adapter = null
        }
        binding = null

        super.onDestroyView()
    }
}