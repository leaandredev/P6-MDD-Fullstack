import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent implements OnInit {
  handsetPortrait: boolean = false;

  constructor(private responsive: BreakpointObserver) {}

  ngOnInit(): void {
    this.responsive
      .observe([Breakpoints.HandsetPortrait])
      .subscribe((result) => {
        if (result.matches) {
          console.log('Mon téléphone est en mode portrait');
          this.handsetPortrait = true;
        } else {
          this.handsetPortrait = false;
        }
      });
  }

  public back() {
    window.history.back();
  }
}
