package com.gracefeed.presentation.onboarding

import androidx.compose.runtime.Composable
import com.gracefeed.core.presentation.screens.GenericOnboardingScreen
import com.gracefeed.core.presentation.screens.OnboardingPage
import com.gracefeed.core.data.local.AppSettings
import org.koin.compose.koinInject

@Composable
fun OnboardingScreen(
    onboardingCompleted: () -> Unit = {}
) {
    val appSettings = koinInject<AppSettings>()
    val pages = listOf(
        OnboardingPage(
            title = "Welcome to GraceFeed",
            description = "Your church community at your fingertips",
        ),
        OnboardingPage(
            title = "Stay Connected",
            description = "Access bulletins, events, and sermons anytime",
        ),
        OnboardingPage(
            title = "Join Us",
            description = "Find upcoming services and connect with our community",
        )
    )

    GenericOnboardingScreen(
        pages = pages,
        onFinish = onboardingCompleted,
        onSkip = onboardingCompleted,
        appSettings = appSettings,
        onboardingKey = "onboarding_completed"
    )
}