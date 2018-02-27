package windows;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
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

import javax.swing.Icon;
import javax.swing.ImageIcon;
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
public class Goods extends JFrame implements ActionListener {
	CommodityManagement cdm = null;
	CustomerManagement ctm = null;
	SellerManagement sm = null;
	FactoryManagement fm = null;

	Connection ct = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	// �������
	JButton jb1, jb2, jb3 = null;
	JPanel jp1, jp2, jp3, jp4 = null;
	JPanel jp21, jp22, jp23, jp24 = null;
	JPanel jp31, jp32 = null;
	JTextField jtf1, jtf2, jtf3, jtf4, jtf5, jtf6 = null;
	JLabel jlb1, jlb2, jlb3, jlb4, jlb5, jlb6 = null;

	int i = 0;

	public Goods(int i, CommodityManagement cdm, CustomerManagement ctm,
			SellerManagement sm, FactoryManagement fm, String name,
			int quantity, String belongto, String signfrom,
			String digitalsignature) {
		this.cdm = cdm;
		this.ctm = ctm;
		this.fm = fm;
		this.sm = sm;

		// �������
		jb1 = new JButton("��֤��Ȩ");
		jb2 = new JButton("������Ʒ");
		jb3 = new JButton("ȡ������");

		jb1.addActionListener(this);
		jb2.addActionListener(this);
		jb3.addActionListener(this);

		jp1 = new JPanel();
		jp2 = new JPanel();
		jp3 = new JPanel();
		jp4 = new JPanel();
		jp21 = new JPanel();
		jp22 = new JPanel();
		jp23 = new JPanel();
		jp24 = new JPanel();
		jp31 = new JPanel();

		jlb1 = new JLabel("���ƣ�");
		jlb2 = new JLabel("�̼ң�");
		jlb3 = new JLabel("������");
		jlb4 = new JLabel("���ң�");
		jlb5 = new JLabel("����ǩ����");

		jtf1 = new JTextField(15);
		jtf2 = new JTextField(15);
		jtf3 = new JTextField(15);
		jtf4 = new JTextField(15);
		jtf5 = new JTextField(15);

		jtf1.setText(name);
		jtf2.setText(belongto);
		jtf3.setText(quantity + "");
		jtf4.setText(signfrom);
		jtf5.setText(digitalsignature);

		jtf1.setEditable(false);
		jtf2.setEditable(false);
		jtf3.setEditable(false);
		jtf4.setEditable(false);
		jtf5.setEditable(false);

		jp21.add(jlb1);
		jp21.add(jtf1);
		jp22.add(jlb2);
		jp22.add(jtf2);
		jp23.add(jlb3);
		jp23.add(jtf3);
		jp24.add(jlb4);
		jp24.add(jtf4);

		jp31.add(jlb5);
		jp31.add(jtf5);

		jp4.add(jb1);
		jp4.add(jb2);
		jp4.add(jb3);

		jp2.add(jp21);
		jp2.add(jp22);
		jp2.add(jp23);
		jp2.add(jp24);

		jp3.add(jp31);

		this.add(jp1, BorderLayout.NORTH);
		this.add(jp2, BorderLayout.WEST);
		this.add(jp3, BorderLayout.EAST);
		this.add(jp4, BorderLayout.SOUTH);

		jp2.setLayout(new GridLayout(4, 1));

		// ���������ñ���
		this.setTitle("��Ʒ��Ϣ");
		// ���ô����С
		this.setSize(800, 600);
		// ���ô����ʼλ��
		this.setLocation(500, 150);
		// ��ʾ����
		this.setVisible(true);
		this.setResizable(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "��֤��Ȩ") {
			String FactoryName = jtf4.getText();
			String MessageSign = jtf5.getText();
			if (MessageSign.equals(""))
				JOptionPane.showMessageDialog(null, "����Ʒδ��Ȩ�����������", "��ʾ��Ϣ",
						JOptionPane.WARNING_MESSAGE);
			else {
				String Message = MessageSign.substring(0,
						MessageSign.indexOf("|"));
				String Sign = MessageSign.substring(
						MessageSign.indexOf("|") + 1, MessageSign.length());
				String PK = "";
				if (!FactoryName.equals("")) {
					ct = fm.getConnection();
					try {
						ps = ct.prepareStatement("select PublicKey from Factory where name=?");
						ps.setString(1, FactoryName);
						if (ps.executeQuery() != null) {
							rs = ps.executeQuery();
							while (rs.next())
								PK = rs.getString(1);
							if (PK.equals("")||PK==null)
								JOptionPane.showMessageDialog(null,
										"����δע�ᣡ���������", "��ʾ��Ϣ",
										JOptionPane.WARNING_MESSAGE);
							else {
								if (DigitalSignature.verify(DigitalSignature
										.getPublicKeyFromString(PK), Message,
										Sign))
									JOptionPane.showMessageDialog(null,
											"��Ȩ��ȷ,ǩ����Ч", "��ʾ��Ϣ",
											JOptionPane.WARNING_MESSAGE);
								else
									JOptionPane.showMessageDialog(null,
											"��Ȩ����,ǩ����Ч", "��ʾ��Ϣ",
											JOptionPane.WARNING_MESSAGE);
							}
						}
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
					JOptionPane.showMessageDialog(null, "δ�������̣����������", "��ʾ��Ϣ",
							JOptionPane.WARNING_MESSAGE);
			}
		}
		if (e.getActionCommand() == "������Ʒ") {
			if (Integer.parseInt(jtf3.getText()) <= 0) {
				JOptionPane.showMessageDialog(null, "����Ʒȱ��,�޷�����", "��ʾ��Ϣ",
						JOptionPane.WARNING_MESSAGE);
			} else {
				String CommodityName = jtf1.getText();
				String belongto = jtf2.getText();
				int quantity = Integer.parseInt(jtf3.getText());
				String signfrom = jtf4.getText();
				String signature = jtf5.getText();
				ArrayList<Commodity> CommodityList = cdm.getCommodityList();
				int temp = 0;

				if ((temp = CommodityList.indexOf(new Commodity(CommodityName,
						quantity, belongto, signfrom, signature))) != -1) {
					jtf3.setText(CommodityList.get(temp).getQuantity() - 1 + "");
					CommodityList.get(temp).reduce();

					ct = cdm.getConnection();
					try {
						ps = ct.prepareStatement("update Commodity set quantity=? where name=? and quantity=? and belongto=? and signfrom=? and digitalsignature=?");
						ps.setInt(1, CommodityList.get(temp).getQuantity());
						ps.setString(2, CommodityName);
						ps.setInt(3, quantity);
						ps.setString(4, belongto);
						ps.setString(5, signfrom);
						ps.setString(6, signature);
						if (ps.executeUpdate() == 1) {
							ct = sm.getConnection();
							ps = ct.prepareStatement("select PrivateKey from Seller where name=?");
							ps.setString(1, belongto);
							if (ps.executeQuery() != null) {
								rs = ps.executeQuery();
								while (rs.next())
									signature = ctm.getCurrentCustomer()
											.getName()
											+ "���̼�"
											+ belongto
											+ "��������Ʒ"
											+ CommodityName
											+ System.currentTimeMillis()
											+ "|"
											+ DigitalSignature
													.sign(DigitalSignature
															.getPrivateKeyFromString(rs
																	.getString(1)),
															ctm.getCurrentCustomer()
																	.getName()
																	+ "���̼�"
																	+ belongto
																	+ "��������Ʒ"
																	+ CommodityName
																	+ System.currentTimeMillis());

								ct = cdm.getConnection();
								String newbelongto = ctm.getCurrentCustomer()
										.getName();
								CommodityList.add(new Commodity(CommodityName,
										1, newbelongto, belongto, signature));
								ps = ct.prepareStatement("insert into Commodity values(?,?,?,?,?)");
								ps.setString(1, CommodityName);
								ps.setInt(2, 1);
								ps.setString(3, newbelongto);
								ps.setString(4, belongto);
								ps.setString(5, signature);
								if (ps.executeUpdate() == 1) {
									JOptionPane.showMessageDialog(null,
											"����ɹ���", "��ʾ��Ϣ",
											JOptionPane.WARNING_MESSAGE);
									CommodityListener.quantity
											.set(i, CommodityListener.quantity
													.get(i) - 1);
								} else
									JOptionPane.showMessageDialog(null,
											"����ʧ�ܣ�", "��ʾ��Ϣ",
											JOptionPane.WARNING_MESSAGE);
							} else
								JOptionPane.showMessageDialog(null, "����ʧ�ܣ�",
										"��ʾ��Ϣ", JOptionPane.WARNING_MESSAGE);
						} else
							JOptionPane.showMessageDialog(null, "δ�ҵ����̻���",
									"��ʾ��Ϣ", JOptionPane.WARNING_MESSAGE);

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
				} else
					JOptionPane.showMessageDialog(null, "δ�ҵ�����Ʒ��", "��ʾ��Ϣ",
							JOptionPane.WARNING_MESSAGE);
			}
			dispose();
		}
		if (e.getActionCommand() == "ȡ������") {
			dispose();
		}
	}
}
