package project.dheeraj.netflag.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import coil.load
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import project.dheeraj.netflag.R
import project.dheeraj.netflag.data.model.Cast
import project.dheeraj.netflag.data.model.Movie
import project.dheeraj.netflag.data.model.Status
import project.dheeraj.netflag.databinding.FragmentActorDetailsBinding
import project.dheeraj.netflag.ui.adapter.BestMoviesRecyclerViewAdapter
import project.dheeraj.netflag.utils.CONSTANTS
import project.dheeraj.netflag.utils.showToast

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ActorDetailsFragment : Fragment() {

    private val viewModel : ActorDetailsViewModel by viewModels()

    private lateinit var binding : FragmentActorDetailsBinding
    private lateinit var cast : Cast

    private var movieCredits : ArrayList<Movie> = ArrayList()
    private lateinit var homeRecyclerViewAdapter : BestMoviesRecyclerViewAdapter

    private lateinit var skeletonBestMovies : Skeleton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_actor_details, container, false)
        binding = FragmentActorDetailsBinding.bind(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cast = requireArguments().get(CONSTANTS.cast) as Cast

        binding.buttonBack.setOnClickListener {
            it.findNavController().navigateUp()
        }

        initAdapters()

        fetchData()

    }

    private fun fetchData() {
        viewModel.getPerson(cast.id).observe(requireActivity(), Observer {
            val actor = it.data
            if (actor != null) {
                binding.textActorName.text = actor.name
                binding.actorImage.load(CONSTANTS.ImageBaseURL + actor.profile_path)

                var knownAs = ""
                for (i in 0..Math.min(4, actor.also_known_as.size) - 1) {
                    knownAs += actor.also_known_as[i]
                    if (i != actor.also_known_as.size - 1) {
                        knownAs += ", "
                    }
                }
                binding.textAlsoKnownAs.text = knownAs

                binding.textBio.text = actor.biography
                    .takeIf { !it.isNullOrBlank() }
                    ?: "No information"

                binding.textPopularity.text = actor.popularity.toString()
                binding.textCharacter.text = actor.known_for_department
                binding.textBirthday.text = actor.birthday
            }
        })

        viewModel.getPersonMovieCredits(cast.id).observe(requireActivity(), Observer { res ->
            when (res.status) {
                Status.LOADING -> {
                    skeletonBestMovies.showSkeleton()
                }
                Status.SUCCESS -> {
                    skeletonBestMovies.showOriginal()
                    movieCredits.clear()
                    movieCredits.addAll(res.data!!.cast)
                    homeRecyclerViewAdapter.notifyDataSetChanged()
                }
                Status.ERROR -> {
                    showToast("Something went wrong!")
                }
            }
        })
    }

    private fun initAdapters() {
        homeRecyclerViewAdapter = BestMoviesRecyclerViewAdapter(requireContext(), movieCredits)
        binding.recyclerViewBestMovies.adapter = homeRecyclerViewAdapter
        skeletonBestMovies = binding.recyclerViewBestMovies.applySkeleton(R.layout.item_movie_home, 10)
    }

}