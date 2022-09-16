package com.devtitans.titanstationapp.view;

import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.devtitans.titanstationapp.databinding.FragmentItemBinding;
import com.devtitans.titanstationapp.model.Sensor;

import java.util.List;


public class SensorRecyclerViewAdapter extends RecyclerView.Adapter<SensorRecyclerViewAdapter.ViewHolder> {

    private final List<Sensor> mValues;

    public SensorRecyclerViewAdapter(List<Sensor> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getName());
        holder.mContentView.setText(mValues.get(position).getValue());

        holder.mIdView.setTextColor(mValues.get(position).getColor());
        holder.mContentView.setTextColor(mValues.get(position).getColor());
        holder.image.setImageResource(mValues.get(position).getImage());
        holder.image.setColorFilter(mValues.get(position).getColor());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;
        public final ImageView image;
        public Sensor mItem;

        public ViewHolder(FragmentItemBinding binding) {
            super(binding.getRoot());
            mIdView = binding.sensorName;
            mContentView = binding.sensorValue;
            image = binding.imageView;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}