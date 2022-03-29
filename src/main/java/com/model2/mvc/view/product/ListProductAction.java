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

public class ListProductAction extends Action {// ��ǰ�����ȸ ��û

	// 1. �ǸŻ�ǰ���� or ��ǰ�˻� �޴� Ŭ��
	// 2. �ǸŻ�ǰ���� ==> http://192.168.0.96:8080/listProduct.do?menu=manage �̵�
	// 3. ��ǰ�˻� ==> http://192.168.0.96:8080/listProduct.do?menu=search �̵�
	// 4. ListProductAction.java�� execute() ����
	// 5. listProduct.jsp �̵�

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("<<<<< ListProductAction : execute() ���� >>>>>");

		Search search=new Search();
		
		int currentPage=1;// ����Ʈ ������ 1��

		if(request.getParameter("currentPage") != null){
			currentPage=Integer.parseInt(request.getParameter("currentPage"));
		}
		System.out.println("currentPage: "+request.getParameter("currentPage"));
		
		search.setCurrentPage(currentPage);
		search.setSearchCondition(request.getParameter("searchCondition"));
		search.setSearchKeyword(request.getParameter("searchKeyword"));

		System.out.println("searchCondition : " + request.getParameter("searchCondition"));
		System.out.println("searchKeyword : " + request.getParameter("searchKeyword"));

		// pageUnit�� web.xml�� "pageSize" �� 3�� �����ϰ�, SearchVO�� pageUnit�� 3 ����
		int pageSize = Integer.parseInt( getServletContext().getInitParameter("pageSize"));
		int pageUnit  =  Integer.parseInt(getServletContext().getInitParameter("pageUnit"));
		search.setPageSize(pageSize);
		System.out.println("searchVO ���ÿϷ� : " + search);

		ProductService service = new ProductServiceImpl();
		Map<String, Object> map = service.getProductList(search);
		System.out.println("map ���ÿϷ� : " + map);

		String menu = request.getParameter("menu");
		System.out.println("menu ���ÿϷ� : " + menu);
		
		Page resultPage	= 
				new Page( currentPage, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println("ListUserAction ::"+resultPage);
		
		request.setAttribute("list", map.get("list"));
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("search", search);
		request.setAttribute("menu", menu);

		System.out.println("<<<<< ListProductAction : execute() ���� >>>>>");

		return "forward:/product/listProduct.jsp";
	}
}