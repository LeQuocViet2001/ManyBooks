import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HeaderComponent} from './layout/header/header.component';
import {BookListComponent} from './book/book-list/book-list.component';
import {FooterComponent} from './layout/footer/footer.component';
import {BookDetailComponent} from './book/book-detail/book-detail.component';
import {BookIntroComponent} from './book/book-intro/book-intro.component';
import {LoginComponent} from './security/login/login.component';
import {ErrorComponent} from './security/error/error.component';
import {ErrorNotFoundComponent} from './security/error/error-not-found/error-not-found.component';
import {BookCartComponent} from './book/book-cart/book-cart.component';
import {authInterceptorProviders} from './helpers/auth.interceptor';
import {JWT_OPTIONS, JwtHelperService} from '@auth0/angular-jwt';
import {APP_BASE_HREF, CommonModule} from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {AngularFireModule} from '@angular/fire';
import {environment} from '../environments/environment';
import {HttpClientModule} from '@angular/common/http';
import {SlickCarouselModule} from 'ngx-slick-carousel';
import { BookBestSellerComponent } from './book/book-best-seller/book-best-seller.component';
import { BookPromotionComponent } from './book/book-promotion/book-promotion.component';
import {NotifierModule} from 'angular-notifier';


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    BookListComponent,
    FooterComponent,
    BookDetailComponent,
    BookIntroComponent,
    LoginComponent,
    ErrorComponent,
    ErrorNotFoundComponent,
    BookCartComponent,
    BookBestSellerComponent,
    BookPromotionComponent
  ],
  imports: [
    CommonModule,
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    CommonModule,
    AngularFireModule.initializeApp(environment.firebaseConfig),
    HttpClientModule,
    SlickCarouselModule,
    FormsModule,
    NotifierModule
  ],
  providers: [HeaderComponent, authInterceptorProviders,
    JwtHelperService,
    {provide: JWT_OPTIONS, useValue: JWT_OPTIONS},
    {provide: APP_BASE_HREF, useValue: '/'}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
