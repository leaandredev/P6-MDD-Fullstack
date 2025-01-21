import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SessionService } from 'src/app/services/session.service';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.scss'],
})
export class UserDetailsComponent implements OnInit {
  public form: FormGroup;

  constructor(private sessionService: SessionService, private fb: FormBuilder) {
    this.form = this.fb.group({
      userName: [
        this.sessionService.sessionInformation?.userName,
        [
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(20),
        ],
      ],
      email: [
        this.sessionService.sessionInformation?.email,
        [Validators.required, Validators.email],
      ],
    });
  }

  ngOnInit(): void {}
}
