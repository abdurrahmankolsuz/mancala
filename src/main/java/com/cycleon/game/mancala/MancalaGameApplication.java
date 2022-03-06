package com.cycleon.game.mancala;

import com.cycleon.game.mancala.initializer.DbMigration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class MancalaGameApplication {

	public static void main(String[] args) throws SQLException {
		DbMigration dbMigration = new DbMigration();
		dbMigration.initDb();
		SpringApplication.run(MancalaGameApplication.class, args);
	}

}
