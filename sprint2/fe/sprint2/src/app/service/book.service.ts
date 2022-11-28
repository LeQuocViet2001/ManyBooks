import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {IBook} from '../model/book/IBook';
import {IAuthor} from '../model/book/IAuthor';
import {ICategory} from '../model/book/ICategory';

@Injectable({
  providedIn: 'root'
})
export class BookService {
  readonly URI: string = 'http://localhost:8080/api/book';

  constructor(
    private httpClient: HttpClient
  ) {
  }

// ==========intro========
  findAllBookIntro(): Observable<IBook[]> {
    return this.httpClient.get<IBook[]>(this.URI + '/book-customer/no-login/book-list-intro');
  }

  findAllBookBestSellerIntro(): Observable<IBook[]> {
    return this.httpClient.get<IBook[]>(this.URI + '/book-customer/no-login/book-list-best-seller-intro');
  }

  findAllBookBestSeller(): Observable<IBook[]> {
    return this.httpClient.get<IBook[]>(this.URI + '/book-customer/no-login/book-list-best-seller');
  }

  findAllBookAuthor(authorId: number, bookId: number): Observable<IBook[]> {
    return this.httpClient.get<IBook[]>(this.URI + '/book-customer/no-login/book-list-author?authorId=' + authorId + '&bookId=' + bookId);
  }

  findAllAuthor(): Observable<IAuthor[]> {
    return this.httpClient.get<IAuthor[]>(this.URI + '/book-customer/no-login/author-intro');
  }

  // =================book-list==========
  // findAllBookList(page: number): Observable<IBook[]> {
  //   return this.httpClient.get<IBook[]>(this.URI + '/book-customer/no-login/book-list?page=' + page);
  // }
  //
  // findAllBookList2(page: number): Observable<IBook[]> {
  //   return this.httpClient.get<IBook[]>(this.URI + '/book-customer/no-login/book-list2?page=' + page);
  // }

  findAllBookListCategory(page: number, bookName: string, categoryId?: number): Observable<IBook[]> {
    return this.httpClient.get<IBook[]>(this.URI + '/book-customer/no-login/book-list-category?page=' + page + '&bookName=' + bookName + '&categoryId=' + categoryId);
  }

  findAllBookListCategory2(page: number, bookName: string, categoryId?: number): Observable<IBook[]> {
    return this.httpClient.get<IBook[]>(this.URI + '/book-customer/no-login/book-list-category2?page=' + page + '&bookName=' + bookName + '&categoryId=' + categoryId);
  }

  // =========category-list=========
  findAllCategory(): Observable<ICategory[]> {
    return this.httpClient.get<ICategory[]>(this.URI + '/book-customer/no-login/category-list');
  }

  // sort book-list
  findAllBookListPriceUp(page: number, categoryId?: number): Observable<IBook[]> {
    return this.httpClient.get<IBook[]>(this.URI + '/book-customer/no-login/book-list-category/price-up?page=' + page + '&categoryId=' + categoryId);
  }

  findAllBookListPriceDown(page: number, categoryId?: number): Observable<IBook[]> {
    return this.httpClient.get<IBook[]>(this.URI + '/book-customer/no-login/book-list-category/price-down?page=' + page + '&categoryId=' + categoryId);
  }

  findAllBookListAZ(page: number, categoryId?: number): Observable<IBook[]> {
    return this.httpClient.get<IBook[]>(this.URI + '/book-customer/no-login/book-list-category/name-az?page=' + page + '&categoryId=' + categoryId);
  }

  findAllBookListZA(page: number, categoryId?: number): Observable<IBook[]> {
    return this.httpClient.get<IBook[]>(this.URI + '/book-customer/no-login/book-list-category/name-za?page=' + page + '&categoryId=' + categoryId);
  }

  findAllBookListPriceUp2(page: number, categoryId?: number): Observable<IBook[]> {
    return this.httpClient.get<IBook[]>(this.URI + '/book-customer/no-login/book-list-category2/price-up?page=' + page + '&categoryId=' + categoryId);
  }

  findAllBookListPriceDown2(page: number, categoryId?: number): Observable<IBook[]> {
    return this.httpClient.get<IBook[]>(this.URI + '/book-customer/no-login/book-list-category2/price-down?page=' + page + '&categoryId=' + categoryId);
  }

  findAllBookListAZ2(page: number, categoryId?: number): Observable<IBook[]> {
    return this.httpClient.get<IBook[]>(this.URI + '/book-customer/no-login/book-list-category2/name-az?page=' + page + '&categoryId=' + categoryId);
  }

  findAllBookListZA2(page: number, categoryId?: number): Observable<IBook[]> {
    return this.httpClient.get<IBook[]>(this.URI + '/book-customer/no-login/book-list-category2/name-za?page=' + page + '&categoryId=' + categoryId);
  }

  // =====detail=====
  findBookById(id: number): Observable<IBook> {
    return this.httpClient.get<IBook>(this.URI + '/book-customer/no-login/book-detail?bookId=' + id);
  }

  // =====list ban chay=====
  findAllBookBestSellerList(page: number): Observable<IBook[]> {
    return this.httpClient.get<IBook[]>(this.URI + '/book-customer/no-login/book-best-seller?page=' + page);
  }

  findAllBookBestSellerList2(page: number): Observable<IBook[]> {
    return this.httpClient.get<IBook[]>(this.URI + '/book-customer/no-login/book-best-seller2?page=' + page);
  }

  // list sach giam gia
  findAllBookPromotion(page: number): Observable<IBook[]> {
    return this.httpClient.get<IBook[]>(this.URI + '/book-customer/no-login/book-list-promotion?page=' + page);
  }

  findAllBookPromotion2(page: number): Observable<IBook[]> {
    return this.httpClient.get<IBook[]>(this.URI + '/book-customer/no-login/book-list-promotion2?page=' + page);
  }
}
