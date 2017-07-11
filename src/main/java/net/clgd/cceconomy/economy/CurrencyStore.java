package net.clgd.cceconomy.economy;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class CurrencyStore {
	@Getter private static Map<String, Currency> currencies = new HashMap<>();
	
	public static void addCurrency(Currency currency) {
		currencies.put(currency.clazz, currency);
	}
	
	public static Currency getCurrency(String clazz) {
		return currencies.get(clazz);
	}
	
	public static Currency getDefaultCurrency() {
		return currencies.values().stream()
			.filter(Currency::isDefault)
			.findFirst()
			.orElse(null);
	}
}
