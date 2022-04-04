package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;

public class AddPurchaseAction extends Action {

	public AddPurchaseAction() {
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Purchase purchase = new Purchase();
		User user = new User();
		//ProductVO productVO = new ProductVO();
		//productVO.setProdName(request.getParameter("prodName"));
		user.setUserId(request.getParameter("buyerId"));
		//purchaseVO.setPurchaseProd(productVO);
		purchase.setTranNo(Integer.parseInt(request.getParameter("prodNo")));
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		purchase.setReceiverName(request.getParameter("receiverName"));
		purchase.setReceiverPhone(request.getParameter("receiverPhone"));
		purchase.setDivyAddr(request.getParameter("receiverAddr"));
		purchase.setDivyRequest(request.getParameter("receiverRequest"));
		purchase.setDivyDate(request.getParameter("receiverDate"));
		purchase.setBuyer(user);
		System.out.println("TranNo >>>>>>>>>"+purchase.getTranNo());
		System.out.println("PaymentOption>>>>>>>>>>>>>"+purchase.getPaymentOption());
		System.out.println("Add purchase:"+purchase);
		PurchaseService service = new PurchaseServiceImpl();
		service.addPurchase(purchase);
		System.out.println("Add purchase:"+purchase);
//		HttpSession session = request.getSession();
//		session.setAttribute("purchase", purchase);
		request.setAttribute("purchase", purchase);
		
		
		
		
		return "forward:/purchase/getPurchase.jsp";
	}

}