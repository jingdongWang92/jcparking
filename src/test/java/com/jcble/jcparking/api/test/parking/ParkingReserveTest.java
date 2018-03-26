package com.jcble.jcparking.api.test.parking;

import com.jcble.jcparking.api.test.TestBase;
import com.jcble.jcparking.api.test.TestHandle;
import com.jcble.jcparking.api.test.TestParamMap;

public class ParkingReserveTest extends TestBase {

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
     * 预约车位
     * @throws Exception
     */
    @org.junit.Test
    public void userRegister() throws Exception {
    	postTest("/reservation", new TestParamMap() {
    		@Override
    		public void setValue() {
    			V("userId", "de4262cd89a7426d99ae88c5c4b9d37d");
    			V("carNumber", "111111");
    			V("payFee", "4");
    			V("parkinglotId", "1");
    			V("duration", "60");
    		}
    	});
    }
    
}

