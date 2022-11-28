package module6.sprint2.service.Impl;

import module6.sprint2.entity.book.Book;
import module6.sprint2.repository.IBookRepository;
import module6.sprint2.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements IBookService {
    @Autowired
    IBookRepository bookRepository;


    @Override
    public List<Book> findAllBookIntro() {
        return bookRepository.findAllBookIntro();
    }

    @Override
    public Page<Book> findAllBookList(Pageable pageable) {
        return bookRepository.findAllBookList(pageable);
    }

    @Override
    public Page<Book> findAllBookPromotion(Pageable pageable) {
        return bookRepository.findAllBookPromotion(pageable);
    }

    @Override
    public Page<Book> findAllBookBestSellerList(Pageable pageable) {
        return bookRepository.findAllBookBestSellerList(pageable);
    }


    @Override
    public List<Book> findAllBookBestSellerIntro() {
        return bookRepository.findAllBookBestSellerIntro();
    }

    @Override
    public List<Book> findAllBookBestSeller() {
        return bookRepository.findAllBookBestSeller();
    }

    @Override
    public Page<Book> findAllBookCategory(String categoryId, String nameBook, Pageable pageable) {
        return bookRepository.findAllBookCategory("%" + categoryId + "%", "%" + nameBook + "%", pageable);
    }

    @Override
    public Page<Book> findAllBookListNameSearch(String nameBook, Pageable pageable) {
        return bookRepository.findAllBookListNameSearch("%" + nameBook + "%", pageable);
    }

    @Override
    public Book findBookById(Long id) {
        return bookRepository.findBookById(id);
    }

    @Override
    public List<Book> findAllBookAuthor(Long idAuthor, Long idBook) {
        return bookRepository.findAllBookAuthor(idAuthor, idBook);
    }

    @Override
    public Page<Book> findAllBookListPriceUp(Pageable pageable) {
        return bookRepository.findAllBookListPriceUp(pageable);
    }

    @Override
    public Page<Book> findAllBookListPriceDown(Pageable pageable) {
        return bookRepository.findAllBookListPriceDown(pageable);
    }

    @Override
    public Page<Book> findAllBookListAZ(Pageable pageable) {
        return bookRepository.findAllBookListAZ(pageable);
    }

    @Override
    public Page<Book> findAllBookListZA(Pageable pageable) {
        return bookRepository.findAllBookListZA(pageable);
    }

    @Override
    public Page<Book> findAllBookListPriceUpCategory(Integer categoryId, Pageable pageable) {
        return bookRepository.findAllBookListPriceUpCategory("%" + categoryId + "%", pageable);
    }

    @Override
    public Page<Book> findAllBookListPriceDownCategory(Integer categoryId, Pageable pageable) {
        return bookRepository.findAllBookListPriceDownCategory("%" + categoryId + "%", pageable);
    }

    @Override
    public Page<Book> findAllBookListAZCategory(Integer categoryId, Pageable pageable) {
        return bookRepository.findAllBookListAZCategory("%" + categoryId + "%", pageable);
    }

    @Override
    public Page<Book> findAllBookListZACategory(Integer categoryId, Pageable pageable) {
        return bookRepository.findAllBookListZACategory("%" + categoryId + "%", pageable);
    }

    @Override
    public Book updateQuantityBook(Book book) {
        return bookRepository.save(book);
    }


}
