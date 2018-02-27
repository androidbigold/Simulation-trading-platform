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

	// 定义组件
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

		// 创建组件
		jb1 = new JButton("登录");
		jb2 = new JButton("注册");
		// 设置监听
		jb1.addActionListener(this);
		jb2.addActionListener(this);

		jrb1 = new JRadioButton("工厂");
		jrb2 = new JRadioButton("商家");
		jrb3 = new JRadioButton("顾客");
		bg = new ButtonGroup();
		bg.add(jrb1);
		bg.add(jrb2);
		bg.add(jrb3);
		jrb1.setSelected(true);// 初始位置的选择

		jp1 = new JPanel();
		jp2 = new JPanel();
		jp3 = new JPanel();
		jp4 = new JPanel();

		jlb1 = new JLabel("用户名：");
		jlb2 = new JLabel("密    码：");
		jlb3 = new JLabel("权    限：");

		jtf = new JTextField(15);
		jpf = new JPasswordField(15);
		// 加入到JPanel中
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

		// 加入JFrame中
		this.add(jp1);
		this.add(jp2);
		this.add(jp3);
		this.add(jp4);
		// 设置布局管理器
		this.setLayout(new GridLayout(4, 1, 10, 1));
		// 给窗口设置标题
		this.setTitle("电子商务平台");
		// 设置窗体大小
		this.setSize(400, 300);
		// 设置窗体初始位置
		this.setLocation(700, 300);
		// 设置当关闭窗口时，保证JVM也退出
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 显示窗体
		this.setVisible(true);
		this.setResizable(true);

	}

	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand() == "登录") {
			if (jrb1.isSelected()) {
				gonglogin(fm);
			} else if (jrb2.isSelected()) {
				shanglogin(sm);
			} else if (jrb3.isSelected()) {
				gulogin(ctm);
			}

		} else if (e.getActionCommand() == "注册") {
			dispose();
			clear();
			new Zhuce(cdm, ctm, sm, fm);
		}

	}

	// 商家登录判断方法
	@SuppressWarnings("deprecation")
	public void shanglogin(SellerManagement sm) {
		String name = jtf.getText();
		String password = jpf.getText();

		if (jtf.getText().isEmpty() && jpf.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "请输入用户名和密码！", "提示消息",
					JOptionPane.WARNING_MESSAGE);
		} else if (jtf.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "请输入用户名！", "提示消息",
					JOptionPane.WARNING_MESSAGE);
		} else if (jpf.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "请输入密码！", "提示消息",
					JOptionPane.WARNING_MESSAGE);
		} else {
			if (sm.LogIn(name, password)) {
				JOptionPane.showMessageDialog(null, "登录成功！", "提示消息",
						JOptionPane.WARNING_MESSAGE);
				clear();
				dispose();
				new MerchantWindows(cdm,ctm,sm,fm);
			}
			else {
				JOptionPane.showMessageDialog(null, "用户名或者密码错误！\n请重新输入",
						"提示消息", JOptionPane.ERROR_MESSAGE);
				// 清空输入框
				clear();
			}
		}
	}

	// 工厂登录判断方法
	@SuppressWarnings("deprecation")
	public void gonglogin(FactoryManagement fm) {
		String name = jtf.getText();
		String password = jpf.getText();

		if (jtf.getText().isEmpty() && jpf.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "请输入用户名和密码！", "提示消息",
					JOptionPane.WARNING_MESSAGE);
		} else if (jtf.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "请输入用户名！", "提示消息",
					JOptionPane.WARNING_MESSAGE);
		} else if (jpf.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "请输入密码！", "提示消息",
					JOptionPane.WARNING_MESSAGE);
		} else {
			if (fm.LogIn(name, password)) {
				JOptionPane.showMessageDialog(null, "登录成功！", "提示消息",
						JOptionPane.WARNING_MESSAGE);
				clear();
				dispose();
				new FactoryWindows(cdm,ctm,sm,fm);
			}
			else {
				JOptionPane.showMessageDialog(null, "用户名或者密码错误！\n请重新输入",
						"提示消息", JOptionPane.ERROR_MESSAGE);
				// 清空输入框
				clear();
			}
		}
	}

	// 顾客登录判断方法
	@SuppressWarnings("deprecation")
	public void gulogin(CustomerManagement ctm) {
		String name = jtf.getText();
		String password = jpf.getText();

		if (jtf.getText().isEmpty() && jpf.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "请输入用户名和密码！", "提示消息",
					JOptionPane.WARNING_MESSAGE);
		} else if (jtf.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "请输入用户名！", "提示消息",
					JOptionPane.WARNING_MESSAGE);
		} else if (jpf.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "请输入密码！", "提示消息",
					JOptionPane.WARNING_MESSAGE);
		} else {
			if (ctm.LogIn(name, password)) {
				JOptionPane.showMessageDialog(null, "登录成功！", "提示消息",
						JOptionPane.WARNING_MESSAGE);
				clear();
				dispose();
				new CustomerWindows(cdm,ctm,sm,fm);
			}

			else {
				JOptionPane.showMessageDialog(null, "用户名或者密码错误！\n请重新输入",
						"提示消息", JOptionPane.ERROR_MESSAGE);
				// 清空输入框
				clear();
			}
		}
	}

	// 清空文本框和密码框
	public void clear() {
		jtf.setText("");
		jpf.setText("");
	}

}
