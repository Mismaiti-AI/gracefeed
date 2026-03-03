# ViewModel Patterns Guide

> Prevent common compilation errors when creating ViewModels that extend `BaseViewModel`.

---

## BaseViewModel Overview

All ViewModels **must** extend `BaseViewModel` (not `ViewModel()` directly). It provides:

| Member | Type | Description |
|--------|------|-------------|
| `isLoading` | `StateFlow<Boolean>` | Shared loading state |
| `error` | `StateFlow<String?>` | Shared error state |
| `safeLaunch {}` | Function | IO coroutine with auto loading/error handling |
| `uiStateFrom()` | Function | Combines a `StateFlow<T>` with loading/error into `UiState<T>` |

---

## Two Patterns: Choose One Per ViewModel

### Pattern A: `uiStateFrom()` — Simple Wrapper

Use when you have a **single data flow** and want the standard `UiState<T>` wrapper (Loading / Success / Error).

```kotlin
class EventsViewModel(
    private val getEventsUseCase: GetEventsUseCase
) : BaseViewModel() {

    // uiStateFrom takes a StateFlow<T> — NOT a Flow<T>
    val state: StateFlow<UiState<List<Event>>> = uiStateFrom(getEventsUseCase())

    init { loadEvents() }
    fun loadEvents() = safeLaunch { getEventsUseCase.load() }
}
```

**Rules:**
- `uiStateFrom()` accepts **`StateFlow<T>`**, not `Flow<T>`
- No extra imports needed — `viewModelScope` is used internally by `uiStateFrom()`
- The screen consumes `UiState.Loading`, `UiState.Success(data)`, `UiState.Error(message)`

### Pattern B: `combine()` — Custom UiState

Use when you need a **custom sealed interface** (e.g., multiple data fields in Success) or combine multiple flows.

```kotlin
import androidx.lifecycle.viewModelScope  // ⚠️ REQUIRED IMPORT

class DashboardViewModel(
    private val getDashboardOverviewUseCase: GetDashboardOverviewUseCase
) : BaseViewModel() {

    val uiState: StateFlow<DashboardUiState> = combine(
        getDashboardOverviewUseCase(),
        isLoading,
        error
    ) { overview, loading, err ->
        when {
            err != null -> DashboardUiState.Error(err)
            loading -> DashboardUiState.Loading
            else -> DashboardUiState.Success(
                bulletinCount = overview.bulletinCount,
                upcomingEvents = overview.upcomingEvents
            )
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), DashboardUiState.Loading)

    init { loadDashboard() }
    fun loadDashboard() = safeLaunch { getDashboardOverviewUseCase.load() }
}
```

**Rules:**
- **Always import `androidx.lifecycle.viewModelScope`** — it's an extension property, not inherited
- **Always include `isLoading` and `error`** in the combine — they come from BaseViewModel
- Return your custom sealed interface directly (not wrapped in `UiState<>`)

---

## Required Imports Checklist

When using Pattern B (`combine` directly), you need **all** of these:

```kotlin
import androidx.lifecycle.viewModelScope          // ⚠️ MOST COMMONLY FORGOTTEN
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.combine
```

When using Pattern A (`uiStateFrom`), you only need:

```kotlin
import kotlinx.coroutines.flow.StateFlow
```

---

## Common Mistakes

### 1. Missing `viewModelScope` import

```
e: Unresolved reference 'viewModelScope'
```

**Why:** `viewModelScope` is a Kotlin extension property on `ViewModel`, not a member field. Even though `BaseViewModel` extends `ViewModel`, subclasses must import it explicitly when referencing it directly in `stateIn()`.

**Fix:** Add `import androidx.lifecycle.viewModelScope`

### 2. Passing `Flow<T>` to `uiStateFrom()` instead of `StateFlow<T>`

```
e: Argument type mismatch: actual type is 'Flow<T>', but 'StateFlow<T>' was expected.
```

**Why:** `uiStateFrom()` requires `StateFlow<T>`. Use cases that return `Flow<T>` from `combine()` are not `StateFlow`.

**Fix:** Use Pattern B (`combine` directly) or convert the Flow to StateFlow first.

### 3. UseCase returning wrapped `MutableStateFlow`

