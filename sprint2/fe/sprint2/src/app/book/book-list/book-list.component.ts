import {Component, OnInit} from '@angular/core';
import {BookService} from '../../service/book.service';
import {IBook} from '../../model/book/IBook';
import {ICategory} from '../../model/book/ICategory';
import {ActivatedRoute, Router} from '@angular/router';
import {FormControl, FormGroup} from '@angular/forms';
import {TokenStorageService} from '../../service/security/token-storage.service';
import {CartService} from '../../service/cart.service';
import {NotifierService} from 'angular-notifier';
import {HeaderComponent} from '../../layout/header/header.component';

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.css']
})
export class BookListComponent implements OnInit {
  bookList: IBook[] = [];
  bookList2: IBook[] = [];
  formSearch: FormGroup;

  categoryList: ICategory[] = [];

  page = 0;
  size: number;
  totalPages: number;
  totalPageList: number[] = [];
  page2 = 0;
  size2: number;
  totalPages2: number;
  totalPageList2: number[] = [];

  check = 1;
  checkSort = '';

  categoryId: any;

  private roles: string[];
  isLoggedIn = false;
  showAdminBoard = false;
  showCustomer = false;
  userName: string;

  accountId: number;

  constructor(
    private bookService: BookService,
    private activatedRoute: ActivatedRoute,
    private cartService: CartService,
    private notification: NotifierService,
    private tokenStorageService: TokenStorageService,
    private router: Router,
    private headerComponent: HeaderComponent
  ) {
  }

