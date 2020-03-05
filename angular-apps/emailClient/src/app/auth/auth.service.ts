import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';
import {tap} from 'rxjs/operators';

interface SignupCredentials {
  username: string;
  password: string;
  passwordConfirmation: string;
}

interface SignedInResponse {
  authenticated: boolean;
  username: string;
}

interface SignInCredentials {
  username: string;
  password: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly rootUrl = 'https://api.angular-email.com';
  isSignedIn$ = new BehaviorSubject(null);

  constructor(private http: HttpClient) {
  }

  usernameAvailable(username: string): Observable<any> {
    return this.http.post<{ message: boolean }>(`${this.rootUrl}/auth/username`, {
      username
    });
  }

  signup(credentials: SignupCredentials) {
    return this.http.post<{username: string}>(`${this.rootUrl}/auth/signup`, credentials).pipe(
      tap(() => {
        this.isSignedIn$.next(true);
      })
    );
  }

  checkAuth() {
    return this.http.get<SignedInResponse>(`${this.rootUrl}/auth/signedin`).pipe(
      tap(({authenticated}) => {
          this.isSignedIn$.next(authenticated);
      })
    );
  }

  signOut() {
    return this.http.post<{}>(`${this.rootUrl}/auth/signout`, {})
      .pipe(
        tap(() => {
          this.isSignedIn$.next(false);
        })
      );
  }

  signin(credentials: SignInCredentials): Observable<any> {
    return this.http.post<any>(`${this.rootUrl}/auth/signin`, credentials)
      .pipe(
        tap(() => {
          this.isSignedIn$.next(true);
        })
      );
  }
}
