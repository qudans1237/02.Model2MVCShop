package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.domain.Purchase;

public class UpdatePurchaseAction extends Action {//구매정보 수정 요청

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("<<<<< UpdatePurchaseAction : execute() 시작 >>>>>");
		
		int tranNo = Integer.parseInt(request.getParameter("tranNo"));
		System.out.println("받은 tranNo : " + tranNo);
		
		Purchase purchase = new Purchase();
		purchase.setTranNo(tranNo);
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		purchase.setReceiverName(request.getParameter("receiverName"));
		purchase.setReceiverPhone(request.getParameter("receiverPhone"));
		purchase.setDivyAddr(request.getParameter("receiverAddr"));
		purchase.setDivyRequest(request.getParameter("receiverRequest"));
		purchase.setDivyDate(request.getParameter("divyDate"));
		System.out.println("purchaseVO 셋팅완료 : " + purchase);
		
		PurchaseService purchaseService = new PurchaseServiceImpl();
		purchaseService.updatePurcahse(purchase);
			
		System.out.println("<<<<< UpdatePurchaseAction : execute() 종료 >>>>>");
		
		return "redirect:/getPurchase.do?tranNo="+tranNo;
	}
}