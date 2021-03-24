import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-views-home',
  templateUrl: './views-home.component.html',
  styleUrls: ['./views-home.component.css']
})
export class ViewsHomeComponent implements OnInit {
  stats = [
    {value: 22, label: '# of users'},
    {value: 900, label: 'Revenue'},
    {value: 600, label: 'Reviews'},
  ];

  items = [
    {
      image: 'https://images.unsplash.com/photo-1573866926487-a1865558a9cf?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=100&q=60%20100w',
      title: 'Couch', description: 'fantastic couch to sit on'
    },
    {
      image: 'https://images.unsplash.com/photo-1568506759799-16b274da9e15?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=3437&q=80 3437w',
      title: 'Dresser', description: 'just cool dresser'
    },
  ];

  constructor() {
  }

  ngOnInit(): void {
  }

}
