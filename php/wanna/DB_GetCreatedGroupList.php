<?php
$response = array();
 
// include db connect class
require_once __DIR__ . '/include/DB_Connect.php';
 
// connecting to db
$db = new DB_Connect();
require_once __DIR__ . '/DB_CheckLogin.php';

if($sessionSuccess == 1){
	$userID=$_SESSION['userid'];

        $result = mysql_query("SELECT `groupID`, `groupName` FROM `group` where `groupCreaterID` = $userID ");	
	if (!empty($result)) {
        // check for empty result
        if (mysql_num_rows($result) > 0) {
			// looping through all results
			// products node
			$response["groupList"] = array();
			while ($row = mysql_fetch_array($result)) {
				// temp user array
				$group = array();
				$group["groupID"] = $row["groupID"];
				$group["groupName"] = $row["groupName"];
				// push single event into final response array
				array_push($response["groupList"], $group);
			}
			//$result = mysql_fetch_array($result);
			
			//$response["groupList"] = $result;           
            // success
            $response["success"] = 1;
			$response["message"] = "Get created group name";
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
	$response["message"] = "Session out of time";
	// echo no users JSON
	echo json_encode($response);
}
?>