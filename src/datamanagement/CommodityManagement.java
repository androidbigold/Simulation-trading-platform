package datamanagement;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import basicclass.DigitalSignature;
import basicclass.Commodity;

public class CommodityManagement {
	ArrayList<Commodity> CommodityList = new ArrayList<Commodity>();
	Commodity currentCommodity = new Commodity();
	Connection ct = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	public CommodityManagement() throws ClassNotFoundException, SQLException,
			Exception {
		if (Link()) {
			System.out.println("���ݿ����ӳɹ�");
			if (isTableExist()) {
				System.out.println("���Ѵ���");
				CommodityInit();
			} else {
				ps = ct.prepareStatement("create table Commodity(name varchar(20),quantity int,belongto varchar(20),signfrom varchar(20),digitalsignature varchar(1024))");
				if (ps.execute())
					System.out.println("������ʧ��");
			}
		} else {
			System.out.println("���ݿ�����ʧ��");
		}
	}

	private boolean Link() throws ClassNotFoundException, SQLException {
		// 1����������
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		// 2���õ�����
		ct = DriverManager
				.getConnection("jdbc:sqlserver://localhost:1433;databaseName=DataManagement;integratedSecurity=true;");
		if (!ct.isClosed()) {
			return true;
		} else
			return false;
	}

	private boolean isTableExist() throws SQLException {
		DatabaseMetaData dbm = ct.getMetaData();
		rs = dbm.getTables(null, null, "Commodity", null);
		if (rs.next())
			return true;
		else
			return false;
	}

	private void CommodityInit() throws Exception {
		String name = null;
		int quantity = 0;
		String belongto = null;
		String signfrom = null;
		String digitalsignature = null;

		ps = ct.prepareStatement("select * from Commodity");
		if (ps.executeQuery() != null) {
			rs = ps.executeQuery();
			while (rs.next()) {
				name = rs.getString(1);
				quantity = rs.getInt(2);
				belongto = rs.getString(3);
				signfrom = rs.getString(4);
				digitalsignature = rs.getString(5);
				CommodityList.add(new Commodity(name, quantity, belongto,
						signfrom, digitalsignature));
			}
			System.out.println("Commodity ��ʼ���ɹ�");
		} else
			System.out.println("Commodity ��ʼ��ʧ��");
	}

	public Connection getConnection() {
		return ct;
	}

	public ArrayList<Commodity> getCommodityList() {
		return CommodityList;
	}

	public void addCommodity(String name, int quantity, String belongto,
			String signfrom, String digitalsignature)
			throws NoSuchAlgorithmException, SQLException {
		ps = ct.prepareStatement("select * from Commodity where name=? and belongto=? and signfrom=?");
		ps.setString(1, name);
		ps.setString(2, belongto);
		ps.setString(3, signfrom);
		if (ps.executeQuery() != null) {
			rs = ps.executeQuery();
			if (!rs.next()) {
				Commodity c = new Commodity(name, quantity, belongto, signfrom,
						digitalsignature);
				ps = ct.prepareStatement("insert into Commodity values(?,?,?,?,?)");
				ps.setString(1, name);
				ps.setInt(2, quantity);
				ps.setString(3, belongto);
				ps.setString(4, signfrom);
				ps.setString(5, digitalsignature);
				if (ps.executeUpdate() == 1)
					System.out.println("��ӳɹ�");
				else
					System.out.println("���ʧ��");
			} else
				System.out.println("����Ʒ�Ѵ���");
		} else
			System.out.println("���ʧ��");
	}

	public void CommodityClose() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (ct != null) {
				ct.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
