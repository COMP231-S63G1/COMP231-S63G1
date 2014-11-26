<?php
$response = array();
 
// include db connect class
require_once __DIR__ . '/include/DB_Connect.php';
 
// connecting to db
$db = new DB_Connect();
require_once '/DB_CheckLogin.php';

if($sessionSuccess == 1){
	$userID=$_SESSION['userid'];

        $result = mysql_query("SELECT `eventID` FROM `eventjoinin` WHERE `userID` = $userID");	
	if (!empty($result)) {
        // check for empty result
        if (mysql_num_rows($result) > 0) {
			// looping through all results
			$response["eventList"] = array();
			while ($row = mysql_fetch_array($result)) {
                                $eventID = $row["eventID"];
                                $resultForEventName = mysql_query("SELECT `eventID`, `eventName` FROM `event` where `eventid` = $eventID");
				if (!empty($resultForEventName)) {
        			// check for empty result
     				   if (mysql_num_rows($resultForEventName) > 0) {
						$resultForEventName = mysql_fetch_array($resultForEventName);
						// temp user array
					        $event = array();
						$event["eventID"] = $resultForEventName["eventID"];
						$event["eventName"] = $resultForEventName["eventName"];
						// push single event into final response array
				                array_push($response["eventList"], $event);
				   }
                                }
			}        
            // success
            $response["success"] = 1;
	    $response["message"] = "Get event name";
            // echo no users JSON
            echo json_encode($response);
			}else{          
            // failed
            $response["success"] = 0;
	    $response["message"] = "No event name";
            // echo no users JSON
            echo json_encode($response);
			}
	}else{          
            // failed
            $response["success"] = 0;
	    $response["message"] = "Database conncetion failed";
            // echo no users JSON
            echo json_encode($response);
	}
}
else{
	// failed
	$response["success"] = 0;
	$response["message"] = "Session out of time";
	// echo no users JSON
	echo json_encode($response);
}
?>