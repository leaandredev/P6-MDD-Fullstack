import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './features/auth/components/home/home.component';
import { UnauthGuard } from './guards/unauth.guard';
import { AuthGuard } from './guards/auth.guard';
import { UserDetailsComponent } from './components/user-details/user-details.component';

const routes: Routes = [
  {
    path: '',
    canActivate: [UnauthGuard],
    loadChildren: () =>
      import('./features/auth/auth.module').then((m) => m.AuthModule),
  },
  {
    path: 'feed',
    canActivate: [AuthGuard],
    loadChildren: () =>
      import('./features/feed/feed.module').then((m) => m.FeedModule),
  },
  {
      title: 'UserDetails',
      path: 'user-details',
      component: UserDetailsComponent,
    },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
