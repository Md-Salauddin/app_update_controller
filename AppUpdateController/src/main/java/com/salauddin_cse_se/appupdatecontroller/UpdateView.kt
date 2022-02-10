package com.salauddin_cse_se.appupdatecontroller

import android.app.Activity

interface UpdateView {
    fun onGetAppVersionSuccessfully(appVersion: AppVersion, vCode: Int, activity: Activity) {
        // if has available versions
        var isAppUpdateVersionAvailable = false
        // if vCodes has version
        if (appVersion.getVCodes().isNotEmpty()) {
            for (i in appVersion.getVCodes().indices) {
                if (appVersion.getVCodes()[i] == vCode) {
                    isAppUpdateVersionAvailable = true
                }
            }
        }

        activity.runOnUiThread {
            if (appVersion.vCodes != null) {
                // if not in maintain state
                if (appVersion.isInMaintainState == 0) {
                    // if version code not matched
                    if (appVersion.vCode != vCode) {
                        // if there is no available version
                        if (!isAppUpdateVersionAvailable) {
                            Common.showUpdateMessageDialog(
                                activity,
                                ConstantString.DT_UPDATE,
                                ConstantString.DM_APP_UPDATE,
                                false,
                                appVersion.updateLink
                            )
                        } else {
                            Common.showUpdateMessageDialog(
                                activity,
                                ConstantString.DT_UPDATE,
                                ConstantString.DM_APP_UPDATE,
                                true,
                                appVersion.updateLink
                            )
                        }
                    }
                } else {
                    Common.showWarningMessageDialog(
                        activity,
                        ConstantString.DT_MESSAGE,
                        ConstantString.DM_APP_IN_MAINTAIN_STATE,
                        false
                    )
                }
            } else {
                Common.showWarningMessageDialog(
                    activity,
                    ConstantString.DT_MESSAGE,
                    ConstantString.DM_APP_VERSION_NOT_FOUND,
                    false
                )
            }
        }
    }
    fun onGetAppVersionUnsuccessfully(message: String, state: Boolean)
    fun showProgressBar()
    fun hideProgressBar()
}