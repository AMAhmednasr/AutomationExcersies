package com.automationexercises.drivers;

public enum Browser {


    CHROME {
        @Override
        public AbsrtactDriver getDriverFactory() {
            return new ChromeFactory();
        }
    },
    EDGE {
        @Override
        public AbsrtactDriver getDriverFactory() {
            return new EdgeFactory();
        }
    },
    FIREFOX {
        @Override
        public AbsrtactDriver getDriverFactory() {
            return new FirefoxFactory();
        }
    },
    SAFARI {
        @Override
        public AbsrtactDriver getDriverFactory() {
            return new SafariFactory();
        }
    };

    public abstract AbsrtactDriver getDriverFactory();

}
