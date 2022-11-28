import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ICart} from '../model/cart/ICart';
import {ICartBook} from '../model/cart/ICartBook';
import {IBook} from '../model/book/IBook';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  readonly URI: string = 'http://localhost:8080/api/cart';

  constructor(
    private httpClient: HttpClient
  ) {
  }

  // findAllCart(): Observable<ICart[]> {
  //   return this.httpClient.get<ICart[]>(this.URI + '/list-cart');
  // }

  findAllCartBook(accountId: number): Observable<ICartBook[]> {
    return this.httpClient.get<ICartBook[]>(this.URI + '/list-cart-book?accountId=' + accountId);
  }

  updateQuantityCart(cartBook: ICartBook): Observable<void> {
    return this.httpClient.put<void>(this.URI + '/update-quantity', cartBook);
  }

  addBook(accountId: number, book: IBook): Observable<void> {
    return this.httpClient.post<void>(this.URI + '/add-book?accountId=' + accountId, book);
  }

  deleteBookCart(cartId: number): Observable<void> {
    return this.httpClient.delete<void>(this.URI + '/cart-delete?cardId=' + cartId);
  }

  paymentCart(cartList: ICart[]): Observable<void> {
    return this.httpClient.put<void>(this.URI + '/payment', cartList);
  }

  // book-detail
  findCartBookByBookId(accountId: number, bookId: number): Observable<ICartBook> {
    return this.httpClient.get<ICartBook>(this.URI + '/cart-book-detail?accountId=' + accountId + '&bookId=' + bookId);
  }
}
