package com.example.budgetapp26.batch.tsb;

import com.example.budgetapp26.batch.TransactionData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.infrastructure.item.file.mapping.FieldSetMapper;
import org.springframework.batch.infrastructure.item.file.transform.FieldSet;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TsbTransactionDataFieldSetMapper implements FieldSetMapper<TransactionData> {

    private static final Logger log = LoggerFactory.getLogger(TsbTransactionDataFieldSetMapper.class);

    private SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    public TransactionData mapFieldSet(FieldSet fieldSet) {
        TransactionData txnData = new TsbTransactionData();

        try {
            txnData.setTxnDate(formatter.parse(fieldSet.readString("date")));
        } catch (ParseException e) {
            String msg = "ParseException occurred unexpectedly when processing Date";
            log.error(msg);
            throw new RuntimeException(msg, e);
        }

        txnData.setDescription(fieldSet.readString("description"));
        txnData.setValue(Double.parseDouble(fieldSet.readString("amount")));

        return txnData;
    }

}
