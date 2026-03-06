import SwiftUI
import ComposeApp
import FirebaseCore
import FirebaseMessaging
import UserNotifications

class AppDelegate: NSObject, UIApplicationDelegate, MessagingDelegate, UNUserNotificationCenterDelegate {

    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]? = nil
    ) -> Bool {
        FirebaseApp.configure()
        Messaging.messaging().delegate = self
        UNUserNotificationCenter.current().delegate = self

        FirestoreServiceFactory.registerFactory()

        // [push_notifications] start
        application.registerForRemoteNotifications()
        // [push_notifications] end

        return true
    }

    // [deep_linking] start
    func application(
        _ app: UIApplication,
        open url: URL,
        options: [UIApplication.OpenURLOptionsKey: Any] = [:]
    ) -> Bool {
        DeepLinkBridge.shared.handleDeepLinkUri(uri: url.absoluteString)
        return true
    }
    // [deep_linking] end

    // [push_notifications] start
    func application(
        _ application: UIApplication,
        didRegisterForRemoteNotificationsWithDeviceToken deviceToken: Data
    ) {
        Messaging.messaging().apnsToken = deviceToken
    }

    @objc func messaging(_ messaging: Messaging, didReceiveRegistrationToken fcmToken: String?) {
        guard let token = fcmToken else { return }
        IosPushNotificationService.companion.fcmToken = token
    }

    @objc func userNotificationCenter(
        _ center: UNUserNotificationCenter,
        willPresent notification: UNNotification,
        withCompletionHandler completionHandler: @escaping (UNNotificationPresentationOptions) -> Void
    ) {
        let userInfo = notification.request.content.userInfo
        let messageId = userInfo["gcm.message_id"] as? String ?? ""
        let title = notification.request.content.title
        let body = notification.request.content.body
    //
        IosPushNotificationService.companion.onMessageReceived(
            messageId: messageId,
            title: title,
            body: body,
            data: [:]
        )
    //
        completionHandler([.banner, .sound, .badge])
    }
    // [push_notifications] end
}

@main
struct iOSApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
