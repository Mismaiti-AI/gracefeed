# Core Module Reference

> Pre-built components and services in `composeApp/src/commonMain/kotlin/com/mismaiti/core/`
> with platform implementations in `androidMain` and `iosMain`.

---

## App Types You Can Build

The core module is a **general-purpose mobile app toolkit**. Below are the app categories it can assemble, grouped by how well the pre-built components cover them.

### Fully Covered (90%+ out of the box)

These apps can be built almost entirely from existing screens and components — code-gen only wires domain models and data sources.

| App Type | Core Components Used | Example Apps |
|----------|---------------------|--------------|
| **Community / Organization App** | Dashboard + List + Detail + Profile + Notifications + Auth + Calendar + Chat | Mosque app, church app, club hub, alumni network, HOA portal |
| **Event & Booking App** | List + Detail + Form + Search + Calendar + Notifications + Paywall | Event finder, conference app, venue booking, class scheduler |
| **Directory / Catalog App** | List + Search + Detail + Filter chips + Profile + Map | Business directory, staff directory, restaurant finder, service marketplace |
| **News / Content App** | List + Detail + Carousel + ImageCard + Search + Notifications | News reader, blog app, magazine, announcement board |
| **Education / Course App** | Dashboard + List + Detail + ProgressCard + Charts + Form + Notifications | Course catalog, student portal, training tracker, quiz app |
| **Portfolio / Showcase App** | Gallery + Detail + Profile + Carousel + ImageCard + VideoCard | Photography portfolio, art showcase, product catalog, portfolio CV |
| **Settings / Admin Panel** | Settings + Form + List + Detail + SwitchRow + StatCard + Charts | Config manager, device settings, admin dashboard |
| **Fitness / Health Tracker** | Dashboard + StatCard + ProgressCard + Charts + Calendar + List + Form + CounterRow | Workout tracker, habit tracker, meal planner, step counter |
| **Task / Project Management** | Dashboard + List + Detail + Form + StatusBadge + TimelineItem + Calendar + ChipGroup | To-do app, project tracker, issue tracker, sprint board |
| **Finance / Banking** | Auth, Dashboard + StatCard + Charts + List + Detail + Notifications + Subscription | Banking app, expense tracker, investment portfolio, budget planner |
| **Inventory / Asset Management** | Dashboard + List + Detail + Form + Search + StatusBadge + StatCard + Charts | Warehouse tracker, equipment log, library catalog |

### Well Covered (70-90% — needs some custom UI)

These need a few custom components beyond what's pre-built, but the core handles structure, auth, and most screens.

| App Type | Core Components Used | What to Add | Example Apps |
|----------|---------------------|-------------|--------------|
| **Travel / Tourism App** | List + Detail + Map + Gallery + Carousel + ImageCard + Search + Calendar + RatingBar | Itinerary builder | City guide, hotel finder, travel planner, tourism board |
| **Real Estate / Property App** | List + Detail + Map + Gallery + ImageCard + Search + Filter + Form + Calendar | Price range slider, mortgage calculator | Property listing, apartment finder, rental marketplace |
| **Recipe / Food App** | List + Detail + Search + ImageCard + RatingBar + CounterRow + Form | Ingredient checklist, cooking timer | Recipe book, meal planner, restaurant reviews |
| **Social / Forum App** | List + Detail + Profile + Chat + Messaging + Gallery + ImageCard + Notifications | Feed composer, reactions | Community forum, Q&A board, interest groups |
| **Marketplace / Classifieds** | List + Detail + Search + ImageCard + Form + Profile + Chat + Map + Paywall | Cart UI | Buy/sell marketplace, classified ads, service listings |
| **E-Commerce** | Auth + List + Detail + Search + Profile + ImageCard + Form + Paywall + Notifications | Cart, checkout flow, order tracking | Online store, fashion app, grocery delivery |
| **Chat / Messaging App** | Auth + Chat + Messaging (RTDB) + ConversationList + MessageBubble + TypingIndicator + Notifications | Voice/video calls, group management | Messaging app, team chat, customer support |
| **SaaS / Subscription App** | Auth + Dashboard + Charts + Paywall + Subscription + Settings + Notifications | App-specific features | Premium content app, productivity tool, SaaS mobile client |
| **Gaming Companion** | Auth + Dashboard + Profile + List + Detail + StatCard + Charts + Chat | Game-specific widgets, leaderboards | Game stats tracker, guild manager, esports companion |

### Partially Covered (50-70% — needs significant custom work)

The core provides auth, navigation, and base components, but the primary app experience requires custom-built features.

