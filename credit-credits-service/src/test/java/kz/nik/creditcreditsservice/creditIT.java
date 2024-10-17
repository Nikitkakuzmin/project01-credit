package kz.nik.creditcreditsservice;

import kz.nik.creditcreditsservice.AbstractTest;
import kz.nik.creditcreditsservice.api.CreditController;
import kz.nik.creditcreditsservice.model.Credit;
import kz.nik.creditcreditsservice.repository.CreditRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

@Sql(scripts = {"classpath:/sql/insert.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"classpath:/sql/clean.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class creditIT extends AbstractTest {

    @Autowired
    private CreditController creditController;

    @Autowired
    private CreditRepository creditRepository;

}
