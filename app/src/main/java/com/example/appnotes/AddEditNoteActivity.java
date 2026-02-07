package com.example.appnotes;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appnotes.database.NoteRepository;
import com.example.appnotes.model.Note;
import com.google.android.material.textfield.TextInputEditText;

public class AddEditNoteActivity extends AppCompatActivity {

    private TextInputEditText etScore, etCourse;
    private AutoCompleteTextView spinnerSemester;
    private NoteRepository repository;
    private int noteId = -1;

    private static final String[] SEMESTERS = {
            "S1", "S2", "S3", "S4", "S5", "S6", "S7", "S8", "S9", "S10"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_note);

        etScore = findViewById(R.id.et_score);
        etCourse = findViewById(R.id.et_course);
        spinnerSemester = findViewById(R.id.spinner_semester);
        Button btnSave = findViewById(R.id.btn_save);

        repository = new NoteRepository(getApplication());

        ArrayAdapter<String> semesterAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_dropdown_item_1line, SEMESTERS);
        spinnerSemester.setAdapter(semesterAdapter);

        if (getIntent().hasExtra(MainActivity.EXTRA_NOTE_ID)) {
            setTitle(R.string.edit_note);
            noteId = getIntent().getIntExtra(MainActivity.EXTRA_NOTE_ID, -1);
            double score = getIntent().getDoubleExtra(MainActivity.EXTRA_NOTE_SCORE, 0);
            String course = getIntent().getStringExtra(MainActivity.EXTRA_NOTE_COURSE);
            String semester = getIntent().getStringExtra(MainActivity.EXTRA_NOTE_SEMESTER);

            etScore.setText(String.valueOf(score));
            etCourse.setText(course);
            spinnerSemester.setText(semester, false);
        } else {
            setTitle(R.string.add_note);
        }

        btnSave.setOnClickListener(v -> saveNote());

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void saveNote() {
        String scoreStr = etScore.getText() != null ? etScore.getText().toString().trim() : "";
        String course = etCourse.getText() != null ? etCourse.getText().toString().trim() : "";
        String semester = spinnerSemester.getText() != null ? spinnerSemester.getText().toString().trim() : "";

        if (scoreStr.isEmpty() || course.isEmpty() || semester.isEmpty()) {
            Toast.makeText(this, R.string.fill_all_fields, Toast.LENGTH_SHORT).show();
            return;
        }

        double score;
        try {
            score = Double.parseDouble(scoreStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, R.string.invalid_score, Toast.LENGTH_SHORT).show();
            return;
        }

        if (score < 0 || score > 20) {
            Toast.makeText(this, R.string.score_range, Toast.LENGTH_SHORT).show();
            return;
        }

        Note note = new Note(score, course, semester);

        if (noteId != -1) {
            note.setId(noteId);
            repository.update(note);
            Toast.makeText(this, R.string.note_updated, Toast.LENGTH_SHORT).show();
        } else {
            repository.insert(note);
            Toast.makeText(this, R.string.note_added, Toast.LENGTH_SHORT).show();
        }

        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
