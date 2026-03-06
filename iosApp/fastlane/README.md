fastlane documentation
----

# Installation

Make sure you have the latest version of the Xcode command line tools installed:

```sh
xcode-select --install
```

For _fastlane_ installation instructions, see [Installing _fastlane_](https://docs.fastlane.tools/#installing-fastlane)

# Available Actions

## iOS

### ios setup

```sh
[bundle exec] fastlane ios setup
```

One-time setup: generate certificates and profiles (create app in App Store Connect first)

### ios sync_signing

```sh
[bundle exec] fastlane ios sync_signing
```

Sync code signing certificates and profiles via match

### ios build_kmp_framework

```sh
[bundle exec] fastlane ios build_kmp_framework
```

Build the KMP shared framework via Gradle

### ios build

```sh
[bundle exec] fastlane ios build
```

Build and archive the iOS app

### ios upload_testflight

```sh
[bundle exec] fastlane ios upload_testflight
```

Upload to TestFlight

### ios release

```sh
[bundle exec] fastlane ios release
```

Full pipeline: build KMP framework, archive iOS app, upload to TestFlight

### ios test

```sh
[bundle exec] fastlane ios test
```

Run iOS unit tests

----

This README.md is auto-generated and will be re-generated every time [_fastlane_](https://fastlane.tools) is run.

More information about _fastlane_ can be found on [fastlane.tools](https://fastlane.tools).

The documentation of _fastlane_ can be found on [docs.fastlane.tools](https://docs.fastlane.tools).
