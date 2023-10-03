package com.mainspring.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mainspring.app.entity.Statement;

@Repository
public interface StatementRepository extends JpaRepository<Statement, Integer> {

	@Query(nativeQuery = true, value = "SELECT * FROM `statement` WHERE account_id = (:accountId) AND `date` BETWEEN (:fromDate) AND (:toDate)")
	List<Statement> findStatementsBetweenDate(@Param("fromDate") String fromDate, @Param("toDate") String toDate,
			@Param("accountId") Integer accountId);

}
