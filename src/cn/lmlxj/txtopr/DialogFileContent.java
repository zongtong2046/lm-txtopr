package cn.lmlxj.txtopr;

import java.awt.Dimension;
import java.awt.Frame;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import cn.lmlxj.txtopr.util.FileOprUtils;

@SuppressWarnings("serial")
public class DialogFileContent extends JDialog {
	final JTextField txtFile = new JTextField();
	final JTextArea txtArea = new JTextArea();
	public DialogFileContent(Frame parent) {
		super(parent, true);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		//
		txtFile.setBounds(15, 15, 570, 30);
		panel.add(txtFile);
		
		JScrollPane jScroll = new JScrollPane(txtArea);
		jScroll.setBounds(15, 55, 570, 310);
		panel.add(jScroll);
		
		add(panel);
		
		Dimension screenSize = getToolkit().getScreenSize();
		int w = 600, h = 400;
		setBounds((screenSize.width - w) / 2, (screenSize.height - h) / 2, w, h);
		setSize(w, h);
		setTitle("文件内容查看");
	}
	
	public void loadFileContent(String filePath) {
		txtFile.setText(filePath);
		txtArea.setText(FileOprUtils.getFileContent(new File(filePath)));
	}
}
