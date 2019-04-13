package com.sdsoon.http;

import com.sdsoon.http.init.HttpServerInit;
import lombok.extern.slf4j.Slf4j;
import org.tio.utils.jfinal.P;

/**
 * Created By Chr on 2019/4/12.
 */

@Slf4j
public class HttpServerShowcaseStarter {

    /**
     * @param args
     * @throws Exception
     * @author Chr
     */
    public static void main(String[] args) throws Exception {
        P.use("app.properties");

        HttpServerInit.init();
    }

    /**
     * @author Chr
     */
    public HttpServerShowcaseStarter() {
    }
}
