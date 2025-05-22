package project.dheeraj.netflag.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.coroutines.ExperimentalCoroutinesApi
import project.dheeraj.netflag.R
import project.dheeraj.netflag.data.model.Movie
import project.dheeraj.netflag.databinding.ItemSearchBinding
import project.dheeraj.netflag.utils.CONSTANTS


@ExperimentalCoroutinesApi
class ViewAllRecyclerViewAdapter :
    PagingDataAdapter<Movie, ViewAllRecyclerViewAdapter.ViewHolder>(MOVIE_COMPARATOR) {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemSearchBinding.bind(itemView)

        fun bind(movie: Movie) {
            binding.apply {
                Glide.with(itemView)
                    .load(CONSTANTS.ImageBaseURL + movie.poster_path)
                    .placeholder(CONSTANTS.moviePlaceHolder[position%4])
                    .error(CONSTANTS.moviePlaceHolder[position%4])
                    .into(searchImage)

                itemView.setOnClickListener {
                    val bundle = bundleOf(CONSTANTS.movie to movie)
                    it.findNavController().navigate(R.id.action_viewAllFragment_to_movieDetailsFragment, bundle)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }

    }

    companion object {
        private val MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem

        }
    }


}