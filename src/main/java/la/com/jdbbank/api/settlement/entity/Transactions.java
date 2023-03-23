package la.com.jdbbank.api.settlement.entity;

import java.math.BigDecimal;

public class Transactions {
	private String accountname;
	private String accountccy;
	private Long id;
	private String frommember;
	private String fromuser;
	private String fromaccount;
	private String totype;
	private String toaccount;
	private String tomember;
	private String reference;
	private String time;
	private BigDecimal amount;
	private BigDecimal fee;
	private String ccy;
	private String purpose;
	
	
	public String getAccountname() {
		return accountname;
	}

	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}

	public String getAccountccy() {
		return accountccy;
	}

	public void setAccountccy(String accountccy) {
		this.accountccy = accountccy;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFrommember() {
		return this.frommember;
	}

	public void setFrommember(String frommember) {
		this.frommember = frommember;
	}

	public String getFromuser() {
		return this.fromuser;
	}

	public void setFromuser(String fromuser) {
		this.fromuser = fromuser;
	}

	public String getFromaccount() {
		return this.fromaccount;
	}

	public void setFromaccount(String fromaccount) {
		this.fromaccount = fromaccount;
	}

	public String getTotype() {
		return this.totype;
	}

	public void setTotype(String totype) {
		this.totype = totype;
	}

	public String getToaccount() {
		return this.toaccount;
	}

	public void setToaccount(String toaccount) {
		this.toaccount = toaccount;
	}

	public String getTomember() {
		return this.tomember;
	}

	public void setTomember(String tomember) {
		this.tomember = tomember;
	}

	public String getReference() {
		return this.reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getTime() {
		return this.time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCcy() {
		return this.ccy;
	}

	public void setCcy(String ccy) {
		this.ccy = ccy;
	}

	public String getPurpose() {
		return this.purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
}
