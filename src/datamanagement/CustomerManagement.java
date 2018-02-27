package datamanagement;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.sql.*;

import basicclass.Commodity;
import basicclass.Customer;
import basicclass.DigitalSignature;

public class CustomerManagement {
	ArrayList<Customer> CustomerList = new ArrayList<Customer>();
	Customer currentCustomer = new Customer();
	PreparedStatement ps = null;
	Connection ct = null;
	ResultSet rs = null;

	public CustomerManagement() throws ClassNotFoundException, SQLException,
			Exception {
		if (Link()) {
			System.out.println("数据库连接成功");
			if (isTableExist()) {
				System.out.println("表已存在");
				CustomerInit();
			} else {
				ps = ct.prepareStatement("create table Customer(name varchar(20),password varchar(20),PublicKey varchar(1024),PrivateKey varchar(1024))");
				if (ps.execute())
					System.out.println("创建表失败");
			}
		} else {
			System.out.println("数据库连接失败");
		}
	}

	private boolean Link() throws ClassNotFoundException, SQLException {
		// 1、加载驱动
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		// 2、得到连接
		ct = DriverManager
				.getConnection("jdbc:sqlserver://localhost:1433;databaseName=DataManagement;integratedSecurity=true;");
		if (!ct.isClosed()) {
			return true;
		} else
			return false;
	}

	private boolean isTableExist() throws SQLException {
		DatabaseMetaData dbm = ct.getMetaData();
		rs = dbm.getTables(null, null, "Customer", null);
		if (rs.next())
			return true;
		else
			return false;
	}

	private void CustomerInit() throws Exception {
		String name = null;
		String password = null;
		PublicKey pu = null;
		PrivateKey pr = null;
		PreparedStatement Commodityps = null;
		ResultSet Commodityrs = null;
		String Commodityname = null;
		int quantity = 0;
		String belongto = null;
		String signfrom = null;
		String digitalsignature = null;
		ArrayList<Commodity> CommodityList = new ArrayList<Commodity>();

		ps = ct.prepareStatement("select * from Customer");
		if (ps.executeQuery() != null) {
			rs = ps.executeQuery();
			while (rs.next()) {
				name = rs.getString(1);
				password = rs.getString(2);
				pu = DigitalSignature.getPublicKeyFromString(rs.getString(3));
				pr = DigitalSignature.getPrivateKeyFromString(rs.getString(4));

				Commodityps = ct
						.prepareStatement("select * from Commodity where belongto=?");
				Commodityps.setString(1, name);
				if (Commodityps.executeQuery() != null) {
					Commodityrs = Commodityps.executeQuery();
					while (Commodityrs.next()) {
						Commodityname = Commodityrs.getString(1);
						quantity = Commodityrs.getInt(2);
						belongto = Commodityrs.getString(3);
						signfrom = Commodityrs.getString(4);
						digitalsignature = Commodityrs.getString(5);
						CommodityList.add(new Commodity(name, quantity,
								belongto, signfrom, digitalsignature));
					}
				}
				CustomerList.add(new Customer(name, password, pu, pr,
						CommodityList));
			}
			System.out.println("Customer 初始化成功");
		} else
			System.out.println("Customer 初始化失败");
	}

	public boolean LogIn(String name, String password) {
		int temp = 0;
		if ((temp = CustomerList.indexOf(new Customer(name, password))) != -1) {
			if (CustomerList.get(temp).isPasswordRight(password)) {
				currentCustomer = CustomerList.get(temp);
				return true;
			} else
				return false;
		} else
			return false;
	}

	public void LogOut() {
		currentCustomer = new Customer();
	}

	public Connection getConnection() {
		return ct;
	}

	public Customer getCurrentCustomer() {
		return currentCustomer;
	}

	public int addCustomer(String name, String password)
			throws NoSuchAlgorithmException, SQLException {
		KeyPair key = DigitalSignature.KeyPairgenerate();
		PublicKey pu = key.getPublic();
		PrivateKey pr = key.getPrivate();
		ArrayList<Commodity> CommodityList = new ArrayList<Commodity>();

		ps = ct.prepareStatement("select * from Customer where name=?");
		ps.setString(1, name);
		if (ps.executeQuery() != null) {
			rs = ps.executeQuery();
			if (!rs.next()) {
				Customer c = new Customer(name, password, pu, pr, CommodityList);
				CustomerList.add(c);
				ps = ct.prepareStatement("insert into Customer values(?,?,?,?)");
				ps.setString(1, name);
				ps.setString(2, password);
				ps.setString(3, DigitalSignature.getKeyAsString(pu));
				ps.setString(4, DigitalSignature.getKeyAsString(pr));
				if (ps.executeUpdate() == 1)
					return 0;
				else
					return -1;
			} else
				return 1;
		} else
			return -1;
	}

	public void CustomerClose() {
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