| App Type | What Core Provides | What to Build Custom | Example Apps |
|----------|-------------------|---------------------|--------------|
| **Streaming / Media** | Auth, VideoCard, MediaPlayer, Gallery, List, Search, Profile, Subscription | Streaming infrastructure, playlists, offline downloads, DRM | Video platform, podcast app, music player |
| **Ride-Sharing / Delivery** | Auth, Map, Form, Profile, Notifications, StatusBadge, TimelineItem, Chat, Payment | Real-time tracking, routing, driver matching, ETA | Ride-hailing, food delivery, courier service |

### Not a Good Fit

These require specialized engines that fall outside a general-purpose UI toolkit:

| App Type | Why |
|----------|-----|
| **Camera / Photo Editor** | Needs native image processing pipeline, filters, layers |
| **AR / VR App** | Needs platform-specific AR frameworks (ARCore, ARKit) |
| **IoT / Hardware Control** | Needs Bluetooth/BLE, device protocols, hardware SDKs |
| **Full Game** | Needs game engine (Unity, Godot), physics, rendering |
| **CAD / Design Tool** | Needs custom canvas, vector graphics engine |

---

### How Components Map to App Features

This table shows which core component serves which common app feature:

| App Feature | Primary Component | Supporting Components |
|-------------|-------------------|----------------------|
| Home / Dashboard | `GenericDashboardScreen` | `StatCard`, `Carousel`, `SectionHeader` |
| Item Browsing | `GenericListScreen` | `ListItemCard`, `ImageCard`, `SearchBar` |
| Item Details | `GenericDetailScreen` | `DetailCard`, `DetailRow`, `InfoRow`, `StatusBadge` |
| Search & Filter | `GenericSearchScreen` | `ChipGroup`, `SearchBar`, `EmptyStateContent` |
| User Profile | `GenericProfileScreen` | `StatCard`, `ListItemCard`, `SectionHeader` |
| App Settings | `GenericSettingsScreen` | `SwitchRow`, `ListItemCard` |
| Data Entry | `GenericFormScreen` | `LoadingButton`, `AppDatePickerDialog`, `ConfirmDialog` |
| First-Run Experience | `GenericOnboardingScreen` | `GenericSplashScreen` |
| Login / Signup | `GenericAuthScreen` | Social sign-in (Google/Apple) |
| Media Upload | `CameraLauncher`, `MediaPickerLauncher` | `MediaPreview`, `CaptureButton` |
| Content Gallery | `GenericGalleryScreen` | `ImageCard`, `VideoCard` |
| Notifications | `GenericNotificationScreen` | `PushNotificationService` |
| Chat / Support | `GenericChatScreen` | `AiChatService` |
| Real-Time Messaging | `GenericConversationListScreen` | `MessageBubble`, `MessageInputBar`, `TypingIndicator`, `ConversationListItem` |
| Map / Location | `GenericMapScreen` | `PlatformMapView`, `MapMarker` |
| Video Playback | `GenericMediaPlayerScreen` | `VideoCard`, `PlatformVideoPlayer` |
| Progress Tracking | `ProgressCard`, `StatCard` | `TimelineItem`, `StatusBadge` |
| Reviews / Ratings | `RatingBar` | `ListItemCard`, `DetailCard` |
| Charts & Analytics | `GenericChartScreen` | `BarChart`, `LineChart`, `PieChart`, `ChartCard` |
| Calendar / Scheduling | `GenericCalendarScreen` | `CalendarGrid`, `WeekStrip`, `DaySchedule`, `AgendaList`, `CalendarEventCard` |
| Payments / Subscriptions | `GenericPaywallScreen`, `GenericSubscriptionScreen` | `PaymentProductCard`, `SubscriptionBadge`, `PaymentSummaryCard` |

## What the Core Produces

The core module is a **complete app starter kit** for Compose Multiplatform. Code-gen only needs to provide domain models, repositories, use cases, ViewModels, and screen wiring — the core handles everything else.

A generated app gets **out of the box**:

- Phase-based app shell (Splash → Onboarding → Auth → Home)
- Social authentication (Google + Apple Sign-In)
- 20+ generic screen templates (dashboard, list, detail, form, search, profile, settings, chat, calendar, paywall, subscription, charts, etc.)
- 40+ reusable UI components (cards, buttons, badges, carousels, charts, calendar views, payment cards, message bubbles, etc.)
- Local database (Room) with user management
- Type-safe navigation with bottom bar scaffold
- Media capture & picking (camera, gallery, files)
- Deep linking infrastructure
- Push notification interface (FCM)
- Firestore real-time database interface
- Firebase Realtime Database messaging interface
- Google Sheets as CMS (read-only CSV parsing)
- AI chat service interface
- In-app purchases & subscriptions (RevenueCat)
- Charts & graphs (Canvas-based: bar, line, pie, donut, area)
- Calendar views (month grid, week strip, day schedule, agenda)

