package com.example.appnotes;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnotes.adapter.NoteAdapter;
import com.example.appnotes.database.NoteRepository;
import com.example.appnotes.model.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_NOTE_ID = "extra_note_id";
    public static final String EXTRA_NOTE_SCORE = "extra_note_score";
    public static final String EXTRA_NOTE_COURSE = "extra_note_course";
    public static final String EXTRA_NOTE_SEMESTER = "extra_note_semester";

    private NoteRepository repository;
    private NoteAdapter adapter;
    private TextView tvEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(R.string.app_name);

        tvEmpty = findViewById(R.id.tv_empty);
        FloatingActionButton fabAdd = findViewById(R.id.fab_add);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter = new NoteAdapter();
        recyclerView.setAdapter(adapter);

        repository = new NoteRepository(getApplication());

        repository.getAllNotes().observe(this, notes -> {
            adapter.setNotes(notes);
            tvEmpty.setVisibility(notes.isEmpty() ? View.VISIBLE : View.GONE);
        });

        adapter.setOnNoteClickListener(new NoteAdapter.OnNoteClickListener() {
            @Override
            public void onEditClick(Note note) {
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                intent.putExtra(EXTRA_NOTE_ID, note.getId());
                intent.putExtra(EXTRA_NOTE_SCORE, note.getScore());
                intent.putExtra(EXTRA_NOTE_COURSE, note.getCourse());
                intent.putExtra(EXTRA_NOTE_SEMESTER, note.getSemester());
                startActivity(intent);
            }

            @Override
            public void onDeleteClick(Note note) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(R.string.delete_title)
                        .setMessage(R.string.delete_message)
                        .setPositiveButton(R.string.yes, (dialog, which) -> repository.delete(note))
                        .setNegativeButton(R.string.no, null)
                        .show();
            }
        });

        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_averages) {
            startActivity(new Intent(this, AverageActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
