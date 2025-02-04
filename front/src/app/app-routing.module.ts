import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UnauthGuard } from './core/guards/unauth.guard';
import { AuthGuard } from './core/guards/auth.guard';
import { TopicsComponent } from './features/topics/topics.component';
import { UserDetailsComponent } from './features/user-details/user-details.component';
import { LoggedLayoutComponent } from './core/layouts/logged-layout/logged-layout.component';
import { UnloggedLayoutComponent } from './core/layouts/unlogged-layout/unlogged-layout.component';

const routes: Routes = [
  {
    path: '',
    component: UnloggedLayoutComponent,
    canActivate: [UnauthGuard],
    loadChildren: () =>
      import('./features/auth/auth.module').then((m) => m.AuthModule),
  },
  {
    path: '',
    component: LoggedLayoutComponent,
    canActivate: [AuthGuard],
    children: [
      {
        path: 'post',
        loadChildren: () =>
          import('./features/post/post.module').then((m) => m.PostModule),
      },
      {
        path: 'topics',
        component: TopicsComponent,
      },
      {
        path: 'user-details',
        component: UserDetailsComponent,
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
