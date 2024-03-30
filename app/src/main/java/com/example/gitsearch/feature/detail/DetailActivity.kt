package com.example.gitsearch.feature.detail

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.gitsearch.R
import com.example.gitsearch.data.local.entity.FavoriteUser
import com.example.gitsearch.databinding.ActivityDetailBinding
import com.example.gitsearch.feature.detail.adapter.DetailPagerAdapter
import com.example.gitsearch.feature.detail.model.DetailModel
import com.example.gitsearch.util.ViewModelFactory
import com.example.gitsearch.util.parseFormatDate
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_DETAIL = "extra_login"
        private val TAB_TITLES = intArrayOf(
            R.string.label_follower,
            R.string.label_following
        )
    }

    private lateinit var binding: ActivityDetailBinding

    private val factory by lazy { ViewModelFactory.getInstance(this) }

    private val detailId by lazy { intent.getStringExtra(EXTRA_DETAIL) }

    private val detailViewModel: DetailViewModel by viewModels() {
        factory
    }

    private val detailPagerAdapter by lazy {
        DetailPagerAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
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

        setupData()
        setupObserver()
        setupPager()
        setupToolbar()
    }

    private fun setupData() {
        detailViewModel.processEvent(
            DetailEvent.GetDetailUser(
                detailId.orEmpty(),
                this
            )
        )
    }

    private fun setupToolbar() {
        binding.apply {
            toolbar.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
            toolbar.setOnMenuItemClickListener { menu ->
                when (menu.itemId) {
                    R.id.favorite -> {
                        detailViewModel.processEvent(
                            DetailEvent.AddOrDeleteFavoriteUser(
                                FavoriteUser(
                                    username = detailId.orEmpty(),
                                    avatarUrl = detailViewModel.detailData.value?.avatarUrl.orEmpty()
                                )
                            )
                        )
                        true
                    }

                    else -> false
                }
            }
        }
    }

    private fun setupPager() {
        binding.apply {
            detailPagerAdapter.username = detailId.orEmpty()
            vpDetail.adapter = detailPagerAdapter

            val tabs: TabLayout = tlDetail
            TabLayoutMediator(
                tabs,
                vpDetail
            ) { tab, position ->
                tab.text = getString(TAB_TITLES[position])
            }.attach()
            supportActionBar?.elevation = 0f
        }
    }

    private fun setupObserver() {
        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        detailViewModel.detailData.observe(this) {
            setDetailData(it)
            detailViewModel.processEvent(
                DetailEvent.GetFavoriteUser(
                    detailViewModel.detailData.value?.username.orEmpty(),
                    this
                )
            )
        }
        detailViewModel.isFavorite.observe(this) {
            showFavorite(it)
        }
    }

    private fun setDetailData(model: DetailModel) {
        binding.apply {
            tvName.text = model.name
            tvUsername.text = model.username
            tvRepository.text = getString(
                R.string.format_repos,
                model.publicRepos.toString(),
                model.privateRepos.toString()
            )
            tvFollower.text = getString(
                R.string.format_follower,
                model.followers.toString()
            )
            tvFollowing.text = getString(
                R.string.format_following,
                model.following.toString()
            )
            tvDate.text = getString(
                R.string.format_joined,
                model.createdAt.parseFormatDate()
            )
            Glide.with(root.context)
                .load(model.avatarUrl)
                .into(ivProfileUser)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.cpiDetail.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showFavorite(isFavorite: Boolean) {
        if (isFavorite) {
            binding.toolbar.menu.findItem(R.id.favorite).setIcon(R.drawable.ic_favorite_active_bg)
        } else {
            binding.toolbar.menu.findItem(R.id.favorite).setIcon(R.drawable.ic_favorite_unactive_bg)
        }
    }

}