---

## Architecture Overview

```
core/
├── di/                     # Dependency injection (Koin)
├── data/                   # Database, settings, entities
├── presentation/
│   ├── auth/               # Auth screens & ViewModel
│   ├── components/         # Reusable UI components (40+)
│   ├── screens/            # Generic screen templates (20+)
│   └── navigation/         # Routes, scaffold, orchestrator
├── services/               # Backend integrations
│   ├── ai/                 # AI chat interface
│   ├── auth/               # Social auth providers
│   ├── firebase/           # Firestore + Firebase Auth
│   ├── googlesheets/       # Google Sheets CSV
│   ├── media/              # Camera, picker, player
│   └── notifications/      # Push notifications (FCM)
└── util/                   # Deep linking, models
```

---

## 1. App Shell & Navigation

### AppOrchestrator

**File**: `presentation/navigation/AppOrchestrator.kt`

Top-level state machine that controls the app lifecycle phases:

| Phase | Purpose | Auto-transitions |
|-------|---------|-----------------|
| `Splash` | Animated launch screen | After delay → Onboarding or Home |
| `Onboarding` | First-run walkthrough | After completion → Auth or Home |
| `Auth` | Login/signup | After sign-in → Home |
| `Home` | Main app content | — |

Each phase receives a content lambda. Deep links arriving during Splash/Auth are deferred and consumed when Home activates.

### AppNavigation (Routes + Scaffold)

**File**: `presentation/navigation/AppNavigation.kt`

Pre-defined `@Serializable` routes:

```kotlin
Routes.Home, Routes.Search, Routes.Profile       // Tab routes
Routes.Detail(itemId), Routes.Edit(itemId)        // Detail routes
Routes.Create, Routes.Settings                    // Action routes
Routes.Login, Routes.Signup, Routes.ForgotPassword // Auth routes
```

`MainScaffold()` provides:
- Bottom navigation bar (auto-hides on non-tab routes)
- TopAppBar with back navigation
- Deep link handler integration
- `NavigationTab` data class for tab configuration

---

## 2. Base Classes

### BaseViewModel

**File**: `presentation/BaseViewModel.kt`

Every generated ViewModel extends this. Provides:

```kotlin
abstract class BaseViewModel : ViewModel() {
    val isLoading: StateFlow<Boolean>   // Shared loading state
    val error: StateFlow<String?>       // Shared error state

    fun safeLaunch(block: suspend () -> Unit)  // Auto error/loading handling
    fun clearError()
}
```

The THIN ViewModel pattern uses `combine(useCase(), isLoading, error)` to derive UI state without manual state copying.

---

## 3. Authentication

### Social Sign-In Flow

```
User taps "Sign in with Google/Apple"
  → Platform native prompt (expect/actual)
  → SocialAuthResult (id, name, email, idToken, provider)
  → AuthRepository.signInWithSocial()
      → Optional: FirebaseAuthHandler verifies token
      → Saves UserEntity to Room
      → Returns SocialSignInResult.Success(user)
  → AuthViewModel updates AuthUiState
```

**Key files**:

| File | Purpose |
|------|---------|
| `services/auth/SocialAuthProvider.kt` | `expect` — platform native sign-in |
| `services/auth/SocialAuthResult.kt` | Native result: id, name, email, idToken, provider |
| `services/auth/AuthRepository.kt` | Orchestrates sign-in + persistence |
| `services/firebase/FirebaseAuthHandler.kt` | Firebase token verification (optional) |
| `presentation/auth/AuthViewModel.kt` | UI state management |
| `presentation/auth/AuthScreenContent.kt` | Composable wrapper |

**Two modes**:
1. **Local-only** (prototype): Skip backend verification, save user directly to Room
2. **With Firebase**: Verify ID token via `FirebaseAuthHandler`, get Firebase UID

**Guest → Registered migration**: `FirebaseAuthHandler` uses `linkWithCredential()` to preserve guest UID and Firestore data during sign-up.

### AuthViewModel

```kotlin
class AuthViewModel(authRepository: AuthRepository) : BaseViewModel()

// States
sealed interface AuthUiState { Idle, Loading, Success(user), Error(message) }
data class AuthFormState(email, password, name, confirmPassword, isLogin)

// Actions
signInWithSocial()
signInWithEmail()  // Stub for email/password
logout()
```

---

## 4. Local Database (Room)

### AppDatabase

**File**: `data/AppDatabase.kt`

```kotlin
@Database(entities = [UserEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
```

Generated apps add their own entities and DAOs, then register them in Koin:

