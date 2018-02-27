package basicclass;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.KeyPairGenerator;
import java.security.KeyPair;
import java.security.SignatureException;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class DigitalSignature {
	private final static Base64.Decoder decoder = Base64.getDecoder();
	private final static Base64.Encoder encoder = Base64.getEncoder();
	private static KeyPair key = null;
	private static byte[] plainText = new byte[] {};
	private static byte[] signature = new byte[] {};
	private static String encodedText = null;
	private static String decodedText = null;
	private static Signature sig = null;

	public static KeyPair KeyPairgenerate() throws NoSuchAlgorithmException {
		KeyPairGenerator keyGen;
		keyGen = KeyPairGenerator.getInstance("RSA");
		keyGen.initialize(1024);
		key = keyGen.generateKeyPair();
		return key;
	}

	public static String sign(PrivateKey pr, String message)
			throws NoSuchAlgorithmException, InvalidKeyException,
			SignatureException, UnsupportedEncodingException {
		plainText = message.getBytes("utf-8");
		sig = Signature.getInstance("SHA1WithRSA");
		sig.initSign(pr);
		sig.update(plainText);
		signature = sig.sign();
		encodedText = encoder.encodeToString(signature);
		return encodedText;
	}

	public static boolean verify(PublicKey pu, String message, String sign)
			throws InvalidKeyException, SignatureException,
			UnsupportedEncodingException, NoSuchAlgorithmException {
		if (message.equals("") || sign.equals(""))
			return false;
		else {
			byte[] temp = message.getBytes("utf-8");
			sig = Signature.getInstance("SHA1WithRSA");
			sig.initVerify(pu);
			sig.update(temp);
			if (sig.verify(decoder.decode(sign))) {
				return true;
			} else
				return false;
		}
	}

	// 将String类型转换为PrivateKey类型
	public static PrivateKey getPrivateKeyFromString(String key)
			throws Exception {
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(
				decoder.decode(key));
		PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
		return privateKey;
	}

	// 将String类型转换为PublicKey类型
	public static PublicKey getPublicKeyFromString(String key) throws Exception {
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(
				decoder.decode(key));
		PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
		return publicKey;
	}

	// 将Key类型转换为String类型
	public static String getKeyAsString(Key key) {
		byte[] keyBytes = key.getEncoded();
		return encoder.encodeToString(keyBytes);
	}
}
