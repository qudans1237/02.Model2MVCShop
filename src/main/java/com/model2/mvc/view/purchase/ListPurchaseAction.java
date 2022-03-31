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

public class ListPurchaseAction extends Action {//���Ÿ�� ��û(Userȭ��)
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("<<<<< ListPurchaseAction : execute() ���� >>>>>");
		
		Search search = new Search();
		
		int page = 1; //ó�� ���� ��� page�� 1
		
		//"page"�� value�� null�� �ƴ� ���(page�� ���� ���� ���) page�� ���� ������ �� ����
		if(request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
			System.out.println("if�� �� page��? " + page); 
		}
		
		//SearchVO�� page�� �� ���� (ó�� ���� ��� 1)
		search.setPage(page);
		System.out.println("page��? " + page);
		
		//SearchVO�� pageUnit�� web.xml�� "pageSize" �� 3 ����
		search.setPageUnit(Integer.parseInt(getServletContext().getInitParameter("pageSize")));
		System.out.println("pageUnit��? "+ getServletContext().getInitParameter("pageSize"));
		
		//���ǿ��� userId�� ������ buyerId�� ����
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		String buyerId = user.getUserId();
		System.out.println("buyerId ��? " + buyerId);
		
		//getPurchaseList()�� ���� �������� ������ �����͸� DB���� ������ map�� ����
		PurchaseService purchaseService = new PurchaseServiceImpl();
		HashMap<String, Object> map = purchaseService.getPurchaseList(search, buyerId);
		
		//������ ������ listPurchase.jsp�� �Ѱ��ֱ� ���� Request Object Scope�� �� ����
		request.setAttribute("search", search);
		request.setAttribute("map", map);
		
		System.out.println("<<<<< ListPurchaseAction : execute() ���� >>>>>");
		
		return "forward:/purchase/listPurchase.jsp";
		
	}//end of execute()	
}//end of class