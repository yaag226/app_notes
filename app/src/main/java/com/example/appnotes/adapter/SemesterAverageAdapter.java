package com.example.appnotes.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnotes.R;

import java.util.Locale;

public class SemesterAverageAdapter extends RecyclerView.Adapter<SemesterAverageAdapter.ViewHolder> {

    private final SemesterAverage[] averages;

    public static class SemesterAverage {
        public final String semester;
        public final double average;

        public SemesterAverage(String semester, double average) {
            this.semester = semester;
            this.average = average;
        }
    }

    public SemesterAverageAdapter(SemesterAverage[] averages) {
        this.averages = averages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_semester_average, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SemesterAverage item = averages[position];
        holder.tvSemesterName.setText(item.semester);
        holder.tvSemesterAvg.setText(String.format(Locale.getDefault(), "%.2f / 20", item.average));
    }

    @Override
    public int getItemCount() {
        return averages.length;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvSemesterName, tvSemesterAvg;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSemesterName = itemView.findViewById(R.id.tv_semester_name);
            tvSemesterAvg = itemView.findViewById(R.id.tv_semester_avg);
        }
    }
}
