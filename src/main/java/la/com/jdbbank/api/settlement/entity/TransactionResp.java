package la.com.jdbbank.api.settlement.entity;

public class TransactionResp {
    private TransferResponse transferresponse;

    public TransferResponse getTransferresponse() {
        return transferresponse;
    }

    public void setTransferresponse(TransferResponse transferresponse) {
        this.transferresponse = transferresponse;
    }
}
