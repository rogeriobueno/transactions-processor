package com.planet.transactionapp.repository;

import com.planet.transactionapp.model.dto.ResponseCountryAmountDto;
import com.planet.transactionapp.model.entities.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Page<Transaction> findAllByCreditNumber(String cardNumber, Pageable pageable);

    @Query("""
            SELECT new com.planet.transactionapp.model.dto.ResponseCountryAmountDto(t.currency, SUM(t.amount))
            FROM Transaction t WHERE t.recipientName = :recipientName GROUP BY t.currency""")
    List<ResponseCountryAmountDto> findSumAmountGroupByCurrencyByRecipientName(String recipientName);

    @Query(" SELECT new com.planet.transactionapp.model.dto.ResponseCountryAmountDto(t.currency, SUM(t.amount)) FROM Transaction t GROUP BY t.currency ")
    List<ResponseCountryAmountDto> findSumAmountGroupByCurrency();

}
