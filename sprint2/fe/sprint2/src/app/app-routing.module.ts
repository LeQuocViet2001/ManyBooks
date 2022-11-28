import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {BookIntroComponent} from './book/book-intro/book-intro.component';
import {BookListComponent} from './book/book-list/book-list.component';
import {BookCartComponent} from './book/book-cart/book-cart.component';
import {BookDetailComponent} from './book/book-detail/book-detail.component';
import {AuthGuard} from './helpers/auth.guard';
import {BothAuthGuard} from './helpers/both-auth.guard';
import {LoginComponent} from './security/login/login.component';
import {BookBestSellerComponent} from './book/book-best-seller/book-best-seller.component';
import {BookPromotionComponent} from './book/book-promotion/book-promotion.component';
import {BothAuthCustomerGuard} from './helpers/both-auth-customer.guard';
import {ErrorNotFoundComponent} from './security/error/error-not-found/error-not-found.component';



const routes: Routes = [
  {path: '', component: BookIntroComponent},
  {path: 'cart', component: BookCartComponent, canActivate: [AuthGuard, BothAuthCustomerGuard]},
  {path: 'book/detail/:id', component: BookDetailComponent},
  {path: 'book/list', component: BookListComponent},
  {path: 'book/list/:id', component: BookListComponent},
  {path: 'book/list-best-seller', component: BookBestSellerComponent},
  {path: 'book/list-promotion', component: BookPromotionComponent},
  {path: 'login', component: LoginComponent},
  {path: 'auth/access-denied', component: ErrorNotFoundComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
