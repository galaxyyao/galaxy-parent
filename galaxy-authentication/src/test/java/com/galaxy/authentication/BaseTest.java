package com.galaxy.authentication;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.net.URLDecoder;
import java.sql.SQLException;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class BaseTest {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Before
    public void startup() throws IOException {
        executeFromFile();
    }

    public void executeFromFile() throws IOException {
        String sqlPath = URLDecoder.decode(ResourceUtils.getURL("classpath:").getPath() + "\\sql\\", "utf-8");
        File file = new File(sqlPath);
        String[] files = file.list();
        for (String fileName : files) {
            String sqlStr = readToString(sqlPath + fileName);
            this.jdbcTemplate.execute(sqlStr);
        }
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
