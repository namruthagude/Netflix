package com.example.netflix.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.netflix.MainScreens.MovieDetails;
import com.example.netflix.Model.CategoryItemList;
import com.example.netflix.R;

import java.util.ArrayList;
import java.util.List;

public class ItemRecyclerAdapter extends RecyclerView.Adapter<ItemRecyclerAdapter.ItemViewHolder> {
    Context context;
    List<CategoryItemList> categoryItemLists;
    List<CategoryItemList> movieListAll;

    public ItemRecyclerAdapter(Context context, List<CategoryItemList> categoryItemLists) {
        this.context = context;
        this.categoryItemLists = categoryItemLists;
        this.movieListAll = new ArrayList<>(categoryItemLists);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.category_row_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Glide.with(context).load(categoryItemLists.get(position).getImageUrl()).into(holder.itemImage);
        holder.itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, MovieDetails.class);
                i.putExtra("movieid",categoryItemLists.get(position).getId());
                i.putExtra("moviename",categoryItemLists.get(position).getMovieName());
                i.putExtra("movieimageurl",categoryItemLists.get(position).getImageUrl());
                i.putExtra("moviefile",categoryItemLists.get(position).getFileUrl());
                context.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryItemLists.size();
    }

    public static final class ItemViewHolder extends RecyclerView.ViewHolder
    {
        ImageView itemImage;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.item_image);
        }
    }
}
