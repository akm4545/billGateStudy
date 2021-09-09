package com.bill.pay;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxia.api.ConfigInfo;
import com.galaxia.api.ServiceBroker;
import com.galaxia.api.crypto.GalaxiaCipher;
import com.galaxia.api.crypto.Seed;
import com.galaxia.api.merchant.Message;
import com.galaxia.api.util.ChecksumUtil;

@Controller
public class HomeController {
	//================================
	// static 변수 및 함수 선언부
	//================================
	//결제 수단 카드, 계좌이체, 무통장입금, 휴대폰
	private static String SERVICE_ID = "glx_api";
	//프로토콜 버전 필수
	public static final String VERSION ="0100";
	//환경설정 파일 경로
	public static final String CONF_PATH ="D:/전자정부프레임워크/eGovFrameDev-3.10.0-64bit/workspace/billGate/src/main/webapp/WEB-INF/classes/config.ini"; //*가맹점 수정 필수
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String paymentInput(Locale locale, Model model) throws Exception {
		/*
		 ------------------------------------------------------------------------------------- 
		 해당 페이지는 빌게이트 결제를 위한 "결제 요청(인증요청)" 테스트 페이지 입니다.
		 ------------------------------------------------------------------------------------- 

		 ※테스트 결제를 원하신다면,
		 1. 결제 정보 (returnUrl, cancelUrl) 가맹점 환경에 맞게 변경
		 2. PayReturn.jsp의 CONF_PATH경로 설정 (config.ini 경로 설정)
		 3. CancleReturn.jsp의 CONF_PAHT경로 설정	(config.ini 경로 설정)
		 4. config.ini 의 로그 파일 경로 및 권한 설정
		 	- 해당 설정 완료 후 그대로 테스트 결제 진행하시면 됩니다.
		 
		 ※실제 상용 테스트를 원하신다면,
		 1. 결제 정보 (serviceId, returnUrl, cancelUrl)를 변경  -> 계약 시 받은 serviceId 정보를 넣으시면 됩니다.
		 2. config.ini 의 Key,Iv 값 변경 (가맹점 관리자 어드민에서 확인 가능)
		 3. config.ini 의 mode = 1(상용)으로 변경 후 실 결제 테스트 하시길 바랍니다. 
		  	- 상용 테스트는 실제 과금이 이뤄지는 점 유의하시길 바랍니다.
		  	------------------------------------------------------------------------------------- 
		*/
		
		//변수 정의
		String transactionId = null;
		String responseCode =null;  
		String responseMessage = null;
		String detailResponseCode = null;
		String detailResponseMessage = null;
		String serviceCode = null;
		
		//날짜변수 선언 
		Calendar today = Calendar.getInstance(); 
		String year = Integer.toString(today.get(Calendar.YEAR));
		String month = Integer.toString(today.get(Calendar.MONTH)+1);
		String date = Integer.toString(today.get(Calendar.DATE));
		String hour = Integer.toString(today.get(Calendar.HOUR_OF_DAY));
		String minute = Integer.toString(today.get(Calendar.MINUTE));
		String second = Integer.toString(today.get(Calendar.SECOND));
			
		if(today.get(Calendar.MONTH)+1 < 10) month = "0" + month ;
		if(today.get(Calendar.DATE) < 10) date = "0" + date ;
		if(today.get(Calendar.HOUR_OF_DAY) < 10) hour = "0" + hour ;
		if(today.get(Calendar.MINUTE) < 10) minute = "0" + minute ;
		if(today.get(Calendar.SECOND) < 10) second = "0" + second ;
		
		//================================================
		// 1. 가맹점 결제 요청 테스트 공통 정보
		//================================================
		//가맹점 ID 필수 && 서비스 코드 필수 && 결제 타입 필수 
		//String serviceId			="glx_api";				//테스트 아이디 일반결제 : glx_api
		//고객 ID 필수 
		String userId 				="user_id";
		//구매 상품명 필수는 아님
		String itemName		="테스트상품_123";
		//상품 코드 필수 (상품키)
		String itemCode			="item_code";
		//결제금액 필수
		String amount			="1004";
		//고객명 필수 아님
		String userName		="홍길동";
		//고객 이메일 필수 아님
		String userEmail		="test@test.com";	
		//주문 일시 (필수)
		String orderDate		= year+month+date+hour+minute+second ;
		//고객 주문 번호 필수 (주문 키)
		String orderId			="test_"+orderDate;
		//업체 측에서 보여줄 결과 페이지 URL 필수
		String returnUrl			="http://localhost:9090/billGate/payReturn";  // *가맹점 수정 필수
		//취소결과 전달 여부 필수 아님
		String cancelFlag		="Y";
		//기타 값 넣을 변수 필수 아님
		String reserved1		="예비변수1";
		String reserved2		="예비변수2";
		String reserved3		="예비변수3";
		//결제창 좌측 상단에 노출될 이미지 URL 필수 아님
		String logo = "";
		
		//================================================
		// 2. 결제 요청 시 위변조 방지를 위한 CHECKSUM 생성
		//================================================
		//데이터 값 변조 방지용 암호화 값 필수 아님 (사용하면 좋을듯)
		String checkSum		="";
		/*
		*	*CHECK_SUM 
		*	: 결제 요청 시 위변조 방지 체크를 위해 CHECK_SUM 생성
		*	SERVICE_ID , ORDER_ID , AMOUNT 3개의 값을 가지고 당사 결제 모듈 billgateApi.jar를 통해 중복되지 않는 유일한 값 생성.		
		*/		
		String temp = SERVICE_ID + orderId + amount;
		checkSum = ChecksumUtil.genCheckSum(temp);
		
		Map<String, String> paymentInfo = new HashMap<String, String>();
		
		paymentInfo.put("serviceId",SERVICE_ID);
		paymentInfo.put("userId",userId);
		paymentInfo.put("itemName",itemName);
		paymentInfo.put("itemCode",itemCode);
		paymentInfo.put("amount",amount);
		paymentInfo.put("userName",userName);
		paymentInfo.put("userEmail",userEmail);
		paymentInfo.put("orderDate",orderDate);
		paymentInfo.put("orderId",orderId);
		paymentInfo.put("returnUrl",returnUrl);
		paymentInfo.put("cancelFlag",cancelFlag);
		paymentInfo.put("logo",logo);
		paymentInfo.put("checkSum",checkSum);
		
		model.addAttribute("paymentInfo", paymentInfo);
		
		return "PayInput";
	}
	
