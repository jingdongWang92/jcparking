package com.jcble.jcparking.api.test.admin;

import com.jcble.jcparking.api.test.TestBase;
import com.jcble.jcparking.api.test.TestHandle;
import com.jcble.jcparking.api.test.TestParamMap;

public class ChargeTest extends TestBase {

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
     * 4.1 获取场内车辆信息
     * @throws Exception
     */
    @org.junit.Test
    public void setJPushId() throws Exception {
    	getTest("/parkinglot/cars", new TestParamMap() {
    		@Override
    		public void setValue() {
    			V("operatorId", "1");
    		}
    	});
    }
    
    /**
     * 4.2 停车订单录入
     * @throws Exception
     */
    @org.junit.Test
    public void createParkingOrder() throws Exception {
    	postTest("/order", new TestParamMap() {
    		@Override
    		public void setValue() {
    			V("operatorId", "1");
    			V("parkingId", "1");
    			V("carNumber", "1");
    			V("carType", "1");
    		}
    	});
    }
    
    /**
     * 4.3 订单信息修改
     * @throws Exception
     */
    @org.junit.Test
    public void updateParkingOrder() throws Exception {
    	putTest("/order/1", new TestParamMap() {
    		@Override
    		public void setValue() {
    			V("operatorId", "1");
    			V("parkingId", "1");
    			V("carNumber", "1");
    			V("carType", "1");
    		}
    	});
    }
    
	/**
	 * 4.4 收费记录/历史未收费订单
	 * 
	 * @throws Exception
	 */
	@org.junit.Test
	public void orders() throws Exception {
		getTest("/orders", new TestParamMap() {
			@Override
			public void setValue() {
				V("operatorId", "1");
				V("startTime", "");
				V("endTime", "");
				V("orderStatus", "1"); // 订单状态 1:未收费 查询未收费记录时orderStatus=1
				V("carNumber", "");
			}
		});
	}
	
	/**
	 * 4.5 收费员账单统计
	 * 
	 * @throws Exception
	 */
	@org.junit.Test
	public void orderStatistics() throws Exception {
		getTest("/order/statistics", new TestParamMap() {
			@Override
			public void setValue() {
				V("operatorId", "1");
			}
		});
	}
	
	/**
	 * 4.6 未收费订单详情
	 * 
	 * @throws Exception
	 */
	@org.junit.Test
	public void orderDetail() throws Exception {
		getTest("/order/14", new TestParamMap() {
			@Override
			public void setValue() {
			}
		});
	}
	
	/**
	 * 4.7 收费
	 * 
	 * @throws Exception
	 */
	@org.junit.Test
	public void charge() throws Exception {
		putTest("/order/charge", new TestParamMap() {
			@Override
			public void setValue() {
				V("orderId", "1");
			}
		});
	}
	
	/**
	 * 4.8 确认收费
	 * 
	 * @throws Exception
	 */
	@org.junit.Test
	public void chargeConfirm() throws Exception {
		putTest("/order/charge_confirm", new TestParamMap() {
			@Override
			public void setValue() {
				V("orderId", "13");
				V("operatorId", "1");
			}
		});
	}
	
	/**
	 *4.9 免单设置
	 * 
	 * @throws Exception
	 */
	@org.junit.Test
	public void setOrderFree() throws Exception {
		putTest("/order/free", new TestParamMap() {
			@Override
			public void setValue() {
				V("operatorId", "1");
				V("reason", "1");
				V("orderId", "1");
			}
		});
	}
	
	/**
	 * 4.10 离场
	 * 
	 * @throws Exception
	 */
	@org.junit.Test
	public void carOut() throws Exception {
		String parkingId = "1";
		deleteTest("parkinglot/car/"+parkingId, new TestParamMap() {
			@Override
			public void setValue() {
			}
		});
	}
	
	/**
	 * 入场
	 * 
	 * @throws Exception
	 */
	@org.junit.Test
	public void carIn() throws Exception {
		String parkingId = "6";
		postTest("parkinglot/car/"+parkingId, new TestParamMap() {
			@Override
			public void setValue() {
			}
		});
	}
    
}

