package module6.sprint2.controller;

import module6.sprint2.entity.book.Book;
import module6.sprint2.entity.cart.Cart;
import module6.sprint2.entity.cart.CartBook;
import module6.sprint2.service.IAccountService;
import module6.sprint2.service.IBookService;
import module6.sprint2.service.ICartBookService;
import module6.sprint2.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("api/cart")
public class CartController {
    @Autowired
    ICartService cartService;

    @Autowired
    ICartBookService cartBookService;

    @Autowired
    IAccountService accountService;

    @Autowired
    IBookService bookService;

    //    @GetMapping("/list-cart")
//    public ResponseEntity<List<Cart>> findAllCart(@RequestParam("accountId") Long accountId) {
//        List<Cart> cartList = cartService.findAllCart(accountId);
//        if (cartList.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        } else {
//            return new ResponseEntity<>(cartList, HttpStatus.OK);
//        }
//    }
    @GetMapping("/cart-book-detail")
    public ResponseEntity<CartBook> findCartBookByBookId(@RequestParam("accountId") Long accountId,
                                                         @RequestParam("bookId") Long bookId) {
        Optional<CartBook> cartBook = cartBookService.findCartBookByBookId(accountId, bookId);
        if (!cartBook.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(cartBook.get(), HttpStatus.OK);
        }
    }


    @GetMapping("/list-cart-book")
    public ResponseEntity<List<CartBook>> findAllCartBook(@RequestParam("accountId") Long accountId) {
        List<CartBook> cartBookList = cartBookService.findAllCartBook(accountId);
        if (cartBookList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(cartBookList, HttpStatus.OK);
        }
    }

    @PutMapping("/update-quantity")
    public ResponseEntity<CartBook> updateQuantityCart(@RequestBody CartBook cartBook) {
        Double totalMoney = cartBook.getBookId().getBookPrice() * cartBook.getCartId().getCartQuantity()
                - cartBook.getBookId().getBookPrice() * cartBook.getCartId().getCartQuantity()
                * (cartBook.getBookId().getBookPromotionId().getPromotionPercent() / 100);
        cartBook.getCartId().setCartTotalMoney(totalMoney);
        cartService.updateQuantityCart(cartBook.getCartId().getCartQuantity(), cartBook.getCartId().getCartTotalMoney(), cartBook.getCartId().getCartId());
        return new ResponseEntity<>(cartBook, HttpStatus.OK);
    }

    // book s??? l?????ng l?? s??? l?????ng ????a v??o gi??? h??ng
    @PostMapping("/add-book")
    public ResponseEntity<?> addBook(@RequestParam("accountId") Long accountId
            , @RequestBody Book book) {
        Book bookById = bookService.findBookById(book.getBookId());
        List<CartBook> cartBookList = cartBookService.findAllCartBook(accountId);
        // ki???m tra t???n t???i gi??? h??ng
        for (CartBook cartBook : cartBookList) {
            // update s??? l?????ng
            // book.getBookQuantity() l?? s??? l?????ng ????a v??o gi??? h??ng
            if (cartBook.getBookId().getBookId() == book.getBookId()) {
                if ((book.getBookQuantity() + cartBook.getCartId().getCartQuantity()) > bookById.getBookQuantity()) {
                    String message = "S??? l?????ng s??ch th??m ???? l???n h??n s??? l?????ng trong kho hi???n t???i ho???c h???t h??ng. Vui l??ng nh???p l???i";
                    return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
                }
                cartBook.getCartId().setCartQuantity(cartBook.getCartId().getCartQuantity() + book.getBookQuantity());
                Double totalMoney = cartBook.getBookId().getBookPrice() * cartBook.getCartId().getCartQuantity()
                        - cartBook.getBookId().getBookPrice() * cartBook.getCartId().getCartQuantity()
                        * (cartBook.getBookId().getBookPromotionId().getPromotionPercent() / 100);
                cartBook.getCartId().setCartTotalMoney(totalMoney);
                cartService.updateQuantityCart(cartBook.getCartId().getCartQuantity(), cartBook.getCartId().getCartTotalMoney(), cartBook.getCartId().getCartId());
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }

        // th??m s??ch m???i v??o gi??? h??ng
        // book.getBookQuantity() l?? s??? l?????ng ????a v??o gi??? h??ng
        if (book.getBookQuantity() > bookById.getBookQuantity()) {
            String message = "S??? l?????ng s??ch th??m ???? l???n h??n s??? l?????ng trong kho hi???n t???i. Vui l??ng nh???p l???i";
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

        Cart cart = new Cart();
        Double totalMoney = book.getBookPrice() * book.getBookQuantity()
                - book.getBookPrice() * book.getBookQuantity()
                * (book.getBookPromotionId().getPromotionPercent() / 100);
        cart.setCartQuantity(book.getBookQuantity());
        cart.setCartTotalMoney(totalMoney);
        cart.setCartAccountId(accountService.findById(accountId));

        Cart cartCreate = cartService.addBook(cart);

        CartBook cartBook = new CartBook();
        cartBook.setBookId(bookService.findBookById(book.getBookId()));
        cartBook.setCartId(cartCreate);

        CartBook cartBookCreate = cartBookService.addBook(cartBook);
        return new ResponseEntity<>(cartBookCreate, HttpStatus.OK);
    }

    // xo?? cart
    @DeleteMapping("/cart-delete")
    public ResponseEntity<Cart> deleteCustomer(@RequestParam("cardId") Long cardId) {
        Optional<Cart> foundCart = cartService.findById(cardId);
        if (!foundCart.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            cartService.deleteCartById(cardId);
            return new ResponseEntity<>(foundCart.get(), HttpStatus.NO_CONTENT);
        }
    }

    // thanh to??n gi??? h??ng
    @PutMapping("/payment")
    public ResponseEntity<?> paymentCart(@RequestBody List<Cart> cartListPayment) {
        List<String> cartCodeList = cartService.checkCodeCart();

        // l???y m?? ho?? ????n
        String cartCode = "";
        for (String code : cartCodeList) {
            cartCode = code;
        }
        if (cartCode.equals("")) {
            cartCode = "1";
        }

        String cartCodePayment = "";
        String[] cartCodeArray = cartCode.split("-");
        System.out.println(Integer.parseInt(cartCodeArray[1]));
        if ((Integer.parseInt(cartCodeArray[1]) + 1) < 10) {
            cartCodePayment = "MHD-00" + (Integer.parseInt(cartCodeArray[1]) + 1);
        } else if (Integer.parseInt(cartCodeArray[1] + 1) < 100) {
            cartCodePayment = "MHD-0" + (Integer.parseInt(cartCodeArray[1]) + 1);
        } else {
            cartCodePayment = "MHD-" + (Integer.parseInt(cartCodeArray[1]) + 1);
        }

        // l???y ng??y hi???n t???i
        LocalDateTime current = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formatted = current.format(formatter);

        CartBook cartBook = null;
        // ti???n h??nh thanh to??n
        for (Cart cart : cartListPayment) {
            cartService.paymentCart(cartCodePayment, formatted, true, cart.getCartId());

            // c???p nh???t l???i s??? l?????ng s??ch
            cartBook = cartBookService.findByCartId(cart);
            cartBook.getBookId().setBookQuantity(cartBook.getBookId().getBookQuantity() - cart.getCartQuantity());
            bookService.updateQuantityBook(cartBook.getBookId());
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
