package test;

import java.util.Scanner;

import exceptions.file.CreateFileException;
import formatComponent.ExtensionList;

import model.MyPath;
import modelS.MyDirectory;
import modelS.MyFile;
import storage.Connection;
import usersComponent.User;
import usersComponent.UserDatabase;

public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("DobroDosli u komadnu liniju!!!");
		connectionComponent.Connection connection = new Connection();
		connection.connectToStorage();

		MyPath myPath = connection.getMyPath();
		ExtensionList extensionList = connection.getExtension();
		UserDatabase userDatabase = connection.getUsers();
		User userLoggedin = connection.getLogin();
		model.MyFile myfile = new MyFile();
		model.MyDirectory myDirectory = new MyDirectory();

		myPath.setTmpPath(myPath.getPath());
		Boolean krajWhile = false;
		Scanner scanner = new Scanner(System.in);
		String name = null;
		String path = null;
		String destStorage = null;
		String destDekstop = null;
		System.out.println("Unesite opciju koju zelite raditi");
		while (true) {
			String key = scanner.nextLine();
			switch (key) {
			case "createFile":
				if(!userLoggedin.isDozvolaZaSnimanje()) {
					System.out.println("Ovaj korisnik ne moze ovo raditi");
					break;
				}
				System.out.println("Unesi ime fajla ");
				name = scanner.nextLine();

				System.out.println("Unesi path fajla ");
				path = scanner.nextLine();
				myfile.create(name, path, extensionList);
				break;
			case "deleteFile":
				if(!userLoggedin.isDozvolaZaBrisanje()) {
					System.out.println("Ovaj korisnik ne moze ovo raditi");
					break;
				}
				System.out.println("Unesi path fajla ");
				path = scanner.nextLine();
				myfile.delete(path);
				break;
			case "createFileMeta":
				if(!userLoggedin.isDozvolaZaSnimanje()) {
					System.out.println("Ovaj korisnik ne moze ovo raditi");
					break;
				}
				System.out.println("Unesi ime fajla ");
				name = scanner.nextLine();
				System.out.println("Unesi path fajla ");
				path = scanner.nextLine();
				myfile.createWithMetadata(name, path, extensionList);
				break;
			case "dowlageFile":
				if(!userLoggedin.isDozvolaZaPreuzimanje()) {
					System.out.println("Ovaj korisnik ne moze ovo raditi");
					break;
				}
				System.out.println("Unesi fajl na storage ");
				destStorage = scanner.nextLine();
				System.out.println("Unesi destinaciju na despotu ");
				destDekstop = scanner.nextLine();
				myfile.download(destStorage, destDekstop);
				break;
			case "uploadFile":
				System.out.println("Unesi fajl na despotu ");
				destDekstop = scanner.nextLine();
				System.out.println("Unesi destinaciju na storage ");
				destStorage = scanner.nextLine();
				myfile.upload(destDekstop, destStorage, extensionList);
				break;
			case "moveFile":
				if(!userLoggedin.isDozvolaZaPreuzimanje()) {
					System.out.println("Ovaj korisnik ne moze ovo raditi");
					break;
				}
				System.out.println("Unesi fajl na storage ");
				destStorage = scanner.nextLine();
				System.out.println("Unesi destinaciju na storage ");
				destDekstop = scanner.nextLine();
				myfile.move(destStorage, destDekstop);
				break;
			case "moveFileMeta":
				if(!userLoggedin.isDozvolaZaPreuzimanje()) {
					System.out.println("Ovaj korisnik ne moze ovo raditi");
					break;
				}
				System.out.println("Unesi fajl na storage ");
				destStorage = scanner.nextLine();
				System.out.println("Unesi destinaciju na storage ");
				destDekstop = scanner.nextLine();
				myfile.moveWithMetadata(destStorage, destDekstop);
				break;
				
			case "renameFile":
				if(!userLoggedin.isDozvolaZaSnimanje()) {
					System.out.println("Ovaj korisnik ne moze ovo raditi");
					break;
				}
				System.out.println("Unesi ime");
				name = scanner.nextLine();
				System.out.println("Unesi destinaciju na storage za promjeni imena");
				path = scanner.nextLine();
				myfile.rename(name, path);
				break;

			case "addUser":
				if(!userLoggedin.isGlavniAdmin()) {
					System.out.println("Ovaj korisnik ne moze ovo raditi");
					break;
				}
				while (true) {
					System.out.println("Username");
					String noviUsername = scanner.nextLine();
					System.out.println("Password");
					String novaSifra = scanner.nextLine();
					Boolean snimanje, brisanje, preuzimanje;
					System.out.println("Snimanje (True/False)");
					snimanje = scanner.nextBoolean();
					System.out.println("brisanje (True/False)");
					brisanje = scanner.nextBoolean();
					System.out.println("preyimanje (True/False)");
					preuzimanje = scanner.nextBoolean();

					User noviKorisnik = new User(noviUsername, novaSifra, false, snimanje, brisanje, preuzimanje);
					userDatabase.getUsers().add(noviKorisnik);
					
					System.out.println("Da li zelite da unesete jos korisnika?(da/ne)");
					if(scanner.hasNext()) {
						
						scanner.nextLine();
					}
					String daNe = scanner.nextLine();
					if (daNe.equalsIgnoreCase("ne")) {
						break;
					}

				}

				break;
				
			case"addExtension":
				if(!userLoggedin.isGlavniAdmin()) {
					System.out.println("Ovaj korisnik ne moze ovo raditi");
					break;
				}
				while(true) {
					System.out.println("Unesite extension:");
					String extension = scanner.nextLine();
					extensionList.getExtensionList().add(extension);
					System.out.println("Da li zelite da unesete jos extension?(da/ne)");
					
					
					String daNeE = scanner.nextLine();
					if (daNeE.equalsIgnoreCase("ne")) {
						break;
					}
				}
				break;
			case "disconnect":
				krajWhile = true;
				break;
			default:
				System.out.println("Pogresna opcija");

			}
			if (krajWhile) {
				break;
			}

		}
		System.out.println("Program je zavrsen");

		connection.disconnectFromStorage();

	}

}
