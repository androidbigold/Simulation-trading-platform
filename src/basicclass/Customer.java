package basicclass;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;

public class Customer {
	private String name = null;
	private String password = null;
	private PublicKey pu = null;
	private PrivateKey pr = null;
	private ArrayList<Commodity> CommodityList = new ArrayList<Commodity>();

	public Customer() {

	}

	public Customer(String name, String password) {
		this.name = name;
		this.password = password;
	}

	public Customer(String name, String password, PublicKey pu, PrivateKey pr,
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

	public PublicKey getPublicKey() {
		return pu;
	}

	public void setPassword(String password){
		this.password=password;
	}
	
	public boolean equals(Object otherobject) {
		if (this == otherobject)
			return true;
		if (otherobject == null)
			return false;
		if (getClass() != otherobject.getClass())
			return false;
		Customer other = (Customer) otherobject;
		return name.equals(other.getName());
	}
}