```kotlin
single { get<AppDatabase>().eventDao() }
single { get<AppDatabase>().userDao() }
```

### Pre-built Entities

| Entity | Table | Fields |
|--------|-------|--------|
| `UserEntity` | `users` | id (auto), name, email, authProvider, providerUserId |

### AppSettings

**File**: `data/AppSettings.kt`

Platform-abstracted key-value storage (SharedPreferences on Android, NSUserDefaults on iOS):

```kotlin
interface AppSettings {
    fun getString(key: String): String?
    fun putString(key: String, value: String)
    fun getBoolean(key: String): Boolean
    fun putBoolean(key: String, value: Boolean)
    fun getInt(key: String): Int
    fun putInt(key: String, value: Int)
    fun remove(key: String)
}
```

---

## 5. Backend Services

### Firestore

**File**: `services/firebase/FirestoreService.kt`

Cross-platform Firestore interface:

```kotlin
interface FirestoreService {
    // Real-time
    fun observeCollection(path): Flow<List<Map<String, Any?>>>
    fun observeDocument(path, id): Flow<Map<String, Any?>?>

    // One-shot
    suspend fun getCollection(path): List<Map<String, Any?>>
    suspend fun getDocument(path, id): Map<String, Any?>?

    // Write
    suspend fun addDocument(path, data): String
    suspend fun setDocument(path, id, data)
    suspend fun updateDocument(path, id, fields)
    suspend fun deleteDocument(path, id)

    // Query
    suspend fun queryCollection(path, field, op, value): List<Map<String, Any?>>
    // Operators: "==", "!=", "<", "<=", ">", ">=", "array-contains"
}
```

### Google Sheets (Read-Only CMS)

**Files**: `services/googlesheets/GoogleSheetsService.kt`, `GoogleSheetsConfig.kt`

Use a published Google Sheet as a lightweight CMS:

```kotlin
class GoogleSheetsService(httpClient: HttpClient) {
    suspend fun fetchSheetCsv(url, sheetName?): String  // Any URL format, multi-tab
    fun parseCsv(content): List<Map<String, String>>     // Quoted values, escapes
    fun parseDateToEpochMillis(dateString): Long?        // Multi-format dates
}

class GoogleSheetsConfig(settings: AppSettings) {
    val sheetUrl: String          // Stored or default
    val isConfigured: Boolean
    fun setSheetUrl(url: String)
}
```

### AI Chat

**File**: `services/ai/AiChatService.kt`

Pluggable AI provider interface:

```kotlin
interface AiChatService {
    suspend fun sendMessage(message, conversationId?): AiChatResponse
    fun sendMessageStream(message, conversationId?): Flow<AiChatResponse>
    suspend fun clearConversation(conversationId)
}
```

### Push Notifications

**File**: `services/notifications/PushNotificationService.kt`

FCM abstraction:

```kotlin
interface PushNotificationService {
    suspend fun requestPermission(): Boolean
    suspend fun getFcmToken(): String?
    val incomingMessages: Flow<PushMessage>
}
```

---

## 6. Media & Files

### Camera

**File**: `services/media/CameraLauncher.kt`

```kotlin
@Composable
fun rememberCameraLauncher(
    mode: CameraCaptureMode,        // PHOTO, VIDEO, PHOTO_AND_VIDEO
    onResult: (MediaResult?) -> Unit
): CameraLauncher

interface CameraLauncher {
    fun launch()
}
```

Auto-requests permissions on Android. iOS uses `NSCameraUsageDescription`.

### Media Picker

**File**: `services/media/MediaPickerLauncher.kt`

```kotlin
@Composable
fun rememberMediaPickerLauncher(
    type: MediaPickerType,          // IMAGE, VIDEO, IMAGE_AND_VIDEO, FILE
    onResult: (MediaResult?) -> Unit
): MediaPickerLauncher
```

Opens `PickVisualMedia` on Android, `PHPickerViewController` on iOS.

### MediaResult

```kotlin
data class MediaResult(
    val bytes: ByteArray,
    val fileName: String,
    val mimeType: String,
    val size: Long,
    val thumbnailBytes: ByteArray?  // Video thumbnail (JPEG)
)
```

### Other Media

| File | Purpose |
|------|---------|
| `DecodeImage.kt` | `expect fun decodeByteArrayToImageBitmap(bytes)` |
| `PlatformVideoPlayer.kt` | `expect @Composable fun PlatformVideoPlayer(source)` |
| `PlatformMapView.kt` | `expect @Composable fun PlatformMapView(markers, center)` |
| `PlayerSource.kt` | `sealed class: Url(url), LocalFile(path)` |
| `MapModels.kt` | `LatLng`, `MapMarker` data classes |

---

