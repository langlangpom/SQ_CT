package com.evian.sqct;

import java.awt.*;

public class NotificationDemo {
    public static void main(String[] args) throws AWTException {
        if (SystemTray.isSupported()) {
            NotificationDemo nd = new NotificationDemo();
            nd.displayTray("测试","车上");
        } else {
            System.err.println("System tray not supported!");
        }
    }

    public void displayTray(String acption,String text) throws AWTException {
        //Obtain only one instance of the SystemTray object
        SystemTray tray = SystemTray.getSystemTray();

        //If the icon is a file
        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
        //Alternative (if the icon is on the classpath):
        //Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("icon.png"));

        TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
        //Let the system resize the image if needed
        trayIcon.setImageAutoSize(true);
        //Set tooltip text for the tray icon
        trayIcon.setToolTip("System tray icon demo");
        tray.add(trayIcon);

        trayIcon.displayMessage(acption, text, TrayIcon.MessageType.INFO);
    }
}