# 串口协议  操作员工卡

## 本程序说明
  本程序是关于Android机器打开串口，并操作串口的示例。需要Android机器有串口，刚好我们演示的机器是Android工控机，直接有串口，可以直接连接设备。并且知道该串口的名字是`"/dev/ttymxc2"`。机器不一样，该串口名称不一样。根据操作的串口设备不同，目前串口的通信设备是员工卡读卡器，像读卡器输出不同的指令，比如扣款 、消费、查询、来简单实现操作通过串口控制串口设备的目的。本程序本质还是串口通信和串口通信协议解析。



## 本程序目录结构
```
 ---SerailProtocal_EmployeeCardControl【工程主目录】
    --app
    --build.gradle
    --libraries
        liba_vmc_serialport
        vmc_employee_card
    build.gradle
    setting.gradle
```

## 协议说明:

```c
 #define DEV_SENDVALUE    	0x01	//外设发送消费金额
 #define DEV_SENDCANCEL		0x02	//外设发送取消命令
 #define DEV_READSN		0x03    //读取机器序列号
 #define DEV_READLASTCONINFO 0x04	//读取最近一次消费卡余额功能
 #define DEV_READCONNECT	0x05	//读取是否成功链接服务器
```


## 协议定长为9字节
包头(2字节) + 功能号(1字节) + 数据(4字节) + 和较验(2字节)

## 协议示例:
 由控制端发送扣款金额为1元,消费机返回接收成功
  ---
 `发送->消费`
  ```c
  AA BB 01 0A 00 00 00 70 01
  ```
 `回复->控制端`
  ```c
  AA BB 01 FE 00 00 00 64 01
  ```

 `发送->消费` 取消本次消费
  ```c
  AA BB 02 00 00 00 00 67 01
  ```

## 通讯类错误编码:
```c
#define	    ERR_BALANCE_SUCCESS		0xF0	//扣款成功
#define	    ERR_CANCEL_SUCCESS		0xF1	//取消成功
#define	    ERR_OVER_TIME			0xFD	//过15S未刷卡，操作延时退出
#define	    ERR_COMM_SUCCESS		0xFE	//通讯成功
```
## 卡片操作类错误编码:
```c
	OPER_C_CARD_BAD,			/* 0x03	卡片已被损坏,请找管理员修复*/
	OPER_C_WRITE_ERR,			/* 0x04	写卡未完成*/
	OPER_C_CARD_DIFFER,			/* 0x05	非同一张卡操作*/
	OPER_C_CARD_OVERDUE,			/* 0x06	此卡已在别的设备消费*/
	OPER_C_ERROR_PASSWORD,			/* 0x07	扇区密码错误*/

	OPER_C_LEVEL_NOT_OPEN = 0x10,		/* 0x10	此卡级别未开放*/
	OPER_C_CARD_BLACK,				/* 0x11	此卡已挂失*/
	OPER_C_VAILD_ERROR1,			/* 0x12	有效期未生效*/
	OPER_C_VAILD_ERROR2,			/* 0x13	已过有效期*/
	OPER_C_NO_ACCESS_CARD,			/* 0x14	此卡未发卡*/
	OPER_C_CARD_BACK,				/* 0x15	此卡已退卡*/
	OPER_C_CARD_UNUSUAL,			/* 0x16	此卡发卡异常*/

	OPER_C_MONEY_LOW = 0x20,		/* 0x20	余额不足 */
	OPER_C_NOT_IN_THE_TIME_RANGE,		/* 0x21	不在消费时段内*/
	OPER_C_LIMIT_OUT,			/* 0x22	超过限制额度或次数*/
	OPER_C_MONEY_PAY_MAX,			/* 0x23	超过次最大消费金额*/
	OPER_C_MONEY_RECHARGE_MAX,		/* 0x24	超过次最大充值金额*/
	OPER_C_XF_ENABLE_TIMEPART_ERROR,	/* 0x25	消费餐段禁止*/

	OPER_C_NOT_ALLOWANCE = 0x30,		/* 0x30	此卡无补贴 */
	OPER_C_RE_ALLOWANCE,			/* 0x31	此卡已领取补贴 */
	OPER_C_OVER_ALLOWANCE,			/* 0x32	补贴超过额度 */
	OPER_C_ALLOWANCE_DATA_ERROR,		/* 0x33	未到领取时间 */
	OPER_C_NOT_ORDERING,			/* 0x34	此卡未订餐 */
	OPER_C_HAVE_ORDERING,			/* 0x35	此卡已订餐 */
	OPER_C_MONEY_ERROR,			/* 0x36	金额输入有误 */

	OPER_C_LOG_FULL = 0x50,			/* 0x50	记录已满*/
	OPER_C_LOG_REPEAT_TIME,			/* 0x51	间隔不足*/
	OPER_C_LOG_CONTINUE,			/* 0x52	连续刷卡*/
```

## 关于作者
   > * 有问题可以联系我
   > * email : lixiaodaoaaa@126.com
   > * http://weibo.com/lixiaodaoaaa