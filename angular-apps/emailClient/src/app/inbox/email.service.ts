import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

interface EmailSummary {
  id: string;
  subject: string;
  from: string;
}

@Injectable({
  providedIn: 'root'
})
export class EmailService {
  rootUrl = 'https://api.angular-email.com';

  constructor(private http: HttpClient) {
  }

  getEmails(): Observable<EmailSummary[]> {
    return this.http.get<EmailSummary[]>(this.rootUrl + '/emails');
  }
}
