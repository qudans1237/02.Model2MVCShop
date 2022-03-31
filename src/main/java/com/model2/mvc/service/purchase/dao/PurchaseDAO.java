package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;

public class PurchaseDAO {
	
	public PurchaseDAO() {
		
	}
	public Purchase findPurchase(int tranNo)throws Exception{
		
		Connection con = DBUtil.getConnection();

		String sql = "select * "
				+ "				from transaction t,product p,users u "
				+ "								where t.prod_no=p.prod_no "
				+ "									and t.buyer_id=u.user_id "
				+ "									and t.tran_no = ?";
				
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, tranNo);

		ResultSet rs = stmt.executeQuery();

		System.out.println("DAO tranNo >>"+tranNo);
		Purchase purchase = new Purchase();
		while (rs.next()) {
			Product product = new Product();
			User user = new User();
			user.setUserId(rs.getString("buyer_id")); // join된 userId 정보로 가져온다
			product.setProdNo(rs.getInt("prod_no"));  // join된 상품번호 가져온다.
			purchase.setTranNo(rs.getInt("tran_no")); // 구매 번호 
			purchase.setPurchaseProd(product);
			purchase.setBuyer(user);
			purchase.setPaymentOption(rs.getString("payment_option"));// 지불방법
			purchase.setReceiverName(rs.getString("receiver_name")); //받는사람 일므
			purchase.setReceiverPhone(rs.getString("receiver_phone")); //받는사람 전화번호
			purchase.setDivyAddr(rs.getString("demailaddr")); // 배송지 주소
			purchase.setDivyRequest(rs.getString("dlvy_request")); //배송시 요구사항
			purchase.setTranCode(rs.getString("tran_status_code")); //구매상태코드
			purchase.setOrderDate(rs.getDate("order_data")); //구매 일자
			purchase.setDivyDate(rs.getString("dlvy_date")); //배송 희망 일자
		}
		System.out.println("DAO purchase >>"+purchase);
		//System.out.println("DAO purchaseVO >>"+rs.getString("buyer_id"));
		
		
		
		con.close();

		return purchase;


	}
	public Purchase findPurchase2 (int prodNo)throws Exception{
		Connection con = DBUtil.getConnection();
		
		String sql = "select t.tran_no "
				+ "from transaction t, product p "
				+ "where t.prod_no = p.prod_no "
				+ "and p.prod_no = ? ";
		PreparedStatement stmt =con.prepareStatement(sql);
		stmt.setInt(1,prodNo);
		
		ResultSet rs = stmt.executeQuery();
		
		Purchase purchase = new Purchase();
		while (rs.next()) {
		purchase.setTranNo(rs.getInt("tran_no"));
		}
		con.close();
		
		return purchase;
	}
public Map<String, Object> getPurchaseList(Search search,String buyerId) throws Exception {
	System.out.println("<<<<< PurchaseDao : getPurcaseList() 시작 >>>>>");
	System.out.println("받은 search : " + search);
	System.out.println("받은 buyerId : " + buyerId);
	
		Map<String,Object> map = new HashMap<String,Object>();
		
		Connection con = DBUtil.getConnection();
		
		String sql = "select "
				+ "t.tran_no, p.prod_no,u.user_id,t.receiver_name,t.receiver_phone,t.tran_status_code "
				+ "from transaction t,product p,users u "
				+ "where t.prod_no=p.prod_no "
				+ "	and t.buyer_id=u.user_id "
				+ "	and u.user_id=? ";
		
		System.out.println("sql print"+sql);
		PreparedStatement stmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

		
		stmt.setString(1, buyerId);
		ResultSet rs = stmt.executeQuery();
		System.out.println(buyerId);
		System.out.println("stmt print "+sql);
		rs.last();
		int total = rs.getRow();
		System.out.println("로우의 수:" + total);

		
		map.put("count", new Integer(total));

		rs.absolute(search.getPage() * search.getPageUnit() - search.getPageUnit()+1);
		
		System.out.println(search.getPage() * search.getPageUnit() - search.getPageUnit()+1);
		System.out.println("search.getPage():" + search.getPage());
		System.out.println("search.getPageUnit():" + search.getPageUnit());

		ArrayList<Purchase> list = new ArrayList<Purchase>();
		if (total > 0) {
			for (int i = 0; i < search.getPageUnit(); i++) {
				Purchase vo = new Purchase();
				User user = new User();
				user.setUserId(rs.getString("user_id"));
				System.out.println("rs>>>>>>>>>>>"+rs.getString("user_id"));
				System.out.println("rs>>>>>>>>>>>"+rs.getInt("prod_no"));
				vo.setReceiverName(rs.getString("receiver_name"));
				vo.setTranNo(rs.getInt("tran_no"));
				vo.setBuyer(user);
				//vo.setPaymentOption(rs.getString("payment_option"));
				vo.setReceiverPhone(rs.getString("receiver_phone"));
				//vo.setDivyAddr(rs.getString("demailaddr"));
				//vo.setDivyRequest(rs.getString("dlvy_request"));
				vo.setTranCode(rs.getString("tran_status_code"));
				System.out.println("tran_status_code: "+rs.getString("tran_status_code")+":  :");
				//vo.setOrderDate(rs.getDate("order_data"));
				//vo.setDivyDate(rs.getString("dlvy_date"));
				
				

				list.add(vo);
				if (!rs.next())
					break;
			}
		}
		System.out.println("list.size() : "+ list.size());
		map.put("list", list);
		System.out.println("map().size() : "+ map.size());

		con.close();
			
		return map;
	}
