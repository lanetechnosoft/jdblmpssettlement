package la.com.jdbbank.api.settlement.entity;

import java.util.List;
import la.com.jdbbank.api.settlement.entity.Summaries;
import la.com.jdbbank.api.settlement.entity.Transactions;

public class SettlementResponse {
	private String date;
	private List<Summaries> summaries;
	private List<Transactions> transactions;

	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<Summaries> getSummaries() {
		return this.summaries;
	}

	public void setSummaries(List<Summaries> summaries) {
		this.summaries = summaries;
	}

	public List<Transactions> getTransactions() {
		return this.transactions;
	}

	public void setTransactions(List<Transactions> transactions) {
		this.transactions = transactions;
	}
}
