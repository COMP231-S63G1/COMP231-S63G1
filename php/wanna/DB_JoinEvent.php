<?php
 
/*
 * Following code will join the event table and profile table store the 
 * event id into the profile table 
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

	if (isset($_POST["eventID"])) {
  		  $eventID= $_POST['eventID'];
 
    	// get event name from event detail page and put in database under status

	$result = mysql_query("UPDATE WANNA.PROFILE SET PROFILE.EVENTID = $eventID WHERE PROFILE.USERID = $userID");
		if ($result) {	
		// successfully inserted into database	
        	$response["success"] = 1;
		$response["message"] = "Update event id into user profile success";
 
        	// echoing JSON response
        	echo json_encode($response);
		}
		else{
        	// inserted into database failed
        	$response["success"] = 0;
		$response["message"] = "Updata event id into user profile failed";
 
        	// echoing JSON response
        	echo json_encode($response);
		}
	}
	else{
        // pass data failed
        $response["success"] = 0;
	$response["message"] = "Pass data failed";
 
        // echoing JSON response
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