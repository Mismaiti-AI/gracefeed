package com.gracefeed.core.data.deeplink

import org.koin.core.context.GlobalContext

/**
 * Bridge for iOS Swift code to send deep link URIs to the Kotlin deep link handler.
 *
 * Called from Swift AppDelegate:
 * ```swift
 * func application(_ app: UIApplication, open url: URL, options: ...) -> Bool {
 *     DeepLinkBridgeKt.handleDeepLinkUri(uri: url.absoluteString)
 *     return true
 * }
 * ```
 */
fun handleDeepLinkUri(uri: String) {
    GlobalContext.get().get<DeepLinkHandler>().handleIncomingUri(uri)
}
