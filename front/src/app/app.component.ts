import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Component } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { combineLatest, filter, map } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  public title: string = 'front';
  public showHeader: boolean = true;

  constructor(private router: Router, private responsive: BreakpointObserver) {
    const url$ = this.router.events.pipe(
      filter((event) => event instanceof NavigationEnd),
      map((event: any) => event.url) // Capture l'URL actuelle
    );

    const isPhonePortrait$ = this.responsive
      .observe([Breakpoints.HandsetPortrait])
      .pipe(map((result) => result.matches)); // True si téléphone en portrait

    // Combine les deux streams
    combineLatest([url$, isPhonePortrait$]).subscribe(
      ([url, isPhonePortrait]) => {
        if (
          url === '/' ||
          (isPhonePortrait && (url === '/register' || url === '/login'))
        ) {
          this.showHeader = false;
        } else {
          this.showHeader = true;
        }
      }
    );
  }
}