	@RequestMapping(value = "payReturn", method = RequestMethod.POST)
	public String payReturn (HttpServletRequest request, Model model) throws Exception {
		/*
		------------------------------------------------------------------------------------- 
		해당 페이지는 빌게이트 결제를 위한 "인증결과 리턴 및 승인요청/응답 "테스트 페이지 입니다.
		------------------------------------------------------------------------------------- 
		*/	
		/* 인증 결과 변수 */
		String serviceId = null; //가맹점 서비스 아이디 
		String serviceCode = null; //서비스 코드
		String orderId = null; //가맹점 측 사용 주문 번호
		String orderDate = null; //가맹점 측 사용 주문 일시
		String transactionId = null; //빌게이트 거래 번호
		String responseCode = null;	//응답 코드
		String responseMessage = null; //응답 메세지
		String detailResponseCode = null; //상세 응답 코드
		String detailResponseMessage = null; //상세 응답 메세지
		String serviceType = null;		//서비스 구분(일반:0000/월자동:1000 존재 해야하나? 쓰는곳 없음)

		String message = null;			//인증 응답 MESSAGE 결제 암호화 값 승인 요청시 필요

		//가상계좌
		String accountNumber = null;		//가상계좌번호
		String bankCode = null;				//발급 은행 코드
		String mixType = null;				//거래 구분(일반:0000/에스크로:1000)
		String expireDate = null;				//입금마감일자(YYYYMMDD)
		String expireTime = null;			//입금마감시간(HH24MISS)
		String amount = null;					//입금예정금액

		/* 승인 결과 변수 */
		String outTransactionId = null;
		String outResponseCode = null;
		String outResponseMessage = null;
		String outDetailResponseCode = null;
		String outDetailResponseMessage = null;

		String authAmount = null; 		// 승인응답 추가 파라미터	_승인금액
		String authNumber = null;		// 승인응답 추가 파라미터_승인번호
		String authDate = null;			// 승인응답 추가 파라미터_승인일시

		String quota = null;					//신용카드 승인응답 추가 파라미터_할부개월 수 
		String cardCompanyCode = null; //신용카드 승인응답 추가 파라미터_발급사 코드 
		
		String usingType = null;				//계좌이체 승인응답 추가 파라미터_현금영수증 용도
		String identifier = null;				//계좌이체 승인응답 추가 파라미터_현금영수증 승인번호
		String identifierType = null;		//계좌이체 승인응답 추가 파라미터_현금영수증 자진발급 유무
		String inputBankCode = null;		//계좌이체  승인응답 추가 파라미터_은행 코드 
		String inputAccountName = null;	//계좌이체  승인응답 추가 파라미터_은행명

		String partCancelType = null;		//휴대폰 승인응답 추가 파라미터_부분 취소 타입(일반 결제시에만 전달)

		Map<String,String> authInfo = null;	 //승인요청 정보 저장

		Message respMsg = null;			

		try{
			
			//================================================
			// 1. 인증 결과 파라미터 수신
			//================================================
			request.setCharacterEncoding("euc-kr");
			//DB기준
			
			//일반결제인지 자동결제인지 표시 필요할듯
			serviceType = request.getParameter("SERVICE_TYPE");						//서비스 타입(일반 :0000 , 월자동:1000)
			//가맹점 아이디 필요 없음
			serviceId = request.getParameter("SERVICE_ID");								//가맹점 서비스 아이디
			//필수 환불 요청시 필요
			serviceCode = request.getParameter("SERVICE_CODE");						//결제 수단 별 서비스코드
			//주문 키 필요
			orderId = request.getParameter("ORDER_ID");										//주문 번호
			//주문 날짜 환불 요청시 필요 (YYYYMMDDHH24MISS)
			orderDate = request.getParameter("ORDER_DATE");							//주문 일자
			//빌게이트 거래번호 환불 요청시 필요
			transactionId = request.getParameter("TRANSACTION_ID");					//거래번호
			//응답코드 불필요
			responseCode = request.getParameter("RESPONSE_CODE");								//응답코드
			//응답 메세지 불필요
			responseMessage = request.getParameter("RESPONSE_MESSAGE");					//응답메시지
			//불필요
			detailResponseCode = request.getParameter("DETAIL_RESPONSE_CODE");		//상세 응답코드
			//불필요
			detailResponseMessage = request.getParameter("DETAIL_RESPONSE_MESSAGE");//상세 응답 메시지
			//환불시 필요
			message = request.getParameter("MESSAGE");								//인증 응답 전문 메시지
			
			System.out.println(transactionId);

			/*가상계좌 채번 응답*/		
			//필수
			accountNumber =request.getParameter("ACCOUNT_NUMBER");			//가상계좌번호
			//필수 아닌듯
			bankCode =request.getParameter("BANK_CODE");							//발급 은행 코드
			//필수 아닌듯?
			mixType = request.getParameter("MIX_TYPE");								//거래 구분(일반:0000/에스크로:1000)
			//필수
			expireDate = request.getParameter("EXPIRE_DATE");						//입금마감일자(YYYYMMDD)
			//필수
			expireTime = request.getParameter("EXPIRE_TIME");						//입금마감시간(HH24MISS)
			//필수
			amount = request.getParameter("AMOUNT");								//입금예정금액
			
			Map<String, String> authResult = new HashMap<String, String>();
			authResult.put("serviceType", serviceType);
			authResult.put("serviceId", serviceId);
			authResult.put("serviceCode", serviceCode);
			authResult.put("orderId", orderId);
			authResult.put("orderDate", orderDate);
			authResult.put("transactionId", transactionId);
			authResult.put("accountNumber", accountNumber);
			authResult.put("amount", amount);
			authResult.put("bankCode", bankCode);
			authResult.put("mixType", mixType);
			authResult.put("expireDate", expireDate);
			authResult.put("expireTime", expireTime);
			authResult.put("responseCode", responseCode);
			authResult.put("responseMessage", responseMessage);
			authResult.put("detailResponseCode", detailResponseCode);
			authResult.put("detailResponseMessage", detailResponseMessage);
			
			System.out.println(responseCode + responseCode);
			
			Map<String, String> approvalResult = new HashMap<String, String>();
			
		
			//================================================
			// 2. 인증 성공일 경우에만 승인 요청 진행
			//================================================
			if(("0000").equals(responseCode)&&!("1800".equals(serviceCode))){ //가상계좌 제외
				
				//결제 정보 Map에 저장
				authInfo = new HashMap<String,String>();
	
				authInfo.put("serviceId", serviceId);
				authInfo.put("serviceCode", serviceCode);
				authInfo.put("message", message);
	
				//================================
				// 4. 승인 요청 & 승인 응답 결과 설정  
				//================================				
				//승인 요청(Message)
				respMsg = MessageAuthProcess(authInfo);
	
				//결제 수단 별 승인 응답 분리
				//휴대폰
				if("1100".equals(serviceCode)){ 			
	
					//승인 응답
					outResponseCode = respMsg.get("1002");
					outResponseMessage = respMsg.get("1003");
					outDetailResponseCode = respMsg.get("1009");
					outDetailResponseMessage = respMsg.get("1010");
					outTransactionId = respMsg.get("1001");			//거래번호
					authDate = respMsg.get("1005");					//승인일시
					authAmount = respMsg.get("1007");				//승인금액
					partCancelType =respMsg.get("7049");			//부분 취소 타입
					
					//불필요
					approvalResult.put("partCancelType", partCancelType);
				//신용카드	 (카드번호 정확하게 입력해야 해서 테스트 불가)
				}else if("0900".equals(serviceCode)){		
			
					//승인 응답
					outResponseCode = respMsg.get("1002");
					outResponseMessage = respMsg.get("1003");
					outDetailResponseCode = respMsg.get("1009");
					outDetailResponseMessage = respMsg.get("1010");
					outTransactionId = respMsg.get("1001");				//거래번호
					authNumber = respMsg.get("1004");					//승인번호	
					authDate = respMsg.get("1005");						//승인일시
					authAmount = respMsg.get("1007");					//승인금액
					quota = respMsg.get("0031");								//할부개월 수
					cardCompanyCode = respMsg.get("0034");			//카드발급사 코드
					
					approvalResult.put("authNumber", authNumber);
					approvalResult.put("quota", quota);
					approvalResult.put("cardCompanyCode", cardCompanyCode);
				//계좌이체
				}else if("1000".equals(serviceCode)){		
				
					//승인 응답
					//불필요
					outResponseCode = respMsg.get("1002");
					//불필요
					outResponseMessage = respMsg.get("1003");
					//불필요
					outDetailResponseCode = respMsg.get("1009");
					//불필요
					outDetailResponseMessage = respMsg.get("1010");
					//불필요?
					outTransactionId = respMsg.get("1001");			//거래번호
					authAmount = respMsg.get("1007");				//승인금액
					//필요할듯
					authDate = respMsg.get("1005");					//승인일시
					//필요핳듯
					usingType = respMsg.get("0015");					//현금영수증 용도
					//필요할듯
					identifier = respMsg.get("0017");					//현금영수증 승인번호
					//필요할듯
					identifierType = respMsg.get("0102");				//현금영수증 자진발급제유무
					//불필요
					mixType = respMsg.get("0037");						//거래구분
					//불필요
					inputBankCode = respMsg.get("0105");			//은행 코드
					//필요할듯
					inputAccountName = respMsg.get("0107");		//은행 명
					
					approvalResult.put("usingType", usingType);
					approvalResult.put("identifier", identifier);
					approvalResult.put("identifierType", identifierType);
					approvalResult.put("mixType", mixType);
					approvalResult.put("inputBankCode", inputBankCode);
					approvalResult.put("inputAccountName", inputAccountName);
				}else {
					System.out.println("결제 에러 페이지로 (결제수단 코드가 정확하지 않음)");
				}
			}
			
			approvalResult.put("outResponseCode", outResponseCode);
			approvalResult.put("outResponseMessage", outResponseMessage);
			approvalResult.put("outDetailResponseCode", outDetailResponseCode);
			approvalResult.put("outDetailResponseMessage", outDetailResponseMessage);
			approvalResult.put("outTransactionId", outTransactionId);
			approvalResult.put("authAmount", authAmount);
			approvalResult.put("authDate", authDate);
			
			model.addAttribute("authInfo", authInfo);
			model.addAttribute("authResult", authResult);
			model.addAttribute("approvalResult", approvalResult);
		}catch (Exception e) {
			System.out.println("승인요청 오류 페이지");
			e.printStackTrace();
		}
		
		return "PayReturn";
	}
	
