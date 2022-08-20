package com.vados.nasa_photo.ui.picture

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.ButtonBarLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.vados.nasa_photo.MainActivity
import com.vados.nasa_photo.R
import com.vados.nasa_photo.databinding.BottomSheetLayoutBinding
import com.vados.nasa_photo.databinding.FragmentPictureOfTheDayBinding
import com.vados.nasa_photo.ui.other.LoadingFragment
import com.vados.nasa_photo.utils.showSnackBarErrorMsg
import com.vados.nasa_photo.viewmodel.AppState
import com.vados.nasa_photo.viewmodel.PictureViewModel

class PictureOfTheDayFragment : Fragment() {

    private var _binding: FragmentPictureOfTheDayBinding? = null
    private val binding get() = _binding!!
    private val loadingFragment = LoadingFragment()
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

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

        setBottomSheetBehavior(view.findViewById(R.id.bottom_sheet_container))
        setBottomAppBar(view)
    }

    private fun renderData(appState: AppState){
        when (appState){
            is AppState.Succes ->{
                removeLoadFragment()
                appState.pictureDTO.let {
                    if (it.mediaType == "image") {
                        binding.imageViewPOTD.load(it.url)
                        binding.textViewPhotoName.text = it.title
                        view?.findViewById<TextView>(R.id.bottomSheetDescriptionHeader)?.let {textView ->
                            textView.text = it.title
                        }
                        view?.findViewById<TextView>(R.id.bottomSheetDescription)?.let {textView ->
                            textView.text = it.explanation
                        }

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

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        val fab = binding.fab
        binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar)
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                //TODO("Not yet implemented")
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                fab.animate()?.scaleX(1 - slideOffset)?.scaleY(1 - slideOffset)?.setDuration(0)?.start()
            }
        })
    }

    private fun setBottomAppBar(view: View) {
        //val mainActivity = activity as MainActivity
        //mainActivity.setSupportActionBar(view.findViewById(R.id.bottom_app_bar))
        //setHasOptionsMenu(false)
        //setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
