package com.chrdrew;

import com.chrdrew.Types.StockType;
import org.apache.commons.math3.stat.StatUtils;

import java.util.Map;

/**
 * Created by chrisdrew on 18/05/2016.
 */

public class Stock {
    private String symbol;
    private StockType stockType;
    private Double lastDividend;
    private Double fixedDividend;
    private Double parValue;

    // Constructor
    public Stock(String symbol, StockType stockType, Double lastDividend, Double fixedDividend, Double parValue) {
        this.symbol = symbol;
        this.stockType = stockType;
        this.lastDividend = lastDividend;
        this.fixedDividend = fixedDividend;
        this.parValue = parValue;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public StockType getStockType() {
        return stockType;
    }

    public void setStockType(StockType stockType) {
        this.stockType = stockType;
    }

    public Double getLastDividend() {
        return lastDividend;
    }

    public void setLastDividend(Double lastDividend) {
        this.lastDividend = lastDividend;
    }

    public Double getFixedDividend() {
        return fixedDividend;
    }

    public void setFixedDividend(Double fixedDividend) {
        this.fixedDividend = fixedDividend;
    }

    public Double getParValue() {
        return parValue;
    }

    public void setParValue(Double parValue) {
        this.parValue = parValue;
    }

    public Double dividendYield(final Double marketPrice) {
        if (this.stockType.equals(StockType.COMMON)) {
            return (this.lastDividend / marketPrice) * 100; // Show as percentage
        } else if (this.stockType.equals(StockType.PREFERRED)) {
            return ((this.getFixedDividend() * this.getParValue()) / marketPrice) * 100;
        } else {
            return null;
        }
    }

    public Double priceToEarningsRatio(final Double marketPrice) {
        if (this.getLastDividend() > 0) {
            return marketPrice / this.getLastDividend();
        } else {
            return 0.0;
        }
    }

    public static double allShareIndex(final Map<String, Stock> exchangeStocks) {
        Double[] stockPrices = new Double[exchangeStocks.size()];

        int i = 0;
        for (Stock stock : exchangeStocks.values()) {
            stockPrices[i] = stock.getParValue();
            i++;
        }

        return StatUtils.geometricMean(org.apache.commons.lang3.ArrayUtils.toPrimitive(stockPrices));
    }
}