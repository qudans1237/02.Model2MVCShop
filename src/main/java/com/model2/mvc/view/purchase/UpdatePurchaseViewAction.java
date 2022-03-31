package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.domain.Purchase;

public class UpdatePurchaseViewAction extends Action {//구매정보 수정을 위한 화면요청

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("<<<<< UpdatePurchaseViewAction : execute() 시작 >>>>>");
		
		int tranNo = Integer.parseInt(request.getParameter("tranNo"));
		System.out.println("받은 tranNo : " + tranNo);
		
		PurchaseService purchaseService = new PurchaseServiceImpl();
		Purchase purchase = purchaseService.getPurchase(tranNo);
		System.out.println("purchaseVO 셋팅완료 : " + purchase);
		
		request.setAttribute("purchase", purchase);
		
		System.out.println("<<<<< UpdatePurchaseViewAction : execute() 종료 >>>>>");
		
		return "forward:/purchase/updatePurchaseView.jsp";
	}
}