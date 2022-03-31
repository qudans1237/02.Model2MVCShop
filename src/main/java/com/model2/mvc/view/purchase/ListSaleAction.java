package com.model2.mvc.view.purchase;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.domain.User;

public class ListSaleAction extends Action {//판매목록 요청(Admin화면)

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("<<<<< ListSaleAction : execute() 시작 >>>>>");
		
		Search search = new Search();
		
		int page = 1; //처음 들어올 경우 page는 1
		
		//"page"의 value가 null이 아닐 경우(page를 눌러 들어올 경우) page에 현재 페이지 값 저장
		if(request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
			System.out.println("if문 내 page는? " + page); 
		}
		
		//SearchVO의 page에 "page"의 값 저장(처음 들어올 경우 1 저장)
		//SearchVO의 searchCondition에 "searchCondition"의 값 저장
		//SearchVO의 searchKeyword에 "searchKeyword"의 값 저장
		search.setPage(page);
		search.setSearchCondition(request.getParameter("searchCondition"));
		search.setSearchKeyword(request.getParameter("searchKeyword"));
		
		//디버깅
		System.out.println("page는? "+page);
		System.out.println("searchCondition은? "+request.getParameter("searchCondition"));
		System.out.println("searchKeyword는? "+request.getParameter("searchKeyword"));
		
		//SearchVO의 pageUnit에 web.xml의 "pageSize" 값 3 셋팅
		search.setPageUnit(Integer.parseInt(getServletContext().getInitParameter("pageSize")));
		System.out.println("pageUnit은? "+ getServletContext().getInitParameter("pageSize"));
		
		//getPurchaseList()를 통해 페이지에 보여줄 데이터를 DB에서 가져와 map에 저장
		PurchaseService purchaseService = new PurchaseServiceImpl();
		HashMap<String, Object> map = purchaseService.getSaleList(search);
		
		//menu에 "menu"의 value(manage 혹은 search)를 불러와 저장
		String menu = request.getParameter("menu");
		System.out.println("menu는? "+menu);//디버깅
		
		//페이지 정보를 listPurchase.jsp에 넘겨주기 위해 Request Object Scope에 값 셋팅
		request.setAttribute("search", search);
		request.setAttribute("map", map);
		request.setAttribute("menu", menu);
		
		System.out.println("<<<<< ListSaleAction : execute() 종료 >>>>>");
		
		return "forward:/purchase/listSale.jsp";
		
	}//end of execute()	
}//end of class