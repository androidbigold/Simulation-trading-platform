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
public class Mergaigoods extends JFrame implements ActionListener {
	CommodityManagement cdm = null;
	CustomerManagement ctm = null;
	SellerManagement sm = null;
	FactoryManagement fm = null;

	Connection ct = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	JButton jb1, jb2 = null;
	JPanel jp1, jp2, jp3, jp4, jp5 = null;
	JTextField jtf1, jtf2, jtf3, jtf4 = null;
	JLabel jlb1, jlb2, jlb3, jlb4 = null;

	String name = null;
	int quantity = 0;
	String belongto = null;
	String signfrom = null;
	String digitalsignature = null;

	public Mergaigoods(CommodityManagement cdm, CustomerManagement ctm,
			SellerManagement sm, FactoryManagement fm, String name,
			int quantity, String belongto, String signfrom,
			String digitalsignature) {
		this.cdm = cdm;
		this.ctm = ctm;
		this.fm = fm;
		this.sm = sm;
		this.name = name;
		this.quantity = quantity;
		this.belongto = belongto;
		this.signfrom = signfrom;
		this.digitalsignature = digitalsignature;

		jb1 = new JButton("ȷ��");
		jb2 = new JButton("ȡ��");

		jb1.addActionListener(this);
		jb2.addActionListener(this);

		jp1 = new JPanel();
		jp2 = new JPanel();
		jp3 = new JPanel();
		jp4 = new JPanel();
		jp5 = new JPanel();

		jlb1 = new JLabel("��     ��   ��");
		jlb2 = new JLabel("��     ��   ��");
		jlb3 = new JLabel("��     ��   ��");
		jlb4 = new JLabel("��Ȩ��   : ");

		jtf1 = new JTextField(15);
		jtf2 = new JTextField(15);
		jtf3 = new JTextField(15);
		jtf4 = new JTextField(15);

		jtf3.setText(signfrom);
		jtf4.setText(digitalsignature);
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

		this.add(jp1);
		this.add(jp2);
		this.add(jp3);
		this.add(jp5);
		this.add(jp4);

		this.setLayout(new GridLayout(5, 1));
		this.setTitle("��Ʒ��Ϣ");
		// ���ô����С
		this.setSize(400, 500);
		// ���ô����ʼλ��
		this.setLocation(500, 150);
		// ��ʾ����
		this.setVisible(true);
		this.setResizable(true);

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "ȷ��") {
			String newname = jtf1.getText();
			int newquantity = Integer.parseInt(jtf2.getText());

			ct = cdm.getConnection();
			try {
				ps = ct.prepareStatement("update Commodity set name=?,quantity=? where name=? and quantity=? and belongto=? and signfrom=? and digitalsignature=?");
				ps.setString(1, newname);
				ps.setInt(2, newquantity);
				ps.setString(3, name);
				ps.setInt(4, quantity);
				ps.setString(5, sm.getCurrentSeller().getName());
				ps.setString(6, signfrom);
				ps.setString(7, digitalsignature);
				if (ps.executeUpdate() == 1) {
					int temp = 0;
					if ((temp = cdm.getCommodityList().indexOf(
							new Commodity(name, quantity, sm.getCurrentSeller()
									.getName(), signfrom, digitalsignature))) != -1) {
						cdm.getCommodityList().get(temp).setName(newname);
						cdm.getCommodityList().get(temp)
								.setQuantity(newquantity);
						JOptionPane.showMessageDialog(null, "�޸ĳɹ���", "��ʾ��Ϣ",
								JOptionPane.WARNING_MESSAGE);
						dispose();
					} else
						JOptionPane.showMessageDialog(null, "δ�ҵ�����Ʒ��", "��ʾ��Ϣ",
								JOptionPane.WARNING_MESSAGE);
				} else
					JOptionPane.showMessageDialog(null, "�޸�ʧ�ܣ�", "��ʾ��Ϣ",
							JOptionPane.WARNING_MESSAGE);

			} catch (SQLException e1) {
				// TODO �Զ����ɵ� catch ��
				e1.printStackTrace();
			}
		}
		if (e.getActionCommand() == "ȡ��") {
			dispose();
		}
	}
}
