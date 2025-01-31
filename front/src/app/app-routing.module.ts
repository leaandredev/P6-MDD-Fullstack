import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UnauthGuard } from './core/guards/unauth.guard';
import { AuthGuard } from './core/guards/auth.guard';
import { TopicsComponent } from './features/topics/topics.component';
import { UserDetailsComponent } from './features/user-details/user-details.component';

const routes: Routes = [
  {
    path: '',
    canActivate: [UnauthGuard],
    loadChildren: () =>
      import('./features/auth/auth.module').then((m) => m.AuthModule),
  },
  {
    path: 'post',
    canActivate: [AuthGuard],
    loadChildren: () =>
      import('./features/post/post.module').then((m) => m.PostModule),
  },
  {
    path: 'topics',
    canActivate: [AuthGuard],
    component: TopicsComponent,
  },
  {
    path: 'user-details',
    canActivate: [AuthGuard],
    component: UserDetailsComponent,
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
