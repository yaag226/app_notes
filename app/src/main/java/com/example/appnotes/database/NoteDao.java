package com.example.appnotes.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.appnotes.model.Note;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("SELECT * FROM notes ORDER BY semester, course")
    LiveData<List<Note>> getAllNotes();

    @Query("SELECT * FROM notes WHERE id = :id")
    Note getNoteById(int id);

    @Query("SELECT AVG(score) FROM notes")
    LiveData<Double> getOverallAverage();

    @Query("SELECT AVG(score) FROM notes WHERE semester = :semester")
    double getAverageBySemester(String semester);

    @Query("SELECT DISTINCT semester FROM notes ORDER BY semester")
    LiveData<List<String>> getAllSemesters();
}
