import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;

public class ServerGUI {

	JFrame frame;
	private JTextField textField;
	static ServerGUI window;
	JLabel lblNewLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new ServerGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ServerGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("Start File Server");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//JOptionPane.showMessageDialog(null, "Starting FileServer");
				lblNewLabel.setText("Starting file server...");
				String path="C:\\Users\\Himanshu\\DTNMessages\\";
				
				try{
					new FileServerThread(textField.getText(),lblNewLabel).start();
				//FIleServer.start(textField.getText());
				}
				catch(Exception e)
				{
					System.out.println("Exception:"+e);
					lblNewLabel.setText("Couldn't file server...");
				}
				
			}
		});
		btnNewButton.setBounds(114, 156, 166, 25);
		frame.getContentPane().add(btnNewButton);
		
		textField = new JTextField();
		textField.setBounds(186, 57, 166, 25);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(26, 224, 339, 16);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Path to store files:");
		lblNewLabel_1.setFont(new Font("Arial Black", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(18, 61, 156, 16);
		frame.getContentPane().add(lblNewLabel_1);
	
		lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(26, 224, 339, 16);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_2 = new JLabel("Connections:");
		//lblNewLabel_2.setFont(new Font("Arial Black", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(10, 10, 400, 16);
		frame.getContentPane().add(lblNewLabel_2);
		
		
	}
}
