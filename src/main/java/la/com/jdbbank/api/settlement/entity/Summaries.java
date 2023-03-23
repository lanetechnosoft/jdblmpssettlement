package la.com.jdbbank.api.settlement.entity;

import java.math.BigDecimal;

public class Summaries {
	private String member;
	private String txdate;
	private String ccy;
	private BigDecimal inbound;
	private BigDecimal inboundfee;
	private BigDecimal outbound;
	private BigDecimal outboundfee;
	
	private BigDecimal debit;
	private BigDecimal credit;
	private BigDecimal balance;
	private BigDecimal issettled;
	
	
	public String getMember() {
		return member;
	}

	public void setMember(String member) {
		this.member = member;
	}

	public String getTxdate() {
		return txdate;
	}

	public void setTxdate(String txdate) {
		this.txdate = txdate;
	}

	public BigDecimal getInbound() {
		return inbound;
	}

	public void setInbound(BigDecimal inbound) {
		this.inbound = inbound;
	}

	public BigDecimal getInboundfee() {
		return inboundfee;
	}

	public void setInboundfee(BigDecimal inboundfee) {
		this.inboundfee = inboundfee;
	}

	public BigDecimal getOutbound() {
		return outbound;
	}

	public void setOutbound(BigDecimal outbound) {
		this.outbound = outbound;
	}

	public BigDecimal getOutboundfee() {
		return outboundfee;
	}

	public void setOutboundfee(BigDecimal outboundfee) {
		this.outboundfee = outboundfee;
	}

	public String getCcy() {
		return this.ccy;
	}

	public void setCcy(String ccy) {
		this.ccy = ccy;
	}

	public BigDecimal getDebit() {
		return this.debit;
	}

	public void setDebit(BigDecimal debit) {
		this.debit = debit;
	}

	public BigDecimal getCredit() {
		return this.credit;
	}

	public void setCredit(BigDecimal credit) {
		this.credit = credit;
	}

	public BigDecimal getBalance() {
		return this.balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getIssettled() {
		return this.issettled;
	}

	public void setIssettled(BigDecimal issettled) {
		this.issettled = issettled;
	}
}