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

public class ListSaleAction extends Action {//�ǸŸ�� ��û(Adminȭ��)

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("<<<<< ListSaleAction : execute() ���� >>>>>");
		
		Search search = new Search();
		
		int page = 1; //ó�� ���� ��� page�� 1
		
		//"page"�� value�� null�� �ƴ� ���(page�� ���� ���� ���) page�� ���� ������ �� ����
		if(request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
			System.out.println("if�� �� page��? " + page); 
		}
		
		//SearchVO�� page�� "page"�� �� ����(ó�� ���� ��� 1 ����)
		//SearchVO�� searchCondition�� "searchCondition"�� �� ����
		//SearchVO�� searchKeyword�� "searchKeyword"�� �� ����
		search.setPage(page);
		search.setSearchCondition(request.getParameter("searchCondition"));
		search.setSearchKeyword(request.getParameter("searchKeyword"));
		
		//�����
		System.out.println("page��? "+page);
		System.out.println("searchCondition��? "+request.getParameter("searchCondition"));
		System.out.println("searchKeyword��? "+request.getParameter("searchKeyword"));
		
		//SearchVO�� pageUnit�� web.xml�� "pageSize" �� 3 ����
		search.setPageUnit(Integer.parseInt(getServletContext().getInitParameter("pageSize")));
		System.out.println("pageUnit��? "+ getServletContext().getInitParameter("pageSize"));
		
		//getPurchaseList()�� ���� �������� ������ �����͸� DB���� ������ map�� ����
		PurchaseService purchaseService = new PurchaseServiceImpl();
		HashMap<String, Object> map = purchaseService.getSaleList(search);
		
		//menu�� "menu"�� value(manage Ȥ�� search)�� �ҷ��� ����
		String menu = request.getParameter("menu");
		System.out.println("menu��? "+menu);//�����
		
		//������ ������ listPurchase.jsp�� �Ѱ��ֱ� ���� Request Object Scope�� �� ����
		request.setAttribute("search", search);
		request.setAttribute("map", map);
		request.setAttribute("menu", menu);
		
		System.out.println("<<<<< ListSaleAction : execute() ���� >>>>>");
		
		return "forward:/purchase/listSale.jsp";
		
	}//end of execute()	
}//end of class