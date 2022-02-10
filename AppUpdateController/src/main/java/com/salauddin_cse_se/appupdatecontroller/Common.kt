package com.salauddin_cse_se.appupdatecontroller

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import okhttp3.Request

class Common {
    companion object {
        // network call
        fun getResponse(
            token: String,
            connString: String,
            baseUrl: String,
            query: String
        ): Request {
            return Request.Builder()
                .url(baseUrl + query)
                .addHeader("tokenNo", token)
                .addHeader("conStr", connString)
                .build()
        }

        fun showWarningMessageDialog(
            activity: Activity?,
            title: String?,
            message: String?,
            isCancelable: Boolean
        ) {
            if (isCancelable) {
                MaterialAlertDialogBuilder(activity!!)
                    .setTitle(title)
                    .setMessage(message)
                    .setCancelable(true)
                    .setNegativeButton(
                        "OK"
                    ) { _: DialogInterface?, _: Int -> }
                    .show()
            } else {
                MaterialAlertDialogBuilder(activity!!)
                    .setTitle(title)
                    .setMessage(message)
                    .setCancelable(false)
                    .setNegativeButton(
                        "EXIT"
                    ) { _: DialogInterface?, _: Int -> activity.finish() }
                    .show()
            }
        }

        fun showUpdateMessageDialog(
            activity: Activity?,
            title: String?,
            message: String?,
            isCancelable: Boolean,
            updateLink: String?
        ) {
            if (isCancelable) {
                MaterialAlertDialogBuilder(activity!!)
                    .setTitle(title)
                    .setMessage(message)
                    .setCancelable(true)
                    .setPositiveButton(
                        "UPDATE"
                    ) { _: DialogInterface?, _: Int ->
                        if (updateLink != null) {
                            gotoUpdateLink(updateLink, activity)
                            activity.finish()
                        }
                    }
                    .setNegativeButton(
                        "LATER"
                    ) { _: DialogInterface?, _: Int -> }
                    .show()
            } else {
                MaterialAlertDialogBuilder(activity!!)
                    .setTitle(title)
                    .setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton(
                        "UPDATE"
                    ) { _: DialogInterface?, _: Int ->
                        if (updateLink != null) {
                            gotoUpdateLink(updateLink, activity)
                            activity.finish()
                        }
                    }
                    .setNegativeButton(
                        "EXIT"
                    ) { _: DialogInterface?, _: Int -> activity.finish() }
                    .show()
            }
        }

        private fun gotoUpdateLink(updateLink: String, activity: Activity) {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(updateLink))
            activity.startActivity(browserIntent)
        }

    }
}