## 7. Deep Linking

**Files**: `util/DeepLinkHandler.kt`, `util/DefaultDeepLinkHandler.kt`

Deferred URI consumption pattern:

```kotlin
interface DeepLinkHandler {
    val pendingDeepLink: StateFlow<String?>
    fun handleIncomingUri(uri: String)
    fun consumeDeepLink()
}
```

URIs arriving during Splash/Auth are stored and consumed once the Home phase activates.

---

## 8. Generic Screen Templates

All screens are `@Composable`, support generics (`<T>`), and include loading, empty, and error states.

### Navigation & Lifecycle Screens

| Screen | Key Props | What It Renders |
|--------|-----------|-----------------|
| `GenericSplashScreen` | appName, icon, tagline | Animated logo + loading indicator, auto-navigates after delay |
| `GenericOnboardingScreen` | pages: `List<OnboardingPage>` | HorizontalPager with dots, skip/next/done buttons |
| `GenericAuthScreen` | onSocialSignIn, onEmailSignIn | Email/password form, social buttons, login/signup toggle |

### Content Screens

| Screen | Key Props | What It Renders |
|--------|-----------|-----------------|
| `GenericDashboardScreen` | stats, quickActions, recentItems | Stats grid (scrollable if 5+), action buttons (max 2), recent list |
| `GenericListScreen<T>` | items, itemContent, onItemClick | Scrollable list + FAB + search + filter buttons + empty state |
| `GenericDetailScreen<T>` | item, headerContent, bodyContent | Full detail view with edit/delete menu actions |
| `GenericFormScreen` | fields: `List<FormField>`, onSubmit | Multi-field form with validation (text, dropdown, checkbox, radio, date) |
| `GenericSearchScreen<T>` | onSearch, filterChips, resultContent | Search input + filter chips + recent searches + results |
| `GenericProfileScreen` | avatar, stats, menuSections | Avatar + stats row + grouped menu items |
| `GenericSettingsScreen` | sections: `List<SettingsSection>` | Settings groups with toggles, navigation rows, info rows + dark mode |

### Specialized Screens

| Screen | Purpose |
|--------|---------|
| `GenericChatScreen` | AI chat UI with message bubbles |
| `GenericGalleryScreen` | Photo/video grid gallery |
| `GenericMapScreen` | Map display with markers |
| `GenericMediaPlayerScreen` | Video/audio playback |
| `GenericNotificationScreen` | Notification list with read/unread |
| `GenericTabScreen` | Custom tab layout container |

---

## 9. Reusable UI Components

### Cards & Content

| Component | Purpose |
|-----------|---------|
| `ListItemCard` | Icon/avatar + text + trailing content + chevron |
| `ImageCard` / `HorizontalImageCard` | Full-width or thumbnail image card with overlay badge |
| `VideoCard` | Thumbnail + play overlay + duration + metadata |
| `DetailCard` + `DetailRow` | Grouped info section with header and labeled rows |
| `StatCard` | Icon + value + label in compact card |
| `ExpandableCard` | Collapsible card with toggle |
| `ProgressCard` | Progress bar visualization |

### Navigation & Layout

| Component | Purpose |
|-----------|---------|
| `SearchBar` | TopAppBar with inline search TextField |
| `SectionHeader` | Title + optional "See All" action button |
| `Carousel` + `CarouselItem` | Auto-scrolling HorizontalPager with page indicators |

### Actions & Inputs

| Component | Purpose |
|-----------|---------|
| `LoadingButton` / `SecondaryButton` | Button with loading spinner, outlined variant |
| `ChipGroup` | Multi-select filter chips |
| `RatingBar` | Star rating input |
| `CounterRow` | +/- counter with label |
| `SwitchRow` | Toggle switch with label |
| `CaptureButton` | Camera/record trigger button |
| `AppDatePickerDialog` | Date picker dialog |
| `ConfirmDialog` | Confirmation alert dialog |

### Data Display

| Component | Purpose |
|-----------|---------|
| `InfoRow` | Label + value + optional icon |
| `StatusBadge` | Colored badge for status/tags |
| `EmptyStateContent` | Icon + message for empty lists |
| `TimelineItem` | Timeline event with connector line |
| `MediaPreview` | Image/video preview thumbnail |

---

## 10. Platform Implementations (Android & iOS)

Every `expect` declaration in `commonMain` has `actual` implementations on both platforms. These are **production-ready** with proper error handling, permission management, and resource cleanup.

### Android (`androidMain/kotlin/com/mismaiti/core/`)

