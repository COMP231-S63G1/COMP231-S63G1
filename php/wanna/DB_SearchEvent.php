<?php
$response = array();
 
// include db connect class
require_once __DIR__ . '/include/DB_Connect.php';
 
// connecting to db
$db = new DB_Connect();
if (isset($_POST["searchEventName"])) {
$searchEventName = $_POST["searchEventName"];
$result = mysql_query("SELECT eventID, eventName FROM event where eventName SOUNDS LIKE '$searchEventName'");	
	if (!empty($result)) {
        // check for empty result
        if (mysql_num_rows($result) > 0) {
			// looping through all results
			// products node
			$response["eventList"] = array();
			while ($row = mysql_fetch_array($result)) {
				// temp user array
				$event = array();
				$event["eventID"] = $row["eventID"];
				$event["eventName"] = $row["eventName"];
				// push single event into final response array
				array_push($response["eventList"], $event);
			}
			//$result = mysql_fetch_array($result);
			
			//$response["eventList"] = $result;           
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
	$response["message"] = "Pass data failed";
	// echo no users JSON
	echo json_encode($response);
}
?>