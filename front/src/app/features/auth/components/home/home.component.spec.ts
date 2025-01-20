import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeComponent } from './home.component';
import {
  BreakpointObserver,
  Breakpoints,
  BreakpointState,
} from '@angular/cdk/layout';
import { of } from 'rxjs';
import '@testing-library/jest-dom';
import { Location } from '@angular/common';

import { fireEvent, screen } from '@testing-library/angular';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientModule } from '@angular/common/http';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { MockComponent } from 'src/app/testing/mock.component';
import { MatToolbarModule } from '@angular/material/toolbar';

describe('HomeComponent', () => {
  let component: HomeComponent;
  let fixture: ComponentFixture<HomeComponent>;

  const mockBreakpointState: BreakpointState = {
    matches: true,
    breakpoints: {},
  };

  const mockBreakpointObserver: jest.Mocked<BreakpointObserver> = {
    observe: jest.fn().mockReturnValue(of(mockBreakpointState)),
  } as unknown as jest.Mocked<BreakpointObserver>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [HomeComponent],
      imports: [
        RouterTestingModule.withRoutes([
          { path: 'register', component: MockComponent },
          { path: 'login', component: MockComponent },
        ]),
        HttpClientModule,
        MatToolbarModule,
        NoopAnimationsModule,
      ],
      providers: [
        { provide: BreakpointObserver, useValue: mockBreakpointObserver },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(HomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  // Integration test
  describe('integration test', () => {
    it('should display Subscribe and Login buttons', async () => {
      const subscribeButton = screen.queryByText('Se connecter');
      const loginButton = screen.queryByText("S'inscrire");

      expect(subscribeButton).toBeInTheDocument();
      expect(loginButton).toBeInTheDocument();
    });

    it('should navigate to "/register" when Register button is clicked', async () => {
      const location = TestBed.inject(Location);

      const registerButton = screen.queryByText("S'inscrire");
      fireEvent.click(registerButton as HTMLElement);
      fixture.detectChanges();

      expect(location.path()).toBe('/register');
    });
  });

  // Unit test
  describe('unit test', () => {
    it('should set handsetPortrait to true when Breakpoints.HandsetPortrait matches', () => {
      mockBreakpointState.matches = true;
      fixture.detectChanges();

      component.ngOnInit();

      expect(mockBreakpointObserver.observe).toHaveBeenCalledWith([
        Breakpoints.HandsetPortrait,
      ]);
      expect(component.handsetPortrait).toBe(true);
    });

    it('should set handsetPortrait to false when Breakpoints.HandsetPortrait does not match', () => {
      mockBreakpointState.matches = false;
      fixture.detectChanges();

      component.ngOnInit();

      expect(mockBreakpointObserver.observe).toHaveBeenCalledWith([
        Breakpoints.HandsetPortrait,
      ]);
      expect(component.handsetPortrait).toBe(false);
    });
  });
});
