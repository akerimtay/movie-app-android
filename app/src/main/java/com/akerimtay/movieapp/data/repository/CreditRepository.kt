package com.akerimtay.movieapp.data.repository

import com.akerimtay.movieapp.data.Resource
import com.akerimtay.movieapp.data.model.Credits

interface CreditRepository {

    suspend fun getCredits(movieId: Int): Resource<Credits>

}