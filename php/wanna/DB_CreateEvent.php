<?php
 
// array for JSON response
$response = array();

// include db connect class
    require_once __DIR__ . '/include/DB_Connect.php';
 
    // connecting to db
    $db = new DB_Connect();
 
require_once __DIR__ . '/DB_CheckLogin.php';
if($sessionSuccess == 1){
    $userID=$_SESSION['userid'];
 
// check for required fields
if (isset($_POST['eventName']) && isset($_POST['eventType']) && isset($_POST['eventDate']) && isset($_POST['eventTime']) && isset($_POST['eventVenue']) && isset($_POST['eventAddress']) && isset($_POST['eventPriceRange']) && isset($_POST['eventDescription']) && isset($_POST['pictureURL']) && isset($_POST['BoolImageChange'])) {
 
    $eventName = $_POST['eventName'];
    $eventType = $_POST['eventType'];
    $eventDate = $_POST['eventDate'];
    $eventTime = $_POST['eventTime'];
    $eventVenue = $_POST['eventVenue'];
    $eventAddress = $_POST['eventAddress'];
    $eventPriceRange = $_POST['eventPriceRange'];
    $eventDescription = $_POST['eventDescription'];
        $pictureURL= "/Images/" . $_POST['pictureURL'] . ".jpg";
        $BoolImageChange = $_POST['BoolImageChange'];

    // mysql inserting a new row
  
   if ($BoolImageChange == "true") {
     $result = mysql_query("INSERT INTO `event` (`eventCreaterID`, `eventType`, `eventName`, `eventDate`, `eventTime`, `eventVenue`, `eventAddress`, `eventPriceRange`, `eventDescription`, `pictureURL`) VALUES ('$userID', '$eventType', '$eventName', '$eventDate', '$eventTime', '$eventVenue', '$eventAddress', '$eventPriceRange', '$eventDescription', '$pictureURL')");
     }
     else if ($BoolImageChange == "false") {
      $result = mysql_query("INSERT INTO `event` (`eventCreaterID`, `eventType`, `eventName`, `eventDate`, `eventTime`, `eventVenue`, `eventAddress`, `eventPriceRange`, `eventDescription`) VALUES ('$userID', '$eventType', '$eventName', '$eventDate', '$eventTime', '$eventVenue', '$eventAddress', '$eventPriceRange', '$eventDescription')");
      }
	 if ($result) {
		 $joinResult = mysql_query("INSERT INTO `eventjoinin` (`userID`, `eventID`) VALUES ($userID, (SELECT `eventID` FROM `event` WHERE `eventCreaterID` = '$userID' AND `eventType` = '$eventType' AND `eventName` = '$eventName' AND `eventDate` = '$eventDate' AND `eventTime` = '$eventTime' AND `eventVenue` = '$eventVenue' AND `eventAddress` = '$eventAddress' AND `eventPriceRange` = '$eventPriceRange'))");
		 if($joinResult){
        $response["success"] = 1;
		$response["message"] = "Create and event success."; 
        // echoing JSON response
        echo json_encode($response);
		 }
		 else{
        // failed to insert row
        $response["success"] = 0;
		$response["message"] = "Create and join event failed."; 
        // echoing JSON response
        echo json_encode($response); 
		 }
}else{
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