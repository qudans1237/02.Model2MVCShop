<%@ page contentType="text/html; charset=EUC-KR" %>

<html>
<head>
<title>��� ��ǰ ����</title>
</head>

<body>
	����� ��� ��ǰ�� �˰� �ִ�
<br>
<br>
<%
	request.setCharacterEncoding("euc-kr");
	response.setCharacterEncoding("euc-kr");
	
	String history = null;
	Cookie[] cookies = request.getCookies();
	//��Ű�� �����´�
	if (cookies!=null && cookies.length > 0) {
		//����������� ���� �ƴϰų� 1�� �̻� �̸�
		for (int i = 0; i < cookies.length; i++) {
			//0���� ��Ű�� ����� �ִ� ��ŭ
			Cookie cookie = cookies[i];
			//cookie�� ����
			if (cookie.getName().equals("history")) {
				history = cookie.getValue();
				System.out.println("history: "+ cookies.length);
				//history�� ������  history�� ��´�.
			}
		}
		// Cookie�� ����: Key: "histroy" , Value: "prodNo#1,prodNo#2,prodNo#3"
		if (history != null) {
			String[] h = history.split(",");		// [String Type] prodNo#1,prodNo#2,prodNo#3
													// [String] -(,�� �������� �ڸ���)-> [List] [prodNo#1, prodNo#2, prodNo#3]
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