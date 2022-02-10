package com.salauddin_cse_se.appupdatecontroller

import android.app.Activity
import com.google.gson.GsonBuilder
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONException
import java.io.IOException

class UpdatePresenter(val updateView: UpdateView) {
    private val client: OkHttpClient = OkHttpClient()

    fun getAppVersion(
        tableName: String,
        appName: String,
        token: String,
        connString: String,
        baseUrl: String,
        activity: Activity,
        vCode: Int
    ) {
        updateView.showProgressBar()
        val query = "SELECT * FROM $tableName WHERE APP_NAME='$appName'"
        try {
            client.newCall(Common.getResponse(token, connString, baseUrl, query))
                .enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        updateView.onGetAppVersionUnsuccessfully(
                            ConstantString.NETWORK_ERROR,
                            false
                        )
                        updateView.hideProgressBar()
                    }
                    override fun onResponse(call: Call, response: Response) {
                        if (response.isSuccessful && response.body != null) {
                            try {
                                val table = JSONArray(response.body!!.string())
                                if (table.length() > 0) {
                                    val appVersion = GsonBuilder().create()
                                        .fromJson(table.get(0).toString(), AppVersion::class.java)
                                    updateView.onGetAppVersionSuccessfully(appVersion, vCode, activity)
                                } else {
                                    updateView.onGetAppVersionUnsuccessfully(
                                        ConstantString.VERSION_DATA_NOT_FOUND_ERROR,
                                        false
                                    )
                                }
                            } catch (e: JSONException) {
                                updateView.onGetAppVersionUnsuccessfully(
                                    ConstantString.JSON_PARSING_ERROR,
                                    false
                                )
                            }
                        }
                        updateView.hideProgressBar()
                    }
                })
        }
        catch (e: Exception) {
            updateView.onGetAppVersionUnsuccessfully(ConstantString.NETWORK_ERROR, false)
            updateView.hideProgressBar()
        }
    }
}