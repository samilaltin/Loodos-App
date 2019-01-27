package com.samilaltin.loodos.loodosapp.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.samilaltin.loodos.loodosapp.R
import com.samilaltin.loodos.loodosapp.pojo.ServiceResponse
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_list_content.view.*

/**
 * Created by saltin on 27.01.2019
 */
class MovieListAdapter(private val serviceResponseMovieList: List<ServiceResponse>) :
    RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgPoster = view.imgPoster!!
        val txtTitleAndYear = view.txtTitleAndYear!!
        val txtGenre = view.txtGenre!!
        val txtActors = view.txtActors!!
        val txtPlot = view.txtPlot!!
        val txtImdbRating = view.txtImdbRating!!
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(p0.context)
                .inflate(R.layout.movie_list_content, p0, false)
        )
    }

    override fun getItemCount(): Int {
        return serviceResponseMovieList.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        setImageUsingPicasso(p0, serviceResponseMovieList[p1].poster)
        p0.txtTitleAndYear.text = serviceResponseMovieList[p1].title + " (" + serviceResponseMovieList[p1].year + ")"
        p0.txtGenre.text = serviceResponseMovieList[p1].genre
        p0.txtActors.text = serviceResponseMovieList[p1].actors
        p0.txtPlot.text = serviceResponseMovieList[p1].plot
        p0.txtImdbRating.text = serviceResponseMovieList[p1].imdbRating

    }

    private fun setImageUsingPicasso(viewHolder: ViewHolder, imageURL: String?) {
        Picasso.get()
            .load(imageURL)
            .error(R.drawable.ic_no_poster)
            .into(viewHolder.imgPoster)
    }


}