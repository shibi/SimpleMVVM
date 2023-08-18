package com.bzcode.simplemvvm.domain.utils;


public interface Config {

    /**
     * App version
     * */
    String APP_VERSION = "1.0";


    /**
     * url prefix
     * */
    String DOMAIN_NAME = "https://bzcode.free.beeceptor.com/";


    /**
     * base url for api calls
     * */
    String BASE_URL = DOMAIN_NAME;


    /**
     * API key normal, edit api key here.
     * */
    String API_KEY = "@Spa_ApiKey0123#";


    /**
     * DO NOT EDIT.....
     * Encrypted API key, used in network request for security
     * Do not edit this ..  edit only API_KEY variable above.
     * */
    String ENCRYPTED_API_KEY = Utility.base64Encrypt(Config.API_KEY);


    /**
     * To enable or disable log
     * */
    boolean IS_DEVELOPMENT = true;

    /**
     * Enable log for tracing request links
     * */
    boolean LOG = Config.IS_DEVELOPMENT;

    /**
     * Enable log for tracing request links
     * */
    String DB_NAME = "AppLocalDB#JavaMVVM";


    /**
     * shared preference local storage name
     * */
    String SHARED_PREF_NAME ="JavaMvvm2";


    /**
     * http client connection read , write  and time out in seconds
     * */
    int HTTP_CONNECTION_TIMEOUT_IN_SEC = 60;
    int HTTP_CONNECTION_READ_TIMEOUT_IN_SEC = 60;
    int HTTP_CONNECTION_WRITE_TIMEOUT_IN_SEC = 60;


    int SPLASH_TIMEOUT = 0;

    String SCAN_ID_PREFIX = "AGS";

}


