package kz.nik.creditcreditsservice.repository;


import feign.Param;
import kz.nik.creditcreditsservice.model.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CreditRepository extends JpaRepository<Credit,Long> {
    List<Credit> findAll();
}
