package com.udacity.asteroidradar.main

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        val application = requireNotNull(this.activity).application
        val viewModelFactory = MainViewModelFactory(application)
        ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(inflater)

//         Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
//         Use =viewLifecycleOwner instead of =this to avoid memory leaks
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

//      Sets the adapter of the asteroidRecycler RecyclerView with clickHandler lambda that
//      tells the viewModel when our property is clicked
        val adapter = MainListAdapter(MainListAdapter.OnClickListener {
            viewModel.displayAsteroidDetailScreen(it)
        })
        binding.asteroidRecycler.adapter = adapter


//      Observe the navigateToDetailScreen LiveData and Navigate when it isn't null
//      After navigating, call displayAsteroidDetailScreenComplete() so that the ViewModel is ready
//      for another navigation event.
        viewModel.navigateToDetailScreen.observe(viewLifecycleOwner) {
            if (null != it) {
                this.findNavController().navigate(
                    MainFragmentDirections.actionShowDetail(it)
                )
                viewModel.displayAsteroidDetailScreenComplete()
            }
        }
//      Sets the OnClickListener for the ImageOfTheDay that tells the viewModel the image has been
//      clicked
        binding.activityMainImageOfTheDay.setOnClickListener {
            Log.i("MainFragment", "setOnClick")
            val imageOfTheDay = viewModel.imageOfTheDay.value
            if (imageOfTheDay != null) {
                viewModel.displayImageDetailScreen(imageOfTheDay)
            }
        }

//      Observe the navigateToImageScreen LiveData and Navigate when it isn't null
//      After navigating, call displayImageDetailScreenComplete() so that the ViewModel is ready
//      for another navigation event.
        viewModel.navigateToImageScreen.observe(viewLifecycleOwner) {
            if (null != it) {
                this.findNavController().navigate(MainFragmentDirections.actionShowImage(it))
                viewModel.displayImageDetailScreenComplete()
            }
        }
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        viewModel.updateFilter(
            when (item.itemId) {
                R.id.show_today_asteroids_menu -> AsteroidFilter.TODAY
                R.id.show_week_asteroids_menu -> AsteroidFilter.WEEK
                R.id.show_all_asteroids_menu -> AsteroidFilter.ALL
                else -> AsteroidFilter.ALL
            }
        )
        return true
    }
}
