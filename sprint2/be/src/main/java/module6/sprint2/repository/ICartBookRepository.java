package module6.sprint2.repository;

import module6.sprint2.entity.cart.Cart;
import module6.sprint2.entity.cart.CartBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ICartBookRepository extends JpaRepository<CartBook, Long> {
    @Query(value = "SELECT `cart_book`.*, `cart`.cart_status, `cart`.cart_account_id FROM `cart_book`\n" +
            "join `cart` on `cart` .cart_id = `cart_book`.cart_id\n" +
            "having `cart_book`.book_id > 0 and `cart`.cart_status = 0 and `cart`.cart_account_id = ?1\n" +
            ";", nativeQuery = true)
    List<CartBook> findAllCartBook(Long id);

    CartBook findByCartId(Cart cart);

    @Query(value = "SELECT `cart_book`.*, `cart`.cart_status, `cart`.cart_account_id FROM `cart_book`\n" +
            "join `cart` on `cart` .cart_id = `cart_book`.cart_id\n" +
            "having `cart_book`.book_id > 0 and `cart`.cart_status = 0 and `cart`.cart_account_id = ?1 and `cart_book`.book_id = ?2\n" +
            ";", nativeQuery = true)
    Optional<CartBook> findCartBookByBookId(Long accountId, Long bookId);
}
