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

	// 定义组件
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

		jb1 = new JButton("确定");
		jb1.addActionListener(this);

		jb2 = new JButton("重置");
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
		jp5 = new JPanel();

		jlb1 = new JLabel("用户名：            ");
		jlb2 = new JLabel("密    码：             ");
		jlb3 = new JLabel("再次输入密码：");
		jlb4 = new JLabel("选择角色类型：");

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

		// 加入JFrame中
		this.add(jp1);
		this.add(jp2);
		this.add(jp3);
		this.add(jp4);
		this.add(jp5);

		// 设置布局管理器
		this.setLayout(new GridLayout(5, 1, 10, 10));
		jp1.setLayout(new FlowLayout(FlowLayout.LEFT));
		jp2.setLayout(new FlowLayout(FlowLayout.LEFT));
		jp3.setLayout(new FlowLayout(FlowLayout.LEFT));
		// 给窗口设置标题
		this.setTitle("新用户注册");
		// 设置窗体大小
		this.setSize(400, 300);
		// 设置窗体初始位置
		this.setLocation(700, 300);
		// 显示窗体
		this.setVisible(true);
		this.setResizable(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "确定") {
			try {
				if(jrb1.isSelected())  
	            {  
	                  gongregister(fm);  
	            }else if(jrb2.isSelected()) //学生在登录系统  
	            {  
	                  shangregister(sm);  
	            }else if(jrb3.isSelected()){
	            	guregister(ctm);
	            }
			} catch (NoSuchAlgorithmException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
		}
		if (e.getActionCommand() == "重置") {
			clear();
		}
	}

	// 注册是否成功判断方法
	@SuppressWarnings("deprecation")
	public void gongregister(FactoryManagement fm)
			throws NoSuchAlgorithmException, SQLException {
		if (jpf1.getText().equals("") || jtf1.getText().equals("")
				|| jpf2.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "输入信息为空！", "提示消息",
					JOptionPane.WARNING_MESSAGE);
		} else if (jpf1.getText().equals(jpf2.getText())) {
			int temp = fm.addFactory(jtf1.getText(), jpf1.getText());
			if (temp == 0) {
				JOptionPane.showMessageDialog(null, "注册成功！", "提示消息",
						JOptionPane.WARNING_MESSAGE);
				dispose();
				new Login(cdm, ctm, sm, fm);
			} else if (temp == 1) {
				clear();
				JOptionPane.showMessageDialog(null, "该用户已存在！", "提示消息",
						JOptionPane.WARNING_MESSAGE);
			} else {
				clear();
				JOptionPane.showMessageDialog(null, "注册失败！", "提示消息",
						JOptionPane.WARNING_MESSAGE);
			}
			// 连接数据库写入用户信息，加判断用户名是否重复
		} else {
			clear();
			JOptionPane.showMessageDialog(null, "信息填写有误！", "提示消息",
					JOptionPane.WARNING_MESSAGE);
		}
	}
	
	@SuppressWarnings("deprecation")
	public void shangregister(SellerManagement sm)
			throws NoSuchAlgorithmException, SQLException {
		if (jpf1.getText().equals("") || jtf1.getText().equals("")
				|| jpf2.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "输入信息为空！", "提示消息",
					JOptionPane.WARNING_MESSAGE);
		} else if (jpf1.getText().equals(jpf2.getText())) {
			int temp = sm.addSeller(jtf1.getText(), jpf1.getText());
			if (temp == 0) {
				JOptionPane.showMessageDialog(null, "注册成功！", "提示消息",
						JOptionPane.WARNING_MESSAGE);
				dispose();
				new Login(cdm, ctm, sm, fm);
			} else if (temp == 1) {
				clear();
				JOptionPane.showMessageDialog(null, "该用户已存在！", "提示消息",
						JOptionPane.WARNING_MESSAGE);
			} else {
				clear();
				JOptionPane.showMessageDialog(null, "注册失败！", "提示消息",
						JOptionPane.WARNING_MESSAGE);
			}
			// 连接数据库写入用户信息，加判断用户名是否重复
		} else {
			clear();
			JOptionPane.showMessageDialog(null, "信息填写有误！", "提示消息",
					JOptionPane.WARNING_MESSAGE);
		}
	}
	
	@SuppressWarnings("deprecation")
	public void guregister(CustomerManagement ctm)
			throws NoSuchAlgorithmException, SQLException {
		if (jpf1.getText().equals("") || jtf1.getText().equals("")
				|| jpf2.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "输入信息为空！", "提示消息",
					JOptionPane.WARNING_MESSAGE);
		} else if (jpf1.getText().equals(jpf2.getText())) {
			int temp = ctm.addCustomer(jtf1.getText(), jpf1.getText());
			if (temp == 0) {
				JOptionPane.showMessageDialog(null, "注册成功！", "提示消息",
						JOptionPane.WARNING_MESSAGE);
				dispose();
				new Login(cdm, ctm, sm, fm);
			} else if (temp == 1) {
				clear();
				JOptionPane.showMessageDialog(null, "该用户已存在！", "提示消息",
						JOptionPane.WARNING_MESSAGE);
			} else {
				clear();
				JOptionPane.showMessageDialog(null, "注册失败！", "提示消息",
						JOptionPane.WARNING_MESSAGE);
			}
			// 连接数据库写入用户信息，加判断用户名是否重复
		} else {
			clear();
			JOptionPane.showMessageDialog(null, "信息填写有误！", "提示消息",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	public void clear() {
		jtf1.setText("");
		jpf1.setText("");
		jpf2.setText("");
	}
}
