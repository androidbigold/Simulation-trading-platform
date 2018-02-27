package windows;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import datamanagement.CommodityManagement;
import datamanagement.CustomerManagement;
import datamanagement.FactoryManagement;
import datamanagement.SellerManagement;

@SuppressWarnings("serial")
public class Zhuce extends JFrame implements ActionListener {
	CommodityManagement cdm = null;
	CustomerManagement ctm = null;
	SellerManagement sm = null;
	FactoryManagement fm = null;

	// �������
	JPanel jp1, jp2, jp3, jp4, jp5 = null;
	JLabel jlb1, jlb2, jlb3, jlb4 = null;
	JTextField jtf1 = null;
	JPasswordField jpf1, jpf2 = null;
	JButton jb1, jb2 = null;
	JRadioButton jrb1, jrb2, jrb3 = null;
	ButtonGroup bg = null;

	public Zhuce(CommodityManagement cdm, CustomerManagement ctm,
			SellerManagement sm, FactoryManagement fm) {
		this.ctm = ctm;
		this.cdm = cdm;
		this.fm = fm;
		this.sm = sm;

		jb1 = new JButton("ȷ��");
		jb1.addActionListener(this);

		jb2 = new JButton("����");
		jb2.addActionListener(this);

		jrb1 = new JRadioButton("����");
		jrb2 = new JRadioButton("�̼�");
		jrb3 = new JRadioButton("�˿�");
		bg = new ButtonGroup();
		bg.add(jrb1);
		bg.add(jrb2);
		bg.add(jrb3);
		jrb1.setSelected(true);// ��ʼλ�õ�ѡ��

		jp1 = new JPanel();
		jp2 = new JPanel();
		jp3 = new JPanel();
		jp4 = new JPanel();
		jp5 = new JPanel();

		jlb1 = new JLabel("�û�����            ");
		jlb2 = new JLabel("��    �룺             ");
		jlb3 = new JLabel("�ٴ��������룺");
		jlb4 = new JLabel("ѡ���ɫ���ͣ�");

		jtf1 = new JTextField(15);
		jpf1 = new JPasswordField(15);
		jpf2 = new JPasswordField(15);

		jtf1.addActionListener(this);
		jpf1.addActionListener(this);
		jpf2.addActionListener(this);

		jp1.add(jlb1);
		jp1.add(jtf1);

		jp2.add(jlb2);
		jp2.add(jpf1);

		jp4.add(jlb4);
		jp4.add(jrb1);
		jp4.add(jrb2);
		jp4.add(jrb3);

		jp3.add(jlb3);
		jp3.add(jpf2);

		jp5.add(jb1);
		jp5.add(jb2);

		// ����JFrame��
		this.add(jp1);
		this.add(jp2);
		this.add(jp3);
		this.add(jp4);
		this.add(jp5);

		// ���ò��ֹ�����
		this.setLayout(new GridLayout(5, 1, 10, 10));
		jp1.setLayout(new FlowLayout(FlowLayout.LEFT));
		jp2.setLayout(new FlowLayout(FlowLayout.LEFT));
		jp3.setLayout(new FlowLayout(FlowLayout.LEFT));
		// ���������ñ���
		this.setTitle("���û�ע��");
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
			try {
				if(jrb1.isSelected())  
	            {  
	                  gongregister(fm);  
	            }else if(jrb2.isSelected()) //ѧ���ڵ�¼ϵͳ  
	            {  
	                  shangregister(sm);  
	            }else if(jrb3.isSelected()){
	            	guregister(ctm);
	            }
			} catch (NoSuchAlgorithmException e1) {
				// TODO �Զ����ɵ� catch ��
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO �Զ����ɵ� catch ��
				e1.printStackTrace();
			}
		}
		if (e.getActionCommand() == "����") {
			clear();
		}
	}

	// ע���Ƿ�ɹ��жϷ���
	@SuppressWarnings("deprecation")
	public void gongregister(FactoryManagement fm)
			throws NoSuchAlgorithmException, SQLException {
		if (jpf1.getText().equals("") || jtf1.getText().equals("")
				|| jpf2.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "������ϢΪ�գ�", "��ʾ��Ϣ",
					JOptionPane.WARNING_MESSAGE);
		} else if (jpf1.getText().equals(jpf2.getText())) {
			int temp = fm.addFactory(jtf1.getText(), jpf1.getText());
			if (temp == 0) {
				JOptionPane.showMessageDialog(null, "ע��ɹ���", "��ʾ��Ϣ",
						JOptionPane.WARNING_MESSAGE);
				dispose();
				new Login(cdm, ctm, sm, fm);
			} else if (temp == 1) {
				clear();
				JOptionPane.showMessageDialog(null, "���û��Ѵ��ڣ�", "��ʾ��Ϣ",
						JOptionPane.WARNING_MESSAGE);
			} else {
				clear();
				JOptionPane.showMessageDialog(null, "ע��ʧ�ܣ�", "��ʾ��Ϣ",
						JOptionPane.WARNING_MESSAGE);
			}
			// �������ݿ�д���û���Ϣ�����ж��û����Ƿ��ظ�
		} else {
			clear();
			JOptionPane.showMessageDialog(null, "��Ϣ��д����", "��ʾ��Ϣ",
					JOptionPane.WARNING_MESSAGE);
		}
	}
	
	@SuppressWarnings("deprecation")
	public void shangregister(SellerManagement sm)
			throws NoSuchAlgorithmException, SQLException {
		if (jpf1.getText().equals("") || jtf1.getText().equals("")
				|| jpf2.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "������ϢΪ�գ�", "��ʾ��Ϣ",
					JOptionPane.WARNING_MESSAGE);
		} else if (jpf1.getText().equals(jpf2.getText())) {
			int temp = sm.addSeller(jtf1.getText(), jpf1.getText());
			if (temp == 0) {
				JOptionPane.showMessageDialog(null, "ע��ɹ���", "��ʾ��Ϣ",
						JOptionPane.WARNING_MESSAGE);
				dispose();
				new Login(cdm, ctm, sm, fm);
			} else if (temp == 1) {
				clear();
				JOptionPane.showMessageDialog(null, "���û��Ѵ��ڣ�", "��ʾ��Ϣ",
						JOptionPane.WARNING_MESSAGE);
			} else {
				clear();
				JOptionPane.showMessageDialog(null, "ע��ʧ�ܣ�", "��ʾ��Ϣ",
						JOptionPane.WARNING_MESSAGE);
			}
			// �������ݿ�д���û���Ϣ�����ж��û����Ƿ��ظ�
		} else {
			clear();
			JOptionPane.showMessageDialog(null, "��Ϣ��д����", "��ʾ��Ϣ",
					JOptionPane.WARNING_MESSAGE);
		}
	}
	
	@SuppressWarnings("deprecation")
	public void guregister(CustomerManagement ctm)
			throws NoSuchAlgorithmException, SQLException {
		if (jpf1.getText().equals("") || jtf1.getText().equals("")
				|| jpf2.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "������ϢΪ�գ�", "��ʾ��Ϣ",
					JOptionPane.WARNING_MESSAGE);
		} else if (jpf1.getText().equals(jpf2.getText())) {
			int temp = ctm.addCustomer(jtf1.getText(), jpf1.getText());
			if (temp == 0) {
				JOptionPane.showMessageDialog(null, "ע��ɹ���", "��ʾ��Ϣ",
						JOptionPane.WARNING_MESSAGE);
				dispose();
				new Login(cdm, ctm, sm, fm);
			} else if (temp == 1) {
				clear();
				JOptionPane.showMessageDialog(null, "���û��Ѵ��ڣ�", "��ʾ��Ϣ",
						JOptionPane.WARNING_MESSAGE);
			} else {
				clear();
				JOptionPane.showMessageDialog(null, "ע��ʧ�ܣ�", "��ʾ��Ϣ",
						JOptionPane.WARNING_MESSAGE);
			}
			// �������ݿ�д���û���Ϣ�����ж��û����Ƿ��ظ�
		} else {
			clear();
			JOptionPane.showMessageDialog(null, "��Ϣ��д����", "��ʾ��Ϣ",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	public void clear() {
		jtf1.setText("");
		jpf1.setText("");
		jpf2.setText("");
	}
}
