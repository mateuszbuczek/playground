import {FormControl} from '@angular/forms';

/*
* Custom control for expiration date
* */
export class DateFormControl extends FormControl {
  setValue(value: string | null, options?: any): void {
    if (!value) {
      super.setValue(null, {...options, emitModelToViewChange: true});
      return;
    }

    if (value.match(/[^0-9|\/]/gi) || value.length > 5) {
      super.setValue(this.value, {...options, emitModelToViewChange: true});
      return;
    }

    if (value.length === 3 && this.value.length === 4) {
      super.setValue(value.substr(0, 2), {...options, emitModelToViewChange: true});
      return;
    }

    if (value.length === 2) {
      super.setValue(value + '/', {...options, emitModelToViewChange: true});
      return;
    }

    super.setValue(value, {...options, emitModelToViewChange: true});
  }
}
