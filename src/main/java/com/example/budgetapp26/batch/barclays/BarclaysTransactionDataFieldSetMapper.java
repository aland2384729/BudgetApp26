package com.example.budgetapp26.batch.barclays;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.infrastructure.item.file.mapping.FieldSetMapper;
import org.springframework.batch.infrastructure.item.file.transform.FieldSet;

import com.example.budgetapp26.batch.TransactionData;
import com.example.budgetapp26.batch.natwest.NatwestTransactionData;
import com.example.budgetapp26.batch.natwest.NatwestTransactionDataFieldSetMapper;

public class BarclaysTransactionDataFieldSetMapper implements FieldSetMapper<TransactionData> {

    private static final Logger log = LoggerFactory.getLogger(NatwestTransactionDataFieldSetMapper.class);

    private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public TransactionData mapFieldSet(FieldSet fieldSet) {
        TransactionData txnData = new NatwestTransactionData();

        if (fieldSet.getFieldCount() > 1) {
            try {
                //txnData.setTxnDate(formatter.parse(fieldSet.readString("txnDate")));
                txnData.setTxnDate(formatter.parse(fieldSet.readString(1)));
            } catch (ParseException e) {
                String msg = "ParseException occurred unexpectedly when processing Date";
                log.error(msg);
                throw new RuntimeException(msg, e);
            }

            //txnData.setDescription(fieldSet.readString("description"));
            txnData.setDescription(fieldSet.readString(5));
            //txnData.setValue(Double.parseDouble(fieldSet.readString("value")));
            txnData.setValue(Double.parseDouble(fieldSet.readString(3)));
        } else {
            // handle empty line gracefully
            log.warn("Ignored line " + fieldSet.toString());
        }

        return txnData;
    }

}