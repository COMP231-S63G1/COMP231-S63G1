<?php
 
/*
 * Following code will get single profile information
 * A profile is identified by profile id (profileID)
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
	// get profile from profile table
    $result = mysql_query("SELECT * FROM profile WHERE userid = $userID");
	
	if (!empty($result)) {
        // check for empty result
        if (mysql_num_rows($result) > 0) {
 
            $result = mysql_fetch_array($result);
 
            $profileInformation = array();
            $profileInformation["profileID"] = $result["profileID"];
            $profileInformation["nickName"] = $result["nickName"];
            $profileInformation["age"] = $result["age"];
            $profileInformation["gender"] = $result["gender"];
            $profileInformation["description"] = $result["description"];
	    $eventID = $result["eventID"];
	    $eventName = "";
	    if($eventID!=NULL){
		$resultForEventName = mysql_query("SELECT eventName FROM wanna.event WHERE eventID = $eventID");
		if (!empty($resultForEventName)) {
        		// check for empty result
       			 if (mysql_num_rows($resultForEventName) > 0) {
 
         			   $resultForEventName = mysql_fetch_array($resultForEventName);
					$eventName =  $resultForEventName["eventName"];
			}
                }
            }
            $profileInformation["eventName"] = $eventName;           
            // success
            $response["success"] = 1;
			$response["message"] = "Get user profile information";
 
            // user node
            $response["profileInformation"] = array();
 
            array_push($response["profileInformation"], $profileInformation);
 
            // echoing JSON response
            echo json_encode($response);
        } else {
            // no profile found
            $response["success"] = 0;
			$response["message"] = "No user profile from database";
 
            // echo no users JSON
            echo json_encode($response);
        }
    } else {
        // no profile found
        $response["success"] = 0;
		$response["message"] = "Database connecton failed";
 
        // echo no users JSON
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