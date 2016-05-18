package com.chrdrew;

import com.chrdrew.Types.TradeType;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.StringJoiner;
import java.util.TreeMap;

/**
 * Created by chrisdrew on 18/05/2016.
 */

public class Trade {
    private String symbol;
    private TradeType tradeType;
    private Integer quantity;
    private Double tradePrice;
    private LocalDateTime timestamp;

    public Trade(String symbol, TradeType tradeType, Integer quantity, Double tradePrice, LocalDateTime timestamp) {
        this.symbol = symbol;
        this.tradeType = tradeType;
        this.quantity = quantity;
        this.tradePrice = tradePrice;
        this.timestamp = timestamp;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public TradeType getTradeType() {
        return tradeType;
    }

    public void setTradeType(TradeType tradeType) {
        this.tradeType = tradeType;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getTradePrice() {
        return tradePrice;
    }

    public void setTradePrice(Double tradePrice) {
        this.tradePrice = tradePrice;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add(this.getTradeType().toString()).add(this.getSymbol()).add(this.getQuantity().toString()).add("@").add(this.getTradePrice().toString()).add(this.getTimestamp().toString());
        return joiner.toString();
    }

    public static double volumeWeightedStockPrice(final TreeMap<LocalDateTime, Trade> stockTrades, final Integer minutes) {
        Double totalVolume = 0.0;
        Double totalPrice = 0.0;

        for (Trade trade : stockTrades.tailMap(LocalDateTime.now().minus(minutes, ChronoUnit.MINUTES)).values()) {
            totalVolume += trade.getQuantity();
            totalPrice += trade.getQuantity() * trade.getTradePrice();
        }

        return totalPrice / totalVolume;
    }
}