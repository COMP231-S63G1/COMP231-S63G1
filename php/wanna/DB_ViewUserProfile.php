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

if (isset($_POST['friendUserID'])) {
    $friendUserID = $_POST['friendUserID'];
       //get profile id from user table
     $result = mysql_query("SELECT `profileID` FROM `personprofile` WHERE `userid` = '$friendUserID'");
if (!empty($result)) {
        // check for empty result
        if (mysql_num_rows($result) > 0) {
     $result = mysql_fetch_array($result);
     $profileID = $result["profileID"];
	// get profile from profile table

    $result = mysql_query("SELECT * FROM `personprofile` WHERE `profileid` = $profileID");
	
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
			$response["message"] = "Get user profile information failed";
 
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
} else {
            // no profile found
            $response["success"] = 0;
			$response["message"] = "Get user profile id failed";
 
            // echo no users JSON
            echo json_encode($response);
        }
    } else {
        // no profile found
        $response["success"] = 0;
		$response["message"] = "Profile table connecton failed";
 
        // echo no users JSON
        echo json_encode($response);
    }
}else{
		// failed
		$response["success"] = 0;
		$response["message"] = "Parameter passing error";
		// echoing JSON response
		echo json_encode($response);	
} 
?>