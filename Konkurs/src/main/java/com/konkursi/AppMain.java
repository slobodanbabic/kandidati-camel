package com.konkursi;

import org.apache.camel.main.Main;
import org.apache.commons.dbcp.BasicDataSource;

public class AppMain {

	public static void main(String[] args) throws Exception {
		Main camelMain = new Main();

		camelMain.configure().addRoutesBuilder(new RestController());
		camelMain.configure().addRoutesBuilder(new RestService());

		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl("jdbc:postgresql://localhost:5432/test");
		dataSource.setUsername("testuser");
		dataSource.setPassword("1234");
		camelMain.bind("myDataSource", dataSource);
		
		camelMain.run();

	}


}
