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

	public Purchase findPurchase(int tranNo) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = "select * " + "				from transaction t,product p,users u "
				+ "								where t.prod_no=p.prod_no "
				+ "									and t.buyer_id=u.user_id "
				+ "									and t.tran_no = ?";

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, tranNo);

		ResultSet rs = stmt.executeQuery();

		System.out.println("DAO tranNo >>" + tranNo);
		Purchase purchase = new Purchase();
		while (rs.next()) {
			Product product = new Product();
			User user = new User();
			user.setUserId(rs.getString("buyer_id")); // join된 userId 정보로 가져온다
			product.setProdNo(rs.getInt("prod_no")); // join된 상품번호 가져온다.
			purchase.setTranNo(rs.getInt("tran_no")); // 구매 번호
			purchase.setPurchaseProd(product);
			purchase.setBuyer(user);
			purchase.setPaymentOption(rs.getString("payment_option"));// 지불방법
			purchase.setReceiverName(rs.getString("receiver_name")); // 받는사람 일므
			purchase.setReceiverPhone(rs.getString("receiver_phone")); // 받는사람 전화번호
			purchase.setDivyAddr(rs.getString("demailaddr")); // 배송지 주소
			purchase.setDivyRequest(rs.getString("dlvy_request")); // 배송시 요구사항
			purchase.setTranCode(rs.getString("tran_status_code")); // 구매상태코드
			purchase.setOrderDate(rs.getDate("order_data")); // 구매 일자
			purchase.setDivyDate(rs.getString("dlvy_date")); // 배송 희망 일자
		}
		System.out.println("DAO purchase >>" + purchase);
		// System.out.println("DAO purchaseVO >>"+rs.getString("buyer_id"));

		con.close();

		return purchase;

	}

	public Purchase findPurchase2(int prodNo) throws Exception {
		Connection con = DBUtil.getConnection();

		String sql = "select t.tran_no "
				+ "from transaction t, product p "
				+ "where t.prod_no = p.prod_no "
				+ "and p.prod_no = ? ";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, prodNo);

		ResultSet rs = stmt.executeQuery();

		Purchase purchase = new Purchase();
		while (rs.next()) {
			purchase.setTranNo(rs.getInt("tran_no"));
		}
		con.close();

		return purchase;
	}

public Map<String, Object> getPurchaseList(Search search,String buyerId) throws Exception {
	System.out.println("============ PurchaseDao : getPurcaseList() 시작 =========");
	System.out.println("받은 search : " + search);
	System.out.println("받은 buyerId : " + buyerId);
	
	
		Map<String,Object> map = new HashMap<String,Object>();
		
		Connection con = DBUtil.getConnection();
		
		String sql = "select "
				+ "t.tran_no, p.prod_no,u.user_id,t.receiver_name,t.receiver_phone,t.tran_status_code from transaction t,product p,users u where ";
		//SearchCondition에 값이 있을 경우
		if (search.getSearchCondition() != null) {
			if ( search.getSearchCondition().equals("0") &&  !search.getSearchKeyword().equals("") ) {
				sql += " t.buyer_id LIKE '%" + search.getSearchKeyword() + "%'";
			} else if (search.getSearchCondition().equals("1")&&  !search.getSearchKeyword().equals("") ) {
				sql += " u.user_name LIKE '%" + search.getSearchKeyword() + "%'";
			}		
		}
		sql += "t.prod_no=p.prod_no(+) ";
		sql +="	and t.buyer_id=u.user_id ";
		sql +="	and u.user_id=?";
		sql +=" ORDER BY t.tran_no";
		System.out.println("sql print :"+sql);
		
		int totalCount = this.getTotalCount(sql);
		System.out.println("UserDAO :: totalCount  :: " + totalCount);
						
		sql = makeCurrentPageSql(sql, search);
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setString(1, buyerId);
		ResultSet rs = pStmt.executeQuery();
		
		System.out.println(buyerId);
		System.out.println("stmt print "+sql);
		
		List<Purchase> list = new ArrayList<Purchase>();
		
		while(rs.next()){
			Purchase purchase = new Purchase();
			User user = new User();
			purchase.setTranNo(rs.getInt("t.tran_no"));
			user.setUserId(rs.getString("user_id"));
			purchase.setReceiverName(rs.getString("receiver_name"));
			purchase.setTranNo(rs.getInt("tran_no"));
			purchase.setReceiverPhone(rs.getString("receiver_phone"));
			purchase.setTranCode(rs.getString("tran_status_code"));
			list.add(purchase);
			if (!rs.next()) { 
				break;
			}
		}
		//==> totalCount 정보 저장
		map.put("totalCount", new Integer(totalCount));
		//==> currentPage 의 게시물 정보 갖는 List 저장
		map.put("list", list);
		System.out.println("map.put list"+list);
		
		rs.close();
		pStmt.close();
		con.close();
		
		System.out.println("<<<<< PurchaseDAO : getPurchaseList() 종료 >>>>>");
		return map;
	}

	public Map<String, Object> getSaleList(Search search) throws Exception {
	System.out.println("<<<<< PurchaseDao : getSaleList() 시작 >>>>>");
	Map<String,Object> map = new HashMap<String,Object>();
		Connection con = DBUtil.getConnection();

		String sql = "select * from USERS ";
		if (search.getSearchCondition() != null) {
			if (search.getSearchCondition().equals("0")) {
				sql += " where USER_ID='" + search.getSearchKeyword() + "'";
			} else if (search.getSearchCondition().equals("1")) {
				sql += " where USER_NAME='" + search.getSearchKeyword() + "'";
			}
		}
		sql += " order by USER_ID";
		int totalCount = this.getTotalCount(sql);
		System.out.println("UserDAO :: totalCount  :: " + totalCount);
						
		sql = makeCurrentPageSql(sql, search);
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		
		System.out.println("stmt print "+sql);

		List<Purchase> list = new ArrayList<Purchase>();
		
		while(rs.next()){
			Purchase purchase = new Purchase();
			User user = new User();
			Product product = new Product();
			
			user.setUserId(rs.getString("buyer_id"));
			product.setProdNo(rs.getInt("prod_no"));
			purchase.setTranNo(rs.getInt("tran_no"));
			purchase.setPurchaseProd(product);
			purchase.setBuyer(user);
			purchase.setPaymentOption(rs.getString("payment_option"));
			purchase.setReceiverName(rs.getString("receiver_name"));
			purchase.setReceiverPhone(rs.getString("receiver_phone"));
			purchase.setDivyAddr(rs.getString("demailaddr"));
			purchase.setDivyRequest(rs.getString("dlvy_request"));
			purchase.setTranCode(rs.getString("tran_status_code"));
			purchase.setOrderDate(rs.getDate("order_data"));
			purchase.setDivyDate(rs.getString("dlvy_date"));
			list.add(purchase);
			if (!rs.next())
				break;
			}
		//==> totalCount 정보 저장
		map.put("totalCount", new Integer(totalCount));
		//==> currentPage 의 게시물 정보 갖는 List 저장
		map.put("list", list);
		System.out.println("map.put list"+list);

		con.close();

		return map;
		}
