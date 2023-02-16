package com.udacity.asteroidradar.imagedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.udacity.asteroidradar.databinding.FragmentImageBinding


/**
 * A simple [Fragment] subclass.
 * Use the [ImageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ImageFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = requireNotNull(activity).application
        val binding = FragmentImageBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        val imageOfDay = ImageFragmentArgs.fromBundle(requireArguments()).selectedImage

        val viewModelFactory = ImageViewModelFactory(imageOfDay, application)
        binding.viewModel =
            ViewModelProvider(this, viewModelFactory).get(ImageViewModel::class.java)

        binding.imageOfDay = imageOfDay

        return binding.root
    }

}