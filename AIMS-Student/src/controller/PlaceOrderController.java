package controller;

import static org.hamcrest.CoreMatchers.allOf;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import entity.cart.Cart;
import entity.cart.CartMedia;
import common.exception.InvalidDeliveryInfoException;
import entity.invoice.Invoice;
import entity.order.Order;
import entity.order.OrderMedia;
import views.screen.popup.PopupScreen;
/**
 * @author UyenNTT_20184014
 */
public class PlaceOrderController extends BaseController{
	
    /**
     * Just for logging purpose
     */
    private static Logger LOGGER = utils.Utils.getLogger(PlaceOrderController.class.getName());

    
    /**
     * This method checks the avalibility of product when user click PlaceOrder button
     * @throws SQLException
     */
    public void placeOrder() throws SQLException{
        Cart.getCart().checkAvailabilityOfProduct();
    }

    /**
     * This method creates the new Order based on the Cart
     * @return Order
     * @throws SQLException
     */
    public Order createOrder() throws SQLException{
        Order order = new Order();
        for (Object object : Cart.getCart().getListMedia()) {
            CartMedia cartMedia = (CartMedia) object;
            OrderMedia orderMedia = new OrderMedia(cartMedia.getMedia(), 
                                                   cartMedia.getQuantity(), 
                                                   cartMedia.getPrice());    
            order.getlstOrderMedia().add(orderMedia);
        }
        return order;
    }

    /**
     * This method creates the new Invoice based on order
     * @param order
     * @return Invoice
     */
    public Invoice createInvoice(Order order) {
        return new Invoice(order);
    }

    /**
     * This method takes responsibility for processing the shipping info from user
     * @param info
     * @throws InterruptedException
     * @throws IOException
     */
    public void processDeliveryInfo(HashMap info) throws InterruptedException, IOException{
        LOGGER.info("Process Delivery Info");
        LOGGER.info(info.toString());
        validateDeliveryInfo(info);
    }
    
    /**
   * The method validates the info
   * @param info
   * @throws InterruptedException
   * @throws IOException
   */
    public void validateDeliveryInfo(HashMap<String, String> info) throws InterruptedException, IOException{
    	
    }
    
    /**
     * @author UyenNTT
     */
    public boolean validatePhoneNumber(String phoneNumber) {
    	if(phoneNumber.length() != 10) return false;
    	
    	try {
			Integer.parseInt(phoneNumber);
		} catch (NumberFormatException e) {
			return false;
		}
    	return phoneNumber.equals("0123456789");
    }
    
    /**
     * @author UyenNTT
     */
    public boolean validateName(String name) {
    	if(name == null) return false;
    	return Pattern.matches("[a-zA-Z\\s]*{1,100}", name);
    }
    
    /**
     * @author UyenNTT
     */
    public boolean validateAddress(String address) {
    	if (address == null) return false;
    	
    	return Pattern.matches("[a-zA-Z\\s.]*{1,200}", address);
    }
    

    /**
     * This method calculates the shipping fees of order
     * @author UyenNTT
     * @param order
     * @return shippingFee
     */
    public int calculateShippingFee(Order order){
        return new ShippingFeeCalculatorWithAlternativeWeight().calculateShippingFee(order); 
    }
}