//		if (total > 0) {
//			for (int i = 0; i < search.getPageUnit(); i++) {
//				Purchase purchase = new Purchase();
//				User user = null;
//				Product product = null;
//				user.setUserId(rs.getString("buyer_id"));
//				product.setProdNo(rs.getInt("prod_no"));
//				purchase.setTranNo(rs.getInt("tran_no"));
//				purchase.setPurchaseProd(product);
//				purchase.setBuyer(user);
//				purchase.setPaymentOption(rs.getString("payment_option"));
//				purchase.setReceiverName(rs.getString("receiver_name"));
//				purchase.setReceiverPhone(rs.getString("receiver_phone"));
//				purchase.setDivyAddr(rs.getString("demailaddr"));
//				purchase.setDivyRequest(rs.getString("dlvy_request"));
//				purchase.setTranCode(rs.getString("tran_status_code"));
//				purchase.setOrderDate(rs.getDate("order_data"));
//				purchase.setDivyDate(rs.getString("dlvy_date"));
//
//				list.add(purchase);
//				if (!rs.next())
//					break;
//			}
//		}
//		System.out.println("list.size() : " + list.size());
//		map.put("list", list);
//		System.out.println("map().size() : " + map.size());
//
//		con.close();
//
//		return map;
//	}

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
		// stmt.setDate(10, purchase.getOrderDate());// sysdate 로 구매일자 대체
		stmt.setString(8, purchase.getDivyDate());
		System.out.println("sql insert>>>>>>>>>>" + stmt);
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
		System.out.println("sql>>>>>>>>>>>>" + stmt);
		stmt.executeUpdate();

		con.close();
	}

	public void updateTranCode(Purchase purchase) throws Exception {
		System.out.println("============DAO UPDATETRANCODE 시작===================");
		Connection con = DBUtil.getConnection();

		String sql = "update TRANSACTION set TRAN_STATUS_CODE=? where Tran_No=?";
		System.out.println("update sql문" + sql);
		System.out.println("purchase : " + purchase);
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, purchase.getTranCode());
		stmt.setInt(2, purchase.getTranNo());
		// stmt.executeUpdate();
		if (stmt.executeUpdate() == 1) {
			System.out.println("성공");
		}
		con.close();
		System.out.println("============DAO UPDATETRANCODE 종료===================");
	}
	
	private int getTotalCount(String sql) throws Exception {
		
		sql = "SELECT COUNT(*) "+
		          "FROM ( " +sql+ ") countTable";
		
		Connection con = DBUtil.getConnection();
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		
		int totalCount = 0;
		if( rs.next() ){
			totalCount = rs.getInt(1);
		}
		
		pStmt.close();
		con.close();
		rs.close();
		
		return totalCount;
	}
	
	// 게시판 currentPage Row 만  return 
	private String makeCurrentPageSql(String sql , Search search){
		sql = 	"SELECT * "+ 
					"FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " +
									" 	FROM (	"+sql+" ) inner_table "+
									"	WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) " +
					"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize();
		
		System.out.println("UserDAO :: make SQL :: "+ sql);	
		
		return sql;
	}

}