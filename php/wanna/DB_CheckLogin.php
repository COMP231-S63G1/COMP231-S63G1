<?php
// array for JSON response
//$response = array();
 
// include db connect class
//require_once __DIR__ . '/include/DB_Connect.php';
 
// connecting to db
//$db = new DB_Connect();
if (isset($_POST["sessionID"])&& isset($_POST["userID"])) {
	$sessionID = $_POST['sessionID'];
	$userID = $_POST['userID'];
	session_id($sessionID);
	session_start();
	if($userID==$_SESSION['userid']){
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
	$sessionMessage = "pass data failed";
				
    // required field is missing
    //$response["success"] = 0; 
    // echoing JSON response
   // echo json_encode($response);
}
?>