	//환불창
	@RequestMapping(value = "cancelInput", method = RequestMethod.GET)
	public String cancelInput(Model model) throws Exception {
		/*-------------------------------------------------------------------------------------
		해당 페이지는 빌게이트 결제 테스트를 위한 "취소 요청 입력" 페이지 입니다.
			※ 가맹점 환경에 맞게 cancelUrl 설정 필요

			※ 상용 전환 시 변경사항
			1. 결제정보(SERVICE_ID) 변경 -> 계약 시 발급 된 SERVICE_ID 정보 입력
			2. config.ini Key, Iv 변경 (가맹점 관리자 어드민에서 확인 가능)
			3. config.ini mode = 1 변경 (상용 모드 설정)

			- 상용 테스트는 실제 취소가 이뤄지는 점 유의하시길 바랍니다.
		-------------------------------------------------------------------------------------*/

		//날짜변수 선언 
		Calendar today = Calendar.getInstance(); 
		String year = Integer.toString(today.get(Calendar.YEAR));
		String month = Integer.toString(today.get(Calendar.MONTH)+1);
		String date = Integer.toString(today.get(Calendar.DATE));
		String hour = Integer.toString(today.get(Calendar.HOUR_OF_DAY));
		String minute = Integer.toString(today.get(Calendar.MINUTE));
		String second = Integer.toString(today.get(Calendar.SECOND));
			
		if(today.get(Calendar.MONTH)+1 < 10) month = "0" + month ;
		if(today.get(Calendar.DATE) < 10) date = "0" + date ;
		if(today.get(Calendar.HOUR_OF_DAY) < 10) hour = "0" + hour ;
		if(today.get(Calendar.MINUTE) < 10) minute = "0" + minute ;
		if(today.get(Calendar.SECOND) < 10) second = "0" + second ;

		//================================================
		// 1. 가맹점 결제 요청 테스트 공통 정보
		//================================================
		//테스트 아이디 일반결제 : glx_api, 월 자동 결제 : glx_at 	
		String orderDate		= year+month+date+hour+minute+second ;
		String orderId			="cancel_"+orderDate;
		String cancelUrl			="http://localhost:9090/billGate/cancelReturn";  // *가맹점 수정 필수
		
		model.addAttribute("serviceId", SERVICE_ID);
		model.addAttribute("orderDate", orderDate);
		model.addAttribute("orderId", orderId);
		model.addAttribute("cancelUrl", cancelUrl);
		
		return "CancelInput";
	}
	
