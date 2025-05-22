package project.dheeraj.netflag.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import project.dheeraj.netflag.R
import project.dheeraj.netflag.data.model.Movie
import project.dheeraj.netflag.databinding.FragmentViewAllBinding
import project.dheeraj.netflag.ui.adapter.BookmarkRecyclerViewAdapter
import project.dheeraj.netflag.ui.adapter.ViewAllRecyclerViewAdapter
import project.dheeraj.netflag.utils.CONSTANTS

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ViewAllFragment : Fragment() {

    private lateinit var binding: FragmentViewAllBinding
    private val viewModel: ViewAllViewModel by viewModels()

    private lateinit var movieAdapter: ViewAllRecyclerViewAdapter
    private lateinit var movieSkeleton: Skeleton

    private var moviesList: ArrayList<Movie> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_view_all, container, false)
        binding = FragmentViewAllBinding.bind(view)
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieAdapter = ViewAllRecyclerViewAdapter()
        binding.movieRecyclerView.adapter = movieAdapter

        movieSkeleton = binding.movieRecyclerView.applySkeleton(R.layout.item_search, 15)

        val pageType = requireArguments().get(CONSTANTS.viewAll)
        binding.pageTitle.text = pageType.toString()
        when(pageType) {
            CONSTANTS.Upcoming -> fetchUpcoming()
            CONSTANTS.TopRated -> fetchTopRated()
            CONSTANTS.Popular -> fetchPopular()
            CONSTANTS.Bookmarks -> fetchBookmarks()
        }

        binding.buttonBack.setOnClickListener {
            binding.root.findNavController().navigateUp()
        }

    }

    fun fetchBookmarks() {

        viewModel.bookmarks.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.movieRecyclerView.adapter = BookmarkRecyclerViewAdapter(it)
            }
        }

        viewModel.fetchBookmarks()

    }

    fun fetchPopular() {
        viewModel.fetchPopular().observe(viewLifecycleOwner) {

            movieAdapter.submitData(viewLifecycleOwner.lifecycle, it)

        }
    }

    fun fetchTopRated() {

        viewModel.fetchTopRated().observe(viewLifecycleOwner) {

            movieAdapter.submitData(viewLifecycleOwner.lifecycle, it)

        }

    }

    fun fetchUpcoming() {

        viewModel.fetchUpcoming().observe(viewLifecycleOwner) {

            movieAdapter.submitData(viewLifecycleOwner.lifecycle, it)

        }

    }

}