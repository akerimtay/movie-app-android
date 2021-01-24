package com.akerimtay.movieapp.data.datasource.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.akerimtay.movieapp.data.model.Movie
import com.akerimtay.movieapp.data.repository.MovieRepository
import kotlinx.coroutines.CoroutineScope

private const val PAGING_DEFAULT_QUERY = "a"

class MoviePageDataSourceFactory(
    private val movieRepository: MovieRepository,
    private var query: String = PAGING_DEFAULT_QUERY,
    private val scope: CoroutineScope
) : DataSource.Factory<Int, Movie>() {

    val source = MutableLiveData<MoviePageDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val source = MoviePageDataSource(movieRepository, query, scope)
        this.source.postValue(source)
        return source
    }

    fun getQuery() = query

    fun getSource() = source.value

    fun updateQuery(query: String) {
        this.query = query
        getSource()?.refresh()
    }
}