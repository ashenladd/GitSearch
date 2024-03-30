package com.example.gitsearch.feature.search

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Display
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.hardware.display.DisplayManagerCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.gitsearch.databinding.ActivitySearchBinding
import com.example.gitsearch.feature.detail.DetailActivity
import com.example.gitsearch.feature.favorite.FavoriteActivity
import com.example.gitsearch.feature.search.adapter.SearchAdapter
import com.example.gitsearch.util.ViewModelFactory

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding

    private val factory by lazy { ViewModelFactory.getInstance(this) }

    private val searchAdapter: SearchAdapter by lazy { SearchAdapter() }

    private val searchViewModel: SearchViewModel by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )
            insets
        }


        setupSearchBar()
        setupAdapter()
        setupObserver()
        setClickListener()
    }

    private fun setClickListener() {
        binding.apply {
            btnFavorite.setOnClickListener {
                navigateToFavorite()
            }
        }
    }


    private fun setupAdapter() {
        val defaultDisplay =
            DisplayManagerCompat.getInstance(this).getDisplay(Display.DEFAULT_DISPLAY)
        val displayContext = createDisplayContext(defaultDisplay!!)

        val screenWidth = displayContext.resources.displayMetrics.widthPixels
        val screenDensity = resources.displayMetrics.density
        val itemWidth = (152 * screenDensity).toInt()

        binding.apply {
            rvHome.layoutManager = GridLayoutManager(
                root.context,
                Integer.max(
                    2,
                    screenWidth / itemWidth
                )
            )
            rvHome.adapter = searchAdapter

            searchAdapter.setOnItemClickListener(object : SearchAdapter.OnItemClickListener {
                override fun onItemClick(id: String) {
                    navigateToDetail(id)
                }
            })
        }
    }

    private fun setupObserver() {
        searchViewModel.listSearch.observe(this) { user ->
            searchAdapter.submitList(user)
        }

        searchViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        searchViewModel.isEmpty.observe(this) {
            showEmpty(it)
        }
        searchViewModel.getThemeSetting().observe(this) {
            setupToggle(it)
        }
    }

    private fun setupToggle(isToggled : Boolean) {
        binding.apply {
            if (isToggled) {
               AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                lavToggle.speed = 0.75f
                lavToggle.playAnimation()
                lavToggle.setOnClickListener {
                    searchViewModel.processEvent(SearchEvent.ToggleMode(false))
                }
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                lavToggle.speed = -0.75f
                lavToggle.playAnimation()
                lavToggle.setOnClickListener {
                    searchViewModel.processEvent(SearchEvent.ToggleMode(true))
                }
            }
        }
    }

    private fun navigateToFavorite() {
        Intent(
            this,
            FavoriteActivity::class.java
        ).apply {
            startActivity(this)
        }
    }
    private fun showLoading(isLoading: Boolean) {
        binding.cpiHome.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showEmpty(isEmpty: Boolean) {
        binding.lytEmpty.clEmpty.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }

    private fun navigateToDetail(id: String) {
        Intent(
            this,
            DetailActivity::class.java
        ).apply {
            putExtra(
                DetailActivity.EXTRA_DETAIL,
                id
            )
            startActivity(this)
        }
    }

    private fun setupSearchBar() {
        with(binding) {
            svHome.setupWithSearchBar(sbHome)

            svHome
                .editText
                .setOnEditorActionListener { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        sbHome.textView.text = svHome.text
                        svHome.hide()
                        searchViewModel.processEvent(
                            SearchEvent.SearchUser(
                                svHome.text.toString(),
                                this@SearchActivity
                            )
                        )
                        true
                    } else {
                        false
                    }

                }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        setupAdapter()
    }
}