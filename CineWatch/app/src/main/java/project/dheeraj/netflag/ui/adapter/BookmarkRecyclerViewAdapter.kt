package project.dheeraj.netflag.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.coroutines.ExperimentalCoroutinesApi
import project.dheeraj.netflag.R
import project.dheeraj.netflag.data.model.Movie
import project.dheeraj.netflag.data.model.MovieDB
import project.dheeraj.netflag.databinding.ItemSearchBinding
import project.dheeraj.netflag.utils.CONSTANTS


@ExperimentalCoroutinesApi
class BookmarkRecyclerViewAdapter(
        val movies : List<MovieDB>
) : RecyclerView.Adapter<BookmarkRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemSearchBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with(holder) {

            Glide.with(itemView)
                .load("${CONSTANTS.ImageBaseURL}${movies[position].poster_path}")
                .placeholder(CONSTANTS.moviePlaceHolder[position%4])
                .error(CONSTANTS.moviePlaceHolder[position%4])
                .into(binding.searchImage)

            binding.root.setOnClickListener {
                val movie = Movie(
                    id = movies[position].id,
                    title =  movies[position].title,
                    backdrop_path = movies[position].backdrop_path,
                    poster_path = movies[position].poster_path,
                    overview = movies[position].overview
                )
                val bundle = bundleOf(CONSTANTS.movie to movie)
                it.findNavController().navigate(R.id.action_viewAllFragment_to_movieDetailsFragment, bundle)
            }

        }

    }

    override fun getItemCount() = movies.size


}