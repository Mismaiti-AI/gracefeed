package com.gracefeed

import androidx.compose.runtime.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.gracefeed.di.moduleList
import com.gracefeed.presentation.theme.AppTheme
import org.koin.compose.KoinApplication
import org.koin.dsl.KoinAppDeclaration

// Pre-built navigation from core/
import com.gracefeed.core.presentation.navigation.AppOrchestrator
import com.gracefeed.core.presentation.navigation.AppState
import com.gracefeed.core.presentation.navigation.NavigationTab

// App routes
import com.gracefeed.presentation.navigation.AppRoutes

// App screens (from Phase 6)
import com.gracefeed.presentation.dashboard.DashboardScreen
import com.gracefeed.presentation.bulletinfeed.BulletinListScreen
import com.gracefeed.presentation.bulletinfeed.BulletinDetailScreen
import com.gracefeed.presentation.serviceschedule.ServiceListScreen
import com.gracefeed.presentation.serviceschedule.ServiceDetailScreen
import com.gracefeed.presentation.eventcalendar.EventCalendarScreen
import com.gracefeed.presentation.eventcalendar.EventDetailScreen
import com.gracefeed.presentation.sermonarchive.SermonListScreen
import com.gracefeed.presentation.sermonarchive.SermonDetailScreen
import com.gracefeed.presentation.settings.SettingsScreen
import com.gracefeed.presentation.onboarding.OnboardingScreen
import com.gracefeed.core.presentation.screens.GenericSplashScreen

@Composable
fun App(koinAppDeclaration: KoinAppDeclaration? = null) {
    KoinApplication(application = {
        modules(moduleList())
        koinAppDeclaration?.invoke(this)
    }) {
        AppTheme {
            var appState by remember { mutableStateOf(AppState.Splash) }

            AppOrchestrator(
                appState = appState,
                splashContent = {
                    GenericSplashScreen(
                        appName = "GraceFeed",
                        icon = Icons.Default.Home,
                        onFinished = { 
                            appState = AppState.Onboarding
                        }
                    )
                },
                onboardingContent = {
                    OnboardingScreen {
                        appState = AppState.Home
                    }
                },
                tabs = listOf(
                    NavigationTab(AppRoutes.Dashboard, "Home", Icons.Default.Home),
                    NavigationTab(AppRoutes.Events, "Events", Icons.Default.List),
                    NavigationTab(AppRoutes.Sermons, "Sermons", Icons.Default.List),
                    NavigationTab(AppRoutes.Settings, "Settings", Icons.Default.Settings),
                ),
                homeStartDestination = AppRoutes.Dashboard,
                showTopBar = false,
                homeBuilder = { nav ->
                    // Tab destinations (bottom bar visible)
                    composable<AppRoutes.Dashboard> { DashboardScreen(nav) }
                    composable<AppRoutes.Bulletin> { BulletinListScreen(nav) }
                    composable<AppRoutes.Services> { ServiceListScreen(nav) }
                    composable<AppRoutes.Events> { EventCalendarScreen(nav) }
                    composable<AppRoutes.Sermons> { SermonListScreen(nav) }
                    composable<AppRoutes.Settings> { SettingsScreen(nav) }

                    // Detail destinations (bottom bar auto-hides)
                    composable<AppRoutes.BulletinDetail> { entry ->
                        val route = entry.toRoute<AppRoutes.BulletinDetail>()
                        BulletinDetailScreen(
                            bulletinId = route.id,
                            onBackClick = { nav.popBackStack() }
                        )
                    }
                    composable<AppRoutes.ServiceDetail> { entry ->
                        val route = entry.toRoute<AppRoutes.ServiceDetail>()
                        ServiceDetailScreen(
                            serviceId = route.id,
                            onBackClick = { nav.popBackStack() }
                        )
                    }
                    composable<AppRoutes.EventDetail> { entry ->
                        val route = entry.toRoute<AppRoutes.EventDetail>()
                        EventDetailScreen(
                            eventId = route.id,
                            onBackClick = { nav.popBackStack() }
                        )
                    }
                    composable<AppRoutes.SermonDetail> { entry ->
                        val route = entry.toRoute<AppRoutes.SermonDetail>()
                        SermonDetailScreen(
                            sermonId = route.id,
                            onBackClick = { nav.popBackStack() }
                        )
                    }
                }
            )
        }
    }
}