```kotlin
// ❌ WRONG — wraps result in MutableStateFlow unnecessarily
operator fun invoke() = combine(flow1, flow2) { a, b ->
    MutableStateFlow(Result(a, b))
}

// ✅ CORRECT — return the data directly
operator fun invoke() = combine(flow1, flow2) { a, b ->
    Result(a, b)
}
```

### 4. Nested UiState in Screen

If the ViewModel returns `StateFlow<CustomUiState>` (Pattern B), the screen should match:

```kotlin
// ✅ Pattern B — custom UiState, consume directly
when (val state = uiState) {
    is DashboardUiState.Loading -> { ... }
    is DashboardUiState.Success -> { ... }
    is DashboardUiState.Error -> { ... }
}
```

Do **not** nest `UiState<DashboardUiState>` — that creates a double-wrapped state.

### 5. DetailCard requires `title` and `rows` parameters

```kotlin
// ❌ WRONG — trailing lambda is not a content block
DetailCard {
    InfoRow(label = "Name", value = "John")
}

// ✅ CORRECT — pass title and rows explicitly
DetailCard(
    title = "Contact Info",
    rows = listOf(
        DetailRow(label = "Name", value = "John"),
        DetailRow(label = "Email", value = "john@example.com"),
    )
)
```

### 6. CalendarEvent uses millis, not Instant

```kotlin
// ❌ WRONG
CalendarEvent(id = "1", title = "...", startDateTime = event.startDate, endDateTime = event.endDate)

// ✅ CORRECT
CalendarEvent(id = "1", title = "...", startMillis = event.startDate.toEpochMilliseconds(), endMillis = event.endDate.toEpochMilliseconds())
```

### 7. `ColorScheme()` has no zero-arg constructor

```kotlin
// ❌ WRONG — no parameterless constructor exists
val LightColorScheme = lightColorScheme(primary = ColorScheme().primary)

// ✅ CORRECT — use the factory functions with defaults
val LightColorScheme = lightColorScheme()
val DarkColorScheme = darkColorScheme()
```

### 8. Wrong import for `Alignment`

```kotlin
// ❌ WRONG — Alignment is not in material3
import androidx.compose.material3.Alignment

// ✅ CORRECT — Alignment is in compose.ui
import androidx.compose.ui.Alignment
```

### 9. Calling `.value` on a `Flow` (only works on `StateFlow`)

```kotlin
// ❌ WRONG — .value is a StateFlow property, Flow doesn't have it
val current = getSettingsUseCase().value.firstOrNull()

// ✅ CORRECT — use .first() suspend function to get the latest emission
val current = getSettingsUseCase().first().firstOrNull()
```

---

## Quick Decision Tree

```
Need a ViewModel?
  └─ Extend BaseViewModel (never ViewModel directly)
       └─ Single data flow, standard Loading/Success/Error?
            ├─ YES → Pattern A: uiStateFrom(useCase())
            └─ NO  → Pattern B: combine() + viewModelScope import
                      └─ Define custom sealed interface
                      └─ Include isLoading + error in combine
                      └─ Use .stateIn(viewModelScope, ...)
```

---

## Actual Fixes Log

The following bugs were found and fixed during the initial `assembleDebug`. This section serves as a reference for what went wrong and why, so the same mistakes are not repeated.

### GetDashboardOverviewUseCase — Wrapped result in `MutableStateFlow`

**File:** `domain/usecase/GetDashboardOverviewUseCase.kt`

```kotlin
// ❌ BEFORE — returned Flow<MutableStateFlow<DashboardOverview>>
operator fun invoke() = combine(flow1, flow2, flow3, flow4) { bulletins, events, sermons, services ->
    MutableStateFlow(
        DashboardOverview(
            bulletinCount = bulletins.size,
            upcomingEvents = upcomingEvents.take(3),
            latestSermons = latestSermons,
            activeServices = activeServices
        )
    )
}

// ✅ AFTER — returns Flow<DashboardOverview>
operator fun invoke() = combine(flow1, flow2, flow3, flow4) { bulletins, events, sermons, services ->
    DashboardOverview(
        bulletinCount = bulletins.size,
        upcomingEvents = upcomingEvents.take(3),
        latestSermons = latestSermons,
        activeServices = activeServices
    )
}
```

**Root cause:** Unnecessary `MutableStateFlow` wrapper around the combine result.

