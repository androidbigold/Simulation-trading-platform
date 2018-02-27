package windows;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import basicclass.Commodity;
import basicclass.DigitalSignature;
import basicclass.Seller;
import datamanagement.CommodityManagement;
import datamanagement.CustomerManagement;
import datamanagement.FactoryManagement;
import datamanagement.SellerManagement;

@SuppressWarnings("serial")
public class Facnewgoods extends JFrame implements ActionListener {
	CommodityManagement cdm = null;
	CustomerManagement ctm = null;
	SellerManagement sm = null;
	FactoryManagement fm = null;

	Connection ct = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	JButton jb1, jb2 = null;
	JPanel jp1, jp2, jp3, jp4, jp5 = null;
	JTextField jtf1, jtf2, jtf3 = null;
	JLabel jlb1, jlb2, jlb3, jlb4 = null;
	JComboBox<String> jcb = null;

	public Facnewgoods(CommodityManagement cdm, CustomerManagement ctm,
			SellerManagement sm, FactoryManagement fm) {
		this.cdm = cdm;
		this.ctm = ctm;
		this.fm = fm;
		this.sm = sm;

		jb1 = new JButton("ȷ��");
		jb2 = new JButton("ȡ��");

		jb1.addActionListener(this);
		jb2.addActionListener(this);

		jp1 = new JPanel();
		jp2 = new JPanel();
		jp3 = new JPanel();
		jp4 = new JPanel();
		jp5 = new JPanel();

		jlb1 = new JLabel("��          ��   ��");
		jlb2 = new JLabel("��          ��   ��");
		jlb3 = new JLabel("��Ȩ�̻�   ��");

		jtf1 = new JTextField(15);
		jtf2 = new JTextField(15);
		jtf3 = new JTextField(15);

		jcb = new JComboBox<String>();

		jcb.setPreferredSize(new Dimension(170, 25));
		getSellerList();
		jcb.addActionListener(this);

		jtf3.setEditable(false);

		jp1.add(jlb1);
		jp1.add(jtf1);

		jp2.add(jlb2);
		jp2.add(jtf2);

		jp3.add(jlb3);
		jp3.add(jcb);

		jp5.add(jb1);
		jp5.add(jb2);

		this.add(jp1);
		this.add(jp2);
		this.add(jp3);
		this.add(jp5);

		this.setLayout(new GridLayout(4, 1));
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
			int temp = 0;
			String newname = null;
			int newquantity = 0;
			String newbelongto = null;
			String newsignfrom = null;
			String newdigitalsignature = null;

			if (jtf1.getText().equals("") || jtf2.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "������������Ϣ��", "��ʾ��Ϣ",
						JOptionPane.WARNING_MESSAGE);
				return;
			}

			newname = jtf1.getText();
			newquantity = Integer.parseInt(jtf2.getText());
			newbelongto = (String) jcb.getSelectedItem();

			ct = fm.getConnection();
			try {
				ps = ct.prepareStatement("select PrivateKey from Factory where name=?");
				ps.setString(1, fm.getCurrentFactory().getName());
				if (ps.executeQuery() != null) {
					rs = ps.executeQuery();
					while (rs.next())
						newdigitalsignature = "�ӳ���"
								+ fm.getCurrentFactory().getName()
								+ "��Ȩ��"
								+ newbelongto
								+ "��Ʒ"
								+ newname
								+ System.currentTimeMillis()
								+ "|"
								+ DigitalSignature.sign(DigitalSignature
										.getPrivateKeyFromString(rs
												.getString(1)), "�ӳ���"
										+ fm.getCurrentFactory().getName()
										+ "��Ȩ��" + newbelongto + "��Ʒ" + newname
										+ System.currentTimeMillis());

				}
			} catch (SQLException e1) {
				// TODO �Զ����ɵ� catch ��
				e1.printStackTrace();
			} catch (InvalidKeyException e1) {
				// TODO �Զ����ɵ� catch ��
				e1.printStackTrace();
			} catch (NoSuchAlgorithmException e1) {
				// TODO �Զ����ɵ� catch ��
				e1.printStackTrace();
			} catch (SignatureException e1) {
				// TODO �Զ����ɵ� catch ��
				e1.printStackTrace();
			} catch (UnsupportedEncodingException e1) {
				// TODO �Զ����ɵ� catch ��
				e1.printStackTrace();
			} catch (Exception e1) {
				// TODO �Զ����ɵ� catch ��
				e1.printStackTrace();
			}
			cdm.getCommodityList()
					.add(new Commodity(newname, newquantity, newbelongto, fm
							.getCurrentFactory().getName(), newdigitalsignature));
			ct = cdm.getConnection();

			try {
				ps = ct.prepareStatement("insert into Commodity values(?,?,?,?,?)");
				ps.setString(1, newname);
				ps.setInt(2, newquantity);
				ps.setString(3, newbelongto);
				ps.setString(4, fm.getCurrentFactory().getName());
				ps.setString(5, newdigitalsignature);
				if (ps.executeUpdate() == 1) {
					JOptionPane.showMessageDialog(null, "�ϼܳɹ���", "��ʾ��Ϣ",
							JOptionPane.WARNING_MESSAGE);
					dispose();
				} else
					JOptionPane.showMessageDialog(null, "�ϼ�ʧ�ܣ�", "��ʾ��Ϣ",
							JOptionPane.WARNING_MESSAGE);
				dispose();
			} catch (SQLException e1) {
				// TODO �Զ����ɵ� catch ��
				e1.printStackTrace();
			}

		}
		if (e.getActionCommand() == "ȡ��") {
			dispose();
		}
	}

	public void getSellerList() {
		ArrayList<Seller> sl = sm.getSellerList();
		for (int i = 0; i < sl.size(); i++) {
			jcb.addItem(sl.get(i).getName());
		}
	}
}
