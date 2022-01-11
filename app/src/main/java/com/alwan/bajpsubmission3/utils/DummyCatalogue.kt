package com.alwan.bajpsubmission3.utils

import com.alwan.bajpsubmission3.data.source.local.entity.MovieEntity
import com.alwan.bajpsubmission3.data.source.local.entity.TvShowEntity
import com.alwan.bajpsubmission3.data.source.remote.response.Genres
import com.alwan.bajpsubmission3.data.source.remote.response.movie.MovieDetailResponse
import com.alwan.bajpsubmission3.data.source.remote.response.tvshow.TvShowDetailResponse

object DummyCatalogue {

    fun getMovies(): ArrayList<MovieEntity> {
        val listMovies = ArrayList<MovieEntity>()

        listMovies.add(
            MovieEntity(
                899082,
                "Harry Potter 20th Anniversary: Return to Hogwarts",
                "An enchanting making-of story told through all-new in-depth interviews and cast conversations, inviting fans on a magical first-person journey through one of the most beloved film franchises of all time.",
                arrayListOf(
                    Genres(99, "Documentary"),
                ).toGenreString(),
                8.5,
                "/34Xss3gwKdwvtomCDkeC2lW4PVB.jpg",
                false,
            )
        )

        listMovies.add(
            MovieEntity(
                634649,
                "Spider-Man: No Way Home",
                "Peter Parker is unmasked and no longer able to separate his normal life from the high-stakes of being a super-hero. When he asks for help from Doctor Strange the stakes become even more dangerous, forcing him to discover what it truly means to be Spider-Man.",
                arrayListOf(
                    Genres(28, "Action"),
                    Genres(12, "Adventure"),
                    Genres(878, "Science Fiction")
                ).toGenreString(),
                8.4,
                "/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg",
                false
            )
        )

        listMovies.add(
            MovieEntity(
                566525,
                "Shang-Chi and the Legend of the Ten Rings",
                "Shang-Chi must confront the past he thought he left behind when he is drawn into the web of the mysterious Ten Rings organization.",
                arrayListOf(
                    Genres(28, "Action"),
                    Genres(12, "Adventure"),
                    Genres(14, "Fantasy")
                ).toGenreString(),
                7.1,
                "/1BIoJGKbXjdFDAqUEiA2VHqkK1Z.jpg",
                false
            )
        )

        return listMovies
    }

    fun getTvShows(): ArrayList<TvShowEntity> {
        val listTvShows = ArrayList<TvShowEntity>()

        listTvShows.add(
            TvShowEntity(
                60574,
                "Peaky Blinders",
                "A gangster family epic set in 1919 Birmingham, England and centered on a gang who sew razor blades in the peaks of their caps, and their fierce boss Tommy Shelby, who means to move up in the world.",
                arrayListOf(
                    Genres(80, "Crime"),
                    Genres(18, "Drama"),
                ).toGenreString(),
                8.6,
                "/bGZn5RVzMMXju4ev7xbl1aLdXqq.jpg",
                false,
            )
        )

        listTvShows.add(
            TvShowEntity(
                69050,
                "Riverdale",
                "Set in the present, the series offers a bold, subversive take on Archie, Betty, Veronica and their friends, exploring the surreality of small-town life, the darkness and weirdness bubbling beneath Riverdale’s wholesome facade.",
                arrayListOf(
                    Genres(80, "Crime"),
                    Genres(18, "Drama"),
                    Genres(9648, "Mystery")
                ).toGenreString(),
                8.6,
                "/xBaeUYKNJfX8VhIFvvgPpFSYxBZ.jpg",
                false
            )
        )

        listTvShows.add(
            TvShowEntity(
                71712,
                "The Good Doctor",
                "Shaun Murphy, a young surgeon with autism and savant syndrome, relocates from a quiet country life to join a prestigious hospital's surgical unit. Unable to personally connect with those around him, Shaun uses his extraordinary medical gifts to save lives and challenge the skepticism of his colleagues.",
                arrayListOf(
                    Genres(18, "Drama"),
                ).toGenreString(),
                8.6,
                "/cXUqtadGsIcZDWUTrfnbDjAy8eN.jpg",
                false
            )
        )

        return listTvShows
    }

    fun getRemoteMovies(): ArrayList<MovieDetailResponse> {
        val listMovies = ArrayList<MovieDetailResponse>()


        listMovies.add(
            MovieDetailResponse(
                899082,
                "Harry Potter 20th Anniversary: Return to Hogwarts",
                8.5,
                "/34Xss3gwKdwvtomCDkeC2lW4PVB.jpg",
                "An enchanting making-of story told through all-new in-depth interviews and cast conversations, inviting fans on a magical first-person journey through one of the most beloved film franchises of all time.",
                arrayListOf(
                    Genres(99, "Documentary"),
                ),
            )
        )

        listMovies.add(
            MovieDetailResponse(
                634649,
                "Spider-Man: No Way Home",
                8.4,
                "/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg",
                "Peter Parker is unmasked and no longer able to separate his normal life from the high-stakes of being a super-hero. When he asks for help from Doctor Strange the stakes become even more dangerous, forcing him to discover what it truly means to be Spider-Man.",
                arrayListOf(
                    Genres(28, "Action"),
                    Genres(12, "Adventure"),
                    Genres(878, "Science Fiction")
                ),
            )
        )

        listMovies.add(
            MovieDetailResponse(
                566525,
                "Shang-Chi and the Legend of the Ten Rings",
                7.1,
                "/1BIoJGKbXjdFDAqUEiA2VHqkK1Z.jpg",
                "Shang-Chi must confront the past he thought he left behind when he is drawn into the web of the mysterious Ten Rings organization.",
                arrayListOf(
                    Genres(28, "Action"),
                    Genres(12, "Adventure"),
                    Genres(14, "Fantasy")
                ),
            )
        )


        return listMovies
    }

    fun getRemoteTvShows(): ArrayList<TvShowDetailResponse> {
        val listTvShows = ArrayList<TvShowDetailResponse>()

        listTvShows.add(
            TvShowDetailResponse(
                60574,
                "Peaky Blinders",
                8.6,
                "/bGZn5RVzMMXju4ev7xbl1aLdXqq.jpg",
                "A gangster family epic set in 1919 Birmingham, England and centered on a gang who sew razor blades in the peaks of their caps, and their fierce boss Tommy Shelby, who means to move up in the world.",
                arrayListOf(
                    Genres(80, "Crime"),
                    Genres(18, "Drama"),
                ),
            )
        )

        listTvShows.add(
            TvShowDetailResponse(
                69050,
                "Riverdale",
                8.6,
                "/xBaeUYKNJfX8VhIFvvgPpFSYxBZ.jpg",
                "Set in the present, the series offers a bold, subversive take on Archie, Betty, Veronica and their friends, exploring the surreality of small-town life, the darkness and weirdness bubbling beneath Riverdale’s wholesome facade.",
                arrayListOf(
                    Genres(80, "Crime"),
                    Genres(18, "Drama"),
                    Genres(9648, "Mystery")
                ),
            )
        )

        listTvShows.add(
            TvShowDetailResponse(
                71712,
                "The Good Doctor",
                8.6,
                "Shaun Murphy, a young surgeon with autism and savant syndrome, relocates from a quiet country life to join a prestigious hospital's surgical unit. Unable to personally connect with those around him, Shaun uses his extraordinary medical gifts to save lives and challenge the skepticism of his colleagues.",
                "/cXUqtadGsIcZDWUTrfnbDjAy8eN.jpg",
                arrayListOf(
                    Genres(18, "Drama"),
                ),
            )
        )

        return listTvShows
    }
}