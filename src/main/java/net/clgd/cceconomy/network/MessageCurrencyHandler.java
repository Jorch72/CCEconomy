package net.clgd.cceconomy.network;

import net.clgd.cceconomy.CCEconomy;
import net.clgd.cceconomy.economy.Currency;
import net.clgd.cceconomy.economy.CurrencyStore;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageCurrencyHandler implements IMessageHandler<MessageCurrency, IMessage> {
	@Override
	public IMessage onMessage(MessageCurrency message, MessageContext ctx) {
		Currency currency = message.getCurrency();
		CurrencyStore.addCurrency(currency);
		
		CCEconomy.logger.info("Server gave us currency {}", currency.clazz);
		
		return null;
	}
}
