import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {CollectionsHomeComponent} from './collections-home/collections-home.component';
import {BiographyComponent} from './biography/biography.component';
import {PartnersComponent} from './partners/partners.component';
import {CompaniesComponent} from './companies/companies.component';


const routes: Routes = [
  {
    path: '',
    component: CollectionsHomeComponent,
    children: [
      {path: '', component: BiographyComponent},
      {path: 'partners', component: PartnersComponent},
      {path: 'companies', component: CompaniesComponent},
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CollectionsRoutingModule {
}
