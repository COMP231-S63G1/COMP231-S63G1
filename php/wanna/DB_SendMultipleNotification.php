<?php
 
/*
 * Following code will accept a friend request update a row in friend table if the user accept the friend request
 *
 */
 
// array for JSON response
$response = array();
$gcm_regid = array();
$gcm_data = array();
 
// include db connect class
require_once __DIR__ . '/include/DB_Connect.php';
 
// connecting to db
$db = new DB_Connect();

require_once __DIR__ . '/DB_CheckLogin.php';

if($sessionSuccess == 1){
	$userID=$_SESSION['userid'];
	// check for post data
  if(isset($_POST['senderType']) && isset($_POST['senderID']) && isset($_POST['receiverType']) && isset($_POST['acceptable']) && isset($_POST['notificationMessage'])){
        $senderType = $_POST['senderType'];
	    $senderID = $_POST['senderID'];
        $receiverType = $_POST['receiverType'];
        $acceptable = $_POST['acceptable'];
        $sendtime = $_POST['sendTime'];
        $notificationMessage = $_POST['notificationMessage'];
        $date = date('Y-m-d H:i:s');    
		if($senderType == "Event"){
			$result = mysql_query("SELECT `userID` FROM `eventjoinin` where `eventid` = $senderID");
                       if (!empty($result)) {
                       // check for empty result
                       if (mysql_num_rows($result) > 0) {
                        
			while ($row = mysql_fetch_array($result)) {
			$receiverID = $receiverUserID = $row['userID'];
			$resultForNotification = mysql_query("INSERT INTO `wanna`.`notification` (`notificationID`, `senderType`, `senderID`, `receiverType`, `receiverID`, `receiverUserID`, `acceptable`, `readStatus`, `message`, `sendTime`) VALUES (NULL, '$senderType', '$senderID', '$receiverType', '$receiverID', '$receiverUserID', '$acceptable', '0', '$notificationMessage', '$sendtime')");	
			$gcmresult = mysql_query("SELECT `gcm_regid` FROM `users` where `userid` = $receiverUserID ");
			if (!empty($gcmresult )) {
                       // check for empty result			
                       if (mysql_num_rows($gcmresult ) > 0) {       
                       while ($row = mysql_fetch_array($gcmresult )) {
                       $single_gcm_regid = $row['gcm_regid'];
                       array_push($gcm_regid, $single_gcm_regid );
                       array_push($gcm_data , $notificationMessage);
                       //$gcm_regid = $row['gcm_regid'];
                       //$gcm_data = $notificationMessage;
	                }
	                }	
	                else{
    			     $response["success"] = 0;
   			     $response["message"] = "mysql row number not bigger than o"; 
   			     // echoing JSON response
			     echo json_encode($response);
  			        }
	                }	
	                else{
    			     $response["success"] = 0;
   			     $response["message"] = "fetch gcm_regid from database failed"; 
   			     echo json_encode($response);
  			        }
  			        
	                
  			        $response["success"] = 1;
     				$response["message"] = "Insert Notification Succeed 1! receiverUserID: " . $receiverUserID  . " gcm_regid: " . $single_gcm_regid  .  " gcm_data: " . $notificationMessage; 
				echo json_encode($response);
	                }
	                require_once __DIR__ . '/GCM_PushNotification.php';
	                if ($resultForNotification) {
	                $response["success"] = 1;
	                $response["message"] = "Insert Notification Succeed 2! gcm_regid: " . $single_gcm_regid . " gcm_data: " .$notificationMessage. " resultForNotification: " . $result; 
	                // echoing JSON response
    			 
    			     }
    			     else{
    			     $response["success"] = 0;
   			         $response["message"] = "Insert Notification failed!"; 
   			        // echoing JSON response
  			        }
					echo json_encode($response);
		      }
			  else{
    			     $response["success"] = 0;
   			         $response["message"] = "mysql row number not bigger than o"; 
   			        // echoing JSON response
					echo json_encode($response);
  			        }
                    }
                      else{
		            $response["success"] = 0;
   			    $response["message"] = "There is no person join this event!"; 
   			    // echoing JSON response
  			    echo json_encode($response);

                      }
                  }
			
		
	    if($senderType == "Group"){
			$result = mysql_query("SELECT `userID` FROM `groupjoinin` where `groupid` = $senderID");
                       if (!empty($result)) {
                       // check for empty result
                       if (mysql_num_rows($result) > 0) {
                        
			while ($row = mysql_fetch_array($result)) {
                $receiverID = $receiverUserID = $row['userID'];
				$resultforNodification = mysql_query("INSERT INTO `wanna`.`notification` (`notificationID`, `senderType`, `senderID`, `receiverType`, `receiverID`, `receiverUserID`, `acceptable`, `readStatus`, `message`, `sendTime`)
       				 VALUES (NULL, '$senderType', '$senderID', '$receiverType', '$receiverID', '$receiverUserID', '$acceptable', '0', '$notificationMessage', '$sendtime')");
				$gcmresult = mysql_query("SELECT `gcm_regid` FROM `users` where `userid` = $receiverUserID ");
			if (!empty($gcmresult )) {
                       // check for empty result			
                       if (mysql_num_rows($gcmresult ) > 0) {       
                       while ($row = mysql_fetch_array($gcmresult )) {
                       $gcm_regid = $row['gcm_regid'];
                       $gcm_data = $notificationMessage;
	                require_once __DIR__ . '/GCM_PushNotification.php';
	                }
	                }
	                else{
    			     $response["success"] = 0;
   			     $response["message"] = "mysql row number not bigger than o"; 
   			     // echoing JSON response
			     echo json_encode($response);
  			        }
	                }
	                else{
    			     $response["success"] = 0;
   			     $response["message"] = "fetch gcm_regid from database failed"; 
   			     echo json_encode($response);
  			        }
  			        }
					if ($resultforNodification) {
				    $response["success"] = 1;
     				$response["message"] = "Insert Notification Succeed!"; 
    			        // echoing JSON response
    			       
    			        }
    			    else{
    			    $response["success"] = 0;
   			        $response["message"] = "Insert Notification failed!"; 
   			        // echoing JSON response
  			       
	                 }
					  echo json_encode($response);
		            }
					 else{
    			     $response["success"] = 0;
   			         $response["message"] = "mysql row number not bigger than o"; 
   			        // echoing JSON response
					echo json_encode($response);
  			        }
                    }
                      else{
		            $response["success"] = 0;
   			    $response["message"] = "There is no person join this event!"; 
   			    // echoing JSON response
  			    echo json_encode($response);

                      }
                  }
   }
   else{
       // data pass failed 
        $response["success"] = 0;
        $response["message"] = "Data pass failed";
 
        // echo no users JSON
        echo json_encode($response);
       }
	   }
else{

		// failed
		$response["success"] = 0;
		$response["message"] = $sessionMessage;
		// echoing JSON response
		echo json_encode($response);	

}
?>