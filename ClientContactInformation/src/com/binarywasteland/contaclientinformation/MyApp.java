package com.binarywasteland.contaclientinformation;

import java.util.Enumeration;

import javax.microedition.io.file.FileSystemRegistry;

import net.rim.device.api.io.MalformedURIException;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.Dialog;

/**
 * This class extends the UiApplication class, providing a
 * graphical user interface.
 */
public class MyApp extends UiApplication
{
    /**
     * Entry point for application
     * @param args Command line arguments (not used)
     * @throws MalformedURIException 
     * @throws IllegalArgumentException 
     */ 
    public static void main(String[] args) throws IllegalArgumentException
    {
    	
        // Create a new instance of the application and make the currently
        // running thread the application's event dispatch thread.
        MyApp theApp = new MyApp();
        theApp.enterEventDispatcher();
    }
    

    /**
     * Creates a new MyApp object
     * @throws MalformedURIException 
     * @throws IllegalArgumentException 
     */
    public MyApp() throws IllegalArgumentException
    {     
    	// Determine if an SDCard is present 
        boolean sdCardPresent = false;
        String root = null;
        Enumeration e = FileSystemRegistry.listRoots();
        while (e.hasMoreElements())
        {
            root = (String)e.nextElement();
            if(root.equalsIgnoreCase("sdcard/"))
            {
                sdCardPresent = true;
            }     
        }            
        if(!sdCardPresent)
        {
            UiApplication.getUiApplication().invokeLater(new Runnable()
            {
                public void run()
                {
                    Dialog.alert("This application requires an SD card to be present. Exiting application...");
                    System.exit(0);            
                } 
            });        
        }          
        else
        {
        	// Push a screen onto the UI stack for rendering.
        	pushScreen(new MyScreen());
        }
    }    
}
