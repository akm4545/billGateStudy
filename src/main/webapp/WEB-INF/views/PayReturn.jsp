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
	body, tr, td {font-size:9pt; font-family:�������,verdana; }
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
			<td height="25" style="padding-left:10px" class="title01"># ������ġ &gt;&gt; �����׽�Ʈ &gt; <b>������ Return Url</b></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td align="center">
				<table width="380" border="0" cellpadding="4" cellspacing="1" bgcolor="#B0B0B0">
					<tr>
						<td><b>������� ${authInfo.serviceCode} ${authResult.responseCode}</b></td>
					</tr>
					<tr>
						<td width="100" align="center" bgcolor="#F6F6F6"><b>������ ���̵�</b></td>
						<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${authInfo.serviceId}</b></td>
					</tr>
					<tr>
						<td width="100" align="center" bgcolor="#F6F6F6"><b>���� �ڵ�</b></td>
						<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${authInfo.serviceCode}</b></td>
					</tr>
					<c:if test="${authInfo.serviceCode == '1100' || authInfo.serviceCode == '1200'}">
						<tr>
							<td width="100" align="center" bgcolor="#F6F6F6"><b>���� Ÿ��</b></td>
							<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${authResult.serviceType}</b></td>
						</tr>
					</c:if>
					<tr>
						<td width="100" align="center" bgcolor="#F6F6F6"><b>�ֹ���ȣ</b></td>
						<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${authResult.orderId}</b></td>
					</tr>
					<tr>
						<td width="100" align="center" bgcolor="#F6F6F6"><b>�ֹ��Ͻ�</b></td>
						<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${authResult.orderDate}</b></td>
					</tr>
					<c:if test="${authInfo.serviceCode == '0700' || authInfo.serviceCode == '0900'}">
						<tr>
							<td width="100" align="center" bgcolor="#F6F6F6"><b>�ŷ���ȣ</b></td>
							<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${approvalResult.transactionId}</b></td>
						</tr>
					</c:if>
					<c:if test="${authResult.serviceCode == '1800' && authResult.responseCode == '0000'}">
						<tr>
							<td width="100" align="center" bgcolor="#F6F6F6"><b>������¹�ȣ</b></td>
							<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${authResult.accountNumber}</b></td>
						</tr>
						<tr>
							<td width="100" align="center" bgcolor="#F6F6F6"><b>�ݾ�</b></td>
							<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${authResult.amount}</b></td>
						</tr>
						<tr>
							<td width="100" align="center" bgcolor="#F6F6F6"><b>�����ڵ�</b></td>
							<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${authResult.bankCode}</b></td>
						</tr>
						<tr>
							<td width="100" align="center" bgcolor="#F6F6F6"><b>�ŷ�����</b></td>
							<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${authResult.mixType}</b></td>
						</tr>
						<tr>
							<td width="100" align="center" bgcolor="#F6F6F6"><b>�Ա� ��ȿ ������</b></td>
							<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${authResult.expireDate}</b></td>
						</tr>
						<tr>
							<td width="100" align="center" bgcolor="#F6F6F6"><b>�Ա� ���� �ð�</b></td>
							<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${authResult.expireTime}</b></td>
						</tr>
					</c:if>
					<tr>
						<td width="100" align="center" bgcolor="#F6F6F6"><b>�����ڵ�</b></td>
						<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${authResult.responseCode}</b></td>
					</tr>
					<tr>
						<td width="100" align="center" bgcolor="#F6F6F6"><b>����޽���</b></td>
						<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${authResult.responseMessage}</b></td>
					</tr>
					<tr>
						<td width="100" align="center" bgcolor="#F6F6F6"><b>�������ڵ�</b></td>
						<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${authResult.detailResponseCode}</b></td>
					</tr>
					<tr>
						<td width="100" align="center" bgcolor="#F6F6F6"><b>������޽���</b></td>
						<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${authResult.detailResponseMessage}</b></td>
					</tr>
                    <!--������� ��-->
                    <!--���ΰ�� ����-->

					<tr>
						<td><b>���ΰ��</b></td>
					</tr>
					<c:if test="${approvalResult.outResponseCode != null}">
						<tr>
							<td width="100" align="center" bgcolor="#F6F6F6"><b>�ŷ���ȣ</b></td>
							<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${approvalResult.outTransactionId}</b></td>
						</tr>					
						<tr>
							<td width="100" align="center" bgcolor="#F6F6F6"><b>�����Ͻ�</b></td>
							<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${approvalResult.authDate}</b></td>
						</tr>
						<tr>
							<td width="100" align="center" bgcolor="#F6F6F6"><b>���αݾ�</b></td>
							<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${approvalResult.authAmount}</b></td>
						</tr>
						<c:if test="${authInfo.serviceCode == '0900'}">
							<tr>
								<td width="100" align="center" bgcolor="#F6F6F6"><b>�Һΰ��� ��</b></td>
								<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${approvalResult.quota}</b></td>
							</tr>
							<tr>
								<td width="100" align="center" bgcolor="#F6F6F6"><b>ī�� �߱޻� �ڵ�</b></td>
								<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${approvalResult.cardCompanyCode}</b></td>
							</tr>
						</c:if>
						<c:if test="${authInfo.serviceCode == '0900' || authInfo.serviceCode == '0100'}">
							<tr>
								<td width="100" align="center" bgcolor="#F6F6F6"><b>���ι�ȣ</b></td>
								<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${approvalResult.authNumber}</b></td>
							</tr>
						</c:if>
						<c:if test="${authInfo.serviceCode == '1000'}">
							<tr>
								<td width="100" align="center" bgcolor="#F6F6F6"><b>�ŷ�����</b></td>
								<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${approvalResult.mixType}</b></td>
							</tr>
							<tr>
								<td width="100" align="center" bgcolor="#F6F6F6"><b>���ݿ����� �뵵</b></td>
								<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${approvalResult.usingType}</b></td>
							</tr>	
							<tr>
								<td width="100" align="center" bgcolor="#F6F6F6"><b>���ݿ����� ���ι�ȣ</b></td>
								<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${approvalResult.identifier}</b></td>
							</tr>
							<tr>
								<td width="100" align="center" bgcolor="#F6F6F6"><b>���ݿ����� �����߱�������</b></td>
								<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${approvalResult.identifierType}</b></td>
							</tr>
							<tr>
								<td width="100" align="center" bgcolor="#F6F6F6"><b>���� �ڵ�</b></td>
								<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${approvalResult.inputBankCode}</b></td>
							</tr>
							<tr>
								<td width="100" align="center" bgcolor="#F6F6F6"><b>�����</b></td>
								<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${approvalResult.inputAccountName}</b></td>
							</tr>
						</c:if>
						<c:if test="${authInfo.serviceCode == '1100' && authResult.serviceType == '0000'}">
							<tr>
								<td width="100" align="center" bgcolor="#F6F6F6"><b>�κ� ��� Ÿ��</b></td>
								<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${approvalResult.partCancelType}</b></td>
							</tr>	
						</c:if>
						<tr>
							<td width="100" align="center" bgcolor="#F6F6F6"><b>�����ڵ�</b></td>
							<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${approvalResult.outResponseCode}</b></td>
						</tr>
						<tr>
							<td width="100" align="center" bgcolor="#F6F6F6"><b>����޽���</b></td>
							<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${approvalResult.outResponseMessage}</b></td>
						</tr>
						<tr>
							<td width="100" align="center" bgcolor="#F6F6F6"><b>�������ڵ�</b></td>
							<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${approvalResult.outDetailResponseCode}</b></td>
						</tr>
						<tr>
							<td width="100" align="center" bgcolor="#F6F6F6"><b>������޽���</b></td>
							<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${approvalResult.outDetailResponseMessage}</b></td>
						</tr>
					</c:if>
					<c:if test="${approvalResult.outResponseCode == null}">
						<tr>
							<td width="300" align="center" bgcolor="#F6F6F6" colspan="2"><b>���� ��� ����</b></td>
						</tr>
					</c:if>
					<!-- ���ΰ�� ��-->
			</table>
			</td>
		</tr>
	</table>>
	</div>
	<br>
</body>

</html>