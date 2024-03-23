package com.example.gitsearch.feature.detail.follow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitsearch.databinding.FragmentFollowBinding
import com.example.gitsearch.feature.detail.follow.adapter.FollowAdapter


class FollowFragment : Fragment() {
    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!

    private val followAdapter: FollowAdapter by lazy {
        FollowAdapter()
    }
    private val followViewModel: FollowViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentFollowBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(
            view,
            savedInstanceState
        )

        setupArgument()
        setupObserver()
        setupRecyclerView()
    }

    private fun setupArgument() {
        val position = arguments?.getInt(ARG_POSITION) ?: 0
        val username = arguments?.getString(ARG_USERNAME).orEmpty()

        if (position == 1) {
            followViewModel.processEvent(FollowEvent.GetFollowers(username))
        } else {
            followViewModel.processEvent(FollowEvent.GetFollowing(username))
        }
    }

    private fun setupObserver() {
        followViewModel.followerData.observe(viewLifecycleOwner) {
            followAdapter.submitList(it)
        }

        followViewModel.followingData.observe(viewLifecycleOwner) {
            followAdapter.submitList(it)
        }

        followViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        followViewModel.isEmptyFollower.observe(viewLifecycleOwner) {
            showEmptyFollower(it)
        }

        followViewModel.isEmptyFollowing.observe(viewLifecycleOwner) {
            showEmptyFollowing(it)
        }

    }

    private fun showEmptyFollower(isEmpty: Boolean) {
        binding.lytEmpty.clEmpty.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }

    private fun showEmptyFollowing(isEmpty: Boolean) {
        binding.lytEmpty.clEmpty.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }

    private fun showLoading(isLoading: Boolean) {
        binding.cpiFollow.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setupRecyclerView() {
        binding.apply {
            rvFollow.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            )
            rvFollow.adapter = followAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }
}