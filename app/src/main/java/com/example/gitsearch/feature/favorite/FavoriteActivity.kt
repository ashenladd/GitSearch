package com.example.gitsearch.feature.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitsearch.R
import com.example.gitsearch.databinding.ActivityFavoriteBinding
import com.example.gitsearch.feature.detail.DetailActivity
import com.example.gitsearch.feature.favorite.adapter.FavoriteAdapter
import com.example.gitsearch.util.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding

    private val factory by lazy { ViewModelFactory.getInstance(this) }

    private val favoriteViewModel: FavoriteViewModel by viewModels() {
        factory
    }
    private val favoriteAdapter: FavoriteAdapter by lazy {
        FavoriteAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left,
                0,
                systemBars.right,
                systemBars.bottom
            )
            insets
        }

        setupToolbar()
        setupObserver()
        setupRecyclerView()
    }


    private fun setupToolbar() {
        binding.apply {
            appbarFavorite.toolbar.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
            appbarFavorite.toolbar.title = getString(R.string.label_favorite_users)
        }
    }

    private fun setupObserver() {
        favoriteViewModel.getFavorite().observe(this) {
            showEmptyFavorite(it.isEmpty())
            favoriteAdapter.submitList(it)
        }
    }

    private fun showEmptyFavorite(isEmpty: Boolean) {
        binding.lytEmpty.clEmpty.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }

    private fun setupRecyclerView() {
        binding.apply {
            rvFavorite.layoutManager = LinearLayoutManager(
                root.context,
                LinearLayoutManager.VERTICAL,
                false
            )
            favoriteAdapter.setOnItemClickListener(object : FavoriteAdapter.OnItemClickListener {
                override fun onItemClick(username: String) {
                    navigateToDetail(username)
                }
            })
            rvFavorite.adapter = favoriteAdapter
        }
    }

    private fun navigateToDetail(username: String) {
        val intent = Intent(
            this,
            DetailActivity::class.java
        )
        intent.putExtra(
            DetailActivity.EXTRA_DETAIL,
            username
        )
        startActivity(intent)
    }
}