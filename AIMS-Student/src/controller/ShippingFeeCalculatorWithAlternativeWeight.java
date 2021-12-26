package controller;

import java.util.Random;
import java.util.logging.Logger;

import entity.order.Order;

/**
 * @author UyenNTT
 *
 */
public class ShippingFeeCalculatorWithAlternativeWeight implements ShippingFeeCalculator{
	
	private static Logger LOGGER = utils.Utils.getLogger(ShippingFeeCalculatorWithAlternativeWeight.class.getName());
	
	@Override
	public int calculateShippingFee(Order order) {
		Random rand = new Random();
		
		int fees = (int)( ( (rand.nextFloat()*10)/100 ) * order.getAmount() );
        int length = (int)( ( (rand.nextFloat()*10)/100 ) * order.getAmount() );
        int width = (int)( ( (rand.nextFloat()*10)/100 ) * order.getAmount() );
        int height = (int)( ( (rand.nextFloat()*10)/100 ) * order.getAmount() );
     
        int altWeight = (length*width*height)/6000;
        fees += altWeight;
        
        LOGGER.info("Order Amount: " + order.getAmount() + " -- Shipping Fees: " + fees);
		
		return fees;
	}

}
