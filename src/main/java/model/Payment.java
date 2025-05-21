package model;

public class Payment
{
    private int paymentId;
    private int cardNumber;
    private int cardExpiryDate;
    private int cardCVV;
    private String cardHolderName;
    private String userEmail;
    private int userId;

    public int getPaymentId() {
        return paymentId;
    }
    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }
    public int getCardNumber() {
        return cardNumber;
    }
    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }
    public int getCardExpiryDate() {
        return cardExpiryDate;
    }
    public void setCardExpiryDate(int cardExpiryDate) {
        this.cardExpiryDate = cardExpiryDate;
    }
    public int getCardCVV() {
        return cardCVV;
    }
    public void setCardCVV(int cardCVV) {
        this.cardCVV = cardCVV;
    }
    public String getCardHolderName() {
        return cardHolderName;
    }
    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }
    public String getUserEmail() {
        return userEmail;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
}

