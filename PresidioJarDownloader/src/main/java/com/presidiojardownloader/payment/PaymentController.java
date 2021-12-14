package com.presidiojardownloader.payment;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.paytm.pg.merchant.PaytmChecksum;
import com.presidiojardownloader.mail.EmailSenderService;

@Controller
public class PaymentController {
	
	@Autowired
	private PaytmDetailPojo paytmDetailPojo;
	@Autowired
	private Environment env;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private EmailSenderService emailSenderService;
	
	
	//@GetMapping("/{senderId}/{jarId}/{orderId}/{receiverId}")
	
//	HttpServletRequest request ,
//	Model model,
//	@PathVariable("senderId")String senderId,
//	@PathVariable("jarId")String jarId,
//	@PathVariable("orderId")String orderId,
//	@PathVariable("receiverId")String receiverId
	
	@GetMapping("/payment/{senderId}/{jarId}/{orderId}/{receiverId}")
//	@GetMapping("/")
	public String home(HttpServletRequest request , Model model ,
					   @PathVariable("senderId")String senderId,
					   @PathVariable("jarId")String jarId,
					   @PathVariable("orderId")String orderId,
					   @PathVariable("receiverId")String receiverId
					   ) {
		
		model.addAttribute("senderId", senderId);
		model.addAttribute("orderId", orderId);
		model.addAttribute("jarId", jarId);
		model.addAttribute("receiverId", receiverId);
		
		
//		System.out.println(senderId +" "+orderId+" "+jarId+" "+receiverId);
		
		
		return "home";
	}

