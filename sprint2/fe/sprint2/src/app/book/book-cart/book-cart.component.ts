import {Component, OnInit} from '@angular/core';
import {CartService} from '../../service/cart.service';
import {ICart} from '../../model/cart/ICart';
import {ICartBook} from '../../model/cart/ICartBook';
import {TokenStorageService} from '../../service/security/token-storage.service';
import {CustomerService} from '../../service/customer.service';
import {FormControl, FormGroup} from '@angular/forms';
import {element} from 'protractor';
import {NotifierService} from 'angular-notifier';
import {ICustomer} from '../../model/customer/ICustomer';
import {HeaderComponent} from '../../layout/header/header.component';

@Component({
  selector: 'app-book-cart',
  templateUrl: './book-cart.component.html',
  styleUrls: ['./book-cart.component.css']
})
export class BookCartComponent implements OnInit {
  // cartList: ICart[] = [];
  // cartForm: FormGroup;
  cartBookList: ICartBook[] = [];
  accountId: number;
  checkList: boolean[] = [];
  checkAll = false;
  money = 0.0;
  totalMoney = 0.0;

  moneyBeforePromotion = 0.0;
  totalMoneyBeforePromotion = 0.0;

  cartDelete: ICartBook = {
    cartId: {},
    bookId: {}
  };

  customer: ICustomer = {};
  cartPaymentList: ICart[] = [];

  constructor(
    private tokenStorageService: TokenStorageService,
    private cartService: CartService,
    private notification: NotifierService,
    private customerService: CustomerService,
    private headerComponent: HeaderComponent
  ) {
  }

  ngOnInit(): void {
    this.topFunction();

    this.accountId = this.tokenStorageService.getUser().account.accountId;
    this.customerService.findCustomerByAccountId(this.accountId).subscribe((data: ICustomer) => {
      this.customer = data;
    }, () => {
    }, () => {
    });

    this.findAllCartBook();

    this.getTotalMoney();
  }

  findAllCartBook() {
    this.cartService.findAllCartBook(this.accountId).subscribe((data: ICartBook[]) => {
      this.cartBookList = data;
      for (let i = 0; i < data.length; i++) {
        this.checkList.push(false);
      }
    });
  }

  // ====thêm, giảm sản phẩm====
  subBook(cartBook: ICartBook) {
    cartBook.cartId.cartQuantity = cartBook.cartId.cartQuantity - 1;
    this.cartService.updateQuantityCart(cartBook).subscribe(data => {
    }, () => {
    }, () => {
      this.findAllCartBook();
      this.getTotalMoney();
      this.headerComponent.getQuantityCart();
    });
  }

  addBook(cartBook: ICartBook) {
    cartBook.cartId.cartQuantity = cartBook.cartId.cartQuantity + 1;
    this.cartService.updateQuantityCart(cartBook).subscribe(data => {
    }, () => {
    }, () => {
      this.findAllCartBook();
      this.getTotalMoney();
      this.headerComponent.getQuantityCart();
    });
  }

  isAllChecked() {
    if (this.checkAll) {
      this.checkAll = false;
    } else {
      this.checkAll = true;
    }

    if (this.checkAll) {
      this.checkList.forEach((checked, index) => {
        this.checkList[index] = true;
      });
    } else {
      this.checkList.forEach((checked, index) => {
        this.checkList[index] = false;
      });
    }

    this.getTotalMoney();
  }

  isChecked(i: number) {
    if (this.checkList[i]) {
      this.checkList[i] = false;
    } else {
      this.checkList[i] = true;
    }
    this.getTotalMoney();
  }

  getTotalMoney() {
    this.cartService.findAllCartBook(this.accountId).subscribe((data: ICartBook[]) => {
      this.cartBookList = data;
      this.cartBookList.forEach((check, index) => {
        if (this.checkList[index]) {
          this.money += this.cartBookList[index].bookId.bookPrice * this.cartBookList[index].cartId.cartQuantity
            - (this.cartBookList[index].bookId.bookPrice * this.cartBookList[index].cartId.cartQuantity
              * (this.cartBookList[index].bookId.bookPromotionId.promotionPercent / 100));

          this.moneyBeforePromotion += this.cartBookList[index].bookId.bookPrice * this.cartBookList[index].cartId.cartQuantity;
        }
      });
      this.totalMoney = this.money;
      this.money = 0.0;

      this.totalMoneyBeforePromotion = this.moneyBeforePromotion;
      this.moneyBeforePromotion = 0.0;
    });
  }

  getQuantity(quantity: any, cartBook: ICartBook) {
    cartBook.cartId.cartQuantity = quantity.value;
    this.cartService.updateQuantityCart(cartBook).subscribe(data => {
    }, () => {
    }, () => {
      this.findAllCartBook();
      this.getTotalMoney();
    });
  }

  // =======xoá===
  showInfoCartDelete(cartBookDelete: ICartBook) {
    this.cartDelete = cartBookDelete;
  }

  deleteCart(cartBookId: number) {
    this.cartService.deleteBookCart(cartBookId).subscribe(
      () => {
      },
      () => {
      },
      () => {
        this.findAllCartBook();
        window.location.assign('/cart');
        this.headerComponent.getQuantityCart();
        this.notification.notify('success', 'Xoá sản phẩm thành công');
      });
  }

  payment() {
    this.cartBookList.forEach((check, index) => {
      if (this.checkList[index]) {
        this.cartPaymentList.push(this.cartBookList[index].cartId);
      }
    });

    this.cartService.paymentCart(this.cartPaymentList).subscribe(data => {
    }, () => {
    }, () => {
      this.notification.notify('success', 'Thanh toán thành công');
      window.location.assign('/cart');
    });
  }

  topFunction() {
    document.body.scrollTop = 0;
    document.documentElement.scrollTop = 200;
  }
}
