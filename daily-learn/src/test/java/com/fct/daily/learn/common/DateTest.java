package com.fct.daily.learn.common;

import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

/**
 * DateTest
 *
 * @author xstarfct
 * @version 2020-10-26 19:52
 */
@Slf4j
public class DateTest {
    
    @Test
    public void before() {
        Date saleEndTime = DateUtils.addDays(new Date(), 10);
        log.info("result = {}", saleEndTime.before(new Date()));
    }
}
