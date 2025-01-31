import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FeedComponent } from './components/feed/feed.component';
import { FormComponent } from './components/form/form.component';
import { PostDetailComponent } from './components/post-detail/post-detail.component';

const routes: Routes = [
  { title: 'Post - Feed', path: '', component: FeedComponent },
  { title: 'Post - Create', path: 'create', component: FormComponent },
  {
    title: 'Post - Detail',
    path: 'detail/:id',
    component: PostDetailComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PostRoutingModule {}
