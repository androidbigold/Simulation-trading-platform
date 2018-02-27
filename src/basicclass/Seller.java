package basicclass;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.util.ArrayList;

public class Seller {
	private String name = null;
	private String password = null;
	private PublicKey pu = null;
	private PrivateKey pr = null;
	private ArrayList<Commodity> CommodityList = new ArrayList<Commodity>();

	public Seller() {

	}

	public Seller(String name, String password) {
		this.name = name;
		this.password = password;
	}

	public Seller(String name, String password, PublicKey pu, PrivateKey pr,
			ArrayList<Commodity> CommodityList) {
		this.name = name;
		this.password = password;
		this.pu = pu;
		this.pr = pr;
		this.CommodityList = CommodityList;
	}

	public String getName() {
		return name;
	}

	public boolean isPasswordRight(String password) {
		return this.password.equals(password);
	}

	public void setPassword(String password) {
		this.password=password;
	}
	
	public PublicKey getPublicKey() {
		return pu;
	}

	public boolean equals(Object otherobject) {
		if (this == otherobject)
			return true;
		if (otherobject == null)
			return false;
		if (getClass() != otherobject.getClass())
			return false;
		Seller other = (Seller) otherobject;
		return name.equals(other.getName());
	}

}
