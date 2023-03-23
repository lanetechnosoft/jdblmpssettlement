package la.com.jdbbank.api.settlement.entity;

public class TransferResponse {
    private Integer shouldrevert;
    private Integer id;
    private String result;
    private String time;

    public Integer getShouldrevert() {
        return shouldrevert;
    }

    public void setShouldrevert(Integer shouldrevert) {
        this.shouldrevert = shouldrevert;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
