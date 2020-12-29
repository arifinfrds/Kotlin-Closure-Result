object Main {
    private val service: FeedService? = FeedServiceImpl()

    @JvmStatic
    fun main(args: Array<String>) {
        fetchFeed(id = -1)
        fetchFeed(id = 0)
        fetchFeed(id = 1)
    }

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
}

