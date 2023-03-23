package la.com.jdbbank.api.settlement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import la.com.jdbbank.api.settlement.entity.TransactionResp;
import la.com.jdbbank.api.settlement.entity.TransferResponse;
import la.com.jdbbank.api.settlement.services.SettlementService;
import la.com.jdbbank.api.settlement.utility.SignatureUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = { "*" }, maxAge = 3600L)
@RestController
public class SettlementController {
	@Autowired
	SettlementService settleService;
	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	private SignatureUtil signUtil;
	@Autowired
	private Gson gson;

	@PostMapping({ "/getSettlementReport" })
	public ResponseEntity<?> getSettlement(@RequestBody String body) {
		try {
			ResponseEntity<?> resp = this.settleService.getSetlement(body);
			if (resp.getStatusCode() != HttpStatus.OK) {
				return ResponseEntity.ok(resp.getBody());
			}
			return ResponseEntity.ok(resp.getBody());
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping({ "/jdbsignature" })
	public ResponseEntity<?> getSignature(@RequestBody String body) {
		try {
			System.out.println(body);
			System.out.println(this.signUtil.signedhash(body));
			/*ResponseEntity<?> resp = this.settleService.getSetlement(body);
			if (resp.getStatusCode() != HttpStatus.OK) {
				return ResponseEntity.ok(resp.getBody());
			}
			return ResponseEntity.ok(resp.getBody());*/
			/*return ResponseEntity.ok()
					.header("X-Destination-Response-Signature", this.signUtil.signedhash(body))
					.body(body);*/
			return ResponseEntity.ok()
					.header("X-Destination-Response-Signature", this.signUtil.signedhash(this.gson.toJson(body)))
					.body(this.gson.toJson(body));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping({ "/getTransaction" })
	public ResponseEntity<?> getTransaction(@RequestBody String body) {
		try {
			ResponseEntity<?> resp = this.settleService.getTransaction(body);
			System.out.println(resp.getBody().toString());
			TransactionResp transactionResp = objectMapper.readValue(resp.getBody().toString(), TransactionResp.class);
			//System.out.println(objectMapper.writeValueAsString(transactionResp));
			/**
			if (transactionResp.getTransferresponse()==null){
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp.getBody().toString());
			}*/
			if (resp.getStatusCode() != HttpStatus.OK) {
				return ResponseEntity.ok(resp.getBody());
			}
			return ResponseEntity.ok(resp.getBody());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
