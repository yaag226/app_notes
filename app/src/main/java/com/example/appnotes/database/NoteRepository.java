package com.example.appnotes.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.appnotes.model.Note;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NoteRepository {

    private final NoteDao noteDao;
    private final LiveData<List<Note>> allNotes;
    private final LiveData<Double> overallAverage;
    private final LiveData<List<String>> allSemesters;
    private final ExecutorService executor;

    public NoteRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        noteDao = database.noteDao();
        allNotes = noteDao.getAllNotes();
        overallAverage = noteDao.getOverallAverage();
        allSemesters = noteDao.getAllSemesters();
        executor = Executors.newSingleThreadExecutor();
    }

    public void insert(Note note) {
        executor.execute(() -> noteDao.insert(note));
    }

    public void update(Note note) {
        executor.execute(() -> noteDao.update(note));
    }

    public void delete(Note note) {
        executor.execute(() -> noteDao.delete(note));
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    public LiveData<Double> getOverallAverage() {
        return overallAverage;
    }

    public LiveData<List<String>> getAllSemesters() {
        return allSemesters;
    }

    public double getAverageBySemester(String semester) {
        return noteDao.getAverageBySemester(semester);
    }
}
