package com.binarywasteland.contaclientinformation;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.component.BasicEditField;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;
import net.rim.device.api.database.*;
import net.rim.device.api.io.*;

/**
 * A class extending the MainScreen class, which provides default standard
 * behavior for BlackBerry GUI applications.
 */
public final class MyScreen extends MainScreen
{
	// Global Variables
	Database db;
	BasicEditField _id = new BasicEditField("ID:", "");
    BasicEditField _firstName = new BasicEditField("First Name:", "");
    BasicEditField _middleName = new BasicEditField("Middle Name:", "");
    BasicEditField _lastName = new BasicEditField("Last Name:", "");
    BasicEditField _address1 = new BasicEditField("Address 1:", "");
    BasicEditField _address2 = new BasicEditField("Address 2:", "");
    BasicEditField _city = new BasicEditField("City:", "");
    BasicEditField _provstate = new BasicEditField("Province/State:", "");
    BasicEditField _zippostalcode = new BasicEditField("Postal/Zip Code:", "");
    BasicEditField _country = new BasicEditField("Country:", "");
    BasicEditField _phone = new BasicEditField("Phone:", "");
    BasicEditField _email = new BasicEditField("Email:", "");
    /**
     * Creates a new MyScreen object
     * @throws MalformedURIException 
     * @throws IllegalArgumentException 
     */
    public MyScreen() throws IllegalArgumentException
    {        
        // Set the displayed title of the screen       
        setTitle("Client Information");
        createDB();
        
        ButtonField _submit = new ButtonField("Submit", ButtonField.CONSUME_CLICK | ButtonField.NEVER_DIRTY | Field.FIELD_HCENTER | Field.FIELD_VCENTER);
        
        VerticalFieldManager vfm = new VerticalFieldManager();
        vfm.add(_id);
        vfm.add(new SeparatorField());
        vfm.add(_firstName);
        vfm.add(new SeparatorField());
        vfm.add(_middleName);
        vfm.add(new SeparatorField());
        vfm.add(_lastName);
        vfm.add(new SeparatorField());
        vfm.add(_address1);
        vfm.add(new SeparatorField());
        vfm.add(_address2);
        vfm.add(new SeparatorField());
        vfm.add(_city);
        vfm.add(new SeparatorField());
        vfm.add(_provstate);
        vfm.add(new SeparatorField());
        vfm.add(_zippostalcode);
        vfm.add(new SeparatorField());
        vfm.add(_country);
        vfm.add(new SeparatorField());
        vfm.add(_phone);
        vfm.add(new SeparatorField());
        vfm.add(_email);
        vfm.add(new SeparatorField());
        
        _submit.setChangeListener(new FieldChangeListener()
        {
	        public void fieldChanged(Field field, int context)
	        {
	        	addClients();
	        }
        });

        vfm.add(_submit);
        add(vfm);
        
        // Adding Menu Item To Application
        addMenuItem(_numClients);
        addMenuItem(_getClient);
        addMenuItem(_updateClient);
        addMenuItem(_deleteClient);
    }
    
    private void createDB()
    {
    	//Create Database if it does not already exist
        try
        {
        	URI dbURI = URI.create("file:///SDCard/ClientInformationDB.db"); 
        	db = DatabaseFactory.openOrCreate(dbURI);
        	db.close();
        	
        	createTable();
        }
        catch(Exception e)
        {
        	 System.out.println( e.getMessage() );
             e.printStackTrace();
        }
    }
    private void createTable()
    {
    	try
    	{
    		URI dbURI = URI.create("file:///SDCard/ClientInformationDB.db"); 
    		db = DatabaseFactory.openOrCreate(dbURI);
    		Statement state = db.createStatement( "CREATE TABLE IF NOT EXISTS Clients ( " +
            		"id INTEGER primary key" +
            		"FirstName TEXT, " +
            		"MiddleName TEXT, " +
            		"LastName TEXT, " +
            		"Address1 TEXT, " +
            		"Address2 TEXT, " +
            		"City TEXT, " +
            		"ProvinceState TEXT, " +
            		"ZipPostalCode TEXT, " +
            		"Country TEXT, " +
            		"Phone TEXT, " +
                    "Email TEXT )" );
            state.prepare();
            state.execute();
            state.close();
            db.close();
    	}
    	catch(Exception e)
    	{
    		System.out.println( e.getMessage() );
            e.printStackTrace();
    	}
    }
    
