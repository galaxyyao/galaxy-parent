package com.galaxy.common.util;

import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class DateUtilTest {
	@Test
	public void parseFromDateTest() {
		String originalDateTimeText = "2018-04-04 12:30:00";
        LocalDateTime dateTime = DateUtil.parseToDateTime(originalDateTimeText);
        String parsedDateTimeText = DateUtil.parseFromDateTime(dateTime);
        Assert.assertEquals(originalDateTimeText, parsedDateTimeText);
	}
}
