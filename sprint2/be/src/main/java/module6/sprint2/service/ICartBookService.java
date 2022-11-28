package module6.sprint2.service;

import module6.sprint2.entity.cart.Cart;
import module6.sprint2.entity.cart.CartBook;

import java.util.List;
import java.util.Optional;

public interface ICartBookService {
    List<CartBook> findAllCartBook(Long id);

    CartBook addBook(CartBook cartBook);

    CartBook findByCartId(Cart cart);

    Optional<CartBook> findCartBookByBookId(Long accountId, Long bookId);
}
