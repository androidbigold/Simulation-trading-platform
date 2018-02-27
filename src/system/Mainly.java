package system;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SignatureException;

import windows.Login;
import basicclass.DigitalSignature;
import datamanagement.CommodityManagement;
import datamanagement.CustomerManagement;
import datamanagement.FactoryManagement;
import datamanagement.SellerManagement;

public class Mainly {
	public static void main(String[] args) throws Exception {
		DigitalSignature ds=new DigitalSignature();
		CommodityManagement cdm=new CommodityManagement();
		CustomerManagement ctm = new CustomerManagement();
		SellerManagement sm=new SellerManagement();
		FactoryManagement fm=new FactoryManagement();
		new Login(cdm,ctm,sm,fm);
	}
}
