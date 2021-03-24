import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup} from '@angular/forms';
import {CustomValidators} from '../custom-validators';
import {delay, filter, scan} from 'rxjs/operators';

@Component({
  selector: 'app-equation',
  templateUrl: './equation.component.html',
  styleUrls: ['./equation.component.css']
})
export class EquationComponent implements OnInit {
  secondsPerSolution = 0;


  mathForm = new FormGroup({
    a: new FormControl(this.randomDigit()),
    b: new FormControl(this.randomDigit()),
    answer: new FormControl('')
  }, [CustomValidators.addition('a', 'b', 'answer')]);

  constructor() {
  }

  ngOnInit(): void {
    this.mathForm.statusChanges.pipe(
      filter(value => value === 'VALID'),
      delay(100),
      scan((acc, value) => {
        return {
          numberSolved: acc.numberSolved + 1,
          startTime: acc.startTime
        };
      }, {numberSolved: 0, startTime: new Date()})
    ).subscribe(({numberSolved, startTime}) => {
      this.secondsPerSolution = (
        new Date().getTime() - startTime.getTime()
      ) / numberSolved / 1000;

      this.mathForm.setValue({
        a: this.randomDigit(),
        b: this.randomDigit(),
        answer: ''
      });
    });
  }

  private onCorrectAnswer() {

  }

  get a() {
    return this.mathForm.controls.a.value;
  }

  get b() {
    return this.mathForm.controls.b.value;
  }

  randomDigit() {
    return Math.floor(Math.random() * 10);
  }
}
