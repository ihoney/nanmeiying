package com.lb.web.listener;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionCounter implements HttpSessionListener {
    private JdbcTemplate jdbcTemplate;

    public SessionCounter() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        jdbcTemplate = (JdbcTemplate) context.getBean("jdbcTemplate");
    }

    /* Session创建事件 */
    public void sessionCreated(HttpSessionEvent se) {
    }

    /* Session失效事件 */
    public void sessionDestroyed(HttpSessionEvent se) {
        String sessionId = se.getSession().getId();
        String sql = "update manager set loginStatus = 0 where sessionId = '" + sessionId + "'";
        jdbcTemplate.update(sql);
    }
}