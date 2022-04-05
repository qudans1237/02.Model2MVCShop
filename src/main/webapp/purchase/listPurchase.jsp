<%@page contentType="text/html; charset=EUC-KR"%>

<%@page import="java.util.List"%>

<%@page import="com.model2.mvc.service.domain.*"%>
<%@page import="com.model2.mvc.common.Search"%>
<%@page import="com.model2.mvc.common.Page"%>
<%@page import="com.model2.mvc.common.util.CommonUtil"%>

<%
	System.out.println("<<<<< listPurchase.jsp 시작 >>>>>");
	
	List<Purchase> list = (List<Purchase>)request.getAttribute("list");
	System.out.println("받은 list : " + list);
	
	Search search = (Search)request.getAttribute("search");
	System.out.println("받은 search : " + search);
	
	Page resultPage = (Page)request.getAttribute("resultPage");
	System.out.println("받은 resultPage : " + resultPage);
	
	//==> null 을 ""(nullString)으로 변경
	String searchCondition = CommonUtil.null2str(search.getSearchCondition());
	String searchKeyword = CommonUtil.null2str(search.getSearchKeyword());
		
	System.out.println("searchCondition은? " + searchCondition);
	System.out.println("searchKeyword는? " + searchKeyword);
%>

<html>
<head>
<title>구매 목록조회</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
	function fncGetUserList(currentPage) {
		document.getElementById("currentPage").value = currentPage;
		document.detailForm.submit();
	}
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width: 98%; margin-left: 10px;">

<form name="detailForm" action="/listUser.do" method="post">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37"><img src="/images/ct_ttl_img01.gif"width="15" height="37"></td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left: 10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">구매 목록조회</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37"><img src="/images/ct_ttl_img03.gif"	width="12" height="37"></td>
	</tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top: 10px;">
	<tr>
		<td colspan="11">
			전체 <%= resultPage.getTotalCount() %> 건수, 현재 <%= resultPage.getCurrentPage() %> 페이지
		</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">회원ID</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">회원명</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">전화번호</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">배송현황</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">정보수정</td>
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	<% 	
		for(int i=0; i<list.size(); i++) {
			Purchase purchase = list.get(i);
	%>
	<tr class="ct_list_pop">
		<td align="center">
			<a href="/getPurchase.do?tranNo=<%= purchase.getTranNo()%>"><%= i + 1 %></a>
		</td>
		<td></td>
		<td align="left">
			<a href="/getUser.do?userId=<%=purchase.getBuyer().getUserId() %>"><%=purchase.getBuyer().getUserId() %></a>
		</td>
		<td></td>
		<td align="left"><%=purchase.getReceiverName() %></td>
		<td></td>
		<td align="left"><%=purchase.getReceiverPhone() %></td>
		<td></td>
		<td align="left">
		<% if (purchase.getTranCode().equals("001")) { %>
			현재 구매완료 상태 입니다.
		<% }else if (purchase.getTranCode().equals("002")) { %>
			현재 배송중 상태 입니다. 
		<% }else if (purchase.getTranCode().equals("003")) { %>
			현재 배송완료 상태 입니다.
		<% } %>
		</td>
		<td></td>
		<td align="left">
		<% if (purchase.getTranCode().trim().equals("2")) { %>
			<a href="/updateTranCode.do?tranNo=<%=purchase.getTranNo()%>&tranCode=3&page=<%= resultPage.getCurrentPage() %>">
			   물건도착</a>
		<% } %>
		</td>
	</tr>
	<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
	</tr>
	<% } %>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 10px;">
	<tr>
		<td align="center">
		 	<input type="hidden" id="currentPage" name="currentPage" value=""/>
			<% if( resultPage.getCurrentPage() <= resultPage.getPageUnit() ){ %>
					◀ 이전
			<% }else{ %>
					<a href="/listPurchase.do?page=<%=resultPage.getCurrentPage()-1%>">◀ 이전</a>
			<% } %>
			
			<% for(int i=resultPage.getBeginUnitPage(); i<=resultPage.getEndUnitPage(); i++) { %>
				<a href="/listPurchase.do?page=<%= i %>"><%= i %></a>
			<% } %>
			
			<% if( resultPage.getEndUnitPage() >= resultPage.getMaxPage() ){ %>
					이후 ▶
			<% }else{ %>
					<a href="/listPurchase.do?page=<%=resultPage.getEndUnitPage()+1%>">이후 ▶</a>
			<% } %>
		</td>
	</tr>
</table>

<!--  페이지 Navigator 끝 -->
</form>

</div>

</body>
</html>
<% System.out.println("<<<<< listPurchase.jsp 종료 >>>>>"); %>