package com.egsi.labsi.sog.ioegje.domain.usecases

import android.util.Log
import com.egsi.labsi.sog.ioegje.data.repo.EggLabelRepository
import com.egsi.labsi.sog.ioegje.data.utils.EggLabelPushToken
import com.egsi.labsi.sog.ioegje.data.utils.EggLabelSystemService
import com.egsi.labsi.sog.ioegje.domain.model.EggLabelEntity
import com.egsi.labsi.sog.ioegje.domain.model.EggLabelParam
import com.egsi.labsi.sog.ioegje.presentation.app.EggLabelApplication

class EggLabelGetAllUseCase(
    private val eggLabelRepository: EggLabelRepository,
    private val eggLabelSystemService: EggLabelSystemService,
    private val eggLabelPushToken: EggLabelPushToken,
) {
    suspend operator fun invoke(conversion: MutableMap<String, Any>?) : EggLabelEntity?{
        val params = EggLabelParam(
            eggLabelLocale = eggLabelSystemService.eggLabelGetLocale(),
            eggLabelPushToken = eggLabelPushToken.eggLabelGetToken(),
            eggLabelAfId = eggLabelSystemService.eggLabelGetAppsflyerId()
        )
        Log.d(EggLabelApplication.EGG_LABEL_MAIN_TAG, "Params for request: $params")
        return eggLabelRepository.eggLabelGetClient(params, conversion)
    }



}