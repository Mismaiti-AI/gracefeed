# Implementation Plan

## Phase 1: Theme
Define app-specific theme colors and typography based on UI design specs

- [x] AppColors.kt — Define custom color palette using seedColor #8B0000 (burgundy) and navy blue (#000080) with dark mode support
- [x] AppTheme.kt — Implement AppTheme composable that wraps content with MaterialTheme using AppColors

## Phase 2: Feature: Bulletin Feed
All layers for weekly digital bulletin feature

- [x] Bulletin.kt — Domain model data class for Bulletin with Instant timestamps
- [x] BulletinRepository.kt — Interface for Bulletin repository operations
- [x] BulletinRepositoryImpl.kt — Firebase implementation of BulletinRepository using FirestoreService
- [x] GetBulletinListUseCase.kt — Use case to fetch list of bulletins from repository
- [x] ViewBulletinDetailsUseCase.kt — Use case to fetch single bulletin details by ID
- [x] BulletinListViewModel.kt — ViewModel for BulletinListScreen with UiState sealed interface
- [x] BulletinListScreen.kt — Screen wrapper calling GenericListScreen with ListItemCard itemContent
- [x] BulletinDetailViewModel.kt — ViewModel for BulletinDetailScreen with UiState sealed interface
- [x] BulletinDetailScreen.kt — Screen wrapper calling GenericDetailScreen with DetailCard detailContent

## Phase 3: Feature: Service Schedule
All layers for Sunday service schedule feature

- [x] Service.kt — Domain model data class for Service
- [x] ServiceRepository.kt — Interface for Service repository operations
- [x] ServiceRepositoryImpl.kt — Firebase implementation of ServiceRepository using FirestoreService
- [x] GetServiceListUseCase.kt — Use case to fetch list of services from repository
- [x] ViewServiceDetailsUseCase.kt — Use case to fetch single service details by ID
- [x] ServiceListViewModel.kt — ViewModel for ServiceListScreen with UiState sealed interface
- [x] ServiceListScreen.kt — Screen wrapper calling GenericListScreen with ListItemCard itemContent
- [x] ServiceDetailViewModel.kt — ViewModel for ServiceDetailScreen with UiState sealed interface
- [x] ServiceDetailScreen.kt — Screen wrapper calling GenericDetailScreen with DetailCard detailContent

## Phase 4: Feature: Event Calendar
All layers for church events calendar feature

- [x] Event.kt — Domain model data class for Event with Instant timestamps
- [x] EventRepository.kt — Interface for Event repository operations
- [x] EventRepositoryImpl.kt — Firebase implementation of EventRepository using FirestoreService
- [x] GetEventListUseCase.kt — Use case to fetch list of events from repository
- [x] ViewEventDetailsUseCase.kt — Use case to fetch single event details by ID
- [x] EventCalendarViewModel.kt — ViewModel for EventCalendarScreen with UiState sealed interface
- [x] EventCalendarScreen.kt — Screen wrapper calling GenericCalendarScreen with CalendarEventCard itemContent
- [x] EventDetailViewModel.kt — ViewModel for EventDetailScreen with UiState sealed interface
- [x] EventDetailScreen.kt — Screen wrapper calling GenericDetailScreen with DetailCard detailContent

## Phase 5: Feature: Sermon Archive
All layers for Sunday sermons archive feature

- [x] Sermon.kt — Domain model data class for Sermon with Instant timestamp
- [x] SermonRepository.kt — Interface for Sermon repository operations
- [x] SermonRepositoryImpl.kt — Firebase implementation of SermonRepository using FirestoreService
- [x] GetSermonListUseCase.kt — Use case to fetch list of sermons from repository
- [x] ViewSermonDetailsUseCase.kt — Use case to fetch single sermon details by ID
- [x] SermonListViewModel.kt — ViewModel for SermonListScreen with UiState sealed interface
- [x] SermonListScreen.kt — Screen wrapper calling GenericListScreen with VideoCard itemContent
- [x] SermonDetailViewModel.kt — ViewModel for SermonDetailScreen with UiState sealed interface
- [x] SermonDetailScreen.kt — Screen wrapper calling GenericMediaPlayerScreen with YouTube player

## Phase 6: Feature: Onboarding
Welcome walkthrough for first-time users

- [x] OnboardingScreen.kt — Screen wrapper calling GenericOnboardingScreen with 3 OnboardingPage items

## Phase 7: Feature: Settings
App preferences including dark mode toggle

- [x] AppSetting.kt — Domain model data class for AppSetting
- [x] SettingRepository.kt — Interface for Setting repository operations
- [x] SettingRepositoryImpl.kt — Implementation using AppSettings for local storage
- [x] GetSettingsUseCase.kt — Use case to fetch current app settings
- [x] UpdateSettingUseCase.kt — Use case to update app settings
- [x] SettingsViewModel.kt — ViewModel for SettingsScreen with UiState sealed interface
- [x] SettingsScreen.kt — Screen wrapper calling GenericSettingsScreen with showAppearanceSection=true

## Phase 8: Feature: Dashboard
Home dashboard aggregating key information from all features

- [x] GetDashboardOverviewUseCase.kt — Use case that aggregates data from Bulletin, Service, Event, and Sermon repositories
- [x] DashboardViewModel.kt — ViewModel for DashboardScreen combining flows from multiple repositories
- [x] DashboardScreen.kt — Screen wrapper calling GenericDashboardScreen with stats, quickActions, and recentItems

## Phase 9: App Wiring
Final wiring of navigation, routes, and dependency injection

- [x] AppRoutes.kt — @Serializable route objects for all screens including Dashboard, Bulletin, Service, Event, Sermon, Settings
- [x] [modify] AppModule.kt — Register project-specific repositories, use cases, and ViewModels in appModule(). Firebase services are pre-registered in coreModule().
- [x] [modify] App.kt — Wire AppOrchestrator with NavigationTab list for Dashboard, Bulletin, Services, Events, Sermons. Set homeStartDestination to Dashboard. Register all screen composables in homeBuilder.

## Cleanup: Unused Template Files
Files/directories to delete before code generation:

- composeApp/src/commonMain/kotlin/com.gracefeed/preview/
- composeApp/src/commonMain/kotlin/com.gracefeed/core/data/auth/
- composeApp/src/commonMain/kotlin/com.gracefeed/core/presentation/auth/
- composeApp/src/commonMain/kotlin/com.gracefeed/core/data/chat/
- composeApp/src/commonMain/kotlin/com.gracefeed/core/presentation/screens/GenericChatScreen.kt
- composeApp/src/commonMain/kotlin/com.gracefeed/core/data/deeplink/
- composeApp/src/commonMain/kotlin/com.gracefeed/core/data/notifications/
- composeApp/src/commonMain/kotlin/com.gracefeed/core/presentation/screens/GenericNotificationScreen.kt
- composeApp/src/commonMain/kotlin/com.gracefeed/core/data/gsheets/
- composeApp/src/commonMain/kotlin/com.gracefeed/core/data/media/
- composeApp/src/commonMain/kotlin/com.gracefeed/core/presentation/media/
- composeApp/src/commonMain/kotlin/com.gracefeed/core/presentation/screens/GenericGalleryScreen.kt
- composeApp/src/commonMain/kotlin/com.gracefeed/core/presentation/screens/GenericPaywallScreen.kt
- composeApp/src/commonMain/kotlin/com.gracefeed/core/presentation/screens/GenericSubscriptionScreen.kt
- composeApp/src/commonMain/kotlin/com.gracefeed/core/data/payment/
