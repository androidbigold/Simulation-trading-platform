package windows;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import basicclass.Commodity;
import datamanagement.CommodityManagement;
import datamanagement.CustomerManagement;
import datamanagement.FactoryManagement;
import datamanagement.SellerManagement;

@SuppressWarnings("serial")
public class Mergoods extends JFrame implements ActionListener {
	CommodityManagement cdm = null;
	CustomerManagement ctm = null;
	SellerManagement sm = null;
	FactoryManagement fm = null;

	Connection ct = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	JButton jb1, jb2, jb3 = null;
	JPanel jp1, jp2, jp3, jp4, jp5 = null;
	JTextField jtf1, jtf2, jtf3, jtf4 = null;
	JLabel jlb1, jlb2, jlb3, jlb4 = null;

	public Mergoods(CommodityManagement cdm, CustomerManagement ctm,
			SellerManagement sm, FactoryManagement fm, String name,
			int quantity, String belongto, String signfrom,
			String digitalsignature) {
		this.cdm = cdm;
		this.ctm = ctm;
		this.fm = fm;
		this.sm = sm;

		jb1 = new JButton("下架商品");
		jb2 = new JButton("修改信息");
		jb3 = new JButton("关闭界面");

		jb1.addActionListener(this);
		jb2.addActionListener(this);
		jb3.addActionListener(this);

		jp1 = new JPanel();
		jp2 = new JPanel();
		jp3 = new JPanel();
		jp4 = new JPanel();
		jp5 = new JPanel();

		jlb1 = new JLabel("名    称   ：");
		jlb2 = new JLabel("数    量   ：");
		jlb3 = new JLabel("厂    家   ：");
		jlb4 = new JLabel("授权书  : ");

		jtf1 = new JTextField(15);
		jtf2 = new JTextField(15);
		jtf3 = new JTextField(15);
		jtf4 = new JTextField(15);

		jtf1.setText(name);
		jtf2.setText(quantity + "");
		jtf3.setText(signfrom);
		jtf4.setText(digitalsignature);

		jtf1.setEditable(false);
		jtf2.setEditable(false);
		jtf3.setEditable(false);
		jtf4.setEditable(false);

		jp1.add(jlb1);
		jp1.add(jtf1);

		jp2.add(jlb2);
		jp2.add(jtf2);

		jp3.add(jlb3);
		jp3.add(jtf3);

		jp5.add(jlb4);
		jp5.add(jtf4);

		jp4.add(jb1);
		jp4.add(jb2);
		jp4.add(jb3);

		this.add(jp1);
		this.add(jp2);
		this.add(jp3);
		this.add(jp5);
		this.add(jp4);

		this.setLayout(new GridLayout(5, 1));
		this.setTitle("商品信息");
		// 设置窗体大小
		this.setSize(400, 500);
		// 设置窗体初始位置
		this.setLocation(500, 150);
		// 显示窗体
		this.setVisible(true);
		this.setResizable(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "下架商品") {
			ct = cdm.getConnection();
			String name = jtf1.getText();
			int quantity = Integer.parseInt(jtf2.getText());
			String signfrom = jtf3.getText();
			String digitalsignature = jtf4.getText();
			try {
				ps = ct.prepareStatement("delete from Commodity where name=? and quantity=? and belongto=? and signfrom=? and digitalsignature=?");
				ps.setString(1, name);
				ps.setInt(2, quantity);
				ps.setString(3, sm.getCurrentSeller().getName());
				ps.setString(4, signfrom);
				ps.setString(5, digitalsignature);
				if (ps.executeUpdate() == 1) {
					int temp = 0;
					if ((temp = cdm.getCommodityList().indexOf(
							new Commodity(name, quantity, sm.getCurrentSeller()
									.getName(), signfrom, digitalsignature))) != -1)
						cdm.getCommodityList().remove(temp);
					JOptionPane.showMessageDialog(null, "删除成功！", "提示消息",
							JOptionPane.WARNING_MESSAGE);
					dispose();
				} else
					JOptionPane.showMessageDialog(null, "删除失败！", "提示消息",
							JOptionPane.WARNING_MESSAGE);

			} catch (SQLException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
		}
		if (e.getActionCommand() == "修改信息") {
			dispose();
			new Mergaigoods(cdm, ctm, sm, fm, jtf1.getText(),
					Integer.parseInt(jtf2.getText()), sm.getCurrentSeller()
							.getName(), jtf3.getText(), jtf4.getText());
		}
		if (e.getActionCommand() == "关闭界面") {
			dispose();
		}
	}
}
