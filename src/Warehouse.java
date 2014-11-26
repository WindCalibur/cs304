// We need to import the java.sql package to use JDBC
import java.sql.*;

// for reading from the command line
import java.io.*;

// for the login window
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class Warehouse {
	private Connection con;
	private JFrame mainFrame;
	private String currentUser = null;
	private JTextField usernameField;
    private JPasswordField passwordField;
    private JPanel contentPane;
    private JPanel customerContentPane;
    private JPanel onlineOrderPane;
    private JPanel clerkPane;
    private JPanel managerPane;
    
    //Registering fields
    private JTextField name;
    private JTextField address;
    private JTextField phoneNumber;
    private JTextField loginID;
    private JPasswordField password;
    
    //Return fields
    private JTextField 	date;
    private JTextField  recept_ID;
    
    //Add item fields
    private JTextField upc;
    private JTextField qty;
    private JTextField price;
    
    //Daily report fields
    private JTextField reportDate;
    private JTable dailyReportTable;
    private JTextField n;
    private JTable topReportTable;
    
    //Online Order
    private JTextField category;
    private JTextField title;
    private JTextField leadSinger;
    private JTextField qtyOnline;
    private JTable onlineOrderTable;
    
    
  
    
	public Warehouse(){
		
		mainFrame = new JFrame("Warehouse");
		contentPane = new JPanel();
		mainFrame.setContentPane(contentPane);
		GridBagLayout gb = new GridBagLayout();
	    GridBagConstraints c = new GridBagConstraints();

	    contentPane.setLayout(gb);
	    contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		JButton customerButton = new JButton("Customer");
		JButton clerkButton = new JButton("Clerk");
		JButton managerButton = new JButton("Manager");
		
		contentPane.add(customerButton);
		contentPane.add(clerkButton);
		contentPane.add(managerButton);
		
		customerButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				loadCustomerContentPane();
			}
			
		});
		
		clerkButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				loadClerkContent();
				
			}
			
		});
		
		managerButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				loadManagerContent();
				
			}
			
		});
		
		
		
		
		
		
		
		
		mainFrame.addWindowListener(new WindowAdapter() 
	      {
		public void windowClosing(WindowEvent e) 
		{ 
		  System.exit(0); 
		}
	      });
		
		 // size the window to obtain a best fit for the components
	      mainFrame.pack();

	      // center the frame
	      Dimension d = mainFrame.getToolkit().getScreenSize();
	      Rectangle r = mainFrame.getBounds();
	      mainFrame.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );

	      // make the window visible
	      mainFrame.setVisible(true);
		
	//driver setup
	try 
	      {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
    	  
   	  
      }
      catch (SQLException ex)
      {
    	  System.out.println("Message: " + ex.getMessage());
    	  System.exit(-1);
      }
	//driver setup end
    }
	private void loadCustomerContentPane(){
		if(customerContentPane == null){
			customerContentPane = new JPanel();
			mainFrame.setContentPane(customerContentPane);
			JButton registeredButton = new JButton("Registered User");
			JButton registerButton = new JButton("Sign Up");
			JButton goBack = new JButton("User Selection Screen");
			
			customerContentPane.add(registeredButton);
			customerContentPane.add(registerButton);
			customerContentPane.add(goBack);
			
			registeredButton.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					JPanel customerLogin = new JPanel();
					mainFrame.setContentPane(customerLogin);
					GridBagLayout gb2 = new GridBagLayout();
				    GridBagConstraints c2 = new GridBagConstraints();
					usernameField = new JTextField(10);
				    passwordField = new JPasswordField(10);
				    passwordField.setEchoChar('*');
				    JLabel usernameLabel = new JLabel("Enter username: ");
				    JLabel passwordLabel = new JLabel("Enter password: ");
				    JButton loginButton = new JButton("Log In");
				     // place the username label 
				      c2.gridwidth = GridBagConstraints.REMAINDER;
				      c2.insets = new Insets(10, 10, 5, 0);
				      gb2.setConstraints(usernameLabel, c2);
				      customerLogin.add(usernameLabel);

				      // place the text field for the username 
				      c2.gridwidth = GridBagConstraints.REMAINDER;
				      c2.insets = new Insets(10, 0, 5, 10);
				      gb2.setConstraints(usernameField, c2);
				      customerLogin.add(usernameField);

				      // place password label
				      c2.gridwidth = GridBagConstraints.REMAINDER;
				      c2.insets = new Insets(0, 10, 10, 0);
				      gb2.setConstraints(passwordLabel, c2);
				      customerLogin.add(passwordLabel);

				      // place the password field 
				      c2.gridwidth = GridBagConstraints.REMAINDER;
				      c2.insets = new Insets(0, 0, 10, 10);
				      gb2.setConstraints(passwordField, c2);
				      customerLogin.add(passwordField);

				      // place the login button
				      c2.gridwidth = GridBagConstraints.REMAINDER;
				      c2.insets = new Insets(5, 10, 10, 10);
				      c2.anchor = GridBagConstraints.CENTER;
				      gb2.setConstraints(loginButton, c2);
				      customerLogin.add(loginButton);
				      
				     
				      loginButton.addActionListener(new ActionListener(){

						@Override
						public void actionPerformed(ActionEvent arg0) {
							int i = authenticate(usernameField.getText(),passwordField.getPassword());
							if (i== 0){
								loadOnlineOrder();
							}
							else{
								JOptionPane.showMessageDialog(null, "Login Error");
								loadCustomerContentPane();
							}
								
						}
				    	  
				      });
				      mainFrame.pack();
				      Dimension d = mainFrame.getToolkit().getScreenSize();
					  Rectangle r = mainFrame.getBounds();
					  mainFrame.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );
				      SwingUtilities.updateComponentTreeUI(mainFrame);
				}
				
			});
			
			registerButton.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					JPanel registerPane = new JPanel();
					name = new JTextField(10);
					address = new JTextField(10);
					phoneNumber = new JTextField(10);
					loginID = new JTextField(10);
					password = new JPasswordField(10);
					password.setEchoChar('*');
					//labels
					JLabel nameLabel = new JLabel("Enter Name: ");
					JLabel addressLabel = new JLabel("Enter Address: ");
					JLabel phoneLabel = new JLabel("Enter Phone Number: ");
					JLabel iDLabel = new JLabel("Enter Login ID: ");
					JLabel passwordLabel = new JLabel("Enter Password: ");
					
					registerPane.add(nameLabel);
					registerPane.add(name);
					registerPane.add(addressLabel);
					registerPane.add(address);
					registerPane.add(phoneLabel);
					registerPane.add(phoneNumber);
					registerPane.add(iDLabel);
					registerPane.add(loginID);
					registerPane.add(passwordLabel);
					registerPane.add(password);
					
					JButton register = new JButton("Register");
					register.addActionListener(new ActionListener(){

						@Override
						public void actionPerformed(ActionEvent arg0) {
							int i = registerUser(name.getText(), address.getText(), phoneNumber.getText(), loginID.getText(), password.getPassword());
							if(i == 0){
								JOptionPane.showMessageDialog(null, "Register Successful Please Log In");
								loadCustomerContentPane();
							}else if (i == 1){
								loginID.setText("");
								JOptionPane.showMessageDialog(null, "Login ID already in use");
							}else{
								//error handling?
							}
						}
						
					});
					registerPane.add(register);
					
					JButton goBack = new JButton("Back");
					goBack.addActionListener(new ActionListener(){

						@Override
						public void actionPerformed(ActionEvent arg0) {
							loadCustomerContentPane();
						}
						
					});
					registerPane.add(goBack);
					mainFrame.setContentPane(registerPane);
					mainFrame.pack();
					Dimension d = mainFrame.getToolkit().getScreenSize();
				    Rectangle r = mainFrame.getBounds();
				    mainFrame.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );
					SwingUtilities.updateComponentTreeUI(mainFrame);
				}
				
			});
			
			goBack.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					mainFrame.setContentPane(contentPane);
					mainFrame.pack();
					Dimension d = mainFrame.getToolkit().getScreenSize();
				    Rectangle r = mainFrame.getBounds();
				    mainFrame.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );
					SwingUtilities.updateComponentTreeUI(mainFrame);
					
				}
				
			});
			
			mainFrame.pack();
			SwingUtilities.updateComponentTreeUI(mainFrame);
			}
			else{
				mainFrame.setContentPane(customerContentPane);
				mainFrame.pack();
				Dimension d = mainFrame.getToolkit().getScreenSize();
			    Rectangle r = mainFrame.getBounds();
			    mainFrame.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );
				SwingUtilities.updateComponentTreeUI(mainFrame);
			}
		
	}
	
	private void loadClerkContent(){
			clerkPane = new JPanel();
			date = new JTextField(10);
			recept_ID = new JTextField(10);
			JLabel dateLabel = new JLabel("Enter Current Date: ");
			JLabel receptLabel = new JLabel("Enter Recept ID: ");
			
			clerkPane.add(dateLabel);
			clerkPane.add(date);
			clerkPane.add(receptLabel);
			clerkPane.add(recept_ID);
			JButton returnItem = new JButton("Return Item");
			returnItem.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					returnItem(date.getText(), recept_ID.getText());
				}
				
			});
			clerkPane.add(returnItem);
			JButton goBack = new JButton("User Selection Screen");
			goBack.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					mainFrame.setContentPane(contentPane);
					mainFrame.pack();
					SwingUtilities.updateComponentTreeUI(mainFrame);
				}
				
			});
			clerkPane.add(goBack);
			mainFrame.setContentPane(clerkPane);
			mainFrame.pack();
			Dimension d = mainFrame.getToolkit().getScreenSize();
		    Rectangle r = mainFrame.getBounds();
		    mainFrame.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );
			SwingUtilities.updateComponentTreeUI(mainFrame);
			
	}
	
	private void loadManagerContent(){
		if(managerPane == null){
			managerPane = new JPanel();
			JButton addItem = new JButton("Add Item");
			JButton dSR = new JButton("Daily Sales Report");
			JButton tSI = new JButton("Top Selling Item");
			JButton goBack = new JButton("User Selection Screen");
			
			addItem.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					loadAddItem();
				}
				
			});
			dSR.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					loadDailySales();
				}
				
			});
			tSI.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					loadTopSelling();
				}
				
			});
			goBack.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					mainFrame.setContentPane(contentPane);
					mainFrame.pack();
					Dimension d = mainFrame.getToolkit().getScreenSize();
				    Rectangle r = mainFrame.getBounds();
				    mainFrame.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );
					SwingUtilities.updateComponentTreeUI(mainFrame);
				}
				
			});
			
			managerPane.add(addItem);
			managerPane.add(dSR);
			managerPane.add(tSI);
			managerPane.add(goBack);
			mainFrame.setContentPane(managerPane);
			mainFrame.pack();
			Dimension d = mainFrame.getToolkit().getScreenSize();
		    Rectangle r = mainFrame.getBounds();
		    mainFrame.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );
			SwingUtilities.updateComponentTreeUI(mainFrame);
			
		}
			else{
			mainFrame.setContentPane(managerPane);
			mainFrame.pack();
			Dimension d = mainFrame.getToolkit().getScreenSize();
		    Rectangle r = mainFrame.getBounds();
		    mainFrame.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );
			SwingUtilities.updateComponentTreeUI(mainFrame);
			}
		
		
	}
	
	public void loadAddItem(){
		JPanel addItemPane = new JPanel();
		JLabel upcLabel = new JLabel("Enter UPC: ");
		JLabel qtyLabel = new JLabel("Enter Quantity: ");
		JLabel priceLabel =new JLabel("Enter Price(Optional): ");
		upc = new JTextField(10);
		qty = new JTextField(10);
		price = new JTextField(10);
		
		addItemPane.add(upcLabel);
		addItemPane.add(upc);
		addItemPane.add(qtyLabel);
		addItemPane.add(qty);
		addItemPane.add(priceLabel);
		addItemPane.add(price);
		
		JButton addItem = new JButton("Add Item");
		addItem.addActionListener(new ActionListener(){
			

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int i = addItem(upc.getText(), qty.getText(), price.getText());
				if (i == 0){
					
				}else if (i == 1){
					
				}else{
					
				}
			}
		});
		addItemPane.add(addItem);
		JButton goBack = new JButton("back");
		goBack.addActionListener(new ActionListener(){
			

			@Override
			public void actionPerformed(ActionEvent arg0) {
				loadManagerContent();
			}
		});
		addItemPane.add(goBack);
		mainFrame.setContentPane(addItemPane);
		mainFrame.pack();
		Dimension d = mainFrame.getToolkit().getScreenSize();
	    Rectangle r = mainFrame.getBounds();
	    mainFrame.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );
		SwingUtilities.updateComponentTreeUI(mainFrame);
	}
	
	public void loadDailySales(){
		JPanel dailySalesPane = new JPanel();
		dailyReportTable = new JTable();
		dailyReportTable.setSize(400,400);
		JScrollPane sP = new JScrollPane(dailyReportTable);
		JLabel dateLabel = new JLabel("Enter Date of Report: ");
		reportDate = new JTextField(10);
		dailySalesPane.add(dateLabel);
		dailySalesPane.add(reportDate);
		JButton getReport = new JButton("Get Report");
		getReport.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				getDailyReport(reportDate.getText());
				
			}
			
		});
		dailySalesPane.add(getReport);
		JButton goBack = new JButton("back");
		goBack.addActionListener(new ActionListener(){
			

			@Override
			public void actionPerformed(ActionEvent arg0) {
				loadManagerContent();
			}
		});
		dailySalesPane.add(goBack);
		dailySalesPane.add(sP);
		
		mainFrame.setContentPane(dailySalesPane);
		mainFrame.pack();
		Dimension d = mainFrame.getToolkit().getScreenSize();
	    Rectangle r = mainFrame.getBounds();
	    mainFrame.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );
		SwingUtilities.updateComponentTreeUI(mainFrame);
		
	}
	
	public void loadTopSelling(){
		JPanel topSellingPane = new JPanel();
		topReportTable = new JTable();
		topReportTable.setSize(400,400);
		JScrollPane sP = new JScrollPane(topReportTable);
		JLabel dateLabel = new JLabel("Enter Date of Report: ");
		reportDate = new JTextField(10);
		JLabel nLabel = new JLabel("Enter Max Item Type: ");
		n = new JTextField(10);
		topSellingPane.add(dateLabel);
		topSellingPane.add(reportDate);
		topSellingPane.add(nLabel);
		topSellingPane.add(n);
		JButton getReport = new JButton("Get Report");
		getReport.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int i = getTopReport(reportDate.getText(), n.getText());
				if(i == 0){
					
				}else if (i == 1){
					
				}else{
					
				}
				
			}
			
		});
		topSellingPane.add(getReport);
		JButton goBack = new JButton("back");
		goBack.addActionListener(new ActionListener(){
			

			@Override
			public void actionPerformed(ActionEvent arg0) {
				loadManagerContent();
			}
		});
		topSellingPane.add(goBack);
		topSellingPane.add(sP);
		mainFrame.setContentPane(topSellingPane);
		mainFrame.pack();
		Dimension d = mainFrame.getToolkit().getScreenSize();
	    Rectangle r = mainFrame.getBounds();
	    mainFrame.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );
		SwingUtilities.updateComponentTreeUI(mainFrame);
	}
	
	
	//todo
	private void loadOnlineOrder(){
		JPanel onlineOrderPane = new JPanel();
		onlineOrderTable = new JTable();
		onlineOrderTable.setSize(400, 400);
		JScrollPane sP = new JScrollPane(onlineOrderTable);
	    JLabel categoryLabel = new JLabel("Category: ");
	    JLabel titleLabel = new JLabel("Title: ");
	    JLabel leadSingerLabel = new JLabel("Lead Singer: ");
	    JLabel qtyOnlineLabel = new JLabel("Quantity: ");
	    category = new JTextField(10);
		title = new JTextField(10);
		leadSinger = new JTextField(10);
		qtyOnline = new JTextField(10);
		JButton search = new JButton("Search");
		JLabel iidLabel = new JLabel("Choose from iid in Table");
		JTextField iid = new JTextField("Enter iid Here");
		JButton addToShoppingCart = new JButton("Add To Shopping Cart");
		JButton backToCustomerSelect = new JButton("Back to Customer Login");
		
		
		//Add Listeners
		
		
		//Add to Pane
		onlineOrderPane.add(categoryLabel);
		onlineOrderPane.add(category);
		onlineOrderPane.add(titleLabel);
		onlineOrderPane.add(title);
		onlineOrderPane.add(leadSingerLabel);
		onlineOrderPane.add(leadSinger);
		onlineOrderPane.add(qtyOnlineLabel);
		onlineOrderPane.add(qtyOnline);
		onlineOrderPane.add(search);
		onlineOrderPane.add(sP);
		onlineOrderPane.add(iidLabel);
		onlineOrderPane.add(iid);
		onlineOrderPane.add(addToShoppingCart);
		onlineOrderPane.add(backToCustomerSelect);
		
		mainFrame.setContentPane(onlineOrderPane);
		mainFrame.setSize(900, 550);
		Dimension d = mainFrame.getToolkit().getScreenSize();
	    Rectangle r = mainFrame.getBounds();
	    mainFrame.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );
		SwingUtilities.updateComponentTreeUI(mainFrame);
		
		
		
	}
	
	
	//TRANSACTION STUFF, shopping cart still needs implementing. 
	//The idea is to fill a variable at the top with the returned info such as in a JTable for the appropriate view.
	
	
	//return 0 if successful, reutrn 1 if not successful. Also update topReportTable if successful
	private int getTopReport(String date, String n){
		return 0;
	}
	
	//return 0 if successful, return 1 if not successful. Also update dailyReportTable if successful.
	private int getDailyReport(String string){
		return 1;
	}
	
	
	//return 0 if successful, return 1 if not successful
	private int addItem(String upc, String qty, String price ){
		return 1;
	}
	
	//todo return 0 if successful, return 1 if not successful. Also set currentUser field to contain cid for online ordering
	private int authenticate(String user, char[] pass){
		return 0;
	}
	
	//todo return 0 if successful, return 1 if id in use, return > 1 for error handling cases
	private int registerUser(String name, String address, String phone, String id, char[] pass){
		return 1;
	}
	
	//todo 
	private int returnItem(String date, String receptID){
		return 1;
	}
	
	//return all items matching category, title, singer (check if fields is empty string("") for lack of constraint)
	//if only 1 item is returned with the appropriate qty, add it to the shopping cart and throw a dialog box saying you did
	//in the returning method. if insufficient qty, throw a dialog saying 
	private int searchItem(String category, String title, String singer, String qty){
		return 1;
	}
	
	
	
	public static void main(String args[])
    {
      Warehouse w = new Warehouse();
    }
}
