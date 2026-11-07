package com.abdownloadmanager.desktop.actions.onevennts

import com.abdownloadmanager.shared.util.DownloadSystem
import com.abdownloadmanager.shared.util.autoremove.AutoRemoveOption
import com.abdownloadmanager.shared.util.ondownloadcompletion.OnDownloadCompletionAction
import ir.amirab.downloader.downloaditem.IDownloadItem
import ir.amirab.downloader.downloaditem.contexts.RemovedBy
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue

/**
 * Removes the download record (but keeps the file on disk) when a completed
 * download is an MP4 file, so finished MP4s don't pile up in the list.
 */
class RemoveMp4DownloadRecordOnDownloadFinish : OnDownloadCompletionAction, KoinComponent {
    private val downloadSystem: DownloadSystem by inject()

    override suspend fun onDownloadCompleted(downloadItem: IDownloadItem) {
        if (!downloadItem.name.endsWith(".mp4", ignoreCase = true)) return
        downloadSystem.removeDownload(
            id = downloadItem.id,
            alsoRemoveFile = false,
            context = RemovedBy(AutoRemoveOption),
        )
    }
}
