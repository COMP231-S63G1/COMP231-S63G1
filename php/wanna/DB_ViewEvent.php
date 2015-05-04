<?php
 
/*
 * Following code will get single event details
 * A event is identified by event id (eventID)
 */
 
// array for JSON response
$response = array();
 
// include db connect class
require_once __DIR__ . '/include/DB_Connect.php';
 
// connecting to db
$db = new DB_Connect();
if (isset($_POST['eventID'])) {
     $eventID = $_POST['eventID'];
    // get a event from event table
    $result = mysql_query("SELECT * FROM event WHERE eventID = $eventID");
 
    if (!empty($result)) {
        // check for empty result
        if (mysql_num_rows($result) > 0) { 
            $result = mysql_fetch_array($result); 

            $eventDetail = array();
            $eventDetail["eventID"] = $result["eventID"];
            $eventDetail["eventType"] = $result["eventType"];
            $eventDetail["eventName"] = $result["eventName"];
            $eventDetail["eventDate"] = $result["eventDate"];
            $eventDetail["eventTime"] = $result["eventTime"];
            $eventDetail["eventVenue"] = $result["eventVenue"];
            $eventDetail["eventLocation"] = $result["eventAddress"];
            $eventDetail["eventPriceRange"] = $result["eventPriceRange"];
            $eventDetail["eventDescription"] = $result["eventDescription"];
            $eventDetail["pictureURL"] = $result["pictureURL"]; 
            // success
            $response["success"] = 1;
			$response["message"] = "Get event success."; 
            // user node
            $response["eventDetail"] = array();
 
            array_push($response["eventDetail"], $eventDetail);
 
            // echoing JSON response
            echo json_encode($response);
        } else {
            // no event found
            $response["success"] = 0;
			$response["message"] = "Get event failed.";
 
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
?>