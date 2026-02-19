package com.saltlauncher.app.feature.download.install

import com.kdt.mcgui.ProgressLayout
import com.saltlauncher.app.R
import com.saltlauncher.app.event.value.DownloadProgressKeyEvent
import com.saltlauncher.app.feature.download.item.ModLoaderWrapper
import com.saltlauncher.app.feature.download.item.VersionItem
import com.saltlauncher.app.feature.log.Logging
import com.saltlauncher.app.feature.version.VersionsManager
import com.saltlauncher.app.task.Task
import com.saltlauncher.app.utils.path.PathManager
import net.kdt.pojavlaunch.progresskeeper.DownloaderProgressWrapper
import net.kdt.pojavlaunch.utils.DownloadUtils
import org.apache.commons.io.FileUtils
import org.greenrobot.eventbus.EventBus
import java.io.File
import java.io.IOException

class InstallHelper {
    companion object {
        @Throws(Throwable::class)
        fun downloadFile(version: VersionItem, targetFile: File, progressKey: String) {
            downloadFile(version, targetFile, progressKey, null)
        }

        @Throws(Throwable::class)
        fun downloadFile(
            version: VersionItem,
            targetFile: File,
            progressKey: String,
            listener: OnFileDownloadedListener?
        ) {
            Task.runTask {
                EventBus.getDefault().post(DownloadProgressKeyEvent(progressKey, true))
                try {
                    val downloadBuffer = ByteArray(8192)
                    DownloadUtils.ensureSha1<Void?>(targetFile, version.fileHash) {
                        Logging.i(
                            "InstallHelper",
                            "Download Url: ${version.fileUrl}"
                        )
                        DownloadUtils.downloadFileMonitored(
                            version.fileUrl, targetFile, downloadBuffer,
                            DownloaderProgressWrapper(R.string.download_install_download_file, progressKey)
                        )
                        null
                    }
                } finally {
                    listener?.onEnded(targetFile)
                    ProgressLayout.clearProgress(progressKey)
                    EventBus.getDefault().post(DownloadProgressKeyEvent(progressKey, false))
                }
            }.execute()
        }

        @Throws(IOException::class)
        fun installModPack(
            version: VersionItem,
            customName: String,
            installFunction: ModPackInstallFunction
        ): ModLoaderWrapper? {
            val modpackFile = File(
                PathManager.DIR_CACHE, "$customName.cf"
            ) // Cache File

            val modLoaderInfo: ModLoaderWrapper?
            try {
                val downloadBuffer = ByteArray(8192)
                DownloadUtils.ensureSha1<Void?>(
                    modpackFile, version.fileHash
                ) {
                    Logging.i("InstallHelper", "Download Url: ${version.fileUrl}")
                    DownloadUtils.downloadFileMonitored(
                        version.fileUrl, modpackFile, downloadBuffer,
                        DownloaderProgressWrapper(
                            R.string.modpack_download_downloading_metadata,
                            ProgressLayout.INSTALL_RESOURCE
                        )
                    )
                    null
                }

                // Install the modpack
                modLoaderInfo = installFunction.install(modpackFile, VersionsManager.getVersionPath(customName))
            } finally {
                FileUtils.deleteQuietly(modpackFile)
                ProgressLayout.clearProgress(ProgressLayout.INSTALL_RESOURCE)
            }
            modLoaderInfo ?: return null
            Logging.i("InstallHelper", "ModLoader is ${modLoaderInfo.nameById}")

            return modLoaderInfo
        }
    }
}