<?php
// array for JSON response
$response = array();
 
// include db connect class
require_once __DIR__ . '/include/DB_Connect.php';
 
// connecting to db
$db = new DB_Connect();

require_once '/DB_CheckLogin.php';
if($sessionSuccess == 1){
	if (isset($_POST['notificationID'])) {
		$notificationID = $_POST['notificationID'];
		$result = mysql_query("SELECT * FROM `notification` WHERE `notificationID` = $notificationID");
		if (!empty($result)) {
        // check for empty result
        if (mysql_num_rows($result) > 0) { 
		$result = mysql_fetch_array($result);
		$senderType = $result["senderType"];
		$senderID = $result["senderID"];
		$receiverType = $result["receiverType"];
		$receiverID = $result["receiverID"];
		if($receiverType == "Person"){
			$declineResult = mysql_query("DELETE FROM `friend` WHERE `friend_one` = '$senderID' and `friend_two` = '$receiverID' OR  `friend_one` = '$receiverID' and `friend_two` = '$senderID'");
			if ($declineResult) {
        	// inserted into database failed
        	$response["success"] = 1;
			$response["message"] = "Decile friend request success";
			// echoing JSON response
        	echo json_encode($response);				
			}
		else{
        	// inserted into database failed
        	$response["success"] = 0;
			$response["message"] = "Decile friend request failed";
			// echoing JSON response
        	echo json_encode($response);
		}
		}else if($receiverType == "Group"){
			//nothing to do
		}		
		} else {
            // no event found
            $response["success"] = 0;
			$response["message"] = "Get notification failed."; 
            // echo no users JSON
            echo json_encode($response);
        }
    } else {
        // no event found
        $response["success"] = 0;
	    $response["message"] = "Database conncetion failed"; 
        // echo no users JSON
        echo json_encode($response);
    }
	} 
		else {
			// required field is missing
			$response["success"] = 0;
			$response["message"] = "Pass data failed.";
			// echoing JSON response
			echo json_encode($response);
			}
			}else{
				// failed
				$response["success"] = 0;
				$response["message"] = $sessionMessage;
				// echoing JSON response
				echo json_encode($response);	
} 
?>