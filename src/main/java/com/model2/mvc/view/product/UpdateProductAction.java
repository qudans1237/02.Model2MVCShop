package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;
import com.model2.mvc.service.domain.User;

public class UpdateProductAction extends Action {//상품정보수정 요청

	//1. 판매상품관리의 상품명을 클릭해서 온 경우 (menu=manage)
	//2. UpdateProductViewAction.java의 execute() 실행
	//3. updateProductView.jsp 이동
	//4. [수정] 버튼 클릭 시 ==> UpdateProductAction.java 이동
	//5. GetProductAction.java 이동
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("<<<<< UpdateProductAction : execute() 시작 >>>>>");
				
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		System.out.println("받은 prodNo : " + prodNo);
		
		String menu = request.getParameter("menu");
		System.out.println("받은 menu : " + menu);
		
		Product product = new Product();
		product.setProdNo(prodNo);
		product.setProdName(request.getParameter("prodName"));
		product.setProdDetail(request.getParameter("prodDetail"));
		product.setManuDate(request.getParameter("manuDate"));
		product.setPrice(Integer.parseInt(request.getParameter("price")));
		product.setFileName(request.getParameter("fileName"));
		System.out.println("product 셋팅완료 : " + product);
		
		ProductService ProductService = new ProductServiceImpl();
		ProductService.updateProduct(product);
		
		System.out.println("<<<<< UpdateProductAction : execute() 시작 >>>>>");
		
		return "redirect:/getProduct.do?prodNo="+prodNo+"&menu="+menu;
	}
}
