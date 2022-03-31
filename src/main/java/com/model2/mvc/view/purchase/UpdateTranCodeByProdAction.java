package com.model2.mvc.view.purchase;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.domain.Purchase;

public class UpdateTranCodeByProdAction extends Action {


	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Search search=new Search();
		String encoding = request.getParameter("searchKeyword");

		
		//keyword1 = new String(encoding.getBytes("8859_1"),"euc-kr");
				
		//System.out.println(keyword1);
		int page=1;
		Purchase purchase = new Purchase();
		//purchase.get     request.getParameter("prodNo");
		
		int ProdNo = Integer.parseInt(request.getParameter("prodNo"));
		System.out.println("tranCode :"+request.getParameter("tranCode"));
		System.out.println("prodNo :"+request.getParameter("prodNo"));
		PurchaseService service1 = new PurchaseServiceImpl();
		//service1.updateTranCode(purchase);
		purchase = service1.getPurchase2(ProdNo);
		purchase.setTranCode(request.getParameter("tranCode"));
		System.out.println("DB 들어가기전"+purchase);
		//purchase.setTranNo(purchase.(service1.getPurchase2(ProdNo)));
		service1.updateTranCode(purchase);
		System.out.println("DB 들어간이후"+purchase);
		if(request.getParameter("page") != null)
			//받아온 page가 null이 아니면
			page=Integer.parseInt(request.getParameter("page"));
		//page에 담는다
		System.out.println("ListProductAction >>> "+ page+"    "+request.getParameter("searchCondition")+"    "+
				request.getParameter("searchKeyword"));
		//page에 담겨져 있는거 확인  
		search.setPage(page);
		search.setSearchCondition(request.getParameter("searchCondition"));
		String keyword = (request.getParameter("searchKeyword")==null)? "": request.getParameter("searchKeyword");
		search.setSearchKeyword(keyword);
		
		String pageUnit=getServletContext().getInitParameter("pageSize");
		search.setPageUnit(Integer.parseInt(pageUnit));
		
		ProductService service=new ProductServiceImpl();
		System.out.println("ListProductAction >>> searchCondition: "+search.getSearchCondition()+
				" searchKeyword: "+search.getSearchKeyword()+" page unit: "+search.getPageUnit());
		/**
		 * 
		 */
		HashMap<String,Object> map=service.getProductList(search);
		request.setAttribute("map", map);
		request.setAttribute("searchVO", search);
		
		return "forward:/product/listProduct.jsp?menu=manage";
	}

}