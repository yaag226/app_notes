import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    loadChildren: () =>
      import('./tabs/tabs.routes').then((m) => m.tabsRoutes),
  },
  {
    path: 'note-form',
    loadComponent: () =>
      import('./pages/note-form/note-form.page').then((m) => m.NoteFormPage),
  },
  {
    path: 'note-form/:id',
    loadComponent: () =>
      import('./pages/note-form/note-form.page').then((m) => m.NoteFormPage),
  },
];
