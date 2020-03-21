import {Injectable} from '@angular/core';
import {Observable, Subject} from 'rxjs';
import {map, pluck, switchMap, tap} from 'rxjs/operators';
import {HttpClient, HttpParams} from '@angular/common/http';

export interface Article {
  title: string;
  url: string;
  source: {
    name: string;
  };
}

interface NewsApiResponse {
  totalResults: number;
  articles: Article[];
}

@Injectable({
  providedIn: 'root'
})
export class NewsApiService {
  private url = 'https://newsapi.org/v2/top-headlines';
  private pageSize = 10;
  private apiKey = '5949c731975a45bcb0946c6fb2bcd82d';
  private country = 'pl';

  private pagesInput: Subject<number>;
  pagesOutput: Observable<Article[]>;
  numberOfPages: Subject<number>;

  constructor(private http: HttpClient) {
    this.numberOfPages = new Subject();
    this.pagesInput = new Subject();
    this.pagesOutput = this.pagesInput.pipe(
      map((pageNumber) => {
        return new HttpParams()
          .set('apiKey', this.apiKey)
          .set('country', this.country)
          .set('pageSize', String(this.pageSize))
          .set('page', String(pageNumber));
      }),
      switchMap((params) => {
        return this.http.get<NewsApiResponse>(this.url, {params});
      }),
      tap(response => {
        const totalPages = Math.ceil(response.totalResults / this.pageSize);
        this.numberOfPages.next(totalPages);
      }),
      pluck('articles')
    );
  }

  getPage(page: number) {
    this.pagesInput.next(page);
  }
}