	@RequestMapping(value = "cancelReturn", method = RequestMethod.POST)
	public String cancelReturn (HttpServletRequest request, Model model) throws Exception {
		/*
		------------------------------------------------------------------------------------- 
		해당 페이지는 빌게이트 결제를 위한 "취소 요청" 테스트 페이지 입니다.
		------------------------------------------------------------------------------------- 
		*/	
		String serviceId = null;
		String serviceCode = null;
		String command = null;
		String orderId = null;
		String orderDate = null;
		String transactionId = null;
		String cancelAmount = null;
		String cancelType = null;
		String outTransactionId = null;
		String outCancelAmount = null;
		String outPartCancelSequenceNumber = null;

		String partCancelType = null;			//휴대폰
		String cancelTransactionId=null;		//휴대폰
		String reauthOldTransactionId = null;//휴대폰
		String reauthNewTransactionId = null;	 //휴대폰

		String responseCode = null;
		String responseMessage = null;
		String detailResponseCode = null;
		String detailResponseMessage = null;

		Map<String,String> cancelInfo = null;
		
		try {
			if(null==request.getParameter("TRANSACTION_ID")){
				System.out.println("빌게이트 결제 번호 잘못입력");
			}
			
			request.setCharacterEncoding("euc-kr");

			serviceId = request.getParameter("SERVICE_ID");							//가맹점 서비스 아이디
			serviceCode = request.getParameter("SERVICE_CODE");					//서비스 코드 
			orderId = request.getParameter("ORDER_ID");									//주문 번호
			orderDate = request.getParameter("ORDER_DATE");						//주문 일시
			transactionId = request.getParameter("TRANSACTION_ID");				//거래번호
			
			Map<String, String> cancelParam = new HashMap<String, String>();
			
			cancelParam.put("serviceId", serviceId);
			cancelParam.put("serviceCode", serviceCode);
			cancelParam.put("orderId", orderId);
			cancelParam.put("orderDate", orderDate);
			cancelParam.put("transactionId", transactionId);
			
//			cancelType = request.getParameter("CANCEL_TYPE");						//부분취소 타입
//			cancelAmount= request.getParameter("CANCEL_AMOUNT");				//취소 금액(부분취소 일 경우)
			
			//====================================
			// 결제 수단 별 분리
			//====================================
			Message respMsg = null;
			//취소 요청 정보 Map에 저장
			cancelInfo = new HashMap<String,String>();	
			//신용카드
			if("0900".equals(serviceCode)){				
				//공통 파라미터
				cancelInfo.put("serviceId", serviceId);
				cancelInfo.put("serviceCode", serviceCode);
				cancelInfo.put("orderId", orderId);
				cancelInfo.put("orderDate", orderDate);
				cancelInfo.put("transactionId", transactionId);

				cancelInfo.put("command", "9200");					//전체취소 Command
				
				/*전체취소 요청*/
				respMsg = cancelProcess(cancelInfo);
				
			//계좌이체 
			}else if("1000".equals(serviceCode)){

				//공통 파라미터
				cancelInfo.put("serviceId", serviceId);
				cancelInfo.put("serviceCode", serviceCode);
				cancelInfo.put("orderId", orderId);
				cancelInfo.put("orderDate", orderDate);

				cancelInfo.put("command", "9000");						//전체취소 Command
				cancelInfo.put("transactionId", transactionId);		//전체취소 거래번호

				/*전체취소 요청*/
				respMsg = cancelProcess(cancelInfo);
				
			//휴대폰
			}else if("1100".equals(serviceCode)){
				//공통 파라미터
				cancelInfo.put("command", "9000");				//부분/전체취소 Command 동일
				cancelInfo.put("serviceId", serviceId);
				cancelInfo.put("serviceCode", serviceCode);
				cancelInfo.put("orderId", orderId);
				cancelInfo.put("orderDate", orderDate);
				cancelInfo.put("transactionId", transactionId);

				/*전체취소 요청*/
				respMsg = cancelProcess(cancelInfo);					

			//휴대폰,신용카드,계좌이체 외 모든 결제수단 취소 전문
			}else{
				cancelInfo.put("command", "9000");
				cancelInfo.put("serviceId", serviceId);
				cancelInfo.put("serviceCode", serviceCode);
				cancelInfo.put("orderId", orderId);
				cancelInfo.put("orderDate", orderDate);
				cancelInfo.put("transactionId", transactionId);

				/*전체취소 요청*/
				respMsg = cancelProcess(cancelInfo);
			}
		  
			/*
			취소 요청에 대한 응답 결과 설정
			*/
			//공통
			responseCode = respMsg.get("1002");
			responseMessage = respMsg.get("1003");
			detailResponseCode = respMsg.get("1009");
			detailResponseMessage = respMsg.get("1010");
			
			Map<String, String> cancelResult = new HashMap<String, String>();
			
			cancelResult.put("responseCode", responseCode);
			cancelResult.put("responseMessage", responseMessage);
			cancelResult.put("detailResponseCode", detailResponseCode);
			cancelResult.put("detailResponseMessage", detailResponseMessage);

			//신용카드 전용
			if("0900".equals(serviceCode)){
				outTransactionId = respMsg.get("1001");		//거래번호
					
				//전체취소
				outCancelAmount = respMsg.get("1033");	//취소금액
			
			//계좌이체 전용
			}else if("1000".equals(serviceCode)){
				outCancelAmount = respMsg.get("1033");		//취소금액

				outTransactionId  = respMsg.get("1001");		//거래번호
					
			//휴대폰 전용
			}else if("1100".equals(serviceCode)){
				outTransactionId  = respMsg.get("1001");			//거래번호

				outCancelAmount  = respMsg.get("1007");		//취소금액
			}else{
				outTransactionId  = respMsg.get("1001");			//거래번호
			}
			
			System.out.println(outCancelAmount);
			cancelResult.put("outTransactionId", outTransactionId);
			cancelResult.put("outCancelAmount", outCancelAmount);
			
			model.addAttribute("cancelParam", cancelParam);
			model.addAttribute("cancelInfo", cancelInfo);
			model.addAttribute("cancelResult", cancelResult);
			
			//취소 금액, 취소 상태만 있으면 될듯 거래번호도?
		}catch (Exception e) {
			System.out.println("환불 에러 페이지");
			e.printStackTrace();
		}
		return "CancelReturn";
	}
	
