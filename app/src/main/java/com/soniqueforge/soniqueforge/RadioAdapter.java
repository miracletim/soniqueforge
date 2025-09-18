package com.soniqueforge.soniqueforge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RadioAdapter extends RecyclerView.Adapter<RadioAdapter.RadioViewHolder> {

    private int currentlyPlayingPosition = -1;
    private Context context;
    private List<Radio> radioList;
    private OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(Radio radio, int position);
    }
    public RadioAdapter(Context context, List<Radio> radioList, OnItemClickListener listener) {
        this.context = context;
        this.radioList = radioList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RadioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_radio, parent, false);
        return new RadioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RadioViewHolder holder, int position) {
        Radio radio = radioList.get(position);
        holder.bind(radio, listener);

        if(position == currentlyPlayingPosition) {
            holder.itemView.setBackgroundColor(
                    context.getResources().getColor(android.R.color.holo_blue_light)
            );
//            holder.name.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.radio, 0);
        }else {
            holder.itemView.setBackgroundColor(
            context.getResources().getColor(android.R.color.transparent)
            );
//            holder.name.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
        }
    }

    @Override
    public int getItemCount() {
        return radioList.size();
    }
    public static class RadioViewHolder extends RecyclerView.ViewHolder {
        TextView name, frequency, location;
        public RadioViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvRadioName);
            frequency = itemView.findViewById(R.id.tvRadioFrequency);
            location = itemView.findViewById(R.id.tvRadioLocation);
        }

        public void bind(final Radio radio, final OnItemClickListener listener) {
            name.setText(radio.getName());
            frequency.setText(radio.getFrequency());
            location.setText(radio.getLocation());

            itemView.setOnClickListener(v -> listener.onItemClick(radio, getAbsoluteAdapterPosition()));
        }
    }

    public void setCurrentlyPlaying(int position) {
        int prevPlaying = currentlyPlayingPosition;
        currentlyPlayingPosition = position;

        // Refresh old + new item so UI updates
        if (prevPlaying != -1) notifyItemChanged(prevPlaying);
        notifyItemChanged(currentlyPlayingPosition);
    }

}
