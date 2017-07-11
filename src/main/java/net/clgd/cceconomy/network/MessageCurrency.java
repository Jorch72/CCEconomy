package net.clgd.cceconomy.network;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.clgd.cceconomy.economy.Currency;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@AllArgsConstructor
public class MessageCurrency implements IMessage {
	@Getter private Currency currency;
	
	public MessageCurrency() {
		this.currency = new Currency();
	}
	
	public MessageCurrency(org.spongepowered.api.service.economy.Currency spongeCurrency) {
		this.currency = convertCurrency(spongeCurrency);
	}
	
	private Currency convertCurrency(org.spongepowered.api.service.economy.Currency spongeCurrency) {
		Currency newCurrency = new Currency();
		
		newCurrency.clazz = spongeCurrency.getClass().getName();
		
		newCurrency.displayName = spongeCurrency.getDisplayName().toPlain();
		newCurrency.pluralDisplayName = spongeCurrency.getPluralDisplayName().toPlain();
		newCurrency.symbol = spongeCurrency.getSymbol().toPlain();
		
		newCurrency.defaultFractionDigits = spongeCurrency.getDefaultFractionDigits();
		newCurrency.isDefault = spongeCurrency.isDefault();
		
		Class<?> spongeCurrencyClass = spongeCurrency.getClass();
		
		try {
			Field prefixSymbolField = spongeCurrencyClass.getField("prefixSymbol");
			newCurrency.prefixSymbol = prefixSymbolField.getBoolean(spongeCurrency);
		} catch (NoSuchFieldException | IllegalAccessException ignored) {
			try {
				Method isPrefixSymbolMethod = spongeCurrencyClass.getMethod("isPrefixSymbol");
				newCurrency.prefixSymbol = (boolean) isPrefixSymbolMethod.invoke(spongeCurrency);
			} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored1) {
				try {
					Method isSymbolPrefixedMethod = spongeCurrencyClass.getMethod("isSymbolPrefixed");
					newCurrency.prefixSymbol = (boolean) isSymbolPrefixedMethod.invoke(spongeCurrency);
				} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored2) {
					newCurrency.prefixSymbol = true;
				}
			}
		}
		
		return newCurrency;
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, currency.clazz);
		
		ByteBufUtils.writeUTF8String(buf, currency.displayName);
		ByteBufUtils.writeUTF8String(buf, currency.pluralDisplayName);
		ByteBufUtils.writeUTF8String(buf, currency.symbol);
		
		buf.writeInt(currency.defaultFractionDigits);
		
		buf.writeBoolean(currency.prefixSymbol);
		buf.writeBoolean(currency.isDefault);
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		currency.clazz = ByteBufUtils.readUTF8String(buf);
		
		currency.displayName = ByteBufUtils.readUTF8String(buf);
		currency.pluralDisplayName = ByteBufUtils.readUTF8String(buf);
		currency.symbol = ByteBufUtils.readUTF8String(buf);
		
		currency.defaultFractionDigits = buf.readInt();
		
		currency.prefixSymbol = buf.readBoolean();
		currency.isDefault = buf.readBoolean();
	}
}
