package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;
import com.model2.mvc.service.domain.User;

public class UpdateProductViewAction extends Action {//상품정보수정을 위한 화면요청

	//1. 판매상품관리의 상품명을 클릭해서 온 경우 (menu=manage)
	//2. UpdateProductViewAction.java의 execute() 실행
	//3. updateProductView.jsp 이동
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("<<<<< UpdateProductViewAction : execute() 시작 >>>>>");
		
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		System.out.println("받은 prodNo : " + prodNo);
		
		ProductService ProductService = new ProductServiceImpl();
		Product product = ProductService.getProduct(prodNo);
		System.out.println("product 셋팅완료 : " + product);
		
		request.setAttribute("product", product);	
		
		System.out.println("<<<<< UpdateProductViewAction : execute() 종료 >>>>>");
		
		return "forward:/product/updateProductView.jsp";
	}
}
