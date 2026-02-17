package com.saltlauncher.app.feature.download

import androidx.lifecycle.ViewModel
import com.saltlauncher.app.feature.download.item.InfoItem
import com.saltlauncher.app.feature.download.platform.AbstractPlatformHelper

class InfoViewModel : ViewModel() {
    var platformHelper: AbstractPlatformHelper? = null
    var infoItem: InfoItem? = null
}