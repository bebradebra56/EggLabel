package com.egsi.labsi.sog.ioegje.domain.model

import com.google.gson.annotations.SerializedName


data class EggLabelEntity (
    @SerializedName("ok")
    val eggLabelOk: String,
    @SerializedName("url")
    val eggLabelUrl: String,
    @SerializedName("expires")
    val eggLabelExpires: Long,
)