| File | What It Provides | Native APIs Used |
|------|-----------------|------------------|
| **SocialAuthProvider.android.kt** | Google Sign-In via Credential Manager | `CredentialManager`, `GetSignInWithGoogleOption` (requires Web Client ID from Google Cloud Console) |
| **AndroidPushNotificationService.kt** | FCM token management + push message relay | `FirebaseMessaging`, `POST_NOTIFICATIONS` permission (Android 13+), notification channel creation |
| **MismaitiFcmService.kt** | Firebase Cloud Messaging receiver service | `FirebaseMessagingService` — must be declared in AndroidManifest.xml |
| **CameraLauncher.android.kt** | Photo/video capture with EXIF + thumbnails | `ActivityResultContracts.TakePicture/CaptureVideo`, `FileProvider`, `MediaMetadataRetriever` for video thumbnails |
| **DecodeImage.android.kt** | Byte array to ImageBitmap with EXIF rotation | `BitmapFactory`, `ExifInterface` — handles 90/180/270 rotation |
| **MediaPickerLauncher.android.kt** | Image/video/file picker with thumbnail gen | `PickVisualMedia`, `OpenDocument`, `PdfRenderer` for PDF thumbnails |
| **PlatformMapView.android.kt** | Google Maps integration | `GoogleMap` (maps-compose library), `MKPointAnnotation`, camera animation |
| **PlatformVideoPlayer.android.kt** | Video playback with progress tracking | `ExoPlayer` + `PlayerView` (androidx.media3), 500ms progress polling |
| **AndroidAppSettings.kt** | Key-value storage | `SharedPreferences` ("app_settings" file) |
| **AndroidFirestoreService.kt** | Full Firestore CRUD + real-time listeners | `FirebaseFirestore` SDK directly — real-time `addSnapshotListener`, query operators |
| **PlatformModule.android.kt** | Koin DI registrations | Room via `BundledSQLiteDriver`, `OkHttp` engine, feature-toggled services |

### iOS (`iosMain/kotlin/com/mismaiti/core/`)

| File | What It Provides | Native APIs Used |
|------|-----------------|------------------|
| **SocialAuthProvider.ios.kt** | Apple Sign-In with nonce generation | `ASAuthorizationAppleIDProvider`, `ASAuthorizationController`, `SecRandomCopyBytes` for nonce, `CC_SHA256` for hash |
| **IosPushNotificationService.kt** | Push permissions + token management | `UNUserNotificationCenter` — Swift AppDelegate must call companion methods to relay token and messages |
| **DeepLinkBridge.kt** | Swift-to-Kotlin deep link relay | Called from Swift `application(_:open:options:)`, gets `DeepLinkHandler` from Koin |
| **CameraLauncher.ios.kt** | Photo/video capture with thumbnails | `UIImagePickerController`, `AVAssetImageGenerator` for video thumbnails, strong delegate reference to prevent GC |
| **DecodeImage.ios.kt** | Byte array to ImageBitmap | `SkiaImage.makeFromEncoded().toComposeImageBitmap()` — simpler than Android (no EXIF needed) |
| **MediaPickerLauncher.ios.kt** | Image/video/file picker + PDF thumbnails | `UIImagePickerController`, `UIDocumentPickerViewController`, `PDFDocument` (PDFKit) for PDF thumbnails, security scoped resources |
| **PlatformMapView.ios.kt** | Apple Maps integration | `MKMapView` via `UIKitView`, `MKPointAnnotation`, custom zoom via `pow2()` with Taylor series |
| **PlatformVideoPlayer.ios.kt** | Video playback with progress tracking | `AVPlayer` + `AVPlayerViewController`, `addPeriodicTimeObserverForInterval` (0.5s) |
| **IosAppSettings.kt** | Key-value storage | `NSUserDefaults.standardUserDefaults` |
| **IosFirestoreService.kt** | Factory bridge to Swift Firestore | `registerFirestoreServiceFactory()` — actual Firestore logic lives in Swift (`FirestoreService.swift`) |
| **PlatformModule.ios.kt** | Koin DI registrations | Room via Documents directory, `Darwin` HTTP engine, feature-toggled services |

### Platform Architecture Patterns

**Expect/Actual**: Core interfaces declared in `commonMain`, platform code in `androidMain`/`iosMain`

```
commonMain (expect)          androidMain (actual)         iosMain (actual)
─────────────────           ──────────────────           ─────────────────
signInWithSocialProvider()  → Credential Manager          → Apple Sign-In (ASAuthorization)
rememberCameraLauncher()    → ActivityResultContracts     → UIImagePickerController
rememberMediaPickerLauncher → PickVisualMedia / OpenDoc   → UIImagePicker / UIDocumentPicker
decodeByteArrayToImageBitmap→ BitmapFactory + EXIF        → Skia
PlatformMapView()           → Google Maps Compose         → MKMapView
PlatformVideoPlayer()       → ExoPlayer (Media3)          → AVPlayer
platformModule()            → OkHttp + SharedPrefs        → Darwin + NSUserDefaults
```

