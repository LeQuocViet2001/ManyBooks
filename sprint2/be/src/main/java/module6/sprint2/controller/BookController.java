package module6.sprint2.controller;

import module6.sprint2.entity.book.Author;
import module6.sprint2.entity.book.Book;
import module6.sprint2.entity.book.Category;
import module6.sprint2.service.IAuthorService;
import module6.sprint2.service.IBookService;
import module6.sprint2.service.ICategoryService;
import module6.sprint2.service.IPromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("api/book")
public class BookController {
    @Autowired
    IBookService bookService;

    @Autowired
    IAuthorService authorService;

    @Autowired
    ICategoryService categoryService;

    @Autowired
    IPromotionService promotionService;


    //===========intro=========
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    @GetMapping("/book-customer/no-login/book-list-intro")
    public ResponseEntity<List<Book>> findAllBookIntro() {
        List<Book> bookList = bookService.findAllBookIntro();
        if (bookList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(bookList, HttpStatus.OK);
        }
    }

    @GetMapping("/book-customer/no-login/book-list-best-seller-intro")
    public ResponseEntity<List<Book>> findAllBookBestSellerIntro() {
        List<Book> bookList = bookService.findAllBookBestSellerIntro();
        if (bookList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(bookList, HttpStatus.OK);
        }
    }

    // sach giam gia
    @GetMapping("/book-customer/no-login/book-list-promotion")
    public ResponseEntity<Page<Book>> findAllBookPromotion(@PageableDefault(value = 9) Pageable pageable) {
        Page<Book> bookList = bookService.findAllBookPromotion(pageable);
        if (bookList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(bookList, HttpStatus.OK);
        }
    }

    @GetMapping("/book-customer/no-login/book-list-promotion2")
    public ResponseEntity<Page<Book>> findAllBookPromotion2(@PageableDefault(value = 4) Pageable pageable) {
        Page<Book> bookList = bookService.findAllBookPromotion(pageable);
        if (bookList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(bookList, HttpStatus.OK);
        }
    }

    // sach ban chay
    @GetMapping("/book-customer/no-login/book-best-seller")
    public ResponseEntity<Page<Book>> findAllBookBestSellerList(@PageableDefault(value = 9) Pageable pageable) {
        Page<Book> bookList = bookService.findAllBookBestSellerList(pageable);
        if (bookList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(bookList, HttpStatus.OK);
        }
    }

    @GetMapping("/book-customer/no-login/book-best-seller2")
    public ResponseEntity<Page<Book>> findAllBookBestSellerList2(@PageableDefault(value = 4) Pageable pageable) {
        Page<Book> bookList = bookService.findAllBookBestSellerList(pageable);
        if (bookList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(bookList, HttpStatus.OK);
        }
    }

    // book cung tac gia
    @GetMapping("/book-customer/no-login/book-list-author")
    public ResponseEntity<List<Book>> findAllBookAuthor(@RequestParam("authorId") Long authorId,
                                                        @RequestParam("bookId") Long bookId) {
        List<Book> bookList = bookService.findAllBookAuthor(authorId, bookId);
//        if (bookList.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        } else {
            return new ResponseEntity<>(bookList, HttpStatus.OK);
//        }
    }

