<%@ page contentType="text/html; charset=EUC-KR" %>

<html>
<head>
<title>열어본 상품 보기</title>
</head>

<body>
	당신이 열어본 상품을 알고 있다
<br>
<br>
<%
	request.setCharacterEncoding("euc-kr");
	response.setCharacterEncoding("euc-kr");
	
	String history = null;
	Cookie[] cookies = request.getCookies();
	//쿠키를 가져온다
	if (cookies!=null && cookies.length > 0) {
		//저장된정보가 널이 아니거나 1개 이상 이면
		for (int i = 0; i < cookies.length; i++) {
			//0부터 쿠키가 담겨져 있는 만큼
			Cookie cookie = cookies[i];
			//cookie에 저장
			if (cookie.getName().equals("history")) {
				history = cookie.getValue();
				System.out.println("history: "+ cookies.length);
				//history와 같으면  history에 담는다.
			}
		}
		// Cookie의 구성: Key: "histroy" , Value: "prodNo#1,prodNo#2,prodNo#3"
		if (history != null) {
			String[] h = history.split(",");		// [String Type] prodNo#1,prodNo#2,prodNo#3
													// [String] -(,를 기준으로 자른다)-> [List] [prodNo#1, prodNo#2, prodNo#3]
			for (int i = 0; i < h.length; i++) {
				if (!h[i].equals("null")) {
%>
<a href="/getProduct.do?prodNo=<%=h[i]%>&menu=search"	target="rightFrame"><%=h[i]%></a>
<br>
<%
				}
			}
		}
	}
%>

</body>
</html>