---

### DashboardViewModel — Used nonexistent `uiStateFrom` overload with transform

**File:** `presentation/dashboard/DashboardViewModel.kt`

```kotlin
// ❌ BEFORE — no 1-flow + transform overload exists on uiStateFrom
val uiState: StateFlow<UiState<DashboardUiState>> = uiStateFrom(
    getDashboardOverviewUseCase()
) { overview ->
    DashboardUiState.Success(
        bulletinCount = overview.bulletinCount,
        upcomingEvents = overview.upcomingEvents,
        latestSermons = overview.latestSermons,
        activeServices = overview.activeServices
    )
}

// ✅ AFTER — Pattern B with combine(), returns custom sealed interface directly
val uiState: StateFlow<DashboardUiState> = combine(
    getDashboardOverviewUseCase(),
    isLoading,
    error
) { overview, loading, err ->
    when {
        err != null -> DashboardUiState.Error(err)
        loading -> DashboardUiState.Loading
        else -> DashboardUiState.Success(
            bulletinCount = overview.bulletinCount,
            upcomingEvents = overview.upcomingEvents,
            latestSermons = overview.latestSermons,
            activeServices = overview.activeServices
        )
    }
}.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), DashboardUiState.Loading)
```

**Root cause:** `uiStateFrom()` only has a single-flow no-transform overload, 2-flow + transform, and 3-flow + transform. There is no 1-flow + transform variant. Since `getDashboardOverviewUseCase()` returns `Flow` (not `StateFlow`), Pattern B was the correct choice.

---

### DashboardScreen — Double-nested state (UiState wrapping DashboardUiState)

**File:** `presentation/dashboard/DashboardScreen.kt`

```kotlin
// ❌ BEFORE — nested when blocks, double-wrapped state
when (val state = uiState) {
    is UiState.Loading -> { ... }
    is UiState.Success -> {
        when (val dashboardState = state.data) {
            is DashboardUiState.Loading -> { ... }
            is DashboardUiState.Success -> { ... }
            is DashboardUiState.Error -> { ... }
        }
    }
    is UiState.Error -> { ... }
}

// ✅ AFTER — flat, single when block
when (val state = uiState) {
    is DashboardUiState.Loading -> { ... }
    is DashboardUiState.Success -> { ... }
    is DashboardUiState.Error -> { ... }
}
```

**Root cause:** ViewModel was changed from `UiState<DashboardUiState>` to `DashboardUiState` directly, so the screen must match.

---

### 5 ViewModels — Missing `viewModelScope` import

**Files:**
- `presentation/eventcalendar/EventCalendarViewModel.kt`
- `presentation/sermonarchive/SermonListViewModel.kt`
- `presentation/serviceschedule/ServiceListViewModel.kt`
- `presentation/settings/SettingsViewModel.kt`
- `presentation/bulletinfeed/BulletinListViewModel.kt`

```kotlin
// ❌ BEFORE — no import, causes "Unresolved reference 'viewModelScope'"
class SomeViewModel(...) : BaseViewModel() {
    val uiState = combine(...).stateIn(viewModelScope, ...)
}

// ✅ AFTER — added import
import androidx.lifecycle.viewModelScope

class SomeViewModel(...) : BaseViewModel() {
    val uiState = combine(...).stateIn(viewModelScope, ...)
}
```

**Root cause:** `viewModelScope` is an extension property defined on `ViewModel`. Even though `BaseViewModel` extends `ViewModel`, the extension must be explicitly imported when used directly by subclasses.

---

### EventCalendarScreen — Wrong `CalendarEvent` parameter names

**File:** `presentation/eventcalendar/EventCalendarScreen.kt`

```kotlin
// ❌ BEFORE — params don't exist on CalendarEvent
CalendarEvent(
    id = event.id,
    title = event.title,
    startDateTime = event.startDate,
    endDateTime = event.endDate,
    description = event.description,
    location = event.location
)

// ✅ AFTER — correct param names, convert Instant to Long millis
CalendarEvent(
    id = event.id,
    title = event.title,
    startMillis = event.startDate.toEpochMilliseconds(),
    endMillis = event.endDate.toEpochMilliseconds(),
    description = event.description,
    location = event.location
)
```

