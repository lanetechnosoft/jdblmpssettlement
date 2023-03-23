package la.com.jdbbank.api.settlement.utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

@Service
public class SignatureUtil {
	private boolean enableHashCheck = true;
	private static final String PRIVATE_KEY = "jdbprivate.key";
	//private static final String PUBLIC_KEY = "jdbpublic.pem";
	@Autowired
	HttpServletRequest request;
	@Autowired
	ObjectMapper jsonMapper;
	private final Logger log = LoggerFactory.getLogger(SignatureUtil.class);

	public boolean verify(String jsonReq) {
		boolean check = false;
		try {
			String SourceHash = this.request.getHeader("X-Destination-Response-Signature");
			String key = new String(Files.readAllBytes((new File("jdbpublic.pem")).toPath()), Charset.defaultCharset());

			String pubKey = key.replace("-----BEGIN PUBLIC KEY-----", "").replaceAll(System.lineSeparator(), "")
					.replace("-----END PUBLIC KEY-----", "").trim();

			X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(DatatypeConverter.parseBase64Binary(pubKey));
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			Signature sig = Signature.getInstance("SHA256withRSA");
			sig.initVerify(keyFactory.generatePublic(pubKeySpec));
			sig.update(jsonReq.getBytes("UTF-8"));
			check = sig.verify(DatatypeConverter.parseHexBinary(SourceHash));
			System.out.println(jsonReq);
			
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}

		return check;
	}

	public String signedhash(String jsonReq) {
		String result = null;
		try {
			File file = ResourceUtils.getFile("classpath:jdbprivate.key");
			String key = new String(Files.readAllBytes(file.toPath()), Charset.defaultCharset());

			String privateKey = key.replace("-----BEGIN PRIVATE KEY-----", "").replaceAll(System.lineSeparator(), "")
					.replace("-----END PRIVATE KEY-----", "").trim();
			byte[] encoded = DatatypeConverter.parseBase64Binary(privateKey);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			Signature sig = Signature.getInstance("SHA256withRSA");
			sig.initSign(keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encoded)));
			sig.update(jsonReq.getBytes());
			byte[] signatureBytes = sig.sign();
			result = DatatypeConverter.printHexBinary(signatureBytes);
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		//System.out.println(result);
		return result;
	}

	public boolean isValidHash(HttpServletRequest request, String jsonReq) {
		if (!this.enableHashCheck) {
			return true;
		}
		String SourceHash = request.getHeader("X-Destination-Response-Signature");

		if ("GET".equalsIgnoreCase(request.getMethod())) {
			return true;
		}

		if ("POST".equalsIgnoreCase(request.getMethod()) && SourceHash != null) {
			String newHash = signedhash(jsonReq);
			if (!verify(jsonReq)) {
				this.log.info("SignedHash=" + SourceHash + " , newHash=" + newHash);

				return false;
			}
			this.log.info("SignedHash=" + SourceHash + " , newHash=" + newHash);
			return true;
		}

		return false;
	}

	public <T> T checkHashAndMapping(String body, Class<T> valueType) {
		if (!isValidHash(this.request, body)) {
			return null;
		}

		try {
			return (T) this.jsonMapper.readValue(body, valueType);
		} catch (RuntimeException | com.fasterxml.jackson.core.JsonProcessingException e) {
			return null;
		}
	}
}