public HashMap<String,Object> getSaleList(Search search) throws Exception {
	
	Connection con = DBUtil.getConnection();
	
	String sql = "select * from USERS ";
	if (search.getSearchCondition() != null) {
		if (search.getSearchCondition().equals("0")) {
			sql += " where USER_ID='" + search.getSearchKeyword()
					+ "'";
		} else if (search.getSearchCondition().equals("1")) {
			sql += " where USER_NAME='" + search.getSearchKeyword()
					+ "'";
		}
	}
	sql += " order by USER_ID";

	PreparedStatement stmt = 
		con.prepareStatement(	sql,
													ResultSet.TYPE_SCROLL_INSENSITIVE,
													ResultSet.CONCUR_UPDATABLE);
	ResultSet rs = stmt.executeQuery();

	rs.last();
	int total = rs.getRow();
	System.out.println("로우의 수:" + total);

	HashMap<String,Object> map = new HashMap<String,Object>();
	map.put("count", new Integer(total));

	rs.absolute(search.getPage() * search.getPageUnit() - search.getPageUnit()+1);
	
	System.out.println(search.getPage() * search.getPageUnit() - search.getPageUnit()+1);
	System.out.println("searchVO.getPage():" + search.getPage());
	System.out.println("searchVO.getPageUnit():" + search.getPageUnit());

	ArrayList<Purchase> list = new ArrayList<Purchase>();
	if (total > 0) {
		for (int i = 0; i < search.getPageUnit(); i++) {
			Purchase vo = new Purchase();
			User uvo = null;
			Product pvo = null;
			uvo.setUserId(rs.getString("buyer_id"));
			pvo.setProdNo(rs.getInt("prod_no"));
			vo.setTranNo(rs.getInt("tran_no"));
			vo.setPurchaseProd(pvo);
			vo.setBuyer(uvo);
			vo.setPaymentOption(rs.getString("payment_option"));
			vo.setReceiverName(rs.getString("receiver_name"));
			vo.setReceiverPhone(rs.getString("receiver_phone"));
			vo.setDivyAddr(rs.getString("demailaddr"));
			vo.setDivyRequest(rs.getString("dlvy_request"));
			vo.setTranCode(rs.getString("tran_status_code"));
			vo.setOrderDate(rs.getDate("order_data"));
			vo.setDivyDate(rs.getString("dlvy_date"));
			
			list.add(vo);
			if (!rs.next())
				break;
		}
	}
	System.out.println("list.size() : "+ list.size());
	map.put("list", list);
	System.out.println("map().size() : "+ map.size());

	con.close();
		
	return map;
}
	
public void insertPurchase(Purchase purchase) throws Exception {
	
	Connection con = DBUtil.getConnection();

String sql = "insert into transaction values  "
		+ "(seq_transaction_tran_no.nextval,?,?,?,?,?,?,?,'001',sysdate,?)";
	
	PreparedStatement stmt = con.prepareStatement(sql);
	stmt.setInt(1, purchase.getTranNo());
	stmt.setString(3, purchase.getPaymentOption());
	stmt.setString(2, purchase.getBuyer().getUserId());
	stmt.setString(4, purchase.getReceiverName());
	stmt.setString(5, purchase.getReceiverPhone());
	stmt.setString(6, purchase.getDivyAddr());
	stmt.setString(7, purchase.getDivyRequest());
	//stmt.setDate(10, purchase.getOrderDate());// sysdate 로 구매일자 대체
	stmt.setString(8, purchase.getDivyDate());
	System.out.println("sql insert>>>>>>>>>>"+stmt);
	stmt.executeUpdate();
	
	
	
	con.close();
}
public void updatePurchase(Purchase purchase) throws Exception {
	
	Connection con = DBUtil.getConnection();

	String sql = "update TRANSACTION set PAYMENT_OPTION=?, RECEIVER_NAME=?, RECEIVER_PHONE=?, demailaddr=?, DLVY_REQUEST=?, DLVY_DATE=?   where Tran_No=?";
	
	PreparedStatement stmt = con.prepareStatement(sql);
	stmt.setString(1, purchase.getPaymentOption());
	stmt.setString(2, purchase.getReceiverName());
	stmt.setString(3, purchase.getReceiverPhone());
	stmt.setString(4, purchase.getDivyAddr());
	stmt.setString(5, purchase.getDivyRequest());
	stmt.setString(6, purchase.getDivyDate());
	stmt.setInt(7, purchase.getTranNo());
	System.out.println("sql>>>>>>>>>>>>"+stmt);
	stmt.executeUpdate();
	
	con.close();
}
public void updateTranCode(Purchase purchase) throws Exception {
	
	Connection con = DBUtil.getConnection();

	String sql = "update TRANSACTION set TRAN_STATUS_CODE=? where Tran_No=?";
	System.out.println("update>"+sql);
	System.out.println("purchase"+purchase);
	PreparedStatement stmt = con.prepareStatement(sql);
	stmt.setString(1, purchase.getTranCode());
	stmt.setInt(2, purchase.getTranNo());
	//stmt.executeUpdate();
	if(stmt.executeUpdate()==1) {
		System.out.println("성공");
	}
	con.close();
}
	
}