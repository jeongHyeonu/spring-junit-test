package org.example.junit_study.domain.transaction;

import org.example.junit_study.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    List<Transaction> findTransactionList(Long id, String gubun, Integer page);
}
