<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.6.0.js"></script> 
<title>Welcome</title>
<style>
        input::-webkit-outer-spin-button,
        input::-webkit-inner-spin-button {
            -webkit-appearance: none;
            margin: 0;
        }

        .parentContainer {
            margin: 3% 10%;

        }
        input{
            margin-bottom:15px;
        }
        .container {
            display: flex;
            flex-direction: row;
            justify-content: space-between;
        
        }
        .formStyle{
            display: block;
            margin: 20% 30% 20% ;

        }
        /* #historyTable {
  			table-layout: fixed;
  			width: 100%;
  			border-collapse: collapse;
  			border: 3px solid purple;
			} */
			
	   #historyDiv {
 			height: 300px;
 			width:10000px;
 			overflow-y: scroll;
		}
			
	   #historyTable { background-color:#eee;border-collapse:collapse; }
       #historyTable th { background-color:#000;color:white;width:50%; }
       #historyTable td { padding:5px;border:1px solid #000; }
       
    </style>
</head>
<body>
<%
/*  if(session.getAttribute("cid")==null){
response.sendRedirect("login.jsp");
}  */ 

response.setHeader("Cache-Control", "no-cache, no-store, must-reavalidate");
response.setHeader("Expires", "0");
System.out.println(request.getHeader("referer"));
if(request.getHeader("referer")==null || session.getAttribute("cid")==null) response.sendRedirect("WelcomePage.html");

%>
<div class="parentContainer">
        <div class="container">
            <div class="formContainer">
                <h3 id="withdraw">Withdraw</h3>
                
            </div>  
            <div class="formContainer">
                <h3 id="deposit">Deposit</h3>
            </div>    
            <div class="formContainer">
                <h3 id="accountTrans">Account Transfer</h3>
            </div>
            <div class="formContainer">
                <h3 id="transferHistory">Transfer History</h3>
            </div>
            <form class="formContainer" action="Logout" method="Post">
                
                <input type= submit value="Logout">
            </form>
            
    </div>
    <form id="withdrawForm" class="formStyle">
        <table>
            <tr>
                <td><label>Withdrawal Amount:</label></td>
                <td><input type="text" id="withamount" name="withdrawAmount"><br></td>
                 
            </tr>
            
        </table>
   		  
        <input type="submit" id="withdrawBut" name="withdrawBut" value="Withdraw">
        
        <small id="insufficientBalance" style="color:red;">Insufficient Balance </small>
    </form>
    <p id="afterwithtext" class="formStyle"></p>
    <form id="depositForm" class="formStyle">
        <table>
            <tr>
                <td><label>Deposit Amount:</label></td>
                <td><input type="text" id="depamount" name="depAmount"><br></td>
            </tr>
        </table>
        <input type="submit" value="Deposit">
        
    </form>
    <p id="afterdeptext" class="formStyle"></p>
    <form id="accountTransForm" class="formStyle">
    	<table>
            <tr>
                <td><label>Transfer Amount :</label></td>
                <td><input type="number" id="transferamount" name="transferAmount"><br></td>
                <td><label>Transfer Account Number :</label></td>
                <td><input type="number" id="transfertoid" name="transferToId"><br></td>
            </tr>
        </table>
        <input type="submit" value="Transfer">
        
        <small id="insufficientBalanceTransfer" style="color:red;">Insufficient Balance </small>
        <small id="invalidAccNum" style="color:red;">Invalid AccountNumber </small>
    </form>
    <p id="aftertranstext" class ="formStyle"></p>
    <div id="historyDiv"class="formStyle">

		<table id='historyTable'>
			<caption><b>Transactions</b></caption>
            <tr>
                <th>TransactionID</th>
                <th>Transaction Type</th>
                <th>Transaction Amount</th>
                <th>Updated Balance</th>
            </tr>
    	</table>
    </div>
    </div>
