package com.example.tea.data.storage.googleplayupdate

import android.app.Activity
import android.content.IntentSender
import com.example.tea.data.storage.GooglePlayUpdateInterface
import com.google.android.gms.tasks.Task
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability

class GooglePlayUpdateImp(val activity: Activity, val appUpdateManager: AppUpdateManager): GooglePlayUpdateInterface {
    val UPDATE_REQUEST_CODE = 8

    override fun checkUpdate() {
        val appUpdateInfoTask: Task<AppUpdateInfo> = appUpdateManager.getAppUpdateInfo()
        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo: AppUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            ) {
                startUpdateFlow(appUpdateInfo)
            } else if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                startUpdateFlow(appUpdateInfo)
            }
        }
    }

    private fun startUpdateFlow(appUpdateInfo: AppUpdateInfo) {
        try {
            appUpdateManager.startUpdateFlowForResult(
                appUpdateInfo,
                AppUpdateType.IMMEDIATE,
                activity,
                UPDATE_REQUEST_CODE
            )
        } catch (e: IntentSender.SendIntentException) {
            e.printStackTrace()
        }
    }
}