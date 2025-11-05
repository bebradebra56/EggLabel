package com.egsi.labsi.sog.ioegje.domain.model

import com.google.gson.annotations.SerializedName


private const val EGG_LABEL_A = "com.egsi.labsi.sog"
private const val EGG_LABEL_B = "egglabel-732cd"
data class EggLabelParam (
    @SerializedName("af_id")
    val eggLabelAfId: String,
    @SerializedName("bundle_id")
    val eggLabelBundleId: String = EGG_LABEL_A,
    @SerializedName("os")
    val eggLabelOs: String = "Android",
    @SerializedName("store_id")
    val eggLabelStoreId: String = EGG_LABEL_A,
    @SerializedName("locale")
    val eggLabelLocale: String,
    @SerializedName("push_token")
    val eggLabelPushToken: String,
    @SerializedName("firebase_project_id")
    val eggLabelFirebaseProjectId: String = EGG_LABEL_B,

    )