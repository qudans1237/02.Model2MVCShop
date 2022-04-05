package com.model2.mvc.view.product;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class ListProductAction extends Action {//상품목록조회 요청

	//1. 판매상품관리 or 상품검색 메뉴 클릭
	//2. 판매상품관리 ==> http://192.168.0.96:8080/listProduct.do?menu=manage 이동
	//3. 상품검색    ==> http://192.168.0.96:8080/listProduct.do?menu=search 이동
	//4. ListProductAction.java의 execute() 실행
	//5. listProduct.jsp 이동
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("<<<<< ListProductAction : execute() 시작 >>>>>");
		
		Search search = new Search();
		
		int currentPage = 1;

		if(request.getParameter("currentPage") != null) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
			System.out.println("currentPage : " + currentPage);
		}
		
		search.setCurrentPage(currentPage);
		search.setSearchCondition(request.getParameter("searchCondition"));
		search.setSearchKeyword(request.getParameter("searchKeyword"));

		System.out.println("searchCondition : "+request.getParameter("searchCondition"));
		System.out.println("searchKeyword : "+request.getParameter("searchKeyword"));
		
		//web.xml  meta-data 로 부터 상수 추출 
		int pageSize = Integer.parseInt(getServletContext().getInitParameter("pageSize"));		
		int pageUnit = Integer.parseInt(getServletContext().getInitParameter("pageUnit"));		
		search.setPageSize(pageSize);
		System.out.println("search 셋팅완료 : " + search);
		
		//Business logic 수행
		ProductService productService = new ProductServiceImpl();
		Map<String,Object> map = productService.getProductList(search);
		System.out.println("map 셋팅완료 : " + map);
		
		Page resultPage = new Page(currentPage, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println("resultPage 셋팅완료 : "+resultPage);
		
		String menu = request.getParameter("menu");
		System.out.println("menu 셋팅완료 : " + menu);

		//Model 과 View 연결
		request.setAttribute("search", search);
		request.setAttribute("list", map.get("list"));
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("menu", menu);
		
		System.out.println("<<<<< ListProductAction : execute() 종료 >>>>>");
		
		return "forward:/product/listProduct.jsp";
		
	}
}