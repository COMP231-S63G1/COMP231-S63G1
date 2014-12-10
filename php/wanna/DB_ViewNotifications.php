<?php
$response = array();
 
// include db connect class
require_once __DIR__ . '/include/DB_Connect.php';
 
// connecting to db
$db = new DB_Connect();
require_once __DIR__ . '/DB_CheckLogin.php';
if($sessionSuccess == 1){
if (isset($_POST["userid"])) {
$userid = $_POST["userid"];
$userType = $_SESSION['userType'];

if($userType == "Person"){
	$result = mysql_query("SELECT * FROM `notification` WHERE `receiverUserID`=$userid ");
}else if($userType == "Organization"){
	$result = mysql_query("SELECT * FROM `notification` WHERE `receiverUserID`=$userid");
}
	
	if (!empty($result)) {
        // check for empty result
        if (mysql_num_rows($result) > 0) {
			// looping through all results
			$response["notificationJSONList"] = array();
			while ($row = mysql_fetch_array($result)) {
				// temp user array
				$notification = array();
				$notification ["notificationID"] = $row["notificationID"];
				$notification ["acceptable"] = $row["acceptable"];
				$notification ["notificationMessage"] = $row["message"];
				// push single group into final response array
				array_push($response["notificationJSONList"], $notification );
			}          
            // success
            $response["success"] = 1;
			$response["message"] = "Get Notification";
            // echo no users JSON
            echo json_encode($response);
			}else{          
            // failed
            $response["success"] = 0;
			$response["message"] = "No Notification";
            // echo no users JSON
            echo json_encode($response);
			}
	}else{          
            // failed
            $response["success"] = 0;
			$response["message"] = "Display notification database conncetion failed";
            // echo no users JSON
            echo json_encode($response);
	}
}
else{
	// failed
	$response["success"] = 0;
	$response["message"] = "Pass user failed";
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