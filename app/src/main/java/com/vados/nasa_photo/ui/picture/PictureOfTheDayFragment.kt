package com.vados.nasa_photo.ui.picture

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.vados.nasa_photo.R
import com.vados.nasa_photo.databinding.FragmentPictureOfTheDayBinding
import com.vados.nasa_photo.ui.other.LoadingFragment
import com.vados.nasa_photo.utils.showSnackBarErrorMsg
import com.vados.nasa_photo.viewmodel.AppState
import com.vados.nasa_photo.viewmodel.PictureViewModel

class PictureOfTheDayFragment : Fragment() {

    private var _binding: FragmentPictureOfTheDayBinding? = null
    private val binding get() = _binding!!
    private val loadingFragment = LoadingFragment()

    companion object {
        lateinit var viewModel: PictureViewModel
        fun newInstance() = PictureOfTheDayFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPictureOfTheDayBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[PictureViewModel::class.java]
        viewModel.getLiveData().observe(viewLifecycleOwner) { t -> renderData(t) }
    }

    private fun renderData(appState: AppState){
        when (appState){
            is AppState.Succes ->{
                removeLoadFragment()
                appState.pictureDTO.let {
                    if (it.mediaType == "image") {
                        binding.imageViewPOTD.load(it.url)
                        binding.textViewPhotoName.text = it.title
                        binding.textViewPhotoDescription.text = it.explanation
                    }
                    else {
                        binding.imageViewPOTD.load(R.drawable.img)
                        view?.showSnackBarErrorMsg("No photo today")
                    }
                }

            }
            is AppState.Loading -> {
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .add(R.id.container,loadingFragment)
                    .commit()
            }
            is AppState.Error -> {
                removeLoadFragment()
                view?.showSnackBarErrorMsg(appState.error.message.toString())
            }
        }
    }

    private fun removeLoadFragment(){
        requireActivity().supportFragmentManager
            .beginTransaction()
            .remove(loadingFragment)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}