package windows;

import java.awt.GridLayout;
import java.awt.Window;
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

import basicclass.Customer;
import basicclass.Factory;
import basicclass.Seller;
import datamanagement.CommodityManagement;
import datamanagement.CustomerManagement;
import datamanagement.FactoryManagement;
import datamanagement.SellerManagement;

@SuppressWarnings("serial")
public class Gaimi extends JFrame implements ActionListener {
	CommodityManagement cdm = null;
	CustomerManagement ctm = null;
	SellerManagement sm = null;
	FactoryManagement fm = null;

	PreparedStatement ps = null;
	Connection ct = null;
	ResultSet rs = null;
	
	Window w=null;

	// �������
	JLabel jlb1, jlb2 = null;
	JButton jb1, jb2 = null;
	JTextField jtf1, jtf2 = null;
	JPanel jp1, jp2, jp3 = null;

	public Gaimi(Window w,CommodityManagement cdm, CustomerManagement ctm,
			SellerManagement sm, FactoryManagement fm) {
		this.cdm=cdm;
		this.ctm=ctm;
		this.fm=fm;
		this.sm=sm;
		this.w=w;
		
		// �������
		jb1 = new JButton("ȷ��");
		jb2 = new JButton("����");

		jb1.addActionListener(this);
		jb2.addActionListener(this);

		jp1 = new JPanel();
		jp2 = new JPanel();
		jp3 = new JPanel();

		jlb1 = new JLabel("ԭ���룺");
		jlb2 = new JLabel("�����룺");

		jtf1 = new JTextField(15);
		jtf2 = new JTextField(15);

		jp1.add(jlb1);
		jp1.add(jtf1);

		jp2.add(jlb2);
		jp2.add(jtf2);

		jp3.add(jb1);
		jp3.add(jb2);

		// ����JFrame��
		this.add(jp1);
		this.add(jp2);
		this.add(jp3);
		// ���ò��ֹ�����
		this.setLayout(new GridLayout(3, 1));
		// ���������ñ���
		this.setTitle("�����޸�");
		// ���ô����С
		this.setSize(400, 300);
		// ���ô����ʼλ��
		this.setLocation(700, 300);
		// ��ʾ����
		this.setVisible(true);
		this.setResizable(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "ȷ��") {
			String name = null;
			String password = null;
			if (ctm != null) {
				ct = ctm.getConnection();
				Customer c = ctm.getCurrentCustomer();
				name = c.getName();
				password = jtf1.getText();
				if (c.isPasswordRight(password)) {
					password = jtf2.getText();
					if (password.equals(""))
						JOptionPane.showMessageDialog(null, "�����������룡", "��ʾ��Ϣ",
								JOptionPane.WARNING_MESSAGE);
					else {
						try {
							ps = ct.prepareStatement("update Customer set password=? where name=?");
							ps.setString(1, password);
							ps.setString(2, name);
							if (ps.executeUpdate() == 1) {
								c.setPassword(password);
								JOptionPane.showMessageDialog(null, "�޸ĳɹ���",
										"��ʾ��Ϣ", JOptionPane.WARNING_MESSAGE);
								dispose();
								w.dispose();
							}

						} catch (SQLException e1) {
							// TODO �Զ����ɵ� catch ��
							e1.printStackTrace();
						}
					}
				} else
					JOptionPane.showMessageDialog(null, "ԭ�������", "��ʾ��Ϣ",
							JOptionPane.WARNING_MESSAGE);
			} else if (sm != null){
				ct = sm.getConnection();
				Seller s = sm.getCurrentSeller();
				name = s.getName();
				password = jtf1.getText();
				if (s.isPasswordRight(password)) {
					password = jtf2.getText();
					if (password.equals(""))
						JOptionPane.showMessageDialog(null, "�����������룡", "��ʾ��Ϣ",
								JOptionPane.WARNING_MESSAGE);
					else {
						try {
							ps = ct.prepareStatement("update Seller set password=? where name=?");
							ps.setString(1, password);
							ps.setString(2, name);
							if (ps.executeUpdate() == 1) {
								s.setPassword(password);
								JOptionPane.showMessageDialog(null, "�޸ĳɹ���",
										"��ʾ��Ϣ", JOptionPane.WARNING_MESSAGE);
								dispose();
								w.dispose();
							}
						} catch (SQLException e1) {
							// TODO �Զ����ɵ� catch ��
							e1.printStackTrace();
						}
					}
				} else
					JOptionPane.showMessageDialog(null, "ԭ�������", "��ʾ��Ϣ",
							JOptionPane.WARNING_MESSAGE);
			}
			else if (fm != null){
				ct = fm.getConnection();
				Factory f = fm.getCurrentFactory();
				name = f.getName();
				password = jtf1.getText();
				if (f.isPasswordRight(password)) {
					password = jtf2.getText();
					if (password.equals(""))
						JOptionPane.showMessageDialog(null, "�����������룡", "��ʾ��Ϣ",
								JOptionPane.WARNING_MESSAGE);
					else {
						try {
							ps = ct.prepareStatement("update Factory set password=? where name=?");
							ps.setString(1, password);
							ps.setString(2, name);
							if (ps.executeUpdate() == 1) {
								f.setPassword(password);
								JOptionPane.showMessageDialog(null, "�޸ĳɹ���",
										"��ʾ��Ϣ", JOptionPane.WARNING_MESSAGE);
								dispose();
								w.dispose();
							}
						} catch (SQLException e1) {
							// TODO �Զ����ɵ� catch ��
							e1.printStackTrace();
						}
					}
				} else
					JOptionPane.showMessageDialog(null, "ԭ�������", "��ʾ��Ϣ",
							JOptionPane.WARNING_MESSAGE);
			}
			// �����ݿ�
		}
		if (e.getActionCommand() == "����") {
			clear();
		}
	}

	// ����ı���������
	public void clear() {
		jtf1.setText("");
		jtf2.setText("");
	}
}