	//체크썸 생성
	@ResponseBody
	@RequestMapping(value = "payCheckSum", method = RequestMethod.POST)
	public String makeCheckSum(@RequestBody Map<String, String> checkSumInfo) throws Exception{
		String temp = SERVICE_ID + checkSumInfo.get("orderId") + checkSumInfo.get("amount");
		String checkSum = ChecksumUtil.genCheckSum(temp);
		
		return checkSum;
	}
	
	//사용 메서드 추후 클래스 분리 필요
	// 승인 요청
	public Message MessageAuthProcess(Map<String,String> authInfo) throws Exception {
		String serviceId = authInfo.get("serviceId");
		String serviceCode = authInfo.get("serviceCode");
		String msg = authInfo.get("message");

		//메시지 Length 제거
		byte[] b = new byte[msg.getBytes().length - 4] ;
		System.arraycopy(msg.getBytes(), 4, b, 0, b.length);

		Message requestMsg = new Message(b, getCipher(serviceId,serviceCode)) ;
		
		Message responseMsg = null ;

		ServiceBroker sb = new ServiceBroker(CONF_PATH, serviceCode);

		responseMsg = sb.invoke(requestMsg);
		
		return responseMsg;
	}
	
	//설정 파일을 통해 key, iv값 가져옴
	private GalaxiaCipher getCipher(String serviceId, String serviceCode) throws Exception {
		GalaxiaCipher cipher = null ;

		String key = null ;
		String iv = null ;
		
		try {
			ConfigInfo config = new ConfigInfo(CONF_PATH, serviceCode);
			key = config.getKey();
			iv = config.getIv();
			
			cipher = new Seed();
			cipher.setKey(key.getBytes());
			cipher.setIV(iv.getBytes());
		} catch(Exception e) {
			throw e ;
		}
		return cipher;
	}
	
	//전체 취소 요청
	public Message cancelProcess(Map<String,String> cancelInfo) throws Exception {
		String serviceId = cancelInfo.get("serviceId");
		String serviceCode =cancelInfo.get("serviceCode");
		String orderId = cancelInfo.get("orderId");
		String orderDate = cancelInfo.get("orderDate");
		String transactionId = cancelInfo.get("transactionId");
		String command = cancelInfo.get("command");

		Message requestMsg = new Message(VERSION, serviceId, 
				serviceCode,
				command,				
				orderId, 
				orderDate, 
				getCipher(serviceId, serviceCode)) ;

		Message responseMsg = null ;
		
		//공통
		if(transactionId != null) requestMsg.put("1001", transactionId);
		
		//전문 통신
		ServiceBroker sb = new ServiceBroker(CONF_PATH, serviceCode);    

		responseMsg = sb.invoke(requestMsg);
		
		return responseMsg;
	}
}
