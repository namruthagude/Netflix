package com.example.netflix.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.netflix.Model.AllCategory;
import com.example.netflix.Model.CategoryItemList;
import com.example.netflix.R;

import java.util.List;

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.MainViewHolder> {
    Context context;
    List<AllCategory> allCategoryList;

    public MainRecyclerAdapter(Context context, List<AllCategory> allCategoryList) {
        this.context = context;
        this.allCategoryList = allCategoryList;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(context).inflate(R.layout.mainrecyclerlayout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        holder.categoryname.setText(allCategoryList.get(position).getCategoryTitle());
        setItemRecycler(holder.itemrecycler,allCategoryList.get(position).getCategoryItemList());

    }

    @Override
    public int getItemCount() {
        return allCategoryList.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {
        TextView categoryname;
        RecyclerView itemrecycler;


        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryname = itemView.findViewById(R.id.itemcategory);
            itemrecycler = itemView.findViewById(R.id.item_recyler);
        }
    }
    public  void setItemRecycler(RecyclerView recyclerView, List<CategoryItemList> categoryItemLists)
    {
        ItemRecyclerAdapter itemRecyclerAdapter = new ItemRecyclerAdapter(context,categoryItemLists);
        recyclerView.setLayoutManager(new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false));
        recyclerView.setAdapter(itemRecyclerAdapter);
    }

}
