package com.gracefeed.di

import com.gracefeed.core.data.local.AppDatabase
import com.gracefeed.core.di.coreModule
import com.gracefeed.core.di.platformModule
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

// Import all repositories, use cases, and ViewModels
import com.gracefeed.data.repository.bulletin.BulletinRepository
import com.gracefeed.data.repository.bulletin.BulletinRepositoryImpl
import com.gracefeed.data.repository.service.ServiceRepository
import com.gracefeed.data.repository.service.ServiceRepositoryImpl
import com.gracefeed.data.repository.event.EventRepository
import com.gracefeed.data.repository.event.EventRepositoryImpl
import com.gracefeed.data.repository.sermon.SermonRepository
import com.gracefeed.data.repository.sermon.SermonRepositoryImpl
import com.gracefeed.data.repository.setting.SettingRepository
import com.gracefeed.data.repository.setting.SettingRepositoryImpl

import com.gracefeed.domain.usecase.GetBulletinListUseCase
import com.gracefeed.domain.usecase.ViewBulletinDetailsUseCase
import com.gracefeed.domain.usecase.GetServiceListUseCase
import com.gracefeed.domain.usecase.ViewServiceDetailsUseCase
import com.gracefeed.domain.usecase.GetEventListUseCase
import com.gracefeed.domain.usecase.ViewEventDetailsUseCase
import com.gracefeed.domain.usecase.GetSermonListUseCase
import com.gracefeed.domain.usecase.ViewSermonDetailsUseCase
import com.gracefeed.domain.usecase.GetSettingsUseCase
import com.gracefeed.domain.usecase.UpdateSettingUseCase
import com.gracefeed.domain.usecase.GetDashboardOverviewUseCase

import com.gracefeed.presentation.bulletinfeed.BulletinListViewModel
import com.gracefeed.presentation.bulletinfeed.BulletinDetailViewModel
import com.gracefeed.presentation.serviceschedule.ServiceListViewModel
import com.gracefeed.presentation.serviceschedule.ServiceDetailViewModel
import com.gracefeed.presentation.eventcalendar.EventCalendarViewModel
import com.gracefeed.presentation.eventcalendar.EventDetailViewModel
import com.gracefeed.presentation.sermonarchive.SermonListViewModel
import com.gracefeed.presentation.sermonarchive.SermonDetailViewModel
import com.gracefeed.presentation.settings.SettingsViewModel
import com.gracefeed.presentation.dashboard.DashboardViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.viewModel

fun moduleList(): List<Module> = listOf(
    platformModule(),
    coreModule(),
    appModule()
)

fun appModule() = module {
    // ⚠️ DO NOT register pre-built services here. They are in coreModule():
    //   Auth (AuthRepository, AuthViewModel)         → [auth] flag
    //   Firebase Auth (FirebaseAuthHandler)           → [firebase_auth] flag
    //   Google Sheets (GoogleSheetsConfig, Service)   → [google_sheets] flag
    //   Chat (AiChatService)                          → [chat] flag
    //   Deep linking (DeepLinkHandler)                → [deep_linking] flag (in platformModule)
    //   Push notifications (PushNotificationService)  → [push_notifications] flag (in platformModule)

    // [if firebase] FirestoreService is already registered in platformModule().
    // Repositories inject it via get<FirestoreService>() — no extra registration needed.
    // ⚠️ For firebase backend: do NOT register DAOs — no entity/DAO files exist.
    // Skip the DAO section entirely. Repository constructors take FirestoreService, not DAOs.

    // Repositories
    singleOf(::BulletinRepositoryImpl) { bind<BulletinRepository>() }
    singleOf(::ServiceRepositoryImpl) { bind<ServiceRepository>() }
    singleOf(::EventRepositoryImpl) { bind<EventRepository>() }
    singleOf(::SermonRepositoryImpl) { bind<SermonRepository>() }
    singleOf(::SettingRepositoryImpl) { bind<SettingRepository>() }

    // Use Cases
    factoryOf(::GetBulletinListUseCase)
    factoryOf(::ViewBulletinDetailsUseCase)
    factoryOf(::GetServiceListUseCase)
    factoryOf(::ViewServiceDetailsUseCase)
    factoryOf(::GetEventListUseCase)
    factoryOf(::ViewEventDetailsUseCase)
    factoryOf(::GetSermonListUseCase)
    factoryOf(::ViewSermonDetailsUseCase)
    factoryOf(::GetSettingsUseCase)
    factoryOf(::UpdateSettingUseCase)
    factoryOf(::GetDashboardOverviewUseCase)

    // ViewModels
    viewModelOf(::BulletinListViewModel)
    viewModelOf(::BulletinDetailViewModel)
    viewModelOf(::ServiceListViewModel)
    viewModelOf(::ServiceDetailViewModel)
    viewModelOf(::EventCalendarViewModel)
    viewModel { params -> EventDetailViewModel( get()) }
    viewModelOf(::SermonListViewModel)
    viewModelOf(::SermonDetailViewModel)
    viewModelOf(::SettingsViewModel)
    viewModelOf(::DashboardViewModel)
}