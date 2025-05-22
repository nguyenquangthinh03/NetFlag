package project.dheeraj.netflag.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import project.dheeraj.netflag.R
import project.dheeraj.netflag.data.model.Movie
import project.dheeraj.netflag.data.model.Status
import project.dheeraj.netflag.databinding.FragmentHomeBinding
import project.dheeraj.netflag.ui.adapter.HomeRecyclerViewAdapter
import project.dheeraj.netflag.ui.adapter.HomeViewPagerAdapter
import project.dheeraj.netflag.ui.auth.AuthActivity
import project.dheeraj.netflag.utils.CONSTANTS
import project.dheeraj.netflag.utils.showToast
import java.lang.Math.abs

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class HomeFragment : Fragment(), View.OnClickListener {

    private lateinit var navController: NavController

    private val viewModel : HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding

    private var upcomingMovieList : ArrayList<Movie> = ArrayList()
    private var popularMovieList : ArrayList<Movie> = ArrayList()
    private var topRatedMovieList : ArrayList<Movie> = ArrayList()

    private lateinit var upcomingAdapter : HomeRecyclerViewAdapter
    private lateinit var popularAdapter : HomeRecyclerViewAdapter
    private lateinit var topRatedAdapter : HomeRecyclerViewAdapter

    private lateinit var viewPagerSkeleton : Skeleton
    private lateinit var upcomingSkeleton : Skeleton
    private lateinit var topRatedSkeleton : Skeleton
    private lateinit var popularSkeleton : Skeleton

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        binding = FragmentHomeBinding.bind(view)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(binding.root)
        binding.homeViewPager.setPageTransformer { page, position ->
            page.translationX = -10 * position
            page.scaleY = 1 - (0.25f * abs(position))
        }

        binding.homeSearchButton.setOnClickListener(this)
        binding.bookmarks.setOnClickListener(this)
        binding.textViewAllPopular.setOnClickListener(this)
        binding.textViewAllTopRated.setOnClickListener(this)
        binding.textViewAllUpcoming.setOnClickListener(this)
        binding.logout.setOnClickListener(this)
        binding.chatbot.setOnClickListener(this)

        initAdapters()

        initSkeletons()

        fetchData()

    }

    private fun fetchData() {
        viewModel.loadNowPlaying().observe(requireActivity(), Observer { res ->
            when (res.status) {
                Status.LOADING -> {
                    viewPagerSkeleton.showSkeleton()
                }
                Status.SUCCESS -> {
                    viewPagerSkeleton.showOriginal()
                    binding.homeViewPager.adapter =
                        HomeViewPagerAdapter(childFragmentManager, lifecycle, res.data!!.results)
                }
                Status.ERROR -> {
                    showToast(res.msg.toString())
                }
            }
        })

        viewModel.loadUpcoming().observe(requireActivity(), Observer { res ->
            when (res.status) {
                Status.LOADING -> {
                    if (upcomingMovieList.isNullOrEmpty())
                        upcomingSkeleton.showSkeleton()
                }
                Status.SUCCESS -> {
                    upcomingSkeleton.showOriginal()
                    upcomingMovieList.clear()
                    upcomingMovieList.addAll(res.data!!.results)
                    upcomingAdapter.notifyDataSetChanged()
                }
                Status.ERROR -> {
                    showToast(res.msg.toString())
                }
            }
        })

        viewModel.loadPopular().observe(requireActivity(), Observer { res ->
            when (res.status) {
                Status.LOADING -> {
                    if (popularMovieList.isNullOrEmpty())
                        popularSkeleton.showSkeleton()
                }
                Status.SUCCESS -> {
                    popularSkeleton.showOriginal()
                    popularMovieList.clear()
                    popularMovieList.addAll(res.data!!.results)
                    popularAdapter.notifyDataSetChanged()
                }
                Status.ERROR -> {
                    showToast(res.msg.toString())
                }
            }
        })

        viewModel.loadTopRated().observe(requireActivity(), Observer { res ->
            when (res.status) {
                Status.LOADING -> {
                    if (topRatedMovieList.isNullOrEmpty())
                        topRatedSkeleton.showSkeleton()
                }
                Status.SUCCESS -> {
                    topRatedSkeleton.showOriginal()
                    topRatedMovieList.clear()
                    topRatedMovieList.addAll(res.data!!.results)
                    topRatedAdapter.notifyDataSetChanged()
                }
                Status.ERROR -> {
                    showToast(res.msg.toString())
                }
            }
        })

    }

    @SuppressLint("ResourceType")
    private fun initSkeletons()
    {
        viewPagerSkeleton = binding.homeViewPager.applySkeleton(R.layout.fragment_home_view_pager)

        upcomingSkeleton = binding.recyclerViewUpcoming.applySkeleton(
            R.layout.item_movie_home,
            itemCount = 10
        )

        popularSkeleton = binding.recyclerViewPopular.applySkeleton(
            R.layout.item_movie_home,
            itemCount = 10
        )

        topRatedSkeleton = binding.recyclerViewTopRated.applySkeleton(
            R.layout.item_movie_home,
            itemCount = 10
        )
    }


    private fun initAdapters() {
        upcomingAdapter = HomeRecyclerViewAdapter(requireContext(), upcomingMovieList)
        binding.recyclerViewUpcoming.adapter = upcomingAdapter

        popularAdapter = HomeRecyclerViewAdapter(requireContext(), popularMovieList)
        binding.recyclerViewPopular.adapter = popularAdapter

        topRatedAdapter = HomeRecyclerViewAdapter(requireContext(), topRatedMovieList)
        binding.recyclerViewTopRated.adapter = topRatedAdapter
    }


    override fun onClick(v: View?) {

        when(v!!.id) {
            R.id.home_search_button -> {
                navController.navigate(R.id.action_homeFragment_to_searchFragment2)
            }
            R.id.text_view_all_popular -> {
                val bundle = bundleOf(CONSTANTS.viewAll to CONSTANTS.Popular)
                navController.navigate(R.id.action_homeFragment_to_viewAllFragment, bundle)
            }
            R.id.text_view_all_top_rated -> {
                val bundle = bundleOf(CONSTANTS.viewAll to CONSTANTS.TopRated)
                navController.navigate(R.id.action_homeFragment_to_viewAllFragment, bundle)
            }
            R.id.text_view_all_upcoming -> {
                val bundle = bundleOf(CONSTANTS.viewAll to CONSTANTS.Upcoming)
                navController.navigate(R.id.action_homeFragment_to_viewAllFragment, bundle)
            }
            R.id.bookmarks -> {
                val bundle = bundleOf(CONSTANTS.viewAll to CONSTANTS.Bookmarks)
                navController.navigate(R.id.action_homeFragment_to_viewAllFragment, bundle)
            }
            R.id.chatbot -> {
                navController.navigate(R.id.action_homeFragment_to_chatFragment)
            }
            R.id.logout -> {
                // 1) Xoá session
                requireActivity()
                    .getSharedPreferences("USER_PREFS", Context.MODE_PRIVATE)
                    .edit()
                    .clear()
                    .apply()
                // 2) Quay về AuthActivity, clear back‑stack
                val intent = Intent(requireContext(), AuthActivity::class.java)
                    .apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }
                startActivity(intent)
            }
            R.id.home_search_button -> navController.navigate(R.id.action_homeFragment_to_searchFragment2)
            R.id.text_view_all_popular -> {
                val bundle = bundleOf(CONSTANTS.viewAll to CONSTANTS.Popular)
                navController.navigate(R.id.action_homeFragment_to_viewAllFragment, bundle)
            }

        }

    }

}