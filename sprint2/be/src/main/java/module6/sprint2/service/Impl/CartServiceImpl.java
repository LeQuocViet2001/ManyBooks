package module6.sprint2.service.Impl;

import module6.sprint2.entity.cart.Cart;
import module6.sprint2.repository.ICartRepository;
import module6.sprint2.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements ICartService {
    @Autowired
    ICartRepository cartRepository;

    @Override
    public List<Cart> findAllCart(Long id) {
        return cartRepository.findAllCart(id);
    }

    @Override
    public void updateQuantityCart(Integer cartQuantity, Double cartTotalMoney, Long cartId) {
        cartRepository.updateQuantityCart(cartQuantity, cartTotalMoney, cartId);
    }

    @Override
    public Cart addBook(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public Optional<Cart> findById(Long cardId) {
        return cartRepository.findById(cardId);
    }

    @Override
    public void deleteCartById(Long cartId) {
        cartRepository.deleteById(cartId);
    }

    @Override
    public void paymentCart(String cartCode, String cartPurchaseDate, Boolean cartStatus, Long cartId) {
        cartRepository.paymentCart(cartCode, cartPurchaseDate, cartStatus, cartId);
    }

    @Override
    public List<String> checkCodeCart() {
        return cartRepository.checkCodeCart();
    }
}
