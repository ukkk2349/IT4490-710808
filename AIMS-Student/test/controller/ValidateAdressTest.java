package controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ValidateAdressTest {
	
	private PlaceOrderController placeOrderController;

	@BeforeEach
	void setUp() throws Exception {
		placeOrderController = new PlaceOrderController();
	}
	
	@ParameterizedTest
	@CsvSource({
		"Hanoi, true",
		"Hai Phong - Vietnam, true",
		"@vn, false"
	})

	@Test
	void test(String address, boolean expected) {
		boolean isValid = placeOrderController.validateAddress(address);
		
		assertEquals(expected, isValid);
	}

}
