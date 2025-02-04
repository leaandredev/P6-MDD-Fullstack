import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Component } from '@angular/core';

@Component({
  selector: 'app-logged-layout',
  templateUrl: './logged-layout.component.html',
  styleUrls: ['./logged-layout.component.scss'],
})
export class LoggedLayoutComponent {
  public handsetPortrait: boolean = false;

  constructor(private responsive: BreakpointObserver) {
    this.responsive
      .observe([Breakpoints.HandsetPortrait])
      .subscribe((result) => {
        if (result.matches) {
          this.handsetPortrait = true;
        } else {
          this.handsetPortrait = false;
        }
      });
  }
}
