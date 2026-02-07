package com.example.appnotes;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnotes.adapter.SemesterAverageAdapter;
import com.example.appnotes.database.NoteRepository;

import java.util.Locale;
import java.util.concurrent.Executors;

public class AverageActivity extends AppCompatActivity {

    private TextView tvOverallAverage, tvNoData;
    private RecyclerView recyclerSemesters;
    private NoteRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_average);

        setTitle(R.string.averages);

        tvOverallAverage = findViewById(R.id.tv_overall_average);
        tvNoData = findViewById(R.id.tv_no_data);
        recyclerSemesters = findViewById(R.id.recycler_semesters);

        recyclerSemesters.setLayoutManager(new LinearLayoutManager(this));

        repository = new NoteRepository(getApplication());

        repository.getOverallAverage().observe(this, average -> {
            if (average != null) {
                tvOverallAverage.setText(String.format(Locale.getDefault(),
                        "%.2f / 20", average));
                tvNoData.setVisibility(View.GONE);
            } else {
                tvOverallAverage.setText("-- / 20");
                tvNoData.setVisibility(View.VISIBLE);
            }
        });

        repository.getAllSemesters().observe(this, semesters -> {
            if (semesters != null && !semesters.isEmpty()) {
                Executors.newSingleThreadExecutor().execute(() -> {
                    SemesterAverageAdapter.SemesterAverage[] averages =
                            new SemesterAverageAdapter.SemesterAverage[semesters.size()];
                    for (int i = 0; i < semesters.size(); i++) {
                        String sem = semesters.get(i);
                        double avg = repository.getAverageBySemester(sem);
                        averages[i] = new SemesterAverageAdapter.SemesterAverage(sem, avg);
                    }
                    runOnUiThread(() -> {
                        SemesterAverageAdapter adapter = new SemesterAverageAdapter(averages);
                        recyclerSemesters.setAdapter(adapter);
                    });
                });
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
