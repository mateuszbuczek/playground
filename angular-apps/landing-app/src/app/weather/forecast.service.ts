import {Injectable} from '@angular/core';
import {Observable, of, throwError} from 'rxjs';
import {catchError, filter, map, mergeMap, pluck, retry, share, switchMap, tap, toArray} from 'rxjs/operators';
import {HttpClient, HttpParams} from '@angular/common/http';
import {NotificationsService} from '../notifications/notifications.service';

interface OpenWeatherResponse {
  list: {
    dt_txt: string;
    main: {
      temp: number
    }
  }[];
}

@Injectable({
  providedIn: 'root'
})
export class ForecastService {

  private url = 'https://api.openweathermap.org/data/2.5/forecast';
  private token = 'f2a7872fc9b64379f9fc5ce755fb1196';

  constructor(private http: HttpClient,
              private notificationsService: NotificationsService) {
  }

  getForecast() {
    return this.getCurrentLocation().pipe(
      map(coords => {
        return new HttpParams()
          .set('lat', String(coords.latitude))
          .set('lon', String(coords.longitude))
          .set('units', 'metric')
          .set('appid', this.token);
      }),
      switchMap(params => this.http.get<OpenWeatherResponse>(this.url, {params})),
      pluck('list'),
      mergeMap(list => of(...list)),
      filter((value, index) => index % 8 === 0),
      map(value => {
        return {
          dateString: value.dt_txt,
          temp: value.main.temp
        };
      }),
      toArray(),
      share()
    );
  }

  getCurrentLocation() {
    return new Observable<Coordinates>((observer) => {
      window.navigator.geolocation.getCurrentPosition(
        position => {
          observer.next(position.coords);
          observer.complete();
        },
        err => observer.error(err)
      );
    }).pipe(
      retry(1),
      tap(() => {
        this.notificationsService.addSuccess('Successfully obtained your location');
      }),
      catchError((err) => {
        this.notificationsService.addError(`Failed to obtain your location`);
        return throwError(err);
      })
    );
  }
}
