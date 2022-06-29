package com.esaudev.gamesgarden.ui.navigation_states

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.esaudev.gamesgarden.R
import com.esaudev.gamesgarden.databinding.FragmentInitialBinding
import com.esaudev.gamesgarden.util.CustomTabsHelper.getPackageNameToUse
import com.esaudev.gamesgarden.util.CustomTabsHelper.openNativeWebView
import com.esaudev.gamesgarden.util.Util


class InitialFragment : Fragment() {

    private var _binding: FragmentInitialBinding? = null
    private val binding: FragmentInitialBinding
        get() = _binding!!

    private var GFG_URI = "https://www.google.com/search?q=asffas&oq=asffas&aqs=chrome..69i57.358j0j1&sourceid=chrome&ie=UTF-8"
    private var package_name = "com.android.chrome"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentInitialBinding
            .inflate(inflater, container, false)
            .apply { _binding = this }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mbGoNext.setOnClickListener {
            //findNavController().navigate(R.id.toFinalFragment)
            goToBrowser()
        }
    }

    private fun goToBrowser() {
        val builder = CustomTabsIntent.Builder()

        // to set the toolbar color use CustomTabColorSchemeParams
        // since CustomTabsIntent.Builder().setToolBarColor() is deprecated

        val params = CustomTabColorSchemeParams.Builder()
        params.setToolbarColor(ContextCompat.getColor(requireContext(), R.color.black))
        builder.setDefaultColorSchemeParams(params.build())

        // shows the title of web-page in toolbar
        builder.setShowTitle(true)

        // setShareState(CustomTabsIntent.SHARE_STATE_ON) will add a menu to share the web-page
        builder.setShareState(CustomTabsIntent.SHARE_STATE_ON)

        // To modify the close button, use
        // builder.setCloseButtonIcon(bitmap)

        // to set weather instant apps is enabled for the custom tab or not, use
        builder.setInstantAppsEnabled(true)

        //  To use animations use -
        //  builder.setStartAnimations(this, android.R.anim.start_in_anim, android.R.anim.start_out_anim)
        //  builder.setExitAnimations(this, android.R.anim.exit_in_anim, android.R.anim.exit_out_anim)
        val customBuilder = builder.build()
        val packageName = getPackageNameToUse(requireContext())?.ifEmpty{
            package_name
        }?: package_name
        Log.d("TAG_ESAU", packageName)

        runCatching {
            customBuilder.intent.setPackage(packageName)
            customBuilder.launchUrl(requireContext(), Uri.parse(GFG_URI))
        }.onFailure {
            openNativeWebView(GFG_URI, GFG_URI, requireContext())
        }
    }

    private fun isAppInstalled(): Boolean {
        return Util.isAppInstalled(package_name, requireContext())
    }

    fun Context.isPackageInstalled(packageName: String): Boolean {
        // check if chrome is installed or not
        return try {
            val appInfo = packageManager.getApplicationInfo(packageName, 0)
            return appInfo.enabled
        } catch (e: PackageManager.NameNotFoundException) {
            Log.d("TAG_ESAU", e.toString())
            false
        }
    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(requireContext(), "on Resume triggered", Toast.LENGTH_SHORT).show()
    }


}