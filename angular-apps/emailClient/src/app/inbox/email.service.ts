import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Email} from "./email";

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

  getEmail(id: string): Observable<Email> {
    return this.http.get<Email>(`${this.rootUrl}/emails/${id}`);
  }

  sendEmail(email: Email) {
    return this.http.post(`${this.rootUrl}/emails`, email);
  }
}
