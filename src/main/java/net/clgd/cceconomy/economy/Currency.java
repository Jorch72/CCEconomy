package net.clgd.cceconomy.economy;

import lombok.Getter;

import java.math.BigDecimal;

public class Currency {
	public String clazz;
	
	public String displayName;
	public String pluralDisplayName;
	public String symbol;
	
	public int defaultFractionDigits;
	
	public boolean prefixSymbol = true;
	@Getter public boolean isDefault;
	
	public String format(BigDecimal amount) {
		return this.format(amount, this.defaultFractionDigits);
	}
	
	public String format(BigDecimal amount, int numFractionDigits) {
		if (prefixSymbol) {
			return symbol + amount.setScale(numFractionDigits, BigDecimal.ROUND_HALF_UP);
		} else {
			return amount.setScale(numFractionDigits, BigDecimal.ROUND_HALF_UP) + symbol;
		}
	}
}
