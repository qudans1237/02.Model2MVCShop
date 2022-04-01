package com.model2.mvc.service.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.purchase.PurchaseService;
//import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.domain.User;

public class ProductDAO {
	//Constructor
	public ProductDAO() {
	}
	
	//Method
	//상품등록을 위한 DBMS를 수행
	public void insertProduct(Product product) throws SQLException {
		System.out.println("<<<<< ProductDAO : insertProduct() 시작 >>>>>");
		System.out.println("받은 product : " + product);
		
		Connection con = DBUtil.getConnection();
		
		String sql = "INSERT INTO product VALUES (seq_product_prod_no.nextval,?,?,?,?,?,sysdate)";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setString(1, product.getProdName());
		pStmt.setString(2, product.getProdDetail());
		pStmt.setString(3, product.getManuDate());
		pStmt.setInt(4, product.getPrice());
		pStmt.setString(5, product.getFileName());
		pStmt.executeUpdate();
		System.out.println("insert 완료 : " + sql);
		
		pStmt.close();
		con.close();	
		System.out.println("<<<<< ProductDAO : insertProduct() 종료 >>>>>");
	}
	
	
	//상품정보 조회를 위한 DBMS를 수행
	public Product findProduct(int prodNo) throws Exception {
		System.out.println("<<<<< ProductDAO : findProduct() 시작 >>>>>");
		System.out.println("받은 prodNo : " + prodNo);
		
		Connection con = DBUtil.getConnection();

		String sql = "SELECT * FROM product WHERE prod_no=?";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setInt(1, prodNo);
		ResultSet rs = pStmt.executeQuery();
		System.out.println("sql 전송완료 : " + sql);

		Product product = new Product();
		while (rs.next()) {
			product.setProdNo(rs.getInt("prod_no"));
			product.setProdName(rs.getString("prod_name"));
			product.setProdDetail(rs.getString("prod_detail"));
			product.setManuDate(rs.getString("manufacture_day"));
			product.setPrice(rs.getInt("price"));
			product.setFileName(rs.getString("image_file"));
			product.setRegDate(rs.getDate("reg_date"));
		}
		System.out.println("productVO 셋팅완료 : " + product);
		
		rs.close();
		pStmt.close();
		con.close();
		System.out.println("<<<<< ProductDAO : findProduct() 종료 >>>>>");
		return product;
	}
	
	
	//상품목록 조회를 위한 DBMS를 수행
	public Map<String,Object> getProductList(Search search) throws Exception {
		System.out.println("<<<<< ProductDAO : getProductList() 시작 >>>>>");
		System.out.println("받은 search : " + search);
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT * FROM product ";
		
		//SearchCondition에 값이 있을 경우
		if (search.getSearchCondition() != null) {
			if ( search.getSearchCondition().equals("0") &&  !search.getSearchKeyword().equals("") ) {
				sql += " WHERE prod_no LIKE '%" + search.getSearchKeyword() + "%'";
			} else if (search.getSearchCondition().equals("1")&&  !search.getSearchKeyword().equals("") ) {
				sql += " WHERE prod_name LIKE '%" + search.getSearchKeyword() + "%'";
			} else if (search.getSearchCondition().equals("2")&&  !search.getSearchKeyword().equals("") ) {
				sql += " WHERE price LIKE '%" + search.getSearchKeyword() + "%'";
			}
		}
		sql += " ORDER BY prod_no";
		
		System.out.println("ProductDao::Original SQL :: "+ sql);
		
		int totalCount = this.getTotalCount(sql);
		System.out.println("UserDAO :: totalCount  :: " + totalCount);
		
		sql = makeCurrentPageSql(sql, search);
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		
		System.out.println("sql 전송완료 : " + sql);
		System.out.println(search);
		
		List<Product> list = new ArrayList<Product>();
		
		while(rs.next()){
			Product product = new Product();
			product.setProdNo(rs.getInt("prod_no"));
			product.setProdName(rs.getString("prod_name"));
			product.setProdDetail(rs.getString("prod_detail"));
			product.setManuDate(rs.getString("manufacture_day"));
			product.setPrice(rs.getInt("price"));
			product.setFileName(rs.getString("image_file"));
			product.setRegDate(rs.getDate("reg_date"));
			list.add(product);
		}
		
		//==> totalCount 정보 저장
		map.put("totalCount", new Integer(totalCount));
		//==> currentPage 의 게시물 정보 갖는 List 저장
		map.put("list", list);
		System.out.println("map.put list"+list);
		
		rs.close();
		pStmt.close();
		con.close();
		
		System.out.println("<<<<< ProductDAO : getProductList() 종료 >>>>>");

		return map;
		
//		PreparedStatement pStmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
//														    ResultSet.CONCUR_UPDATABLE);
//		ResultSet rs = pStmt.executeQuery();
//		System.out.println("sql 전송완료 : " + sql);
//		
//		rs.last(); //boolean last() : 마지막 행으로 커서 이동
//		int total = rs.getRow(); //int getRow() : 현재 행번호 검색 (마지막 행번호 = 전체 행의 수)
//		System.out.println("전체 로우 수(total) : " + total);
//
//		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("count", new Integer(total));
//		System.out.println("map에 count 추가 : " + map);
//
//		//boolean absolute(int row) : 지정된 행번호로 커서 이동
//		rs.absolute(searchVO.getPage() * searchVO.getPageUnit() - searchVO.getPageUnit()+1);
//		
//		ArrayList<Product> list = new ArrayList<Product>();
//		
//		PurchaseService service = new PurchaseServiceImpl();
//		if (total > 0) {
//			for (int i=0; i<searchVO.getPageUnit(); i++) {
//				Product productVO = new Product();
//				productVO.setProdNo(rs.getInt("prod_no"));
//				productVO.setProdName(rs.getString("prod_name"));
//				productVO.setProdDetail(rs.getString("prod_detail"));
//				productVO.setManuDate(rs.getString("manufacture_day"));
//				productVO.setPrice(rs.getInt("price"));
//				productVO.setFileName(rs.getString("image_file"));
//				productVO.setRegDate(rs.getDate("reg_date"));
//				
//				if(service.getPurchase2(productVO.getProdNo()) != null) {
//					productVO.setProTranCode("재고없음"); 
//				}else {
//					productVO.setProTranCode("판매중");	
//				}
//				
//				list.add(productVO);
//				if (!rs.next()) {
//					break;
//				}
//				System.out.println("productVO 셋팅완료 : " + productVO);	
//			}
//		}
//		map.put("list", list);
//		System.out.println("map에 list 추가 : " + map);
//		System.out.println("list.size() : " + list.size()); 
//		System.out.println("map.size() : " + map.size()); 

	}
	
	
	//상품정보 수정을 위한 DBMS를 수행
	public void updateProduct(Product product) throws Exception {
		System.out.println("<<<<< ProductDAO : updateProduct() 시작 >>>>>");
		System.out.println("받은 productVO : " + product);
		
		Connection con = DBUtil.getConnection();

		String sql = "UPDATE product "
				    + "SET prod_name=?, prod_detail=?, manufacture_day=?, "
				    + "price=?, image_file=? WHERE prod_no=?";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setString(1, product.getProdName());
		pStmt.setString(2, product.getProdDetail());
		pStmt.setString(3, product.getManuDate());
		pStmt.setInt(4, product.getPrice());
		pStmt.setString(5, product.getFileName());
		pStmt.setInt(6, product.getProdNo());
		pStmt.executeUpdate();
		System.out.println("update 완료 : " + sql);
		
		pStmt.close();
		con.close();
		System.out.println("<<<<< ProductDAO : updateProduct() 종료 >>>>>");
	}
	// 게시판 Page 처리를 위한 전체 Row(totalCount)  return
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
}//end of class
