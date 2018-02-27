package windows;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
public class MerchantWindows extends JFrame implements ActionListener {
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
	ArrayList<JButton> CommodityButton = new ArrayList<JButton>();

	// 定义组件
	JButton jb1, jb2, jb3, jb4 = null;
	JButton jc1 = null;
	JPanel jp1, jp2 = null;

	public MerchantWindows(CommodityManagement cdm, CustomerManagement ctm,
			SellerManagement sm, FactoryManagement fm) {
		this.cdm = cdm;
		this.ctm = ctm;
		this.fm = fm;
		this.sm = sm;

		// 创建组件
		jb1 = new JButton("商品列表");
		jb2 = new JButton("上架商品");
		jb3 = new JButton("修改密码");
		jb4 = new JButton("退出登录");
		// 设置监听
		jb1.addActionListener(this);
		jb2.addActionListener(this);
		jb3.addActionListener(this);
		jb4.addActionListener(this);

		jp1 = new JPanel();
		jp2 = new JPanel();

		jp1.add(jb1);
		jp1.add(jb2);
		jp1.add(jb3);
		jp1.add(jb4);

		this.add(jp1, BorderLayout.WEST);
		this.add(jp2, BorderLayout.CENTER);

		jp1.setLayout(new GridLayout(4, 1));
		jp2.setLayout(new FlowLayout(FlowLayout.LEFT));

		// 给窗口设置标题
		this.setTitle("merchant");
		// 设置窗体大小
		this.setSize(800, 600);
		// 设置窗体初始位置
		this.setLocation(450, 100);
		// 设置当关闭窗口时，保证JVM也退出
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 显示窗体
		this.setVisible(true);
		this.setResizable(true);
        jp2.setVisible(false);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "商品列表") {
			CommodityList.clear();
			CommodityButton.clear();
			SellerCommodityListener.clear();

			jp2.removeAll();
			ct = cdm.getConnection();
			try {
				ps = ct.prepareStatement("select * from Commodity where belongto =?");
				ps.setString(1, sm.getCurrentSeller().getName());
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
								+ "厂家: " + CommodityList.get(i).getSignfrom()
								+ "</html>"));
					}
					for (int j = 0; j < CommodityButton.size(); j++) {
						CommodityButton.get(j).setPreferredSize(
								new Dimension(200, 80));
						CommodityButton.get(j).setActionCommand("商品" + j);
						CommodityButton.get(j).addActionListener(
								new SellerCommodityListener(cdm, ctm, sm, fm,
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
			// 此处连接数据库调出已购的产品
			jp2.repaint();
			jp2.setVisible(true);
			this.repaint();
		}
		if (e.getActionCommand() == "上架商品") {
			new Mernewgoods(cdm, ctm, sm, fm);
		}
		if (e.getActionCommand() == "修改密码") {
			// 连接数据库改密码
			new Gaimi(this, null, null, sm, null);
		}
		if (e.getActionCommand() == "退出登录") {
			dispose();
			sm.LogOut();
			new Login(cdm, ctm, sm, fm);
		}
	}
}

class SellerCommodityListener implements ActionListener {
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

	public SellerCommodityListener(CommodityManagement cdm,
			CustomerManagement ctm, SellerManagement sm, FactoryManagement fm,
			String name, int quantity, String belongto, String signfrom,
			String digitalsignature) {
		this.cdm = cdm;
		this.ctm = ctm;
		this.fm = fm;
		this.sm = sm;
		SellerCommodityListener.name.add(name);
		SellerCommodityListener.quantity.add(quantity);
		SellerCommodityListener.belongto.add(belongto);
		SellerCommodityListener.signfrom.add(signfrom);
		SellerCommodityListener.digitalsignature.add(digitalsignature);
		number++;
	}

	static void clear() {
		number = 0;
		SellerCommodityListener.name.clear();
		SellerCommodityListener.quantity.clear();
		SellerCommodityListener.belongto.clear();
		SellerCommodityListener.signfrom.clear();
		SellerCommodityListener.digitalsignature.clear();
	}

	public void actionPerformed(ActionEvent e) {
		for (int i = 0; i < number; i++) {
			if (e.getActionCommand().equals("商品" + i))
				new Mergoods(cdm, ctm, sm, fm, name.get(i), quantity.get(i),
						belongto.get(i), signfrom.get(i),
						digitalsignature.get(i));
		}
	}
}