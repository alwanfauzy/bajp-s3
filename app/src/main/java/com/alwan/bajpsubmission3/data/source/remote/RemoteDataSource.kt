package com.alwan.bajpsubmission3.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alwan.bajpsubmission3.data.source.remote.response.movie.MovieDetailResponse
import com.alwan.bajpsubmission3.data.source.remote.response.movie.MoviesResponse
import com.alwan.bajpsubmission3.data.source.remote.response.tvshow.TvShowDetailResponse
import com.alwan.bajpsubmission3.data.source.remote.response.tvshow.TvShowsResponse
import com.alwan.bajpsubmission3.network.RetrofitConfig
import com.alwan.bajpsubmission3.utils.EspressoIdlingResource
import com.alwan.bajpsubmission3.utils.NetworkInfo.API_KEY
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RemoteDataSource {

    fun getMovies(): LiveData<ApiResponse<ArrayList<MovieDetailResponse>>> {
        EspressoIdlingResource.increment()
        val movies = MutableLiveData<ApiResponse<ArrayList<MovieDetailResponse>>>()
        val client = RetrofitConfig.apiInstance.getMovies(API_KEY)

        client.enqueue(object : Callback<MoviesResponse> {
            override fun onResponse(
                call: Call<MoviesResponse>,
                response: Response<MoviesResponse>
            ) {
                movies.postValue(ApiResponse.success(response.body()?.results as ArrayList<MovieDetailResponse>))
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                Log.e("RemoteDataSource", "getMovies onFailure : ${t.message}")
                EspressoIdlingResource.decrement()
            }
        })

        return movies
    }

    fun getDetailMovie(movieId: String) : LiveData<ApiResponse<MovieDetailResponse>>{
        EspressoIdlingResource.increment()
        val detailMovie = MutableLiveData<ApiResponse<MovieDetailResponse>>()
        val client = RetrofitConfig.apiInstance.getMovieDetail(movieId, API_KEY)

        client.enqueue(object : Callback<MovieDetailResponse> {
            override fun onResponse(
                call: Call<MovieDetailResponse>,
                response: Response<MovieDetailResponse>
            ) {
                detailMovie.postValue(ApiResponse.success(response.body() as MovieDetailResponse))
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<MovieDetailResponse>, t: Throwable) {
                Log.e("RemoteDataSource", "getMovieDetail onFailure : ${t.message}")
                EspressoIdlingResource.decrement()
            }
        })

        return detailMovie
    }

    fun getTvShows() : LiveData<ApiResponse<ArrayList<TvShowDetailResponse>>> {
        EspressoIdlingResource.increment()
        val tvShows = MutableLiveData<ApiResponse<ArrayList<TvShowDetailResponse>>>()
        val client = RetrofitConfig.apiInstance.getTvShows(API_KEY)

        client.enqueue(object : Callback<TvShowsResponse> {
            override fun onResponse(
                call: Call<TvShowsResponse>,
                response: Response<TvShowsResponse>
            ) {
                tvShows.postValue(ApiResponse.success(response.body()?.results as ArrayList<TvShowDetailResponse>))
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<TvShowsResponse>, t: Throwable) {
                Log.e("RemoteDataSource", "getTvShows onFailure : ${t.message}")
                EspressoIdlingResource.decrement()
            }
        })

        return tvShows
    }

    fun getDetailTvShow(tvShowId: String) : LiveData<ApiResponse<TvShowDetailResponse>> {
        EspressoIdlingResource.increment()
        val detailTvShow = MutableLiveData<ApiResponse<TvShowDetailResponse>>()
        val client = RetrofitConfig.apiInstance.getTvShowDetail(tvShowId, API_KEY)
        client.enqueue(object : Callback<TvShowDetailResponse> {
            override fun onResponse(
                call: Call<TvShowDetailResponse>,
                response: Response<TvShowDetailResponse>
            ) {
                detailTvShow.postValue(ApiResponse.success(response.body() as TvShowDetailResponse))
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<TvShowDetailResponse>, t: Throwable) {
                Log.e("RemoteDataSource", "getDetailTvShow onFailure : ${t.message}")
                EspressoIdlingResource.decrement()
            }
        })

        return detailTvShow
    }

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource()
            }
    }
}