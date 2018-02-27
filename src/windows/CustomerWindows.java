package windows;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import basicclass.Commodity;
import datamanagement.CommodityManagement;
import datamanagement.CustomerManagement;
import datamanagement.FactoryManagement;
import datamanagement.SellerManagement;

@SuppressWarnings("serial")
public class CustomerWindows extends JFrame implements ActionListener {
	String name = null;
	int quantity = 0;
	String belongto = null;
	String signfrom = null;
	String digitalsignature = null;

	CommodityManagement cdm = null;
	CustomerManagement ctm = null;
	SellerManagement sm = null;
	FactoryManagement fm = null;

	Connection ct = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	ArrayList<Commodity> CommodityList = new ArrayList<Commodity>();
	ArrayList<Commodity> BuyedCommodityList = new ArrayList<Commodity>();

	// 定义组件
	JButton jb1, jb2, jb3, jb4 = null;
	JPanel jp1, jp2, jp3 = null;
	ArrayList<JButton> CommodityButton = new ArrayList<JButton>();
	ArrayList<JButton> BuyedCommodityButton = new ArrayList<JButton>();

	public CustomerWindows(CommodityManagement cdm, CustomerManagement ctm,
			SellerManagement sm, FactoryManagement fm) {
		this.cdm = cdm;
		this.ctm = ctm;
		this.fm = fm;
		this.sm = sm;

		// 创建组件
		jb1 = new JButton("所有商品");
		jb2 = new JButton("已购商品");
		jb3 = new JButton("修改密码");
		jb4 = new JButton("退出登录");

		// 设置监听
		jb1.addActionListener(this);
		jb2.addActionListener(this);
		jb3.addActionListener(this);
		jb4.addActionListener(this);

		jp1 = new JPanel();
		jp2 = new JPanel();
		jp3 = new JPanel();

		jp1.add(jb1);
		jp1.add(jb2);
		jp1.add(jb3);
		jp1.add(jb4);

		this.add(jp1, BorderLayout.WEST);
		this.add(jp3, BorderLayout.CENTER);
		this.add(jp2, BorderLayout.CENTER);

		jp1.setLayout(new GridLayout(4, 1));
		jp2.setLayout(new FlowLayout(FlowLayout.LEFT));
		jp3.setLayout(new FlowLayout(FlowLayout.LEFT));

		// 给窗口设置标题
		this.setTitle("customer");
		// 设置窗体大小
		this.setSize(1000, 800);
		// 设置窗体初始位置
		this.setLocation(450, 100);
		// 设置当关闭窗口时，保证JVM也退出
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 显示窗体
		this.setVisible(true);
		this.setResizable(true);
		jp3.setVisible(false);
		jp2.setVisible(false);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "所有商品") {
			CommodityList.clear();
			CommodityButton.clear();
			BuyedCommodityList.clear();
			BuyedCommodityButton.clear();
			CommodityListener.clear();

			jp2.removeAll();
			ct = cdm.getConnection();
			try {
				ps = ct.prepareStatement("select * from Commodity where belongto in (select name from Seller)");
				if ((rs = ps.executeQuery()) != null) {
					while (rs.next()) {
						name = rs.getString(1);
						quantity = rs.getInt(2);
						belongto = rs.getString(3);
						signfrom = rs.getString(4);
						digitalsignature = rs.getString(5);
						CommodityList.add(new Commodity(name, quantity,
								belongto, signfrom, digitalsignature));
					}

					for (int i = 0; i < CommodityList.size(); i++) {
						CommodityButton.add(new JButton("<html>" + "商品名: "
								+ CommodityList.get(i).getName() + "<br>"
								+ "卖家: " + CommodityList.get(i).getBelongto()
								+ "</html>"));

					}

					for (int j = 0; j < CommodityButton.size(); j++) {
						CommodityButton.get(j).setPreferredSize(new Dimension(200,80));
						CommodityButton.get(j).setActionCommand("商品" + j);
						CommodityButton.get(j).addActionListener(
								new CommodityListener(cdm, ctm, sm, fm,
										CommodityList.get(j).getName(),
										CommodityList.get(j).getQuantity(),
										CommodityList.get(j).getBelongto(),
										CommodityList.get(j).getSignfrom(),
										CommodityList.get(j)
												.getDigitalsignature()));
						jp2.add(CommodityButton.get(j));
					}

				} else
					JOptionPane.showMessageDialog(null, "读取商品列表失败！", "提示消息",
							JOptionPane.WARNING_MESSAGE);
			} catch (SQLException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}

			jp2.repaint();
			jp3.setVisible(false);
			this.add(jp2, BorderLayout.CENTER);
			jp2.setVisible(true);
			this.repaint();
		}
		if (e.getActionCommand() == "已购商品") {
			CommodityList.clear();
			CommodityButton.clear();
			BuyedCommodityList.clear();
			BuyedCommodityButton.clear();
			BuyedCommodityListener.clear();

			jp3.removeAll();
			ct = cdm.getConnection();
			try {
				ps = ct.prepareStatement("select * from Commodity where belongto =?");
				ps.setString(1, ctm.getCurrentCustomer().getName());
				if ((rs = ps.executeQuery()) != null) {
					while (rs.next()) {
						name = rs.getString(1);
						quantity = rs.getInt(2);
						belongto = rs.getString(3);
						signfrom = rs.getString(4);
						digitalsignature = rs.getString(5);
						BuyedCommodityList.add(new Commodity(name, quantity,
								belongto, signfrom, digitalsignature));
					}

					for (int i = 0; i < BuyedCommodityList.size(); i++) {
						BuyedCommodityButton.add(new JButton("商品名: "
								+ BuyedCommodityList.get(i).getName()));
					}
					for (int j = 0; j < BuyedCommodityButton.size(); j++) {
						BuyedCommodityButton.get(j).setPreferredSize(new Dimension(200,80));
						BuyedCommodityButton.get(j).setActionCommand("商品" + j);
						BuyedCommodityButton
								.get(j)
								.addActionListener(
										new BuyedCommodityListener(cdm, ctm,
												sm, fm, BuyedCommodityList.get(
														j).getName(),
												BuyedCommodityList.get(j)
														.getQuantity(),
												BuyedCommodityList.get(j)
														.getBelongto(),
												BuyedCommodityList.get(j)
														.getSignfrom(),
												BuyedCommodityList.get(j)
														.getDigitalsignature()));
						jp3.add(BuyedCommodityButton.get(j));
					}
				} else
					JOptionPane.showMessageDialog(null, "读取商品列表失败！", "提示消息",
							JOptionPane.WARNING_MESSAGE);
			} catch (SQLException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
			// 此处连接数据库调出已购的产品
			jp3.repaint();
			jp2.setVisible(false);
			this.add(jp3, BorderLayout.CENTER);
			jp3.setVisible(true);
			this.repaint();
		}
		if (e.getActionCommand() == "修改密码") {
			// 连接数据库改密码
			new Gaimi(this, null, ctm, null, null);
		}
		if (e.getActionCommand() == "退出登录") {
			dispose();
			ctm.LogOut();
			new Login(cdm, ctm, sm, fm);
		}
	}
}