	 @PostMapping(value = "/submitPaymentDetail")
	    public ModelAndView getRedirect(@RequestParam(name = "CUST_ID") String customerId,
	                                    @RequestParam(name = "TXN_AMOUNT") String transactionAmount,
	                                    @RequestParam(name = "ORDER_ID") String orderId,
	                                    @RequestParam(name="JAR_ID") String jarId,
	                                    @RequestParam(name="RECEIVER_ID")String receiverId
	                                    ) throws Exception {

		 	OrderController order = new OrderController(orderRepository);
		 	
		 	ModelAndView modelAndView = new ModelAndView("redirect:" + paytmDetailPojo.getPaytmUrl());
	        TreeMap<String, String> parameters = new TreeMap<>();
	        paytmDetailPojo.getDetails().forEach((k, v) -> parameters.put(k, v));
	        parameters.put("MOBILE_NO", env.getProperty("paytm.mobile"));
	        parameters.put("EMAIL", env.getProperty("paytm.email"));
	        parameters.put("ORDER_ID", orderId);
	        parameters.put("TXN_AMOUNT", transactionAmount);
	        parameters.put("CUST_ID", customerId);
	        Long cus_id = Long.valueOf(customerId);
	        Long jar_id = Long.valueOf(jarId);
	        Long rec_Id = Long.valueOf(receiverId);
	        String checkSum = getCheckSum(parameters);
	        parameters.put("CHECKSUMHASH", checkSum);
	        modelAndView.addAllObjects(parameters);
	        order.saveOrder(orderId,cus_id,jar_id, rec_Id, transactionAmount);
	        return modelAndView;
	    }
	 

	 
	 @PostMapping(value = "/pgresponse")
	    public String getResponseRedirect(HttpServletRequest request, Model model) {
		 OrderController order = new OrderController(orderRepository);
		 Map<String, String[]> mapData = request.getParameterMap();
		 
	        TreeMap<String, String> parameters = new TreeMap<String, String>();
	        String paytmChecksum = "";
	        for (Entry<String, String[]> requestParamsEntry : mapData.entrySet()) {
	            if ("CHECKSUMHASH".equalsIgnoreCase(requestParamsEntry.getKey())){
	                paytmChecksum = requestParamsEntry.getValue()[0];
	            } else {
	            	parameters.put(requestParamsEntry.getKey(), requestParamsEntry.getValue()[0]);
	            }
	        }
	        String result;

	        boolean isValideChecksum = false;
	       String OrderId = parameters.get("ORDERID");
	        
	       // System.out.println("The order is -----------------------------------------------       "+OrderId);
	        System.out.println("RESULT : "+parameters.toString());
	        try {
	            isValideChecksum = validateCheckSum(parameters, paytmChecksum);
	            System.out.println("Validating Check Sum --->" + isValideChecksum);
	            if (isValideChecksum && parameters.containsKey("RESPCODE")) {
	                if (parameters.get("RESPCODE").equals("01")) {
	                	order.updateOrder(OrderId);
	                	Order ord= orderRepository.findOrderById(OrderId);
	                	String jarName = orderRepository.findJarNameByJarId(ord.getJarId());
	                	String userName = orderRepository.findUserNameByUserId(ord.getReceiverId());
	                	String senderMail=orderRepository.findEmailByUserId(ord.getSenderId());
	                	System.out.println("You have paid for "+jarName+" created by "+userName);
	                	String body = "<h1>Hi Thanks For Supporting.</h1>\n";
	                	body+="<strong>Donated To </strong>"+":"+"<p>"+userName+"</p>\n";
	                	body+="<strong>Donated For</strong>"+":"+"<p>"+jarName+"</p>\n";
	                	body+="<strong>Amount</strong>"+":"+"<p>"+parameters.get("TXNAMOUNT")+"</p>\n";
	                	body+="<strong>OrderId</strong>"+":"+"<p>"+parameters.get("ORDERID")+"</p>\n";
	                	body+="<strong>TransactionID</strong>"+":"+"<p>"+parameters.get("TXNID")+"</p>\n";
	                	body+="<strong>Transaction date</strong>"+":"+"<p>"+parameters.get("TXNDATE")+"</p>\n";
	                	body+="<strong>Status</strong>"+":"+"<p color=\"green\">Success</p>\n";
	                	emailSenderService.sendSimpleEmailWithHtml(senderMail, body,"Transaction Status");
	                    result = "Payment Successful";
	                } else {
	                	order.deleteOrder(OrderId);
	                    result = "Payment Failed";
	                }
	            } else {
	            	order.deleteOrder(OrderId);
	                result = "Checksum mismatched";
	            }
	        } catch (Exception e) {
	            result = e.toString();
	        }
	        model.addAttribute("result",result);
	        parameters.remove("CHECKSUMHASH");
	        model.addAttribute("parameters",parameters);
	        return "report";
	    }

	    private boolean validateCheckSum(TreeMap<String, String> parameters, String paytmChecksum) throws Exception {
	    	System.out.println("mid "+parameters.get("MID") + " Order id "+ parameters.get("ORDERID"));
//	    	TreeMap<String , String >gcs = new TreeMap<>();
//			gcs.put("MID", parameters.get("MID"));
//			gcs.put("ORDERID", parameters.get("ORDERID"));
//	    	String body = "{"\"MID"\":"\parameters.get("MID")\","\"ORDERID"\":"\parameters.get("ORDERID")\"}";
	    	/* initialize JSON String */
	    	//String body = "{"\mid\":"\parameters.get("MID")\","\orderId\":"\YOUR_ORDER_ID_HERE\"}"
	        return PaytmChecksum.verifySignature(parameters,
	                paytmDetailPojo.getMerchantKey(), paytmChecksum);
	    }


	private String getCheckSum(TreeMap<String, String> parameters) throws Exception {
		System.out.println("Validate CheckSum "+parameters.toString());
//		String message="";
//		
//		TreeMap<String , String >gcs = new TreeMap<>();
//		gcs.put("MID", parameters.get("MID"));
//		gcs.put("ORDERID", parameters.get("ORDERID"));
		return PaytmChecksum.generateSignature(parameters, paytmDetailPojo.getMerchantKey());
	}
	
	
	
}

