package com.egsi.labsi.sog.ioegje.presentation.ui.view

import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.PermissionRequest
import android.webkit.ValueCallback
import android.widget.FrameLayout
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.egsi.labsi.sog.ioegje.presentation.app.EggLabelApplication
import com.egsi.labsi.sog.ioegje.presentation.ui.load.EggLabelLoadFragment
import org.koin.android.ext.android.inject

class EggLabelV : Fragment(){

    private lateinit var eggLabelPhoto: Uri
    private var eggLabelFilePathFromChrome: ValueCallback<Array<Uri>>? = null

    private val eggLabelTakeFile: ActivityResultLauncher<PickVisualMediaRequest> = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {
        eggLabelFilePathFromChrome?.onReceiveValue(arrayOf(it ?: Uri.EMPTY))
        eggLabelFilePathFromChrome = null
    }

    private val eggLabelTakePhoto: ActivityResultLauncher<Uri> = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        if (it) {
            eggLabelFilePathFromChrome?.onReceiveValue(arrayOf(eggLabelPhoto))
            eggLabelFilePathFromChrome = null
        } else {
            eggLabelFilePathFromChrome?.onReceiveValue(null)
            eggLabelFilePathFromChrome = null
        }
    }

    private val eggLabelDataStore by activityViewModels<EggLabelDataStore>()


    private val eggLabelViFun by inject<EggLabelViFun>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(EggLabelApplication.EGG_LABEL_MAIN_TAG, "Fragment onCreate")
        CookieManager.getInstance().setAcceptCookie(true)
        requireActivity().onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (eggLabelDataStore.eggLabelView.canGoBack()) {
                        eggLabelDataStore.eggLabelView.goBack()
                        Log.d(EggLabelApplication.EGG_LABEL_MAIN_TAG, "WebView can go back")
                    } else if (eggLabelDataStore.eggLabelViList.size > 1) {
                        Log.d(EggLabelApplication.EGG_LABEL_MAIN_TAG, "WebView can`t go back")
                        eggLabelDataStore.eggLabelViList.removeAt(eggLabelDataStore.eggLabelViList.lastIndex)
                        Log.d(EggLabelApplication.EGG_LABEL_MAIN_TAG, "WebView list size ${eggLabelDataStore.eggLabelViList.size}")
                        eggLabelDataStore.eggLabelView.destroy()
                        val previousWebView = eggLabelDataStore.eggLabelViList.last()
                        eggLabelAttachWebViewToContainer(previousWebView)
                        eggLabelDataStore.eggLabelView = previousWebView
                    }
                }

            })
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (eggLabelDataStore.eggLabelIsFirstCreate) {
            eggLabelDataStore.eggLabelIsFirstCreate = false
            eggLabelDataStore.eggLabelContainerView = FrameLayout(requireContext()).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                id = View.generateViewId()
            }
            return eggLabelDataStore.eggLabelContainerView
        } else {
            return eggLabelDataStore.eggLabelContainerView
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(EggLabelApplication.EGG_LABEL_MAIN_TAG, "onViewCreated")
        if (eggLabelDataStore.eggLabelViList.isEmpty()) {
            eggLabelDataStore.eggLabelView = EggLabelVi(requireContext(), object :
                EggLabelCallBack {
                override fun eggLabelHandleCreateWebWindowRequest(eggLabelVi: EggLabelVi) {
                    eggLabelDataStore.eggLabelViList.add(eggLabelVi)
                    Log.d(EggLabelApplication.EGG_LABEL_MAIN_TAG, "WebView list size = ${eggLabelDataStore.eggLabelViList.size}")
                    Log.d(EggLabelApplication.EGG_LABEL_MAIN_TAG, "CreateWebWindowRequest")
                    eggLabelDataStore.eggLabelView = eggLabelVi
                    eggLabelVi.eggLabelSetFileChooserHandler { callback ->
                        eggLabelHandleFileChooser(callback)
                    }
                    eggLabelAttachWebViewToContainer(eggLabelVi)
                }

            }, eggLabelWindow = requireActivity().window).apply {
                eggLabelSetFileChooserHandler { callback ->
                    eggLabelHandleFileChooser(callback)
                }
            }
            eggLabelDataStore.eggLabelView.eggLabelFLoad(arguments?.getString(EggLabelLoadFragment.EGG_LABEL_D) ?: "")
//            ejvview.fLoad("www.google.com")
            eggLabelDataStore.eggLabelViList.add(eggLabelDataStore.eggLabelView)
            eggLabelAttachWebViewToContainer(eggLabelDataStore.eggLabelView)
        } else {
            eggLabelDataStore.eggLabelViList.forEach { webView ->
                webView.eggLabelSetFileChooserHandler { callback ->
                    eggLabelHandleFileChooser(callback)
                }
            }
            eggLabelDataStore.eggLabelView = eggLabelDataStore.eggLabelViList.last()

            eggLabelAttachWebViewToContainer(eggLabelDataStore.eggLabelView)
        }
        Log.d(EggLabelApplication.EGG_LABEL_MAIN_TAG, "WebView list size = ${eggLabelDataStore.eggLabelViList.size}")
    }

    private fun eggLabelHandleFileChooser(callback: ValueCallback<Array<Uri>>?) {
        Log.d(EggLabelApplication.EGG_LABEL_MAIN_TAG, "handleFileChooser called, callback: ${callback != null}")

        eggLabelFilePathFromChrome = callback

        val listItems: Array<out String> = arrayOf("Select from file", "To make a photo")
        val listener = DialogInterface.OnClickListener { _, which ->
            when (which) {
                0 -> {
                    Log.d(EggLabelApplication.EGG_LABEL_MAIN_TAG, "Launching file picker")
                    eggLabelTakeFile.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }
                1 -> {
                    Log.d(EggLabelApplication.EGG_LABEL_MAIN_TAG, "Launching camera")
                    eggLabelPhoto = eggLabelViFun.eggLabelSavePhoto()
                    eggLabelTakePhoto.launch(eggLabelPhoto)
                }
            }
        }

        AlertDialog.Builder(requireContext())
            .setTitle("Choose a method")
            .setItems(listItems, listener)
            .setCancelable(true)
            .setOnCancelListener {
                Log.d(EggLabelApplication.EGG_LABEL_MAIN_TAG, "File chooser canceled")
                callback?.onReceiveValue(null)
                eggLabelFilePathFromChrome = null
            }
            .create()
            .show()
    }

    private fun eggLabelAttachWebViewToContainer(w: EggLabelVi) {
        eggLabelDataStore.eggLabelContainerView.post {
            (w.parent as? ViewGroup)?.removeView(w)
            eggLabelDataStore.eggLabelContainerView.removeAllViews()
            eggLabelDataStore.eggLabelContainerView.addView(w)
        }
    }


}