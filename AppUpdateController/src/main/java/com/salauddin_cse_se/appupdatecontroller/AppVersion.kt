package com.salauddin_cse_se.appupdatecontroller

import com.google.gson.Gson
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

class AppVersion {
    @SerializedName("APP_NAME") @Expose var appName: String? = null
    @SerializedName("VERSION_NAME") @Expose var vName: String? = null
    @SerializedName("VERSION_CODE") @Expose var vCode = 0
    @SerializedName("VERSION_CODE_AVAIL") @Expose var vCodes: String? = null
    @SerializedName("IS_MAINTAIN") @Expose var isInMaintainState = 0
    @SerializedName("UPDATE_LINK") @Expose var updateLink: String? = null

    fun getVCodes(): List<Int> {
        return Gson().fromJson(vCodes, object : TypeToken<List<Int>>() {}.type)
    }
}