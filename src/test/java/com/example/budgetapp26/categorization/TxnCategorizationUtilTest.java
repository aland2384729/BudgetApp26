package com.example.budgetapp26.categorization;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TxnCategorizationUtilTest {

    @Test
    public void testGetCategory() {
        TxnCategorizationUtil categorizationUtil = TxnCategorizationUtil.getInstance();

        assertEquals(TransactionCategory.MISCELLANEOUS,
                categorizationUtil.getCategory("DUFF TRANSACTION"), "Unexpected Transaction Category");

        assertEquals(TransactionCategory.FOOD_AND_DRINK,
                categorizationUtil.getCategory("ASDA"), "Unexpected Transaction Category");
        assertEquals(TransactionCategory.FOOD_AND_DRINK,
                categorizationUtil.getCategory("GREGGS"), "Unexpected Transaction Category");
        assertEquals(TransactionCategory.FOOD_AND_DRINK,
                categorizationUtil.getCategory("LIDL"), "Unexpected Transaction Category");

        assertEquals(TransactionCategory.HOME,
                categorizationUtil.getCategory("GIFFGAFF"), "Unexpected Transaction Category");
        assertEquals(TransactionCategory.HOME,
                categorizationUtil.getCategory("SOUTH LANARKSHIRE"), "Unexpected Transaction Category");

        assertEquals(TransactionCategory.FUN,
                categorizationUtil.getCategory("AMAZON.CO.UK"), "Unexpected Transaction Category");
        assertEquals(TransactionCategory.FUN,
                categorizationUtil.getCategory("GLASGOW SCIENCE CENTRE"), "Unexpected Transaction Category");

        assertEquals(TransactionCategory.INSURANCE,
                categorizationUtil.getCategory("COVERFORYOU.COM"), "Unexpected Transaction Category");

        assertEquals(TransactionCategory.COMMUTING,
                categorizationUtil.getCategory("KINGSWAY M O T"), "Unexpected Transaction Category");

        assertEquals(TransactionCategory.CHARITY,
                categorizationUtil.getCategory("JUSTGIVING"), "Unexpected Transaction Category");
    }

}