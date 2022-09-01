package com.vados.nasa_photo.ui.picture

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.forEach
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.vados.nasa_photo.R
import com.vados.nasa_photo.databinding.FragmentPictureOfTheDayBinding
import com.vados.nasa_photo.ui.support.PreferencesFragment
import com.vados.nasa_photo.ui.support.SettingsFragment
import com.vados.nasa_photo.utils.showSnackBarErrorMsg
import com.vados.nasa_photo.utils.showSnackBarInfoMsg
import com.vados.nasa_photo.utils.toast
import com.vados.nasa_photo.viewmodel.AppState
import com.vados.nasa_photo.viewmodel.PictureViewModel

/**
 * Главный фрагмент реализует функции:
 * - отображение фотограции получаемой с сайта NASA
 * - работа нижнего меню BottomAppBar и кнопки FAB
 * - работа перетаскиваемого ифнормационного окна "BottomSheetBehavior"
 */
class PictureOfTheDayFragment : Fragment() {
    private var _binding: FragmentPictureOfTheDayBinding? = null
    private val binding get() = _binding!!
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    companion object {
        lateinit var viewModel: PictureViewModel
        fun newInstance() = PictureOfTheDayFragment()
        private var isMain = true // Переменная хранящая состояние кнопки FAB
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

        //Инициализируем работу нижнего меню
        initBottomAppBar()
        //Инициализируем работу FAB
        initFAB()

        //Работа кнопки поиска ВИКИПЕДИЯ
        binding.inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data =
                    Uri.parse("https://en.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
            })
        }
    }

    //Функция работы с состояниями ViewModel
    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Succes -> {
                binding.progressBarPictureOTD.isVisible = false
                appState.pictureDTO.let {
                    if (it.mediaType == "image") {
                        binding.imageViewPOTD.load(it.hdurl)
                        binding.textViewPhotoName.text = it.title
                        view?.findViewById<TextView>(R.id.bottomSheetDescriptionHeader)
                            ?.let { textView ->
                                textView.text = it.title
                            }
                        view?.findViewById<TextView>(R.id.bottomSheetDescription)?.let { textView ->
                            textView.text = it.explanation
                        }
                    } else {
                        binding.imageViewPOTD.load(R.drawable.img)
                        view?.showSnackBarErrorMsg("No photo today")
                    }
                }

            }
            is AppState.Loading -> {
                binding.progressBarPictureOTD.isVisible = true
            }
            is AppState.Error -> {
                binding.progressBarPictureOTD.isVisible = false
                view?.showSnackBarErrorMsg(appState.error.message.toString())
            }
        }
    }

    //функция инициализирует BottomSheet (Нижнее перетаскиваемое окно)
    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                //TODO
            }

            //Дейтсвия при перетаскивании
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                binding.fab.animate()?.scaleX(1 - slideOffset)?.scaleY(1 - slideOffset)
                    ?.setDuration(0)?.start()
                binding.bottomAppBar.fabCradleRoundedCornerRadius = 160 * slideOffset
                binding.bottomAppBar.fabCradleMargin = 12 * (1 - slideOffset)
            }
        })
    }

    //функция реализует логику работы по клику на иконки меню BottomAppBar
    private fun onMenuItemSelected(menu: Menu) {
        menu.forEach {
            it.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.app_bar_fav -> {
                        view?.showSnackBarInfoMsg("Button Favorite")
                    }
                    R.id.app_bar_settings -> {
                        requireActivity().supportFragmentManager
                            .beginTransaction()
                            .hide(this)
                            .add(R.id.container, SettingsFragment())
                            .addToBackStack("main")
                            .commit()
                    }
                }
                true
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }
    //endregion

    //Функция инициализирует и устанавливает логику работы BottomAppBar
    private fun initBottomAppBar() {
        binding.bottomAppBar.let { it ->
            it.replaceMenu(R.menu.menu_bottom_bar)
            onMenuItemSelected(it.menu)
            it.setNavigationOnClickListener { itView ->
                val popupMenu = PopupMenu(context, itView)
                popupMenu.menuInflater.inflate(R.menu.menu_bottom_navigation, popupMenu.menu)
                popupMenu.show()
                popupMenu.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.navigation_archive -> {
                            itView?.showSnackBarInfoMsg("Button Archive")
                        }
                        R.id.navigation_send -> {
                            itView?.toast("Button Send")
                        }
                    }
                    true
                }
            }
        }
    }

    //Функция инициализирует и устанавливает логику работы FAB
    private fun initFAB() {
        with(binding) {
            fab.setOnClickListener {
                if (isMain) {
                    isMain = false
                    bottomAppBar.navigationIcon = null
                    bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                    binding.fab.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.ic_back_fab
                        )
                    )
                    bottomAppBar.replaceMenu(R.menu.menu_bottom_bar_other_screen)
                } else {
                    isMain = true
                    bottomAppBar.navigationIcon =
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.ic_hamburger_menu_bottom_bar
                        )
                    bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                    fab.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.ic_plus_fab
                        )
                    )
                    initBottomAppBar()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
