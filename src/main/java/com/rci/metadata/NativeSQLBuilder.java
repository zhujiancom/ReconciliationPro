package com.rci.metadata;

public class NativeSQLBuilder {
	public static final String QUERY_ALL_DISH="select rtrim(ch_dishno) 'ch_dishno',rtrim(vch_dishname) 'vch_dishname',rtrim(ch_typeno) 'ch_typeno',rtrim(ch_seriesno) 'ch_seriesno',num_price1,ch_stopflag from dbo.v_bt_dish";
	/* 查询单个菜品 */
	public static final String QUERY_DISH_BY_NO="select rtrim(ch_dishno) 'ch_dishno',vch_dishname,rtrim(ch_typeno) 'ch_typeno',num_price1,ch_stopflag from dbo.v_bt_dish where ch_dishno=?";
	/* 查询单个菜品类型 */
	public static final String QUERY_DISH_TYPE_BY_NO="select rtrim(ch_typeno) 'ch_typeno',vch_typename from dbo.cybr_bt_dish_type where ch_typeno=?";
	/* 查询某个菜品大类下的小类 */
	public static final String QUERY_DISH_TYPE_BY_SERIES_NO="select rtrim(ch_typeno) 'ch_typeno',vch_typename,ch_seriesno from dbo.cybr_bt_dish_type where ch_seriesno=?";
	/* 查询所有菜品 */
	public static final String QUERY_DISH_By_TYPENO="select rtrim(ch_dishno) 'ch_dishno',vch_dishname,rtrim(ch_typeno) 'ch_typeno',num_price1,ch_stopflag from dbo.v_bt_dish where ch_typeno=?";
	/* 查询所有 菜品类型*/
	public static final String QUERY_ALL_DISH_TYPE="select rtrim(ch_typeno) 'ch_typeno',rtrim(vch_typename) 'vch_typename',rtrim(ch_seriesno) 'ch_seriesno' from dbo.cybr_bt_dish_type";
	/* 查询所有 菜品大类*/
	public static final String QUERY_ALL_DISH_SERIES = "select rtrim(ch_seriesno) 'ch_seriesno', rtrim(vch_seriesname) 'vch_seriesname' from dbo.cybr_bt_dish_series";
	public static final String QUERY_DISH_SERIES_BY_NO="select rtrim(ch_seriesno) 'ch_seriesno', rtrim(vch_seriesname) 'vch_seriesname' from dbo.cybr_bt_dish_series where rtrim(ch_seriesno)=?";
	/* 查询order detail 信息*/
	public static final String QUERY_ORDERITEM="select rtrim(ord.ch_billno) 'billno',rtrim(ord.ch_payno) 'payno',rtrim(ord.ch_dishno) 'dishno',ord.ch_suitflag 'suitflag',rtrim(ord.ch_suitno) 'suitno',ord.num_num count,\n"
												+ "ord.num_back countback,ord.num_price 'price',ord.int_discount 'discount',ord.dt_operdate 'consumeTime' from dbo.v_u_orderdish ord \n"
												+ "where ord.ch_billno=?";
	/* 查询order detail 信息*/
	public static final String QUERY_ORDERITEM_BY_DATE="select rtrim(ord.ch_billno) 'billno',rtrim(ord.ch_payno) 'payno',rtrim(ord.ch_dishno) 'dishno',ord.ch_suitflag 'suitflag',rtrim(ord.ch_suitno) 'suitno',ord.num_num count,\n"
												+ "ord.num_back countback,ord.num_price 'price',ord.int_discount 'discount',ord.dt_operdate 'consumeTime' \n"
												+ "from dbo.v_u_orderdish ord \n"
												+ "left join dbo.v_u_checkout_master cmaster \n"
												+ "on ord.ch_billno = cmaster.ch_billno \n"
												+ "where cmaster.dt_operdate between ? and ?";
	/* 查询order 信息*/
	public static final String QUERY_ORDER="select rtrim(tab.ch_billno) 'billno',rtrim(tab.ch_payno) 'payno',detail.ch_paymodeno 'paymode', \n"
			+"cmaster.num_cost 'originamount',tab.dt_operdate 'opendesktime',rtrim(tab.ch_tableno) 'tableno', \n"
			+ "cmaster.dt_operdate 'checkouttime',detail.num_realamount 'realamount' \n"
			+ "from dbo.v_u_table tab \n"
			+ "join dbo.v_u_checkout_master cmaster \n"
			+ "on tab.ch_billno=cmaster.ch_billno \n"
			+ "join dbo.v_u_checkout_detail detail \n"
			+ "on cmaster.ch_payno = detail.ch_payno \n"
			+ "where cmaster.dt_operdate between ? and ?";
	
	/* 查询所有 支付方式*/
	public static final String QUERY_PAYMODES="select ch_paymodeno,vch_paymode,ch_incomeflag from dbo.v_bt_paymode";
	
	/* 查询所有桌号信息  */
	public static final String QUERY_TABLES="select rtrim(ch_tableno) 'table_no',vch_tablename 'table_name',rtrim(tb.ch_typeno) 'table_type',tbt.vch_typename 'table_type_name' from dbo.v_bt_table tb \n"
			+ "left join dbo.cybr_bt_table_type tbt \n"
			+ "on tb.ch_typeno = tbt.ch_typeno";
	
	public static final String QUERY_SUM="select sum(oar.real_amount) 'amount' from bus_tb_order_account_ref oar \n"
			+ "left join bus_tb_order o on oar.order_no=o.order_no \n"
			+ "where oar.post_time=? \n"
			+ "and oar.accno=? \n"
			+ "and o.framework=?";
	
}
