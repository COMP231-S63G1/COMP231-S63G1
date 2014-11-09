<?php
 
/*
 * Following code will get single profile details
 * A event is identified by profile id (profileID)
 */
 
// array for JSON response
$response = array();
 
// include db connect class
require_once __DIR__ . '/include/DB_Connect.php';
 
// connecting to db
$db = new DB_Connect();
 
// check for post data
if (isset($_POST["profileID"])) {
    $profileID = $_POST['profileID'];
 
    // get profile from profile table
    $result = mysql_query("SELECT nickName, description FROM profile WHERE profileID = $profileID");
 
    if (!empty($result)) {
        // check for empty result
        if (mysql_num_rows($result) > 0) {
 
            $result = mysql_fetch_array($result);
 
            $profileInformation = array();
            $profileInformation["nickName"] = $result["nickName"];
            $profileInformation["description"] = $result["description"];
	    
            // success
            $response["success"] = 1;
 
            // user node
            $response["profileInformation"] = array();
 
            array_push($response["profileInformation"], $profileInformation);
 
            // echoing JSON response
            echo json_encode($response);
        } else {
            // no event found
            $response["success"] = 0;
 
            // echo no users JSON
            echo json_encode($response);
        }
    } else {
        // no profile found
        $response["success"] = 0;
 
        // echo no users JSON
        echo json_encode($response);
    }
} else {
    // required field is missing
    $response["success"] = 0;
 
    // echoing JSON response
    echo json_encode($response);
}
?>