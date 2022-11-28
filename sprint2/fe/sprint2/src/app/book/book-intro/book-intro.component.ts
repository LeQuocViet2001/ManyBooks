import {Component, OnInit} from '@angular/core';
import {BookService} from '../../service/book.service';
import {IBook} from '../../model/book/IBook';
import {IAuthor} from '../../model/book/IAuthor';
import {ICategory} from '../../model/book/ICategory';
import {TokenStorageService} from '../../service/security/token-storage.service';
import {CartService} from '../../service/cart.service';
import {NotifierService} from 'angular-notifier';
import {ICartBook} from '../../model/cart/ICartBook';
import {HeaderComponent} from '../../layout/header/header.component';

@Component({
  selector: 'app-book-intro',
  templateUrl: './book-intro.component.html',
  styleUrls: ['./book-intro.component.css']
})
export class BookIntroComponent implements OnInit {
  bookIntroList: IBook[] = [];
  bookBestSellerSliderList: IBook[] = [];
  bookBestSeller: IBook[] = [];
  authorList: IAuthor[] = [];
  categoryList: ICategory[] = [];
  slideConfig = {slidesToShow: 4, slidesToScroll: 4};
  pageBookIntroList = 0;
  pageBookBestSeller = 0;
  pageAuthor = 0;

  private roles: string[];
  isLoggedIn = false;
  showAdminBoard = false;
  showCustomer = false;
  userName: string;

  accountId: number;

  constructor(
    private bookService: BookService,
    private cartService: CartService,
    private notification: NotifierService,
    private tokenStorageService: TokenStorageService,
    private headerComponent: HeaderComponent
  ) {
  }

  ngOnInit(): void {
    this.topFunction();
    this.findAllBookIntro();
    this.findAllBookBestSellerIntro();
    this.findAllBookBestSeller();
    this.findAllAuthor();
    this.findAllCategory();
    if (!localStorage.getItem('foo')) {
      localStorage.setItem('foo', 'no reload');
      location.reload();
    } else {
      localStorage.removeItem('foo');
    }

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

    this.accountId = this.tokenStorageService.getUser().account.accountId;
  }

  // +++++++++++++++lấy dữ liệu++++++++++++++++
  findAllBookIntro() {
    this.bookService.findAllBookIntro().subscribe((data: IBook[]) => {
      this.bookIntroList = data;
    });
  }

  findAllBookBestSellerIntro() {
    this.bookService.findAllBookBestSellerIntro().subscribe((data: IBook[]) => {
      this.bookBestSellerSliderList = data;
    });
  }

  findAllBookBestSeller() {
    this.bookService.findAllBookBestSeller().subscribe((data: IBook[]) => {
      this.bookBestSeller = data;
    });
  }

  findAllAuthor() {
    this.bookService.findAllAuthor().subscribe((data: IAuthor[]) => {
      this.authorList = data;
    });
  }

  findAllCategory() {
    this.bookService.findAllCategory().subscribe((data: ICategory[]) => {
      this.categoryList = data;
    });
  }

  // thêm sách vào giỏ hàng
  addBook(bookAdd: IBook) {
    bookAdd.bookQuantity = 1;
    this.cartService.addBook(this.accountId, bookAdd).subscribe(() => {
    }, (error) => {
      this.notification.notify('error', error.error);
    }, () => {
      this.notification.notify('success', 'Thêm sách vào giỏ hàng thành công');
      this.headerComponent.getQuantityCart();
    });
  }

  // ============slider book-intro=====

  backBookIntro() {
    if (this.pageBookIntroList >= 4) {
      this.pageBookIntroList -= 4;
      return this.pageBookIntroList;
    } else {
      this.pageBookIntroList = 0;
      return 0;
    }
  }

  nextBookIntro() {
    if (this.pageBookIntroList > this.bookIntroList.length) {
      this.pageBookIntroList = 0;
      return 0;
    }
    this.pageBookIntroList += 4;
    return this.pageBookIntroList;
  }

  // ============slider book-best-seller=====

  backBookBestSeller() {
    if (this.pageBookBestSeller >= 4) {
      this.pageBookBestSeller -= 4;
      return this.pageBookBestSeller;
    } else {
      this.pageBookBestSeller = 0;
      return 0;
    }
  }

  nextBookBestSeller() {
    if (this.pageBookBestSeller > this.bookBestSeller.length) {
      this.pageBookBestSeller = 0;
      return 0;
    }
    this.pageBookBestSeller += 4;
    return this.pageBookBestSeller;
  }

  // ============slider author=====

  backAuthor() {
    if (this.pageAuthor >= 4) {
      this.pageAuthor -= 4;
      return this.pageAuthor;
    } else {
      this.pageAuthor = 0;
      return 0;
    }
  }

  nextAuthor() {
    if (this.pageAuthor > this.authorList.length) {
      this.pageAuthor = 0;
      return 0;
    }
    this.pageAuthor += 4;
    return this.pageAuthor;
  }

  topFunction() {
    document.body.scrollTop = 0;
    document.documentElement.scrollTop = 0;
  }
}
