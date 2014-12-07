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
  if(isset($_POST['senderType']) && isset($_POST['senderID']) && isset($_POST['receiverType']) && isset($_POST['receiverID']) && isset($_POST['acceptable']) && isset($_POST['notificationMessage'])){
        $senderType = $_POST['senderType'];
		$senderID = $_POST['senderID'];
        $receiverType = $_POST['receiverType'];
        $receiverID = $_POST['receiverID'];
        $acceptable = $_POST['acceptable'];
        $sendtime = $_POST['sendTime'];
        $notificationMessage = $_POST['notificationMessage'];
		if($receiverType == "User"){
			$receiverUserID = $receiverID;
		}else if($receiverType == "Group"){
			//check if user is already in grouop
			$checkUserMembership = mysql_query("SELECT `userid` from `groupjoinin` WHERE `userID` = '$senderID' AND `groupID` = '$receiverID'");
			if(!empty($checkUserMembership)){
				if (mysql_num_rows($checkUserMembership) = 0) {			
			$getCreaterIDresult = mysql_query("SELECT `groupCreaterID` FROM `group` WHERE `groupID` = '$receiverID'");
			if(!empty($getCreaterIDresult)){
				if (mysql_num_rows($getCreaterIDresult) > 0) {
					$getCreaterIDresult = mysql_fetch_array($getCreaterIDresult);
					$receiverUserID = $getCreaterIDresult["groupCreaterID"];
				} else {
            // no event found
            $response["success"] = 0;
			$response["message"] = "Get group creater id failed."; 
            // echo no users JSON
            echo json_encode($response);
        }
    } else {
        // no event found
        $response["success"] = 0;
	    $response["message"] = "Group table conncetion failed"; 
        // echo no users JSON
        echo json_encode($response);
			}
			} else {
            // no event found
            $response["success"] = 0;
			$response["message"] = "You have already joined in this grouop."; 
            // echo no users JSON
            echo json_encode($response);
        }
    } else {
        // no event found
        $response["success"] = 0;
	    $response["message"] = "Joined in group table conncetion failed"; 
        // echo no users JSON
        echo json_encode($response);
			}
		}
		
	$result = mysql_query("INSERT INTO `wanna`.`notification` (`notificationNO`, `senderType`, `senderID`, `receiverType`, `receiverID`, `receiverUserID`, `acceptable`, `readStatus`, `message`, `sendTime`)
        VALUES (NULL, '$senderType', '$senderID', '$receiverType', '$receiverID', '$receiverUserID', '$acceptable', '0', '$notificationMessage', '$sendtime')");
	if ($result) {
	$response["success"] = 1;
        $response["message"] = "Insert Notification Succeed!"; 
        // echoing JSON response
        echo json_encode($response);
        }
        else{
        $response["success"] = 0;
        $response["message"] = "Insert Notification failed!"; 
        // echoing JSON response
        echo json_encode($response);
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