**Push Notification Wiring**:
- Android: `MismaitiFcmService` → `AndroidPushNotificationService.instance.emitMessage()`
- iOS: Swift AppDelegate → `IosPushNotificationService.Companion.onMessageReceived()`

**Firestore Strategy**:
- Android: Direct `FirebaseFirestore` SDK usage in Kotlin
- iOS: Factory pattern — Swift registers implementation, Kotlin calls via `getFirestoreService()`

**HTTP Engines**:
- Android: `OkHttp` (preconfigured `OkHttpClient()`)
- iOS: `Darwin` (native `URLSession` wrapper)

**Database**:
- Android: Room with `BundledSQLiteDriver` in cache directory
- iOS: Room with `BundledSQLiteDriver` in Documents directory (NSFileManager)

---

## 11. Dependency Injection (Koin)

### CoreModule

**File**: `di/CoreModule.kt`

Pre-configures:
- `HttpClient` with timeout, retry, logging, content negotiation
- `AppSettings` (from platform module)
- `AppDatabase` + `UserDao`
- `DeepLinkHandler`

Feature-toggled registrations (commented blocks, uncommented when enabled):

```
[auth]           → AuthRepository, AuthViewModel
[firebase_auth]  → FirebaseAuthHandler
[google_sheets]  → GoogleSheetsConfig, GoogleSheetsService
[chat]           → AiChatService registration
[payment]        → PaymentService (RevenueCat)
[messaging]      → RealtimeDbService (platform modules)
```

### Platform Module

Each platform provides `actual fun platformModule(): Module` with:
- `AppDatabase` construction (Room builder)
- `AppSettings` implementation
- `SocialAuthProvider` (Google/Apple native SDKs)
- Platform-specific services (DateFormatter, etc.)

---

## 11. Feature Toggle System

Features are controlled via block markers in `CoreModule.kt`:

```kotlin
// [feature] start
// registration code here (commented = disabled)
// [feature] end
```

The backend toggle system:
1. **Block markers**: `// [feature] start` / `// [feature] end` — code inside is commented/uncommented
2. **Single-line**: `// [feature] code` — uncommented when enabled
3. **File deletion**: `CONDITIONAL_FILES` dict — entire file removed when feature disabled

### Feature Toggle Summary

| Feature | Toggle Tag | Has Dependencies | Platform Code | Files |
|---------|-----------|-----------------|---------------|-------|
| Charts | `[charts]` | No | No | 6 |
| Payment | `[payment]` | Yes (RevenueCat KMP) | No (KMP SDK) | 8 |
| Messaging | `[messaging]` | Firebase RTDB (native) | Yes (Android + iOS) | 9 + enhanced GenericChatScreen |
| Calendar | `[calendar]` | No | No | 8 |

---

## 12. Charts & Graphs (Custom Canvas)

Pure Compose Canvas-based charting — no third-party library.

### Data Models (`core/data/charts/`)
- **ChartDataPoint** — `label`, `value`, `color?`
- **ChartMetrics** — `min`, `max`, `avg`, `total` (with `from()` factory)
- **ChartType** enum — `BAR`, `LINE`, `PIE`, `DONUT`, `AREA`

### Components (`core/presentation/components/`)
- **BarChart** — Vertical bars with grid lines, labels, animated entrance
- **LineChart** — Line with gradient fill, data points, grid
- **PieChart** — Pie or donut chart with legend, animated sweep, center content slot
- **ChartCard** — Wrapper card with title, subtitle, chart slot, optional metrics row

### Screen
- **GenericChartScreen** — Chart type selector tabs, multiple chart sections, refresh action

---

## 13. Payment (RevenueCat KMP)

In-app purchases and subscriptions via RevenueCat KMP SDK.

### Data Models (`core/data/payment/`)
- **PaymentProduct** — `id`, `title`, `price`, `period`, `isBestValue`
- **SubscriptionStatus** sealed — `Active`, `Expired`, `Trial`, `None`
- **PurchaseResult** sealed — `Success`, `Cancelled`, `Error`
- **PaymentCustomerInfo** — Active entitlements, purchased product IDs
- **PaymentOffering** — Group of products

### Service (`core/data/payment/`)
- **PaymentService** interface — `configure()`, `getOfferings()`, `purchase()`, `restorePurchases()`, `observeCustomerInfo()`, `isEntitled()`, `getSubscriptionStatus()`, `logOut()`
- **RevenueCatPaymentService** — Implementation wrapping RevenueCat SDK