    private void addClients()
    {
    	try
        {
    		URI dbURI = URI.create("file:///SDCard/ClientInformationDB.db");  
            db = DatabaseFactory.openOrCreate(dbURI);
            Statement st = db.createStatement("INSERT INTO Clients(FirstName, MiddleName, LastName, " +
            		"Address1, Address2, " +
            		"City, ProvinceState, ZipPostalCode, " +
            		"Country, Phone, Email) " +
                    "VALUES ('" + _firstName.getText() + "', '" + _middleName.getText() + "', " +
                    		"'" + _lastName.getText() + "', '" + _address1.getText() + "', " +
                    		"'" + _address2.getText() + "', '" + _city.getText() + "', " +
                    		"'" + _provstate.getText() + "', '" + _zippostalcode.getText() + "', " +
                    		"'" + _country.getText() + "', '" + _phone.getText() + "', " +
                    		"'" + _email.getText() + "')");
            st.prepare();
            st.execute();
            st.close();
            db.close();
            
        	Dialog.alert("Data Added Successfully");
        	
        	_id.setText("");
        	_firstName.setText("");
        	_middleName.setText("");
        	_lastName.setText("");
        	_address1.setText("");
        	_address2.setText("");
        	_city.setText("");
        	_provstate.setText("");
        	_zippostalcode.setText("");
        	_country.setText("");
        	_phone.setText("");
        	_email.setText("");
        }
        catch ( Exception e ) 
        {     
            System.out.println( e.getMessage() );
            e.printStackTrace();
            Dialog.alert(e.getMessage());
        }
        finally
    	{
        	try 
        	{
        		db.close();
        	} 
        	catch (DatabaseIOException e) 
        	{
        		// TODO Auto-generated catch block
        		System.out.println( e.getMessage() );
                e.printStackTrace();
                Dialog.alert(e.getMessage());
        	}
    	}
    }
    
    private MenuItem _numClients = new MenuItem("# Of Clients", 110, 10)
    {
        public void run()
        {
        	try 
        	{
	        	URI dbURI = URI.create("file:///SDCard/ClientInformationDB.db");
	        	db = DatabaseFactory.open(dbURI);
	        	
	        	Statement state = db.createStatement("SELECT COUNT(*) FROM Clients");
	        	state.prepare();
	        	Cursor cursor = state.getCursor();
	        	int count = 0;
	        	Row row;
	        	while(cursor.next()) 
	        	{
	        		row = cursor.getRow();
	        		count = row.getInteger(0);
	        	}
	        	state.close();
	        	cursor.close();
	        	db.close();
	        	
	        	System.out.print(count);
	        	_id.setText(String.valueOf(count));
	        	Dialog.alert("Number of entries: " + count);
        	}
        	catch ( Exception e ) 
        	{
	        	System.out.println( e.getMessage() );
	        	e.printStackTrace();
        	}
        	finally
        	{
	        	try 
	        	{
	        		db.close();
	        	} 
	        	catch (DatabaseIOException e) 
	        	{
	        		// TODO Auto-generated catch block
	        		System.out.println( e.getMessage() );
	                e.printStackTrace();
	                Dialog.alert(e.getMessage());
	        	}
        	}
        }
    };
    
