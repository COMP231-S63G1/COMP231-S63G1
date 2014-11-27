<?php
 
// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['eventID']) && isset($_POST['eventName']) && isset($_POST['eventType']) && isset($_POST['eventDate']) && isset($_POST['eventTime']) && isset($_POST['eventVenue']) && isset($_POST['eventAddress']) && isset($_POST['eventPriceRange']) && isset($_POST['eventDescription'])) {
    $eventID = $_POST['eventID'];
    $eventType = $_POST['eventType'];
    $eventName = $_POST['eventName'];
    $eventDate = $_POST['eventDate'];
    $eventTime = $_POST['eventTime'];
    $eventVenue = $_POST['eventVenue'];
    $eventAddress= $_POST['eventAddress'];
    $eventPriceRange = $_POST['eventPriceRange'];
    $eventDescription = $_POST['eventDescription'];
 
    // include db connect class
    require_once __DIR__ . '/include/DB_Connect.php';
 
    // connecting to db
    $db = new DB_Connect();
 
    // mysql inserting a new row
    $result = mysql_query("UPDATE event SET eventType='$eventType', eventName = '$eventName', eventDate='$eventDate', eventTime='$eventTime', eventVenue='$eventVenue', eventAddress='$eventAddress', eventPriceRange=$eventPriceRange, eventDescription = '$eventDescription' WHERE eventID = $eventID;");
 
    // check if row inserted or not
    if ($result) {
        // successfully inserted into database
        $response["success"] = 1;
		$response["message"] = "Update event success.";
 
        // echoing JSON response
        echo json_encode($response);
    } else {
        // failed to insert row
        $response["success"] = 0;
		$response["message"] = "Update event failed.";
 
        // echoing JSON response
        echo json_encode($response);
    }
	}else{
	// failed
	$response["success"] = 0;
	$response["message"] = "Pass search type failed";
	echo json_encode($response);
}
?>