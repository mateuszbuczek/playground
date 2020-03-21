import { Component, OnInit } from '@angular/core';
import {Article, NewsApiService} from '../news-api.service';

@Component({
  selector: 'app-news-article-list',
  templateUrl: './news-article-list.component.html',
  styleUrls: ['./news-article-list.component.css']
})
export class NewsArticleListComponent implements OnInit {
  private articles: Article[];

  constructor(private service: NewsApiService) {
    this.service.pagesOutput.subscribe(articles => {
      this.articles = articles;
    });

    this.service.getPage(1);
  }

  ngOnInit() {
  }

}
