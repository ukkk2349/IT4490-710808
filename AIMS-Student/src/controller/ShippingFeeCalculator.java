package controller;

import entity.order.Order;

/**
 * @author UyenNTT
 *
 */
public interface ShippingFeeCalculator {

	public int calculateShippingFee(Order order);
}
