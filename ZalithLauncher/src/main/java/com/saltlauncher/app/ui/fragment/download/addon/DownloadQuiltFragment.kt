package com.saltlauncher.app.ui.fragment.download.addon

import com.saltlauncher.app.R
import com.saltlauncher.app.feature.mod.modloader.FabricLikeUtils

class DownloadQuiltFragment : DownloadFabricLikeFragment(FabricLikeUtils.QUILT_UTILS, R.drawable.ic_quilt) {
    companion object {
        const val TAG: String = "DownloadQuiltFragment"
    }
}