<?php
// array for JSON response
$response = array();
 
// include db connect class
require_once __DIR__ . '/include/DB_Connect.php';
 
// connecting to db
$db = new DB_Connect();

require_once __DIR__ . '/DB_CheckLogin.php';
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
		if($receiverType == "User"){
			$acceptResult = mysql_query("UPDATE `friend` SET `status`='1' WHERE `friend_one` = '$senderID' and `friend_two` = '$receiverID' OR  `friend_one` = '$receiverID' and `friend_two` = '$senderID'");		
		}else if($receiverType == "Group"){
			$acceptResult = mysql_query("INSERT INTO `groupjoinin` (`userID`, `groupID`) VALUES ('$senderID', '$receiverID')");			
		}		
		if ($acceptResult) {
        	// inserted into database failed
        	$response["success"] = 1;
			$response["message"] = "Accept request success";
			// echoing JSON response
        	echo json_encode($response);				
			}
		else{
        	// inserted into database failed
        	$response["success"] = 0;
			$response["message"] = "Accept request failed";
			// echoing JSON response
        	echo json_encode($response);
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