import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {
  IonHeader,
  IonToolbar,
  IonTitle,
  IonContent,
  IonList,
  IonItem,
  IonLabel,
  IonItemSliding,
  IonItemOptions,
  IonItemOption,
  IonFab,
  IonFabButton,
  IonIcon,
  IonBadge,
  AlertController,
} from '@ionic/angular/standalone';
import { addIcons } from 'ionicons';
import { addOutline, createOutline, trashOutline, libraryOutline } from 'ionicons/icons';
import { NgFor, NgIf } from '@angular/common';
import { NotesService } from '../services/notes.service';
import { Note } from '../models/note.model';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
  imports: [
    IonHeader,
    IonToolbar,
    IonTitle,
    IonContent,
    IonList,
    IonItem,
    IonLabel,
    IonItemSliding,
    IonItemOptions,
    IonItemOption,
    IonFab,
    IonFabButton,
    IonIcon,
    IonBadge,
    NgFor,
    NgIf,
  ],
})
export class HomePage implements OnInit {
  notes: Note[] = [];

  constructor(
    private notesService: NotesService,
    private router: Router,
    private alertController: AlertController
  ) {
    addIcons({ addOutline, createOutline, trashOutline, libraryOutline });
  }

  async ngOnInit() {
    await this.loadNotes();
  }

  async ionViewWillEnter() {
    await this.loadNotes();
  }

  async loadNotes() {
    this.notes = await this.notesService.loadNotes();
  }

  addNote() {
    this.router.navigate(['/note-form']);
  }

  editNote(note: Note) {
    this.router.navigate(['/note-form', note.id]);
  }

  async deleteNote(note: Note) {
    const alert = await this.alertController.create({
      header: 'Confirmer la suppression',
      message: `Voulez-vous supprimer la note de ${note.course} ?`,
      buttons: [
        {
          text: 'Annuler',
          role: 'cancel',
        },
        {
          text: 'Supprimer',
          role: 'destructive',
          handler: async () => {
            await this.notesService.deleteNote(note.id);
            await this.loadNotes();
          },
        },
      ],
    });
    await alert.present();
  }
}
