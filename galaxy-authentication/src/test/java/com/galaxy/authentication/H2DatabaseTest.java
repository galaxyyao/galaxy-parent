package com.galaxy.authentication;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class H2DatabaseTest {

    private Connection connection;

    @Before
    public void startup() throws IOException, SQLException {
        JdbcDataSource jdbcDataSource = new JdbcDataSource();
        jdbcDataSource.setUrl("jdbc:h2:mem:corm");
        jdbcDataSource.setUser("sa");
        jdbcDataSource.setPassword("");
        try {
            connection = jdbcDataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        executeFromFile("tuser.sql");
    }

    @After
    public void shutdown() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Ignore
    @Test
    public void query() throws SQLException {
        Statement statement = this.connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from tuser");
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1));
            System.out.println(resultSet.getString(2));
        }
        statement.close();
    }


    public void executeFromFile(String fileName) throws SQLException, IOException {
        Statement statement = this.connection.createStatement();
        String sqlPath = ResourceUtils.getURL("classpath:").getPath() + "\\sql\\";
        sqlPath = URLDecoder.decode(sqlPath, "utf-8") + fileName;
        String sqlStr = readToString(sqlPath);
        statement.execute(sqlStr);
        statement.close();
    }

    private String readToString(String fileName) {
        String encoding = "UTF-8";
        File file = new File(fileName);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return new String(filecontent, encoding);
        } catch (UnsupportedEncodingException e) {
            System.err.println("The OS doesn't support " + encoding);
            e.printStackTrace();
            return null;
        }
    }

}
