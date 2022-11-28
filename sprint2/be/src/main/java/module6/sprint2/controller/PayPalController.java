//package module6.sprint2.controller;
//
//import module6.sprint2.dto.Order;
//import module6.sprint2.service.PayPalServices;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import com.paypal.api.payments.Links;
//import com.paypal.api.payments.Payment;
//import com.paypal.base.rest.PayPalRESTException;
//
//@Controller
//@CrossOrigin
//@RequestMapping("/api/book/book-customer/no-login")
//public class PayPalController {
//
//    @Autowired
//    PayPalServices payPalServices;
//
//    public static final String SUCCESS_URL = "pay/success";
//    public static final String CANCEL_URL = "pay/cancel";
//
//    @GetMapping("")
//    public String home() {
//        System.out.println(1111111);
//        return "home";
//    }
//
//    @PostMapping("/pay")
//    public String payment(@ModelAttribute("order") Order order) {
//        try {
//            Payment payment = payPalServices.createPayment(order.getPrice(), order.getCurrency(), order.getMethod(),
//                    order.getIntent(), order.getDescription(), "http://localhost:9090/" + CANCEL_URL,
//                    "http://localhost:9090/" + SUCCESS_URL);
//            for(Links link:payment.getLinks()) {
//                if(link.getRel().equals("approval_url")) {
//                    return "redirect:"+link.getHref();
//                }
//            }
//
//        } catch (PayPalRESTException e) {
//
//            e.printStackTrace();
//        }
//        return "redirect:";
//    }
//
//    @GetMapping(value = CANCEL_URL)
//    public String cancelPay() {
//        return "cancel";
//    }
//
//    @GetMapping(value = SUCCESS_URL)
//    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
//        try {
//            Payment payment = payPalServices.executePayment(paymentId, payerId);
//            System.out.println(payment.toJSON());
//            if (payment.getState().equals("approved")) {
//                return "success";
//            }
//        } catch (PayPalRESTException e) {
//            System.out.println(e.getMessage());
//        }
//        return "redirect:";
//    }
//}