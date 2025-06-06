package project.dheeraj.netflag.ui.main.viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import coil.load
import kotlinx.coroutines.ExperimentalCoroutinesApi
import project.dheeraj.netflag.R
import project.dheeraj.netflag.data.model.Movie
import project.dheeraj.netflag.databinding.FragmentHomeViewPagerBinding
import project.dheeraj.netflag.utils.CONSTANTS

@ExperimentalCoroutinesApi
class HomeViewPagerFragment(
    val movie: Movie
) : Fragment() {

    private lateinit var binding : FragmentHomeViewPagerBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home_view_pager, container, false)
        binding = FragmentHomeViewPagerBinding.bind(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewPagerImage.load(CONSTANTS.ImageBaseURL + movie.backdrop_path) {
            placeholder(CONSTANTS.viewPagerPlaceHolder.random())
            error(CONSTANTS.viewPagerPlaceHolder.random())
        }
        binding.viewPagerText.text = movie.title

        binding.root.setOnClickListener {
            val bundle = bundleOf(CONSTANTS.movie to movie)
            it.findNavController().navigate(R.id.action_homeFragment_to_movieDetailsFragment, bundle)
        }

    }
}