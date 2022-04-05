package com.model2.mvc.view.purchase;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class ListPurchaseAction extends Action {//구매목록 요청(User화면)
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("<<<<< ListPurchaseAction : execute() 시작 >>>>>");
		
		Search search = new Search();
		
		int currentPage = 1; 
		
		if(request.getParameter("currentPage") != null) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
			System.out.println("currentPage : " + currentPage); 
		}
		
		search.setCurrentPage(currentPage);
		System.out.println("currentPage : " + currentPage);
		
		//web.xml  meta-data 로 부터 상수 추출 
		int pageSize = Integer.parseInt(getServletContext().getInitParameter("pageSize"));		
		int pageUnit = Integer.parseInt(getServletContext().getInitParameter("pageUnit"));		
		search.setPageSize(pageSize);
		System.out.println("search 셋팅완료 : " + search);
		
		//세션에서 userId를 가져와 buyerId에 저장
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		String buyerId = user.getUserId();
//		System.out.println("buyerId : " + user.getUserId());
		System.out.println("buyerId : " + buyerId);
		
		//Business logic 수행
		PurchaseService purchaseService = new PurchaseServiceImpl();
		Map<String, Object> map = purchaseService.getPurchaseList(search, buyerId);
		System.out.println("map 셋팅완료 : " + map);
		
		Page resultPage = new Page(currentPage, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println("resultPage 셋팅완료 : "+resultPage);
		
		//Model 과 View 연결
		request.setAttribute("search", search);
		request.setAttribute("list", map.get("list"));
		request.setAttribute("resultPage", resultPage);
		
		System.out.println("<<<<< ListPurchaseAction : execute() 종료 >>>>>");
		
		return "forward:/purchase/listPurchase.jsp";
		
	}
	
}