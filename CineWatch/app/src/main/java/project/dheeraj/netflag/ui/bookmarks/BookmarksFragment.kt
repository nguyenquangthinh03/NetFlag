package project.dheeraj.netflag.ui.bookmarks


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class BookmarksFragment : Fragment(), View.OnClickListener {
    override fun onClick(v: View?) {
        // Handle click events here
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Initialize your views and set up the fragment here
    }

    companion object {
        fun newInstance() = BookmarksFragment()
    }
}