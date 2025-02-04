import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-unlogged-layout',
  templateUrl: './unlogged-layout.component.html',
  styleUrls: ['./unlogged-layout.component.scss'],
})
export class UnloggedLayoutComponent {
  public handsetPortrait: boolean = false;

  constructor(private router: Router, private responsive: BreakpointObserver) {
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

  get showHeader() {
    return this.router.url !== '/' && !this.handsetPortrait;
  }

  get showBackButton() {
    return this.router.url !== '/';
  }

  public back() {
    window.history.back();
  }
}
