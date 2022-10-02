package com.vados.nasa_photo.ui.picture

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
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
import com.vados.nasa_photo.model.ImageToMemoryLoader
import com.vados.nasa_photo.ui.support.SettingsFragment
import com.vados.nasa_photo.utils.*
import com.vados.nasa_photo.viewmodel.AppState
import com.vados.nasa_photo.viewmodel.PictureViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

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
    private var urlPicture:String? = null
    private val coroutineScope = CoroutineScope(Dispatchers.Main)


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
                    urlPicture = it.hdurl
                    if (it.mediaType == "image") {
                        binding.imageViewPOTD.load(urlPicture)
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
                        binding.imageViewPOTD.load(urlPicture){
                            target {draw ->
                                coroutineScope.launch {
                                    //savePicture(draw)
                                    view?.toast(ImageToMemoryLoader.savePicture(requireContext(),draw))
                                }
                            }
                        }
                    }
                    R.id.app_bar_settings -> {
                        requireActivity().supportFragmentManager
                            .beginTransaction()
                            .hide(this)
                            .add(R.id.container, SettingsFragment.newInstance())
                            .addToBackStack("main")
                            .commit()
                    }
                }
                true
            }
        }
    }
    //endregion

    //Функция инициализирует и устанавливает логику работы BottomAppBar
    private fun initBottomAppBar() {
        binding.bottomAppBar.let {
            it.replaceMenu(R.menu.menu_bottom_bar)
            onMenuItemSelected(it.menu)
            it.setNavigationOnClickListener { itView ->
                val popupMenu = PopupMenu(context,itView)
                requireActivity().menuInflater.inflate(R.menu.menu_bottom_navigation, popupMenu.menu)
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
                popupMenu.show()
            }
        }
    }

    //Функция инициализирует и устанавливает логику работы FAB
    @SuppressLint("ResourceType")
    private fun initFAB() {
        with(binding) {
            fab.setOnClickListener {
                if (isMain) {
                    isMain = false
                    bottomAppBar.navigationIcon = null
                    //Перемещаем fab вправо
                    bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                    setFABicon(R.drawable.ic_back_fab)
                    bottomAppBar.replaceMenu(R.menu.menu_bottom_bar_other_screen)
                } else {
                    isMain = true
                    bottomAppBar.navigationIcon =
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.ic_hamburger_menu_bottom_bar
                        )
                    //Перемещаем fab в центр
                    bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                    if (getCodeTheme() == THEME_SPACE){
                        setFABicon(R.drawable.ic_star)
                    }
                    else{
                        setFABicon(R.drawable.ic_plus_fab)
                    }

                    initBottomAppBar()
                }
            }
        }
    }

    private fun getCodeTheme():Int{
        val sharedPref = requireContext().getSharedPreferences(PREF_SETTINGS, Context.MODE_PRIVATE)
        return sharedPref.getInt(PREF_THEME_INT, THEME_LIGHT)
    }

    private fun setFABicon(id:Int){
        binding.fab.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                id
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        coroutineScope.cancel()
        _binding = null
    }
}
