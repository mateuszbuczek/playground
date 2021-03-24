import {Injectable} from '@angular/core';
import {AsyncValidator, FormControl, ValidationErrors} from '@angular/forms';
import {Observable, of} from 'rxjs';
import {catchError, map} from 'rxjs/operators';
import {AuthService} from '../auth.service';

@Injectable({providedIn: 'root'})
export class UniqueUsername implements AsyncValidator {

  constructor(private authService: AuthService) {
  }

  validate = (control: FormControl): Promise<ValidationErrors | null> | Observable<ValidationErrors | null> => {
    const {value} = control;

    return this.authService.usernameAvailable(value).pipe(
      map(val => {
        if (val.available) {
          return null;
        }
      }),
      catchError(err => {
        // new Observable shortcut
        if (err.error.username) {
          return of({nonUniqueUsername: true});
        } else {
          return of({noConnection: true});
        }
      })
    );
  };
}
