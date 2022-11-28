package module6.sprint2.service;

import module6.sprint2.entity.cart.Cart;

import java.util.List;
import java.util.Optional;

public interface ICartService {
    List<Cart> findAllCart(Long id);

    void updateQuantityCart(Integer cartQuantity, Double cartTotalMoney, Long cartId);

    Cart addBook(Cart cart);

    Optional<Cart> findById(Long cardId);

    void deleteCartById(Long cartId);

    void paymentCart(String cartCode, String cartPurchaseDate, Boolean cartStatus, Long cartId);

    List<String> checkCodeCart();
}
