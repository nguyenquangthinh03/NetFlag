package project.dheeraj.netflag.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import project.dheeraj.netflag.data.model.Movie
import project.dheeraj.netflag.ui.main.viewpager.HomeViewPagerFragment


@ExperimentalCoroutinesApi
class HomeViewPagerAdapter (
    fm : FragmentManager,
    lifecycle: Lifecycle,
    val movies : List<Movie> = ArrayList()
): FragmentStateAdapter(fm, lifecycle){

    override fun getItemCount(): Int = movies.size

    override fun createFragment(position: Int): Fragment {
        return HomeViewPagerFragment(movies[position])
    }

}