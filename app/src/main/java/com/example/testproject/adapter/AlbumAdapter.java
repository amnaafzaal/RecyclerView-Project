package com.example.testproject.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.testproject.R;
import com.example.testproject.model.AlbumData;

import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.MyHolder> {

    Context mContext;
    List<AlbumData> albumDataList;

    public AlbumAdapter(Context mContext, List<AlbumData> albumDataList) {
        this.mContext = mContext;
        this.albumDataList = albumDataList;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.album_item, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder( MyHolder holder, int position) {

        AlbumData albumData = albumDataList.get(position);

        holder.albumIdTxt.setText(String.valueOf(albumData.getAlbumId()));
        holder.albumTitleTxt.setText(albumData.getTitle());

        Glide.with(mContext)
                .load(albumData.getThumbnailUrl())
                .into(holder.albumThumbnail);

        if (position % 2 == 0 ){
            holder.rootItem.setBackgroundColor(Color.WHITE);
        }else {
            holder.rootItem.setBackgroundColor(Color.parseColor("#f5f5f5"));
        }

    }

    public void replaceAlbums(List<AlbumData> dataList){
        this.albumDataList = dataList;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return albumDataList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        LinearLayout rootItem;
        TextView albumIdTxt, albumTitleTxt;
        ImageView albumThumbnail;

        public MyHolder(View itemView) {
            super(itemView);

            rootItem = itemView.findViewById(R.id.bg_id);
            albumIdTxt = itemView.findViewById(R.id.albumId);
            albumTitleTxt = itemView.findViewById(R.id.album_title);
            albumThumbnail = itemView.findViewById(R.id.album_thumbnail);
        }
    }
}
