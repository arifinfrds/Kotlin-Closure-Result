interface FeedService {
    fun fetchFeeds(id: Int, completion: (Result<MutableList<Feed>>) -> Unit)
}

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