  ngOnInit(): void {
    this.formSearch = new FormGroup({
      nameSearch: new FormControl(null)
    });

    this.findAllBookList(0);
    this.findAllBookList2(0);
    this.findAllCategory();
    if (!localStorage.getItem('foo')) {
      localStorage.setItem('foo', 'no reload');
      location.reload();
    } else {
      localStorage.removeItem('foo');
    }

    this.activatedRoute.paramMap.subscribe((param) => {
      this.categoryId = param.get('id');
      this.findAllBookListCategory(0, this.categoryId);
    }, () => {
    }, () => {
    });

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

  // ========list======
  findAllBookList(page: number, categoryId?: any) {
    if (categoryId === undefined) {
      this.categoryId = '';
    } else {
      this.categoryId = categoryId;
    }
    this.page = page;
    this.bookService.findAllBookListCategory(this.page, this.formSearch.get('nameSearch').value, this.categoryId).subscribe((data: any) => {
      this.bookList = data.content;
      this.totalPages = data.totalPages;
      this.size = data.size;

      this.totalPageList = [];
      for (let i = 0; i < this.totalPages; i++) {
        this.totalPageList.push(i);
      }
    }, () => {
      // this.page--;
      // this.findAllBookList(this.page, this.categoryId);
    }, () => {
      this.page2 = 0;
    });
  }

  findAllBookList2(page: number, categoryId?: any) {
    if (categoryId === undefined) {
      this.categoryId = '';
    } else {
      this.categoryId = categoryId;
    }
    this.page2 = page;
    this.bookService.findAllBookListCategory2(this.page2, this.formSearch.get('nameSearch').value, this.categoryId).subscribe((data: any) => {
      this.bookList2 = data.content;
      this.totalPages2 = data.totalPages;
      this.size2 = data.size;

      this.totalPageList2 = [];
      for (let i = 0; i < this.totalPages2; i++) {
        this.totalPageList2.push(i);
      }
    }, () => {
      // this.page2--;
      // this.findAllBookList2(this.page2, this.categoryId);
    }, () => {
      this.page = 0;
    });
  }

  findAllCategory() {
    this.bookService.findAllCategory().subscribe((data: ICategory[]) => {
      this.categoryList = data;
    });
  }

  findAllBookListCategory(page: number, categoryId?: any) {
    if (categoryId === undefined) {
      this.categoryId = '';
    } else {
      this.categoryId = categoryId;
    }
    this.bookService.findAllBookListCategory(page, this.formSearch.get('nameSearch').value, this.categoryId).subscribe((data: any) => {
      this.bookList = data.content;
      this.totalPages = data.totalPages;
      this.size = data.size;

      this.totalPageList = [];
      for (let i = 0; i < this.totalPages; i++) {
        this.totalPageList.push(i);
      }

      this.bookList2 = data.content;
      this.totalPages2 = data.totalPages;
      this.size2 = data.size;

      this.totalPageList2 = [];
      for (let i = 0; i < this.totalPages2; i++) {
        this.totalPageList2.push(i);
      }
    }, () => {
      // this.page--;
      // this.findAllBookListCategory(this.page, this.categoryId);
    }, () => {
    });
  }

  // sort book-list
  sortBookList(sortCheck: any, page: number) {
    this.checkSort = sortCheck;
    switch (sortCheck) {
      case 'new-date':
        this.page = page;
        this.bookService.findAllBookListCategory(this.page, this.formSearch.get('nameSearch').value, this.categoryId).subscribe((data: any) => {
          this.bookList = data.content;
          this.totalPages = data.totalPages;
          this.size = data.size;

          this.totalPageList = [];
          for (let i = 0; i < this.totalPages; i++) {
            this.totalPageList.push(i);
          }
        }, () => {
          this.page--;
          this.findAllBookList(this.page, this.categoryId);
        }, () => {
          this.page2 = 0;
        });

        this.page2 = page;
        this.bookService.findAllBookListCategory2(this.page2, this.formSearch.get('nameSearch').value, this.categoryId).subscribe((data: any) => {
          this.bookList2 = data.content;
          this.totalPages2 = data.totalPages;
          this.size2 = data.size;

          this.totalPageList2 = [];
          for (let i = 0; i < this.totalPages2; i++) {
            this.totalPageList2.push(i);
          }
        }, () => {
          this.page2--;
          this.findAllBookList2(this.page2, this.categoryId);
        }, () => {
          this.page = 0;
        });
        break;
      case 'price:asc':
        this.page = page;
        this.bookService.findAllBookListPriceUp(this.page, this.categoryId).subscribe((data: any) => {
          this.bookList = data.content;
          this.totalPages = data.totalPages;
          this.size = data.size;

          this.totalPageList = [];
          for (let i = 0; i < this.totalPages; i++) {
            this.totalPageList.push(i);
          }
        }, () => {
          this.page--;
          this.findAllBookList(this.page, this.categoryId);
        }, () => {
          this.page2 = 0;
        });

        this.page2 = page;
        this.bookService.findAllBookListPriceUp2(this.page2, this.categoryId).subscribe((data: any) => {
          this.bookList2 = data.content;
          this.totalPages2 = data.totalPages;
          this.size2 = data.size;

          this.totalPageList2 = [];
          for (let i = 0; i < this.totalPages2; i++) {
            this.totalPageList2.push(i);
          }
        }, () => {
          this.page2--;
          this.findAllBookList2(this.page2, this.categoryId);
        }, () => {
          this.page = 0;
        });
        break;
      case 'price:desc':
        this.page = page;
        this.bookService.findAllBookListPriceDown(this.page, this.categoryId).subscribe((data: any) => {
          this.bookList = data.content;
          this.totalPages = data.totalPages;
          this.size = data.size;

          this.totalPageList = [];
          for (let i = 0; i < this.totalPages; i++) {
            this.totalPageList.push(i);
          }
        }, () => {
          this.page--;
          this.findAllBookList(this.page, this.categoryId);
        }, () => {
          this.page2 = 0;
        });

        this.page2 = page;
        this.bookService.findAllBookListPriceDown2(this.page2, this.categoryId).subscribe((data: any) => {
          this.bookList2 = data.content;
          this.totalPages2 = data.totalPages;
          this.size2 = data.size;

          this.totalPageList2 = [];
          for (let i = 0; i < this.totalPages2; i++) {
            this.totalPageList2.push(i);
          }
        }, () => {
          this.page2--;
          this.findAllBookList2(this.page2, this.categoryId);
        }, () => {
          this.page = 0;
        });
        break;
      case 'name:asc':
        this.page = page;
        this.bookService.findAllBookListAZ(this.page, this.categoryId).subscribe((data: any) => {
          this.bookList = data.content;
          this.totalPages = data.totalPages;
          this.size = data.size;

          this.totalPageList = [];
          for (let i = 0; i < this.totalPages; i++) {
            this.totalPageList.push(i);
          }
        }, () => {
          this.page--;
          this.findAllBookList(this.page, this.categoryId);
        }, () => {
          this.page2 = 0;
        });

        this.page2 = page;
        this.bookService.findAllBookListAZ2(this.page2, this.categoryId).subscribe((data: any) => {
          this.bookList2 = data.content;
          this.totalPages2 = data.totalPages;
          this.size2 = data.size;

          this.totalPageList2 = [];
          for (let i = 0; i < this.totalPages2; i++) {
            this.totalPageList2.push(i);
          }
        }, () => {
          this.page2--;
          this.findAllBookList2(this.page2, this.categoryId);
        }, () => {
          this.page = 0;
        });
        break;
      case 'name:desc':
        this.page = page;
        this.bookService.findAllBookListZA(this.page, this.categoryId).subscribe((data: any) => {
          this.bookList = data.content;
          this.totalPages = data.totalPages;
          this.size = data.size;

          this.totalPageList = [];
          for (let i = 0; i < this.totalPages; i++) {
            this.totalPageList.push(i);
          }
        }, () => {
          this.page--;
          this.findAllBookList(this.page, this.categoryId);
        }, () => {
          this.page2 = 0;
        });

        this.page2 = page;
        this.bookService.findAllBookListZA2(this.page2, this.categoryId).subscribe((data: any) => {
          this.bookList2 = data.content;
          this.totalPages2 = data.totalPages;
          this.size2 = data.size;

          this.totalPageList2 = [];
          for (let i = 0; i < this.totalPages2; i++) {
            this.totalPageList2.push(i);
          }
        }, () => {
          this.page2--;
          this.findAllBookList2(this.page2, this.categoryId);
        }, () => {
          this.page = 0;
        });
        break;
    }
  }

  // ======phân trang==========
  getPreviousPage() {
    this.page--;
    this.findAllBookList(this.page, this.categoryId);
  }

  getNextPage() {
    this.page++;
    this.findAllBookList(this.page, this.categoryId);
  }

  getNumberPage(pageNumber: number) {
    this.page = pageNumber;
    this.findAllBookList(this.page, this.categoryId);
  }

  getPreviousPage2() {
    this.page2--;
    this.findAllBookList2(this.page2, this.categoryId);
  }

  getNextPage2() {
    this.page2++;
    this.findAllBookList2(this.page2, this.categoryId);
  }

  getNumberPage2(pageNumber: number) {
    this.page2 = pageNumber;
    this.findAllBookList2(this.page2, this.categoryId);
  }

  // ========phân loại list======
  checkList(check1: number) {
    if (check1 === 1 && this.checkSort === '') {
      this.page = 0;
      this.check = 1;
      this.findAllBookList(this.page, this.categoryId);
    }
    if (check1 === 1 && this.checkSort !== '') {
      this.page = 0;
      this.check = 1;
      this.sortBookList(this.checkSort, this.page);
    }

    if (check1 === 2 && this.checkSort === '') {
      this.page2 = 0;
      this.check = 2;
      this.findAllBookList2(this.page2, this.categoryId);
    }
    if (check1 === 2 && this.checkSort !== '') {
      this.page2 = 0;
      this.check = 2;
      this.sortBookList(this.checkSort, this.page2);
    }
  }

  searchBook() {
    this.findAllBookList(0);
    this.findAllBookList2(0);
  }
}

