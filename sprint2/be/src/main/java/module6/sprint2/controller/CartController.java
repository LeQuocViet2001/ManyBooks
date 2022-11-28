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

    // book số lượng là số lượng đưa vào giỏ hàng
    @PostMapping("/add-book")
    public ResponseEntity<?> addBook(@RequestParam("accountId") Long accountId
            , @RequestBody Book book) {
        Book bookById = bookService.findBookById(book.getBookId());
        List<CartBook> cartBookList = cartBookService.findAllCartBook(accountId);
        // kiểm tra tồn tại giỏ hàng
        for (CartBook cartBook : cartBookList) {
            // update số lượng
            // book.getBookQuantity() là số lượng đưa vào giỏ hàng
            if (cartBook.getBookId().getBookId() == book.getBookId()) {
                if ((book.getBookQuantity() + cartBook.getCartId().getCartQuantity()) > bookById.getBookQuantity()) {
                    String message = "Số lượng sách thêm đã lớn hơn số lượng trong kho hiện tại hoặc hết hàng. Vui lòng nhập lại";
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

        // thêm sách mới vào giỏ hàng
        // book.getBookQuantity() là số lượng đưa vào giỏ hàng
        if (book.getBookQuantity() > bookById.getBookQuantity()) {
            String message = "Số lượng sách thêm đã lớn hơn số lượng trong kho hiện tại. Vui lòng nhập lại";
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

    // xoá cart
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

    // thanh toán giỏ hàng
    @PutMapping("/payment")
    public ResponseEntity<?> paymentCart(@RequestBody List<Cart> cartListPayment) {
        List<String> cartCodeList = cartService.checkCodeCart();

        // lấy mã hoá đơn
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

        // lấy ngày hiện tại
        LocalDateTime current = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formatted = current.format(formatter);

        CartBook cartBook = null;
        // tiến hành thanh toán
        for (Cart cart : cartListPayment) {
            cartService.paymentCart(cartCodePayment, formatted, true, cart.getCartId());

            // cập nhật lại số lượng sách
            cartBook = cartBookService.findByCartId(cart);
            cartBook.getBookId().setBookQuantity(cartBook.getBookId().getBookQuantity() - cart.getCartQuantity());
            bookService.updateQuantityBook(cartBook.getBookId());
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
