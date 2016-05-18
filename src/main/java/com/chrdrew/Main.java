package com.chrdrew;

import com.chrdrew.Types.StockType;
import com.chrdrew.Types.TradeType;

import java.time.LocalDateTime;
import java.util.*;

import static com.chrdrew.Trade.volumeWeightedStockPrice;

/**
 * Created by chrisdrew on 18/05/2016.
 */

public class Main {
    public static void main(String[] args) throws InterruptedException {

        Map<String, Stock> exchangeStocks = new HashMap<String, Stock>();
        exchangeStocks.put("TEA", new Stock("TEA", StockType.COMMON, 0.0, null, 100.0));
        exchangeStocks.put("POP", new Stock("POP", StockType.COMMON, 8.0, null, 100.0));
        exchangeStocks.put("ALE", new Stock("ALE", StockType.COMMON, 23.0, null, 60.0));
        exchangeStocks.put("GIN", new Stock("GIN", StockType.PREFERRED, 8.0, 0.2, 100.0));
        exchangeStocks.put("JOE", new Stock("JOE", StockType.COMMON, 13.0, null, 250.0));

        List<String> stockSymbols = new ArrayList<String>(exchangeStocks.keySet());

        // Groups treemaps of stock trades by symbol
        Map<String, TreeMap<LocalDateTime, Trade>> trades = new HashMap<>();

        // Initialize treemap for each symbol
        for (String symbol : stockSymbols) {
            trades.put(symbol, new TreeMap<>());
        }

        // Populate with some random trades
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            Thread.sleep(10); // Sleep because using current time as treemap key
            LocalDateTime tradeTimestamp = LocalDateTime.now();
            String randomStock = stockSymbols.get(random.nextInt(stockSymbols.size()));
            Double randomStockPrice = Double.parseDouble(randomNumber(random, 1, 100, 2));
            Trade trade = new Trade(randomStock, randomEnum(TradeType.class, random), random.nextInt(500), randomStockPrice, tradeTimestamp);
            trades.get(randomStock).put(tradeTimestamp, trade);
            System.out.println(trade.toString());
        }

        // Perform calculations and display results on each stock
        exchangeStocks.forEach((k, v) -> {
            System.out.println("---");
            System.out.println(k + " Dividend yield = " + v.dividendYield(100.0) + "%");
            System.out.println(k + " P/E radio = " + String.format("%.2f", v.priceToEarningsRatio(100.0)));
            System.out.println(k + " Volume weighted stock price 15 mins = " + String.format("$%.2f", volumeWeightedStockPrice(trades.get(k), 15)));
        });

        System.out.println("---");
        System.out.println("GBCE all share index: " + String.format("$%.2f", Stock.allShareIndex(exchangeStocks)));
    }

    public static <T extends Enum<?>> T randomEnum(Class<T> clazz, Random random) {
        int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }

    public static String randomNumber(final Random random, final int lowerBound, final int upperBound, final int decimalPlaces) {
        final double dbl = ((random == null ? new Random() : random).nextDouble() * (upperBound - lowerBound)) + lowerBound;
        return String.format("%." + decimalPlaces + "f", dbl);
    }
}


