package core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import core.items.PriceFormat;

/**
 * Tests if PriceFormat.java formats price as intended.
 * @author Ole Dahl
 */

public class PriceFormatTest {
    /**
     * Testing correct notation (k, m, b) and decimal places for positive and negative numbers.
     */
    @Test
    public void testNumberFormat(){
        Assertions.assertEquals("$125.0k",PriceFormat.numberFormat(125000.0));
        Assertions.assertEquals("$552.5m",PriceFormat.numberFormat(552500000.1));
        Assertions.assertEquals("$5.53b",PriceFormat.numberFormat(5525000001.9));
        Assertions.assertEquals("$125.01k",PriceFormat.numberFormat(125009.9));
        Assertions.assertEquals("$-552.5m",PriceFormat.numberFormat(-552500000.1));
        Assertions.assertEquals("$-5.53b",PriceFormat.numberFormat(-5525000001.9));
        Assertions.assertEquals("$-125.0k",PriceFormat.numberFormat(-125000.0));
        Assertions.assertEquals("$0.0k",PriceFormat.numberFormat(0.0));
    }
    /**
     * Testing correct notation (1,000 100,000 1,000,000 etc) for positive and negative numbers.
     */
    @Test
    public void testPriceNumberFormat() {
        Assertions.assertEquals("$125,000",PriceFormat.priceNumberFormat(125000.0));
        Assertions.assertEquals("$552,500,000.1",PriceFormat.priceNumberFormat(552500000.1));
        Assertions.assertEquals("$5,525,000,001.9",PriceFormat.priceNumberFormat(5525000001.9));
        Assertions.assertEquals("$125,009.9",PriceFormat.priceNumberFormat(125009.9));
        Assertions.assertEquals("$-552,500,000.1",PriceFormat.priceNumberFormat(-552500000.1));
        Assertions.assertEquals("$-5,525,000,001.9",PriceFormat.priceNumberFormat(-5525000001.9));
        Assertions.assertEquals("$-125,000",PriceFormat.priceNumberFormat(-125000.0));
        Assertions.assertEquals("$0.0",PriceFormat.priceNumberFormat(0.0));
    }

    /**
     * Testing correct rounding for positive and negative numbers. 
     */
    @Test
    public void testRound() {
        Assertions.assertEquals(10.0, PriceFormat.round(10.0, 2));
        Assertions.assertEquals(10.12, PriceFormat.round(10.12, 2));
        Assertions.assertEquals(10.1235, PriceFormat.round(10.1234566, 4));
        Assertions.assertEquals(10.12, PriceFormat.round(10.12456547475775275274646431, 2));
        Assertions.assertEquals(1156789.13, PriceFormat.round(1156789.125678878675, 2));
        Assertions.assertEquals(-10.12, PriceFormat.round(-10.12, 2));
        Assertions.assertEquals(-10.1235, PriceFormat.round(-10.1234566, 4));
    }
}
