package com.egsi.labsi.sog.ioegje.presentation.di

import com.egsi.labsi.sog.ioegje.data.repo.EggLabelRepository
import com.egsi.labsi.sog.ioegje.data.shar.EggLabelSharedPreference
import com.egsi.labsi.sog.ioegje.data.utils.EggLabelPushToken
import com.egsi.labsi.sog.ioegje.data.utils.EggLabelSystemService
import com.egsi.labsi.sog.ioegje.domain.usecases.EggLabelGetAllUseCase
import com.egsi.labsi.sog.ioegje.presentation.pushhandler.EggLabelPushHandler
import com.egsi.labsi.sog.ioegje.presentation.ui.load.EggLabelLoadViewModel
import com.egsi.labsi.sog.ioegje.presentation.ui.view.EggLabelViFun
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val eggLabelModule = module {
    factory {
        EggLabelPushHandler()
    }
    single {
        EggLabelRepository()
    }
    single {
        EggLabelSharedPreference(get())
    }
    factory {
        EggLabelPushToken()
    }
    factory {
        EggLabelSystemService(get())
    }
    factory {
        EggLabelGetAllUseCase(
            get(), get(), get()
        )
    }
    factory {
        EggLabelViFun(get())
    }
    viewModel {
        EggLabelLoadViewModel(get(), get(), get())
    }
}