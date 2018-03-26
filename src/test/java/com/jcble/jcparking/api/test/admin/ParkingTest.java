package com.jcble.jcparking.api.test.admin;

import com.jcble.jcparking.api.test.TestBase;
import com.jcble.jcparking.api.test.TestHandle;
import com.jcble.jcparking.api.test.TestParamMap;

public class ParkingTest extends TestBase {

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
     * 2.1 获取4位手机验证码
     * @throws Exception
     */
    @org.junit.Test
    public void verify_code() throws Exception {
        postTest("common/verify_code", new TestParamMap() {
            @Override
            public void setValue() {
                V("mobile", "18782207486");
            }
        });
    }
    
    /**
     * 2.2 获取停车场收费规则
     * @throws Exception
     */
    @org.junit.Test
    public void parkinglotChargeRule() throws Exception {
        getTest("common/charge_rule", new TestParamMap() {
            @Override
            public void setValue() {
                V("parkinglotId", "1");
            }
        });
    }
    
    /**
     * 3.1 管理员登录
     * @throws Exception
     */
    @org.junit.Test
    public void login() throws Exception {
    	postTest("admin/login", new TestParamMap() {
            @Override
            public void setValue() {
                V("account", "18782207486");
                V("password", "111111");
            }
        });
    }
    
    /**
     * 3.2 修改密码
     * @throws Exception
     */
    @org.junit.Test
    public void findPassword() throws Exception {
    	putTest("admin/password", new TestParamMap() {
    		@Override
    		public void setValue() {
    			V("account", "18782207486");
    			V("password", "111111");
    			V("vcode", "1234");
    		}
    	});
    }
    
    /**
     * 3.3 获取停车场收费规则
     * @throws Exception
     */
    @org.junit.Test
    public void parkinglots() throws Exception {
        getTest("parkinglots", new TestParamMap() {
            @Override
            public void setValue() {
            	
            }
        });
    }
    
    /**
     * 3.4 获取车位信息
     * @throws Exception
     */
    @org.junit.Test
    public void parkings() throws Exception {
    	getTest("parkings", new TestParamMap() {
    		@Override
    		public void setValue() {
    			V("operatorId", "2");
    			V("lockStatus", "");
    			V("parkinglotId", "1");
    			V("parkingNo", "");
    			V("devBindStatus", "0");
    		}
    	});
    }
    
    /**
     * 3.5 绑定设备到车位
     * @throws Exception
     */
    @org.junit.Test
    public void bindDevices() throws Exception {
    	postTest("devices", new TestParamMap() {
    		@Override
    		public void setValue() {
    			V("operatorId", "1");
    			V("devices", "[{\"parkingId\":\"1\",\"serialNumber\":\"111\"}]");
    		}
    	});
    }
    
    /**
     * 3.6 获取告警消息
     * @throws Exception
     */
    @org.junit.Test
    public void getAlarmMessages() throws Exception {
    	getTest("alarm_messages", new TestParamMap() {
    		@Override
    		public void setValue() {
    			V("operatorId", "1");
    			V("parkinglotId", "1");
    			V("deviceType", "");
    			V("snagType", "0");
    		}
    	});
    }
    
    /**
     * 3.7 处理告警消息
     * @throws Exception
     */
    @org.junit.Test
    public void handleAlarmMessages() throws Exception {
    	putTest("alarm_messages", new TestParamMap() {
    		@Override
    		public void setValue() {
    			V("operatorId", "1");
    			V("alarmMessages", "1");
    		}
    	});
    }
    
    /**
     * 3.8 获取停车场区域
     * @throws Exception
     */
    @org.junit.Test
    public void getParkinglotArea() throws Exception {
    	getTest("/parkinglot/1/area", new TestParamMap() {
    		@Override
    		public void setValue() {
    		}
    	});
    }
    
    /**
     * 3.9 管理员未处理告警消息记录数
     * @throws Exception
     */
    @org.junit.Test
    public void getAlarmMsgCount() throws Exception {
    	getTest("/alarm_messages/count", new TestParamMap() {
    		@Override
    		public void setValue() {
    			V("operatorId", "1");
    		}
    	});
    }
    
    /**
     * 3.10 设置管理员极光推送id
     * @throws Exception
     */
    @org.junit.Test
    public void setJPushId() throws Exception {
    	putTest("/admin/jpush_id", new TestParamMap() {
    		@Override
    		public void setValue() {
    			V("account", "1");
    			V("jpushId", "1");
    		}
    	});
    }
    
    
}
