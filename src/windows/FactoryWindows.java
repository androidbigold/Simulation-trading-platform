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
public class FactoryWindows extends JFrame implements ActionListener {
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

	// �������
	JButton jb1, jb2, jb3, jb4 = null;
	JPanel jp1, jp2 = null;

	public FactoryWindows(CommodityManagement cdm, CustomerManagement ctm,
			SellerManagement sm, FactoryManagement fm) {
		this.cdm = cdm;
		this.ctm = ctm;
		this.fm = fm;
		this.sm = sm;

		// �������
		jb1 = new JButton("��Ʒ�б�");
		jb2 = new JButton("�ϼ���Ʒ");
		jb3 = new JButton("�޸�����");
		jb4 = new JButton("�˳���¼");
		// ���ü���
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
		
		// ���������ñ���
		this.setTitle("factory");
		// ���ô����С
		this.setSize(800, 600);
		// ���ô����ʼλ��
		this.setLocation(450, 100);
		// ���õ��رմ���ʱ����֤JVMҲ�˳�
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// ��ʾ����
		this.setVisible(true);
		this.setResizable(true);
		jp2.setVisible(false);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "��Ʒ�б�") {
			CommodityList.clear();
			CommodityButton.clear();
			FactoryCommodityListener.clear();

			jp2.removeAll();
			ct = cdm.getConnection();
			try {
				ps = ct.prepareStatement("select * from Commodity where belongto =? or signfrom=?");
				ps.setString(1, fm.getCurrentFactory().getName());
				ps.setString(2, fm.getCurrentFactory().getName());
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
						CommodityButton.add(new JButton("��Ʒ��: "
								+ CommodityList.get(i).getName()));
					}
					for (int j = 0; j < CommodityButton.size(); j++) {
						CommodityButton.get(j).setPreferredSize(
								new Dimension(200, 80));
						CommodityButton.get(j).setActionCommand("��Ʒ" + j);
						CommodityButton.get(j).addActionListener(
								new FactoryCommodityListener(cdm, ctm, sm, fm,
										CommodityList.get(j).getName(),
										CommodityList.get(j).getQuantity(),
										CommodityList.get(j).getBelongto(),
										CommodityList.get(j).getSignfrom(),
										CommodityList.get(j)
												.getDigitalsignature()));
						jp2.add(CommodityButton.get(j));
					}
				} else
					JOptionPane.showMessageDialog(null, "��ȡ��Ʒ�б�ʧ�ܣ�", "��ʾ��Ϣ",
							JOptionPane.WARNING_MESSAGE);
			} catch (SQLException e1) {
				// TODO �Զ����ɵ� catch ��
				e1.printStackTrace();
			}
			// �˴��������ݿ�����ѹ��Ĳ�Ʒ
			jp2.repaint();
			jp2.setVisible(true);
			this.repaint();
		}
		if (e.getActionCommand() == "�ϼ���Ʒ") {
			new Facnewgoods(cdm, ctm, sm, fm);
		}
		if (e.getActionCommand() == "�޸�����") {
			// �������ݿ������
			new Gaimi(this, null, null, null, fm);
		}
		if (e.getActionCommand() == "�˳���¼") {
			dispose();
			fm.LogOut();
			new Login(cdm, ctm, sm, fm);
		}
	}
}

class FactoryCommodityListener implements ActionListener {
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

	public FactoryCommodityListener(CommodityManagement cdm,
			CustomerManagement ctm, SellerManagement sm, FactoryManagement fm,
			String name, int quantity, String belongto, String signfrom,
			String digitalsignature) {
		this.cdm = cdm;
		this.ctm = ctm;
		this.fm = fm;
		this.sm = sm;
		FactoryCommodityListener.name.add(name);
		FactoryCommodityListener.quantity.add(quantity);
		FactoryCommodityListener.belongto.add(belongto);
		FactoryCommodityListener.signfrom.add(signfrom);
		FactoryCommodityListener.digitalsignature.add(digitalsignature);
		number++;
	}

	static void clear() {
		number = 0;
		FactoryCommodityListener.name.clear();
		FactoryCommodityListener.quantity.clear();
		FactoryCommodityListener.belongto.clear();
		FactoryCommodityListener.signfrom.clear();
		FactoryCommodityListener.digitalsignature.clear();
	}

	public void actionPerformed(ActionEvent e) {
		for (int i = 0; i < number; i++) {
			if (e.getActionCommand().equals("��Ʒ" + i))
				new Facgoods(cdm, ctm, sm, fm, name.get(i), quantity.get(i),
						belongto.get(i), signfrom.get(i),
						digitalsignature.get(i));
		}
	}
}