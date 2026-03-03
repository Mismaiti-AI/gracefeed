import Foundation
import FirebaseFirestore
import ComposeApp

class IOSFirestoreService: NSObject, FirestoreService {

    private lazy var db: Firestore = {
        Firestore.firestore()
    }()

    static let shared = IOSFirestoreService()

    private override init() { super.init() }

    // MARK: - Real-time Observe (returns Kotlinx Flow via callback)

    // Note: Real-time observation requires CFlow/callback bridge.
    // For the template, we provide one-shot methods.
    // The code-gen LLM can add real-time wrappers as needed.

    func observeCollection(collectionPath: String) -> any Kotlinx_coroutines_coreFlow {
        // Placeholder — real-time needs CFlow bridge or polling
        fatalError("Use Android for real-time or implement CFlow bridge")
    }

    func observeDocument(collectionPath: String, documentId: String) -> any Kotlinx_coroutines_coreFlow {
        fatalError("Use Android for real-time or implement CFlow bridge")
    }

    func getCollection(collectionPath: String) async throws -> [NSDictionary] {
        let snapshot = try await db.collection(collectionPath).getDocuments()
        return snapshot.documents.map { doc in
            let data = NSMutableDictionary(dictionary: doc.data())
            data["id"] = doc.documentID
            return data
        }
    }

    func getDocument(collectionPath: String, documentId: String) async throws -> NSDictionary? {
        let doc = try await db.collection(collectionPath).document(documentId).getDocument()
        guard let data = doc.data() else { return nil }
        let result = NSMutableDictionary(dictionary: data)
        result["id"] = doc.documentID
        return result
    }

    func addDocument(collectionPath: String, data: NSDictionary) async throws -> String {
        let ref = try await db.collection(collectionPath).addDocument(data: data as! [String: Any])
        return ref.documentID
    }

    func setDocument(collectionPath: String, documentId: String, data: NSDictionary) async throws {
        try await db.collection(collectionPath).document(documentId).setData(data as! [String: Any])
    }

    func updateDocument(collectionPath: String, documentId: String, fields: NSDictionary) async throws {
        try await db.collection(collectionPath).document(documentId).updateData(fields as! [String: Any])
    }

    func deleteDocument(collectionPath: String, documentId: String) async throws {
        try await db.collection(collectionPath).document(documentId).delete()
    }

    func queryCollection(collectionPath: String, field: String, op: String, value: Any) async throws -> [NSDictionary] {
        var query: Query = db.collection(collectionPath)
        switch op {
        case "==": query = query.whereField(field, isEqualTo: value)
        case "!=": query = query.whereField(field, isNotEqualTo: value)
        case "<": query = query.whereField(field, isLessThan: value)
        case "<=": query = query.whereField(field, isLessThanOrEqualTo: value)
        case ">": query = query.whereField(field, isGreaterThan: value)
        case ">=": query = query.whereField(field, isGreaterThanOrEqualTo: value)
        case "array-contains": query = query.whereField(field, arrayContains: value)
        default: break
        }
        let snapshot = try await query.getDocuments()
        return snapshot.documents.map { doc in
            let data = NSMutableDictionary(dictionary: doc.data())
            data["id"] = doc.documentID
            return data
        }
    }
}
