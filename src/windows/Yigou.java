package windows;

import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
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

import basicclass.DigitalSignature;
import datamanagement.CommodityManagement;
import datamanagement.CustomerManagement;
import datamanagement.FactoryManagement;
import datamanagement.SellerManagement;

@SuppressWarnings("serial")
public class Yigou extends JFrame implements ActionListener {
	CommodityManagement cdm = null;
	CustomerManagement ctm = null;
	SellerManagement sm = null;
	FactoryManagement fm = null;

	Connection ct = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	JButton jb1, jb2 = null;
	JPanel jp1, jp2, jp3, jp4, jp5, jp6 = null;
	JTextField jtf1, jtf2, jtf3, jtf4, jtf5 = null;
	JLabel jlb1, jlb2, jlb3, jlb4, jlb5 = null;

	public Yigou(CommodityManagement cdm, CustomerManagement ctm,
			SellerManagement sm, FactoryManagement fm, String name,
			int quantity, String belongto, String signfrom,
			String digitalsignature) {

		this.cdm = cdm;
		this.ctm = ctm;
		this.fm = fm;
		this.sm = sm;

		// �������
		jb1 = new JButton("��֤ǩ��");
		jb2 = new JButton("�رս���");

		jb1.addActionListener(this);
		jb2.addActionListener(this);

		jp1 = new JPanel();
		jp2 = new JPanel();
		jp3 = new JPanel();
		jp4 = new JPanel();
		jp5 = new JPanel();
		jp6 = new JPanel();

		jlb1 = new JLabel("����    ��");
		jlb2 = new JLabel("�̼�    ��");
		jlb3 = new JLabel("����    ��");
		jlb5 = new JLabel("�̼�ǩ����");

		jtf1 = new JTextField(15);
		jtf2 = new JTextField(15);
		jtf3 = new JTextField(15);
		jtf5 = new JTextField(15);

		jtf1.setText(name);
		jtf2.setText(signfrom);
		jtf3.setText(quantity + "");
		jtf5.setText(digitalsignature);

		jtf1.setEditable(false);
		jtf2.setEditable(false);
		jtf3.setEditable(false);
		jtf5.setEditable(false);

		jp1.add(jlb1);
		jp1.add(jtf1);

		jp2.add(jlb2);
		jp2.add(jtf2);

		jp3.add(jlb3);
		jp3.add(jtf3);

		jp5.add(jlb5);
		jp5.add(jtf5);

		jp6.add(jb1);
		jp6.add(jb2);

		this.add(jp1);
		this.add(jp2);
		this.add(jp3);
		this.add(jp5);
		this.add(jp6);

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
		if (e.getActionCommand() == "��֤ǩ��") {
			String SellerName = jtf2.getText();
			String MessageSign = jtf5.getText();
			if (MessageSign.equals(""))
				JOptionPane.showMessageDialog(null, "����Ʒδǩ�������������˼ٻ���", "��ʾ��Ϣ",
						JOptionPane.WARNING_MESSAGE);
			else {
				String Message = MessageSign.substring(0,
						MessageSign.indexOf("|"));
				String Sign = MessageSign.substring(
						MessageSign.indexOf("|") + 1, MessageSign.length());
				String PK = null;
				if (!SellerName.equals("")) {
					ct = fm.getConnection();
					try {
						ps = ct.prepareStatement("select PublicKey from Seller where name=?");
						ps.setString(1, SellerName);
						if (ps.executeQuery() != null) {
							rs = ps.executeQuery();
							while (rs.next())
								PK = rs.getString(1);
							if (DigitalSignature
									.verify(DigitalSignature
											.getPublicKeyFromString(PK),
											Message, Sign))
								JOptionPane.showMessageDialog(null, "ǩ����Ч",
										"��ʾ��Ϣ", JOptionPane.WARNING_MESSAGE);
							else
								JOptionPane.showMessageDialog(null, "ǩ����Ч",
										"��ʾ��Ϣ", JOptionPane.WARNING_MESSAGE);
						} else
							JOptionPane.showMessageDialog(null,
									"�̻�δע�ᣡ���������˼ٻ���", "��ʾ��Ϣ",
									JOptionPane.WARNING_MESSAGE);
					} catch (SQLException e1) {
						// TODO �Զ����ɵ� catch ��
						e1.printStackTrace();
					} catch (InvalidKeyException e1) {
						// TODO �Զ����ɵ� catch ��
						e1.printStackTrace();
					} catch (HeadlessException e1) {
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
				} else
					JOptionPane.showMessageDialog(null, "δ�����̻������������˼ٻ���",
							"��ʾ��Ϣ", JOptionPane.WARNING_MESSAGE);
			}
		}
		if (e.getActionCommand() == "�رս���") {
			dispose();
		}
	}
}
