<?php
 
// array for JSON response
$response = array();
 
// check for required fields 

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
 
        // echoing JSON response
        echo json_encode($response);
    } else {
        // failed to insert row
        $response["success"] = 0;
 
        // echoing JSON response
        echo json_encode($response);
    }
?>