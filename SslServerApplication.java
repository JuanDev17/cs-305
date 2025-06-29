package com.snhu.sslserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.MessageDigest;

@SpringBootApplication
public class SslServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(SslServerApplication.class, args);
	}
}

@RestController
class ServerController {

	private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

	private String getHash(String input) {
		try {
			MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
			byte[] hashedBytes = sha256.digest(input.getBytes());
			return bytesToHex(hashedBytes);
		} catch (Exception e) {
			e.printStackTrace();
			return "Hashing failed";
		}
	}

	private static String bytesToHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for (int i = 0; i < bytes.length; i++) {
			int v = bytes[i] & 0xFF;
			hexChars[i * 2] = HEX_ARRAY[v >>> 4];
			hexChars[i * 2 + 1] = HEX_ARRAY[v & 0x0F];
		}
		return new String(hexChars);
	}

	@RequestMapping("/hash")
	public String myHash() {
		String data = "Hello World - Juan Aviles";
		String hash = getHash(data);
		return "<p>Data: " + data + "</p><p>SHA-256 Checksum: " + hash + "</p>";
	}
}
