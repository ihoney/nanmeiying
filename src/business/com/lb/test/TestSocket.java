package com.lb.test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-7-12
 * Time: 上午10:23
 * To change this template use File | Settings | File Templates.
 */
public class TestSocket {

    public static void main(String[] args) throws IOException {
//        URL url = new URL("http://localhost:8080/customer/checkLogin.do?account=13333333333&password=rootadmin");
        URL url = new URL("http://localhost:8080/customer/checkLogin.do?account=13333333333&password=rootadmin&JSESSIONID=6580B504EC14F937BE71E37C48F9706C");
        URLConnection connection = url.openConnection();
        InputStream inputStream = connection.getInputStream();
        int len = 0;
        byte[] buf= new byte[1024];
        while ((len = inputStream.read(buf)) > 0){
            System.out.println(new String(buf,0,len));
        }
    }
}
