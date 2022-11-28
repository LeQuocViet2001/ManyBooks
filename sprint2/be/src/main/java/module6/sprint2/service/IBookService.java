package module6.sprint2.service;

import module6.sprint2.entity.book.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IBookService {
    List<Book> findAllBookIntro();

    Page<Book> findAllBookList(Pageable pageable);

    Page<Book> findAllBookPromotion(Pageable pageable);

    Page<Book> findAllBookBestSellerList(Pageable pageable);

    List<Book> findAllBookBestSellerIntro();

    List<Book> findAllBookBestSeller();

    Page<Book> findAllBookCategory(String categoryId, String nameBook, Pageable pageable);

    Page<Book> findAllBookListNameSearch(String nameBook, Pageable pageable);

    Book findBookById(Long id);

    List<Book> findAllBookAuthor(Long idAuthor, Long idBook);

    //sort book
    Page<Book> findAllBookListPriceUp(Pageable pageable);

    Page<Book> findAllBookListPriceDown(Pageable pageable);

    Page<Book> findAllBookListAZ(Pageable pageable);

    Page<Book> findAllBookListZA(Pageable pageable);

    Page<Book> findAllBookListPriceUpCategory(Integer categoryId, Pageable pageable);

    Page<Book> findAllBookListPriceDownCategory(Integer categoryId, Pageable pageable);

    Page<Book> findAllBookListAZCategory(Integer categoryId, Pageable pageable);

    Page<Book> findAllBookListZACategory(Integer categoryId, Pageable pageable);

    //payment
    Book updateQuantityBook(Book book);
}
