<?php
 
/*
 * Following code will insert data in to  groupjoinin with the profileid of the 
 * joinner and the group id the joinner want to join with 
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

	if (isset($_POST["groupID"])) {
            $groupID= $_POST['groupID'];


	$result = mysql_query("SELECT `userID`, `groupid` FROM `groupjoinin` where `userID` = $userID AND `groupid` = $groupID");
        if (mysql_num_rows($result) > 0) {
	    $response["success"] = 0;
            $response["message"] = "You already join this event";
	    echo json_encode($response);
        }else{


        //insert the profileID and eventID into the child table

	$result = mysql_query("INSERT INTO `groupjoinin` (`userID`, `groupID`) VALUES ($userID, $groupID);");
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