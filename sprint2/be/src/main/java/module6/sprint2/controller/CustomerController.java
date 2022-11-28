package module6.sprint2.controller;

import module6.sprint2.entity.customer.Customer;
import module6.sprint2.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("api/customer")
public class CustomerController {
    @Autowired
    ICustomerService customerService;

    @GetMapping("/getCustomerByAccount")
    public ResponseEntity<Customer> findCustomerByAccountId(@RequestParam("accountId") Long accountId) {
        Customer customer = this.customerService.findCustomerByAccountId(accountId);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }
}
