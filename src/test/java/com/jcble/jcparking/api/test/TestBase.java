package com.jcble.jcparking.api.test;

public abstract class TestBase {

    protected void postTest(String url, TestParam param) throws Exception {
        this.getTestHandle().postTest(url, param.get());
    }
    
    protected void putTest(String url, TestParam param) throws Exception {
    	this.getTestHandle().putTest(url, param.get());
    }

    protected void getTest(String url,TestParam param) throws Exception {
        this.getTestHandle().getTest(url,param.get());
    }
    
    protected void deleteTest(String url,TestParam param) throws Exception {
    	this.getTestHandle().deleteTest(url,param.get());
    }

    protected abstract TestHandle getTestHandle();
}
