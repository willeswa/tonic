package com.example.android.quakereport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.MainViewHolder>{
    private final List<TonicQuake> tonicData;
    private final LayoutInflater mInflater;

    public MainRecyclerAdapter(List<TonicQuake> tonicData, Context context) {
        this.tonicData = tonicData;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_item, parent, false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        TonicQuake quake = tonicData.get(position);
        holder.magnitude.setText(quake.getMagnitude());
        holder.date.setText(quake.getDate());
        holder.place.setText(quake.getPlace());
    }

    @Override
    public int getItemCount() {
        return this.tonicData.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {
        private TextView magnitude;
        private TextView place;
        private TextView date;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            magnitude = itemView.findViewById(R.id.mag_view);
            place = itemView.findViewById(R.id.location_view);
            date = itemView.findViewById(R.id.date_view);

        }
    }
}
