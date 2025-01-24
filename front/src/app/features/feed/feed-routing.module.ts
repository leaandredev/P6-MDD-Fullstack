import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FeedComponent } from './components/feed/feed.component';
import { TopicsComponent } from './components/topics/topics.component';
import { UserDetailsComponent } from './components/user-details/user-details.component';

const routes: Routes = [
  { title: 'Feed', path: '', component: FeedComponent },
  { title: 'Topics', path: 'topics', component: TopicsComponent },
  {
    title: 'UserDetails',
    path: 'user-details',
    component: UserDetailsComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class FeedRoutingModule {}