<script>
    $(document).ready(function(){


        $('#depositForm').hide()
        $('#withdrawForm').hide()
        $('#accountTransForm').hide()
        $('#afterwithtext').hide()
        $('#afterdeptext').hide()
        $('#aftertranstext').hide()
        $('#insufficientBalance').hide()
        $('#insufficientBalanceTransfer').hide()
        $('#invalidAccNum').hide()
        $('#historyDiv').hide()
		//$('#historyTable').hide()
		
        $('#withdraw').css({ 'cursor': 'pointer' });

        $('#deposit').css({ 'cursor': 'pointer' });

        $('#accountTrans').css({ 'cursor': 'pointer' });

        $('#transferHistory').css({ 'cursor': 'pointer' });
        
        $('#logout').css({ 'cursor': 'pointer' });

        $('#withdraw').click(function(){
        	$('#withdrawForm').toggle()
            $('#depositForm').hide()
            $('#accountTransForm').hide()
            $('#historyDiv').hide()
            $('#afterwithtext').hide()
            $('#afterdeptext').hide()
            $('#aftertranstext').hide()
            //$('#historyTable').hide();
        	
         })

         $('#deposit').click(function(){
        	 $('#withdrawForm').hide()
             $('#depositForm').toggle()
             $('#accountTransForm').hide()
             $('#historyDiv').hide()
             $('#afterwithtext').hide()
             $('#afterdeptext').hide()
             $('#aftertranstext').hide()
            // $('#historyTable').hide();
         })

         $('#accountTrans').click(function(){
        	 $('#withdrawForm').hide();
             $('#depositForm').hide()
             $('#accountTransForm').toggle()
             $('#historyDiv').hide()
             $('#afterwithtext').hide()
             $('#afterdeptext').hide()
             $('#aftertranstext').hide()
             //$('#historyTable').hide();
         })
		
         
         
         $('#transferHistory').click(function(){
        	 
        	 $('#withdrawForm').hide();
             $('#depositForm').hide()
             $('#accountTransForm').hide()
             $('#historyDiv').toggle()
             $('#afterwithtext').hide()
             $('#afterdeptext').hide()
             $('#aftertranstext').hide()
           // $('#historyTable').show();
             $("#historyTable tr").remove();
             
             $.ajax({
            	 
            	 method : "POST",
            	 url:"Operations",
            	 dataType:"json",
            	 data:JSON.stringify({"operation":"as"})
            	 
             }).done(function(data){
            	
            	 var transaction ='';
            	 $.each(data, function(key,value){
            		 
            		 transaction += '<tr>';
            		 transaction += '<td>' + value.tid + '</td>';
                     transaction += '<td>' + value.type + '</td>';
                     transaction += '<td>' +  value.amount + '</td>';    
                     transaction += '<td>' + value.balance + '</td>';
                     transaction += '</tr>';    
     
            	 })
            	 $('#historyTable').append(transaction)
             }) 
         })
         
         $('#withdrawForm').submit(function(e){
			 
			 e.preventDefault()
			 let amount = $('#withamount').val();
	
			 $.ajax({
      			
      			method:"POST",
      			url:"Operations",
      			data:JSON.stringify({"amount":amount,"operation":"withdraw"}), 
      			dataType:"json",
      	
      		}).done(function(data){
      			
      			if(data.check == "1"){
      				
      				$('#withdrawForm').hide()
      				$('#afterwithtext').show()
      				$('#insufficientBalance').hide()
      				$('#afterwithtext').html("Withdraw Successfull!<br>Updated Balance :"+data.balance )
      			}
      			else{
      				$('#insufficientBalance').show()
      			}
      		})
		 }) 
		 
		 $('#depositForm').submit(function(e){
			 
			 e.preventDefault()
			 let amount = $('#depamount').val();
			
			 
			 $.ajax({
      			
      			method:"POST",
      			url:"Operations",
      			data:JSON.stringify({"amount":amount,"operation":"deposit"}), 
      			dataType:"json",
      	
      		}).done(function(data){
      			
      			$('#depositForm').hide()
      			$('#afterdeptext').show()
      			$('#afterdeptext').html("Deposit Successfull!<br>Updated Balance :"+data.balance )
     
      		})
		 })
		 
		 $('#accountTransForm').submit(function(e){
			 
			 e.preventDefault()
			 let amount = $('#transferamount').val();
			 let tacNum = $('#transfertoid').val();
			 
			 
			 //console.log(amount+' '+tid+' ')
			 
			 $.ajax({
      			
      			method:"POST",
      			url:"Operations",
      			data:JSON.stringify({"amount":amount,"tacNum":tacNum,"operation":"accountTransfer"}), 
      			dataType:"json",
      	
      		}).done(function(data){
      			if(data.check =="1"){
      				$('#accountTransForm').hide()
      				$('#aftertranstext').show()
      				$('#aftertranstext').html("Transfer Success!<br>Updated Balance :"+data.balance );
      				$('#insufficientBalanceTransfer').hide()
      		        $('#invalidAccNum').hide()
      			}
      			else{
      				if(data.balance-amount>=1000){
      					$('#invalidAccNum').show()
      					$('#insufficientBalanceTransfer').hide()
      				}
      				else{
      					$('#insufficientBalanceTransfer').show()
      					$('#invalidAccNum').hide()
      				}
      		        
      			}
     
      		})
		 })
		 
		 
    })

    
</script>
</body>
</html>