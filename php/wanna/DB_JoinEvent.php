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

require_once __DIR__ . '/DB_CheckLogin.php';

if($sessionSuccess == 1){
	$userID=$_SESSION['userid'];
        $profileID=$_SESSION['profileid'];
	// check for post data

	if (isset($_POST["eventID"])) {
            $eventID= $_POST['eventID'];

	$result = mysql_query("SELECT `userid`, `eventID` FROM `eventjoinin` where `userID` = $userID AND `eventID` = $eventID");
        if (mysql_num_rows($result) > 0) {
	    $response["success"] = 0;
		$response["message"] = "You already join this event";
	    echo json_encode($response);
        }else{
        //insert the profileID and eventID into the child table
		$result = mysql_query("INSERT INTO `eventjoinin` (`userID`, `eventID`) VALUES ($userID, $eventID)");
		if ($result) {	
		// successfully inserted into database	
        	$response["success"] = 1;
			$response["message"] = "Join this event succeed";
 
        	// echoing JSON response
        	echo json_encode($response);
		}
		else{
        	// inserted into database failed
        	$response["success"] = 0;
		$response["message"] = "Join this event failed";
 
        	// echoing JSON response
        	echo json_encode($response);
		}
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