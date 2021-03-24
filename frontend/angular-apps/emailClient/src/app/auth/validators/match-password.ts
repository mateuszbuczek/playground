import {FormGroup, ValidationErrors, Validator} from '@angular/forms';
import {Injectable} from '@angular/core';

@Injectable({ providedIn: 'root'})
export class MatchPassword implements Validator {

  validate(form: FormGroup): ValidationErrors | null {
    const {password, passwordConfirmation} = form.value;

    if (password !== passwordConfirmation) {
      return {passwordsDontMatch: true};
    }

    return null;
  }
}