class CommodityListener implements ActionListener {
	static int number = 0;

	CommodityManagement cdm = null;
	CustomerManagement ctm = null;
	SellerManagement sm = null;
	FactoryManagement fm = null;
	
	static ArrayList<String> name = new ArrayList<String>();
	static ArrayList<Integer> quantity = new ArrayList<Integer>();
	static ArrayList<String> belongto = new ArrayList<String>();
	static ArrayList<String> signfrom = new ArrayList<String>();
	static ArrayList<String> digitalsignature = new ArrayList<String>();

	public CommodityListener(CommodityManagement cdm, CustomerManagement ctm,
			SellerManagement sm, FactoryManagement fm, String name,
			int quantity, String belongto, String signfrom,
			String digitalsignature) {
		this.cdm = cdm;
		this.ctm = ctm;
		this.fm = fm;
		this.sm = sm;
		CommodityListener.name.add(name);
		CommodityListener.quantity.add(quantity);
		CommodityListener.belongto.add(belongto);
		CommodityListener.signfrom.add(signfrom);
		CommodityListener.digitalsignature.add(digitalsignature);
		number++;
	}

	static void clear() {
		number = 0;
		CommodityListener.name.clear();
		CommodityListener.quantity.clear();
		CommodityListener.belongto.clear();
		CommodityListener.signfrom.clear();
		CommodityListener.digitalsignature.clear();
	}

	public void actionPerformed(ActionEvent e) {
		for (int i = 0; i < number; i++) {
			if (e.getActionCommand().equals("商品" + i))
				new Goods(i,cdm, ctm, sm, fm, name.get(i), quantity.get(i),
						belongto.get(i), signfrom.get(i),
						digitalsignature.get(i));
		}
	}
}

class BuyedCommodityListener implements ActionListener {
	static int number = 0;

	CommodityManagement cdm = null;
	CustomerManagement ctm = null;
	SellerManagement sm = null;
	FactoryManagement fm = null;

	static ArrayList<String> name = new ArrayList<String>();
	static ArrayList<Integer> quantity = new ArrayList<Integer>();
	static ArrayList<String> belongto = new ArrayList<String>();
	static ArrayList<String> signfrom = new ArrayList<String>();
	static ArrayList<String> digitalsignature = new ArrayList<String>();

	public BuyedCommodityListener(CommodityManagement cdm,
			CustomerManagement ctm, SellerManagement sm, FactoryManagement fm,
			String name, int quantity, String belongto, String signfrom,
			String digitalsignature) {
		this.cdm = cdm;
		this.ctm = ctm;
		this.fm = fm;
		this.sm = sm;
		BuyedCommodityListener.name.add(name);
		BuyedCommodityListener.quantity.add(quantity);
		BuyedCommodityListener.belongto.add(belongto);
		BuyedCommodityListener.signfrom.add(signfrom);
		BuyedCommodityListener.digitalsignature.add(digitalsignature);
		number++;
	}

	static void clear() {
		number = 0;
		BuyedCommodityListener.name.clear();
		BuyedCommodityListener.quantity.clear();
		BuyedCommodityListener.belongto.clear();
		BuyedCommodityListener.signfrom.clear();
		BuyedCommodityListener.digitalsignature.clear();
	}

	public void actionPerformed(ActionEvent e) {
		for (int i = 0; i < number; i++) {
			if (e.getActionCommand().equals("商品" + i))
				new Yigou(cdm, ctm, sm, fm, name.get(i), quantity.get(i),
						belongto.get(i), signfrom.get(i),
						digitalsignature.get(i));
		}
	}
}