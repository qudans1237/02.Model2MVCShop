package com.model2.mvc.view.purchase;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.common.Page;
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
		
		int currentPage=1;// ����Ʈ ������ 1��
		
		//"page"�� value�� null�� �ƴ� ���(page�� ���� ���� ���) page�� ���� ������ �� ����
		if(request.getParameter("currentPage") != null){
			currentPage=Integer.parseInt(request.getParameter("currentPage"));
		}
		System.out.println("currentPage: "+request.getParameter("currentPage"));
		
		//SearchVO�� page�� �� ���� (ó�� ���� ��� 1)
		// pageUnit�� web.xml�� "pageSize" �� 3�� �����ϰ�, SearchVO�� pageUnit�� 3 ����
		int pageSize = Integer.parseInt( getServletContext().getInitParameter("pageSize"));
		int pageUnit  =  Integer.parseInt(getServletContext().getInitParameter("pageUnit"));
		search.setPageSize(pageSize);
		System.out.println("searchVO ���ÿϷ� : " + search);
		
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		String buyerId = user.getUserId();
		System.out.println("buyerId ��? " + buyerId);
		
		//getSaleList()�� ���� �������� ������ �����͸� DB���� ������ map�� ����
		PurchaseService purchaseService = new PurchaseServiceImpl();
		Map<String, Object> map = purchaseService.getSaleList(search);
		System.out.println("map ���ÿϷ� : " + map);

		System.out.println("searchCondition��? "+request.getParameter("searchCondition"));
		System.out.println("searchKeyword��? "+request.getParameter("searchKeyword"));
		
		//menu�� "menu"�� value(manage Ȥ�� search)�� �ҷ��� ����
		String menu = request.getParameter("menu");
		System.out.println("menu��? "+menu);//�����
		
		Page resultPage	= 
				new Page( currentPage, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println("ListUserAction ::"+resultPage);
		
		//������ ������ listPurchase.jsp�� �Ѱ��ֱ� ���� Request Object Scope�� �� ����
		request.setAttribute("list", map.get("list"));
		request.setAttribute("search", search);
		request.setAttribute("map", map);
		request.setAttribute("menu", menu);
		
		System.out.println("<<<<< ListSaleAction : execute() ���� >>>>>");
		
		return "forward:/purchase/listSale.jsp";
		
	}//end of execute()	
}//end of class