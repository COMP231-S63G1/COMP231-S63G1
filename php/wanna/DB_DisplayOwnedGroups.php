<?php
 
/*
 * Following code will get all owner's groups
 * A profile is identified by user id (userID)
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
	$profileID=$_SESSION['profileid'];
	// get profile from profile table
    $result = mysql_query("SELECT `groupID`, `groupName` FROM `group` WHERE `groupCreaterID`= $profileID");
	if (!empty($result)) {
        // check for empty result
        if (mysql_num_rows($result) > 0) {
			// looping through all results
			$response["groupList"] = array();
			while ($row = mysql_fetch_array($result)) {
				// temp user array
				$group = array();
				$group["groupID"] = $row["groupID"];
				$group["groupName"] = $row["groupName"];				
				// push all groups into final response array
				array_push($response["groupList"], $group);
			}          
            // success
            $response["success"] = 1;
			$response["message"] = "Get group name";
            // echo no users JSON
            echo json_encode($response);
			}else{          
            // failed
            $response["success"] = 0;
			$response["message"] = "No group name";
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
	$response["message"] = "Pass group type failed";
	echo json_encode($response);
}
?>