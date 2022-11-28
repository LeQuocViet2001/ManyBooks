package module6.sprint2.repository;

import module6.sprint2.entity.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ICustomerRepository extends JpaRepository<Customer, Long> {
    @Query(value = "SELECT * FROM customer WHERE customer_account_id =?1", nativeQuery = true)
    Customer findCustomerByAccountId(Long accountId);
}
