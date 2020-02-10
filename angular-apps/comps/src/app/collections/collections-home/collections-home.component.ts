import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-collections-home',
  templateUrl: './collections-home.component.html',
  styleUrls: ['./collections-home.component.css']
})
export class CollectionsHomeComponent implements OnInit {
  data = [
    {name: 'Janes', age: 24, job: 'Designer'},
    {name: 'John', age: 27, job: 'Dev'},
    {name: 'Loe', age: 25, job: 'Engineer'},
    {name: 'Cloe', age: 45, job: 'SEO'},
  ];
  headers = [
    {key: 'name', label: 'Name'},
    {key: 'age', label: 'Age'},
    {key: 'job', label: 'Job'},
  ];

  constructor() {
  }

  ngOnInit(): void {
  }

}
