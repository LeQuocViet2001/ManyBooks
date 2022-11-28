package module6.sprint2.repository;

import module6.sprint2.entity.book.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface IBookRepository extends JpaRepository<Book, Long> {
    @Query(value = "select * from book where book_flag = 0 and book_id > 0 order by book_publish_date desc", nativeQuery = true)
    List<Book> findAllBookIntro();

    @Query(value = "select * from book where book_flag = 0 and book_id > 0 order by book_publish_date desc", nativeQuery = true)
    Page<Book> findAllBookList(Pageable pageable);

    @Query(value = "select * from book where book_flag = 0 and book_id > 0 and book_id = ?1", nativeQuery = true)
    Book findBookById(Long id);

    @Query(value = "select * from book where book_flag = 0 and book_id > 0 and book_author_id = ?1 and book_id != ?2 order by book_publish_date desc", nativeQuery = true)
    List<Book> findAllBookAuthor(Long idAuthor, Long idBook);

    @Query(value = "select * from book where book_flag = 0 and book_id > 0 and book_promotion_id > 1 order by book_promotion_id desc", nativeQuery = true)
    Page<Book> findAllBookPromotion(Pageable pageable);

    @Query(value = "select book.*, `cart`.cart_status from book\n" +
            "join cart_book on book.book_id = cart_book.book_id\n" +
            "join cart on cart.cart_id = cart_book.cart_id\n" +
            "group by cart_book.book_id \n" +
            "having sum(cart.cart_quantity) is not null and book.book_flag = 0 and `cart`.cart_status = 1 and `cart_book`.book_id > 0 and `book`.book_quantity > 0\n" +
            "order by sum(cart.cart_quantity) desc limit 3;", nativeQuery = true)
    List<Book> findAllBookBestSellerIntro();

    @Query(value = "select `book`.*, `cart`.cart_status from `book`\n" +
            "join `cart_book` on `book`.book_id = `cart_book`.book_id\n" +
            "join `cart` on `cart`.cart_id = `cart_book`.cart_id\n" +
            "group by `cart_book`.book_id \n" +
            "having sum(`cart`.cart_quantity) is not null and `book`.book_flag = 0 and `cart`.cart_status = 1 and `cart_book`.book_id > 0\n" +
            "order by sum(`cart`.cart_quantity)", nativeQuery = true)
    Page<Book> findAllBookBestSellerList(Pageable pageable);

    @Query(value = "select book.*, `cart`.cart_status from book\n" +
            "join cart_book on book.book_id = cart_book.book_id\n" +
            "join cart on cart.cart_id = cart_book.cart_id\n" +
            "group by cart_book.book_id \n" +
            "having sum(cart.cart_quantity) is not null and book.book_flag = 0 and `cart`.cart_status = 1 and `cart_book`.book_id > 0\n" +
            "order by sum(cart.cart_quantity) desc;", nativeQuery = true)
    List<Book> findAllBookBestSeller();

    // list theo category
    @Query(value = "select * from book where book_flag = 0 and book_id > 0 and book_category_id like ?1 and book_name like ?2 order by book_publish_date desc", nativeQuery = true)
    Page<Book> findAllBookCategory(String categoryId, String nameBook, Pageable pageable);

    @Query(value = "select * from book where book_flag = 0 and book_id > 0 and book_name like ?1 order by book_publish_date desc", nativeQuery = true)
    Page<Book> findAllBookListNameSearch(String nameBook, Pageable pageable);

    //sort book
    @Query(value = "select * from book where book_flag = 0 and book_id > 0 order by book_price", nativeQuery = true)
    Page<Book> findAllBookListPriceUp(Pageable pageable);

    @Query(value = "select * from book where book_flag = 0 and book_id > 0 and book_category_id like ?1 order by book_price", nativeQuery = true)
    Page<Book> findAllBookListPriceUpCategory(String categoryId, Pageable pageable);

    @Query(value = "select * from book where book_flag = 0 and book_id > 0 order by book_price desc", nativeQuery = true)
    Page<Book> findAllBookListPriceDown(Pageable pageable);

    @Query(value = "select * from book where book_flag = 0 and book_id > 0 and book_category_id like ?1 order by book_price desc", nativeQuery = true)
    Page<Book> findAllBookListPriceDownCategory(String categoryId, Pageable pageable);

    @Query(value = "select * from book where book_flag = 0 and book_id > 0 order by book_name", nativeQuery = true)
    Page<Book> findAllBookListAZ(Pageable pageable);

    @Query(value = "select * from book where book_flag = 0 and book_id > 0 and book_category_id like ?1 order by book_name", nativeQuery = true)
    Page<Book> findAllBookListAZCategory(String categoryId, Pageable pageable);

    @Query(value = "select * from book where book_flag = 0 and book_id > 0 order by book_name desc", nativeQuery = true)
    Page<Book> findAllBookListZA(Pageable pageable);

    @Query(value = "select * from book where book_flag = 0 and book_id > 0 and book_category_id like ?1 order by book_name desc", nativeQuery = true)
    Page<Book> findAllBookListZACategory(String categoryId, Pageable pageable);
}
