package com.model2.mvc.view.product;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;

public class ListProductAction extends Action {// 상품목록조회 요청

	// 1. 판매상품관리 or 상품검색 메뉴 클릭
	// 2. 판매상품관리 ==> http://192.168.0.96:8080/listProduct.do?menu=manage 이동
	// 3. 상품검색 ==> http://192.168.0.96:8080/listProduct.do?menu=search 이동
	// 4. ListProductAction.java의 execute() 실행
	// 5. listProduct.jsp 이동

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("<<<<< ListProductAction : execute() 시작 >>>>>");

		Search search=new Search();
		
		int currentPage=1;// 리스트 페이지 1쪽

		if(request.getParameter("currentPage") != null){
			currentPage=Integer.parseInt(request.getParameter("currentPage"));
		}
		System.out.println("currentPage: "+request.getParameter("currentPage"));
		
		search.setCurrentPage(currentPage);
		search.setSearchCondition(request.getParameter("searchCondition"));
		search.setSearchKeyword(request.getParameter("searchKeyword"));

		System.out.println("searchCondition : " + request.getParameter("searchCondition"));
		System.out.println("searchKeyword : " + request.getParameter("searchKeyword"));

		// pageUnit에 web.xml의 "pageSize" 값 3을 저장하고, SearchVO의 pageUnit에 3 저장
		int pageSize = Integer.parseInt( getServletContext().getInitParameter("pageSize"));
		int pageUnit  =  Integer.parseInt(getServletContext().getInitParameter("pageUnit"));
		search.setPageSize(pageSize);
		System.out.println("searchVO 셋팅완료 : " + search);

		ProductService service = new ProductServiceImpl();
		Map<String, Object> map = service.getProductList(search);
		System.out.println("map 셋팅완료 : " + map);

		String menu = request.getParameter("menu");
		System.out.println("menu 셋팅완료 : " + menu);
		
		Page resultPage	= 
				new Page( currentPage, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println("ListUserAction ::"+resultPage);
		
		request.setAttribute("list", map.get("list"));
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("search", search);
		request.setAttribute("menu", menu);

		System.out.println("<<<<< ListProductAction : execute() 종료 >>>>>");

		return "forward:/product/listProduct.jsp";
	}
}