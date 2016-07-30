package com.codepath.articleatlas;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by melissahuang on 7/27/16.
 */
public class ArticlesAdapter extends
        RecyclerView.Adapter<ArticlesAdapter.ViewHolder> {

    private final String BASE_URL = "http://www.nytimes.com/";

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public ImageView thumbnailImageView;
        public TextView headlineTextView;
        public IMyViewHolderClicks mListener;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView, IMyViewHolderClicks listener) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            mListener = listener;
            itemView.setOnClickListener(this);
            thumbnailImageView = (ImageView) itemView.findViewById(R.id.ivThumbnail);
            headlineTextView = (TextView) itemView.findViewById(R.id.tvHeadline);
        }

        @Override
        public void onClick(View v) {
            mListener.onArticleClick(v, getAdapterPosition());
        }

        public interface IMyViewHolderClicks {
            void onArticleClick(View view, int pos);
        }
    }

    // Store a member variable for the articles
    private List<Article> mArticles;
    // Store the context for easy access
    private Context mContext;

    // Pass in the article array into the constructor
    public ArticlesAdapter(Context context, List<Article> articles) {
        mArticles = articles;
        mContext = context;
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public ArticlesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_article, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView, new ArticlesAdapter.ViewHolder.IMyViewHolderClicks() {
            public void onArticleClick(View view, int pos) {
                if(mContext instanceof MainActivity){
                    Article article = ((MainActivity)mContext).getArticleAtPosition(pos);
                    System.out.println("wow: " + article.getWebUrl());
                }
                Intent i = new Intent(mContext, SettingsActivity.class);
//                i.putExtra("myBook", book);
                mContext.startActivity(i);
            }
        });
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ArticlesAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Article article = mArticles.get(position);

        // Set item views based on your views and data model
        TextView textView = viewHolder.headlineTextView;
        textView.setText(article.getHeadline().getMain());
        ImageView imageView = viewHolder.thumbnailImageView;
        List<Multimedia> multimedia = article.getMultimedia();
        if (multimedia.size() > 0) {
            String url = BASE_URL + article.getMultimedia().get(0).url;
            Picasso.with(getContext()).load(url).into(imageView);
        }
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mArticles.size();
    }
}
