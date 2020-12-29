# Kotlin-Closure-Result
Understanding `Result` inside closure in Kotlin (Similar to Swift)

## Example 

### Defining the closure with `Result` type
Kotlin
```kotlin
interface FeedService {
    fun fetchFeeds(id: Int, completion: (Result<MutableList<Feed>>) -> Unit)
}
```
Swift
```swift
protocol FeedService {
    func fetchFeeds(id: Int, completion: (Result<[Feed], Error>) -> Void)
}
```

### Implementing the closure with `Result` type
Kotlin
```kotlin
class FeedServiceImpl : FeedService {

    override fun fetchFeeds(id: Int, completion: (Result<MutableList<Feed>>) -> Unit) {
        if (id == -1) {
            completion(Result.failure(FetchFeedError.InvalidFeedId))
            return
        }
        if (id == 0) {
            completion(Result.failure(FetchFeedError.Connectivity("please check connection")))
            return
        }
        completion(Result.success(mutableListOf()))
    }

    private fun makeAnyThrowable(message: String): Throwable {
        return Throwable(message)
    }
}

sealed class FetchFeedError : Throwable() {
    object InvalidFeedId : FetchFeedError()
    data class Connectivity(override val message: String) : FetchFeedError()
}
```

Swift
```swift
class FeedServiceImpl : FeedService {

    func fetchFeeds(id: Int, completion: (Result<[Feed], Error>) -> Void) {
        if (id == -1) {
            completion(.failure(.invalidFeedId))
            return
        }
        if (id == 0) {
            completion(.failure(.connectivity("please check connection")))
            return
        }
        completion(.success([]))
    }
}

enum FetchFeedError: Error {
    case invalidFeedId
    case connectivity(message: String)
}
```

### Using the closure with `Result` type
Kotlin
```kotlin
    private fun fetchFeed(id: Int) {
        service?.fetchFeeds(id = id, completion = { result ->
            result.onSuccess { feeds ->
                println(feeds)
            }
            result.onFailure { throwable ->
                println("$throwable : ${throwable.message}")
            }
        })
    }
```
Swift
```swift
    private func fetchFeed(id: Int) {        
        service?.fetchFeeds(id: id) { result in 
            switch result {
                case .success(let feeds):
                    print(feeds)
                case .failure(let error):
                    print(error)
            }
        }
    }
```
