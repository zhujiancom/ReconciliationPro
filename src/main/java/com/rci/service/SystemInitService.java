package com.rci.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.rci.bean.entity.DishType;
import com.rci.bean.entity.account.Account;
import com.rci.bean.entity.base.DictGroup;
import com.rci.bean.entity.base.DictItem;
import com.rci.enums.CommonEnums.YOrN;
import com.rci.metadata.service.IDataTransformService;
import com.rci.service.base.IDictGroupService;
import com.rci.service.base.IDictItemService;

@Service("SystemInitService")
public class SystemInitService implements InitializingBean{
	@Resource(name="DataTransformService")
	private IDataTransformService transformService;
	@Resource(name="FetchMarkService")
	private IFetchMarkService markService;
	@Resource(name="AccountService")
	private IAccountService accService;
	@Resource(name="SchemeService")
	private ISchemeService schemeService;
	@Resource(name="DictionaryService")
	private IDictItemService dictItemService;
	@Resource(name="DictGroupService")
	private IDictGroupService dictGroupService;
	@Resource(name="DishTypeService")
	private IDishTypeService dishTypeService;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if(markService.isSystemInit()){
			return;
		}
		//1. 初始化菜品
		transformService.transformDishInfo();
		//2. 初始化支付方式
		transformService.transformPaymodeInfo();
		
		init();
		
		markService.rwInitSystem();//系统初始化完成标记
	}
	
	//硬编码初始数据
	public void init(){
		//1. 初始化账户
		initAccount();
		//2. 创建scheme
		initSchemes();
		//3. 初始化字典项
		initDictionary();
		//4. 设置饮料为不可打折菜品类型
		DishType dishType = dishTypeService.queryDishTypeByNo("23");
		dishType.setNotDiscount(YOrN.Y);
		dishTypeService.rwUpdate(dishType);
	}
	
	/**
	 * 
	 * Describle(描述)： 初始化 账户Account数据
	 *
	 * 方法名称：initAccount
	 *
	 * 所在类名：SystemInitService
	 *
	 * Create Time:2015年4月29日 上午9:43:46
	 *
	 */
	private void initAccount(){
		List<Account> accounts = new ArrayList<Account>();
		Account account1 = new Account("收银机","CASH_MACHINE","CASH");
		Account account2 = new Account("现金账户","CASH","CASH");
		Account account3 = new Account("储蓄卡账户","DEPOSIT","DEPOSIT");
		Account account4 = new Account("饿了么在线支付","ELE","VIRTUAL");
		Account account5 = new Account("饿了么活动补贴","FREE_ELE","VIRTUAL");
		Account account6 = new Account("美团账户","MT","VIRTUAL");
		Account account7 = new Account("大众点评团购账户","DPTG","VIRTUAL");
		Account account8 = new Account("美团外卖在线支付","MTWM","VIRTUAL");
		Account account9 = new Account("美团外卖活动补贴","FREE_MTWM","VIRTUAL");
		Account account10 = new Account("支付宝账户","TDD","VIRTUAL");
		Account account11 = new Account("免单账户","FREE","CASH");
		Account account12 = new Account("美团超级代金券在线支付","MT_SUPER","VIRTUAL");
		Account account13 = new Account("美团超级代金券立减10元","FREE_MT_SUPER","VIRTUAL");
		accounts.add(account1);
		accounts.add(account2);
		accounts.add(account3);
		accounts.add(account4);
		accounts.add(account5);
		accounts.add(account6);
		accounts.add(account7);
		accounts.add(account8);
		accounts.add(account9);
		accounts.add(account10);
		accounts.add(account11);
		accounts.add(account12);
		accounts.add(account13);
		accService.rwCreate(accounts.toArray(new Account[0]));
	}
	
	/**
	 * 
	 * Describle(描述)： 初始化scheme 数据
	 *
	 * 方法名称：initSchemes
	 *
	 * 所在类名：SystemInitService
	 *
	 * Create Time:2015年4月29日 上午9:43:12
	 *
	 */
	private void initSchemes(){
	}

	/**
	 * 
	 * Describle(描述)：初始化字典项数据
	 *
	 * 方法名称：initDictionary
	 *
	 * 所在类名：SystemInitService
	 *
	 * Create Time:2015年4月29日 上午9:54:18
	 *
	 */
	private void initDictionary(){
		List<DictGroup> groups = new ArrayList<DictGroup>();
		DictGroup group1 = new DictGroup("NUM_UNIT","数量单位","number unit");
		DictGroup group2 = new DictGroup("CURRENCY","货币种类","currency");
		DictGroup group3 = new DictGroup("EXPENSE","支出分类","expense");
		DictGroup group4 = new DictGroup("EARNING","收入分类","earning");
		DictGroup group5 = new DictGroup("MERCHANT","商家分类","merchant");
		DictGroup group6 = new DictGroup("PROJECT","项目分类","project");
		DictGroup group7 = new DictGroup("MEMBER","成员设置","member");
		groups.add(group1);groups.add(group2);groups.add(group3);groups.add(group4);
		groups.add(group5);groups.add(group6);groups.add(group7);
		dictGroupService.rwCreateDictGroup(groups.toArray(new DictGroup[0]));
		
		List<DictItem> items = new ArrayList<DictItem>();
		DictItem item1 = new DictItem("NUM_UNIT","PC");
		item1.setDescription("代金券张");
		item1.setItemCname("张");
		item1.setItemEname("piece");
		item1.setSeq(1);
		DictItem item2 = new DictItem("NUM_UNIT","PKG");
		item2.setDescription("套餐件");
		item2.setItemCname("件");
		item2.setItemEname("package");
		item2.setSeq(2);
		DictItem item3 = new DictItem("CURRENCY","RMB");
		item3.setDescription("人名币");
		item3.setItemCname("元");
		item3.setItemEname("rmb");
		item3.setSeq(1);
		DictItem item4 = new DictItem("EARNING","XSSR");
		item4.setDescription("销售收入");
		item4.setItemCname("销售收入");
		item4.setItemEname("sales revenue");
		item4.setSeq(1);
		DictItem item5 = new DictItem("EARNING","MFP");
		item5.setDescription("卖废品收入");
		item5.setItemCname("卖废品");
		item5.setItemEname("selling scrap");
		item5.setSeq(2);
		DictItem item6 = new DictItem("EARNING","QT");
		item6.setDescription("其他");
		item6.setItemCname("其他收入");
		item6.setItemEname("others");
		item6.setSeq(3);
		DictItem item7 = new DictItem("EXPENSE","YGXZ");
		item7.setDescription("员工薪资发放");
		item7.setItemCname("员工薪资");
		item7.setItemEname("staff salary");
		item7.setSeq(1);
		DictItem item8 = new DictItem("EXPENSE","YGFB");
		item8.setDescription("员工饭补");
		item8.setItemCname("员工饭补");
		item8.setItemEname("staff lanuch allowance");
		item8.setSeq(2);
		DictItem item9 = new DictItem("PROJECT","YYSR");
		item9.setDescription("营业收入");
		item9.setItemCname("营业收入");
		item9.setItemEname("business earning");
		item9.setSeq(1);
		items.add(item1);items.add(item2);items.add(item3);items.add(item4);items.add(item5);
		items.add(item6);items.add(item7);items.add(item8);items.add(item9);
		dictItemService.rwCreateDictItem(items.toArray(new DictItem[0]));
	}
}