### Components
- **PaymentProductCard** — Product with price, period badge, selected state, best-value highlight
- **PaymentSummaryCard** — Order summary with line items and total
- **SubscriptionBadge** — Active/expired/trial status badge

### Screens
- **GenericPaywallScreen** — Product list, features, CTA, restore, terms/privacy
- **GenericSubscriptionScreen** — Current plan display, manage, upgrade/downgrade

---

## 14. Real-Time Messaging (Firebase Realtime Database)

Real-time chat using Firebase RTDB with platform-specific native SDKs.

### Data Models (`core/data/messaging/`)
- **ChatMessage** — `id`, `conversationId`, `senderId`, `senderName`, `content`, `timestampMillis`, `isRead`, `type`
- **MessageType** enum — `TEXT`, `IMAGE`, `SYSTEM`
- **Conversation** — `id`, `participants`, `lastMessage`, `unreadCount`, `updatedAtMillis`
- **TypingStatus** — `userId`, `isTyping`

### Service
- **RealtimeDbService** interface — `observeMessages()`, `sendMessage()`, `observeConversations()`, `markAsRead()`, `setTypingStatus()`, `observeTyping()`, `deleteMessage()`
- **AndroidRealtimeDbService** — Android impl using `com.google.firebase.database`
- **IosRealtimeDbService** — iOS factory bridge (same pattern as Firestore)

### Components
- **MessageBubble** — Own/other bubble with timestamp, read receipt, sender name
- **MessageInputBar** — Text input + send + attach + typing indicator
- **ConversationListItem** — Avatar + name + last message + unread badge + timestamp
- **TypingIndicator** — Animated three-dot indicator

### Screen
- **GenericConversationListScreen** — Conversation list with search, FAB for new chat

---

## 15. Calendar (Custom Compose)

Custom Compose calendar with multiple view modes — no third-party library.

### Data Models (`core/data/calendar/`)
- **CalendarEvent** — `id`, `title`, `startMillis`, `endMillis`, `description?`, `location?`, `color?`, `isAllDay`
- **CalendarViewMode** enum — `MONTH`, `WEEK`, `DAY`, `AGENDA`
- **CalendarDay** — `dateMillis`, `dayOfMonth`, `isCurrentMonth`, `isToday`, `isSelected`, `events`

### Components
- **CalendarGrid** — Month grid with day-of-week headers, event dots, month navigation
- **WeekStrip** — Horizontal scrollable week strip with selected day highlight
- **DaySchedule** — Vertical 24h time slots with positioned event blocks, current time indicator
- **AgendaList** — Date-grouped event list (today, tomorrow, upcoming)
- **CalendarEventCard** — Event card with color bar, time, title, location
- **TimeRangeSelector** — Start/end time picker buttons for event creation

### Screen
- **GenericCalendarScreen** — View mode tabs, calendar display, selected day events, FAB

---

## What Code-Gen Adds

Given the core module, a generated app only needs:

| Layer | What to Generate | Example |
|-------|-----------------|---------|
| **Domain Models** | Data classes from `project-context.json` | `Event.kt`, `News.kt` |
| **Entities** | Room `@Entity` classes with Long timestamps | `EventEntity.kt` |
| **DAOs** | Room `@Dao` interfaces | `EventDao.kt` |
| **Repositories** | Interface + Impl (with `StateFlow` state) | `EventRepository.kt` |
| **Use Cases** | `invoke()` + `load()` + `refresh()` | `GetEventsUseCase.kt` |
| **ViewModels** | Extend `BaseViewModel`, inject use cases | `EventsViewModel.kt` |
| **Screens** | Wire generic screens with domain data | `EventListScreen.kt` |
| **Navigation** | Add routes + wire in `AppNavigation` | Custom `Routes` |
| **Koin Module** | Register DAOs, repos, use cases, ViewModels | `AppModule.kt` |

The core handles the rest — auth, navigation shell, UI components, database setup, backend connectivity, and platform abstractions.

---

## File Count

| Location | Files | Covers |
|----------|-------|--------|
| Common Main (`commonMain/kotlin/com/mismaiti/core/`) | ~123 | Interfaces, screens, components, base classes, services |
| Android (`androidMain/kotlin/com/mismaiti/core/`) | ~13 | Google Sign-In, ExoPlayer, Google Maps, FCM, SharedPrefs, Firestore SDK, RTDB, OkHttp |
| iOS (`iosMain/kotlin/com/mismaiti/core/`) | ~13 | Apple Sign-In, AVPlayer, MapKit, UNNotifications, NSUserDefaults, Firestore bridge, RTDB bridge, Darwin HTTP |
| **Total** | **~149** | **Full cross-platform mobile app toolkit** |
