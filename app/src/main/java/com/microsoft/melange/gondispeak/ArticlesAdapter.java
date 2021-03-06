package com.microsoft.melange.gondispeak;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.MyViewHolder> {

    private Context mContext;
    private List<Article> articleList;
    Dashboard dashboard = Dashboard.getDashboard();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, content;
        public ImageView thumbnail;
        public ImageButton playButton, stopButton;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            content = (TextView) view.findViewById(R.id.article_content);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            playButton = (ImageButton) view.findViewById(R.id.overflow1);
            stopButton = (ImageButton) view.findViewById(R.id.overflow2);
        }
    }

    public ArticlesAdapter(Context mContext, List<Article> articleList) {
        this.mContext = mContext;
        this.articleList = articleList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.article_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Article article = articleList.get(position);
        holder.title.setText(article.getTitle());
        holder.content.setText(article.getContent());

        // loading album cover using Glide library
        Glide.with(mContext).load(article.getThumbnail()).into(holder.thumbnail);

        holder.playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readOutArticle(holder.title, holder.content);
            }
        });
        holder.stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopReading();
            }
        });
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void readOutArticle(View title, View content) {
        // inflate menu
        // Activate TTS
        String speak_title = ((TextView) title).getText().toString();
        String speak_content = ((TextView) content).getText().toString();

        Log.d("ARTCILE CONTENT", speak_title);
        dashboard.sayText(speak_title, TextToSpeech.QUEUE_ADD);
        dashboard.sayText(speak_content, TextToSpeech.QUEUE_ADD);
    }

    private void stopReading() {
        dashboard.stopSpeaking();
    }


    @Override
    public int getItemCount() {
        return articleList.size();
    }
}
