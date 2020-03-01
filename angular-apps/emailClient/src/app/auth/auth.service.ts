import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) {
  }

  usernameAvailable(username: string): Observable<any> {
    return this.http.post<{ message: boolean }>('https://api.angular-email.com/auth/username', {
      username
    });
  }
}
