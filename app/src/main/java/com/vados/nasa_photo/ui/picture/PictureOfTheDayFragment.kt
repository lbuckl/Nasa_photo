package com.vados.nasa_photo.ui.picture

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.ButtonBarLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.forEach
import androidx.core.view.marginBottom
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.vados.nasa_photo.MainActivity
import com.vados.nasa_photo.R
import com.vados.nasa_photo.databinding.BottomSheetLayoutBinding
import com.vados.nasa_photo.databinding.FragmentPictureOfTheDayBinding
import com.vados.nasa_photo.ui.other.LoadingFragment
import com.vados.nasa_photo.ui.other.SettingsFragment
import com.vados.nasa_photo.utils.showSnackBarErrorMsg
import com.vados.nasa_photo.utils.showSnackBarInfoMsg
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

        //Эта функция позволяет реализовать верхнее меню, оставляю на будущее
        //setBottomAppBar(view)

        //Работа кнопки поиска ВИКИПЕДИЯ
        binding.inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://en.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
            })
        }
    }

    private fun renderData(appState: AppState){
        when (appState){
            is AppState.Succes ->{
                removeLoadFragment()
                appState.pictureDTO.let {
                    if (it.mediaType == "image") {
                        binding.imageViewPOTD.load(it.hdurl)
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
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                //TODO
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                binding.fab.animate()?.scaleX(1 - slideOffset)?.scaleY(1 - slideOffset)?.setDuration(0)?.start()
                binding.bottomAppBar.fabCradleRoundedCornerRadius = 160*slideOffset
                binding.bottomAppBar.fabCradleMargin = 12*(1-slideOffset)
            }
        })

        //Инициализируем иконок в нижнем меню и навешиваем лисенеры
        binding.bottomAppBar.let{
            it.replaceMenu(R.menu.menu_bottom_bar)
            onMenuItemSelected(it.menu)
        }
    }

    private fun onMenuItemSelected(menu: Menu){
        menu.forEach{
            it.setOnMenuItemClickListener {item ->
                when (item.itemId) {
                    R.id.app_bar_fav -> {
                        view?.showSnackBarInfoMsg("Button Favorite")
                    }
                    R.id.app_bar_settings -> {
                        requireActivity().supportFragmentManager
                            .beginTransaction()
                            .hide(this)
                            .add(R.id.container,SettingsFragment())
                            .addToBackStack("main")
                            .commit()
                    }
                }
                true
            }
        }

    }

    //region Эта функция позволяет реализовать верхнее меню, оставляю на будущее
    private fun setBottomAppBar(view: View) {
        //setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }
    //endregion

    private fun Fragment.toast(string: String?) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.BOTTOM, 0, 250)
            show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
