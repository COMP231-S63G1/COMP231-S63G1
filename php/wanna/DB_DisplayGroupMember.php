<?php
$response = array();
 
// include db connect class
require_once __DIR__ . '/include/DB_Connect.php';
 
// connecting to db
$db = new DB_Connect();
if (isset($_POST["groupID"])) {
$groupID = $_POST["groupID"];
$result = mysql_query("SELECT `nickName`, `profileID` FROM `profile` where `profileID`in(SELECT DISTINCT(`profileID`) from `groupjoinin` WHERE `groupID`=$groupID)");
	
	if (!empty($result)) {
        // check for empty result
        if (mysql_num_rows($result) > 0) {
			// looping through all results
			$response["groupMemberList"] = array();
			while ($row = mysql_fetch_array($result)) {
				// temp user array
				$groupMember = array();
				$groupMember["profileID"] = $row["profileID"];
				$groupMember["groupMemberName"] = $row["nickName"];
				// push single group into final response array
				array_push($response["groupMemberList"], $groupMember);
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