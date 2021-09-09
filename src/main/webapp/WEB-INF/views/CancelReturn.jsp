<%@ page contentType="text/html; charset=EUC-KR" %>
<%@ page import="com.galaxia.api.util.*"%>
<%@ page import="com.galaxia.api.merchant.* "%>
<%@ page import="com.galaxia.api.crypto.* "%>
<%@ page import="com.galaxia.api.*"%>   
<%@ page import="java.util.* "%>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title></title>
<style>
	body, tr, td {font-size:9pt; font-family:맑은고딕,verdana; }
	div {width: 98%; height:100%; overflow-y: auto; overflow-x:hidden;}
</style>
<meta charset="EUC-KR">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
<title>Insert title here</title>
</head>
<body>
	<div>
		<table width="380px" border="0" cellpadding="0"	cellspacing="0">
		<tr> 
	 		 <td height="25" style="padding-left:10px" class="title01"># 현재위치 &gt;&gt; <b>가맹점 취소 페이지</b></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td align="center"><!--본문테이블 시작--->
				<table width="380" border="0" cellpadding="4" cellspacing="1" bgcolor="#B0B0B0">	
					<tr>
						<td><b>취소결과</b></td>
					</tr>
					<tr>
						<td width="100" align="center" bgcolor="#F6F6F6"><b>가맹점 아이디</b></td>
						<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${cancelParam.serviceId}</b></td>
					</tr>
					<tr>
						<td width="100" align="center" bgcolor="#F6F6F6"><b>서비스코드</b></td>
						<td align="left" bgcolor="#FFFFFF">&nbsp;<b>${cancelParam.serviceCode}</b></td>
					</tr>
					<tr>
						<td width="100" align="center" bgcolor="#F6F6F6"><b>주문번호</b></td>
						<td align="left" bgcolor="#FFFFFF">&nbsp;<b>${cancelParam.orderId}</b></td>
					</tr>
					<tr>
						<td width="100" align="center" bgcolor="#F6F6F6"><b>주문일시</b></td>
						<td align="left" bgcolor="#FFFFFF">&nbsp;<b>${cancelParam.orderDate}</b></td>
					</tr>			
					<tr>
						<td width="100" align="center" bgcolor="#F6F6F6"><b>거래번호</b></td>
						<td align="left" bgcolor="#FFFFFF">&nbsp;<b>${cancelParam.outTransactionId}</b></td>
					</tr>
					<c:if test="${cancelResult.outCancelAmount != null}">
						<tr>
							<td width="100" align="center" bgcolor="#F6F6F6"><b>취소금액</b></td>
							<td align="left" bgcolor="#FFFFFF">&nbsp;<b>${cancelResult.outCancelAmount}</b></td>
						</tr>
					</c:if>
					<tr>
						<td width="100" align="center" bgcolor="#F6F6F6"><b>응답코드</b></td>
						<td align="left" bgcolor="#FFFFFF">&nbsp;<b>${cancelResult.responseCode}</b></td>
					</tr>
					<tr>
						<td width="100" align="center" bgcolor="#F6F6F6"><b>응답메시지</b></td>
						<td align="left" bgcolor="#FFFFFF">&nbsp;<b>${cancelResult.responseMessage}</b></td>
					</tr>
					<tr>
						<td width="100" align="center" bgcolor="#F6F6F6"><b>상세응답코드</b></td>
						<td align="left" bgcolor="#FFFFFF">&nbsp;<b>${cancelResult.detailResponseCode}</b></td>
					</tr>
					<tr>
						<td width="100" align="center" bgcolor="#F6F6F6"><b>상세응답메시지</b></td>
						<td align="left" bgcolor="#FFFFFF">&nbsp;<b>${cancelResult.detailResponseMessage}</b></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>