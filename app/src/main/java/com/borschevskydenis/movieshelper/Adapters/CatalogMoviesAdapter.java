package com.borschevskydenis.movieshelper.Adapters;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.borschevskydenis.movieshelper.R;
import com.borschevskydenis.movieshelper.ResultsFromServer.MovieSearch;
import com.borschevskydenis.movieshelper.Utils.CommonUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CatalogMoviesAdapter extends RecyclerView.Adapter<CatalogMoviesAdapter.MovieViewHolder> {

    private ArrayList<MovieSearch.ResultsBean> movies;
    private OnPosterClickListener onPosterClickListener;
    private OnButtonFullClickListener onButtonFullClickListener;

    public void setOnPosterClickListener(OnPosterClickListener onPosterClickListener) {
        this.onPosterClickListener = onPosterClickListener;
    }

    public void setOnButtonFullClickListener(OnButtonFullClickListener onButtonFullClickListener){
        this.onButtonFullClickListener = onButtonFullClickListener;
    }

    public CatalogMoviesAdapter() {
        movies = new ArrayList<>();
    }

    public interface OnPosterClickListener {
        void onPosterClick(int position);
    }

    public interface  OnButtonFullClickListener {
        void onButtonFullClick();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        if (i == R.layout.recommendation_movie_item) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recommendation_movie_item, viewGroup, false);
        } else {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.full_adapter_item, viewGroup, false);
        }
        return new MovieViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int i) {
        if(i == movies.size()) {
            movieViewHolder.btnFullMovies.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onButtonFullClickListener != null){
                        onButtonFullClickListener.onButtonFullClick();
                    }
                }
            });
        } else {
            MovieSearch.ResultsBean movie = movies.get(i);
            if(movie.getPoster_path()!=null)
                Picasso.get().load(CommonUtils.BASE_POSTER_URL + CommonUtils.W342_SIZE + movie.getPoster_path()).into(movieViewHolder.imageViewSmallPoster);
            else Picasso.get().load(R.drawable.poster_is_missing).into(movieViewHolder.imageViewSmallPoster);
            movieViewHolder.textViewTitle.setText(movie.getTitle());
//        movieViewHolder.textViewOriginalTitle.setText(movie.getOriginal_title());
        }

    }

    @Override
    public int getItemViewType(int position) {
        return (position == movies.size()) ? R.layout.full_adapter_item : R.layout.recommendation_movie_item;
    }

    @Override
    public int getItemCount() {
        return movies.size()+1;
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewSmallPoster;
        private TextView textViewTitle;
        private Button btnFullMovies;
//        private TextView textViewOriginalTitle;
//        private TextView textViewGenre;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewSmallPoster = itemView.findViewById(R.id.ivSmallPoster);
            textViewTitle = itemView.findViewById(R.id.tvTitle);
            btnFullMovies = itemView.findViewById(R.id.btnFullMovies);
//            textViewOriginalTitle = itemView.findViewById(R.id.tvOriginalTitle);
//            textViewGenre = itemView.findViewById(R.id.tvGenre);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onPosterClickListener != null) {
                        onPosterClickListener.onPosterClick(getAdapterPosition());
                    }
                }
            });
        }
    }

    public void setMovies(ArrayList<MovieSearch.ResultsBean> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    public ArrayList<MovieSearch.ResultsBean> getMovies() {
        return movies;
    }
}



