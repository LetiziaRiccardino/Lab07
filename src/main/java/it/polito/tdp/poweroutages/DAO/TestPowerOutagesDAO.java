package it.polito.tdp.poweroutages.DAO;

import java.sql.Connection;

public class TestPowerOutagesDAO {

	public static void main(String[] args) {
		
		try {
			Connection connection = ConnectDB.getConnection();
			connection.close();
			System.out.println("Connection Test PASSED");
			
			PowerOutageDAO dao = new PowerOutageDAO() ;
			
			System.out.println(dao.getNercList()) ;
			System.out.println("Connection TestNerc PASSED");
			
			System.out.println(dao.getOutagesList(dao.getNercList().get(1))) ;
			System.out.println("Connection TestOutages PASSED");

		} catch (Exception e) {
			System.err.println("Test FAILED");
		}

	}

}
