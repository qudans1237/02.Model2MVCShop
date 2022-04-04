package com.model2.mvc.view.purchase;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class UpdateTranCodeByProdAction extends Action {


	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("===UpdateTranCodeByProdAction=== 시작");
		
		Search search=new Search();
		int currentPage=1;
		Purchase purchase = new Purchase();
		int ProdNo = Integer.parseInt(request.getParameter("prodNo"));
		System.out.println("tranCode :"+request.getParameter("tranCode"));
		System.out.println("prodNo :"+request.getParameter("prodNo"));
		PurchaseService service1 = new PurchaseServiceImpl();
		
		purchase = service1.getPurchase2(ProdNo);
		purchase.setTranCode(request.getParameter("tranCode"));
		System.out.println("DB 들어가기전"+purchase);
		
		service1.updateTranCode(purchase);
		System.out.println("DB 들어간이후"+purchase);

		if(request.getParameter("page") != null)
			currentPage=Integer.parseInt(request.getParameter("page"));
		
		System.out.println("ListProductAction >>> "+ currentPage+"    "+request.getParameter("searchCondition")+"    "+
				request.getParameter("searchKeyword"));
		
		search.setCurrentPage(currentPage);
		search.setSearchCondition(request.getParameter("searchCondition"));
		search.setSearchKeyword(request.getParameter("searchKeyword"));
		
		int pageSize = Integer.parseInt(getServletContext().getInitParameter("pageSize"));
		int pageUnit = Integer.parseInt(getServletContext().getInitParameter("pageUnit"));
		search.setPageSize(pageSize);
		System.out.println("pageSize :>>"+pageSize+"pageUnit"+pageUnit);
		
		ProductService service=new ProductServiceImpl();
		System.out.println("ListProductAction >>> searchCondition: "+search.getSearchCondition()+
				" searchKeyword: "+search.getSearchKeyword()+" page unit: "+search.getPageSize());
		
		Map<String,Object> map=service.getProductList(search);
		Page resultPage	= 
				new Page( currentPage, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println("ListUserAction ::"+resultPage);

		
		request.setAttribute("resultPage", resultPage);
		System.out.println(map.get("list"));
		request.setAttribute("list", map.get("list"));
		request.setAttribute("search", search);
		System.out.println("===============UpdateTranCodeByProdAction 종료======================");
		return "forward:/listProduct.do?menu=manage";
	}

}