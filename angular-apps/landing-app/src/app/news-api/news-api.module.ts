import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NewsArticleListComponent } from './news-article-list/news-article-list.component';
import { TrimOutletNamePipe } from './trim-outlet-name.pipe';
import {SharedModule} from '../shared/shared.module';



@NgModule({
    declarations: [NewsArticleListComponent, TrimOutletNamePipe],
    exports: [
        NewsArticleListComponent
    ],
  imports: [
    CommonModule,
    SharedModule
  ]
})
export class NewsApiModule { }
