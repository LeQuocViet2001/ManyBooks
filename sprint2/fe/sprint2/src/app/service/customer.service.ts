import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ICart} from '../model/cart/ICart';
import {ICartBook} from '../model/cart/ICartBook';
import {ICustomer} from '../model/customer/ICustomer';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {
  readonly URI: string = 'http://localhost:8080/api/customer';

  constructor(
    private httpClient: HttpClient
  ) {
  }

  // findAllCart(): Observable<ICart[]> {
  //   return this.httpClient.get<ICart[]>(this.URI + '/list-cart');
  // }

  findCustomerByAccountId(accountId: number): Observable<ICustomer> {
    return this.httpClient.get<ICustomer>(this.URI + '/getCustomerByAccount?accountId=' + accountId);
  }
}
