package controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * This JUnitTest is used for validate name tests
 * @author Uyen_NTT184014
 *
 */
class ValidateNameTest {
	
	private PlaceOrderController placeOrderController;

	@BeforeEach
	void setUp() throws Exception {
		placeOrderController = new PlaceOrderController();
	}
	
	@ParameterizedTest
	@CsvSource({
		"Nguyen Van A, true",
		"123 An, false",
		"@U, false"
	})

	@Test
	void test(String name, boolean expected) {
		boolean isValid = placeOrderController.validateName(name);

        assertEquals(expected, isValid);
	}

}
