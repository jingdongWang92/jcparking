package com.jcble.jcparking.api.test.user;

import com.jcble.jcparking.api.test.TestBase;
import com.jcble.jcparking.api.test.TestHandle;
import com.jcble.jcparking.api.test.TestParamMap;

public class UserTest extends TestBase {

    private String host = "http://localhost:8080/jcparking-api/";

    // 是否打印接口返回结果
    private boolean showResult = true;

    private TestHandle testHandle = null;
    
    private String userId = "429385968";

    @Override
    public TestHandle getTestHandle() {
        if (null == testHandle) {
            testHandle = new TestHandle(host, showResult);
        }
        return testHandle;
    }
    
    /**
     * 5.1 用户注册
     * @throws Exception
     */
    @org.junit.Test
    public void userRegister() throws Exception {
    	postTest("/user", new TestParamMap() {
    		@Override
    		public void setValue() {
    			V("account", "18782207486");
    			V("password", "123456");
    		}
    	});
    }
    
    /**
     * 5.2 用户注册
     * @throws Exception
     */
    @org.junit.Test
    public void login() throws Exception {
    	postTest("/user/login", new TestParamMap() {
    		@Override
    		public void setValue() {
    			V("account", "18782207486");
    			V("password", "123456");
    		}
    	});
    }
    
    /**
     * 5.3 用户获取个人信息
     * @throws Exception
     */
    @org.junit.Test
    public void getUserInfo() throws Exception {
    	getTest("/user/"+userId, new TestParamMap() {
    		@Override
    		public void setValue() {
    		}
    	});
    }
    
    /**
     * 5.4 用户修改密码
     * @throws Exception
     */
    @org.junit.Test
    public void updatePassword() throws Exception {
    	putTest("/user/password", new TestParamMap() {
    		@Override
    		public void setValue() {
    			V("account", "18782207486");
    			V("oldPassword", "123456");
    			V("password", "123456");
    		}
    	});
    }
    
    /**
     * 5.4 用户修改密码
     * @throws Exception
     */
    @org.junit.Test
    public void findPassword() throws Exception {
    	postTest("/user/password", new TestParamMap() {
    		@Override
    		public void setValue() {
    			V("account", "18782207486");
    			V("password", "123456");
    			V("vcode", "1234");
    		}
    	});
    }
    
    /**
     * 5.6 绑定微信账号
     * @throws Exception
     */
    @org.junit.Test
    public void linkWechat() throws Exception {
    	postTest("/user/"+userId+"/wechat", new TestParamMap() {
    		@Override
    		public void setValue() {
    			V("wechatAcc", "zhangsan");
    			V("nickname", "zhangsan");
    		}
    	});
    }
    
    /**
     * 5.7 解除微信账号绑定
     * @throws Exception
     */
    @org.junit.Test
    public void deleteWechat() throws Exception {
    	deleteTest("/user/"+userId+"/wechat/5", new TestParamMap() {
    		@Override
    		public void setValue() {
    		}
    	});
    }
    
    /**
     * 5.8 用户获取个人车辆信息
     * @throws Exception
     */
    @org.junit.Test
    public void getUserCars() throws Exception {
    	getTest("/cars/"+userId, new TestParamMap() {
    		@Override
    		public void setValue() {
    		}
    	});
    }
    
    /**
     * 5.9 添加用户车辆信息
     * @throws Exception
     */
    @org.junit.Test
    public void addCar() throws Exception {
    	postTest("/car", new TestParamMap() {
    		@Override
    		public void setValue() {
    			V("userId","429385968");
    			V("carNumber","111111");
    		}
    	});
    }
    
    /**
     * 5.10 删除用户绑定车辆信息
     * @throws Exception
     */
    @org.junit.Test
    public void deleteCar() throws Exception {
    	deleteTest("/car/4", new TestParamMap() {
    		@Override
    		public void setValue() {
    		}
    	});
    }
    
    /**
     * 5.11 预约车位
     * @throws Exception
     */
    @org.junit.Test
    public void reservationParking() throws Exception {
    	postTest("/reservation", new TestParamMap() {
    		@Override
    		public void setValue() {
    			V("userId",userId);
    			V("carNumber","111111");
    			V("duration","60");
    			V("payFee","4");
    			V("parkinglotId","1");
    		}
    	});
    }
    
    /**
     * 5.12 取消预约
     * @throws Exception
     */
    @org.junit.Test
    public void reservationCancel() throws Exception {
    	postTest("/reservation/9", new TestParamMap() {
    		@Override
    		public void setValue() {
    		}
    	});
    }
    
    /**
     * 5.13 获取个人停车订单记录
     * @throws Exception
     */
    @org.junit.Test
    public void getUserOrders() throws Exception {
    	getTest("user/"+userId+"/orders", new TestParamMap() {
    		@Override
    		public void setValue() {
    		}
    	});
    }
    
    /**
     * 5.15 获取个人预约记录
     * @throws Exception
     */
    @org.junit.Test
    public void getUserReservations() throws Exception {
    	getTest("reservations/"+userId, new TestParamMap() {
    		@Override
    		public void setValue() {
    		}
    	});
    }
    
    /**
     * 5.16 获取车辆进行中的预约记录信息
     * @throws Exception
     */
    @org.junit.Test
    public void getReservationIngByCarId() throws Exception {
    	getTest("car/6/reservation", new TestParamMap() {
    		@Override
    		public void setValue() {
    		}
    	});
    }
    /**
     * 5.17 获取车辆进行中的停车订单信息
     * @throws Exception
     */
    @org.junit.Test
    public void getOrderByCarId() throws Exception {
    	getTest("car/6/order", new TestParamMap() {
    		@Override
    		public void setValue() {
    		}
    	});
    }
    
    /**
     * 5.18 获取车辆包月信息
     * @throws Exception
     */
    @org.junit.Test
    public void getWhitelistByCar() throws Exception {
    	getTest("car/111111/whitelist", new TestParamMap() {
    		@Override
    		public void setValue() {
    			V("parkinglotId","1");
    		}
    	});
    }
    
    /**
	 * 5.19 获取附近停车场
	 * 
	 * @throws Exception
	 */
	@org.junit.Test
	public void getNeighborhoodParlingLot() throws Exception {
		getTest("/parkinglots/neighborhood", new TestParamMap() {
			@Override
			public void setValue() {
				V("range","10000");
				V("coordinateX","106.527485");
				V("coordinateY","29.547812");
			}
		});
	}
    
	/**
	 * 5.20 获取停车场详情
	 * 
	 * @throws Exception
	 */
	@org.junit.Test
	public void getParkinglotDetail() throws Exception {
		getTest("/parkinglot/1", new TestParamMap() {
			@Override
			public void setValue() {
			}
		});
	}
	
	/**
	 * 5.25 用户消息
	 * 
	 * @throws Exception
	 */
	@org.junit.Test
	public void getUserMessages() throws Exception {
		getTest("/messages/"+userId, new TestParamMap() {
			@Override
			public void setValue() {
			}
		});
	}
	
	/**
	 * 5.26 用户阅读推送消息
	 * 
	 * @throws Exception
	 */
	@org.junit.Test
	public void getMessageDetail() throws Exception {
		getTest("/message/1", new TestParamMap() {
			@Override
			public void setValue() {
			}
		});
	}
	
	/**
	 * 5.27  用户阅读推送消息
	 * 
	 * @throws Exception
	 */
	@org.junit.Test
	public void readMessage() throws Exception {
		putTest("/message/1", new TestParamMap() {
			@Override
			public void setValue() {
			}
		});
	}
}

