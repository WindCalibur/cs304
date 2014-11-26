// We need to import the java.sql package to use JDBC
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
// for reading from the command line
import java.io.*;


// for the login window
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


import com.mysql.jdbc.exceptions.*;
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;
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
    private JTextField upcOnline;
    private JTable onlineOrderTable;
    
    
    
    //Checkout
    private JTable receiptView;
    private JTextField ccNumber;
    private JTextField ccExpiry;
    private int finalCount;
    
    
    //Process Delivery
    private JTextField processDate;
    private JTextField processID;
    
    
  //Returned query data
  	List<String[]> TopRowData = new ArrayList<String[]>();
  	List<String[]> DailyRowData = new ArrayList<String[]>();
  	String amountReturned;
  	boolean loggedIn = false;
  	List<String[]> shoppingList = new ArrayList<String[]>();
  	List<String[]> OnlineRowData = new ArrayList<String[]>();
  	boolean quantityBoolean = false;
  	int quantityAmount = 0;
  	String currentUPC;
    
    
  
    

	

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

		// Event to open Customer pane		
		customerButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				loadCustomerContentPane();
			}
		});

		// Event to open Clerk pane
		clerkButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				loadClerkContent();
			}
		});

		// Event to open Manager pane		
		managerButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				loadManagerContent();
			}
		});
		
		// Event to close the application		
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
		connect("root", "1234");
		//driver setup end
		if(con == null){
			System.exit(1);
		}
	}
	
	private boolean connect(String username, String password)
    {

//*************** NEED TO CHOOSE THE SERVER URL ************************   

    	//MySQL URL for HOME
		String connectURL = "jdbc:mysql://localhost/project3";  
//*************************************************************************
    	
    	try 
    	{	
    		con = DriverManager.getConnection(connectURL,username,password);

    		System.out.println("\nConnected to Database!");
    		return true;
    	}
    	catch (SQLException ex)
    	{
    		System.out.println("Message: " + ex.getMessage());
    		return false;
    	}
    }


	// Customer Pane	
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

			// Event to login user			
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
					
					JLabel usernameLabel = new JLabel("Enter LoginId: ");
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
							int i = authenticate(usernameField.getText().trim(),passwordField.getPassword());
							if (i== 0){
								shoppingList.clear();
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

			// Event to register user			
			registerButton.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					JPanel registerPane = new JPanel();
					
					name = new JTextField(10);
					address = new JTextField(20);
					phoneNumber = new JTextField(10);
					loginID = new JTextField(10);
					loginID.setText("ID must be a number");
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
							int i = registerUser(name.getText().trim(), address.getText().trim(), phoneNumber.getText().trim(), loginID.getText().trim(), password.getPassword());
							if(i == 0){
								JOptionPane.showMessageDialog(null, "Register Successful Please Log In");
								loadCustomerContentPane();
							}else if (i == 1){
								loginID.setText("");
								JOptionPane.showMessageDialog(null, "Login ID already in use");
							}else if(i == 2){
								loginID.setText("");
								JOptionPane.showMessageDialog(null, "Login ID not valid, must be a number");
							}else{
								JOptionPane.showMessageDialog(null, "SQLException thrown, check console");
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

			// Event to go back
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
			Dimension d = mainFrame.getToolkit().getScreenSize();
		    Rectangle r = mainFrame.getBounds();
		    mainFrame.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );
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

	// Clerk Pane	

	private void loadClerkContent(){
			clerkPane = new JPanel();
			date = new JTextField("YYYY-MM-DD");
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
					
					int i;
					try{
						Integer.parseInt(recept_ID.getText().trim());
					}catch(NumberFormatException e){
						JOptionPane.showMessageDialog(null, "Receipt ID must be a Number");
						return;
					}
					String temp = date.getText().trim();
					if(!temp.matches("[\\d][\\d][\\d][\\d]-[\\d][\\d]-[\\d][\\d]") ){
						JOptionPane.showMessageDialog(null, "Date Format Invalid, Please Enter YYYY-MM-DD");
						return;
					}
					
					
					if(Integer.parseInt(temp.substring(5,7)) < 13 && Integer.parseInt(temp.substring(8,10)) < 32 && !recept_ID.getText().trim().equals("")){
						i = returnItem(temp, recept_ID.getText().trim());
					}else{
						JOptionPane.showMessageDialog(null, "Invalid Date or Enter a Receipt ID");
						return;
					}
					
					if(i== 0){
						if(amountReturned == null){
						JOptionPane.showMessageDialog(null, "No purchases found, either no such purchase or it's already returned");	
						}else
						JOptionPane.showMessageDialog(null, "Refund Is: " + amountReturned);
					}else{
						JOptionPane.showMessageDialog(null, "No Records Found");
					}
					
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

	// Manager Pane
	private void loadManagerContent(){
		if(managerPane == null){
			managerPane = new JPanel();
			JButton addItem = new JButton("Add Item");
			JButton dSR = new JButton("Daily Sales Report");
			JButton tSI = new JButton("Top Selling Item");
			JButton goBack = new JButton("User Selection Screen");
			JButton processDelivery = new JButton("Process Delivery");
			// Event to add a new item
			addItem.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					loadAddItem();
				}

			});
			
			// Event to retrieve daily sales
			dSR.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					loadDailySales();
				}

			});
			
			// Event to retrieve top selling items
			tSI.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					loadTopSelling();
				}

			});
			
			// Event to go back
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
			
			processDelivery.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					loadProcessDelivery();
					
				}
				
			});

			managerPane.add(addItem);
			managerPane.add(dSR);
			managerPane.add(tSI);
			managerPane.add(processDelivery);
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

	// Adding Items	
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
		// Event to add item
		addItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int i = addItem(upc.getText(), qty.getText(), price.getText());
				if (i == 0){
					JOptionPane.showMessageDialog(null, "Update Successful");
				} else if (i == 1){
					JOptionPane.showMessageDialog(null, "Update Failed, UPC not found ");
				} else{
					JOptionPane.showMessageDialog(null, "Error, Unexpected Behavior Occured");
				}
			}
		});
		addItemPane.add(addItem);
		
		JButton goBack = new JButton("back");
		// Event to go back
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

	// Daily Sales	
	public void loadDailySales(){
		JPanel dailySalesPane = new JPanel();
		Vector<String> columnNames = new Vector<String>();
			columnNames.add("UPC");
			columnNames.add("Category");
			columnNames.add("Unit Price");
			columnNames.add( "Units");
			columnNames.add("Total Value");
		dailyReportTable = new JTable(new DefaultTableModel(columnNames,0));
		dailyReportTable.setSize(400,400);
		JScrollPane sP = new JScrollPane(dailyReportTable);
		JLabel dateLabel = new JLabel("Enter Date of Report: ");
		reportDate = new JTextField("YYYY-MM-DD");
		dailySalesPane.add(dateLabel);
		dailySalesPane.add(reportDate);
		
		JButton getReport = new JButton("Get Report");
		// Event to get report of daily sales
		getReport.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int i;
				String temp = reportDate.getText().trim();
				if(!temp.matches("[\\d][\\d][\\d][\\d]-[\\d][\\d]-[\\d][\\d]") ){
					JOptionPane.showMessageDialog(null, "Date Format Invalid, Please Enter YYYY-MM-DD");
					return;
				}
				
				
				if(Integer.parseInt(temp.substring(5,7)) < 13 && Integer.parseInt(temp.substring(8,10)) < 32){
					i = getDailyReport(reportDate.getText().trim());
				}else{
					JOptionPane.showMessageDialog(null, "Invalid Date");
					return;
				}
				if(i == 0){
					//just some memory freeing
					Runtime runtime = Runtime.getRuntime();
					runtime.gc();
					//
					DefaultTableModel model = (DefaultTableModel) dailyReportTable.getModel();
					int rows = model.getRowCount(); 
					for(int i3 = rows - 1; i3 >=0; i3--)
					{
					   model.removeRow(i); 
					}
					for(int i2 = 1;i2<DailyRowData.size(); i2++){
					model.addRow(DailyRowData.get(i2));
					//System.out.println(a[0] + a[1] + a[2]);
					}
					SwingUtilities.updateComponentTreeUI(mainFrame);
					
				}else{
					
				}
				

			}

		});
		dailySalesPane.add(getReport);
		
		JButton goBack = new JButton("back");
		// Event to load manager content
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

	// Top Selling Items	
	public void loadTopSelling(){
		JPanel topSellingPane = new JPanel();
		Vector<String> columnNames = new Vector<String>();
		columnNames.add("Title");
		columnNames.add("Company");
		columnNames.add("Stock");
		columnNames.add( "Sold");
		topReportTable = new JTable(new DefaultTableModel(columnNames,0));
		topReportTable.setSize(400,400);
		JScrollPane sP = new JScrollPane(topReportTable);
		JLabel dateLabel = new JLabel("Enter Date of Report: ");
		reportDate = new JTextField("YYYY-MM-DD");
		JLabel nLabel = new JLabel("Enter Max Items Shown: ");
		n = new JTextField("Number Value Here");
		topSellingPane.add(dateLabel);
		topSellingPane.add(reportDate);
		topSellingPane.add(nLabel);
		topSellingPane.add(n);
		
		JButton getReport = new JButton("Get Report");
		// Event to get top selling items report
		getReport.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int i;
				try{
				Integer.parseInt(n.getText().trim());	
				}catch(NumberFormatException e){
					JOptionPane.showMessageDialog(null, "Max Item Type Number");
				}
				String temp = reportDate.getText().trim();
				if(!temp.matches("[\\d][\\d][\\d][\\d]-[\\d][\\d]-[\\d][\\d]") ){
					JOptionPane.showMessageDialog(null, "Date Format Invalid, Please Enter YYYY-MM-DD");
					return;
				}
				
				
				if(Integer.parseInt(temp.substring(5,7)) < 13 && Integer.parseInt(temp.substring(8,10)) < 32){
					i = getTopReport(reportDate.getText().trim(), n.getText().trim());
				}else{
					JOptionPane.showMessageDialog(null, "Invalid Date");
					return;
				}
				if(i == 0){
					//just some memory freeing
					Runtime runtime = Runtime.getRuntime();
					runtime.gc();
					//
					DefaultTableModel model = (DefaultTableModel) topReportTable.getModel();
					int rows = model.getRowCount(); 
					for(int i3 = rows - 1; i3 >=0; i3--)
					{
					   model.removeRow(i); 
					}
					for(int i2 = 1;i2<TopRowData.size(); i2++){
					model.addRow(TopRowData.get(i2));
					//System.out.println(a[0] + a[1] + a[2]);
					}
					SwingUtilities.updateComponentTreeUI(mainFrame);
				}else if (i == 1){
					
				}else{

				}

			}

		});
		topSellingPane.add(getReport);
		
		JButton goBack = new JButton("back");
		// Event go back
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

	
	//
	//
	//
	//
	//
	//
	private void loadCheckOut(){
		JPanel checkOutPane = new JPanel();
		finalCount = 0;
		
		ccExpiry = new JTextField("YYYY-MM-DD");
		ccNumber = new JTextField(10);
		JLabel ccExpiryLabel = new JLabel("Expiry Date");
		JLabel ccNumberLabel = new JLabel("Credit Card Number");
		JButton purchaseConfirm = new JButton("Confirm Purchase");
		
		Vector<String> columnNames = new Vector<String>();
		columnNames.add("Item UPC");
		columnNames.add("Item Amount");
		columnNames.add("Price Per Unit");
		columnNames.add("Price");
		
		JTable receiptTable = new JTable(new DefaultTableModel(columnNames, 0));
		receiptTable.setSize(400,400);
		JScrollPane sP = new JScrollPane(receiptTable);
		DefaultTableModel model = (DefaultTableModel) receiptTable.getModel();
		int total = 0;
		for(String[] shoppingItem: shoppingList){
			Vector<String> temp = new Vector<String>();
			temp.add(shoppingItem[0]);
			temp.add(shoppingItem[1]);
			temp.add(shoppingItem[2]);
			temp.add(Integer.toString(Integer.parseInt(shoppingItem[1].trim()) * Integer.parseInt(shoppingItem[2].trim())));
		    total = total + (Integer.parseInt(shoppingItem[1].trim()) * Integer.parseInt(shoppingItem[2].trim()));
		    model.addRow(temp);
		}
		
		Vector<String> temp2 = new Vector<String>();
		temp2.add("Total");
		temp2.add("Price");
		temp2.add("  :  ");
		temp2.add(Integer.toString(total));
		model.addRow(temp2);
		checkOutPane.add(ccNumberLabel);
		checkOutPane.add(ccNumber);
		checkOutPane.add(ccExpiryLabel);
		checkOutPane.add(ccExpiry);
		checkOutPane.add(sP);
		checkOutPane.add(purchaseConfirm);
		purchaseConfirm.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//makesure date is correct format
				String temp = ccExpiry.getText().trim();
				if(!temp.matches("[\\d][\\d][\\d][\\d]-[\\d][\\d]-[\\d][\\d]") ){
					JOptionPane.showMessageDialog(null, "Date Format Invalid, Please Enter YYYY-MM-DD");
					return;
				}
				
				
				if(Integer.parseInt(temp.substring(5,7)) < 13 && Integer.parseInt(temp.substring(8,10)) < 32){
				
				int i=0;
				
				
				for(String[] shoppingItem: shoppingList){
				currentUPC = shoppingItem[0].trim();
				quantityAmount = Integer.parseInt(shoppingItem[1].trim());
				i = i + purchaseItem(ccNumber.getText(), ccExpiry.getText());
				}
				if(i == 0){
				JOptionPane.showMessageDialog(null, "Purchase Complete, all items should arrive within " + Integer.toString(finalCount) + "days");
				//loadCustomerContentPane();
				}else{
				JOptionPane.showMessageDialog(null, Integer.toString(i) + "Purchases were incomplete");
				}
				}else{
					JOptionPane.showMessageDialog(null, "Date Invalid");
					return;
				}
			}
			
		});
		mainFrame.setContentPane(checkOutPane);
		mainFrame.setSize(600, 600);
		Dimension d = mainFrame.getToolkit().getScreenSize();
	    Rectangle r = mainFrame.getBounds();
	    mainFrame.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );
		SwingUtilities.updateComponentTreeUI(mainFrame);
	}
	
	private void loadOnlineOrder(){
		
		JPanel onlineOrderPane = new JPanel();
		Vector<String> columnNames = new Vector<String>();
		columnNames.add("UPC");
		columnNames.add("Item title");
		columnNames.add("Type");
		columnNames.add("Category");
		columnNames.add("Company");
		columnNames.add("Year");
		columnNames.add("Price");
		columnNames.add("Stock");
		onlineOrderTable = new JTable(new DefaultTableModel(columnNames,0));
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
		JLabel iidLabel = new JLabel("Choose from upc in Table");
		upcOnline = new JTextField("Enter upc Here");
		JButton addToShoppingCart = new JButton("Add To Shopping Cart");
		JButton backToCustomerSelect = new JButton("Back to Customer Select");
		JButton checkOut = new JButton("Checkout");
		
		
		
		//Add Listeners
		search.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(qtyOnline.getText().trim().equals("") || qtyOnline.getText().trim().equals("0")){
					JOptionPane.showMessageDialog(null, "Enter a Quantity");
					return;
				}
				int i = search(category.getText().trim(), title.getText().trim(), leadSinger.getText().trim());
				if(i == 0){
					//just some memory freeing
					Runtime runtime = Runtime.getRuntime();
					runtime.gc();
					//
					
					DefaultTableModel model = (DefaultTableModel) onlineOrderTable.getModel();
					int rows = model.getRowCount(); 
					for(int i3 = rows - 1; i3 >=0; i3--)
					{
					   model.removeRow(i); 
					}
					for(int i2 = 1;i2<OnlineRowData.size(); i2++){
					model.addRow(OnlineRowData.get(i2));
					//System.out.println(a[0] + a[1] + a[2]);
					}
					if(OnlineRowData.size() == 2){
						String qty = null;
						String price = null;
						String upcTemp = null;
						upcTemp = OnlineRowData.get(1)[0].trim();
						qty = OnlineRowData.get(1)[7].trim();
						price = OnlineRowData.get(1)[6].trim();
						
						
						if(qty!= null&&price!=null){
							if(Integer.parseInt(qty.trim()) >= Integer.parseInt(qtyOnline.getText().trim())){
							addShopping(upcTemp, qtyOnline.getText().trim(), price);
							JOptionPane.showMessageDialog(null, qtyOnline.getText().trim() +" of Item# "+ upcTemp + "at Price " + price +" is in the Cart");
							
							}else{
								int flag = JOptionPane.showConfirmDialog(null, "quantity available (" + qty +")is lower than user specificed ("+ qtyOnline.getText().trim() + "), continue?");
								if(flag == 0){
									addShopping(upcTemp, qty, price);
									JOptionPane.showMessageDialog(null, qty +" of Item# "+ upcTemp + "at Price " + price +" is in the Cart");
									//System.out.print("yay");
								}
								
							}
							
						}else{
							JOptionPane.showMessageDialog(null, "Invalid UPC, must be in table or No price or No quantity Info");
							return;
						}
						
					}
					SwingUtilities.updateComponentTreeUI(mainFrame);
					
				}else if(i == 1){
					JOptionPane.showMessageDialog(null, "No Items Found");
				}else{
					JOptionPane.showMessageDialog(null, "Unexpected Behaviour");
				}
				
			}
			
		});
		addToShoppingCart.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(qtyOnline.getText().trim().equals("") || qtyOnline.getText().trim().equals("0")){
					JOptionPane.showMessageDialog(null, "Enter a Quantity");
					return;
				}
				try{
				Integer.parseInt(upcOnline.getText().trim());
				}catch(NumberFormatException e){
					JOptionPane.showMessageDialog(null, "Invalid UPC, must be a number");
					return;
				}
				String qty=null;
				String price=null;
				for(String[] row: OnlineRowData){
					if(row[0].trim().equals(upcOnline.getText().trim())){
						qty = row[7].trim();
						price = row[6].trim();
						break;
					}
				}
				
				if(qty!= null&&price!=null){
					if(Integer.parseInt(qty.trim()) >= Integer.parseInt(qtyOnline.getText().trim())){
					addShopping(upcOnline.getText().trim(), qtyOnline.getText().trim(), price);
					JOptionPane.showMessageDialog(null, qtyOnline.getText().trim() +" of Item# "+ upcOnline.getText().trim() + "at Price " + price +" is in the Cart");
					
					}else{
						int flag = JOptionPane.showConfirmDialog(null, "quantity is lower than specificed, continue?");
						if(flag == 0){
							addShopping(upcOnline.getText().trim(), qtyOnline.getText().trim(), price);
							JOptionPane.showMessageDialog(null, qtyOnline.getText().trim() +" of Item# "+ upcOnline.getText().trim() + "at Price " + price +" is in the Cart");
							//System.out.print("yay");
						}
						
					}
					
				}else{
					JOptionPane.showMessageDialog(null, "Invalid UPC, must be in table or No price or No quantity Info");
					return;
				}
				
			}
			
		});
		backToCustomerSelect.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				loadCustomerContentPane();
				currentUser = null;
				shoppingList.clear();
			}
			
		});
		checkOut.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				loadCheckOut();
				
			}
			
		});
		
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
		onlineOrderPane.add(upcOnline);
		onlineOrderPane.add(addToShoppingCart);
		onlineOrderPane.add(backToCustomerSelect);
		onlineOrderPane.add(checkOut);
		
		mainFrame.setContentPane(onlineOrderPane);
		mainFrame.setSize(900, 550);
		Dimension d = mainFrame.getToolkit().getScreenSize();
	    Rectangle r = mainFrame.getBounds();
	    mainFrame.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );
		SwingUtilities.updateComponentTreeUI(mainFrame);
	}
	
	private void loadProcessDelivery(){
		JPanel processDeliveryPane = new JPanel();
		processDate = new JTextField("YYYY-MM-DD");
		processID = new JTextField(10);
		JLabel processIDLabel = new JLabel("Receipt ID");
		JLabel processDateLabel = new JLabel("Delivery Date");
		JButton process = new JButton("Process");
		JButton goBack = new JButton("Back");
		
		processDeliveryPane.add(processIDLabel);
		processDeliveryPane.add(processID);
		processDeliveryPane.add(processDateLabel);
		processDeliveryPane.add(processDate);
		processDeliveryPane.add(process);
		processDeliveryPane.add(goBack);
		goBack.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				loadManagerContent();
			}
		});
		process.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int i;
				try{
					Integer.parseInt(processID.getText().trim());
				}catch(NumberFormatException e){
					JOptionPane.showMessageDialog(null, "Receipt ID Invalid");
					return;
				}
				
				String temp = processDate.getText().trim();
				if(!temp.matches("[\\d][\\d][\\d][\\d]-[\\d][\\d]-[\\d][\\d]") ){
					JOptionPane.showMessageDialog(null, "Date Format Invalid, Please Enter YYYY-MM-DD");
					return;
				}
				
				
				if(Integer.parseInt(temp.substring(5,7)) < 13 && Integer.parseInt(temp.substring(8,10)) < 32){
					i = updateDelivery(processDate.getText().trim(), processID.getText().trim());
				}else{
					JOptionPane.showMessageDialog(null, "Invalid Date");
					return;
				}
				if(i ==0){
					JOptionPane.showMessageDialog(null, "Update Success");
				}else if( i == 1){
					JOptionPane.showMessageDialog(null, "Update Failed, No Such Receipt ID Found");
				}
			}
			
		});
		
		mainFrame.setContentPane(processDeliveryPane);
		mainFrame.pack();
		Dimension d = mainFrame.getToolkit().getScreenSize();
	    Rectangle r = mainFrame.getBounds();
	    mainFrame.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );
		SwingUtilities.updateComponentTreeUI(mainFrame);
	   
		
	}
	
	
	
	
	
	
	
	//TRANSACTION STUFF, shopping cart still needs implementing. 
	//The idea is to fill a variable at the top with the returned info such as in a JTable for the appropriate view.

	//
	//
	//
	//
	//
	
	

	//todo


	// TODO
		// Get Online Orders
		// Updates OnlineRowData
		private int search(String category, String title, String leading){
			OnlineRowData.clear();
			Statement  stmt;
	    	ResultSet  rs;
	    	String leadSinger= "";
	    	String where ="";
	    	
	    	if(!leading.equals("")){
	    	leadSinger = " ,LeadSinger, HasSong h ";
	    	}
	    	if((!leading.equals("")||!category.equals("")||!title.equals(""))){
	    	where = " WHERE Item.upc IS NOT NULL";
	    	}
	    		
	    	String query = ("select Item.upc, Item.title, type, category, company, year, price, stock from Item" + 
	    	leadSinger + where);
	    	
	    	if (!category.equals("")) {
	    		query = query + " and category = '" + category + "'";
	    	}
	    	if (!leading.equals("")) {
	    		query = query + " and Item.upc = LeadSinger.upc and Item.upc = h.upc" +" and name = '" + leading + "'";
	    	}
	    	if (!title.equals("")) {
	    		query = query + " and Item.title = '" + title + "'";
	    	}
	    	query = query + ";";

	    try
		{
		  stmt = con.createStatement();  
		  rs = stmt.executeQuery(query);
		  		  
		  // get info on ResultSet
		  ResultSetMetaData rsmd = rs.getMetaData();

		  // get number of columns
		  int numCols = rsmd.getColumnCount();
		  String[] buffer = new String[numCols];
		  // display column names;
		  buffer[0] = "UPC";
	      buffer[1] = "Item title";
	      buffer[2] = "Type";
	      buffer[3] = "Category";
	      buffer[4] = "Company";
	      buffer[5] = "Year";
	      buffer[6] = "Price";
	      buffer[7] = "Stock";
		  OnlineRowData.add(buffer);
		  //displayToConsole(rs);
		  while(rs.next())
		  {
			  buffer = new String[numCols];
			  buffer[0] = rs.getString("upc");
		      buffer[1] = rs.getString("title");
		      buffer[2] = rs.getString("type");
		      buffer[3] = rs.getString("category");
		      buffer[4] = rs.getString("company");
		      buffer[5] = rs.getString("year");
		      buffer[6] = rs.getString("price");
		      buffer[7] = rs.getString("stock");
		      OnlineRowData.add(buffer);
		  }
	 
		  // close the statement; 
		  // the ResultSet will also be closed
		  	stmt.close();
			}
			catch (SQLException ex)
			{
			    System.out.println("Message: " + ex.getMessage());
			    return 1;
			}	
	    	displayToConsole2(TopRowData);
			return 0;
		}

		
		// Add to shopping cart
		// Updates shoppingList
	
	private int addShopping(String upc, String qty, String price) {
		int flag = 0;
		for(String[] shoppingItem: shoppingList){
			if(shoppingItem[0].equals(upc)){
				flag = JOptionPane.showConfirmDialog(null, "Shopping list contains same type of item, replace quantity(" +shoppingItem[1] +") with " + qty+"?"); 
				if (flag == 0){
					shoppingItem[1] = qty.trim();
					shoppingItem[2] = price.trim();
					flag = 13;
					return 0;
				}else{
					flag = 1;
				}
			}
		}
		if(flag != 13 && flag != 1){	
		String[] buffer = new String[3];
		buffer[0] = upc;
		buffer[1] = qty;
		buffer[2] = price;
		shoppingList.add(buffer);
		return 0;
		}
		else {
			System.out.println("seriously wow");
			return 1;
		}
	}
	
	// Uses: currentUser, quantityAmount, currentUPC
	
	private int purchaseItem(String card, String expireDate) {
		Statement  stmt;
    	ResultSet  rs;
    	int count;
    	
    	String query = ("select count(*) from Orders where deliveredDate IS NULL;");
    	try {
	    	stmt = con.createStatement(); 
	    	rs = stmt.executeQuery(query);
	    	rs.next();
	    	count = Integer.parseInt(rs.getString("count(*)"));
	    	// a = orders per day approxiate
	    	int a = 2;
	    	count = count / a;
	    	finalCount = count;
	    	query = ("select max(receiptId) from Orders;");
	    	stmt = con.createStatement(); 
	    	rs = stmt.executeQuery(query);
	    	rs.next();
	    	String receiptId = rs.getString("max(receiptId)");
	    	int rid = 0;
	    	if (rs.wasNull()) {
				  rid = 100;
			  } else {
				  rid = 1 + Integer.parseInt(receiptId);
			  }
	    	query = ("insert into Orders values " + 
	    			"("+ rid +", curdate(), "+ currentUser +" , '" + card + "' , '" + expireDate + "', date_add(curdate(), interval " + count + " day), null);");		
	    	
	    	stmt = con.createStatement(); 
    		int rows = stmt.executeUpdate(query);
	    	if (rows != 1 ) {
	    		return 1;	
	    	} 
	    	query = ("insert into PurchaseItem values (" + rid + ", " + currentUPC + ", " + quantityAmount + ");");
	    	stmt = con.createStatement(); 
    		rows = stmt.executeUpdate(query);
	    	if (rows != 1 ) {
	    		return 1;	
	    	} 
	    	// Update quantity
	    	query = ("update Item set stock = stock - " + quantityAmount + " where upc = " + currentUPC + ";");
	    	stmt = con.createStatement(); 
    		rows = stmt.executeUpdate(query);
	    	if (rows != 1 ) {
	    		return 1;	
	    	} 
	    	return 0;
    	} catch (SQLException e) {
    		e.printStackTrace();
    		return 1;
    	}
	}
	
	// updates quantityBoolean and quantityAmount(actual quantity of item)
	private int quantityCheck(String qty, String upc) {
		Statement  stmt;
    	ResultSet  rs;
    	String query = ("select stock, count(*) from Item Where Item.upc = " + upc + ";");
    	int quantity;
    try
	{
	  stmt = con.createStatement();  
	  rs = stmt.executeQuery(query);
	  rs.next();
	  if (Integer.parseInt(rs.getString("count(*)")) == 0) {
		  stmt.close();
		  return 1;
	  }  
	  quantity = Integer.parseInt(rs.getString("stock"));
	  stmt.close();
	  if (quantity < Integer.parseInt(qty)) {
		  quantityAmount = quantity;
		  return 1;
	  }
	  

	  // get info on ResultSet
	  	  	
		}
		catch (SQLException ex)
		{
		    System.out.println("Message: " + ex.getMessage());
		    return 1;
		}	
    	//displayToConsole2(TopRowData);
		return 0;
	}
	
	
	//return 0 if successful, return 1 if not successful. Also update topReportTable if successful
	// updates topRowData
	
	private int getTopReport(String date, String n){
		TopRowData.clear();
    	Statement  stmt;
    	ResultSet  rs;
    	String query = ("select title, company, stock, sum(quantity) " + 
				"from Orders, PurchaseItem, Item " + 
				"where Orders.dates = '" + date + "' and Orders.receiptId = PurchaseItem.receiptId and PurchaseItem.upc = Item.upc " +
				"group by Item.upc " +
				"order by quantity " +
				"desc limit " + n + ";");
    	
    try
	{
	  stmt = con.createStatement();  
	  rs = stmt.executeQuery(query);

	  // get info on ResultSet
	  ResultSetMetaData rsmd = rs.getMetaData();

	  // get number of columns
	  int numCols = rsmd.getColumnCount();
	  String[] buffer = new String[numCols];
	  // display column names;
	  buffer[0] = "Title";
      buffer[1] = "Company";
      buffer[2] = "Stock";
      buffer[3] = "Sold";
	  TopRowData.add(buffer);
	  //displayToConsole(rs);
	  while(rs.next())
	  {
		  buffer = new String[numCols];
		  buffer[0] = rs.getString("title");
	      buffer[1] = rs.getString("company");
	      buffer[2] = rs.getString("stock");
	      buffer[3] = rs.getString("sum(quantity)");
	      TopRowData.add(buffer);
	  }
 
	  // close the statement; 
	  // the ResultSet will also be closed
	  	stmt.close();
		}
		catch (SQLException ex)
		{
		    System.out.println("Message: " + ex.getMessage());
		    return 1;
		}	
    	displayToConsole2(TopRowData);
		return 0;
	}

	//
	//
	//
	//return 0 if successful, return 1 if not successful. Also update dailyReportTable if successful.
	//updates DailyRowData
	private int getDailyReport(String date){
		Statement  stmt;
    	ResultSet  rs;
    	String query = ("select i.upc, category, price, sum(p.quantity), sum(price)	from Item i, PurchaseItem p, Orders"
    			+ "	where Orders.dates = '" + date + "' and i.upc = p.upc and p.receiptId = Orders.receiptId "
    			+ "group by i.upc "
    			+ "order by category;");

    	String CurrentCategory = "";
    	int subUnit = 0;
    	int totalUnit = 0;
    	int subSum = 0;
    	int totalSum = 0;
    	int numCols;
    	String[] buffer;
    	String[] buffer2;
    	
    try
	{
	  stmt = con.createStatement();  
	  rs = stmt.executeQuery(query);

	  // get info on ResultSet
	  ResultSetMetaData rsmd = rs.getMetaData();

	  // get number of columns
	  numCols = rsmd.getColumnCount();
	  buffer = new String[numCols];
	  buffer2 = new String[numCols];
	  
	  //displayToConsole(rs);
	  // display column names;
	  buffer[0] = "UPC";
      buffer[1] = "Category";
      buffer[2] = "Unit Price";
      buffer[3] = "Units";
      buffer[4] = "Total Value";
	  DailyRowData.add(buffer);
	  
	  while(rs.next())
	  {		
		  buffer = new String[numCols];
		  buffer2 = new String[numCols];
		  buffer[0] = rs.getString("upc");
	      buffer[1] = rs.getString("category");
	      buffer[2] = rs.getString("price");
	      buffer[3] = rs.getString("sum(p.quantity)");
	      buffer[4] = rs.getString("sum(price)");
	      if (CurrentCategory.equals(buffer[1])) {
	    	  subUnit = subUnit + Integer.parseInt(buffer[3]);
	    	  subSum = subSum + Integer.parseInt(buffer[4]);
	      } else {	
	    	  totalUnit = totalUnit + subUnit;
	    	  totalSum = totalSum + subSum;
	    	  buffer2[0] = "";
	          buffer2[1] = "Total";
	          buffer2[2] = "";
	    	  buffer2[3] = Integer.toString(subUnit);
	    	  buffer2[4] = Integer.toString(subSum);
	    	  subUnit = Integer.parseInt(buffer[3]);
	    	  subSum = Integer.parseInt(buffer[4]);
	    	  if (!CurrentCategory.equals("")) {
	    	  DailyRowData.add(buffer2);
	    	  }
	    	  CurrentCategory = buffer[1];
	    	  
	      }
	      DailyRowData.add(buffer);
	  }
 
	  // close the statement; 
	  // the ResultSet will also be closed
	  	stmt.close();
		}
		catch (SQLException ex)
		{
		    System.out.println("Message: " + ex.getMessage());
		    return 1;
		}
      totalUnit = totalUnit + subUnit;
	  totalSum = totalSum + subSum;
	  buffer2 = new String[numCols];
	  buffer2[0] = "";
      buffer2[1] = "Total";
      buffer2[2] = "";
	  buffer2[3] = Integer.toString(subUnit);
	  buffer2[4] = Integer.toString(subSum);
	  DailyRowData.add(buffer2);
	  buffer2 = new String[numCols];
	  buffer2[0] = "";
      buffer2[1] = "Total Daily Sales";
      buffer2[2] = "";
	  buffer2[3] = Integer.toString(totalUnit);
	  buffer2[4] = Integer.toString(totalSum);
	  DailyRowData.add(buffer2);
	  displayToConsole2(DailyRowData);
	  return 0;
	}

	//return 0 if successful, return 1 if not successful
	private int addItem(String upc, String qty, String price ){
		Statement  stmt;
    	String query = ("update Item set stock = stock + " + qty + " where upc = " + upc + ";");
    	try {
	    	stmt = con.createStatement(); 
	    	int rows = stmt.executeUpdate(query);
	    	if (rows == 0 ) {
	    		return 1;	
	    	} 
	    	if (!price.equals("")) {
	    		query = ("update Item set price = " + price + " where upc = " + upc + ";");
	    		stmt = con.createStatement(); 
	    		rows = stmt.executeUpdate(query);
		    	if (rows == 0 ) {
		    		return 1;	
		    	} 
	    	}
	    	return 0;
    	} catch (SQLException e) {
    		e.printStackTrace();
    		return 1;
    	}
	}

	//todo return 0 if successful, return 1 if not successful. Also set currentUser field to contain cid for online ordering
	// Updates currentUser and loggedIn
	private int authenticate(String user, char[] pass){
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < pass.length; i++) {
		   result.append( pass[i] );
		}
		String newPass = result.toString();
		Statement  stmt;
    	ResultSet  rs;
    	String query = ("select count(*) from Customer where cid = " + user + " and password = '" + newPass + "';");
    	int count;
    	//System.out.println(pass.toString());
    try
	{
	  stmt = con.createStatement();  
	  rs = stmt.executeQuery(query);

	  // get info on ResultSet
	  rs.next();
	  count = Integer.parseInt(rs.getString("count(*)"));
	  stmt.close();
	  if (count == 0) {
		  //System.out.println("failure login");
		  return 1;
	  } else {
		  currentUser = user;
		  loggedIn = true;
		  //System.out.println("successful login");
		  return 0;
	  }
		}
		catch (SQLException ex)
		{
		    System.out.println("Message: " + ex.getMessage());
		    return 1;
		}	

	}

	//todo return 0 if successful, return 1 if id in use, return > 1 for error handling cases
	private int registerUser (String name, String address, String phone, String id, char[] pass){
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < pass.length; i++) {
		   result.append( pass[i] );
		}
		String newPass = result.toString();
		Statement  stmt;
		String query = ("insert into Customer values (" + id + ", '" + newPass + "', '" + name + "', '" + address
		+ "', '" + phone + "');");
		try {
	    	stmt = con.createStatement(); 
	    	int rows = stmt.executeUpdate(query);
	    	if (rows != 0 ) {
	    		return 0;	
	    	} else {
	    		return 1;
	    	}
		} catch (MySQLIntegrityConstraintViolationException e) {
			e.printStackTrace();
			return 1;
		} catch(MySQLSyntaxErrorException e){
			e.printStackTrace();
			return 2;
		} catch (SQLException e){
			e.printStackTrace();
			return 1;
		}
	}

	//todo 
	// Results are stored in amountReturned
	private int returnItem(String date, String receiptID){
		amountReturned = null;
		Statement  stmt;
    	ResultSet  rs;
    	String query = ("select count(*), PurchaseItem.upc, PurchaseItem.quantity from PurchaseItem, Orders "
    			+ "where Orders.receiptId = PurchaseItem.receiptId and datediff('" + date + "', Orders.dates) < 15 and datediff('" + date + "', Orders.dates) >= 0 and "
    			+ receiptID + " = PurchaseItem.receiptId and PurchaseItem.receiptID NOT IN(Select distinct receiptID from Returns);");
    	int count;
    	int retId;
    	int price;
    	String upc;
    	String quantity;
    try
	{
    	
	  stmt = con.createStatement();  
	  System.out.println("here");
	  rs = stmt.executeQuery(query);
	  System.out.println("here2");
	  // displayToConsole(rs);
	  rs.next();
	  count = Integer.parseInt(rs.getString("count(*)"));
	  System.out.println(count);
	  if (count > 0) {
		  upc = rs.getString("upc");
		  System.out.println(upc);
		  quantity = rs.getString("quantity");
		  System.out.println(quantity);
		  query = ("select max(retId)" +
				  "from Returns;");	
		  stmt = con.createStatement();
		  rs = stmt.executeQuery(query);
		  rs.next();
		  String stub = rs.getString("max(retId)");
		  if (rs.wasNull()) {
			  retId = 100;
		  } else {
			  retId = 1 + Integer.parseInt(rs.getString("max(retId)"));
		  }
		  System.out.println(retId);
		  query = ("insert into Returns values " + 
				  "(" + retId + ",'" + date + "'," + receiptID + ");"); 
		  stmt = con.createStatement();
		  int rows = stmt.executeUpdate(query);
		  System.out.println("First row" + rows);
	    	if (rows == 0 ) {
	    		return 1;	
	    	}
		  query = ("insert into ReturnItem values " + 
				  "(" + retId + "," + upc + "," + quantity + ");");
		  System.out.println("Second row" + rows);
		  stmt = con.createStatement();
		  rows = stmt.executeUpdate(query);
	    	if (rows == 0 ) {
	    		return 1;	
	    	}
		  query = ("(select price from Item "+
				  "where " + upc + " = Item.upc);");
		  stmt = con.createStatement();
		  rs = stmt.executeQuery(query);
		  rs.next();
		  price = Integer.parseInt(rs.getString("price"));
		  query = ("update Item set stock = stock + " + quantity + " "+
				  "where upc = " + upc + " ;");
		  stmt = con.createStatement();
		  rows = stmt.executeUpdate(query);
		  System.out.println("Fourth row" + rows);
	    	if (rows == 0 ) {
	    		return 1;	
	    	}
	     
	    	
		  amountReturned = Integer.toString(price*(Integer.parseInt(quantity)));
		  System.out.println(amountReturned);
	  }
	  // get info on ResultSet
	  ResultSetMetaData rsmd = rs.getMetaData();
	  // get number of columns
	  stmt.close();
		}
		catch (SQLException ex)
		{
		    System.out.println("Message: " + ex.getMessage());
		    return 1;
		}
      return 0;
	}

	
	

	
	private int updateDelivery(String date, String receiptId) {
		Statement  stmt;
    	ResultSet  rs;
    	String query = ("update Orders set deliveredDate = '" + date + "' where receiptId = "+ receiptId + ";");
    	try {
	    	stmt = con.createStatement(); 
	    	int rows = stmt.executeUpdate(query);
	    	if (rows != 0 ) {
	    		return 0;	
	    	} else {
	    		return 1;
	    	}
    	} catch (SQLException e) {
    		e.printStackTrace();
    		return 1;
    	}
	}
	
	private void displayToConsole(ResultSet rs) {
		System.out.println(" ");
		try {
			while(rs.next())
			  {
			      System.out.printf("%-15.15s", rs.getString("count(*)"));
			      System.out.printf("%-15.15s", rs.getString("upc"));
			      System.out.printf("%-15.15s", rs.getString("quantity"));
			     // System.out.printf("%-15.15s", rs.getString("sum(p.quantity)"));
			     // System.out.printf("%-15.15s\n", rs.getString("sum(price)"));
			  }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void displayToConsole2(List<String[]> dailyRowData2) {
		System.out.println(" ");
		for (int i = 0; i < dailyRowData2.size(); i++) {
			for (int j = 0; j < dailyRowData2.get(i).length; j++) {
				System.out.printf("%-15.15s", dailyRowData2.get(i)[j]);
			}
			System.out.println(" ");
		}
	}

	public static void main(String args[])
	{
		Warehouse w = new Warehouse();
		
	}
}

