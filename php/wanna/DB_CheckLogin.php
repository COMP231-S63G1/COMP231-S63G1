<?php
// array for JSON response
//$response = array();
 
// include db connect class
//require_once __DIR__ . '/include/DB_Connect.php';
 
// connecting to db
//$db = new DB_Connect();
if (isset($_POST["sessionid"]) && isset($_POST["userid"]) && isset($_POST["userType"])) {
	$sessionID = $_POST['sessionid'];
	$userID = $_POST['userid'];
	$userType = $_POST['userType'];
	session_id($sessionID);
	session_start();
	if($userID == $_SESSION['userid'] && $userType == $_SESSION['userType']){
		$sessionSuccess = 1;
		$sessionMessage = "session exist";
		
		// success
		//$response["success"] = 1;
		// echoing JSON response
		//echo json_encode($response);
	}
	else{
		$sessionSuccess = 0;
		$sessionMessage = "session does not exist";
		
		// required field is missing
		//$response["success"] = 0;
		// echoing JSON response
		//echo json_encode($response);
		}
}
else{
	$sessionSuccess = 0;
	$sessionMessage = "Check login pass data failed";
				
    // required field is missing
    //$response["success"] = 0; 
    // echoing JSON response
   // echo json_encode($response);
}
?>