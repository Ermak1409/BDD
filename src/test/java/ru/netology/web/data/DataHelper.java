package ru.netology.web.data;

import lombok.Value;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        String login;
        String password;
    }

    public static AuthInfo getUserAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    @Value
    public static class VerificationCode {
        String code;
    }

    public static VerificationCode getVerificationCodeFor() {
        return new VerificationCode("12345");
    }

    @Value
    public static class CardId {
        String id;
    }

    public static CardId getFirstCardId() {
        return new CardId("92df3f1c-a033-48e6-8390-206f6b1f56c0");
    }

    public static CardId getSecondCardId() {
        return new CardId("0f3f5c2a-249e-4c3d-8287-09f7a039391d");
    }

    @Value
    public static class TransferInfo {
        int amount;
        String numberCard;
    }

    public static TransferInfo getFirstCardTransferInfo() {
        return new TransferInfo(5000, "5559 0000 0000 0002");
    }

    public static TransferInfo getSecondCardTransferInfo() {
        return new TransferInfo(11000, "5559 0000 0000 0001");
    }


    public static TransferInfo setFirstCardTransferInfo(int amountTransfer) {
        return new TransferInfo(amountTransfer, "5559 0000 0000 0002");
    }

    public static TransferInfo setSecondCardTransferInfo(int amountTransfer) {
        return new TransferInfo(amountTransfer, "5559 0000 0000 0001");
    }
}
