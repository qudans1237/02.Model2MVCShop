package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.domain.Purchase;

public class GetPurchaseAction extends Action {


	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
//		PurchaseVO purchaseVO = new PurchaseVO();
		int tranNo=Integer.parseInt(request.getParameter("tranNo"));
		
		PurchaseService service=new PurchaseServiceImpl();
		Purchase purchase=service.getPurchase(tranNo);
		System.out.println("getprodNo>>>>>:"+tranNo);
		System.out.println("getprodNo vo>>>>>:"+purchase);
		request.setAttribute("purchase", purchase);
//		request.setAttribute("pvo", purchaseVO);
		
		return "forward:/purchase/getPurchaseView.jsp";
	}

}