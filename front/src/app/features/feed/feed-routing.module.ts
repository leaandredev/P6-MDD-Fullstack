import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FeedComponent } from './components/feed/feed.component';
import { TopicsComponent } from './components/topics/topics.component';

const routes: Routes = [
  { title: 'Feed', path: '', component: FeedComponent },
  { title: 'Topics', path: 'topics', component: TopicsComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class FeedRoutingModule {}
