package com.papaswatch.psw.config;

public abstract class Constant {

    public abstract static class DB {
        public static final String JPA_PROP = "jpaProperties";

        public static final String DS = "dataSource";
        public static final String EM = "entityManagerFactory";
        public static final String TX = "transactionManager";
    }

    public abstract static class USER {
        public static final String SESSION = "USER_SESSION";

        public abstract static class VALIDATION {
            public static final String BANK = "bank validation ::";
            public static final String BUSINESS_REGISTRATION_CERTIFICATE = "Business registration certificate validation ::";
        }
    }
}