package com.example.course_work_2.adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.course_work_2.Activities.EditActivity;
import com.example.course_work_2.Database.AppDatabase;
import com.example.course_work_2.Models.Hike;
import com.example.course_work_2.R;

import java.util.List;

public class HikeAdapter extends RecyclerView.Adapter<HikeAdapter.HikeViewHolder>{
    private List<Hike> hikes;
    private AppDatabase appDatabase;

    public HikeAdapter(List<Hike> hikes, AppDatabase appDatabase) {
        this.hikes = hikes;
        this.appDatabase = appDatabase;
    }

    public void deleteAll(List<Hike> hikes, AppDatabase appDatabase) {
        appDatabase.hikeDao().deleteAll(hikes);
        hikes.clear();
        notifyDataSetChanged();

        //test push
    }


    @NonNull
    @Override
    public HikeAdapter.HikeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hike_card, parent, false);
        return new HikeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HikeAdapter.HikeViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Hike hike = hikes.get(position);
        holder.hikeName.setText(hike.name);
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hikes.remove(position);
                notifyItemRemoved(position);
                appDatabase.hikeDao().delete(hike);
            }
        });
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EditActivity.class);
                intent.putExtra("hikeId", hike.id);
                intent.putExtra("position", position);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return hikes.size();
    }

    public class HikeViewHolder extends RecyclerView.ViewHolder {
        TextView hikeName;
        Button deleteButton;

        Button editButton;

        public HikeViewHolder(@NonNull View itemView) {
            super(itemView);
            hikeName = itemView.findViewById(R.id.hike_name);
            deleteButton = itemView.findViewById(R.id.delete_button);
            editButton = itemView.findViewById(R.id.edit_button);
        }
    }
}
