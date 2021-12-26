package entity.payment;

public class DomesticDebitCard extends Card{
	private String issuingBank;
	private String cardNumber;
	private String validFromDate;
	private String cardholderName;
	
	public DomesticDebitCard(String issuingBank, String cardNumber, String validFromDate, String cardholderName) {
		super();
		this.issuingBank = issuingBank;
		this.cardNumber = cardNumber;
		this.validFromDate = validFromDate;
		this.cardholderName = cardholderName;
	}
	
	
}
