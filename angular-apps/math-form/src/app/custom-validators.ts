import {AbstractControl} from '@angular/forms';

export class CustomValidators {

  static addition(a: string, b: string, c: string) {
    return (form: AbstractControl) => {
      const first = form.value[a];
      const second = form.value[b];
      const sum = form.value[c];
      if (first + second === parseInt(sum, 10)) {
        return null;
      }
      return {addition: true};
    };
}

  static subtraction() {

  }
}
