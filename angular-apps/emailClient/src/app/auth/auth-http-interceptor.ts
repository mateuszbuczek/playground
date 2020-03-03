import {Injectable} from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpEventType, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {tap} from 'rxjs/operators';

@Injectable()
export class AuthHttpInterceptor implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const reqClone = req.clone({withCredentials: true});

    return next.handle(reqClone);
      // .pipe(
      //   tap(val => {
      //     if (val.type === HttpEventType.Sent) {
      //       console.log('Request was sent to server');
      //     }
      //   }, err => {
      //     if (err instanceof HttpErrorResponse) {
      //       console.log(err);
      //     }
      //   })
      // );
  }
}
