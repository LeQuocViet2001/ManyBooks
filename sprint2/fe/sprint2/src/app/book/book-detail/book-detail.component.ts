import {Component, OnInit} from '@angular/core';
import {BookService} from '../../service/book.service';
import {ActivatedRoute, Router} from '@angular/router';
import {IBook} from '../../model/book/IBook';
import {TokenStorageService} from '../../service/security/token-storage.service';
import {CartService} from '../../service/cart.service';
import {NotifierService} from 'angular-notifier';
import {ICartBook} from '../../model/cart/ICartBook';
import {ICart} from '../../model/cart/ICart';
import {HeaderComponent} from '../../layout/header/header.component';

@Component({
  selector: 'app-book-detail',
  templateUrl: './book-detail.component.html',
  styleUrls: ['./book-detail.component.css']
})
export class BookDetailComponent implements OnInit {
  id: number;
  bookById: IBook = {};
  slideConfig = {slidesToShow: 4, slidesToScroll: 4};
  bookAuthorList: IBook[] = [];
  pageBookAuthorList = 0;

  private roles: string[];
  isLoggedIn = false;
  showAdminBoard = false;
  showCustomer = false;
  userName: string;

  accountId: number;
  bookQuantity = 1;

  quantityCart = 0;

  constructor(
    private tokenStorageService: TokenStorageService,
    private bookService: BookService,
    private cartService: CartService,
    private notification: NotifierService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private headerComponent: HeaderComponent
  ) {
  }

  ngOnInit(): void {
    this.topFunction();

    if (!localStorage.getItem('foo')) {
      localStorage.setItem('foo', 'no reload');
      location.reload();
    } else {
      localStorage.removeItem('foo');
    }

    this.activatedRoute.paramMap.subscribe((param) => {
      this.id = +(param.get('id'));
    }, () => {
    }, () => {
      this.bookService.findBookById(this.id).subscribe((data: IBook) => {
        this.bookById = data;
      }, () => {
      }, () => {
      });
    });

    this.getQuantityCart();

    // kiểm tra đăng nhập
    this.isLoggedIn = !!this.tokenStorageService.getToken();
    if (this.isLoggedIn) {
      this.userName = this.tokenStorageService.getUser().account.username;
      this.roles = this.tokenStorageService.getUser().account.roles[0].roleName;
      // kiểm tra role
      this.showAdminBoard = this.roles.includes('ROLE_ADMIN');
      this.showCustomer = this.roles.includes('ROLE_CUSTOMER');

      console.log('roles: ' + this.roles);
    }

    this.findAllBookAuthor();

    this.accountId = this.tokenStorageService.getUser().account.accountId;
  }

  // thêm sách vào giỏ hàng
  addBook(bookAdd: IBook) {
    // lấy số lượng trong giỏ hàng
    this.getQuantityCart();

    // thêm vào giỏ hàng
    bookAdd.bookQuantity = this.bookQuantity;
    this.cartService.addBook(this.accountId, bookAdd).subscribe(() => {
    }, (error) => {
      this.notification.notify('error', error.error);
      this.bookService.findBookById(this.id).subscribe((data: IBook) => {
        this.bookById = data;
      }, () => {
      }, () => {
      });
    }, () => {
      this.notification.notify('success', 'Thêm sách vào giỏ hàng thành công');
      (document.getElementById('input-quantity') as HTMLFormElement).value = '1';
      this.headerComponent.getQuantityCart();

      this.bookService.findBookById(this.id).subscribe((data: IBook) => {
        this.bookById = data;
      }, () => {
      }, () => {
      });
    });
  }

  // lấy số lượng trong giỏ hàng
  getQuantityCart() {
    this.cartService.findCartBookByBookId(this.accountId, this.id).subscribe((data: ICartBook) => {
      this.quantityCart = data.cartId.cartQuantity;
    }, () => {
      this.quantityCart = 0;
    }, () => {
    });
  }

  // ========list======
  findAllBookAuthor() {
    this.bookService.findBookById(this.id).subscribe((data: IBook) => {
      this.bookById = data;
    }, () => {
    }, () => {
      this.bookService.findAllBookAuthor(this.bookById.bookAuthorId.authorId, this.id).subscribe((data: IBook[]) => {
        this.bookAuthorList = data;
      }, () => {
      });
    });
  }

  backBookIntro() {
    if (this.pageBookAuthorList >= 4) {
      this.pageBookAuthorList -= 4;
      return this.pageBookAuthorList;
    } else {
      this.pageBookAuthorList = 0;
      return 0;
    }
  }

  nextBookIntro() {
    if (this.pageBookAuthorList > this.bookAuthorList.length) {
      this.pageBookAuthorList = 0;
      return 0;
    }
    this.pageBookAuthorList += 4;
    return this.pageBookAuthorList;
  }

  topFunction() {
    document.body.scrollTop = 0;
    document.documentElement.scrollTop = 200;
  }

  addBookQuantity() {
    this.bookQuantity = this.bookQuantity + 1;
    // lấy số lượng trong giỏ hàng
    this.getQuantityCart();
  }

  subBookQuantity() {
    this.bookQuantity = this.bookQuantity - 1;
    // lấy số lượng trong giỏ hàng
    this.getQuantityCart();
  }

  getQuantity(quantity: any) {
    this.bookQuantity = quantity.value;
    // lấy số lượng trong giỏ hàng
    this.getQuantityCart();

    // số lượng tối đa mua được
    if ((this.quantityCart + this.bookQuantity) > this.bookById.bookQuantity) {
      (document.getElementById('input-quantity') as HTMLFormElement).value = (this.bookById.bookQuantity - this.quantityCart).toString();
      this.notification.notify('warning', 'Số lượng muốn mua đã vượt quá số lượng sách có trong kho');
    }
  }
}