    private MenuItem _getClient = new MenuItem("Get Client By ID", 110, 10)
    {
        public void run()
        {
        	try 
        	{
	        	URI dbURI = URI.create("file:///SDCard/ClientInformationDB.db");
	        	db = DatabaseFactory.open(dbURI);
	        	
	        	Statement state = db.createStatement("SELECT FirstName, MiddleName, LastName, Address1, " +
	        			"Address2, City, ProvinceState, ZipPostalCode, Country, Phone, Email " +
	        			"FROM Clients WHERE id = ?");
	        	state.prepare();
	        	state.bind(1, _id.getText());
	        	Cursor cursor = state.getCursor();
	        	int count = 0;
	        	Row row;
	        	while(cursor.next()) 
	        	{
	        		row = cursor.getRow();
	        		_firstName.setText(row.getString(0));
	        		_middleName.setText(row.getString(1));
	        		_lastName.setText(row.getString(2));
	        		_address1.setText(row.getString(3));
	        		_address2.setText(row.getString(4));
	        		_city.setText(row.getString(5));
	        		_provstate.setText(row.getString(6));
	        		_zippostalcode.setText(row.getString(7));
	        		_country.setText(row.getString(8));
	        		_phone.setText(row.getString(9));
	        		_email.setText(row.getString(10));
	        	}
	        	state.close();
	        	cursor.close();
	        	db.close();
	        	Dialog.alert("Entry Returned For: " + _id);
        	}
        	catch ( Exception e ) 
        	{
	        	System.out.println( e.getMessage() );
	        	e.printStackTrace();
        	}
        	finally
        	{
	        	try 
	        	{
	        		db.close();
	        	} 
	        	catch (DatabaseIOException e) 
	        	{
	        		// TODO Auto-generated catch block
	        		System.out.println( e.getMessage() );
	                e.printStackTrace();
	                Dialog.alert(e.getMessage());
	        	}
        	}
        }
    };
    
    private MenuItem _updateClient = new MenuItem("Update Client With ID", 110, 10)
    {
        public void run()
        {
        	try 
        	{
	        	URI dbURI = URI.create("file:///SDCard/ClientInformationDB.db");
	        	db = DatabaseFactory.open(dbURI);
	        	
	        	Statement state = db.createStatement("UPDATE Clients SET FirstName = ?, MiddleName = ?, LastName = ?, Address1 = ?, " +
	        			"Address2 = ?, City = ?, ProvinceState = ?, ZipPostalCode = ?, Country = ?, Phone = ?, Email = ? " +
	        			"WHERE id = ?");
	        	state.prepare();
	        	state.bind(1, _firstName.getText());
	        	state.bind(2, _middleName.getText());
	        	state.bind(3, _lastName.getText());
	        	state.bind(4, _address1.getText());
	        	state.bind(5, _address2.getText());
	        	state.bind(6, _city.getText());
	        	state.bind(7, _provstate.getText());
	        	state.bind(8, _zippostalcode.getText());
	        	state.bind(9, _country.getText());
	        	state.bind(10, _phone.getText());
	        	state.bind(11, _email.getText());
	        	state.bind(12, _id.getText());
	        	state.execute();
	        	state.close();
	        	db.close();
	        	Dialog.alert("Entry #: " + _id + " updated!");
        	}
        	catch ( Exception e ) 
        	{
	        	System.out.println( e.getMessage() );
	        	e.printStackTrace();
        	}
        	finally
        	{
	        	try 
	        	{
	        		db.close();
	        	} 
	        	catch (DatabaseIOException e) 
	        	{
	        		// TODO Auto-generated catch block
	        		System.out.println( e.getMessage() );
	                e.printStackTrace();
	                Dialog.alert(e.getMessage());
	        	}
        	}
        }
    };
    
    private MenuItem _deleteClient = new MenuItem("Delete Client By ID", 110, 10)
    {
        public void run()
        {
        	try 
        	{
	        	URI dbURI = URI.create("file:///SDCard/ClientInformationDB.db");
	        	db = DatabaseFactory.open(dbURI);
	        	
	        	Statement state = db.createStatement("DELETE FROM Clients WHERE id = ?");
	        	state.prepare();
	        	state.bind(1, _id.getText());
	        	state.execute();
	        	state.close();
	        	db.close();
	        	Dialog.alert("Entry #: " + _id + " deleted!");
        	}
        	catch ( Exception e ) 
        	{
	        	System.out.println( e.getMessage() );
	        	e.printStackTrace();
        	}
        	finally
        	{
	        	try 
	        	{
	        		db.close();
	        	} 
	        	catch (DatabaseIOException e) 
	        	{
	        		// TODO Auto-generated catch block
	        		System.out.println( e.getMessage() );
	                e.printStackTrace();
	                Dialog.alert(e.getMessage());
	        	}
        	}
        }
    };
    
    public void close()
    {
        
        if(Dialog.ask(4, "Do You Really Want To Leave Me?") == Dialog.OK)
        	super.close();
        else
        	Dialog.alert("Thanks For Sticking Around!");
    } 
}
