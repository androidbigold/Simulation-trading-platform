package windows;

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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import basicclass.Commodity;
import basicclass.DigitalSignature;
import datamanagement.CommodityManagement;
import datamanagement.CustomerManagement;
import datamanagement.FactoryManagement;
import datamanagement.SellerManagement;

@SuppressWarnings("serial")
public class Mernewgoods extends JFrame implements ActionListener {
	CommodityManagement cdm = null;
	CustomerManagement ctm = null;
	SellerManagement sm = null;
	FactoryManagement fm = null;

	Connection ct = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	JButton jb1, jb2 = null;
	JPanel jp1, jp2, jp3, jp4 = null;
	JTextField jtf1, jtf2, jtf3 = null;
	JLabel jlb1, jlb2, jlb3 = null;

	public Mernewgoods(CommodityManagement cdm, CustomerManagement ctm,
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

		jlb1 = new JLabel("��     ��   ��");
		jlb2 = new JLabel("��     ��   ��");
		jlb3 = new JLabel("��     ��   ��");

		jtf1 = new JTextField(15);
		jtf2 = new JTextField(15);
		jtf3 = new JTextField(15);

		jp1.add(jlb1);
		jp1.add(jtf1);

		jp2.add(jlb2);
		jp2.add(jtf2);

		jp3.add(jlb3);
		jp3.add(jtf3);

		jp4.add(jb1);
		jp4.add(jb2);

		this.add(jp1);
		this.add(jp2);
		this.add(jp3);
		this.add(jp4);

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
			newsignfrom = jtf3.getText();
			
			try {
				ct = sm.getConnection();
				ps = ct.prepareStatement("select PrivateKey from Seller where name=?");
				ps.setString(1, sm.getCurrentSeller().getName());
				if (ps.executeQuery() != null) {
					rs = ps.executeQuery();
					while (rs.next())
						newdigitalsignature = "�ӳ���"
								+ newsignfrom
								+ "��Ȩ��"
								+ sm.getCurrentSeller().getName()
								+ "��Ʒ"
								+ newname
								+ System.currentTimeMillis()
								+ "|"
								+ DigitalSignature.sign(
										DigitalSignature
												.getPrivateKeyFromString(rs
														.getString(1)),
										"�ӳ���"
												+ newsignfrom
												+ "��Ȩ��"
												+ sm.getCurrentSeller()
														.getName() + "��Ʒ"
												+ newname
												+ System.currentTimeMillis());

				}
				cdm.getCommodityList().add(
						new Commodity(newname, newquantity, sm
								.getCurrentSeller().getName(), newsignfrom,
								newdigitalsignature));
				ct = cdm.getConnection();
				ps = ct.prepareStatement("insert into Commodity values(?,?,?,?,?)");
				ps.setString(1, newname);
				ps.setInt(2, newquantity);
				ps.setString(3, sm.getCurrentSeller().getName());
				ps.setString(4, newsignfrom);
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
		}
		if (e.getActionCommand() == "ȡ��") {
			dispose();
		}
	}
}
