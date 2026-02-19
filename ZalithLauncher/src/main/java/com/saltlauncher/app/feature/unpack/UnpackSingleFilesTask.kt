package com.saltlauncher.app.feature.unpack

import android.content.Context
import com.saltlauncher.app.feature.log.Logging.e
import com.saltlauncher.app.utils.CopyDefaultFromAssets.Companion.copyFromAssets
import com.saltlauncher.app.utils.path.PathManager
import net.kdt.pojavlaunch.Tools

class UnpackSingleFilesTask(val context: Context) : AbstractUnpackTask() {
    override fun isNeedUnpack(): Boolean = true

    override fun run() {
        runCatching {
            copyFromAssets(context)
            Tools.copyAssetFile(context, "resolv.conf", PathManager.DIR_DATA, false)
        }.getOrElse { e("AsyncAssetManager", "Failed to unpack critical components !") }
    }
}