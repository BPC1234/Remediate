-- Holiday or weedends payment
SELECT
   :ruleId AS ruleId
  ,:ruleTitle AS ruleTitle
  ,:ruleCode AS ruleCode
  ,:ruleExp AS ruleExp
  ,tx.id AS transactionId,
  tx.job_id
FROM transaction tx
  LEFT JOIN region cnt
    ON (cnt.id = 162)
  LEFT JOIN weekend wknd
    ON (wknd.id = cnt.weekend_id)
WHERE (DATE_FORMAT(tx.transaction_date, '%Y-%m-%d')
       IN (SELECT
             DATE_FORMAT(holiday_date, '%Y-%m-%d')
           FROM holiday)
       OR DATE_FORMAT(tx.transaction_date, '%W') IN (wknd.day_one, wknd.day_two)) AND tx.job_Id = :jobId;



-- VMF or EMF address contains P.O address

    SELECT
   :ruleId AS ruleId
  ,:ruleTitle AS ruleTitle
  ,:ruleCode AS ruleCode
  ,:ruleExp AS ruleExp
  ,tx.id AS transactionId,
  tx.job_id
FROM transaction tx
    JOIN vendor_master_ledger vm ON( vm.id = tx.vendor_master_ledger_FK)
WHERE (vm.entity_address LIKE '%pobox%'
       OR vm.entity_address LIKE '%po box%'
       OR vm.entity_address LIKE '%P.O%'
         OR vm.entity_address LIKE '%p o%'
      )
      AND tx.job_Id =:jobId
UNION
SELECT
   :ruleId AS ruleId
  ,:ruleTitle AS ruleTitle
  ,:ruleCode AS ruleCode
  ,:ruleExp AS ruleExp
  ,tx.id AS transactionId,
  tx.job_id
FROM transaction tx
    JOIN employee_master_ledger cm ON( cm.id = tx.employee_master_ledger_FK)
WHERE (cm.employee_address LIKE '%poboxf%'
       OR cm.employee_address LIKE '%po box%'
       OR cm.employee_address LIKE '%P.O%'
       OR cm.employee_address LIKE  '%p o%'
      )
      AND tx.job_Id =:jobId

-- EMF or EMF address contains "care of" or "c o"

  SELECT
   :ruleId AS ruleId
  ,:ruleTitle AS ruleTitle
  ,:ruleCode AS ruleCode
  ,:ruleExp AS ruleExp
  ,tx.id AS transactionId,
  tx.job_id
FROM transaction tx
JOIN  vendor_master_ledger vm ON ( vm.id = tx.vendor_master_ledger_FK)
WHERE (vm.entity_address LIKE '%care of%'
       OR vm.entity_address LIKE '%C/O%'
       OR vm.entity_address LIKE '%C. O%'
      )
      AND tx.job_Id =:jobId
UNION
SELECT
   :ruleId AS ruleId
  ,:ruleTitle AS ruleTitle
  ,:ruleCode AS ruleCode
  ,:ruleExp AS ruleExp
  ,tx.id AS transactionId,
  tx.job_id
FROM transaction tx
   JOIN  employee_master_ledger cm ON ( cm.id = tx.employee_master_ledger_FK)
WHERE (cm.employee_address LIKE '%care of%'
       OR cm.employee_address LIKE '%C/O%'
       OR cm.employee_address LIKE '%C. O%'
      )
      AND tx.job_Id =:jobId


-- vmf status value "Do not pay" or "inactive"

  SELECT
   :ruleId AS ruleId
  ,:ruleTitle AS ruleTitle
  ,:ruleCode AS ruleCode
  ,:ruleExp AS ruleExp
  ,tx.id AS transactionId,
  tx.job_id
FROM transaction tx
   JOIN  vendor_master_ledger vm ON ( vm.id = tx.vendor_master_ledger_FK)
where (entity_status = 'inactive' or entity_status = 'Do not pay') AND tx.job_Id = :jobId

-- vmf status value "OFAC" or "PEP"

  SELECT
   :ruleId AS ruleId
  ,:ruleTitle AS ruleTitle
  ,:ruleCode AS ruleCode
  ,:ruleExp AS ruleExp
  ,tx.id AS transactionId,
  tx.job_id
FROM transaction tx
   JOIN  vendor_master_ledger vm ON ( vm.id = tx.vendor_master_ledger_FK)
where (entity_status = 'OFAC' or entity_status = 'PEP') AND tx.job_Id = :jobId

-- vmf status value "inactive" or ''
-- Transaction with entity without designated status value   OK
  SELECT
   :ruleId AS ruleId
  ,:ruleTitle AS ruleTitle
  ,:ruleCode AS ruleCode
  ,:ruleExp AS ruleExp
  ,tx.id AS transactionId,
  tx.job_id
FROM transaction tx
   JOIN  vendor_master_ledger vm ON ( vm.id = tx.vendor_master_ledger_FK)
