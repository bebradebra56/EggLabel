package com.egsi.labsi.sog.ioegje.presentation.ui.load

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.egsi.labsi.sog.MainActivity
import com.egsi.labsi.sog.R
import com.egsi.labsi.sog.databinding.FragmentLoadEggLabelBinding
import com.egsi.labsi.sog.ioegje.data.shar.EggLabelSharedPreference
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class EggLabelLoadFragment : Fragment(R.layout.fragment_load_egg_label) {
    private lateinit var eggLabelLoadBinding: FragmentLoadEggLabelBinding

    private val eggLabelLoadViewModel by viewModel<EggLabelLoadViewModel>()

    private val eggLabelSharedPreference by inject<EggLabelSharedPreference>()

    private var eggLabelUrl = ""

    private val eggLabelRequestNotificationPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            eggLabelNavigateToSuccess(eggLabelUrl)
        } else {
            if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                eggLabelSharedPreference.eggLabelNotificationRequest =
                    (System.currentTimeMillis() / 1000) + 259200
                eggLabelNavigateToSuccess(eggLabelUrl)
            } else {
                eggLabelNavigateToSuccess(eggLabelUrl)
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eggLabelLoadBinding = FragmentLoadEggLabelBinding.bind(view)

        eggLabelLoadBinding.eggLabelGrandButton.setOnClickListener {
            val eggLabelPermission = Manifest.permission.POST_NOTIFICATIONS
            eggLabelRequestNotificationPermission.launch(eggLabelPermission)
            eggLabelSharedPreference.eggLabelNotificationRequestedBefore = true
        }

        eggLabelLoadBinding.eggLabelSkipButton.setOnClickListener {
            eggLabelSharedPreference.eggLabelNotificationRequest =
                (System.currentTimeMillis() / 1000) + 259200
            eggLabelNavigateToSuccess(eggLabelUrl)
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                eggLabelLoadViewModel.eggLabelHomeScreenState.collect {
                    when (it) {
                        is EggLabelLoadViewModel.EggLabelHomeScreenState.EggLabelLoading -> {

                        }

                        is EggLabelLoadViewModel.EggLabelHomeScreenState.EggLabelError -> {
                            requireActivity().startActivity(
                                Intent(
                                    requireContext(),
                                    MainActivity::class.java
                                )
                            )
                            requireActivity().finish()
                        }

                        is EggLabelLoadViewModel.EggLabelHomeScreenState.EggLabelSuccess -> {
                            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S_V2) {
                                val eggLabelPermission = Manifest.permission.POST_NOTIFICATIONS
                                val eggLabelPermissionRequestedBefore = eggLabelSharedPreference.eggLabelNotificationRequestedBefore

                                if (ContextCompat.checkSelfPermission(requireContext(), eggLabelPermission) == PackageManager.PERMISSION_GRANTED) {
                                    eggLabelNavigateToSuccess(it.data)
                                } else if (!eggLabelPermissionRequestedBefore && (System.currentTimeMillis() / 1000 > eggLabelSharedPreference.eggLabelNotificationRequest)) {
                                    // первый раз — показываем UI для запроса
                                    eggLabelLoadBinding.eggLabelNotiGroup.visibility = View.VISIBLE
                                    eggLabelLoadBinding.eggLabelLoadingGroup.visibility = View.GONE
                                    eggLabelUrl = it.data
                                } else if (shouldShowRequestPermissionRationale(eggLabelPermission)) {
                                    // временный отказ — через 3 дня можно показать
                                    if (System.currentTimeMillis() / 1000 > eggLabelSharedPreference.eggLabelNotificationRequest) {
                                        eggLabelLoadBinding.eggLabelNotiGroup.visibility = View.VISIBLE
                                        eggLabelLoadBinding.eggLabelLoadingGroup.visibility = View.GONE
                                        eggLabelUrl = it.data
                                    } else {
                                        eggLabelNavigateToSuccess(it.data)
                                    }
                                } else {
                                    // навсегда отклонено — просто пропускаем
                                    eggLabelNavigateToSuccess(it.data)
                                }
                            } else {
                                eggLabelNavigateToSuccess(it.data)
                            }
                        }

                        EggLabelLoadViewModel.EggLabelHomeScreenState.EggLabelNotInternet -> {
                            eggLabelLoadBinding.eggLabelStateGroup.visibility = View.VISIBLE
                            eggLabelLoadBinding.eggLabelLoadingGroup.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }


    private fun eggLabelNavigateToSuccess(data: String) {
        findNavController().navigate(
            R.id.action_eggLabelLoadFragment_to_eggLabelV,
            bundleOf(EGG_LABEL_D to data)
        )
    }

    companion object {
        const val EGG_LABEL_D = "eggLabelData"
    }
}