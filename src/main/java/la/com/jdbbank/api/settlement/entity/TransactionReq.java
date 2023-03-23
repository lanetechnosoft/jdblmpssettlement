package la.com.jdbbank.api.settlement.entity;

public class TransactionReq {
    private String frommember;
    private String reference;

    public String getFrommember() {
        return frommember;
    }

    public void setFrommember(String frommember) {
        this.frommember = frommember;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}
