package com.akerimtay.movieapp.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.akerimtay.movieapp.data.datasource.paging.MoviePageDataSourceFactory
import com.akerimtay.movieapp.data.datasource.paging.NetworkState
import com.akerimtay.movieapp.data.repository.MovieRepository
import com.akerimtay.movieapp.utils.PAGING_ITEMS_SIZE
import com.akerimtay.movieapp.utils.PAGING_PREFETCH_DISTANCE

class SearchViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val movieDataSource = MoviePageDataSourceFactory(
        movieRepository = movieRepository,
        scope = viewModelScope
    )

    val movies = LivePagedListBuilder(movieDataSource, pagedListConfig()).build()
    val networkState: LiveData<NetworkState> = Transformations.switchMap(movieDataSource.source) {
        it.getNetworkState()
    }

    fun searchMovie(query: String?) {
        val search = query?.trim().orEmpty()
        if (query == null || search.isEmpty()) return
        if (movieDataSource.getQuery() == search) return
        movieDataSource.updateQuery(search)
    }

    private fun pagedListConfig() = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setPageSize(PAGING_ITEMS_SIZE)
        .setInitialLoadSizeHint(PAGING_ITEMS_SIZE)
        .setPrefetchDistance(PAGING_PREFETCH_DISTANCE)
        .build()

    fun refreshFailedRequest() = movieDataSource.getSource()?.retryFailedQuery()

    fun refreshAllList() = movieDataSource.getSource()?.refresh()

    fun getCurrentQuery() = movieDataSource.getQuery()

}