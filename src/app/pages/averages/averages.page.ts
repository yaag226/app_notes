import { Component, OnInit } from '@angular/core';
import {
  IonHeader,
  IonToolbar,
  IonTitle,
  IonContent,
  IonCard,
  IonCardHeader,
  IonCardTitle,
  IonCardContent,
  IonList,
  IonItem,
  IonLabel,
  IonBadge,
  IonIcon,
} from '@ionic/angular/standalone';
import { NgFor, NgIf, DecimalPipe } from '@angular/common';
import { addIcons } from 'ionicons';
import { schoolOutline, trophyOutline } from 'ionicons/icons';
import { NotesService } from '../../services/notes.service';

@Component({
  selector: 'app-averages',
  templateUrl: 'averages.page.html',
  styleUrls: ['averages.page.scss'],
  imports: [
    IonHeader,
    IonToolbar,
    IonTitle,
    IonContent,
    IonCard,
    IonCardHeader,
    IonCardTitle,
    IonCardContent,
    IonList,
    IonItem,
    IonLabel,
    IonBadge,
    IonIcon,
    NgFor,
    NgIf,
    DecimalPipe,
  ],
})
export class AveragesPage implements OnInit {
  generalAverage = 0;
  semesters: { name: string; average: number; count: number }[] = [];
  totalNotes = 0;

  constructor(private notesService: NotesService) {
    addIcons({ schoolOutline, trophyOutline });
  }

  async ngOnInit() {
    await this.loadAverages();
  }

  async ionViewWillEnter() {
    await this.loadAverages();
  }

  async loadAverages() {
    await this.notesService.loadNotes();
    this.totalNotes = this.notesService.getNotes().length;
    this.generalAverage = this.notesService.getGeneralAverage();

    const semesterNames = this.notesService.getSemesters();
    this.semesters = semesterNames.map((name) => ({
      name,
      average: this.notesService.getAverageBySemester(name),
      count: this.notesService.getNotes().filter((n) => n.semester === name).length,
    }));
  }
}
