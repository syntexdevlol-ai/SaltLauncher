package com.saltlauncher.app.ui.fragment.download.addon

import com.saltlauncher.app.R
import com.saltlauncher.app.feature.mod.modloader.FabricLikeUtils

class DownloadFabricFragment : DownloadFabricLikeFragment(FabricLikeUtils.FABRIC_UTILS, R.drawable.ic_fabric) {
    companion object {
        const val TAG: String = "DownloadFabricFragment"
    }
}