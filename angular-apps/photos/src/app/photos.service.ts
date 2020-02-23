import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

interface UnsplashResponse {
  urls: {
    regular: string
  };
}

@Injectable({
  providedIn: 'root'
})
export class PhotosService {

  constructor(private http: HttpClient) {
  }

  getPhoto() {
    return this.http.get<UnsplashResponse>('https://api.unsplash.com/photos/random', {
      headers: {
        Authorization: 'Client-ID Ge0MfcN-Y9vnBex0u1gIk_KNA4Ab_JcFLJcvWs8R-2k'
      }
    });
  }
}
