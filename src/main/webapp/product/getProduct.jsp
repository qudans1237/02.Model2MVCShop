<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import="com.model2.mvc.service.domain.Product" %>

<%
	System.out.println("<<<<< getProduct.jsp ���� >>>>>");

	Product product = (Product)request.getAttribute("product");
	System.out.println("���� product : " + product);
	
	String menu = (String)request.getParameter("menu");
	System.out.println("���� menu : " + menu);
	
	String prvHistory = "";
	
	for (Cookie c:request.getCookies()){
		if (c.getName().equals("history")){
			prvHistory=c.getValue();
			System.out.println("getProduct: "+ prvHistory);			
		}
	}
	// Cookie�� Request, Response�� ������ �ҷ����� �Ǵ� ������ �̷������.
	// ���� Project���� ���Ǵ� Cookie�� ������ Key "history", value: prodNo�̸鼭 �� ProdNo�� , �� ���� �Ǿ�����.
	int prodNo= product.getProdNo();
	System.out.println("getProduct: "+ prvHistory);
	System.out.println("getProduct: "+ prodNo+","+prvHistory);
	Cookie cookie = new Cookie("history", prodNo+","+prvHistory);	// ��Ű ����
	cookie.setMaxAge(60*60);	// ���� Cookie�� �����Ⱓ
	response.addCookie(cookie);
	
	
%>
<!DOCTYPE html>
<html>
<head>
<title>ȸ����������</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript" src="../javascript/calendar.js">
</script>

<script type="text/javascript">
<!--
function fncAddProduct(){
	//Form ��ȿ�� ����
 	var name = document.detailForm.prodName.value;
	var detail = document.detailForm.prodDetail.value;
	var manuDate = document.detailForm.manuDate.value;
	var price = document.detailForm.price.value;

	if(name == null || name.length<1){
		alert("��ǰ���� �ݵ�� �Է��Ͽ��� �մϴ�.");
		return;
	}
	if(detail == null || detail.length<1){
		alert("��ǰ�������� �ݵ�� �Է��Ͽ��� �մϴ�.");
		return;
	}
	if(manuDate == null || manuDate.length<1){
		alert("�������ڴ� �ݵ�� �Է��ϼž� �մϴ�.");
		return;
	}
	if(price == null || price.length<1){
		alert("������ �ݵ�� �Է��ϼž� �մϴ�.");
		return;
	}
		
	document.detailForm.action='/updateProduct.do';
	document.detailForm.submit();
}
-->
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<form name="detailForm" method="post" >

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37"><img src="/images/ct_ttl_img01.gif"	width="15" height="37"></td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left: 10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">��ǰ����ȸ</td>
					<td width="20%" align="right">&nbsp;</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif"  width="12" height="37"/>
		</td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top: 13px;">
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">
			��ǰ��ȣ <img src="/images/ct_icon_red.gif" width="3" height="3" align="absmiddle"/>
		</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="105"><%=product.getProdNo() %></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">
			��ǰ�� <img src="/images/ct_icon_red.gif" width="3" height="3" align="absmiddle"/>
		</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01"><%=product.getProdName() %></td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">
			��ǰ�̹��� <img 	src="/images/ct_icon_red.gif" width="3" height="3" align="absmiddle"/>
		</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01"><%=product.getFileName() %>
			<img src = "/images/uploadFiles/../../images/empty.GIF"/>
		</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">
			��ǰ������ <img src="/images/ct_icon_red.gif" width="3" height="3" align="absmiddle"/>
		</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01"><%=product.getProdDetail() %></td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">��������</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01"><%=product.getManuDate() %></td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">����</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01"><%=product.getPrice() %></td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">�������</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01"><%=product.getRegDate() %></td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top: 10px;">
	<tr>
		<td width="53%"></td>
		<td align="right">

		<table border="0" cellspacing="0" cellpadding="0">
			<tr>
				<% if(menu.equals("search")) { %>
				
				<td width="17" height="23">
					<img src="/images/ct_btnbg01.gif" width="17" height="23"/>
				</td>	
				<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top: 3px;">
					<a href="/addPurchaseView.do?prod_no=<%=product.getProdNo() %>">����</a>
				</td>
				<td width="14" height="23">
					<img src="/images/ct_btnbg03.gif" width="14" height="23">
				</td>
				<td width="30"></td>
				
				<td width="17" height="23">
					<img src="/images/ct_btnbg01.gif" width="17" height="23"/>
				</td>
				<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top: 3px;">
					<a href="javascript:history.go(-1)">����</a>
				</td>
						
				<% }else { %>                      
				
				<td width="17" height="23">
					<img src="/images/ct_btnbg01.gif" width="17" height="23"/>
				</td>
				<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top: 3px;">
					<a href="/listProduct.do?menu=manage">Ȯ��</a>
				</td>	
					
				<% } %>
					
				
				<td width="14" height="23">
					<img src="/images/ct_btnbg03.gif" width="14" height="23">
				</td>
			</tr>	
		</table>

		</td>
	</tr>
</table>
</form>

</body>
</html>
<% System.out.println("<<<<< getProduct.jsp ���� >>>>>"); %>