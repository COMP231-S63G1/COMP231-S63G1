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
		$response["notification"] = array();
		if (!empty($result)) {
        // check for empty result
        if (mysql_num_rows($result) > 0) { 
		$result = mysql_fetch_array($result);
		$notification = array();
		$senderType = $result["senderType"];
		$senderID = $result["senderID"];
		$notification["notificationMessage"] = $result["message"];
		$notification["sendTime"] = $result["sendTime"];
		if($senderType == "User"){
			$senderResult = mysql_query("SELECT `username` AS senderName from `users` WHERE `userid` = $senderID");
		}else if($senderType == "Event"){
			$senderResult = mysql_query("SELECT `eventname` AS senderName from `event` WHERE `event` = $senderID");			
		}else if($senderType == "Group"){
			$senderResult = mysql_query("SELECT `groupname` AS senderName from `group` WHERE `groupid` = $senderID");			
		}
		if (!empty($senderResult)) {
        // check for empty result
        if (mysql_num_rows($senderResult) > 0) { 			
            $senderResult = mysql_fetch_array($senderResult);
			$notification["senderName"] = $senderResult["senderName"];
			// push single event into final response array
			array_push($response["notification"], $notification);
                        $response["success"] = 1;
			$response["message"] = "Get sender name";
            // echo no users JSON
            echo json_encode($response);
		 } else {
            // no sender found
            $response["success"] = 0;
			$response["message"] = "Get sender name failed."; 
            // echo no users JSON
            echo json_encode($response);
        }
    } else {
        // no event found
        $response["success"] = 0;
	    $response["message"] = "Sender table conncetion failed"; 
        // echo no users JSON
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
		} else {
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