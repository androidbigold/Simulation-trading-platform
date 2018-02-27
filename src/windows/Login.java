package windows;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
public class Login extends JFrame implements ActionListener {
	CommodityManagement cdm = null;
	CustomerManagement ctm = null;
	SellerManagement sm = null;
	FactoryManagement fm = null;

	// �������
	JButton jb1, jb2 = null;
	JRadioButton jrb1, jrb2, jrb3 = null;
	JPanel jp1, jp2, jp3, jp4 = null;
	JTextField jtf = null;
	JLabel jlb1, jlb2, jlb3 = null;
	JPasswordField jpf = null;
	ButtonGroup bg = null;

	public Login(CommodityManagement cdm, CustomerManagement ctm,
			SellerManagement sm, FactoryManagement fm) {
		this.cdm = cdm;
		this.ctm = ctm;
		this.sm = sm;
		this.fm = fm;

		// �������
		jb1 = new JButton("��¼");
		jb2 = new JButton("ע��");
		// ���ü���
		jb1.addActionListener(this);
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

		jlb1 = new JLabel("�û�����");
		jlb2 = new JLabel("��    �룺");
		jlb3 = new JLabel("Ȩ    �ޣ�");

		jtf = new JTextField(15);
		jpf = new JPasswordField(15);
		// ���뵽JPanel��
		jp1.add(jlb1);
		jp1.add(jtf);

		jp2.add(jlb2);
		jp2.add(jpf);

		jp3.add(jlb3);
		jp3.add(jrb1);
		jp3.add(jrb2);
		jp3.add(jrb3);

		jp4.add(jb1);
		jp4.add(jb2);

		// ����JFrame��
		this.add(jp1);
		this.add(jp2);
		this.add(jp3);
		this.add(jp4);
		// ���ò��ֹ�����
		this.setLayout(new GridLayout(4, 1, 10, 1));
		// ���������ñ���
		this.setTitle("��������ƽ̨");
		// ���ô����С
		this.setSize(400, 300);
		// ���ô����ʼλ��
		this.setLocation(700, 300);
		// ���õ��رմ���ʱ����֤JVMҲ�˳�
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// ��ʾ����
		this.setVisible(true);
		this.setResizable(true);

	}

	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand() == "��¼") {
			if (jrb1.isSelected()) {
				gonglogin(fm);
			} else if (jrb2.isSelected()) {
				shanglogin(sm);
			} else if (jrb3.isSelected()) {
				gulogin(ctm);
			}

		} else if (e.getActionCommand() == "ע��") {
			dispose();
			clear();
			new Zhuce(cdm, ctm, sm, fm);
		}

	}

	// �̼ҵ�¼�жϷ���
	@SuppressWarnings("deprecation")
	public void shanglogin(SellerManagement sm) {
		String name = jtf.getText();
		String password = jpf.getText();

		if (jtf.getText().isEmpty() && jpf.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "�������û��������룡", "��ʾ��Ϣ",
					JOptionPane.WARNING_MESSAGE);
		} else if (jtf.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "�������û�����", "��ʾ��Ϣ",
					JOptionPane.WARNING_MESSAGE);
		} else if (jpf.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "���������룡", "��ʾ��Ϣ",
					JOptionPane.WARNING_MESSAGE);
		} else {
			if (sm.LogIn(name, password)) {
				JOptionPane.showMessageDialog(null, "��¼�ɹ���", "��ʾ��Ϣ",
						JOptionPane.WARNING_MESSAGE);
				clear();
				dispose();
				new MerchantWindows(cdm,ctm,sm,fm);
			}
			else {
				JOptionPane.showMessageDialog(null, "�û��������������\n����������",
						"��ʾ��Ϣ", JOptionPane.ERROR_MESSAGE);
				// ��������
				clear();
			}
		}
	}

	// ������¼�жϷ���
	@SuppressWarnings("deprecation")
	public void gonglogin(FactoryManagement fm) {
		String name = jtf.getText();
		String password = jpf.getText();

		if (jtf.getText().isEmpty() && jpf.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "�������û��������룡", "��ʾ��Ϣ",
					JOptionPane.WARNING_MESSAGE);
		} else if (jtf.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "�������û�����", "��ʾ��Ϣ",
					JOptionPane.WARNING_MESSAGE);
		} else if (jpf.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "���������룡", "��ʾ��Ϣ",
					JOptionPane.WARNING_MESSAGE);
		} else {
			if (fm.LogIn(name, password)) {
				JOptionPane.showMessageDialog(null, "��¼�ɹ���", "��ʾ��Ϣ",
						JOptionPane.WARNING_MESSAGE);
				clear();
				dispose();
				new FactoryWindows(cdm,ctm,sm,fm);
			}
			else {
				JOptionPane.showMessageDialog(null, "�û��������������\n����������",
						"��ʾ��Ϣ", JOptionPane.ERROR_MESSAGE);
				// ��������
				clear();
			}
		}
	}

	// �˿͵�¼�жϷ���
	@SuppressWarnings("deprecation")
	public void gulogin(CustomerManagement ctm) {
		String name = jtf.getText();
		String password = jpf.getText();

		if (jtf.getText().isEmpty() && jpf.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "�������û��������룡", "��ʾ��Ϣ",
					JOptionPane.WARNING_MESSAGE);
		} else if (jtf.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "�������û�����", "��ʾ��Ϣ",
					JOptionPane.WARNING_MESSAGE);
		} else if (jpf.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "���������룡", "��ʾ��Ϣ",
					JOptionPane.WARNING_MESSAGE);
		} else {
			if (ctm.LogIn(name, password)) {
				JOptionPane.showMessageDialog(null, "��¼�ɹ���", "��ʾ��Ϣ",
						JOptionPane.WARNING_MESSAGE);
				clear();
				dispose();
				new CustomerWindows(cdm,ctm,sm,fm);
			}

			else {
				JOptionPane.showMessageDialog(null, "�û��������������\n����������",
						"��ʾ��Ϣ", JOptionPane.ERROR_MESSAGE);
				// ��������
				clear();
			}
		}
	}

	// ����ı���������
	public void clear() {
		jtf.setText("");
		jpf.setText("");
	}

}
