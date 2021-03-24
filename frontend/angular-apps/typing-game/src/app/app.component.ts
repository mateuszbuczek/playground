import {Component} from '@angular/core';
import {lorem} from 'faker';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  randomText = lorem.sentence();
  enteredText = '';

  onInput(value: string) {
    this.enteredText = value;
  }

  compare(randomTextElement: string, letter: string) {
    if (!letter) {
      return 'pending';
    }

    console.log(letter, randomTextElement);

    return letter === randomTextElement ? 'correct' : 'incorrect';
  }
}
