<?php
$response = array();
 
// include db connect class
require_once __DIR__ . '/include/DB_Connect.php';
 
// connecting to db
$db = new DB_Connect();
require_once __DIR__ . '/DB_CheckLogin.php';
if($sessionSuccess == 1){
if (isset($_POST["groupID"])) {
$groupID = $_POST["groupID"];
$userType = $_SESSION['userType'];

if($userType == "Person"){
	$result = mysql_query("SELECT `nickName`, `profileID` FROM `personprofile` where `profileID` in(SELECT `profileID` FROM `users` WHERE `userid` in (SELECT DISTINCT(`userid`) from `groupjoinin` WHERE `groupID`=$groupID))");
}else if($userType == "Organization"){
	$result = mysql_query("SELECT `nickName`, `profileID` FROM `organizationfile` where `profileID `in (SELECT `profileID` FROM `users` WHERE `userid` in(SELECT DISTINCT(`userid`) from `groupjoinin` WHERE `groupID`=$groupID))");
}
	
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
			$response["message"] = "Display member database conncetion failed";
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
}else{
		// failed
		$response["success"] = 0;
		$response["message"] = $sessionMessage;
		// echoing JSON response
		echo json_encode($response);	
}
?>