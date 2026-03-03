# iOS SPM Dependencies

Add these Swift Package Manager dependencies to your Xcode project:

## How to Add in Xcode

1. Open `iosApp.xcodeproj` in Xcode
2. Select the project in the navigator
3. Select the iosApp target
4. Go to "General" tab → "Frameworks, Libraries, and Embedded Content"
5. Click "+" → "Add Package Dependency..."
6. Enter the package URL and version below

## Required Packages

### Firebase
- **URL:** `https://github.com/firebase/firebase-ios-sdk`
- **Version:** `10.20.0`
- **Products:** FirebaseFirestore

---

## Alternative: Package.swift

If using a Package.swift file, add these dependencies:

```swift
dependencies: [
    .package(url: "https://github.com/firebase/firebase-ios-sdk", from: "10.20.0"),
]
```

