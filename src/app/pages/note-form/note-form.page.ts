import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import {
  IonHeader,
  IonToolbar,
  IonTitle,
  IonContent,
  IonButtons,
  IonBackButton,
  IonItem,
  IonInput,
  IonButton,
  IonSelect,
  IonSelectOption,
  ToastController,
} from '@ionic/angular/standalone';
import { NgFor } from '@angular/common';
import { NotesService } from '../../services/notes.service';
import { Note } from '../../models/note.model';

@Component({
  selector: 'app-note-form',
  templateUrl: 'note-form.page.html',
  styleUrls: ['note-form.page.scss'],
  imports: [
    IonHeader,
    IonToolbar,
    IonTitle,
    IonContent,
    IonButtons,
    IonBackButton,
    IonItem,
    IonInput,
    IonButton,
    IonSelect,
    IonSelectOption,
    FormsModule,
    NgFor,
  ],
})
export class NoteFormPage implements OnInit {
  noteId: string | null = null;
  score: number | null = null;
  course = '';
  semester = '';
  isEdit = false;

  semesters = ['S1', 'S2', 'S3', 'S4', 'S5', 'S6', 'S7', 'S8', 'S9', 'S10'];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private notesService: NotesService,
    private toastController: ToastController
  ) {}

  async ngOnInit() {
    await this.notesService.loadNotes();
    this.noteId = this.route.snapshot.paramMap.get('id');
    if (this.noteId) {
      this.isEdit = true;
      const note = this.notesService.getNoteById(this.noteId);
      if (note) {
        this.score = note.score;
        this.course = note.course;
        this.semester = note.semester;
      }
    }
  }

  async saveNote() {
    if (this.score === null || this.score < 0 || this.score > 20) {
      await this.showToast('La note doit etre entre 0 et 20');
      return;
    }
    if (!this.course.trim()) {
      await this.showToast('Le nom du cours est requis');
      return;
    }
    if (!this.semester) {
      await this.showToast('Le semestre est requis');
      return;
    }

    const noteData = {
      score: this.score,
      course: this.course.trim(),
      semester: this.semester,
    };

    if (this.isEdit && this.noteId) {
      await this.notesService.updateNote(this.noteId, noteData);
      await this.showToast('Note modifiee avec succes');
    } else {
      await this.notesService.addNote(noteData);
      await this.showToast('Note ajoutee avec succes');
    }

    this.router.navigate(['/']);
  }

  private async showToast(message: string) {
    const toast = await this.toastController.create({
      message,
      duration: 2000,
      position: 'bottom',
    });
    await toast.present();
  }
}