where (entity_status = 'inactive' or entity_status = '') AND tx.job_Id = :jobId


-- Round Transaction amount
  SELECT
   :ruleId AS ruleId
  ,:ruleTitle AS ruleTitle
  ,:ruleCode AS ruleCode
  ,:ruleExp AS ruleExp
  ,tx.id AS transactionId,
  tx.job_id
  FROM transaction tx
WHERE (tx.amount MOD 1000) = 0  AND tx.job_Id = :jobId;

--   Multiple gift transNarrative matches 65%

  SELECT
   :ruleId AS ruleId
  ,:ruleTitle AS ruleTitle
  ,:ruleCode AS ruleCode
  ,:ruleExp AS ruleExp
  ,tx.id AS transactionId,
  tx.job_id
FROM transaction tx
  INNER JOIN (fund_disbursement_ledger fdl
    INNER JOIN (SELECT trx.id, transactionNarrativeOrDescription
                from transaction trx
                  JOIN fund_disbursement_ledger fdli ON (trx.id = fdli.transaction_fk)
                WHERE  transactionNarrativeOrDescription != '' AND transactionNarrativeOrDescription IS NOT NULL
                GROUP BY transactionNarrativeOrDescription
                HAVING COUNT(fdli.id) > 1
               ) temp
      ON levenshtein_ratio(TRIM(fdl.transactionNarrativeOrDescription), temp.transactionNarrativeOrDescription) > 64)
    ON tx.id = fdl.transaction_fk
 WHERE tx.job_Id = :jobId;


-- Duplicate Document Number
SELECT
   :ruleId AS ruleId
  ,:ruleTitle AS ruleTitle
  ,:ruleCode AS ruleCode
  ,:ruleExp AS ruleExp
  ,tx.id AS transactionId,
  tx.job_id
FROM transaction tx
  INNER JOIN (accounts_payable_ledger_information apl
                INNER JOIN (SELECT
                            document_number,
                            document_date
                            FROM accounts_payable_ledger_information
                            GROUP BY document_number
                            HAVING COUNT(id) > 1) temp
                  ON apl.document_number = temp.document_number)
    ON tx.id = apl.transaction_fk
WHERE tx.job_Id = :jobId


--

SELECT
   :ruleId AS ruleId
  ,:ruleTitle AS ruleTitle
  ,:ruleCode AS ruleCode
  ,:ruleExp AS ruleExp
  ,tx.id AS transactionId,
  tx.job_id
FROM transaction tx
WHERE tx.employee_master_ledger_FK IS NULL AND tx.vendor_master_ledger_FK IS NULL AND tx.job_Id = :jobId;



CREATE FUNCTION `levenshtein`( s1 text, s2 text) RETURNS int(11)
DETERMINISTIC
BEGIN
DECLARE s1_len, s2_len, i, j, c, c_temp, cost INT;
DECLARE s1_char CHAR;
DECLARE cv0, cv1 text;
SET s1_len = CHAR_LENGTH(s1), s2_len = CHAR_LENGTH(s2), cv1 = 0x00, j = 1, i = 1, c = 0;
IF s1 = s2 THEN
RETURN 0;
ELSEIF s1_len = 0 THEN
RETURN s2_len;
ELSEIF s2_len = 0 THEN
RETURN s1_len;
ELSE
WHILE j <= s2_len DO
SET cv1 = CONCAT(cv1, UNHEX(HEX(j))), j = j + 1;
END WHILE;
WHILE i <= s1_len DO
SET s1_char = SUBSTRING(s1, i, 1), c = i, cv0 = UNHEX(HEX(i)), j = 1;
WHILE j <= s2_len DO
SET c = c + 1;
IF s1_char = SUBSTRING(s2, j, 1) THEN
SET cost = 0; ELSE SET cost = 1;
END IF;
SET c_temp = CONV(HEX(SUBSTRING(cv1, j, 1)), 16, 10) + cost;
IF c > c_temp THEN SET c = c_temp; END IF;
SET c_temp = CONV(HEX(SUBSTRING(cv1, j+1, 1)), 16, 10) + 1;
IF c > c_temp THEN
SET c = c_temp;
END IF;
SET cv0 = CONCAT(cv0, UNHEX(HEX(c))), j = j + 1;
END WHILE;
SET cv1 = cv0, i = i + 1;
END WHILE;
END IF;
RETURN c;
END;


CREATE FUNCTION `levenshtein_ratio`( s1 text, s2 text ) RETURNS int(11)
DETERMINISTIC
  BEGIN
    DECLARE s1_len, s2_len, max_len INT;
    SET s1_len = LENGTH(s1), s2_len = LENGTH(s2);
    IF s1_len > s2_len THEN
      SET max_len = s1_len;
    ELSE
      SET max_len = s2_len;
    END IF;
    RETURN ROUND((1 - LEVENSHTEIN(s1, s2) / max_len) * 100);
  END;
