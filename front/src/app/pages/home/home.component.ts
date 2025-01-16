import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit {
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

  start() {
    alert('Commencez par lire le README et à vous de jouer !');
  }
}
