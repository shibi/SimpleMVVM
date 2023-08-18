package com.bzcode.simplemvvm.domain.utils.list_utils;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class DataBoundViewHolder extends RecyclerView.ViewHolder {

    public DataBoundViewHolder(@NonNull View itemView) {
        super(itemView);
        try {

            bindView(itemView);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public abstract void bindView(View view);
}
