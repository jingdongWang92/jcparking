package com.jcble.jcparking.api.test.parking;

import com.jcble.jcparking.api.test.TestBase;
import com.jcble.jcparking.api.test.TestHandle;
import com.jcble.jcparking.api.test.TestParamMap;

public class CarPassedTest extends TestBase {

    private String host = "http://localhost:8080/jcparking-api/";

    // 是否打印接口返回结果
    private boolean showResult = true;

    private TestHandle testHandle = null;
    
    @Override
    public TestHandle getTestHandle() {
        if (null == testHandle) {
            testHandle = new TestHandle(host, showResult);
        }
        return testHandle;
    }
    
    /**
     * 过车
     * @throws Exception
     */
    @org.junit.Test
    public void userRegister() throws Exception {
    	postTest("/car/passed", new TestParamMap() {
    		@Override
    		public void setValue() {
    			V("carNumber", "渝111111");
    			V("parkinglotId", "1");
    			V("inOrOut", "1");//0 ：入场 1：出场
    		}
    	});
    }
    
}