    @GetMapping("/book-customer/no-login/book-detail")
    public ResponseEntity<Book> findBookById(@RequestParam("bookId") Long bookId) {
        Book book = bookService.findBookById(bookId);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @GetMapping("/book-customer/no-login/book-list-best-seller")
    public ResponseEntity<List<Book>> findAllBookBestSeller() {
        List<Book> bookList = bookService.findAllBookBestSeller();
        if (bookList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(bookList, HttpStatus.OK);
        }
    }

    @GetMapping("/book-customer/no-login/author-intro")
    public ResponseEntity<List<Author>> findAllAuthor() {
        List<Author> authorList = authorService.findAllAuthor();
        if (authorList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(authorList, HttpStatus.OK);
        }
    }

    //==============list=========
//    @GetMapping("/book-customer/no-login/book-list")
//    public ResponseEntity<Page<Book>> findAllBookList(@PageableDefault(value = 9) Pageable pageable) {
//        Page<Book> bookPage = bookService.findAllBookList(pageable);
//        if (bookPage.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        } else {
//            return new ResponseEntity<>(bookPage, HttpStatus.OK);
//        }
//    }
//
//    @GetMapping("/book-customer/no-login/book-list2")
//    public ResponseEntity<Page<Book>> findAllBookList2(@PageableDefault(value = 4) Pageable pageable) {
//        Page<Book> bookPage = bookService.findAllBookList(pageable);
//        if (bookPage.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        } else {
//            return new ResponseEntity<>(bookPage, HttpStatus.OK);
//        }
//    }

    @GetMapping("/book-customer/no-login/book-list-category")
    public ResponseEntity<Page<Book>> findAllBookCategory(@PageableDefault(value = 9) Pageable pageable
            , @RequestParam("categoryId") Optional<String> categoryId, @RequestParam("bookName") String bookName) {
        if (bookName.equals("null")) {
            bookName = "";
        }
        System.out.println(categoryId.toString());
        if (categoryId.isPresent() && !categoryId.get().equals("null")) {
            Page<Book> bookPage = bookService.findAllBookCategory(categoryId.get(), bookName, pageable);
//            if (bookPage.isEmpty()) {
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            } else {
            return new ResponseEntity<>(bookPage, HttpStatus.OK);
//            }
        }
        Page<Book> bookPage = bookService.findAllBookListNameSearch(bookName, pageable);
//        if (bookPage.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        } else {
        return new ResponseEntity<>(bookPage, HttpStatus.OK);
//        }
    }

    @GetMapping("/book-customer/no-login/book-list-category2")
    public ResponseEntity<Page<Book>> findAllBookCategory2(@PageableDefault(value = 4) Pageable pageable
            , @RequestParam("categoryId") Optional<String> categoryId, @RequestParam("bookName") String bookName) {
        if (bookName.equals("null")) {
            bookName = "";
        }
        if (categoryId.isPresent() && !categoryId.get().equals("null")) {
            Page<Book> bookPage = bookService.findAllBookCategory(categoryId.get(), bookName, pageable);
//            if (bookPage.isEmpty()) {
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            } else {
            return new ResponseEntity<>(bookPage, HttpStatus.OK);
//            }
        }
        Page<Book> bookPage = bookService.findAllBookListNameSearch(bookName, pageable);
//        if (bookPage.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        } else {
        return new ResponseEntity<>(bookPage, HttpStatus.OK);
//        }
    }

    // sort book
    //11111 book-list
    @GetMapping("/book-customer/no-login/book-list-category/price-up")
    public ResponseEntity<Page<Book>> findAllBookListPriceUp(@PageableDefault(value = 9) Pageable pageable, @RequestParam("categoryId") Optional<Integer> categoryId) {
        if (categoryId.isPresent()) {
            Page<Book> bookPage = bookService.findAllBookListPriceUpCategory(categoryId.get(), pageable);
            if (bookPage.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(bookPage, HttpStatus.OK);
            }
        }

        Page<Book> bookPage = bookService.findAllBookListPriceUp(pageable);
        if (bookPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(bookPage, HttpStatus.OK);
        }
    }

    @GetMapping("/book-customer/no-login/book-list-category/price-down")
    public ResponseEntity<Page<Book>> findAllBookListPriceDown(@PageableDefault(value = 9) Pageable pageable, @RequestParam("categoryId") Optional<Integer> categoryId) {
        if (categoryId.isPresent()) {
            Page<Book> bookPage = bookService.findAllBookListPriceDownCategory(categoryId.get(), pageable);
            if (bookPage.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(bookPage, HttpStatus.OK);
            }
        }

        Page<Book> bookPage = bookService.findAllBookListPriceDown(pageable);
        if (bookPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(bookPage, HttpStatus.OK);
        }
    }

    @GetMapping("/book-customer/no-login/book-list-category/name-az")
    public ResponseEntity<Page<Book>> findAllBookListAZ(@PageableDefault(value = 9) Pageable pageable, @RequestParam("categoryId") Optional<Integer> categoryId) {
        if (categoryId.isPresent()) {
            Page<Book> bookPage = bookService.findAllBookListAZCategory(categoryId.get(), pageable);
            if (bookPage.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(bookPage, HttpStatus.OK);
            }
        }

        Page<Book> bookPage = bookService.findAllBookListAZ(pageable);
        if (bookPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(bookPage, HttpStatus.OK);
        }
    }

    @GetMapping("/book-customer/no-login/book-list-category/name-za")
    public ResponseEntity<Page<Book>> findAllBookListZA(@PageableDefault(value = 9) Pageable pageable, @RequestParam("categoryId") Optional<Integer> categoryId) {
        if (categoryId.isPresent()) {
            Page<Book> bookPage = bookService.findAllBookListZACategory(categoryId.get(), pageable);
            if (bookPage.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(bookPage, HttpStatus.OK);
            }
        }

        Page<Book> bookPage = bookService.findAllBookListZA(pageable);
        if (bookPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(bookPage, HttpStatus.OK);
        }
    }

    // book-list 222222
    @GetMapping("/book-customer/no-login/book-list-category2/price-up")
    public ResponseEntity<Page<Book>> findAllBookListPriceUp2(@PageableDefault(value = 4) Pageable pageable, @RequestParam("categoryId") Optional<Integer> categoryId) {
        if (categoryId.isPresent()) {
            Page<Book> bookPage = bookService.findAllBookListPriceUpCategory(categoryId.get(), pageable);
            if (bookPage.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(bookPage, HttpStatus.OK);
            }
        }

        Page<Book> bookPage = bookService.findAllBookListPriceUp(pageable);
        if (bookPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(bookPage, HttpStatus.OK);
        }
    }

    @GetMapping("/book-customer/no-login/book-list-category2/price-down")
    public ResponseEntity<Page<Book>> findAllBookListPriceDown2(@PageableDefault(value = 4) Pageable pageable, @RequestParam("categoryId") Optional<Integer> categoryId) {
        if (categoryId.isPresent()) {
            Page<Book> bookPage = bookService.findAllBookListPriceDownCategory(categoryId.get(), pageable);
            if (bookPage.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(bookPage, HttpStatus.OK);
            }
        }

        Page<Book> bookPage = bookService.findAllBookListPriceDown(pageable);
        if (bookPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(bookPage, HttpStatus.OK);
        }
    }

    @GetMapping("/book-customer/no-login/book-list-category2/name-az")
    public ResponseEntity<Page<Book>> findAllBookListAZ2(@PageableDefault(value = 4) Pageable pageable, @RequestParam("categoryId") Optional<Integer> categoryId) {
        if (categoryId.isPresent()) {
            Page<Book> bookPage = bookService.findAllBookListAZCategory(categoryId.get(), pageable);
            if (bookPage.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(bookPage, HttpStatus.OK);
            }
        }

        Page<Book> bookPage = bookService.findAllBookListAZ(pageable);
        if (bookPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(bookPage, HttpStatus.OK);
        }
    }

    @GetMapping("/book-customer/no-login/book-list-category2/name-za")
    public ResponseEntity<Page<Book>> findAllBookListZA2(@PageableDefault(value = 4) Pageable pageable, @RequestParam("categoryId") Optional<Integer> categoryId) {
        if (categoryId.isPresent()) {
            Page<Book> bookPage = bookService.findAllBookListZACategory(categoryId.get(), pageable);
            if (bookPage.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(bookPage, HttpStatus.OK);
            }
        }

        Page<Book> bookPage = bookService.findAllBookListZA(pageable);
        if (bookPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(bookPage, HttpStatus.OK);
        }
    }

    // =========header=========
    @GetMapping("/book-customer/no-login/category-list")
    public ResponseEntity<List<Category>> findAllCategory() {
        List<Category> categoryList = categoryService.findAllCategory();
        if (categoryList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(categoryList, HttpStatus.OK);
        }
    }
}
