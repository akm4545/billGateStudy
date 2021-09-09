<%@ page contentType="text/html; charset=EUC-KR" %>
<%@ page import="com.galaxia.api.util.*"%>
<%@ page import="com.galaxia.api.merchant.* "%>
<%@ page import="com.galaxia.api.crypto.* "%>
<%@ page import="com.galaxia.api.*"%>
<%@ page import="java.sql.* "%>
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
			<td height="25" style="padding-left:10px" class="title01"># 현재위치 &gt;&gt; 결제테스트 &gt; <b>가맹점 Return Url</b></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td align="center">
				<table width="380" border="0" cellpadding="4" cellspacing="1" bgcolor="#B0B0B0">
					<tr>
						<td><b>인증결과 ${authInfo.serviceCode} ${authResult.responseCode}</b></td>
					</tr>
					<tr>
						<td width="100" align="center" bgcolor="#F6F6F6"><b>가맹점 아이디</b></td>
						<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${authInfo.serviceId}</b></td>
					</tr>
					<tr>
						<td width="100" align="center" bgcolor="#F6F6F6"><b>서비스 코드</b></td>
						<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${authInfo.serviceCode}</b></td>
					</tr>
					<c:if test="${authInfo.serviceCode == '1100' || authInfo.serviceCode == '1200'}">
						<tr>
							<td width="100" align="center" bgcolor="#F6F6F6"><b>서비스 타입</b></td>
							<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${authResult.serviceType}</b></td>
						</tr>
					</c:if>
					<tr>
						<td width="100" align="center" bgcolor="#F6F6F6"><b>주문번호</b></td>
						<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${authResult.orderId}</b></td>
					</tr>
					<tr>
						<td width="100" align="center" bgcolor="#F6F6F6"><b>주문일시</b></td>
						<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${authResult.orderDate}</b></td>
					</tr>
					<c:if test="${authInfo.serviceCode == '0700' || authInfo.serviceCode == '0900'}">
						<tr>
							<td width="100" align="center" bgcolor="#F6F6F6"><b>거래번호</b></td>
							<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${approvalResult.transactionId}</b></td>
						</tr>
					</c:if>
					<c:if test="${authResult.serviceCode == '1800' && authResult.responseCode == '0000'}">
						<tr>
							<td width="100" align="center" bgcolor="#F6F6F6"><b>가상계좌번호</b></td>
							<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${authResult.accountNumber}</b></td>
						</tr>
						<tr>
							<td width="100" align="center" bgcolor="#F6F6F6"><b>금액</b></td>
							<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${authResult.amount}</b></td>
						</tr>
						<tr>
							<td width="100" align="center" bgcolor="#F6F6F6"><b>은행코드</b></td>
							<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${authResult.bankCode}</b></td>
						</tr>
						<tr>
							<td width="100" align="center" bgcolor="#F6F6F6"><b>거래구분</b></td>
							<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${authResult.mixType}</b></td>
						</tr>
						<tr>
							<td width="100" align="center" bgcolor="#F6F6F6"><b>입금 유효 만료일</b></td>
							<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${authResult.expireDate}</b></td>
						</tr>
						<tr>
							<td width="100" align="center" bgcolor="#F6F6F6"><b>입금 마감 시간</b></td>
							<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${authResult.expireTime}</b></td>
						</tr>
					</c:if>
					<tr>
						<td width="100" align="center" bgcolor="#F6F6F6"><b>응답코드</b></td>
						<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${authResult.responseCode}</b></td>
					</tr>
					<tr>
						<td width="100" align="center" bgcolor="#F6F6F6"><b>응답메시지</b></td>
						<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${authResult.responseMessage}</b></td>
					</tr>
					<tr>
						<td width="100" align="center" bgcolor="#F6F6F6"><b>상세응답코드</b></td>
						<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${authResult.detailResponseCode}</b></td>
					</tr>
					<tr>
						<td width="100" align="center" bgcolor="#F6F6F6"><b>상세응답메시지</b></td>
						<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${authResult.detailResponseMessage}</b></td>
					</tr>
                    <!--인증결과 끝-->
                    <!--승인결과 시작-->

					<tr>
						<td><b>승인결과</b></td>
					</tr>
					<c:if test="${approvalResult.outResponseCode != null}">
						<tr>
							<td width="100" align="center" bgcolor="#F6F6F6"><b>거래번호</b></td>
							<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${approvalResult.outTransactionId}</b></td>
						</tr>					
						<tr>
							<td width="100" align="center" bgcolor="#F6F6F6"><b>승인일시</b></td>
							<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${approvalResult.authDate}</b></td>
						</tr>
						<tr>
							<td width="100" align="center" bgcolor="#F6F6F6"><b>승인금액</b></td>
							<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${approvalResult.authAmount}</b></td>
						</tr>
						<c:if test="${authInfo.serviceCode == '0900'}">
							<tr>
								<td width="100" align="center" bgcolor="#F6F6F6"><b>할부개월 수</b></td>
								<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${approvalResult.quota}</b></td>
							</tr>
							<tr>
								<td width="100" align="center" bgcolor="#F6F6F6"><b>카드 발급사 코드</b></td>
								<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${approvalResult.cardCompanyCode}</b></td>
							</tr>
						</c:if>
						<c:if test="${authInfo.serviceCode == '0900' || authInfo.serviceCode == '0100'}">
							<tr>
								<td width="100" align="center" bgcolor="#F6F6F6"><b>승인번호</b></td>
								<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${approvalResult.authNumber}</b></td>
							</tr>
						</c:if>
						<c:if test="${authInfo.serviceCode == '1000'}">
							<tr>
								<td width="100" align="center" bgcolor="#F6F6F6"><b>거래구분</b></td>
								<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${approvalResult.mixType}</b></td>
							</tr>
							<tr>
								<td width="100" align="center" bgcolor="#F6F6F6"><b>현금영수증 용도</b></td>
								<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${approvalResult.usingType}</b></td>
							</tr>	
							<tr>
								<td width="100" align="center" bgcolor="#F6F6F6"><b>현금영수증 승인번호</b></td>
								<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${approvalResult.identifier}</b></td>
							</tr>
							<tr>
								<td width="100" align="center" bgcolor="#F6F6F6"><b>현금영수증 자진발급제유무</b></td>
								<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${approvalResult.identifierType}</b></td>
							</tr>
							<tr>
								<td width="100" align="center" bgcolor="#F6F6F6"><b>은행 코드</b></td>
								<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${approvalResult.inputBankCode}</b></td>
							</tr>
							<tr>
								<td width="100" align="center" bgcolor="#F6F6F6"><b>은행명</b></td>
								<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${approvalResult.inputAccountName}</b></td>
							</tr>
						</c:if>
						<c:if test="${authInfo.serviceCode == '1100' && authResult.serviceType == '0000'}">
							<tr>
								<td width="100" align="center" bgcolor="#F6F6F6"><b>부분 취소 타입</b></td>
								<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${approvalResult.partCancelType}</b></td>
							</tr>	
						</c:if>
						<tr>
							<td width="100" align="center" bgcolor="#F6F6F6"><b>응답코드</b></td>
							<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${approvalResult.outResponseCode}</b></td>
						</tr>
						<tr>
							<td width="100" align="center" bgcolor="#F6F6F6"><b>응답메시지</b></td>
							<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${approvalResult.outResponseMessage}</b></td>
						</tr>
						<tr>
							<td width="100" align="center" bgcolor="#F6F6F6"><b>상세응답코드</b></td>
							<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${approvalResult.outDetailResponseCode}</b></td>
						</tr>
						<tr>
							<td width="100" align="center" bgcolor="#F6F6F6"><b>상세응답메시지</b></td>
							<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${approvalResult.outDetailResponseMessage}</b></td>
						</tr>
					</c:if>
					<c:if test="${approvalResult.outResponseCode == null}">
						<tr>
							<td width="300" align="center" bgcolor="#F6F6F6" colspan="2"><b>승인 결과 없음</b></td>
						</tr>
					</c:if>
					<!-- 승인결과 끝-->
			</table>
			</td>
		</tr>
	</table>>
	</div>
	<br>
</body>

</html>