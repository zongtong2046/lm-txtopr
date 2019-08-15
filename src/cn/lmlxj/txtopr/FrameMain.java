package cn.lmlxj.txtopr;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.prefs.Preferences;
import java.util.regex.Pattern;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import cn.lmlxj.txtopr.util.FileOprUtils;
import cn.lmlxj.txtopr.util.StringUtils;
import cn.lmlxj.txtopr.util.TxtRegUtils;

public class FrameMain extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final String ICON_QUESTION = "src/assets/question16x16.png";
	private static final String STR_FINDONE = "查找第一个";
	private static final String STR_FINDALL = "查找全部";
	private static final String STR_REPLACEONE = "替换第一个";
	private static final String STR_REPLACEALL = "替换全部";
	private static final String SPLIT_CHAR = "@#@";
	
	final JFileChooser jfc = new JFileChooser();
	
	final JButton btnIconFile = new JButton(new ImageIcon(ICON_QUESTION));
	final JButton btnIconSrch = new JButton(new ImageIcon(ICON_QUESTION));
	final JButton btnIconReplace = new JButton(new ImageIcon(ICON_QUESTION));
	final JButton btnFindOne = new JButton(STR_FINDONE);
	final JButton btnFindAll = new JButton(STR_FINDALL);
	final JButton btnReplaceOne = new JButton(STR_REPLACEONE);
	final JButton btnReplaceAll = new JButton(STR_REPLACEALL);
	
	final JTextField txtFolder = new JTextField();
	final JTextField txtCost = new JTextField();
	
	final JComboBox<String> cmbFile = new JComboBox<String>();
	final JComboBox<String> cmbSearch = new JComboBox<String>();
	final JComboBox<String> cmbReplace = new JComboBox<String>();
	
	final JCheckBox chkFileReg = new JCheckBox("正则匹配", false);
	final JCheckBox chkSrchReg = new JCheckBox("正则匹配", false);
	final JCheckBox chkSrchCase = new JCheckBox("大小写敏感", false);
	
	final DefaultListModel<String> listModel = new DefaultListModel<String>();
	final JList<String> jList = new JList<String>(listModel);
	
	final Preferences preferences = Preferences.userRoot().node(getClass().getName());
	DialogFileContent dialogFileContent = null;
	
	public FrameMain() {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		
		final JLabel lbl1 = new JLabel("选择文件夹：");
		final JLabel lbl2 = new JLabel("文件过滤符：");
		final JLabel lbl3 = new JLabel("查找字符串：");
		final JLabel lbl4 = new JLabel("替换字符串：");
		final JLabel lbl5 = new JLabel("处理结果：");
		
		final JButton btnFolder = new JButton("...");
		
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		// line-1 
		lbl1.setBounds(20, 20, 80, 30);
		panel.add(lbl1);
		txtFolder.setBounds(100, 20, 590, 30);
		panel.add(txtFolder);
		btnFolder.setBounds(690, 20, 60, 30);
		btnFolder.addActionListener(new BtnFolderHandler());
		panel.add(btnFolder);
		
		// line-2
		lbl2.setBounds(20, 60, 80, 30);
		panel.add(lbl2);
		cmbFile.setBounds(100, 60, 560, 30);
		cmbFile.setEditable(true);
		panel.add(cmbFile);
		btnIconFile.setBounds(660, 60, 30, 30);
		btnIconFile.setFocusable(false);
		btnIconFile.addActionListener(new BtnIconHandler());
		panel.add(btnIconFile);
		
		// line3
		lbl3.setBounds(20, 100, 80, 30);
		panel.add(lbl3);
		cmbSearch.setBounds(100, 100, 560, 30);
		cmbSearch.setEditable(true);
		panel.add(cmbSearch);
		btnIconSrch.setBounds(660, 100, 30, 30);
		btnIconSrch.addActionListener(new BtnIconHandler());
		panel.add(btnIconSrch);
		
		// line4
		lbl4.setBounds(20, 140, 80, 30);
		panel.add(lbl4);
		cmbReplace.setBounds(100, 140, 560, 30);
		cmbReplace.setEditable(true);
		panel.add(cmbReplace);
		btnIconReplace.setBounds(660, 140, 30, 30);
		btnIconReplace.addActionListener(new BtnIconHandler());
		panel.add(btnIconReplace);		
		
		// checkbox
		chkFileReg.setBounds(690, 60, 90, 30);
		chkFileReg.setEnabled(false);
		panel.add(chkFileReg);		
		chkSrchReg.setBounds(690, 100, 90, 30);
		panel.add(chkSrchReg);
		chkSrchCase.setBounds(690, 140, 100, 30);
		panel.add(chkSrchCase);
		
		// btns
		btnFindOne.setBounds(540, 180, 120, 30);
		btnFindOne.addActionListener(new BtnSrchReplaceHandler());
		panel.add(btnFindOne);
		btnFindAll.setBounds(660, 180, 120, 30);
		btnFindAll.addActionListener(new BtnSrchReplaceHandler());
		panel.add(btnFindAll);
		btnReplaceOne.setBounds(540, 210, 120, 30);
		btnReplaceOne.addActionListener(new BtnSrchReplaceHandler());
		panel.add(btnReplaceOne);
		btnReplaceAll.setBounds(660, 210, 120, 30);
		btnReplaceAll.addActionListener(new BtnSrchReplaceHandler());
		panel.add(btnReplaceAll);
		
		// line5
		lbl5.setBounds(20, 210, 80, 30);
		panel.add(lbl5);
		
		txtCost.setBounds(100, 210, 420, 30);
		txtCost.setEnabled(false);
		panel.add(txtCost);
		
		// JSeparator
		JSeparator sep = new JSeparator(JSeparator.HORIZONTAL);
		sep.setBounds(20, 95, 760, 10);
		panel.add(sep);
		JSeparator sep2 = new JSeparator(JSeparator.HORIZONTAL);
		sep2.setBounds(20, 245, 760, 10);
		panel.add(sep2);
		
		// JTextArea
		JScrollPane jScroll = new JScrollPane(jList);
		jScroll.setBounds(20, 255, 760, 210);
		panel.add(jScroll);
		jList.addMouseListener(new MouseAdapter() {
			@SuppressWarnings({ "unchecked" })
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					String filePath = listModel.getElementAt(((JList<String>)e.getSource()).getSelectedIndex());
					if (filePath.indexOf(". ") > 0) {
						filePath = filePath.substring(filePath.indexOf(". ") + 2);
					}
					dialogFileContent= new DialogFileContent(FrameMain.this);
					dialogFileContent.loadFileContent(filePath);
					dialogFileContent.setVisible(true);
				}
			}
		});
		//
		String lastPath = preferences.get("lastPath", "");
		if (!StringUtils.isEmpty(lastPath)) {
			jfc.setCurrentDirectory(new File(lastPath));
			txtFolder.setText(lastPath);
		}
		
		setComboItems(cmbFile, preferences.get("txtFile", ""));
		setComboItems(cmbSearch, preferences.get("txtSearch", ""));
		setComboItems(cmbReplace, preferences.get("txtReplace", ""));
		
		Dimension screenSize = getToolkit().getScreenSize();
		add(panel);
		
		int w = 800, h = 500;
		setBounds((screenSize.width - w) / 2, (screenSize.height - h) / 2, w, h);
		setTitle("乡台瑞猿--文本手术刀");
		setSize(w, h);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(3);
		
		// 参数回写
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				//
				preferences.put("lastPath", txtFolder.getText());
				preferences.put("txtFile", getComboItems(cmbFile));
				preferences.put("txtSearch", getComboItems(cmbSearch));
				preferences.put("txtReplace", getComboItems(cmbReplace));
			}
		});
	}
	
	private String getComboItems(JComboBox<String> cmb) {
		int count = cmb.getItemCount();
		StringBuilder sb = new StringBuilder();
		if (count > 0) {
			for(int i = 0;i<count; i++) {
				sb.append(cmb.getItemAt(i)).append(SPLIT_CHAR);
			}
		}
		if (sb.length() > 0 ) {
			sb.delete(sb.length() - 3, sb.length());
		}
		return sb.toString();
	}
	
	private void setComboItems(JComboBox<String> cmb, String items) {
		if(! StringUtils.isEmpty(items)) {
			String[] arrItems = items.split(SPLIT_CHAR);
			for(String item : arrItems) {
				if (! StringUtils.isEmpty(item)) {
					cmb.addItem(item);
				}
			}
		}
		if (cmb.getItemCount() > 0) cmb.setSelectedIndex(0);
	}
	
	private class BtnFolderHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (jfc.showOpenDialog(null) != 1) {
				File f = jfc.getSelectedFile();
				String path = f.getAbsolutePath();
				txtFolder.setText(path);
				if (path.lastIndexOf("\\") > 0) {
					txtFolder.setText(String.valueOf(path.substring(0, path.lastIndexOf("\\"))) + "\\"
							+ path.substring(path.lastIndexOf("\\") + 1));
				}
			}
		}
	}
	
	private class BtnSrchReplaceHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String strFolder = txtFolder.getText();
			String strFile = cmbFile.getEditor().getItem()!=null ? cmbFile.getEditor().getItem().toString() : "";
			String strSrch = cmbSearch.getEditor().getItem()!=null ? cmbSearch.getEditor().getItem().toString() : "";
			String strReplace = cmbReplace.getEditor().getItem()!=null ? cmbReplace.getEditor().getItem().toString() : "";
			int oprFlag = 0;
			long timeStart = new Date().getTime();
			if (StringUtils.isEmpty(strFolder) || StringUtils.isEmpty(strFile) || StringUtils.isEmpty(strSrch)) {
				JOptionPane.showMessageDialog(btnIconFile.getParent(), "目标文件夹,文件匹配符,查找字符串不能为空");
				return;
			}
			//
			reIndexComboxItem(cmbFile);
			reIndexComboxItem(cmbSearch);
			reIndexComboxItem(cmbReplace);
			//
			
			if (e.getSource() == btnFindOne) {	// find one
				oprFlag = oprFlag | FileOprUtils.OPR_FIRST | FileOprUtils.OPR_SRCH;
			} else if (e.getSource() == btnFindAll) {	// find all
				oprFlag = oprFlag | FileOprUtils.OPR_SRCH;
			} else if (e.getSource() == btnReplaceOne) {
				oprFlag = oprFlag | FileOprUtils.OPR_FIRST | FileOprUtils.OPR_REPLACE;
			} else if (e.getSource() == btnReplaceAll) {
				oprFlag = oprFlag | FileOprUtils.OPR_REPLACE;
			}
			if (chkSrchReg.isSelected()) oprFlag = oprFlag | FileOprUtils.OPR_FLAG_REG;
			if (chkSrchCase.isSelected()) oprFlag = oprFlag | FileOprUtils.OPR_FLAG_CASE;
			
			try {
				listModel.clear();
				txtCost.setText("");
				Pattern filePattern = TxtRegUtils.getFileRegPattern(strFile);
				//
				System.out.println("strfolder: " + strFolder);
				//
				List<String> fileList = FileOprUtils.scanFile(filePattern, strFolder, strSrch, strReplace, oprFlag);
				for(int i = 0, l = fileList.size(); i<l; i++) {
					listModel.addElement((i+1) + ". " + fileList.get(i));
				}
				txtCost.setText(StringUtils.format("共找到替换：{0}个文件，耗时：{1}豪妙", fileList.size(), 
						new Date().getTime() - timeStart));
			} catch (Exception exp) {
				JOptionPane.showMessageDialog(FrameMain.this, exp.getMessage());
			}
		}
	}
	
	private class BtnIconHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnIconFile) {
				JOptionPane.showMessageDialog(btnIconFile.getParent(), "*表示所有字符,?表示单个字符,多个文件格式以英文逗号分隔,如:*.jsp,*.js");
			} else if (e.getSource() == btnIconSrch || e.getSource() == btnIconReplace) {
				JOptionPane.showMessageDialog(btnIconFile.getParent(), "正常情况: 基于行查找;a+b表示同时包含a,b;a|b只要包含a或b即可\r\n"
						+ "正则匹配: 按java流派正常表达式处理.");
			}
		}
	}
	
	private void reIndexComboxItem(JComboBox<String> cmb) {
		String strItem = cmb.getEditor().getItem()!=null ? cmb.getEditor().getItem().toString() : "";
		if (! StringUtils.isEmpty(strItem)) {
			System.out.println("stritem: " + strItem);
			int count = cmb.getItemCount();
			for(int i = 0; i<count; i++) {
				if (strItem.equals(cmb.getItemAt(i))) {
					cmb.removeItemAt(i);
					break;
				}
			}
			cmb.insertItemAt(strItem, 0);
			cmb.setSelectedIndex(0);				
		}
		// 最多10个，超出截掉
		int count = cmb.getItemCount();
		if (count > 10) {
			cmb.removeItemAt(count-1);
		}
	}

	public static void main(String[] args) {
		new FrameMain();
	}
}