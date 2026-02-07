import { Routes } from '@angular/router';
import { TabsPage } from './tabs.page';

export const tabsRoutes: Routes = [
  {
    path: '',
    component: TabsPage,
    children: [
      {
        path: 'notes',
        loadComponent: () =>
          import('../home/home.page').then((m) => m.HomePage),
      },
      {
        path: 'averages',
        loadComponent: () =>
          import('../pages/averages/averages.page').then((m) => m.AveragesPage),
      },
      {
        path: '',
        redirectTo: 'notes',
        pathMatch: 'full',
      },
    ],
  },
];
