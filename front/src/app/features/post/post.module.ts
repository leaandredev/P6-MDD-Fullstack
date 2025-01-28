import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PostRoutingModule } from './post-routing.module';
import { FeedComponent } from './components/feed/feed.component';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LayoutModule } from '@angular/cdk/layout';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

const materialModule = [
  MatButtonModule,
  MatCardModule,
  MatIconModule,
  MatSnackBarModule,
  MatToolbarModule,
  MatFormFieldModule,
  MatInputModule,
  MatSidenavModule,
  MatListModule,
];

@NgModule({
  declarations: [FeedComponent],
  imports: [
    PostRoutingModule,
    LayoutModule,
    FormsModule,
    ReactiveFormsModule,
    ...materialModule,
  ],
})
export class PostModule {}
