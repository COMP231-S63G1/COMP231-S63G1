<?php
 
/*
 * Following code will accept a friend request update a row in friend table if the user accept the friend request
 *
 */
 
// array for JSON response
$response = array();
 
// include db connect class
require_once __DIR__ . '/include/DB_Connect.php';
 
// connecting to db
$db = new DB_Connect();

require_once '/DB_CheckLogin.php';

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
				$resultForNotification = mysql_query("INSERT INTO `wanna`.`notification` (`notificationID`, `senderType`, `senderID`, `receiverType`, `receiverID`, `receiverUserID`, `acceptable`, `readStatus`, `message`, `sendTime`)
       				 VALUES (NULL, '$senderType', '$senderID', '$receiverType', '$receiverID', '$receiverUserID', '$acceptable', '0', '$notificationMessage', '$sendtime')");
	                }
					 if ($resultForNotification) {
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
			
		
	    if($senderType == "Group"){
			$result = mysql_query("SELECT `userID` FROM `groupjoinin` where `groupid` = $senderID");
                       if (!empty($result)) {
                       // check for empty result
                       if (mysql_num_rows($result) > 0) {
                        
			while ($row = mysql_fetch_array($result)) {
                $receiverID = $receiverUserID = $row['userID'];
				$resultforNodification = mysql_query("INSERT INTO `wanna`.`notification` (`notificationID`, `senderType`, `senderID`, `receiverType`, `receiverID`, `receiverUserID`, `acceptable`, `readStatus`, `message`, `sendTime`)
       				 VALUES (NULL, '$senderType', '$senderID', '$receiverType', '$receiverID', '$receiverUserID', '$acceptable', '0', '$notificationMessage', '$sendtime')");
				
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