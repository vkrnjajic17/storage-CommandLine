package test;

import formatComponent.ExtensionList;

import model.MyPath;
import modelS.MyDirectory;
import modelS.MyFile;
import storage.Connection;
import usersComponent.User;
import usersComponent.UserDatabase;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		connectionComponent.Connection connection = new Connection();
		connection.connectToStorage();
		
		MyPath myPath= connection.getMyPath();
		ExtensionList extensionList=connection.getExtension();
		UserDatabase userDatabase=connection.getUsers();
		User userLoggedin = connection.getLogin();
		model.MyFile myfile= new MyFile();
		model.MyDirectory myDirectory = new MyDirectory();
		
		myPath.setTmpPath(myPath.getPath());
		
		
		
		connection.disconnectFromStorage();

	}

}
