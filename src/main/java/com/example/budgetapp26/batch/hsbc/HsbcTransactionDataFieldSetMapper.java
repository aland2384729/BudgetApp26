package com.example.budgetapp26.batch.hsbc;

import com.example.budgetapp26.batch.TransactionData;
import com.example.budgetapp26.entities.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.infrastructure.item.file.mapping.FieldSetMapper;
import org.springframework.batch.infrastructure.item.file.transform.FieldSet;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class HsbcTransactionDataFieldSetMapper implements FieldSetMapper<TransactionData> {

    private static final Logger log = LoggerFactory.getLogger(HsbcTransactionDataFieldSetMapper.class);

    private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public TransactionData mapFieldSet(FieldSet fieldSet) {
        TransactionData txnData = new HsbcTransactionData();

        try {
            txnData.setTxnDate(formatter.parse(fieldSet.readString("txnDate")));
        } catch (ParseException e) {
            String msg = "ParseException occurred unexpectedly when processing Date";
            log.error(msg);
            throw new RuntimeException(msg, e);
        }

        txnData.setDescription(fieldSet.readString("description"));

        String value = fieldSet.readString("value");
        if (value.contains(",")) {
            log.debug("Quotes detected in value " + value);
            String unquotedValue = value.replace(",", "");
            log.debug("Unquoted value = " + unquotedValue);
            txnData.setValue(Double.parseDouble(unquotedValue));
        } else {
            txnData.setValue(Double.parseDouble(value));
        }

        return txnData;
    }

}
