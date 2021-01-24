package com.akerimtay.movieapp.data.datasource.paging

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.akerimtay.movieapp.data.Resource
import com.akerimtay.movieapp.data.model.Movie
import com.akerimtay.movieapp.data.repository.MovieRepository
import kotlinx.coroutines.*
import timber.log.Timber

private const val PAGING_DEFAULT_PAGE = 1
private const val PAGING_DEFAULT_NEXT_PAGE = 2

class MoviePageDataSource(
    private val movieRepository: MovieRepository,
    private val query: String,
    private val scope: CoroutineScope
) : PageKeyedDataSource<Int, Movie>() {

    private var supervisorJob = SupervisorJob()
    private val networkState = MutableLiveData<NetworkState>()
    private var retryQuery: (() -> Any)? = null

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>) {
        retryQuery = { loadInitial(params, callback) }
        executeQuery(PAGING_DEFAULT_PAGE) { list ->
            callback.onResult(list, null, PAGING_DEFAULT_NEXT_PAGE)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        val page = params.key
        retryQuery = { loadAfter(params, callback) }
        executeQuery(page) { list ->
            callback.onResult(list, page + 1)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {}

    override fun invalidate() {
        super.invalidate()
        supervisorJob.cancelChildren()
    }

    private fun executeQuery(page: Int, callback: (List<Movie>) -> Unit) {
        networkState.postValue(NetworkState.RUNNING)
        scope.launch(getJobErrorHandler() + supervisorJob) {
            val response = movieRepository.search(query, page)
            if (response.status == Resource.Status.SUCCESS) {
                networkState.postValue(NetworkState.SUCCESS)
                val movies = response.data ?: listOf()
                callback(movies)
                retryQuery = null
            } else {
                networkState.postValue(NetworkState.FAILED)
            }
        }
    }

    private fun getJobErrorHandler() = CoroutineExceptionHandler { _, e ->
        Timber.e("An error happened: $e")
        networkState.postValue(NetworkState.FAILED)
    }

    fun getNetworkState(): LiveData<NetworkState> = networkState

    fun refresh() = invalidate()

    fun retryFailedQuery() {
        val prevQuery = retryQuery
        retryQuery = null
        prevQuery?.invoke()
    }

}