**Root cause:** `CalendarEvent` (in `core/data/calendar/CalendarModels.kt`) uses `startMillis: Long` and `endMillis: Long`, not `startDateTime`/`endDateTime`. The domain `Event` model uses `Instant`, so conversion is needed.

---

### EventDetailScreen, ServiceDetailScreen — `DetailCard` used as content lambda

**Files:**
- `presentation/eventcalendar/EventDetailScreen.kt`
- `presentation/serviceschedule/ServiceDetailScreen.kt`

```kotlin
// ❌ BEFORE — trailing lambda syntax, but DetailCard doesn't accept content lambda
DetailCard {
    InfoRow(label = "Time", value = service.regularTime)
    InfoRow(label = "Location", value = service.regularLocation)
}

// ✅ AFTER — explicit title + rows parameters
DetailCard(
    title = "Service Info",
    rows = listOf(
        DetailRow(label = "Time", value = service.regularTime),
        DetailRow(label = "Location", value = service.regularLocation),
    )
)
```

**Root cause:** `DetailCard` signature is `DetailCard(title: String, rows: List<DetailRow>, ...)`. It does not have a trailing `@Composable` content lambda.

---

### BulletinDetailScreen — Stray trailing lambda on `DetailCard`

**File:** `presentation/bulletinfeed/BulletinDetailScreen.kt`

```kotlin
// ❌ BEFORE — extra empty trailing lambda
DetailCard(title = "Daily Verse", rows = listOf(...)) {

}

// ✅ AFTER — no trailing lambda
DetailCard(title = "Daily Verse", rows = listOf(...))
```

**Root cause:** Leftover trailing lambda `{ }` from incorrect code generation.

---

### ServiceListScreen — Wrong Alignment import

**File:** `presentation/serviceschedule/ServiceListScreen.kt`

```kotlin
// ❌ BEFORE — Alignment is not in material3
import androidx.compose.material3.Alignment
import androidx.compose.ui.Alignment.Companion.Center

CircularProgressIndicator(modifier = Modifier.align(Center))

// ✅ AFTER
import androidx.compose.ui.Alignment

CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
```

**Root cause:** `Alignment` lives in `androidx.compose.ui`, not `androidx.compose.material3`.

---

### SettingsViewModel — `.value` on a Flow

**File:** `presentation/settings/SettingsViewModel.kt`

```kotlin
// ❌ BEFORE — .value is only on StateFlow, not Flow
val currentSettings = getSettingsUseCase().value.firstOrNull() ?: AppSetting()

// ✅ AFTER — use .first() suspend function
val currentSettings = getSettingsUseCase().first().firstOrNull() ?: AppSetting()
```

**Root cause:** `getSettingsUseCase()` returns `Flow<List<AppSetting>>`, not `StateFlow`. The `.value` property only exists on `StateFlow`. To get the current emission from a `Flow`, use the `.first()` suspend function.

---

### AppColors.kt — `ColorScheme()` has no zero-arg constructor

**File:** `ui/theme/AppColors.kt`

```kotlin
// ❌ BEFORE — 80 lines all calling ColorScheme() which has no parameterless constructor
val LightColorScheme = lightColorScheme(
    primary = ColorScheme().primary,
    onPrimary = ColorScheme().onPrimary,
    // ... 35+ more lines
)

// ✅ AFTER — factory functions provide sensible defaults
val LightColorScheme = lightColorScheme()
val DarkColorScheme = darkColorScheme()
```

**Root cause:** `ColorScheme` is a data class with 40+ required parameters. Use `lightColorScheme()` / `darkColorScheme()` factory functions which provide Material3 defaults.

---

### Missing chart components — Imported but never created

**Files affected:**
- `preview/FinanceMockup.kt` — imported `PieChart`, `ChartCard`, `ChartDataPoint`
- `preview/FitnessMockup.kt` — imported `BarChart`, `ChartCard`, `ChartDataPoint`

**Files created:**
- `core/data/charts/ChartDataPoint.kt` — `data class ChartDataPoint(label, value, color)`
- `core/presentation/components/ChartCard.kt` — Surface card wrapper for charts
- `core/presentation/components/PieChart.kt` — Canvas-based pie/donut chart
- `core/presentation/components/BarChart.kt` — Canvas-based bar chart

**Root cause:** Mockup files were generated referencing chart components that were planned but never implemented.
