package com.galaxy.authentication;

import com.galaxy.authentication.domain.entity.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class H2DatabaseTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Before
    public void startup() throws IOException, SQLException {
        executeFromFile("tuser.sql");
    }

    @Test
    public void queryUser(){
        RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
        List<User> userList = jdbcTemplate.query("select * from tuser", rowMapper);
        Assert.assertFalse(userList.isEmpty());
    }

    public void executeFromFile(String fileName) throws SQLException, IOException {
        String sqlPath = ResourceUtils.getURL("classpath:").getPath() + "\\sql\\";
        sqlPath = URLDecoder.decode(sqlPath, "utf-8") + fileName;
        String sqlStr = readToString(sqlPath);
        this.jdbcTemplate.execute(sqlStr);
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
