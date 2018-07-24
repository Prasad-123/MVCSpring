package com.cg.mypaymentapp.repo;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.cg.mypaymentapp.beans.Transactions;

public interface TransactionRepo extends JpaRepository<Transactions,String >,CrudRepository<Transactions,String> {
List <Transactions> findByMobileNo(String mobileNo);
}
