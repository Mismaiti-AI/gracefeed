package com.gracefeed

import androidx.compose.runtime.*
import com.gracefeed.di.moduleList
import com.gracefeed.presentation.theme.AppTheme
import com.gracefeed.preview.CommunityMockup
import org.koin.compose.KoinApplication
import org.koin.dsl.KoinAppDeclaration


@Composable
fun App(koinAppDeclaration: KoinAppDeclaration? = null) {
    KoinApplication(application = {
        modules(moduleList())
        koinAppDeclaration?.invoke(this)
    }) {
        AppTheme {
            CommunityMockup()
        }
    }
}
