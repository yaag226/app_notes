import { Injectable } from '@angular/core';
import { Preferences } from '@capacitor/preferences';
import { Note } from '../models/note.model';

const STORAGE_KEY = 'notes';

@Injectable({
  providedIn: 'root',
})
export class NotesService {
  private notes: Note[] = [];

  async loadNotes(): Promise<Note[]> {
    const { value } = await Preferences.get({ key: STORAGE_KEY });
    this.notes = value ? JSON.parse(value) : [];
    return this.notes;
  }

  private async saveNotes(): Promise<void> {
    await Preferences.set({
      key: STORAGE_KEY,
      value: JSON.stringify(this.notes),
    });
  }

  async addNote(note: Omit<Note, 'id'>): Promise<void> {
    const newNote: Note = {
      ...note,
      id: Date.now().toString(),
    };
    this.notes.push(newNote);
    await this.saveNotes();
  }

  async updateNote(id: string, updated: Omit<Note, 'id'>): Promise<void> {
    const index = this.notes.findIndex((n) => n.id === id);
    if (index !== -1) {
      this.notes[index] = { ...updated, id };
      await this.saveNotes();
    }
  }

  async deleteNote(id: string): Promise<void> {
    this.notes = this.notes.filter((n) => n.id !== id);
    await this.saveNotes();
  }

  getNoteById(id: string): Note | undefined {
    return this.notes.find((n) => n.id === id);
  }

  getNotes(): Note[] {
    return this.notes;
  }

  getGeneralAverage(): number {
    if (this.notes.length === 0) return 0;
    const sum = this.notes.reduce((acc, n) => acc + n.score, 0);
    return sum / this.notes.length;
  }

  getSemesters(): string[] {
    return [...new Set(this.notes.map((n) => n.semester))].sort();
  }

  getAverageBySemester(semester: string): number {
    const semesterNotes = this.notes.filter((n) => n.semester === semester);
    if (semesterNotes.length === 0) return 0;
    const sum = semesterNotes.reduce((acc, n) => acc + n.score, 0);
    return sum / semesterNotes.length;
  }
}
