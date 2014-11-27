<?php
 
// array for JSON response
$response = array();

// include db connect class
    require_once __DIR__ . '/include/DB_Connect.php';
 
    // connecting to db
    $db = new DB_Connect();
 
require_once '/DB_CheckLogin.php';
if($sessionSuccess == 1){
    $userID=$_SESSION['userid'];
    $profileID = $_SESSION['profileid'];
 
// check for required fields
if (isset($_POST['eventName']) && isset($_POST['eventType']) && isset($_POST['eventDate']) && isset($_POST['eventTime']) && isset($_POST['eventVenue']) && isset($_POST['eventAddress']) && isset($_POST['eventPriceRange']) && isset($_POST['eventDescription'])) {
 
    $eventName = $_POST['eventName'];
    $eventType = $_POST['eventType'];
    $eventDate = $_POST['eventDate'];
    $eventTime = $_POST['eventTime'];
    $eventVenue = $_POST['eventVenue'];
    $eventAddress = $_POST['eventAddress'];
    $eventPriceRange = $_POST['eventPriceRange'];
    $eventDescription = $_POST['eventDescription'];
    // mysql inserting a new row
  
     $result = mysql_query("INSERT INTO wanna.event (eventID,eventCreaterID,eventType, eventName, eventDate, eventTime, eventVenue, eventAddress, eventPriceRange, eventDescription) VALUES (NULL,$profileID, '$eventType', '$eventName','$eventDate', '$eventTime', '$eventVenue', '$eventAddress', '$eventPriceRange', '$eventDescription');");
    // check if row inserted or not
    if ($result) {
        // successfully inserted into database
        $response["success"] = 1;
		$response["message"] = "Create event success.";
 
        // echoing JSON response
        echo json_encode($response);
    } else {
        // failed to insert row
        $response["success"] = 0;
		$response["message"] = "Create event failed.";
 
        // echoing JSON response
        echo json_encode($response);
    }
} else {
    // required field is missing
    $response["success"] = 0;
	$response["message"] = "Pass data failed.";
    // echoing JSON response
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
