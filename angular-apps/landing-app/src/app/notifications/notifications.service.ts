import {Injectable} from '@angular/core';
import {Observable, Subject} from 'rxjs';
import {scan} from 'rxjs/operators';

enum CommandType {
  error, success, clear
}

export interface Command {
  id: number;
  type: CommandType;
  text?: string;
}

@Injectable({
  providedIn: 'root'
})
export class NotificationsService {
  messagesInput: Subject<Command>;
  messagesOutput: Observable<Command[]>;

  constructor() {
    this.messagesInput = new Subject<Command>();
    this.messagesOutput = this.messagesInput.pipe(
      scan((acc: Command[], value: Command) => {
        if (value.type === CommandType.clear) {
          return acc.filter(message => message.id !== value.id);
        } else {
          return [...acc, value];
        }
      }, [])
    );
  }

  addSuccess(message: string) {
    this.messagesInput.next({
      id: this.randomId(),
      text: message,
      type: CommandType.success
    });
  }

  addError(message: string) {
    this.messagesInput.next({
      id: this.randomId(),
      text: message,
      type: CommandType.error
    });
  }

  clearMessage(id: number) {
    this.messagesInput.next({
      id: this.randomId(),
      type: CommandType.clear
    });
  }

  private randomId() {
    return Math.round(Math.random() * 10000);
  }
}
